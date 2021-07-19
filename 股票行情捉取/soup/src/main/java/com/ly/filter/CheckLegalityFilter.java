package com.ly.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alibaba.fastjson.JSONObject;

//https://www.cnblogs.com/java-jun-world2099/p/9087668.html
@Component(value = "myFilter")
public class CheckLegalityFilter extends OncePerRequestFilter {
	
	@Autowired
	private MyService myService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*"); //解决跨域访问报错   
	    response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");   
	    response.setHeader("Access-Control-Max-Age", "3600"); //设置过期时间   
	    response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization");   
	    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 支持HTTP 1.1.   
	    response.setHeader("Pragma", "no-cache"); // 支持HTTP 1.0. response.setHeader("Expires", "0");   
		response.setContentType("text/html; charset=utf-8");
		String allowURL = request.getRequestURL().toString();
		
		Map ret = new HashMap();
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		if (allowURL.contains("api")) {
			filterChain.doFilter(request, response);
		} else {
			String token = request.getParameter("token");

			if (StringUtils.isNotEmpty(token)) {
				String keyWord = URLDecoder.decode(token, "UTF-8");
				boolean flag = myService.isLogin(keyWord);
				if (flag) {
					filterChain.doFilter(request, response);
				}else{
					ret.put("msg", "用户未登录");
					jsonObject = JSONObject.parseObject(JSONObject.toJSONString(ret));
					writer = response.getWriter();
					writer.print(jsonObject);
					writer.close();
					response.flushBuffer();
				}
			} else {
				ret.put("msg", "用户未登录");
				jsonObject = JSONObject.parseObject(JSONObject.toJSONString(ret));
				writer = response.getWriter();
				writer.print(jsonObject);
				writer.close();
				response.flushBuffer();
			}

		}		
	}
	
	
	/*@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "*"); //解决跨域访问报错   
	    response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");   
	    response.setHeader("Access-Control-Max-Age", "3600"); //设置过期时间   
	    response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization");   
	    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 支持HTTP 1.1.   
	    response.setHeader("Pragma", "no-cache"); // 支持HTTP 1.0. response.setHeader("Expires", "0");   
		response.setContentType("text/html; charset=utf-8");
		
		//filterChain.doFilter(request, response);
		
		
		HttpSession session = request.getSession();
		
		String allowURL = request.getRequestURL().toString();
		Map ret = new HashMap();
		JSONObject jsonObject = null;
		PrintWriter writer = null;
		if (allowURL.contains("api")) {
			filterChain.doFilter(request, response);
		} else {
				boolean flag = myService.isLogin(session);
				if (flag) {
					filterChain.doFilter(request, response);
				}else{
					ret.put("msg", "用户未登录");
					jsonObject = JSONObject.parseObject(JSONObject.toJSONString(ret));
					writer = response.getWriter();
					writer.print(jsonObject);
					writer.close();
					response.flushBuffer();
				}
			}
	}*/
}
