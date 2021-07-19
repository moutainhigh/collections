package com.gwssi.ssoagent.dualUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.gwssi.ssoagent.model.SSOUser;
public interface HttpUserActor { 
	
	//验证用户是否已经登录
	public boolean checkLocalLogin(HttpSession httpSession) throws Exception; 
	
	//用户登录
	public void loadLoginUser(HttpServletRequest request, SSOUser ssoUser) throws Exception;

}
