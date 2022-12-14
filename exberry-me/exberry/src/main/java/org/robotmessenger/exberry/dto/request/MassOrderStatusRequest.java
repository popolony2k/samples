package org.robotmessenger.exberry.dto.request;


/**
 * Any participant can use the massOrderStatus API to retrieve the current 
 * status of all its own active orders.
 * 
 * @author popolony2k
 *
 */
public class MassOrderStatusRequest extends BaseRequest  {
	
	private static final long serialVersionUID = 1L;
	private static final String __REQUEST = "v1/exchange.market/massOrderStatus";
	
	public MassOrderStatus   d;

	{
		d   = new MassOrderStatus();
		q   = __REQUEST;
		sid = 102;
	}

	/**
	 * Mass order status request information.
	 * @author popolony2k
	 *
	 */
	public class MassOrderStatus  {
		
	}
	
}  // MassOrderStatusRequest

