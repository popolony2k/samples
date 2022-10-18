package org.robotmessenger.exberry.dto.request;


public class OrderBookDepthRequest extends BaseRequest  {
	
	private static final long serialVersionUID = 1L;
	private static final String __REQUEST = "v2/exchange.market/orderBookDepth";
	
	public static final OrderBookDepth NEXT_UPCOMING_EVENT = null;

	
	public OrderBookDepth   d;

	{
		d   = new OrderBookDepth();
		q   = __REQUEST;
		sid = 101;
	}

	/**
	 * Order book depth request information.
	 * @author popolony2k
	 *
	 */
	public class OrderBookDepth  {
		
		public Long  trackingNumber;
	}
	
}  // OrderBookDepthRequest

