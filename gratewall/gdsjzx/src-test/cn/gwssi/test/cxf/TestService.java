package cn.gwssi.test.cxf;

import java.util.Date;  
import javax.jws.WebParam;  
import javax.jws.WebService;  
import javax.jws.soap.SOAPBinding;  
import javax.jws.soap.SOAPBinding.Style;  
   
/** 
 * <b>function:</b> WebService传递复杂对象，如JavaBean、Array、List、Map等 
 */  
@WebService(endpointInterface="cn.gwssi.cxf.IService")
//@SOAPBinding(style = Style.RPC)  
public class TestService implements IService {

	@Override
	public void sayHello(String name) {
		System.out.println("hello," + name);
	}  
    
}
