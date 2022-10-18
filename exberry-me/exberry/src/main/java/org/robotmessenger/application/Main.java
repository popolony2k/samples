package org.robotmessenger.application;

import java.nio.ByteBuffer;
import java.util.Scanner;

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
	 */
	public static void waitEnterKey()  {
		
        Scanner scanner = new Scanner( System.in );
        
        scanner.nextLine();
        scanner.close();

	}
	
	/**
	 * Main program entry point.
	 * @param args command line arguments
	 */
    public static void main( String[] args ) {

    	try {
    		String strSignature = "92e48fb675b749fe82ee78eccf39183ade91ce06bd4f1f5b1c6639f9c617587e";
    		String strApiKey = "a31a75f4-0bf7-4264-8842-473fb77f7bca";
        	//String strURI = "wss://demo.piesocket.com/v3/channel_1?api_key=VCXCEuvhGcBDP7XhiJJUDvR1e1D3eiVjgZ9VRiaV&notify_self";    	
			//URI uri = new URI( strURI );

			session = new ExberryOrderManager( ExberryOrderManager.EXBERRY_STAGING_URI, strApiKey, strSignature );
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	new Thread( new Runnable()  {

    		public void run() {	    	
		    	if( ( session != null ) && session.start() )  {
		            System.out.println( "Application running!" );
		            
		            if( !session.sendAuth() )
		            	System.err.println( "Error sending authorization" );
		            else
		            //if( !session.requestOrderBookState() )
		            //	System.err.println( "Error sending requestOrderBookState()" );
		            //else
		            //if( !session.requestOrderBookDepth( 0L ) )
		            //	System.err.println( "Error sending requestOrderBookDepth()" );
		            //else
		            //if( !session.requestMassOrderStatus() )
		            //	System.err.println( "Error sending requestMassOrderStatus()" );
		            //else
		            //if( !session.requestExecutionReports( 0L ) )
		            //	System.err.println( "Error sending requestExecutionReports()" );
                    //else
		            if( !session.requestTrades( 0L ) )
			            System.err.println( "Error sending requestTrades()" );

		            session.join();
		    	}
		    	else
		            System.out.println( "Failed to start application" );
	        }
    	} ).start();
    	
    	waitEnterKey();
        
        if( session != null )
        	session.stop();
        
        System.out.println( "Application stopped !!!" );
    }
}
