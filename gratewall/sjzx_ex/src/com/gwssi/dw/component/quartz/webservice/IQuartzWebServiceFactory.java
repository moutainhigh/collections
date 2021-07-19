package com.gwssi.dw.component.quartz.webservice;

import javax.xml.rpc.ServiceException;

public class IQuartzWebServiceFactory
{
	private static IQuartzWebService service = null;
	public synchronized static IQuartzWebService createIQuartzWebService() throws ServiceException{
		if (service == null){
			IQuartzWebServiceService s = new IQuartzWebServiceServiceLocator();
			service = s.getQuartzWebService();
		}
		return service;
	}
}
