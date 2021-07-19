package com.tz.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tz.model.TzMusicUser;

/**
 * 
 * springmvc 登陆拦截器 LoginInterceptor 创建人:keke 时间：2015年3月24日-下午9:32:36
 * 
 * @version 1.0.0
 * 
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj) throws Exception {
		TzMusicUser musicUser = (TzMusicUser) request.getSession().getAttribute("user");
		String requestType = request.getHeader("X-Requested-With");//告诉：是一个ajax请求 XMLHttpRequest
		if (musicUser == null) {
			if (requestType != null && requestType.equals("XMLHttpRequest")) {
				response.getWriter().print("logout");
			}else{
				response.sendRedirect("/login");// 跳转到登陆页去
			}
			return false;
		} else {
			return true;// 让下一个拦截器去执行
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

}
