package com.gwssi.ebaic.apply.entaccount.domain;


import java.io.Serializable;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;

@Entity
@Table(name = "cp_account_manager")
public class EntAccountManagerBo implements Serializable {

	private static final long serialVersionUID = -9171324406169853216L;

	private String managerId;
	
	private String accountId;
	
	private String name;
	
	private String cerNo;
	
	private String mobile;
	
	private String role;
	
	private String operation;
	
	private String accState;
	
	@Id
	@Column(name = "manager_id")
	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	@Column(name = "account_id")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "cer_no")
	public String getCerNo() {
		return cerNo;
	}

	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}
	@Column(name = "mobile")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column(name = "role")
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	@Column(name = "operation")
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	@Column(name = "acc_state")
	public String getAccState() {
		return accState;
	}

	public void setAccState(String accState) {
		this.accState = accState;
	}
	
}
