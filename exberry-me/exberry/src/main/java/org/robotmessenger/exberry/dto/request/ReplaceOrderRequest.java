package org.robotmessenger.exberry.dto.request;

import java.util.Date;

import org.robotmessenger.exberry.dto.Enums.TimeInForce;

/**
 * The modifyOrder API is used to reduce the order quantity 
 * without losing the time priority.
 *  
 * @author popolony2k
 *
 */
public class ReplaceOrderRequest extends BaseRequest  {
	
	private static final long serialVersionUID = 1L;
	private static final String __REQUEST = "v1/exchange.market/replaceOrder";
	
		
	public ReplaceOrder   d;

	{
		d   = new ReplaceOrder();
		q   = __REQUEST;
		sid = 105;
	}
	
	
	/**
	 * Return an instance of PlaceOrder object;
	 * @return a PlaceOrder allocated object;
	 */
	public static ReplaceOrder newInstance()  {

		return new ReplaceOrderRequest().new ReplaceOrder();
	}

	/**
	 * Replace order information.
	 * @author popolony2k
	 *
	 */
	public class ReplaceOrder {
		public long           orderId;
		public String         instrument;
		public double         quantity;
		public double         price;
		public TimeInForce    timeInForce;
		Date                  expiryDate;
	}
}  // ReplaceOrderRequest

