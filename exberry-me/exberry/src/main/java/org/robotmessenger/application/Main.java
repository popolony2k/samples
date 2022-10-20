package org.robotmessenger.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import org.robotmessenger.comm.IConnectionListener;
import org.robotmessenger.exberry.ExberryOrderManager;


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

	static ExberryOrderManager session = null;

	
	/**
	 * Wait for enter input key.
	 * @throws IOException 
	 */
	public static char readKey() throws IOException  {
		
        BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
        String         data   = reader.readLine();
        
        if( data.length() > 0 )
        	return data.charAt( 0 );
        
        return ' ';
	}
	
	/**
	 * Draw menu with all exberry operations available.
	 */
	public static void drawMenu()  {
		System.out.println( "Exberry Operations" );
		System.out.println( "============================================" );
		
		System.out.println( "1) Send authentication" );
		System.out.println( "2) Request Order Book State" );
		System.out.println( "3) Request Order Book Depth" );
		System.out.println( "4) Request Mass Order Status" );
		System.out.println( "5) Request Execution Reports" );
		System.out.println( "6) Request Trades" );
		System.out.println( "Q) Quit application" );
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
		} catch (Exception e) {
			e.printStackTrace();
		}
     	
    	if( ( session != null ) && session.start() )  {
    		
    		boolean   exit = false;
   
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
			            if( !session.sendAuth() )
			            	System.err.println( "Error sending authorization" );
			            break;
	        	  	case '2' :
			            if( !session.requestOrderBookState() )
			            	System.err.println( "Error sending requestOrderBookState()" );
			            break;
	        	  	case '3' :
			            if( !session.requestOrderBookDepth( 0L ) )
			            	System.err.println( "Error sending requestOrderBookDepth()" );
			            break;
	        	  	case '4' :
			            if( !session.requestMassOrderStatus() )
			            	System.err.println( "Error sending requestMassOrderStatus()" );
			            break;
	        	  	case '5' :
			            if( !session.requestExecutionReports( 0L ) )
			            	System.err.println( "Error sending requestExecutionReports()" );
			            break;
	        	  	case '6' :
			            if( !session.requestTrades( 0L ) )
				            System.err.println( "Error sending requestTrades()" );
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
