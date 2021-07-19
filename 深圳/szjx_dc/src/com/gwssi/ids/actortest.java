package com.gwssi.ids;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.gwssi.application.webservice.client.IReceiveHrApp;
import com.gwssi.application.webservice.client.ReceiveHrAppImpl;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.optimus.plugin.auth.model.User;
import com.trs.idm.client.actor.ActorException;
import com.trs.idm.client.actor.SSOUser;
import com.trs.idm.client.actor.StdHttpSessionBasedActor;

import edu.emory.mathcs.backport.java.util.Arrays;

public class actortest extends StdHttpSessionBasedActor {

	private static Logger logger = Logger.getLogger(actortest.class);

	@Override
	public boolean addUser(SSOUser arg0, HttpServletRequest arg1)
			throws ActorException {
		// 应用同步增加用户的实现. 返回 true, 表示同步成功; 否则表示同步失败lsdjfsdfsdf
		return false;
	}

	@Override
	public boolean disableUser(SSOUser arg0) throws ActorException {
		// 应用禁用用户的实现. 返回 true, 表示禁用成功; 否则表示禁用失败
		return false;
	}

	@Override
	public boolean enableUser(SSOUser arg0) throws ActorException {
		// 应用启用用户的实现. 返回 true, 表示启用成功; 否则表示启用失败
		return false;
	}

	@Override
	public String extractUserName(HttpServletRequest arg0)
			throws ActorException {
		// 从应用的自有登录页面的表单中获取用户名.
		return null;
	}

	@Override
	public String extractUserPwd(HttpServletRequest arg0) throws ActorException {
		// 从应用的自有登录页面的表单中获取密码.
		return null;
	}

	@Override
	public boolean removeUser(SSOUser arg0, HttpServletRequest arg1)
			throws ActorException {
		// 应用同步删除用户的实现. 返回 true, 表示同步成功; 否则表示同步失败.
		return false;
	}

	@Override
	public boolean updateUser(SSOUser arg0, HttpServletRequest arg1)
			throws ActorException {
		// 应用同步更新用户的实现. 返回 true, 表示同步成功; 否则表示同步失败.
		return false;
	}

	@Override
	public boolean userExist(SSOUser arg0) throws ActorException {
		// 应用判断用户是否存在的实现. 返回true, 表示用户存在, 使得登录时不必先调用同步增加用户的方法.
		return false;
	}

	@Override
	public boolean checkLocalLogin(HttpSession httpSession)
			throws ActorException {
		// 检查登录逻辑
		boolean flag = false;
		logger.info("检查当前登录用户：【"
				+ httpSession.getAttribute(OptimusAuthManager.LOGIN_NAME)
				+ "】是否登录过。");
		if (null != httpSession.getAttribute(OptimusAuthManager.LOGIN_NAME)) {
			flag = true;
		}
		logger.info("登录标记为： " + flag);
		return flag;
	}

	@Override
	public void loadLoginUser(HttpServletRequest request, SSOUser ssoUser) {
	
			String userName = ssoUser.getUserName();
			logger.info("根据TRSids单点登录方法返回的当前登录人是：" + userName);

//			Properties properties = ConfigManager.getProperties("ad");
//			String userName = userName + properties.getProperty("ad.dn.com");
//			logger.info("为当前登录人加上域后缀：" + userCom);

			// 调用人事接口获取当前登录人的人员信息和岗位信息
			IReceiveHrApp iReceiveHr = new ReceiveHrAppImpl();
			userName=this.dualUserName(userName);

			User user = iReceiveHr.getUserInfo(userName);

			// 根据当前登录的岗位信息查出角色集合
/*			AuthService authService = new AuthService();
			List roleList = new ArrayList();
			roleList = authService.getRoleIdByPost(user.getPostList());
			user.setRoleList(roleList);*/

			// 将当前登录人的信息存入缓存中
			HttpSession session = request.getSession();
			session.setAttribute(OptimusAuthManager.LOGIN_NAME, userName);
			session.setAttribute(OptimusAuthManager.USER, user);

	}
	/**
	 * 处理用户登录看是否用加后缀
	 * @param username
	 * @return
	 */
	private String dualUserName(String username){
		username=username.toUpperCase();
		Properties properties = ConfigManager.getProperties("common");
		String nosuffixuser = properties.getProperty("ids.login.no.suffix");
		String suffix= properties.getProperty("ids.login.suffix");
		if(StringUtils.isNotEmpty(nosuffixuser)){
			nosuffixuser=nosuffixuser.toUpperCase();
			String nosuffu[] =nosuffixuser.split(",");
			List list =Arrays.asList(nosuffu);
			if(list.contains(username)){
				return username;
			}else{
				if(!StringUtils.isEmpty(suffix)){
					username=username+suffix;
				}
					return username.toUpperCase();
				
			}
		}else{
			return null;
		}
	}
	
	@Override
	public void logout(HttpSession session) throws ActorException {
		// 处理注销逻辑
		session.removeAttribute(OptimusAuthManager.LOGIN_NAME);
		session.removeAttribute(OptimusAuthManager.USER);
		session.invalidate();
	}

}
