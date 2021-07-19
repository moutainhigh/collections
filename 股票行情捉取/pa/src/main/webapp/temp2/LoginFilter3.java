package com.ly.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.filter.OncePerRequestFilter;

import com.ly.pojo.User;

//https://blog.csdn.net/qq_23934475/article/details/80941877
//https://fanshuyao.iteye.com/blog/2432408

//https://www.cnblogs.com/jianjianyang/p/5009457.html
//https://www.cnblogs.com/lr393993507/p/5543025.html
public class LoginFilter3 extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String path = request.getRequestURI();
		System.out.println(path);
		if (path.equals("/pa/login.do")) {
			filterChain.doFilter(request, response);
			return;
		}

		if (user != null && user.getName() != null) {
			filterChain.doFilter(request, response);
		} else {
			response.getWriter().print("{\"msg\":\"-1\"}");
		}

	}

}
