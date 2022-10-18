package org.robotmessenger.exberry;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

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
	 * @throws URISyntaxException 
	 */
	public ExberrySession( String strURI, String apiKey, String signature ) throws URISyntaxException {

		try {
			URI uri = new URI( strURI );
			connection = new Connection( uri, this );
			session.d.signature = signature;
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
		
		//TODO: Ajustar depois que tivermos uma chave oficial
		//session.d.timestamp = System.currentTimeMillis();
		session.d.signature = "06d10d571e113c7803b4072e6485b2639db8983e806546980333c878ec237196";
		session.d.timestamp = 1666040407406L;
		
		return sendRequest( session );
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
