package com.gwssi.rodimus.util;

import javax.servlet.http.HttpServletRequest;

import com.gwssi.optimus.core.common.ThreadLocalManager;
import com.gwssi.optimus.core.web.event.OptimusRequest;
import com.gwssi.rodimus.exception.RodimusException;

/**
 * 
 * @author liuhailong
 */
public class RequestUtil {

	/**
	 * 
	 * @return
	 */
	public static OptimusRequest getOptimusRequest(){
		OptimusRequest ret = (OptimusRequest) ThreadLocalManager.get(ThreadLocalManager.OPTIMUS_REQUEST);
		if(ret==null){
			throw new RodimusException("未从ThreadLocal中获取到Request对象。");
		}
		return ret;
	}
	/**
	 * 
	 * @return
	 */
	public static HttpServletRequest getHttpRequest(){
		HttpServletRequest ret = (HttpServletRequest) ThreadLocalManager.get(ThreadLocalManager.RAW_HTTP_REQUEST);
		return ret;
	}
}
