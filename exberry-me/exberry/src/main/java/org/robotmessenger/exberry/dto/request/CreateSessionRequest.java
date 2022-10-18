package org.robotmessenger.exberry.dto.request;


/**
 * Define a create exberry session data transfer object for authentication at 
 * Exberry server.
 * 
 * Details at https://docs.exberry.io/authentication-api
 * 
 * @author popolony2k
 *
 */
public class CreateSessionRequest extends BaseRequest  {
	
	private static final long serialVersionUID = 1L;
	private static final String __REQUEST = "exchange.market/createSession";
	
	public Auth   d;

	{
		d   = new Auth();
		q   = __REQUEST;
		sid = 1;
	}

	/**
	 * Authentication information.
	 * @author popolony2k
	 *
	 */
	public class Auth  {

		public String   apiKey;
		public long     timestamp;
		public String   signature;
	}
	
}  // Session

