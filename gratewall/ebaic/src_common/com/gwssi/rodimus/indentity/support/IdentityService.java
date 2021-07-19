package com.gwssi.rodimus.indentity.support;

import com.gwssi.rodimus.indentity.domain.IdentityCardBO;

/**
 * 公安部身份核查接口。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public interface IdentityService {
	/**
	 * 姓名和校验身份证号码。
	 * 
	 * @param name
	 * @param cerNo
	 * @return
	 */
	public IdentityCardBO validate(String name, String cerNo) ;
}
