package cn.gwssi.test.cxf;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface HelloWorldDao {
	@WebMethod
    public void sayHello(@WebParam(name = "name") String name);
}
