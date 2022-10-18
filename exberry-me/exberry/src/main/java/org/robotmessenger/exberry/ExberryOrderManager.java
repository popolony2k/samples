package org.robotmessenger.exberry;


import java.net.URISyntaxException;

import org.robotmessenger.exberry.dto.request.ExecutionReportsRequest;
import org.robotmessenger.exberry.dto.request.MassOrderStatusRequest;
import org.robotmessenger.exberry.dto.request.OrderBookDepthRequest;
import org.robotmessenger.exberry.dto.request.OrderBookStateRequest;
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
	
	OrderBookStateRequest   orderBookState   = new OrderBookStateRequest();
	OrderBookDepthRequest   orderBookDepth   = new OrderBookDepthRequest();
	MassOrderStatusRequest  massOrderStatus  = new MassOrderStatusRequest();
	ExecutionReportsRequest executionReports = new ExecutionReportsRequest();
	TradesRequest           trades           = new TradesRequest();
	

	/**
	 * Constructor. Initialize all class data.
	 * @param strURI The Exberry environment URI to connect;
	 * @throws URISyntaxException 
	 */
	public ExberryOrderManager( String strURI, String apiKey, String signature ) throws URISyntaxException {

		super( strURI, apiKey, signature );
	}
	
	/**
	 * Send an order book state request to Exberry server.
	 *
	 * @return true if success otherwise false;
	 */
	public boolean requestOrderBookState()  {
		
		return sendRequest( orderBookState );
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
		
		orderBookDepth.d.trackingNumber = trackingNumber;
		
		return sendRequest( orderBookDepth );
	}
	
	/**
	 * Any participant can use the massOrderStatus API to retrieve the current 
	 * status of all its own active orders.
	 * 
	 * @return true if success otherwise false;
	 */
	public boolean requestMassOrderStatus()  {
		
		return sendRequest( massOrderStatus );
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
		
		executionReports.d.trackingNumber = trackingNumber;
		
		return sendRequest( executionReports );
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
		
		trades.d.trackingNumber = trackingNumber;
		
		return sendRequest( trades );
	}

}  // ExberryOrderManager
