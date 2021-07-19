package com.gwssi.cxf.ws.auth;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.frontend.MethodDispatcher;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.slf4j.Logger;

public class ErrorHandlerInterceptor extends AbstractSoapInterceptor {
	 private Logger logger = org.slf4j.LoggerFactory.getLogger(ErrorHandlerInterceptor.class);
	
	 public ErrorHandlerInterceptor() {  
	        super(Phase.MARSHAL);  
	    }  

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		 // 错误原因  
        Fault fault = (Fault) message.getContent(Exception.class);  
        // 错误信息  
        String errorMessage = null;  
        Throwable cause = null;  
        if (fault != null) {  
            errorMessage = fault.getMessage();  
            cause = fault.getCause();  
        }  
        
        System.out.println("错误原因 :"+cause);
        System.out.println("错误信息   :"+errorMessage);
        
        Exchange exchange = message.getExchange();  
        // wsdl描述  
        String servicePath = null;  
        // url与uri  
        String url = null;  
        String uri = null;  
        // 客户端ip  
        String clientIp = null;  
        // 用户名  
        String username = null;  
        // 密码  
        String psw = null;  
        // 服务中的方法  
        String methodName = null;  
        // 参数名  
        Object[] paramNames = null;  
        // 参数值  
        Object[] paramValues = null; 
        
        if (exchange != null) { 
        	 Object object = exchange.get("javax.xml.ws.wsdl.description");  
             if (object != null) {  
                 servicePath = object.toString();  
             }  
             System.out.println("servicePath="+servicePath); 
             Message inMessage = exchange.getInMessage();  
            if (inMessage != null) {  
                HttpServletRequest req = (HttpServletRequest) inMessage.get("HTTP.REQUEST");  
               clientIp = IPUtil.getIpPropAddr(req);  
                System.out.println(clientIp);
                uri=  req.getRequestURI();
                req.getRequestURL();
              //System.out.println(uri);
             // //System.out.println(req.getRequestURL());
              //System.out.println(inMessage);
            } 
        	
          //取出SOAP的Body元素
		  System.err.println("========Yf========");
		  System.out.println(message);
		  // 获取方法信息  
		  // Exchange exchange = message.getExchange();  
		  BindingOperationInfo bop = exchange.get(BindingOperationInfo.class);  
		  MethodDispatcher md = (MethodDispatcher) exchange.get(Service.class).get(MethodDispatcher.class.getName());  
		  Method method = md.getMethod(bop); 
		  System.out.println(method);  
		  System.out.println(method.getName());
		  System.out.println(method.getReturnType().getName()); 
		   
		  // 获取参数  
		  List<?> content = message.getContent(List.class);  
		  System.out.println(content); 
        }
  
		
	}

}
