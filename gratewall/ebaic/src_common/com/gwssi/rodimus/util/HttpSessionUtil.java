package com.gwssi.rodimus.util;

import javax.servlet.http.HttpSession;

import com.gwssi.ebaic.apply.util.SessionConst;
import com.gwssi.ebaic.domain.SysmgrIdentityBO;
import com.gwssi.ebaic.domain.TPtYhBO;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.session.HttpTorchSession;

/**
 * 
 * TODO 与 SessionUtil合并
 * 
 * @author liuhailong
 */
public class HttpSessionUtil {
	
	/**
	 * @return
	 */
	public static HttpSession getSession() {
		//HttpSession session = WebContext.getHttpSession();
		HttpSession session = new HttpTorchSession();
		
		return session;
	}
	public static String getCurrentUserId(){
		TPtYhBO user = getCurrentUser();
		if(user==null){
			return "";
		}
		String ret = user.getUserId();
		return ret;
	}
	/**
	 * 得到当前登录用户。
	 * 
	 * @return
	 */
	public static TPtYhBO getCurrentUser() {
		//HttpSession session = getSession();
		HttpSession session=new HttpTorchSession();
		TPtYhBO user = (TPtYhBO) session.getAttribute(SessionConst.USER);
		return user;
	}
	/**
	 * 得到法人股东登录用户信息
	 * 
	 * @return
	 */
	public static SysmgrIdentityBO getEntUser(){
		HttpSession session=new HttpTorchSession();
		SysmgrIdentityBO user = (SysmgrIdentityBO) session.getAttribute(SessionConst.USER);
		return user;
	}
	
	/**
	 * 是否登录.
	 * 
	 * @return
	 */
	public static boolean isLogin() {
		//HttpSession session = getSession();
		HttpSession session=new HttpTorchSession();
		boolean ret = (session.getAttribute(SessionConst.USER) != null);
		return ret;
	}
	/**
	 * 
	 * @return SessionId
	 */
	public static String getId() {
		HttpSession session = getSession();
		String ret = session.getId();
		return ret;
	}
	
	public static String getLoginUserType(){
		HttpSession session=new HttpTorchSession();
		String loginUserType = StringUtil.safe2String(session.getAttribute(OptimusAuthManager.LOGIN_USER_TYPE));
		return loginUserType;
	}
}
