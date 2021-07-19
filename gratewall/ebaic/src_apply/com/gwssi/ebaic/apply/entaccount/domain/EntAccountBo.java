package com.gwssi.ebaic.apply.entaccount.domain;

import java.io.Serializable;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;

@Entity
@Table(name = "CP_ACCOUNT")
public class EntAccountBo implements Serializable{

	private static final long serialVersionUID = -5171581143859876663L;

	private String accountId;
	
	private String RsEntId;
	
	private String uniScid;
	
	private String entName;
	
	private String accountPwd;
	
	private String accoutState;
	
	private String legName;
	
	private String legCerType;
	
	private String legCerNo;
	
	private String legMobile;
	
	private String legIdentityState;
	
	private String gid;
	
	@Id
	@Column(name = "ACCOUNT_ID")
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Column(name = "RS_ENT_ID")
	public String getRsEntId() {
		return RsEntId;
	}

	public void setRsEntId(String rsEntId) {
		RsEntId = rsEntId;
	}

	@Column(name = "UNI_SCID")
	public String getUniScid() {
		return uniScid;
	}

	public void setUniScid(String uniScid) {
		this.uniScid = uniScid;
	}

	@Column(name = "ENT_NAME")
	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	@Column(name = "ACCOUNT_PWD")
	public String getAccountPwd() {
		return accountPwd;
	}

	public void setAccountPwd(String accountPwd) {
		this.accountPwd = accountPwd;
	}

	@Column(name = "accout_state")
	public String getAccoutState() {
		return accoutState;
	}

	public void setAccoutState(String accoutState) {
		this.accoutState = accoutState;
	}

	@Column(name = "LEG_NAME")
	public String getLegName() {
		return legName;
	}

	public void setLegName(String legName) {
		this.legName = legName;
	}

	@Column(name = "LEG_CER_TYPE")
	public String getLegCerType() {
		return legCerType;
	}

	public void setLegCerType(String legCerType) {
		this.legCerType = legCerType;
	}

	@Column(name = "LEG_CER_NO")
	public String getLegCerNo() {
		return legCerNo;
	}

	public void setLegCerNo(String legCerNo) {
		this.legCerNo = legCerNo;
	}

	@Column(name = "LEG_MOBILE")
	public String getLegMobile() {
		return legMobile;
	}

	public void setLegMobile(String legMobile) {
		this.legMobile = legMobile;
	}

	@Column(name = "LEG_IDENTITY_STATE")
	public String getLegIdentityState() {
		return legIdentityState;
	}

	public void setLegIdentityState(String legIdentityState) {
		this.legIdentityState = legIdentityState;
	}

	@Column(name = "GID")
	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}
	
	
	
}
