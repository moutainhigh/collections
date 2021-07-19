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
	// Ӧ��ͬ�������û���ʵ��. ���� true, ��ʾͬ���ɹ�; �����ʾͬ��ʧ��
	public boolean addUser(SSOUser arg0, HttpServletRequest arg1)
			throws ActorException
	{
		return false;
	}

	// Ӧ�ý����û���ʵ��. ���� true, ��ʾ���óɹ�; �����ʾ����ʧ��
	public boolean disableUser(SSOUser arg0) throws ActorException
	{
		return false;
	}

	// Ӧ�������û���ʵ��. ���� true, ��ʾ���óɹ�; �����ʾ����ʧ��
	public boolean enableUser(SSOUser arg0) throws ActorException
	{
		return false;
	}

	// ��Ӧ�õ����е�¼ҳ��ı��л�ȡ�û���.
	public String extractUserName(HttpServletRequest arg0)
			throws ActorException
	{
		return null;
	}

	// ��Ӧ�õ����е�¼ҳ��ı��л�ȡ����.
	public String extractUserPwd(HttpServletRequest arg0) throws ActorException
	{
		return null;
	}

	// Ӧ��ͬ��ɾ���û���ʵ��. ���� true, ��ʾͬ���ɹ�; �����ʾͬ��ʧ��.
	public boolean removeUser(SSOUser arg0, HttpServletRequest arg1)
			throws ActorException
	{
		return false;
	}

	// Ӧ��ͬ�������û���ʵ��. ���� true, ��ʾͬ���ɹ�; �����ʾͬ��ʧ��.
	public boolean updateUser(SSOUser arg0, HttpServletRequest arg1)
			throws ActorException
	{
		return false;
	}

	// Ӧ���ж��û��Ƿ���ڵ�ʵ��. ����true, ��ʾ�û�����, ʹ�õ�¼ʱ�����ȵ���ͬ�������û��ķ���.
	public boolean userExist(SSOUser arg0) throws ActorException
	{
		return false;
	}

	// ����¼�߼�
	@Override
	public boolean checkLocalLogin(HttpSession httpSession)
			throws ActorException
	{
		System.out.println("������checkLocalLogin����");
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

	// ��¼�߼�
	@Override
	public void loadLoginUser(HttpServletRequest request, SSOUser ssoUser)
			throws ActorException
	{
		System.out.println("������loadLoginUser����");
		   String userName =DualUserName.UserNameDual(ssoUser.getUserName());
		    System.out.println("�����userName:" +userName);    
		TxnContext context = new TxnContext();
		context.setValue("username", userName);
		System.out.println("��ȡ����ǰ��AD�û�Ϊ��" + userName);
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

	// ע���߼�
	@Override
	public void logout(HttpSession session) throws ActorException
	{
		// request.removeAttribute("login-user-name");
		session.invalidate();
	}

}
