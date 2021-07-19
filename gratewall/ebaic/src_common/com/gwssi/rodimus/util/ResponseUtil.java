package com.gwssi.rodimus.util;

import javax.servlet.http.HttpServletResponse;

import com.gwssi.optimus.core.common.ThreadLocalManager;
import com.gwssi.optimus.core.web.event.OptimusResponse;

public class ResponseUtil {
	/**
	 * 
	 * @return
	 */
	public static OptimusResponse getOptimusResponse(){
		OptimusResponse ret = (OptimusResponse) ThreadLocalManager.get(ThreadLocalManager.OPTIMUS_RESPONSE);
		return ret;
	}
	/**
	 * 
	 * @return
	 */
	public static HttpServletResponse getHttpResponse(){
		HttpServletResponse ret = (HttpServletResponse) ThreadLocalManager.get(ThreadLocalManager.RAW_HTTP_RESPONSE);
		return ret;
	}
}
