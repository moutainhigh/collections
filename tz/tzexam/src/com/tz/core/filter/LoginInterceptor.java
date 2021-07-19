package com.tz.core.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tz.model.User;
import com.tz.util.TzConstant;

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
		User user = (User) request.getSession().getAttribute(TzConstant.SESSION_USER);
		if(user==null){
			String requestType = request.getHeader("X-Requested-With");
			if (requestType != null && requestType.equals("XMLHttpRequest")) {
				response.getWriter().print("logout");
			}else{
				response.sendRedirect(request.getContextPath()+"/"+"login");//首页居多
			}
			return false;
		}else{
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}
}
