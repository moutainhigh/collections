package com.gwssi.ebaic.mobile.api;

import com.gwssi.rodimus.rpc.ParameterName;

/**
 * <h2>法定代表人电话维护</h2>
 * 
 * @author liuxiangqian
 *
 */
public interface ElicLegMobileService {
	
	/**
	 * <h3>获取法定代表人手机号</h3>
	 * 
	 * @param accountId 
	 * @return mobile（不带星号）
	 */
	public String getLegMobile(@ParameterName(value="accountId")String accountId);
	
	/**
	 * <h3>保存法定代表人手机号</h3>
	 * 
	 * @param accountId : 企业账号ID
	 * @param oldMobile : 法定代表人原手机号
	 * @param oldMobileValCode ： 原手机号验证码
	 * @param newMobile ： 法定代表人新手机号
	 * @param newMobileValCode ： 新手机号验证码
	 */
	public void saveLegMobile(@ParameterName(value="accountId")String accountId,
			@ParameterName(value="oldMobile")String oldMobile,
			@ParameterName(value="oldMobileValCode")String oldMobileValCode,
			@ParameterName(value="newMobile")String newMobile,
			@ParameterName(value="newMobileValCode")String newMobileValCode);
}
