package com.gwssi.cxf.ws.impl;

import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.gwssi.cxf.ws.HelloWorldInterface;

/*@Service(value = "helloWorldImpl")*/
@WebService(endpointInterface="com.gwssi.cxf.ws.HelloWorldInterface")
/*@SOAPBinding(style = Style.RPC)*/
public class HelloWorldImpl implements HelloWorldInterface {

	@Override
	public String sayHi(@WebParam(name = "name")String name) {
		return "你好，来自webservice服务端："+new Date()+","+name;
	}

	@Override
	public String sayHiTwo(String name, String time) {
		// TODO Auto-generated method stub
		return name+time;
	}

}
