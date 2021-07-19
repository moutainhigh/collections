package com.gwssi.ebaic.apply.util;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.gwssi.rodimus.exception.EBaicException;
import com.gwssi.rodimus.rule.RuleUtil;
import com.gwssi.rodimus.util.HttpSessionUtil;
import com.gwssi.rodimus.validate.msg.ValidateMsg;
import com.gwssi.rodimus.validate.msg.ValidateMsgEntity;

/**
 * 业务入口。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class EntranceUtil {
	
	/**
	 * 设立入口校验Code。
	 */
	public static final String RULE_APP_CODE_SETUP_ENTRANCE = "ebaic_setup_entrance";
	
	/**
	 * 执行入口校验规则
	 * @param params
	 */
	public static void cpCheckIn(String nameId){
		String userId = HttpSessionUtil.getCurrentUserId();
		if(StringUtils.isBlank(userId)){
			throw new EBaicException("登陆超时，请重新登陆");
		}
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("nameId", nameId);
		params.put("userId", userId);
		ValidateMsg msg = RuleUtil.getInstance().runApp(RULE_APP_CODE_SETUP_ENTRANCE, params);
		List<ValidateMsgEntity> errors =  msg.getAllMsg();
		if(errors!=null && !errors.isEmpty()){
			throw new EBaicException(msg.getAllMsgString());
		}
		
	}
}
