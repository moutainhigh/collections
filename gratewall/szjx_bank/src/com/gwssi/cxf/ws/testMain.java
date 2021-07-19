package com.gwssi.cxf.ws;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.ws.Endpoint;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.phase.Phase;

import com.gwssi.cxf.ws.auth.AuthInterceptor;
import com.gwssi.cxf.ws.impl.HelloWorldImpl;

public class testMain {

	public static void main(String[] args) throws IOException {
		HelloWorldInterface hw = new HelloWorldImpl();
	/*	//调用Endpoint的publish方法发布Web Service
		EndpointImpl ep = (EndpointImpl)Endpoint.publish("http://10.1.32.188:4337/HelloWorld", hw);
		//ws服务端添加拦截器,输出到文件
		//添加In拦截器
		ep.getInInterceptors().add(new LoggingInInterceptor(new PrintWriter(new FileWriter("in.txt"))));
		//添加Out拦截器
		ep.getInInterceptors().add(new LoggingOutInterceptor(new PrintWriter(new FileWriter("out.txt"))));
		
		//输出到控制台
		ep.getInInterceptors().add(new LoggingInInterceptor());
		ep.getOutInterceptors().add(new LoggingOutInterceptor());*/
		JaxWsServerFactoryBean jwsFactory = new JaxWsServerFactoryBean();
        jwsFactory.setAddress("http://10.1.32.188:4337/hello");   //指定WebService的发布地址
        jwsFactory.setServiceClass(HelloWorldInterface.class);//WebService对应的类型
        jwsFactory.setServiceBean(hw);//WebService对应的实现对象
     /* //输出到控制台
        jwsFactory.getInInterceptors().add(new LoggingInInterceptor());
        jwsFactory.getOutInterceptors().add(new LoggingOutInterceptor());*/
        
      //添加自定义拦截器
        jwsFactory.getInInterceptors().add(new AuthInterceptor());

        jwsFactory.create();
      
        

		
		 System.out.println("publich success!");

	}

}
