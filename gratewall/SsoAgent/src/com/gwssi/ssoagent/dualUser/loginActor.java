package com.gwssi.ssoagent.dualUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;





import com.gwssi.ssoagent.model.SSOUser;

public class loginActor implements HttpUserActor {

	/**
	 * ����û��Ƿ��¼
	 */
	@Override
	public boolean checkLocalLogin(HttpSession httpSession) throws Exception {
		// ����¼�߼�
		System.out.println("������checkLocalLogin����");
		boolean flag = false;
		String loginName = null;
		System.out.println("LOGIN_NAME is "
				+ httpSession.getAttribute("loginName"));
		loginName=(String) httpSession.getAttribute("loginName");
		if(loginName!=null&&loginName.length()>0){
			flag=true;
		}
		System.out.println("flag is " + flag);
		System.out.println("�˳���checkLocalLogin����");
		return flag;
	}

	/**
	 * �û���¼��Ϣ
	 */
	@Override
	public void loadLoginUser(HttpServletRequest request, SSOUser ssoUser)
			throws Exception {

		System.out.println("����loadLoginUser����");
		String userName = ssoUser.getUsername();
		System.out.println("��ǰ��¼���ǣ�" + userName);
		
		

		userName=userName.toUpperCase();
		System.out.println(ssoUser);
		// ����ǰ��¼�˵���Ϣ���뻺����
		HttpSession session = request.getSession();
		session.setAttribute("loginName", userName);
		
	}


}
