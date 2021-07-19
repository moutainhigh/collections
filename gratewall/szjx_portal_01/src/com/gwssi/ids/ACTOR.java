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
import com.gwssi.ssoagent.dualUser.HttpUserActor;
import com.gwssi.ssoagent.model.SSOUser;

import edu.emory.mathcs.backport.java.util.Arrays;

public class ACTOR implements HttpUserActor {
	private static Logger log = Logger.getLogger(ACTOR.class);
	/**
	 * 检查用户是否登录
	 */
	@Override
	public boolean checkLocalLogin(HttpSession httpSession) throws Exception {
		// 检查登录逻辑
		//System.out.println("进入了checkLocalLogin方法");
		boolean flag = false;
		String loginName = null;
		//System.out.println("LOGIN_NAME is "
			//	+ httpSession.getAttribute(OptimusAuthManager.LOGIN_NAME));
		loginName=(String) httpSession.getAttribute(OptimusAuthManager.LOGIN_NAME);
		if(loginName!=null&&loginName.length()>0){
			flag=true;
		}
//		System.out.println("flag is " + flag);
//		System.out.println("退出了checkLocalLogin方法");
		return flag;
	}

	/**
	 * 用户登录信息
	 */
	@Override
	public void loadLoginUser(HttpServletRequest request, SSOUser ssoUser)
			throws Exception {

		log.info("登陆系统 当前登陆人信息："+ssoUser);
//		System.out.println("进入loadLoginUser方法");
		String userName = ssoUser.getUsername();
//		System.out.println("当前登录人是：" + userName);
		
		userName =this.dualUserName(userName,ssoUser);
		userName=userName.toUpperCase();
		log.info("处理后用户名："+userName);
		IReceiveHrApp iReceiveHr = new ReceiveHrAppImpl();
	//	User user = iReceiveHr.getUserInfo(userName);
		User user =iReceiveHr.getUserInfoByUserRoles(userName);
		user.setIp(iReceiveHr.getIP(request));

		userName=userName.toUpperCase();
//		System.out.println(ssoUser);
		// 将当前登录人的信息存入缓存中
		HttpSession session = request.getSession();
		session.setAttribute(OptimusAuthManager.LOGIN_NAME, userName);
		session.setAttribute(OptimusAuthManager.USER, user);
		
	}
	/**
	 * 处理用户登录看是否用加后缀
	 * @param username
	 * @param ssoUser 
	 * @return
	 */
	private String dualUserName(String username, SSOUser ssoUser){
		ssoUser.setUsername(StringUtils.upperCase(ssoUser.getUsername())+"@"+StringUtils.upperCase(ssoUser.getAdDomain()));
		username=StringUtils.upperCase(ssoUser.getUsername());
		return username;
		
/*		Properties properties = ConfigManager.getProperties("common");
		String nosuffixuser = properties.getProperty("ids.login.no.suffix");
		String suffix= properties.getProperty("ids.login.suffix"); //从配置文件中获取
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
			if(!StringUtils.isEmpty(suffix)){
				username=username+suffix;
			}
			return username.toUpperCase();
		}*/
	}

}
