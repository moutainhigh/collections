package com.gwssi.ids;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.gwssi.app.login.LoginService;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.component.security.KeyManager;
import cn.gwssi.common.component.security.RSAPrivateKey;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoUser;
import cn.gwssi.common.web.WebConstants;

import com.gwssi.common.util.Constants;
import com.gwssi.common.util.MD5;
import com.trs.idm.client.actor.ActorException;
import com.trs.idm.client.actor.SSOUser;
import com.trs.idm.client.actor.StdHttpSessionBasedActor;

public class actortest extends StdHttpSessionBasedActor
{
	// 应用同步增加用户的实现. 返回 true, 表示同步成功; 否则表示同步失败
	public boolean addUser(SSOUser arg0, HttpServletRequest arg1)
			throws ActorException
	{
		return false;
	}

	// 应用禁用用户的实现. 返回 true, 表示禁用成功; 否则表示禁用失败
	public boolean disableUser(SSOUser arg0) throws ActorException
	{
		return false;
	}

	// 应用启用用户的实现. 返回 true, 表示启用成功; 否则表示启用失败
	public boolean enableUser(SSOUser arg0) throws ActorException
	{
		return false;
	}

	// 从应用的自有登录页面的表单中获取用户名.
	public String extractUserName(HttpServletRequest arg0)
			throws ActorException
	{
		return null;
	}

	// 从应用的自有登录页面的表单中获取密码.
	public String extractUserPwd(HttpServletRequest arg0) throws ActorException
	{
		return null;
	}

	// 应用同步删除用户的实现. 返回 true, 表示同步成功; 否则表示同步失败.
	public boolean removeUser(SSOUser arg0, HttpServletRequest arg1)
			throws ActorException
	{
		return false;
	}

	// 应用同步更新用户的实现. 返回 true, 表示同步成功; 否则表示同步失败.
	public boolean updateUser(SSOUser arg0, HttpServletRequest arg1)
			throws ActorException
	{
		return false;
	}

	// 应用判断用户是否存在的实现. 返回true, 表示用户存在, 使得登录时不必先调用同步增加用户的方法.
	public boolean userExist(SSOUser arg0) throws ActorException
	{
		return false;
	}

	// 检查登录逻辑
	@Override
	public boolean checkLocalLogin(HttpSession httpSession)
			throws ActorException
	{
		System.out.println("进入了checkLocalLogin方法");
		boolean flag = false;
		TxnContext context = (TxnContext) httpSession.getAttribute("111");
		if (null != context) {
			VoUser operData = context.getOperData();
			if (null != operData && !operData.isEmpty())
				flag = true;
		}
		System.out.println("flag:" + flag);
		return flag;
	}

	// 登录逻辑
	@Override
	public void loadLoginUser(HttpServletRequest request, SSOUser ssoUser)
			throws ActorException
	{
		System.out.println("进入了loadLoginUser方法");
		   String userName =DualUserName.UserNameDual(ssoUser.getUserName());
		    System.out.println("处理后userName:" +userName);    
		TxnContext context = new TxnContext();
		context.setValue("username", userName);
		System.out.println("获取到当前的AD用户为：" + userName);
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

		System.out.println();
		System.out.println(context);
		System.out.println();
	}

	// 注销逻辑
	@Override
	public void logout(HttpSession session) throws ActorException
	{
		// request.removeAttribute("login-user-name");
		session.invalidate();
	}

}
