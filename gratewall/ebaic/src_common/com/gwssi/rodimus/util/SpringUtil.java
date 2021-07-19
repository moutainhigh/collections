package com.gwssi.rodimus.util;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.gwssi.rodimus.exception.RodimusException;

/**
 * 
 * @author liuhailong
 */
public class SpringUtil {

	private static WebApplicationContext wc = null;
	
	static{
		wc = ContextLoader.getCurrentWebApplicationContext();
	}
	/**
	 * 
	 * @param clz
	 * @return
	 */
	public static <T> T getBean(Class<T> clz){
		if(wc==null){
			wc = ContextLoader.getCurrentWebApplicationContext();
			if(wc==null){
				throw new RodimusException("获取Bean实例失败。");
			}
		}
		T ret = wc.getBean(clz);
		return ret;
	}
	/**
	 * 
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName){
		if(wc==null){
			wc = ContextLoader.getCurrentWebApplicationContext();
			if(wc==null){
				throw new RodimusException("获取Bean实例失败。");
			}
		}
		Object ret = wc.getBean(beanName);
		return ret;
	}
}
