package com.ly.intercepter;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ly.common.SysResultCode;

import net.sf.json.JSONObject;

//https://blog.csdn.net/Reminisce_man/article/details/76546371
//https://blog.csdn.net/u013979547/article/details/53449788
//https://blog.csdn.net/yangyuscript/article/details/72802580
//https://qiaolevip.iteye.com/blog/1827676
//https://www.cnblogs.com/limn/p/8733126.html
public class MyInterceptor implements HandlerInterceptor {
	private final static Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

	//https://www.cnblogs.com/shihaiming/p/9544060.html
	//https://blog.csdn.net/shadowtime/article/details/80612594
	//https://www.jianshu.com/p/3ae1739c50ba
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		
		String origin = request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", origin);
		logger.info("进入 preHandle 方法..." + request.getRequestURL().toString() + "," + request.getRequestURI());
		String projectPath = request.getContextPath();
		 response.setContentType("text/html; charset=utf-8");
		// 如果是登录login则放行
		 String allowURL = request.getRequestURL().toString();
		if (allowURL.contains("api")) {
			return true;
		}else{
			// 如果已经登录过,则放行
			String userName = (String) session.getAttribute("username");
			System.out.println(session.getAttribute("username"));
			if (userName != null) {
				return true;
			}else{
				//response.sendRedirect(projectPath+"/pages/login.html");
				
			
				JSONObject jsonObject = JSONObject.fromObject(SysResultCode.codeStatus0());
				
				 PrintWriter writer = response.getWriter();
			     writer.print(jsonObject);
			     writer.close();
			     response.flushBuffer(); 
				 return false;
			}
			/*// 没登录过又不登录login页面,转发到登录页面
			request.setAttribute("msg", "你还没登录");
			request.getRequestDispatcher("/WEB-INF/pages/login.html").forward(request, response);
			return false;*/
		}
		
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		logger.info("进入 postHandle 方法..." + request.getRequestURL().toString() + "," + request.getRequestURI());
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		logger.info("进入 afterCompletion 方法..." + request.getRequestURL().toString() + "," + request.getRequestURI());
	}
}
