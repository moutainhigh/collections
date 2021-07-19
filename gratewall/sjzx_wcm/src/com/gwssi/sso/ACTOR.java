package com.gwssi.sso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.gwssi.AppConstants;
import com.gwssi.ssoagent.dualUser.HttpUserActor;
import com.gwssi.ssoagent.model.SSOUser;
import com.gwssi.util.UserUtil;

/**
 * 依据金信权限集成规范，实现 HttpUserActor 接口。
 * 
 * web.xml中配置了com.gwssi.ssoagent.webFilter.SsoAgentFilter拦截器。
 * 相关程序在 gwssi-sso.jar中。
 * 参见 SSoAgent.propertis 配置。
 * 
 * @author chaihaowei
 */
public class ACTOR implements HttpUserActor {
	
	private static Logger logger = Logger.getLogger(ACTOR.class);
	
	/**
	 * 由SsoAgentFilter调用此方法，检查用户是否登录。
	 * 如果返回 true，继续正常调用；
	 * 如果返回 false，拦截器将向api.szaic.gov.cn/sso获取当前域账号信息后调用 loadLoginUser方法。
	 */
	@Override
	public boolean checkLocalLogin(HttpSession httpSession) throws Exception {
		String userId = (String) httpSession.getAttribute(AppConstants.SESSION_KEY_USER_ID);
		
		boolean flag = false;
		if(StringUtils.isNotBlank(userId)){
			flag = true ;
		}else{
			flag = false ;
		}
		logger.info("flag is " + flag + "," + userId);
		return flag;
	}

	
	/**
	 * 执行用户登录操作。
	 * 
	 * @param request
	 * @param ssoUser 通过api.szaic.gov.cn/sso获得的当前登录域账户信息。
	 */
	@Override
	public void loadLoginUser(HttpServletRequest request, SSOUser ssoUser)
			throws Exception {

		System.out.println("ssousere:>>>>>>>>>>>>>"+ssoUser);
		logger.debug("进入loadLoginUser方法");
		String userID = this.dualUserName(ssoUser);
		String userName = UserUtil.getUserName(userID);
		logger.info("当前登录人是：" + userID + "，" + userName);
		logger.info(ssoUser);

		HttpSession session = request.getSession();
		session.setAttribute(AppConstants.SESSION_KEY_USER_ID, userID);
		session.setAttribute(AppConstants.SESSION_KEY_USER_NAME, userName);
		session.setAttribute(AppConstants.SESSION_KEY_USER_IP, ssoUser.getIpAddr());
		session.setAttribute(AppConstants.SESSION_KEY_SSO_USER, ssoUser);
	}
	
	
	/**
	 * 处理用户登录看是否用加后缀。
	 * 
	 * 处理后格式形如： LINQY1@SZAIC。
	 * 
	 * @param username
	 * @param ssoUser 
	 * @return
	 */
	private String dualUserName(SSOUser ssoUser){
		if(ssoUser==null){
			throw new RuntimeException("当前登录用户信息为空。");
		}
		ssoUser.setUsername(StringUtils.upperCase(ssoUser.getUsername())+"@"+StringUtils.upperCase(ssoUser.getAdDomain()));
		String ret =StringUtils.upperCase(ssoUser.getUsername());
		return ret;
	}

}
