package com.gwssi.ebaic.mobile.domain;

import com.gwssi.rodimus.rpc.RpcBaseBo;

public class QuickMsgBo extends RpcBaseBo {
	
	private static final long serialVersionUID = 2200489210209511370L;
	private String msgId = "";
	private String userId = "";
	private String moduleId = "";
	private String toModuleId = "";
	private String message = "";
	private String startTime = "";
	private String entTime = "";
	private String flag = "";
	
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return 在哪个模块/页面展示
	 */
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	/**
	 * 点击后跳转到 哪个模块/页面
	 * @return
	 */
	public String getToModuleId() {
		return toModuleId;
	}
	public void setToModuleId(String toModuleId) {
		this.toModuleId = toModuleId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEntTime() {
		return entTime;
	}
	public void setEntTime(String entTime) {
		this.entTime = entTime;
	}
	/**
	 * 
	 * @return 状态，1-正常，0-失效
	 */
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
