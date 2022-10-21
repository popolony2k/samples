package org.robotmessenger.exberry.dto.request;


/**
 * The modifyOrder API is used to reduce the order quantity 
 * without losing the time priority.
 *  
 * @author popolony2k
 *
 */
public class ModifyOrderRequest extends BaseRequest  {
	
	private static final long serialVersionUID = 1L;
	private static final String __REQUEST = "v1/exchange.market/modifyOrder";
	
		
	public ModifyOrder   d;

	{
		d   = new ModifyOrder();
		q   = __REQUEST;
		sid = 105;
	}
	
	
	/**
	 * Return an instance of PlaceOrder object;
	 * @return a PlaceOrder allocated object;
	 */
	public static ModifyOrder newInstance()  {

		return new ModifyOrderRequest().new ModifyOrder();
	}

	/**
	 * Modify order information.
	 * @author popolony2k
	 *
	 */
	public class ModifyOrder {
		public long           orderId;
		public String         instrument;
		public double         quantity;
	}
}  // ModifyOrderRequest

