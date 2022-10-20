package org.robotmessenger.comm;

/**
 * Basic connection life cycle object management.
 * 
 * @author popolony2k
 *
 */
public interface IConnection {

    /**
     * Send a message through an IConnection object;
     * @param data String data to send;
     * @return true if data was successfully sent;
     */
    public boolean send( String data );
    
    /**
     * Must be implemented to return the object connection status;
     * @return true if object is connected;
     */
    public boolean isConnected();

	
}  // IConnection