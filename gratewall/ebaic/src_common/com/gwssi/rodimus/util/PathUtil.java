package com.gwssi.rodimus.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
/**
 * 本类得到用户当前访问项目的路径IP
 * @author liuhailong
 */
public class PathUtil {
	/**
	 * 得到访问者IP。
	 * @param request
	 * @return
	 */
	public static String getClientIP() {
		HttpServletRequest request = RequestUtil.getHttpRequest();
		String userIP=request.getHeader("X-Forwarded-For");
		if(StringUtil.isBlank(userIP)){
			userIP = request.getRemoteAddr();
		}
		return userIP;
	}
	
	/**
	 * 获取响应请求的服务器IP。
	 * 
	 * @param request
	 * @return 服务器IP。
	 */
	public static String getServerIP() {
		HttpServletRequest request = RequestUtil.getHttpRequest();
		return request.getLocalAddr();
	}
	
	private static String webRootPath = null;
	/**
	 * @return 网站根目录。
	 */
	public static String getWebRootPath(){
		if(webRootPath!=null){
			System.out.println(webRootPath);
			return webRootPath;
		}
		HttpServletRequest httpRequest = RequestUtil.getHttpRequest();
		if(httpRequest!=null){
			ServletContext servletContext = httpRequest.getSession().getServletContext();
			webRootPath = servletContext.getRealPath("/");
			System.out.println(webRootPath);
		}
		return webRootPath;
	}
	
}
