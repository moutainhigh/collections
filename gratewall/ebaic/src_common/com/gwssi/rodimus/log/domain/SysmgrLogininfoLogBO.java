package com.gwssi.rodimus.log.domain;

import java.util.Calendar;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * SYSMGR_LOGININFO_LOG表对应的实体类
 */
@Entity
@Table(name = "SYSMGR_LOGININFO_LOG")
public class SysmgrLogininfoLogBO extends AbsDaoBussinessObject {
	
	private String sessionId;	
	private String loginName;	
	private String userName;	
	private String userId;	
	private Calendar loginTime;	
	private String loginIp;	
	private Calendar exitTime;	
	private String serverIp;	
	private String id;	
	
	public SysmgrLogininfoLogBO(){
		
	}
	
	@Column(name = "SESSION_ID")
	public String getSessionId(){
		return sessionId;
	}
	public void setSessionId(String sessionId){
		this.sessionId = sessionId;
		markChange("sessionId", sessionId);
	}
	@Column(name = "LOGIN_NAME")
	public String getLoginName(){
		return loginName;
	}
	public void setLoginName(String loginName){
		this.loginName = loginName;
		markChange("loginName", loginName);
	}
	@Column(name = "USER_NAME")
	public String getUserName(){
		return userName;
	}
	public void setUserName(String userName){
		this.userName = userName;
		markChange("userName", userName);
	}
	@Column(name = "USER_ID")
	public String getUserId(){
		return userId;
	}
	public void setUserId(String userId){
		this.userId = userId;
		markChange("userId", userId);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOGIN_TIME")
	public Calendar getLoginTime(){
		return loginTime;
	}
	public void setLoginTime(Calendar loginTime){
		this.loginTime = loginTime;
		markChange("loginTime", loginTime);
	}
	@Column(name = "LOGIN_IP")
	public String getLoginIp(){
		return loginIp;
	}
	public void setLoginIp(String loginIp){
		this.loginIp = loginIp;
		markChange("loginIp", loginIp);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXIT_TIME")
	public Calendar getExitTime(){
		return exitTime;
	}
	public void setExitTime(Calendar exitTime){
		this.exitTime = exitTime;
		markChange("exitTime", exitTime);
	}
	@Column(name = "SERVER_IP")
	public String getServerIp(){
		return serverIp;
	}
	public void setServerIp(String serverIp){
		this.serverIp = serverIp;
		markChange("serverIp", serverIp);
	}
	@Id
	@Column(name = "ID")
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
		markChange("id", id);
	}
}