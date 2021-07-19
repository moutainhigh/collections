package com.gwssi.test;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.gwssi.cxf.validatecode.inter.ValidateCodeInter;

public class Client {
	public static void main(String[] args) throws Exception {
		
	
/*	JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();  
    org.apache.cxf.endpoint.Client client = clientFactory.createClient("BankInterface/services/EntInfoBankService?wsdl");  
    Object[] result = null;
	try {
		result = client.invoke("getHouseInfo", "KEVIN");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  
    System.out.println(result[0]);*/
		
		 JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();  
	        bean.setServiceClass(ValidateCodeInter.class);  
	        bean.setAddress("http://10.1.32.175:9090/house_code/services/ValidateCodeService");  
	        ValidateCodeInter helloWorldService = (ValidateCodeInter)bean.create();  
	        String result = helloWorldService.getHouseInfo("cxftest") ;
	        System.out.println(result);
}
	}
