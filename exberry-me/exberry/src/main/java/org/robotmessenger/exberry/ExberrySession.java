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

	String                secretKey;
	Connection            connection;
	CreateSessionRequest  session = new CreateSessionRequest();
	
	
	
	// Private methods
	/**
	 * Send a request to server;
	 * @param request The object request to send;
	 * @return true if operation was sucessfull;
	 */
	protected boolean sendRequest( BaseRequest request )  {
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String strJson = gson.toJson( request );
				
		System.out.println( strJson );
				
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
			connection  = new Connection( uri, this );
			this.secretKey = secretKey;
			session.d.apiKey = apiKey;
		} catch( URISyntaxException e ) {
			throw e;
		}
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
		
	}

	/**
	 * Handle websocket closing communication event with exberry;
	 */
	@Override
	public void onClose( int reasonCode ) {
		
	}

	/**
	 * Handle communication error events from websocket implementation;
	 */
	@Override
	public void onError( String error ) {
		
		System.err.println( error );
	}

	/**
	 * Handle websocket String message transfer processing event with data from exberry;
	 */
	@Override
	public void onMessage( String message ) {
		
		System.out.println( message );
		
		// TODO: Deserialize here !!
	}

	/**
	 * Handle websocket ByteBuffer message transfer processing event with data from exberry;
	 */
	@Override
	public void onMessage( ByteBuffer message ) {
		
	}
	
}  // ExberrySession
