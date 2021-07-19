package com.ly.intercepter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ly.common.SysResultCode;

import net.sf.json.JSONObject;

//https://blog.csdn.net/xiaozhu0301/article/details/49208017
//https://blog.csdn.net/lzg319/article/details/80312497
//https://www.jb51.net/article/119692.htm
@Component(value = "myFilter")
public class CheckLegalityFilter extends OncePerRequestFilter {
	@Autowired
	private MyService myService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String origin = request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", origin);
		response.setContentType("text/html; charset=utf-8");
		String allowURL = request.getRequestURL().toString();
		Map ret = new HashMap();
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		if (allowURL.contains("api")) {
			filterChain.doFilter(request, response);
		} else {
			String token = request.getParameter("token");

			if (!StringUtils.isEmpty(token)&&!(token.equals("null"))) {
				String keyWord = URLDecoder.decode(token, "UTF-8");
				boolean flag = myService.isLogin(keyWord);
				if (flag) {
					filterChain.doFilter(request, response);
				}else{
					ret.put("msg", "用户未登录");
					jsonObject = JSONObject.fromObject(ret);
					writer = response.getWriter();
					writer.print(jsonObject);
					writer.close();
					response.flushBuffer();
				}
			} else {
				ret.put("msg", "用户未登录");
				jsonObject = JSONObject.fromObject(ret);
				writer = response.getWriter();
				writer.print(jsonObject);
				writer.close();
				response.flushBuffer();
			}

		}
	}
}
