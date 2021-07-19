package com.gwssi.cxf.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/*@Service(value = "helloWorld")*/
@WebService
/*@SOAPBinding(style = Style.RPC)*/
public interface HelloWorldInterface {
	//基本数据类型
	@WebResult(name = "String")  
	public String sayHi(@WebParam(name = "name") String name);
	
	@WebResult(name = "String")  
	public String sayHiTwo(@WebParam(name = "name") String name,@WebParam(name = "time") String time);

}
