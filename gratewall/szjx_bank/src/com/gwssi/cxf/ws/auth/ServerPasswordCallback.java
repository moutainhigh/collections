package com.gwssi.cxf.ws.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.slf4j.Logger;

public class ServerPasswordCallback implements CallbackHandler {
	 private Logger logger = org.slf4j.LoggerFactory.getLogger(ServerPasswordCallback.class);  

	 private static final Map<String, String> userMap = new HashMap<String, String>();  
	   
	    static {  
	        userMap.put("client", "clientpass");  
	        userMap.put("server", "serverpass");  
	    }  
	   
	    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {  
	        WSPasswordCallback pc  = (WSPasswordCallback) callbacks[0]; 
	        // 标识符  
	        String identifier = pc.getIdentifer();
	     // 此处获取到的password为null，但是并不代表服务端没有拿到该属性。  
	        String password = pc.getPassword();  
	        logger.info("identifier:" + identifier);  
	        logger.info("password:" + password);
	        
	        if (identifier != null && identifier.equals("admin")) {  
	            /**  
	             * 此处应该这样做： 
	                 * 1. 查询数据库，得到数据库中该用户名对应密码 
	                 * 2. 设置密码，wss4j会自动将你设置的密码 与客户端传递的密码进行匹配 
	                 * 3. 如果相同，则放行，否则返回权限不足信息  
	             *  
	             */  
	            pc.setPassword("1234@abcd");  
	        }else{  
	            logger.info("未授权的用户");  
	        }  
	    }  
}
