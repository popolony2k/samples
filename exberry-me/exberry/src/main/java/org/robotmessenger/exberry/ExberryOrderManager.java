package org.robotmessenger.exberry;


import java.net.URISyntaxException;

import org.robotmessenger.exberry.dto.request.CancelOrderRequest;
import org.robotmessenger.exberry.dto.request.ExecutionReportsRequest;
import org.robotmessenger.exberry.dto.request.MassCancelRequest;
import org.robotmessenger.exberry.dto.request.MassOrderStatusRequest;
import org.robotmessenger.exberry.dto.request.ModifyOrderRequest;
import org.robotmessenger.exberry.dto.request.OrderBookDepthRequest;
import org.robotmessenger.exberry.dto.request.OrderBookStateRequest;
import org.robotmessenger.exberry.dto.request.PlaceOrderRequest;
import org.robotmessenger.exberry.dto.request.ReplaceOrderRequest;
import org.robotmessenger.exberry.dto.request.TradesRequest;

/**
 * Defines all exberry session available operations.
 * 
 * @author popolony2k
 *
 */
public class ExberryOrderManager extends ExberrySession  {
		
	/**
	 * Staging exberry sandbox environment URI
	 */
	public static final String EXBERRY_STAGING_URI = "wss://sandbox-shared.staging.exberry-uat.io";
		

	/**
	 * Constructor. Initialize all class data.
	 * @param strURI The Exberry environment URI to connect;
	 * @param secreKey The user session secret key used to decrypt data;
	 * @throws URISyntaxException 
	 */
	public ExberryOrderManager( String strURI, String apiKey, String secret ) throws URISyntaxException {

		super( strURI, apiKey, secret );
	}
	
	/**
	 * Send an order book state request to Exberry server.
	 *
	 * @return true if success otherwise false;
	 */
	public boolean requestOrderBookState()  {
		
		OrderBookStateRequest   request = new OrderBookStateRequest();
		
		return sendRequest( request );
	}
	
	/**
	 * Send an order book depth request to Exberry server.
	 * @param tranckingNumber Determines the starting point of stream.
	 * When set to 0 - Stream will start from first event ever
	 * When empty - Stream will start from the next upcoming event
	 * When set to specific trackingNumber- Stream will start from the next 
	 * event after the given trackingNumber
	 *
	 * @return true if success otherwise false;
	 */
	public boolean requestOrderBookDepth( Long trackingNumber )  {
		
		OrderBookDepthRequest   request = new OrderBookDepthRequest();

		request.d.trackingNumber = trackingNumber;
		
		return sendRequest( request );
	}
	
	/**
	 * Any participant can use the massOrderStatus API to retrieve the current 
	 * status of all its own active orders.
	 * 
	 * @return true if success otherwise false;
	 */
	public boolean requestMassOrderStatus()  {
		
		MassOrderStatusRequest  request = new MassOrderStatusRequest();
		
		return sendRequest( request );
	}
	
	/**
	 * Send an execution reports request to Exberry server.
	 * @param tranckingNumber Determines the starting point of stream.
	 * When set to 0 - Stream will start from first event ever
	 * When empty - Stream will start from the next upcoming event
	 * When set to specific trackingNumber- Stream will start from the next 
	 * event after the given trackingNumber
	 *
	 * @return true if success otherwise false;
	 */
	public boolean requestExecutionReports( Long trackingNumber )  {
		
		ExecutionReportsRequest request = new ExecutionReportsRequest();
		
		request.d.trackingNumber = trackingNumber;
		
		return sendRequest( request );
	}
	
	/**
	 * Send a trades request to Exberry server.
	 * @param tranckingNumber Determines the starting point of stream.
	 * When set to 0 - Stream will start from first event ever
	 * When empty - Stream will start from the next upcoming event
	 * When set to specific trackingNumber- Stream will start from the next 
	 * event after the given trackingNumber
	 *
	 * @return true if success otherwise false;
	 */
	public boolean requestTrades( Long trackingNumber )  {
		
		TradesRequest           request = new TradesRequest();
		
		request.d.trackingNumber = trackingNumber;
		
		return sendRequest( request );
	}
	
	/**
	 * The placeOrder API lets you place a new order into exchange.
	 * If you send a valid order, you should receive a response with 
	 * "Pending" status, this means that order was validated and accepted. 
	 * The response contains the exchange orderId which should be stored and 
	 * used for later status changes, notified via the orderBookDepth stream.
	 * Non-valid order will be responded with error message.
	 * 
	 * @param order Order object to send on request;
	 * 
	 * @return true if success otherwise false;
	 */
	public boolean placeOrder( PlaceOrderRequest.PlaceOrder order )  {
		
		PlaceOrderRequest    request = new PlaceOrderRequest();
		
		request.d = order;
		
		return sendRequest( request );
	}
	
	/**
	 * The cancelOrder API is used to request that an order be cancelled.
	 * If you send a valid order to cancel, you should receive a response that 
	 * confirms that order was cancelled. This means that remaining open quantity 
	 * of the order was cancelled.
	 * Non-valid cancel order will be responded with the error message.
	 * 
	 * @param order Order object to send on request;
	 * 
	 * @return true if success otherwise false;
	 */
	public boolean cancelOrder( CancelOrderRequest.CancelOrder order )  {
		
		CancelOrderRequest    request = new CancelOrderRequest();
		
		request.d = order;
		
		return sendRequest( request );
	}
	
	/**
	 * The massCancel API is used to cancel all the active order for specific 
	 * instrument for specific market participant.
	 * If you send a valid request, you should receive a response that confirms 
	 * the number of orders that were cancelled.
	 * Non-valid cancel order will be responded with the error message.
	 * 
	 * @param instrument The instrument identifier;
	 * 
	 * @return true if success otherwise false;
	 */
	public boolean massCancel( String instrument )  {

		MassCancelRequest    request = new MassCancelRequest();
		
		request.d.instrument = instrument;
		
		return sendRequest( request );
	}
	
	/**
	 * The modifyOrder API is used to reduce the order quantity without 
	 * losing the time priority.
	 * If you send a valid order to modify, you should receive a response
	 * that confirms that order was modified. This means that remaining open 
	 * quantity of the order was reduced.
	 * Non-valid modify order requests will be responded with the error message.
	 * 
	 * @param order Order object to send on request;
	 * 
	 * @return true if success otherwise false;
	 */
	public boolean modifyOrder( ModifyOrderRequest.ModifyOrder order )  {
		
		ModifyOrderRequest    request = new ModifyOrderRequest();
		
		request.d = order;
		
		return sendRequest( request );
	}
	
	/**
	 * The replaceOrder API is used to change a few of the order parameters 
	 * in a single command for order which is resting on the book, this action 
	 * will lose the time priority as it going to cancel the previous order and
	 * place a new one.
	 * If you send a valid order to be replaced, you should receive a response 
	 * that confirms that order was replaced.
	 * Non-valid replace order requests will be responded with the error message.
	 * 
	 * @param order Order object to send on request;
	 * 
	 * @return true if success otherwise false;
	 */
	public boolean replaceOrder( ReplaceOrderRequest.ReplaceOrder order )  {
		
		ReplaceOrderRequest    request = new ReplaceOrderRequest();
		
		request.d = order;
		
		return sendRequest( request );
	}

}  // ExberryOrderManager
