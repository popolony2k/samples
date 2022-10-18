package org.robotmessenger.comm;

import java.nio.ByteBuffer;


/**
 * Connection event listener interface.
 *  
 * @author popolony2k
 *
 */
public interface IConnectionListener {
	
	/**
	 * Must be implemented to handle websocket opening communication event;
	 */
	public void onOpen();

	/**
	 * Must be implemented to handle websocket closing communication event;
	 * @param reasonCode The reason code closing operation;
	 */
	public void onClose( int reasonCode );
	
	/**
	 * Must be implemented to handle websocket errors communication events;
	 * @param error Error message;
	 */
	public void onError( String error );

	/**
	 * Must be implemented to handle websocket received message;
	 * 
	 * @param message Message to be processed as string;
	 */
	public void onMessage( String message );

	/**
	 * Must be implemented to handle websocket received message;
	 * 
	 * @param message Message to be processed as ByteBuffer;
	 */
	public void onMessage( ByteBuffer message );
}
