package cn.gwssi.test.cxf;

import javax.jws.WebMethod;
import javax.jws.WebParam;  
import javax.jws.WebService;  
import javax.jws.soap.SOAPBinding;  
import javax.jws.soap.SOAPBinding.Style;  

@WebService  
public interface IService {
	@WebMethod
	public void sayHello(@WebParam(name = "name") String name);
}
