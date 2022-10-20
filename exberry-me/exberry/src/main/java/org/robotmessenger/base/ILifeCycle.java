package org.robotmessenger.base;


/**
 * Basic life cycle object management.
 * 
 * @author popolony2k
 *
 */
public interface ILifeCycle {
	
    /**
     * Start the connection object establishing communication with server. 
     * @return true on success, otherwise false;
     */
    public boolean start();

    /**
     * Stop the connection object establishing communication with server. 
     * @return true on success, otherwise false;
     */
    public boolean stop();

    /**
     * Put thread on wait state. This wait state can be stopped by {@link Stop} method. 
     * @return true on success, otherwise false;
     */
    public boolean join();
    
    /**
     * Return the running status of an ILifeCycle object;
     * @return
     */
    public boolean isRunning();
}
