//package com.gwssi.ebaic.apply.util;
//
//import com.alibaba.fastjson.JSON;
//import com.gwssi.rodimus.rule.RuleUtil;
//import com.gwssi.rodimus.validate.msg.ValidateMsg;
//
//
///**
// * 检查在办业务。
// * 
// * @author liuhailong(liuhailong2008#foxmail.com)
// */
//public class BusiInProcessUtil {
//	/**
//	 * 名称在办业务入口校验Code。
//	 */
//	public static final String RULE_APP_CODE_BUSI_INPROCESS = "ebaic_Busi_InProcess";
//	
//	/**
//	 * 名称在办业务查询。
//	 * 
//	 * @param nameAppId
//	 * @param serialNo
//	 */
//	public static void nameBipCheck(String nameId){
//		ValidateMsg msg = RuleUtil.runApp(RULE_APP_CODE_BUSI_INPROCESS, nameId);
//		if(!msg.isEmpty()){
//			throw new EBaicException(JSON.toJSONString(msg.getAllMsg()));
//		}
//	}
//}
