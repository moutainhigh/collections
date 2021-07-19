package com.gwssi.application.webservice.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Service(value = "authority")
@WebService(targetNamespace = "http://www.gwssi.com.cn", serviceName = "services", portName = "auth")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class Authority extends SpringBeanAutowiringSupport {

	private static Logger logger = Logger.getLogger(Authority.class);

	@Autowired
	private AuthorityService authorityService;

	/**
	 * 
	 * @param name
	 * @return
	 */
	@WebMethod(exclude = false, operationName = "hello")
	@WebResult(name = "ret", partName = "ret")
	public String hello(@WebParam(name = "name", partName = "name") String name) { 
		logger.info("进入测试方法，webservice服务端成功发布！");
		return "Hello" + name + "!";
	}

	/**
	 * 
	 * @param xmlParam
	 * @return
	 */
	@WebMethod(exclude = false, operationName = "getAuth")
	@WebResult(name = "ret", partName = "ret")
	public String getRoleAndFunc(
			@WebParam(name = "xmlParam", partName = "xmlParam") String xmlParam) {
		logger.info("参数xml为：" + xmlParam);
		return "1";
	}
}
