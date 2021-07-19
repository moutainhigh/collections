package com.gwssi.common.rodimus.report.util;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringUtil implements ApplicationContextAware{
	
	public static Object getBean(String beanId,ServletContext sc){
		WebApplicationContext wc = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
		if(wc==null){
			return null;
		}
		Object ret = wc.getBean(beanId);
		return ret;
	}
	
	/**
	 * @param beanId
	 * @return
	 */
	public static Object getBean(String beanId){
		if(applicationContext==null){
			throw new RuntimeException("Cannot get ApplicationContext instance.");
		}
		Object ret = applicationContext.getBean(beanId);
		return ret;
	}
	
	public void setApplicationContext(ApplicationContext c)
			throws BeansException {
		SpringUtil.applicationContext = c;
	}
	
	private static ApplicationContext applicationContext; 
}
