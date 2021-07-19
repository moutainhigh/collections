package com.gwssi.ebaic.apply.entaccount.interfac.api;

import java.util.Map;

/**
 * 信用网接口。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public interface EntAuthCreditService {
	
	/**
	 * 是否有显示认证按钮。
	 * 
	 * @param regNo
	 * @return
	 */
	public boolean isECert(String regNo);
	
	/**
	 * 获取服务码。
	 * 
	 * @param regNo 注册号或统一社会信用代码。
	 * @return Map中，key为serverCode取到服务码；key为startTime取到开始时间。
	 */
	public Map<String,String> getServerCode(String regNo);
	
	/**
	 * 
	 * @param authCode
	 * @param serverCode
	 * @return Map中，key为authEntName取到企业名称；authFlag为认证结果， 0 为成功 1为失败 2为已超时。
	 */
	public Map<String,String> checkEntAuth(String authCode, String serverCode);

}
