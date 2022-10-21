package org.robotmessenger.comm.websocket;

import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.robotmessenger.base.ILifeCycle;
import org.robotmessenger.comm.IConnection;
import org.robotmessenger.comm.IConnectionListener;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import java.net.URI;
import java.nio.ByteBuffer;


/**
 * 
 * Defines a websocket connection layer.
 * 
 * @author popolony2k
 *
 */
@ClientEndpoint
public class Connection implements ILifeCycle, IConnection {
	
    private Session             session     = null;
    private URI                 endpointURI = null;
    private IConnectionListener listener    = null;
    private WebSocketContainer  container   = null;
    private boolean             isRunning   = false;
    private boolean             isConnected = false;

    

    /**
     * Constructor. Initialize class data;
     * 
     * @param endpointURI connection URI endpoint;
     * @param listener The listener to register;
     */
    public Connection( URI endpointURI, IConnectionListener listener ) {
    	
    	this.endpointURI = endpointURI;
    	this.listener = listener;
    }
    
    /**
     * Start the connection object establishing communication with server. 
     * @return true on success, otherwise false;
     */
    public boolean start()  {
    	
    	if( session == null )  {
	        try {
	            container = ContainerProvider.getWebSocketContainer();
	            container.connectToServer( this, endpointURI );
	            isRunning = true;
	        } catch ( Exception e ) {
	        	if( listener != null )
	        		listener.onError( e.getMessage() );
	        	
	            return false;
	        }
	        
	        return true;
    	}
    	
    	return false;
    }

    /**
     * Stop the connection object establishing communication with server. 
     * @return true on success, otherwise false;
     */
    public boolean stop()  {
    	
    	if( session != null )  {
	    	try  {
	    		session.close();
	    		
	    		synchronized( container )  {
	    			container.notify();
	    		}
	    		
	    		container = null;
	    		isRunning = false;
	    	} catch( Exception e )  {
	        	if( listener != null )
	        		listener.onError( e.getMessage() );
	        	
	        	return false;
	    	}
	    		    	
	    	return true;
    	}
    	
    	return false;
    }

    /**
     * Put thread on wait state. This wait state can be stopped by {@link stop} method. 
     * @return true on success, otherwise false;
     */
    public boolean join()  {
    	
    	if( session != null )  {
	    	try {
	    		synchronized( container )  {
	    			container.wait();
	    		}
			} catch( InterruptedException e ) {
	        	if( listener != null )
	        		listener.onError( e.getMessage() );
	        	
	        	return false;
			}
	    	
	    	return true;
    	}
    	
    	return false;
    }
    
    /**
     * Return the running status of an ILifeCycle object;
     * @return true if object was started by start() method;
     */
    @Override
    public boolean isRunning()  {
    	
    	return isRunning;
    }

        
    /**
     * Return the {@link IConnectionListener} object previously registered
     * through {@link addMessageListener}; 
     * @return the listener object;
     */
    public IConnectionListener getMessageListener()  {
    	
    	return listener;
    }

    /**
     * Send a message through connection websocket;
     * @param data String data sent;
     * @return true if data was successfully sent;
     */
    @Override
    public boolean send( String data ) {
    	
    	try  {
        this.session.getAsyncRemote().sendText( data );
    	}  catch( Exception e )  {
    		
            if ( this.listener != null ) {
            	listener.onError( e.getMessage() );
            }

    		return false;
    	}
    	
    	return true;
    }
    
    /**
     * Return the object connected status;
     * @return true if object is connected;
     */
    @Override
    public boolean isConnected()  {
    	
    	return isConnected;
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param session the user session which is opened.
     */
    @OnOpen
    public void onOpen( Session session ) {

        if ( this.listener != null ) {
            this.listener.onOpen();
        }

        this.isConnected = true;
        this.session     = session;
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param session the user session which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    public void onClose( Session session, CloseReason reason ) {
    	        
        if ( this.listener != null ) {
            this.listener.onClose( reason.getCloseCode().getCode() );
        }

    	this.isConnected = false;
        this.session     = null;
    }
    
    /**
     * Callback hook for Connection error events.
     *  
     * @param session The session in use when the error occurs;
     * @param t The throwable representing the problem;
     */
    @OnError
    public void onError( Session session, Throwable t )  {

        if ( this.listener != null ) {
            this.listener.onError( t.getMessage() );
        }
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client receive a message.
     *
     * @param message The message received in string format;
     */
    @OnMessage
    public void onMessage( String message ) {
    	
        if ( this.listener != null ) {
            this.listener.onMessage( message );
        }
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client receive a message.
     *
     * @param message The message received in byte buffer format;
     */
   @OnMessage
   public void onMessage( ByteBuffer message ) {
	   
       if ( this.listener != null ) {
           this.listener.onMessage( message );
       }
   }
}
