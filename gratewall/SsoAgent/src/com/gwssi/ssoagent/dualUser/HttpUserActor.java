package com.gwssi.ssoagent.dualUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.gwssi.ssoagent.model.SSOUser;
public interface HttpUserActor { 
	
	//��֤�û��Ƿ��Ѿ���¼
	public boolean checkLocalLogin(HttpSession httpSession) throws Exception; 
	
	//�û���¼
	public void loadLoginUser(HttpServletRequest request, SSOUser ssoUser) throws Exception;

}
