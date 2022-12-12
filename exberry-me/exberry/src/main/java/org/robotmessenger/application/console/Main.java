package org.robotmessenger.application.console;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import org.robotmessenger.comm.IConnectionListener;
import org.robotmessenger.comm.websocket.Connection;

public class Main  {
	
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
    	
    	if( args.length > 0 )  {
			try {
				URI uri = new URI( args[0] );
				Connection connection = new Connection( uri, new IConnectionListener() {
					
					@Override
					public void onOpen() {
						System.out.println( "Connection stablished" );
					}
					
					@Override
					public void onMessage(ByteBuffer message) {
						System.out.println( "REVC -> " + new String( message.array() ) );
					}
					
					@Override
					public void onMessage(String message) {
						System.out.println( "REVC -> " + message );
					}
					
					@Override
					public void onError(String error) {
						System.err.println( "Error -> " + error );
					}
					
					@Override
					public void onClose(int reasonCode) {
						System.out.println( "Connection closed. Reason code [" + reasonCode + "]" );
					}
				} );
				
				if( !connection.start() )  {
					System.err.println( "Error connection to [" + args[0] + "]" );
					return;
				}
				
				System.out.println( "Websocket application started" );
				
				connection.join();
				
			} catch( URISyntaxException e ) {
				System.err.println(e);
			}
    	}
    }
}