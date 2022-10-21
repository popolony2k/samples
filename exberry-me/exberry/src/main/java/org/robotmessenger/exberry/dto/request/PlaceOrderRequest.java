package org.robotmessenger.exberry.dto.request;

import java.util.Date;
import java.util.Vector;

import org.robotmessenger.exberry.dto.Enums.AccountType;
import org.robotmessenger.exberry.dto.Enums.OrderType;
import org.robotmessenger.exberry.dto.Enums.Side;
import org.robotmessenger.exberry.dto.Enums.TimeInForce;


/**
 * The placeOrder API lets you place a new order into exchange.
 * 
 * @author popolony2k
 *
 */
public class PlaceOrderRequest extends BaseRequest  {
	
	private static final long serialVersionUID = 1L;
	private static final String __REQUEST = "v1/exchange.market/placeOrder";
	
		
	public PlaceOrder   d;

	{
		d   = new PlaceOrder();
		q   = __REQUEST;
		sid = 105;
	}
	
	
	/**
	 * Return an instance of PlaceOrder object;
	 * @return a PlaceOrder allocated object;
	 */
	public static PlaceOrder newInstance()  {

		return new PlaceOrderRequest().new PlaceOrder();
	}

	/**
	 * Trades request information.
	 * @author popolony2k
	 *
	 */
	public class PlaceOrder  {
		public long           mpOrderId;
		public OrderType      orderType;
		public Side           side;
		public String         instrument;
		public double         quantity;
		public Double         price;
		public TimeInForce    timeInForce;
		public Date           expiryDate;
		public String         userId;
		public Integer        accountId;
		public AccountType    accountType;
		public Vector<Party>  parties;
	}
	
	/**
	 * Party entity
	 * @author popolony2k
	 *
	 */
	public class Party  {
		public String      id;
		public char        source;
		public int         role;
	}	
}  // PlaceOrderRequest

