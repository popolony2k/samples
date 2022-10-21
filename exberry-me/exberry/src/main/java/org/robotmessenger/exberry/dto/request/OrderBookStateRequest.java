package org.robotmessenger.exberry.dto.request;


/**
 * This stream provides close to real time snapshot of order book state 
 * for all traded instruments.
 * 
 * @author popolony2k
 *
 */
public class OrderBookStateRequest extends BaseRequest  {
	
	private static final long serialVersionUID = 1L;
	private static final String __REQUEST = "v2/exchange.market/orderBookState";
	
	public OrderBookState   d;

	{
		d   = new OrderBookState();
		q   = __REQUEST;
		sid = 100;
	}

	/**
	 * Order book state request information.
	 * @author popolony2k
	 *
	 */
	public class OrderBookState  {
	}
	
}  // OrderBookStateRequest

