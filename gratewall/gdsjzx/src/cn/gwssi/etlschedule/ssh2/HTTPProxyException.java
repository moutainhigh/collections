
package cn.gwssi.etlschedule.ssh2;

import java.io.IOException;

/**
 * May be thrown upon connect() if a HTTP proxy is being used.
 * 
 * @see Connection#connect()
 * @see Connection#setProxyData(ProxyData)
 * 
 * @author Christian Plattner, plattner@inf.ethz.ch
 * @version $Id: HTTPProxyException.java,v 1.2 2006/08/02 12:05:00 cplattne Exp $
 */

public class HTTPProxyException extends IOException
{
	private static final long serialVersionUID = 2241537397104426186L;

	public final String httpResponse;
	public final int httpErrorCode;

	public HTTPProxyException(String httpResponse, int httpErrorCode)
	{
		super("HTTP Proxy Error (" + httpErrorCode + " " + httpResponse + ")");
		this.httpResponse = httpResponse;
		this.httpErrorCode = httpErrorCode;
	}
}
