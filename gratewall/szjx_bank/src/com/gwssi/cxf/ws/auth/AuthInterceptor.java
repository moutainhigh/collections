package com.gwssi.cxf.ws.auth;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.SOAPException;

import org.apache.cxf.service.Service; 
import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.frontend.MethodDispatcher;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.XMLUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.*;

//通过PhaseInterceptor，指定拦截器在那个阶段起作用
public class AuthInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
	
	@Autowired
	private UserInfoService userInfo;
	
	
	private static final Logger logger = Logger.getLogger(AuthInterceptor.class);
	public static final String xml_namespaceUR_att = "http://gd.chinamobile.com//authentication";  
	public static final String xml_header_el = "authHeader";  
	public static final String xml_authentication_el = "auth:authentication";  
	public static final String xml_systemID_el = "systemID";  
    public static final String xml_userID_el = "userID";  
    public static final String xml_password_el = "password";  
	public AuthInterceptor() {
		//Super表示显示调用父类有参数的构造器。
		//显示调用父类构造器之，程序不会隐示调用父类无参数的构造器。
		super(Phase.PRE_INVOKE);// Phase.PRE_INVOKE//在调用之前拦截Soap消息
	}
	// 处理被拦截到的soap消息,获取用户名密码、验证用户权限
	//实现自己的拦截器时，需要实现handleMessage方法
	//handleMessage方法中的形参就是被拦截到的Soap消息
	//一旦程序获取Soap消息，剩下的事情就是可以解析或修改Soap消息
	@Override
	public void handleMessage(SoapMessage msg) throws Fault {
		logger.info("==================SoapMessage =" + msg);  
	    // 获取SOAP消息的全部头 
		List<Header> headers = msg.getHeaders();
		if (null == headers || headers.size() < 1) {  
            throw new Fault(new SOAPException("SOAP消息头格式不对哦！"));  
        }  
		 logger.info("headers.size()=" + headers.size()); 
		for (Header header : headers) {  
	        SoapHeader soapHeader = (SoapHeader) header;  
	        // 取出SOAP的Header元素  
	        Element element = (Element) soapHeader.getObject(); 
	        logger.info("ELEMENT =" + element.toString());  
            XMLUtils.printDOM(element); 
	        NodeList systemIdNodes = element.getElementsByTagName(xml_systemID_el);
			NodeList userIdNodes= element.getElementsByTagName(xml_userID_el);
			NodeList pwdNodes = element.getElementsByTagName(xml_password_el);
			logger.info("############ 打印帐号信息 ##############");  
			logger.info(systemIdNodes.item(0) + "="+ systemIdNodes.item(0).getTextContent()); 
            logger.info(userIdNodes.item(0) + "=" + userIdNodes.item(0).getTextContent());  
            logger.info(pwdNodes.item(0) + "="+ pwdNodes.item(0).getTextContent()); 
            logger.info("############————————##############");  
			if (systemIdNodes.getLength() != 1) {
				throw new Fault(new IllegalArgumentException("系统ID的格式不对！"));
			}
			if (userIdNodes.getLength() != 1) {
				throw new Fault(new IllegalArgumentException("用户名的格式不对！"));
			}
			if (pwdNodes.getLength() != 1) {
				throw new Fault(new IllegalArgumentException("密码的格式不对！"));
			}
			String systemID=systemIdNodes.item(0).getTextContent();
			String userId=userIdNodes.item(0).getTextContent();
			String password=pwdNodes.item(0).getTextContent();
			/**  
             * 此处应该这样做： 
                 * 1. 查询数据库，得到数据库中该用户名对应密码 
                 * 2. 与客户端传递的密码进行匹配 
                 * 3. 如果相同，则放行，否则返回权限不足信息  
             *  
             */  
		  //  UserInfoService userInfo= new UserInfoService();
		    List  lists = userInfo.getUserInfo(systemID,userId,password);
			
			if(lists!=null&&lists.size()>0){
				
				
				logger.info("############ 登陆成功 ##############");  
			}else{
				logger.info("############ 系统ID或用户名或者密码错误! ##############");  
				throw new Fault(new IllegalStateException("系统ID或用户名或者密码错误!"));
			}
		 } 
    }  
		
	}

