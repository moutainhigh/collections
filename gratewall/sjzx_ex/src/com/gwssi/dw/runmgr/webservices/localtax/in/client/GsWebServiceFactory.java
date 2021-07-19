package com.gwssi.dw.runmgr.webservices.localtax.in.client;

import javax.xml.rpc.ServiceException;

public class GsWebServiceFactory
{
	private static GsWebServicePort_PortType service = null; 
	
	public synchronized static GsWebServicePort_PortType createGsWebService() throws ServiceException{
		if (service == null){
			GsWebServiceLocator gsService = new GsWebServiceLocator();
			gsService.setGsWebServicePortEndpointAddress("http://172.24.27.26:8011//BJTAX-PS-OUTER/GONGSHANG/PS/GSSjjhPS");
			service = gsService.getGsWebServicePort();
			((GsWebServicePortStub)service).setUsername("GongShang");
			((GsWebServicePortStub)service).setPassword("11111111");
		}
		return service;
	}
}
