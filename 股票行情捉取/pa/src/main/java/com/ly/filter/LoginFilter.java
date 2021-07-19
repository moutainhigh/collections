package com.ly.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ly.pojo.User;


//https://blog.csdn.net/lili518/article/details/77922265
public class LoginFilter implements  Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest htRequest, ServletResponse htResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) htRequest;
		HttpServletResponse response = (HttpServletResponse) htResponse;
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("username");
		String path = request.getRequestURI();
		//System.out.println(path);
		String projectPath = request.getContextPath();
		 request.getSession().setAttribute("fromPath", path);
		if (userName != null) {
			filterChain.doFilter(request, response);
		}else{
		    response.sendRedirect(projectPath+"/pages/login.html");
			//request.getRequestDispatcher(projectPath+"/pages/login.html").forward(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
