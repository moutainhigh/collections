package com.gwssi.ssoagent.dualUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;





import com.gwssi.ssoagent.model.SSOUser;

public class loginActor implements HttpUserActor {

	/**
	 * 检查用户是否登录
	 */
	@Override
	public boolean checkLocalLogin(HttpSession httpSession) throws Exception {
		// 检查登录逻辑
		System.out.println("进入了checkLocalLogin方法");
		boolean flag = false;
		String loginName = null;
		System.out.println("LOGIN_NAME is "
				+ httpSession.getAttribute("loginName"));
		loginName=(String) httpSession.getAttribute("loginName");
		if(loginName!=null&&loginName.length()>0){
			flag=true;
		}
		System.out.println("flag is " + flag);
		System.out.println("退出了checkLocalLogin方法");
		return flag;
	}

	/**
	 * 用户登录信息
	 */
	@Override
	public void loadLoginUser(HttpServletRequest request, SSOUser ssoUser)
			throws Exception {

		System.out.println("进入loadLoginUser方法");
		String userName = ssoUser.getUsername();
		System.out.println("当前登录人是：" + userName);
		
		

		userName=userName.toUpperCase();
		System.out.println(ssoUser);
		// 将当前登录人的信息存入缓存中
		HttpSession session = request.getSession();
		session.setAttribute("loginName", userName);
		
	}


}
