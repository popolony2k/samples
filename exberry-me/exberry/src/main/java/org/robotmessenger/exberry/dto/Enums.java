package org.robotmessenger.exberry.dto;


/**
 * Enumerators used by all DTOs.
 *  
 * @author popolony2k
 *
 */
public class Enums  {
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
}  // Enums