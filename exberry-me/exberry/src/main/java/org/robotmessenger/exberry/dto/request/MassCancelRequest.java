package org.robotmessenger.exberry.dto.request;



/**
 * The massCancel API is used to cancel all the active order for specific 
 * instrument for specific market participant.
 * 
 * @author popolony2k
 *
 */
public class MassCancelRequest extends BaseRequest  {
	
	private static final long serialVersionUID = 1L;
	private static final String __REQUEST = "v1/exchange.market/massCancel";
	
		
	public MassCancel   d;

	{
		d   = new MassCancel();
		q   = __REQUEST;
		sid = 105;
	}
	
	
	/**
	 * Return an instance of PlaceOrder object;
	 * @return a PlaceOrder allocated object;
	 */
	public static MassCancel newInstace()  {

		return new MassCancelRequest().new MassCancel();
	}

	/**
	 * Trades request information.
	 * @author popolony2k
	 *
	 */
	public class MassCancel  {
		public String         instrument;
	}
}  // MassCancelRequest

