package com.gwssi.ebaic.domain;

import java.util.Calendar;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * 身份认证信息表。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Entity
@Table(name="sysmgr_identity")
public class SysmgrIdentityBO extends AbsDaoBussinessObject implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1164052703547614643L;
	private String identityId;
	private String type;
	private String name;
	private String cerType;
	private String cerNo;
	private String mobile;
	private String approveUserId;
	private String approveUserName;
	private Calendar approveDate;
	private String approveMsg;
	private String regOrg;
	private String appUserId;
	private String flag;
	private Calendar timestamp;
	private Calendar createTime;
	
	@Id
	@Column(name="IDENTITY_ID")
	public String getIdentityId() {
		return identityId;
	}
	public void setIdentityId(String identityId) {
		this.identityId = identityId;
		markChange("identityId",identityId);
	}
	@Column(name="TYPE")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
		markChange("type",type);
	}
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		markChange("name",name);
	}
	@Column(name="CER_TYPE")
	public String getCerType() {
		return cerType;
	}
	public void setCerType(String cerType) {
		this.cerType = cerType;
		markChange("cerType",cerType);
	}
	@Column(name="CER_NO")
	public String getCerNo() {
		return cerNo;
	}
	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
		markChange("cerNo",cerNo);
	}
	@Column(name="MOBILE")
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
		markChange("mobile",mobile);
	}
	@Column(name="APPROVE_USER_ID")
	public String getApproveUserId() {
		return approveUserId;
	}
	public void setApproveUserId(String approveUserId) {
		this.approveUserId = approveUserId;
		markChange("approveUserId",approveUserId);
	}
	@Column(name="APPROVE_USER_NAME")
	public String getApproveUserName() {
		return approveUserName;
	}
	public void setApproveUserName(String approveUserName) {
		this.approveUserName = approveUserName;
		markChange("approveUserName",approveUserName);
	}
	@Temporal(TemporalType.DATE)
	@Column(name="APPROVER_DATE")
	public Calendar getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Calendar approveDate) {
		this.approveDate = approveDate;
		markChange("approveDate",approveDate);
	}
	@Column(name="APPROVE_MSG")
	public String getApproveMsg() {
		return approveMsg;
	}
	public void setApproveMsg(String approveMsg) {
		this.approveMsg = approveMsg;
		markChange("approveMsg",approveMsg);
	}
	@Column(name="REG_ORG")
	public String getRegOrg() {
		return regOrg;
	}
	public void setRegOrg(String regOrg) {
		this.regOrg = regOrg;
		markChange("regOrg",regOrg);
	}
	@Column(name="APP_USER_ID")
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
		markChange("appUserId",appUserId);
	}
	@Column(name="FLAG")
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
		markChange("flag",flag);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TIMESTAMP")
	public Calendar getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Calendar timestamp) {
		this.timestamp = timestamp;
		markChange("timestamp",timestamp);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_TIME")
	public Calendar getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
		markChange("createTime",createTime);
	}
	
}
