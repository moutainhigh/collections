package cn.gwssi.test.cxf.restful;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path(value="/userexit")  
public class UserExitImpl implements UserExit {  
	@GET  
    @Path("/exitUser")  
    public boolean exitUser() {  
        System.out.println("lllll");  
        return false;  
    }  
}
