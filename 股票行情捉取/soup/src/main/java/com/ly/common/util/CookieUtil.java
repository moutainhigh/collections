package com.ly.common.util;

import com.ly.pojo.Account;

public class CookieUtil {

	public static String getCookie(Account ac){
		//String cookie = "ps_login_token_id=" + DESUtils.getDecryptString(ac.getTokenId()) + ";ps_login_app_name=" + DESUtils.getDecryptString(ac.getAppName())+";ps_login_union_id=" + DESUtils.getDecryptString(ac.getUnionId());
	//	String cookie = "ps_login_token_id=" + DESUtils.getDecryptString(ac.getTokenId()) + ";ps_login_app_name=" + DESUtils.getDecryptString(ac.getAppName());
		String cookie = "ps_login_token_id=" + ac.getTokenId() + ";ps_login_app_name=" + ac.getAppName();
		return cookie;
	}
	
}
