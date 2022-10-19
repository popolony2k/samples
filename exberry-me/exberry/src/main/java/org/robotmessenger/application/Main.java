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
		    	else  {
		            System.out.println( "Failed to start application" );
		            System.exit( 0 );
		    	}
	        }
    	} ).start();
    	
    	waitEnterKey();
        
        if( session != null )
        	session.stop();
        
        System.out.println( "Application stopped !!!" );
    }
}
