package com.gwssi.ad;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;


@WebService
public class YeAdServerTest {

	
	@WebMethod
	public String sayHello(String name) {
		AdDao ad =new AdDao();
		Person p=ad.searchOnePersion("changruan");
		System.out.println(p.getDistinguishedName());
		return p.getDistinguishedName();
	}
	
	
	
	public static void main(String[] args) {
		String address ="http://localhost:8086/yeWeb/webservice";
		
		Endpoint.publish(address, new YeAdServerTest());
		System.out.println("webservice成功发布");
	}
}
