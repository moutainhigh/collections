package com.gwssi.ids;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.gwssi.common.util.Constants;
import com.gwssi.ssoagent.dualUser.HttpUserActor;
import com.gwssi.ssoagent.model.SSOUser;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoUser;
import cn.gwssi.common.web.WebConstants;



public class ACTOR implements HttpUserActor {

	/**
	 * 检查用户是否登录
	 */
	@Override
	public boolean checkLocalLogin(HttpSession httpSession) throws Exception {
		boolean flag = false;
		TxnContext context = (TxnContext) httpSession.getAttribute("111");
		if (null != context) {
			VoUser operData = context.getOperData();
			if (null != operData && !operData.isEmpty())
				flag = true;
		}
		return flag;
	}

	/**
	 * 用户登录信息
	 */
	@Override
	public void loadLoginUser(HttpServletRequest request, SSOUser ssoUser)
			throws Exception {


		   String userName =DualUserName.UserNameDual(ssoUser.getUsername());
		TxnContext context = new TxnContext();
		context.setValue("username", userName);
		context.setValue(Constants.LOGIN_PASSWORD, "222222");

		VoUser operData = context.getOperData();
		operData.setUserName(userName);
		operData.setSessionId(request.getRequestedSessionId());

		context.setValue("logintype", "000001");

		request.setAttribute(VoUser.OPER_USERNAME, userName);
		request.setAttribute(WebConstants.DATABUS_NODE, context);
		request.getSession().setAttribute(VoUser.OPER_USERNAME,
				userName);

		request.getSession().setAttribute(TxnContext.OPER_DATA_NODE, operData);
		request.getSession().setAttribute("111", context);


	}
	/**
	 * 处理用户登录看是否用加后缀
	 * @param username
	 * @param ssoUser 
	 * @return
	 */
	private String dualUserName(String username, SSOUser ssoUser){
		return ssoUser==null?"":(ssoUser.getUsername()+"@"+ssoUser.getAdDomain()).toUpperCase();
	}

}
