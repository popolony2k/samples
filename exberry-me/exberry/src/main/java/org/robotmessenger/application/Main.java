package org.robotmessenger.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.Scanner;

import org.robotmessenger.comm.IConnectionListener;
import org.robotmessenger.exberry.ExberryOrderManager;
import org.robotmessenger.exberry.dto.Enums.OrderType;
import org.robotmessenger.exberry.dto.Enums.Side;
import org.robotmessenger.exberry.dto.Enums.TimeInForce;
import org.robotmessenger.exberry.dto.request.CancelOrderRequest;
import org.robotmessenger.exberry.dto.request.ModifyOrderRequest;
import org.robotmessenger.exberry.dto.request.PlaceOrderRequest;
import org.robotmessenger.exberry.dto.request.ReplaceOrderRequest;


class ConnectionAdapter implements IConnectionListener  {

	@Override
	public void onOpen() {
		System.out.println( "Connection stablished" );
	}

	@Override
	public void onClose( int nReasonCode ) {
		System.out.println( "Connection closed" );
	}

	@Override
	public void onError( String error ) {
		System.out.println( "Error received -> " + error );		
	}

	@Override
	public void onMessage(String message ) {
		System.out.println( "String message received -> " + message  );		
	}

	@Override
	public void onMessage( ByteBuffer message ) {
		System.out.println( "ByteBuffer message received -> " + message  );		
	}
} // Connection Adapter



/**
 * 
 * Main application entry-point.
 */
public class Main {

	private static ExberryOrderManager session = null;

	
	/**
	 * Wait for enter input key.
	 * @throws IOException 
	 */
	private static char readKey() throws IOException  {
		
        BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
        String         data   = reader.readLine();
        
        if( data.length() > 0 )
        	return data.charAt( 0 );
        
        return ' ';
	}
	
	/**
	 * Draw menu with all exberry operations available.
	 */
	private static void drawMenu()  {
		System.out.println( "Exberry Operations" );
		System.out.println( "============================================" );
		
		System.out.println( "1) Send authentication" );
		System.out.println( "2) Request Order Book State" );
		System.out.println( "3) Request Order Book Depth" );
		System.out.println( "4) Request Mass Order Status" );
		System.out.println( "5) Request Execution Reports" );
		System.out.println( "6) Request Trades" );
		System.out.println( "7) Place Order" );
		System.out.println( "8) Cancel Order" );
		System.out.println( "9) Mass Cancel" );
		System.out.println( "0) Modify Order" );
		System.out.println( "A) Replace Order" );	
		System.out.println( "Q) Quit application" );
	}
	
	/**
	 * User entry for place order call;
	 * @param order The order object to send;
	 * @return true if success otherwise false;
	 */
	private static boolean placeOrder( PlaceOrderRequest.PlaceOrder order )  {
		
		order.orderType   = OrderType.Limit;
		order.side        = Side.Buy;
		order.quantity    = 1.3;
		order.price       = 100.33;
		order.instrument  = "INS3";
		order.mpOrderId   = 5004;
	    order.timeInForce = TimeInForce.GTC;
	    order.userId      = "UATUserTest10";
		
		return ( session.isConnected() && session.placeOrder( order ) );
	}
	
	/**
	 * User entry for cancel order call;
	 * @param order The order object to send;
	 * @return true if success otherwise false;
	 */
	private static boolean cancelOrder( CancelOrderRequest.CancelOrder order )  {
		
		order.mpOrderId   = 5004L;
		order.instrument  = "INS3";
		
		return ( session.isConnected() && session.cancelOrder( order ) );
	}
	
	/**
	 * User entry for mass cancel orders call;
	 * @return true if success otherwise false;
	 */
	private static boolean massCancel()  {
		
		System.out.print( "Instrument -> " );

		@SuppressWarnings("resource")
		Scanner   scanner    = new Scanner( System.in );
		String    instrument = scanner.nextLine();
					
		return ( session.isConnected() && session.massCancel( instrument ) );
	}
	
	/**
	 * User entry for modify order call;
	 * @param order The order object to send;
	 * @return true if success otherwise false;
	 */
	private static boolean modifyOrder( ModifyOrderRequest.ModifyOrder order )  {

		System.out.print( "Order ID -> " );

		@SuppressWarnings("resource")
		Scanner   scanner    = new Scanner( System.in );
		long      orderId    = scanner.nextInt();
		System.out.print( "Instrument -> " );
		String    instrument = scanner.next();
		System.out.print( "Quantity -> " );
		double    quantity   = Double.valueOf( scanner.next() );
		
		order.orderId    = orderId;
		order.instrument = instrument;
		order.quantity   = quantity;
		
		return ( session.isConnected() && session.modifyOrder( order ) );
	}
	
