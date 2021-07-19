package com.gwssi.ebaic.mobile.impl;

import org.springframework.stereotype.Service;

import com.gwssi.ebaic.mobile.api.CommonService;
import com.gwssi.rodimus.sms.SmsVerCodeUtil;

@Service(value="commonServiceImpl")
public class CommonServiceImpl implements CommonService {

	/**
	 * 发送手机校验码。
	 */
	public void sendMobileVerCode(String mobile) {
		SmsVerCodeUtil.send(mobile);
	}

}
