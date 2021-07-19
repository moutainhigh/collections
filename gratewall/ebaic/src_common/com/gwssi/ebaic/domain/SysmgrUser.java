package com.gwssi.ebaic.domain;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Table;

/**
 * 审核平台用户BO。
 * 
 * @author xupeng
 */
@Entity
@Table(name = "VIEW_SYSMGR_USER")
public class SysmgrUser {
	
	private String userId;
	private String orgCodeFk;
	private String userName;
	private String staffNo;
	private String caCertId ;
	private String signPicUrl;
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(name = "ORG_CODE_FK")
	public String getOrgCodeFk() {
		return orgCodeFk;
	}
	public void setOrgCodeFk(String orgCodeFk) {
		this.orgCodeFk = orgCodeFk;
	}
	@Column(name = "USER_NAME")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "STAFF_NO")
	public String getStaffNo() {
		return staffNo;
	}
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}
	
	@Column(name = "CA_CERT_ID")
    public String getCaCertId() {
		return caCertId;
	}
	public void setCaCertId(String caCertId) {
		this.caCertId = caCertId;
	}
	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}
	@Column(name = "SIGN_PIC_URL")
    public String getSignPicUrl() {
        return signPicUrl;
    }
    public void setSignPicUrl(String signPicUrl) {
        this.signPicUrl = signPicUrl;
    }
	
}
