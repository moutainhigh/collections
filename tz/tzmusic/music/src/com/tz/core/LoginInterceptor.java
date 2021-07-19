package com.tz.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登陆拦截器
 * LoginInterceptor<BR>
 * 创建人:潭州学院-keke <BR>
 * 时间：2015年2月7日-下午11:28:46 <BR>
 * 
 * @version 1.0.0
 * 
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//获取会话里面登陆的用户信息
		String username = (String) request.getSession().getAttribute("username");
		String requestType = request.getHeader("X-Requested-With");
		if(username==null){
			if (requestType != null && requestType.equals("XMLHttpRequest")) {
				response.getWriter().print("logout");
			}else{
				response.sendRedirect("/login");//首页居多
			}
			return false;//终止后面的拦截器的执行
		}else{
			return true;//让下一个拦截器去处理
		}
	}
		

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("==================对视图进一步处理。。。。");
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("=====================对参数的一起处理...");

	}

}
