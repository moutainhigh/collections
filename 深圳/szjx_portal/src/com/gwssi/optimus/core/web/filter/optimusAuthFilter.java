package com.gwssi.optimus.core.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gwssi.application.home.controller.HomeController;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.optimus.plugin.auth.service.AuthService;
import org.apache.log4j.Logger;
//import org.apache.log4j.MDC;
public class optimusAuthFilter  extends OncePerRequestFilter{
	/*session过期页面*/
	private  String noSessionUrl =null;
	
	/*没有权限页面*/
	private  String noRightPage=null;
   
	/*登录方法.do*/
	private String logindo =null;
	private static Logger log = Logger.getLogger(optimusAuthFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest req,	HttpServletResponse res, FilterChain arg2)throws ServletException, IOException {
		
		FilterConfig  filterconfig =this.getFilterConfig();
		this.noSessionUrl=	filterconfig.getInitParameter("noSessionUrl");
		this.noRightPage =filterconfig.getInitParameter("noRightPage");
		this.logindo =filterconfig.getInitParameter("logindo");
		
		if(StringUtils.equals(req.getServletPath(), logindo)){
			arg2.doFilter(req, res);
			return;
		}
		
		//session过期
		if(req.getSession(false)==null){
			res.sendRedirect(req.getContextPath()+""+noSessionUrl);
			return ;
		}

		
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		if(user==null||user.getFunclist()==null){
			res.sendRedirect(req.getContextPath()+""+noSessionUrl);
			return ;
		}
		List<String> needFilterlist=user.getAuthfunclist();
       // MDC.put("userId",user.getUserId());
        //System.out.println("username-----:"+user.getUserName());
      //  MDC.put("userName",user.getUserName());
       // MDC.put("ip", user.getIp());
		if(needFilterlist.contains(req.getServletPath())){
			boolean hasurlRight=hasUrlRight(req,res);
			//System.out.println("需要验证："+req.getServletPath()+"	是否通过验证："+hasurlRight);
			if(!hasurlRight){
				res.sendRedirect(req.getContextPath()+""+noRightPage);
			}else{
				arg2.doFilter(req, res);
			}
			
	
		}else{
			//System.out.println("不需要验证："+req.getServletPath());
			arg2.doFilter(req, res);
		}
	
	}
	/**
	 * 判断是否有权限
	 * @param req
	 * @param res
	 * @return
	 */
	private boolean hasUrlRight(HttpServletRequest req, HttpServletResponse res){
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);
		boolean hasRight =false;
		if(null!=user&&null!=user.getFunclist()){
			List<Map> userlist=user.getFunclist();
			String url=req.getServletPath();
	
			for(Map<String, String> map1:userlist){
				if(url.equals(map1.get("functionUrl"))){
				//	MDC.put("FUNCTION_CODE", map1.get("functionCode"));
					hasRight=true;
					break;
				}	
			}			
		}

		
		return hasRight;
	}

}
