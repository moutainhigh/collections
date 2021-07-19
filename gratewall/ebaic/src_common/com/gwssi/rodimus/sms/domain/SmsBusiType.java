package com.gwssi.rodimus.sms.domain;

/**
 * 短信业务类型。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public enum SmsBusiType {
	
	/**
	 * 验证码
	 */
	VER_CODE("1","10","验证码"),
	/**
	 * 核准通过：企业账号
	 */
	APPROVE_ENT_ACCOUNT("2","02","核准通过：企业账号"),
	/**
	 * 核准通过：取照通知
	 */
	APPROVE_GET_CERTICATE("3","03","核准通过：取照通知"),
	/**
	 * 审核意见：退回修改
	 */
	CHECK_MSG("4","04","审核意见：退回修改"),
	/**
	 * 法人确认提醒
	 */
	LE_MAKE_SURE_NOTICE("5","05","法人确认提醒"),
	/**
	 * 现场身份认证：密码
	 */
	IDENTITY_MSG("6","06","现场身份认证：密码"),
	/**
	 * 审核意见：驳回
	 */
	CHECK_REJECT("7","07","审核意见：驳回"),
	/**
	 * 取照提醒
	 */
	LIC_GET_NOTICE("8","08","取照提醒");
	
	private String templateId ;
	private String code ;
	private String text ;
	
	SmsBusiType(String templateId, String code,String text){
		this.templateId = templateId ;
		this.code = code ;
		this.text = text ;
	}
	
	public String getTemplateId(){
		return templateId;
	}
	public String getCode(){
		return code;
	}
	public String toString(){
		return code + ":" + text ;
	}
}
