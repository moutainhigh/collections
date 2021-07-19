package com.ly.filter;

import static com.ly.common.System.LOGIN_TO_URL;

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

import com.ly.pojo.User;


public class LoginFilter2 implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	//https://blog.csdn.net/qq_34445857/article/details/79223526
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

		  //chain.doFilter(req, resp);
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
       /* request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");*/
        //request.setCharacterEncoding("utf-8");
        //response.setCharacterEncoding("utf-8");
        //response.setContentType("text/html;character=UTF-8");
        
        HttpSession session = request.getSession();
        
        
        String fromUR = request.getRequestURI();
        request.getSession().setAttribute(LOGIN_TO_URL, fromUR);
        System.out.println(fromUR);
        
        if(fromUR.equals("/pa/login.do")){
        	chain.doFilter(request,response);
        }
        
        
        String path =request.getContextPath();
        //PrintWriter out = response.getWriter();
        User user = (User)session.getAttribute("user");
       if(user!=null){
    	   String userName = user.getName();
    	   if(userName != null){
               chain.doFilter(request,response);
           } else{
        	   response.sendRedirect(path +"/pages/login.html");
           }
       		}else{
           // out.println("您还未登陆，三秒钟后跳转至登录页面");
            //out.println("<script language='javascript'>alert('你还未登录');");
            //response.setHeader("refresh","3;/pages/login.html");
          
          // response.sendRedirect(path +"/pages/login.html");
            
            
           request.getRequestDispatcher("/pages/login.html").forward(request,response);
        }

		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
