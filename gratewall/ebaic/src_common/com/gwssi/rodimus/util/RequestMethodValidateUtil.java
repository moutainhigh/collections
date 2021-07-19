package com.gwssi.rodimus.util;

import javax.servlet.http.HttpServletRequest;

import com.gwssi.rodimus.exception.RodimusException;

public class RequestMethodValidateUtil {
	
	public static void validate(HttpServletRequest request){
		if(request==null){
			throw new RodimusException("request is null.");
		}
		String method = request.getMethod();
		if(StringUtil.isBlank(method)){
			throw new RodimusException("request method is null.");
		}
		method = method.toUpperCase();
		if(!"GET".equals(method) && !"POST".equals(method)){
			throw new RodimusException("Only 'get' and 'post' method supported. '"+method+"' is not supported.");
		}
	}
}
