package com.gwssi.rodimus.validate.msg;

import java.io.Serializable;

/**
 * 验证错误信息。
 * 
 * @author 海龙
 */

public class ValidateMsgEntity implements Serializable{

	private String msg ;
	private String name ;
	private String type ;
	private String ruleId ;
	private String ruleStepId ;
	private String errCode ;
	
	public void setRuleStepId(String ruleStepId) {
		this.ruleStepId = ruleStepId;
	}
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleStepId() {
		return ruleStepId;
	}
	public ValidateMsgEntity(){}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}


	private static final long serialVersionUID = 6406525568123485851L;
}
