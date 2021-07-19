package com.gwssi.ebaic.mobile.api;

import com.gwssi.rodimus.rpc.ParameterName;

/**
 * 通用服务。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public interface CommonService {
	
	/**
	 * 获取手机校验码。
	 * 
	 * @param mobile 手机号码。
	 */
	public void sendMobileVerCode(@ParameterName(value="mobile")String mobile);
}
