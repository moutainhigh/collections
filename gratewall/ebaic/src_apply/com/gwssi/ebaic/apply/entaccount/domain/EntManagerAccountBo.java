package com.gwssi.ebaic.apply.entaccount.domain;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;


/**
 * 对应于操作管理员的POJO实体类，用于增，删，改，查询用Torch提供
 * @author ye
 *
 */
@Entity
@Table(name = "CP_ACCOUNT")
public class EntManagerAccountBo {

	private String managerId;
	private String accountId;
	private String name;
	private String cerNo;
	private String mobile;
	private String role;
	private String operation;
	private String accState;

	public EntManagerAccountBo() {
		super();
	}

	public EntManagerAccountBo(String managerId) {
		super();
		this.managerId = managerId;
	}

	@Id
	@Column(name = "MANAGER_ID")
	public String getManagerId() {
		return managerId;
	}

	@Column(name = "ACCOUNT_ID")
	public String getAccountId() {
		return accountId;
	}

	@Column(name = "ACC_STATE")
	public String getAccState() {
		return accState;
	}

	@Column(name = "CER_NO")
	public String getCerNo() {
		return cerNo;
	}

	@Column(name = "MOBILE")
	public String getMobile() {
		return mobile;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	@Column(name = "OPERATION")
	public String getOperation() {
		return operation;
	}

	@Column(name = "ROLE")
	public String getRole() {
		return role;
	}

	// set
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setAccState(String accState) {
		this.accState = accState;
	}

	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
