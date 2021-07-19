package com.arj.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arj.comm.util.IpReport;
public class IpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8") ;
		String city = request.getParameter("ip") ;
		String info = IpReport.getIp(city) ;
		request.setAttribute("ip", getIpAddr(request));
		request.setAttribute("info", info) ;
		request.getRequestDispatcher("/search.jsp").forward(request, response) ;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		this.doGet(request, response) ;
	}
	
	public static String getIpAddr(HttpServletRequest request) {
		  String ip = request.getHeader("x-forwarded-for");
		  if (ip ==null || "unknown".equalsIgnoreCase(ip)) {
		   ip = request.getHeader("Proxy-Client-IP");
		  }
		  if (ip ==null || "unknown".equalsIgnoreCase(ip)) {
		   ip = request.getHeader("WL-Proxy-Client-IP");
		  }
		  if (ip ==null || "unknown".equalsIgnoreCase(ip)) {
		   ip = request.getRemoteAddr();
		  }
		  return ip;
		}
}
