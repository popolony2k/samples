package org.robotmessenger.exberry;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.robotmessenger.base.ILifeCycle;
import org.robotmessenger.comm.IConnectionListener;
import org.robotmessenger.comm.websocket.Connection;
import org.robotmessenger.exberry.dto.request.BaseRequest;
import org.robotmessenger.exberry.dto.request.CreateSessionRequest;


/**
 * Defines all exberry session available operations.
 * 
 * @author popolony2k
 *
 */
public class ExberrySession implements ILifeCycle, IConnectionListener  {

	String                  secretKey;
	Connection              connection;
	IExberrySessionListener listener;
	boolean                 verbose = false;
	CreateSessionRequest    session = new CreateSessionRequest();
	
	
	
	// Private methods
	/**
	 * Send a request to server;
	 * @param request The object request to send;
	 * @return true if operation was sucessfull;
	 */
	protected boolean sendRequest( BaseRequest request )  {
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String strJson = gson.toJson( request );
				
		if( verbose )  {
			System.out.println( strJson );
		}
				
		return ( ( connection != null ) && ( connection.send( strJson ) ) );

	}
	
	/**
	 * Constructor. Initialize all class data.
	 * @param strURI The Exberry environment URI to connect;
	 * @param secreKey The user session secret key used to decrypt data;
	 * @throws URISyntaxException 
	 */
	public ExberrySession( String strURI, String apiKey, String secretKey ) throws URISyntaxException {

		try {
			URI uri = new URI( strURI );
			connection = new Connection( uri, this );
			this.secretKey   = secretKey;
			session.d.apiKey = apiKey;
		} catch( URISyntaxException e ) {
			throw e;
		}
	}
	
	/**
	 * Set the verbose information mode to show debug information on default console;
	 * @param verbose The new verbose mode;
	 */
	public void setVerbose( boolean verbose )  {
		
		this.verbose = verbose;
	}
	
	/**
	 * Get the verbose mode status
	 * @return true if verbose mode is active, otherwise false;
	 */
	public boolean getVerbose()  {
		
		return this.verbose;
	}
	
	/**
	 * Add an IExberrySessionListener event handler to this instance object;
	 * @param listener
	 */
	public void addExberrySessionListener( IExberrySessionListener listener )  {
		
		this.listener = listener;
	}
	
	/**
	 * Send an authentication request to Exberry server.
	 *
	 * @return true if success otherwise false;
	 */
	public boolean sendAuth()  {
				
		try {
			long          timeStamp    = System.currentTimeMillis();			
			String        strSignature = String.format( "\"apiKey\":\"%s\",\"timestamp\":\"%d\"", session.d.apiKey, timeStamp );
			Mac           sha256HMAC   = Mac.getInstance( "HmacSHA256" );
			SecretKeySpec secretKey    = new SecretKeySpec( this.secretKey.getBytes( "UTF-8" ), "HmacSHA256" );
			  
			sha256HMAC.init( secretKey );

			session.d.signature = new BigInteger( 1, sha256HMAC.doFinal( strSignature.getBytes( "UTF-8" ) ) ).toString( 16 );
			session.d.timestamp = timeStamp;
						
			return sendRequest( session );

		} catch( Exception e ) {
			onError( e.getMessage() );
			return false;
		}
	}
	
	// ILifeCycle implementation

	/**
	 * Start connection to exberry server through websocket;
	 */
	@Override
	public boolean start() {
		
		return ( ( connection != null ) && connection.start() );
	}

	/**
	 * Stop the previous opened connection to exberry server;
	 */
	@Override
	public boolean stop() {
		
		return ( ( connection != null ) && connection.stop() );
	}

	/**
     * Put thread on wait state. This wait state can be stopped by {@link stop} method. 
     * @return true on success, otherwise false;
     */
	@Override
	public boolean join() {
		
		return ( ( connection != null ) && connection.join() );
	}
	
    /**
     * Return the running status of an ILifeCycle object;
     * @return true if object was started by start() method;
     */
	@Override
    public boolean isRunning()  {
    	
    	return connection.isRunning();
    }
	
    /**
     * Return the object connected status;
     * @return true if object is connected;
     */
    public boolean isConnected()  {
    	
    	return connection.isConnected();
    }
	
	// IConnectionListener event handling

	/**
	 * Handle websocket opening communication event with exberry;
	 */
	@Override
	public void onOpen() {
		
		if( listener != null )
			listener.onOpen( this );
	}

	/**
	 * Handle websocket closing communication event with exberry;
	 * 
	 * @param reasonCode Close reason code; 
	 */
	@Override
	public void onClose( int reasonCode ) {

		if( listener != null )
			listener.onClose( this, reasonCode );
	}

	/**
	 * Handle communication error events from websocket implementation;
	 * 
	 * @param error The error message;
	 */
	@Override
	public void onError( String error ) {
		
		if( verbose )
			System.err.println( error );
		
		if( listener != null )
			listener.onError( this, error );
	}

	/**
	 * Handle websocket String message transfer processing event with data from exberry;
	 * 
	 * @param message The exbrry server message received; 
	 */
	@Override
	public void onMessage( String message ) {
		
		if( verbose )
			System.out.println( message );
		
		if( listener != null )
			listener.onMessage( this, message );
		
		// TODO: Deserialize here ?????!!
	}

	/**
	 * Handle websocket ByteBuffer message transfer processing event with data from exberry;
	 * 
	 * @param message The exbrry server message received;
	 */
	@Override
	public void onMessage( ByteBuffer message ) {
		
		// TODO: Will be needed ?
	}
	
}  // ExberrySession
