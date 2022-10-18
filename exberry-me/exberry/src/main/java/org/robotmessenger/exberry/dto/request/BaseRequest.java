package org.robotmessenger.exberry.dto.request;

import java.io.Serializable;

/**
 * Defines a base Exberry request data transfer object
 * for requesting data do Exberry server.
 * @author popolony2k
 *
 */
public class BaseRequest implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
	public String   q;
    public int      sid;
    
}  // RequestBase
