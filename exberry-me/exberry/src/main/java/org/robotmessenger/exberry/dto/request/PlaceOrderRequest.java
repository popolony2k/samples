package org.robotmessenger.exberry.dto.request;

import java.util.Date;
import java.util.Vector;

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
	public static PlaceOrder newPlaceOrder()  {

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
	
	/**
	 * Available order types
	 * @author popolony2k
	 *
	 */
	public enum OrderType  {
		Limit,
		Market
	}
	
	/**
	 * Order side
	 * @author popolony2k
	 *
	 */
	public enum Side  {
		Buy,
		Sell
	}
	
	/**
	 * Available time in force parameters
	 * @author popolony2k
	 *
	 */
	public enum TimeInForce  {
		GTC, 
		GTD, 
		FOK, 
		IOC, 
		GAA
	}
	
	/**
	 * Account type.
	 * @author popolony2k
	 *
	 */
	public enum AccountType  {
		Client,
		House
	}
}  // TradesRequest

