package com.gwssi.common.rodimus.report.util;


import org.apache.commons.lang.StringUtils;

import com.gwssi.common.rodimus.report.entity.SysmgrUser;


public class CurrentUserUtil {

	public static SysmgrUser get(){
		SysmgrUser user = (SysmgrUser)SessionUtil.get("USER");
		if(user==null){
			throw new RuntimeException("登录超时，请重新登录。");
		}
		return user;
	}
	
	/**
	 * @return 当前登录用户所在的工商机关编号。
	 */
	public static String getOrgCode(){
		SysmgrUser user = get();
		if(user==null){
			throw new RuntimeException("登录超时，请重新登录。");
		}
		String ret = user.getOrgCodeFk();
		if(StringUtils.isEmpty(ret)){
			throw new RuntimeException("当前登录用户不是工商人员，没有权限执行当前操作。");
		}
		return ret;
	}
	
	/**
	 * 保存用户级别到Session中，以USER_TYPE为key。
	 * 1表示市局用户，2表示分局用户，3表示
	 */
	public static void saveUserType(){
		String userOrgCode = getOrgCode();
		if(StringUtils.isEmpty(userOrgCode)){
			throw new RuntimeException("当前登录用户不是工商人员，没有权限执行当前操作。");
		}
		SessionUtil.set("rptFullUserOrg", userOrgCode);
		if(userOrgCode.length()>=6){
			userOrgCode = userOrgCode.substring(0, 6);
		}
		SessionUtil.set("rptUserOrg", userOrgCode);
	}
}
