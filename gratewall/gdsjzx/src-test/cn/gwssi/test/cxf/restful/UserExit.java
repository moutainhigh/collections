package cn.gwssi.test.cxf.restful;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path(value="/userexit")
public interface  UserExit {
	
	@GET
	@Path("/exitUser")
	public boolean exitUser();
}