	/**
	 * User entry for replace order call;
	 * @param order The order object to send;
	 * @return true if success otherwise false;
	 */
	private static boolean replaceOrder( ReplaceOrderRequest.ReplaceOrder order )  {

//		System.out.print( "Order ID -> " );
//
//		@SuppressWarnings("resource")
//		Scanner   scanner    = new Scanner( System.in );
//		long      orderId    = scanner.nextInt();
//		System.out.print( "Instrument -> " );
//		String    instrument = scanner.next();
//		System.out.print( "Quantity -> " );
//		double    quantity   = Double.valueOf( scanner.next() );
//		
//		order.orderId    = orderId;
//		order.instrument = instrument;
//		order.quantity   = quantity;
		
		return ( session.isConnected() && session.replaceOrder( order ) );
	}
	
	/**
	 * Main program entry point.
	 * @param args command line arguments
	 */
    public static void main( String[] args ) {
    	
    	/*
    	 *  For those who are trying to connect through a proxy server
    	 *  uncomment below
    	 */
    	//System.setProperty( "java.net.useSystemProxies", "true" );


    	try {
    		String strSecretKey = "8e46b743e9cfa5c3ec1dfdfec2efe14b04d8f72dc574ea50582edf8ce5dbe792";
    		String strApiKey    = "a31a75f4-0bf7-4264-8842-473fb77f7bca";
 
			session = new ExberryOrderManager( ExberryOrderManager.EXBERRY_STAGING_URI, strApiKey, strSecretKey );
			session.setVerbose( true );
		} catch (Exception e) {
			e.printStackTrace();
		}
     	
    	if( ( session != null ) && session.start() )  {
    		
    		PlaceOrderRequest.PlaceOrder      placeOrder   = PlaceOrderRequest.newInstance();
    		CancelOrderRequest.CancelOrder    cancelOrder  = CancelOrderRequest.newInstace();
    		ModifyOrderRequest.ModifyOrder    modifyOrder  = ModifyOrderRequest.newInstance();
    		ReplaceOrderRequest.ReplaceOrder  replaceOrder = ReplaceOrderRequest.newInstance();    		
    		boolean                           exit  = false;
    		
   
    		System.out.println( "Application running!" );
            
        	drawMenu();
        	
        	do {
        	  char key;
		
        	  try {
        		  key = readKey();
				
        		  switch( key )  {
        		  	case 'q' :
	        	  	case 'Q' :  
	        	  		exit = true; 
	        	  		break;
	        	  	case '1' :
			            if( !session.isConnected() || !session.sendAuth() )
			            	System.err.println( "Error sending authorization" );
			            break;
	        	  	case '2' :
			            if( !session.isConnected() || !session.requestOrderBookState() )
			            	System.err.println( "Error sending requestOrderBookState()" );
			            break;
	        	  	case '3' :
			            if( !session.isConnected() || !session.requestOrderBookDepth( 0L ) )
			            	System.err.println( "Error sending requestOrderBookDepth()" );
			            break;
	        	  	case '4' :
			            if( !session.isConnected() || !session.requestMassOrderStatus() )
			            	System.err.println( "Error sending requestMassOrderStatus()" );
			            break;
	        	  	case '5' :
			            if( !session.isConnected() || !session.requestExecutionReports( 0L ) )
			            	System.err.println( "Error sending requestExecutionReports()" );
			            break;
	        	  	case '6' :
			            if( !session.isConnected() || !session.requestTrades( 0L ) )
				            System.err.println( "Error sending requestTrades()" );
			            break;
	        	  	case '7' :
			            if( !placeOrder( placeOrder ) )
				            System.err.println( "Error sending placesOerder()" );
			            break;
	        	  	case '8' :
			            if( !cancelOrder( cancelOrder ) )
				            System.err.println( "Error sending cancelOrder()" );
			            break;
	        	  	case '9' :
			            if( !massCancel() )
				            System.err.println( "Error sending massCancel()" );
			            break;
	        	  	case '0' :
			            if( !modifyOrder( modifyOrder ) )
				            System.err.println( "Error sending modifyOrder()" );
			            break;
	        	  	case 'a' :
	        	  	case 'A' :
			            if( !replaceOrder( replaceOrder ) )
				            System.err.println( "Error sending replaceOrder()" );
			            break;

			        default :
			        	drawMenu();
        		  }
        	  } catch( IOException e ) {
        		  System.err.println( e.getMessage() );
			  }        	  
        	} while( !exit );
        	
            if( session != null )
            	session.stop();
        	
            System.out.println( "Application finished!" );
    	}
    	else  {
            System.out.println( "Failed to start application" );
            System.exit( 0 );
    	}    	
    }
}
