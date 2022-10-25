package org.robotmessenger.exberry;


/**
 * Exberry session event handling.
 * @author popolony2k
 *
 */
public interface IExberrySessionListener  {
	
	/**
	 * Must be implemented to handle session opening with exberry;
	 * 
	 * @param session The session owner of this event;
	 */
	public void onOpen( ExberrySession session );
		
	/**
	 * Must be implemented to handle session closing with exberry;
	 * 
	 * @param session The session owner of this event;
	 * @param reasonCode Close reason code;
	 */
	public void onClose( ExberrySession session, int reasonCode );

	/**
	 * Must be implemented to handle exberry session errors;
	 * 
	 * @param session The session owner of this event;
	 * @param error The error message;
	 */
	public void onError( ExberrySession session, String error );

	/**
	 * Must be implemented to handle exberry session messages;
	 * 
	 * @param session The session owner of this event;
	 * @param message The exbrry server message received;
	 */
	public void onMessage( ExberrySession session, String message );

} // IEXberrySessionListener