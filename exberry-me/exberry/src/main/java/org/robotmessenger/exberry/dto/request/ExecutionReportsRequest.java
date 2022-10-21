package org.robotmessenger.exberry.dto.request;


/**
 * Any participant can use the executionReportsAPI to subscribe to its 
 * own orders and trades events.
 * 
 * @author popolony2k
 *
 */
public class ExecutionReportsRequest extends BaseRequest  {
	
	private static final long serialVersionUID = 1L;
	private static final String __REQUEST = "v1/exchange.market/executionReports";
	
	public static final ExecutionReports NEXT_UPCOMING_EVENT = null;
	
	public ExecutionReports   d;

	{
		d   = new ExecutionReports();
		q   = __REQUEST;
		sid = 103;
	}

	/**
	 * Execution report request information.
	 * @author popolony2k
	 *
	 */
	public class ExecutionReports  {
		
		public Long  trackingNumber;
	}
	
}  // ExecutionReportsRequest

