package com.gwssi.ids;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.gwssi.application.webservice.client.IReceiveHrApp;
import com.gwssi.application.webservice.client.ReceiveHrAppImpl;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.gwssi.ssoagent.dualUser.HttpUserActor;
import com.gwssi.ssoagent.model.SSOUser;

public class ACTOR implements HttpUserActor {
	private static Logger log = Logger.getLogger(ACTOR.class);
	/**
	 * 检查用户是否登录
	 */
	@Override
	public boolean checkLocalLogin(HttpSession httpSession) throws Exception {
		// 检查登录逻辑
		boolean flag =false;
		String loginName = null;
		//System.out.println("LOGIN_NAME is "
			//	+ httpSession.getAttribute(OptimusAuthManager.LOGIN_NAME));
		loginName=(String) httpSession.getAttribute(OptimusAuthManager.LOGIN_NAME);
		
		if(loginName!=null&&loginName.length()>0){
			flag=true;
		}
//		System.out.println("flag is " + flag);
		System.out.println("退出了checkLocalLogin方法: " + flag);
		return flag;
	}

	/**
	 * 用户登录信息
	 */
	@Override
	public void loadLoginUser(HttpServletRequest request, SSOUser ssoUser)
			throws Exception {

		log.info("登陆系统 当前登陆人信息："+ssoUser);
//		System.out.println("进入loadLoginUser方法"); 
		String userName = ssoUser.getUsername();
		System.out.println("当前登录人是：" + userName);
		
		userName =this.dualUserName(userName,ssoUser);
		userName=userName.toUpperCase();
		
		
		//userName = "chenws";
		log.info("处理后用户名："+userName);
		IReceiveHrApp iReceiveHr = new ReceiveHrAppImpl();
		//User user = iReceiveHr.getUserInfo(userName);
		User user = iReceiveHr.getUserInfoByUserRoles(userName);

		user.setIp(iReceiveHr.getIP(request));
		
		//FOR TEST   /page/report/ana.jsp?reportPath=report@@ReportAnalysis@@主题分析@@商事主体发
		user.getAuthfunclist().add("/page/report/ana.jsp?reportPath=report%40%40ReportAnalysis%40%40%e4%b8%bb%e9%a2%98%e5%88%86%e6%9e%90%40%40%e5%95%86%e4%ba%8b%e4%b8%bb%e4%bd%93%e5%8f%91");

		userName=userName.toUpperCase();
//		System.out.println(ssoUser);
		// 将当前登录人的信息存入缓存中
	
		HttpSession session = request.getSession();
		session.setAttribute(OptimusAuthManager.LOGIN_NAME, userName);
		session.setAttribute(OptimusAuthManager.USER, user);
		
	}
	/**
	 * 处理用户登录看是否用加后缀
	 * @param username
	 * @param ssoUser 
	 * @return
	 */
	private String dualUserName(String username, SSOUser ssoUser){
		return ssoUser == null ? "" : (ssoUser.getUsername() + "@" + ssoUser.getAdDomain()).toUpperCase();
	}
}
