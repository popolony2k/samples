package org.robotmessenger.exberry.dto.request;



public class CancelOrderRequest extends BaseRequest  {
	
	private static final long serialVersionUID = 1L;
	private static final String __REQUEST = "v1/exchange.market/cancelOrder";
	
		
	public CancelOrder   d;

	{
		d   = new CancelOrder();
		q   = __REQUEST;
		sid = 105;
	}
	
	
	/**
	 * Return an instance of PlaceOrder object;
	 * @return a PlaceOrder allocated object;
	 */
	public static CancelOrder newInstace()  {

		return new CancelOrderRequest().new CancelOrder();
	}

	/**
	 * Trades request information.
	 * @author popolony2k
	 *
	 */
	public class CancelOrder  {
		public Long           orderId;
		public Long           mpOrderId;
		public String         instrument;
	}
}  // CancelOrderRequest

