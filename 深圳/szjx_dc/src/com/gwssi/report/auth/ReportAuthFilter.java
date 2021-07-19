package com.gwssi.report.auth;

import java.io.IOException;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;

public class ReportAuthFilter implements Filter{
	
	private ReportAuthService service = new ReportAuthService();
	
	
	public static final String USER_REGION_KEY = "USER_REGION_CODE";
	public static final String USER_BASIC_REGION_KEY = "USER_BASIC_REGION_CODE";
	public static final String USER_ROLE_KEY = "USER_DOOR_ROLE_CODE";
	public static final String SUPER_SYS="SUPER_SYS";//超级管理员
	public static final String SUPER_SYS_FENXI="SUPER_SYS_FENXI";//超级管理员
	
	public static final String ZONG_HE_ROLE_CODE = "ZONGHE_REPORT_ROLE";//综合岗角色CODE
	public static final String YE_WU_ROLE_CODE = "YEWU_REPORT_ROLE";    //业务岗角色CODE
	public static final String HUN_HE_ROLE_CODE="ZONGHE_REPORT_ROLE,YEWU_REPORT_ROLE"; //混合岗角色CODE
	public static final String HUN_HE_ROLE_CODE1="YEWU_REPORT_ROLE,ZONGHE_REPORT_ROLE";//混合岗角色CODE
	@Override
	public void destroy() {
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse res = (HttpServletResponse) response;
		System.out.println("--------------------"+"ReportAuthFilter.doFilter"+"-------------------");
		//获取用户
		User user  = (User) req.getSession().getAttribute(OptimusAuthManager.USER);
		//@FOR_TEST
		//User u = new User();
//		u.setUserId("CHANGRUAN@SZAIC");
//		u.setUserName("CHANGRUAN@SZAIC");
//		user = u;
//		判断用户是否为空
		if(user == null){ 
			redirectNoRight(req, res);
			return ;
		}
		//获取用户部门、角色信息
		String [] roleDepartInfo = service.getUserDepartment(user.getUserId());
		//@FOR_TEST
		//roleDepartInfo[2] = "yewu@TODO";
		//部门信息
		if(roleDepartInfo == null  || roleDepartInfo[2] == null ||
				!(ZONG_HE_ROLE_CODE.equals(roleDepartInfo[2]) ||   
				 YE_WU_ROLE_CODE.equals(roleDepartInfo[2]) ||
				 HUN_HE_ROLE_CODE.equals(roleDepartInfo[2])||
				 HUN_HE_ROLE_CODE1.equals(roleDepartInfo[2])||
				 SUPER_SYS.equals(roleDepartInfo[2])||
				 SUPER_SYS_FENXI.equals(roleDepartInfo[2]))){//@TODO 角色不属于报表角色 
					redirectNoRight(req, res);
					System.out.println("角色信息不正确");
					return;
				}
		
		//部门和区域信息都为空
		if (roleDepartInfo[0]==null&&roleDepartInfo[1]==null) {
			redirectNoRight(req, res);
			return;
		}
		//区域信息为空
		if (roleDepartInfo[0]!=null&&roleDepartInfo[1]==null) {
			roleDepartInfo[1]=service.getRegion(roleDepartInfo[0]);
		}
		
		Set<SimpleReportInfo> reportNames = UserDepartmentUtils.getInstance().getReportNames(roleDepartInfo[0]==null?"lqbz":roleDepartInfo[0]);
		String reportNames1="";
		if (reportNames==null||reportNames.size()==0) {
			reportNames1="";
		}else{
			for (SimpleReportInfo simpleReportInfo : reportNames) {
				reportNames1+=simpleReportInfo.reportName;
			}
		}
		req.getSession().setAttribute("reportNames",reportNames1);
		
		if (ZONG_HE_ROLE_CODE.equals(roleDepartInfo[2])||
				SUPER_SYS.equals(roleDepartInfo[2])||
				SUPER_SYS_FENXI.equals(roleDepartInfo[2])) {
			//综合岗，代码为1
			req.getSession().setAttribute(USER_ROLE_KEY, "1");
			}else if (YE_WU_ROLE_CODE.equals(roleDepartInfo[2])) {
			//业务岗，代码为2
			req.getSession().setAttribute(USER_ROLE_KEY, "2");
		}else if (HUN_HE_ROLE_CODE1.equals(roleDepartInfo[2]) ||
				HUN_HE_ROLE_CODE.equals(roleDepartInfo[2])) {
			//混合岗，代码为3
			req.getSession().setAttribute(USER_ROLE_KEY,"3");
		}else{
			//该角色不属于系统角色
			redirectNoRight(req, res);
			return;
			}
				
			//部门信息//需要判断用户部门信息是否为空
			req.getSession().setAttribute(USER_BASIC_REGION_KEY, roleDepartInfo[0]);//部门
			//区域信息//需要判断用户区域信息是否为空
			req.getSession().setAttribute(USER_REGION_KEY, service.getReportRegionCode(roleDepartInfo[1]));//区域
			//综合岗-1
			//req.getSession().setAttribute(USER_ROLE_KEY  , ZONG_HE_ROLE_CODE.equals(roleDepartInfo[2]) ? "1" : "2");
			//区域代码
			//req.getSession().setAttribute(USER_REGION_KEY, ZONG_HE_ROLE_CODE.equals(roleDepartInfo[2]) ? "1" : service.getReportRegionCode(roleDepartInfo[1]));
			//req.getSession().setAttribute(USER_BASIC_REGION_KEY, roleDepartInfo[0]);
			chain.doFilter(request, response);
	}
	 
	private void redirectNoRight(HttpServletRequest req, HttpServletResponse res){
		try {
			res.sendRedirect(req.getContextPath()+"/page/admin/noRight.html");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//		final HttpServletRequest req = (HttpServletRequest) request;
//		final HttpServletResponse res = (HttpServletResponse) response;
//		//获取用户
//		User user  = (User) req.getSession().getAttribute(OptimusAuthManager.USER);
//		List<String> funcList = user.getAuthfunclist();
//		//后台配置URL编码，此处应有解码
//		String temp = null;
//		for(int i = 0; i < funcList.size(); i ++){
//			temp = funcList.get(i);
//			temp = URLDecoder.decode(temp);
//			funcList.set(i, temp);
//		}
//		final String requestPath = req.getRequestURI().replaceAll(req.getContextPath(), "");
//		//先简单url匹配。@TODO 按参数
//		for(String t : funcList) {
//			if(t.indexOf(requestPath) != -1){
//				chain.doFilter(request, response);
//				return ;
//			}
//		}
//		res.sendRedirect(req.getContextPath()+"/page/admin/noRight.html");
//	}


	//public static void main(String[] args) {
//		String s = "﻿﻿page/report/ana.jsp?reportPath=report@@ReportAnalysis@@主题分析@@商事主体发展趋势_按年份_总图(主体类型)";
//		System.out.println(URLDecoder.decode(s));
//		Set<String> sets = new HashSet<String>();
	//}

	@Override
	public void init(FilterConfig config) throws ServletException {
	
	}
	

}
