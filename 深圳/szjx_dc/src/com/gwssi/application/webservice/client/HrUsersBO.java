package com.gwssi.application.webservice.client;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * HR_USERS表对应的实体类
 */
@Entity
@Table(name = "HR_USERS")
public class HrUsersBO extends AbsDaoBussinessObject {
	
	public HrUsersBO(){}

	private String userId;	
	private String userName;	
	private String organId;	
	private String groupId;	
	private String password;	
	private String accountStatus;	
	private String createTime;	
	private String lockTime;	
	private String expiredTime;	
	private String businessCode;	
	
	@Id
	@Column(name = "USER_ID")
	public String getUserId(){
		return userId;
	}
	public void setUserId(String userId){
		this.userId = userId;
		markChange("userId", userId);
	}
	@Column(name = "USER_NAME")
	public String getUserName(){
		return userName;
	}
	public void setUserName(String userName){
		this.userName = userName;
		markChange("userName", userName);
	}
	@Column(name = "ORGAN_ID")
	public String getOrganId(){
		return organId;
	}
	public void setOrganId(String organId){
		this.organId = organId;
		markChange("organId", organId);
	}
	@Column(name = "GROUP_ID")
	public String getGroupId(){
		return groupId;
	}
	public void setGroupId(String groupId){
		this.groupId = groupId;
		markChange("groupId", groupId);
	}
	@Column(name = "PASSWORD")
	public String getPassword(){
		return password;
	}
	public void setPassword(String password){
		this.password = password;
		markChange("password", password);
	}
	@Column(name = "ACCOUNT_STATUS")
	public String getAccountStatus(){
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus){
		this.accountStatus = accountStatus;
		markChange("accountStatus", accountStatus);
	}
	@Column(name = "CREATE_TIME")
	public String getCreateTime(){
		return createTime;
	}
	public void setCreateTime(String createTime){
		this.createTime = createTime;
		markChange("createTime", createTime);
	}
	@Column(name = "LOCK_TIME")
	public String getLockTime(){
		return lockTime;
	}
	public void setLockTime(String lockTime){
		this.lockTime = lockTime;
		markChange("lockTime", lockTime);
	}
	@Column(name = "EXPIRED_TIME")
	public String getExpiredTime(){
		return expiredTime;
	}
	public void setExpiredTime(String expiredTime){
		this.expiredTime = expiredTime;
		markChange("expiredTime", expiredTime);
	}
	@Column(name = "BUSINESS_CODE")
	public String getBusinessCode(){
		return businessCode;
	}
	public void setBusinessCode(String businessCode){
		this.businessCode = businessCode;
		markChange("businessCode", businessCode);
	}
}