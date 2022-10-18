package org.robotmessenger.exberry.dto.request;


public class TradesRequest extends BaseRequest  {
	
	private static final long serialVersionUID = 1L;
	private static final String __REQUEST = "v1/exchange.market/trades";
	
	public static final Trades NEXT_UPCOMING_EVENT = null;
	
	public Trades   d;

	{
		d   = new Trades();
		q   = __REQUEST;
		sid = 104;
	}

	/**
	 * Trades request information.
	 * @author popolony2k
	 *
	 */
	public class Trades  {
		
		public Long  trackingNumber;
	}
	
}  // TradesRequest

