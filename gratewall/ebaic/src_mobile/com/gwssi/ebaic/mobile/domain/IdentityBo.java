package com.gwssi.ebaic.mobile.domain;

import java.util.ArrayList;
import java.util.List;

import com.gwssi.rodimus.rpc.RpcBaseBo;

/**
 * 身份认证信息Bo。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class IdentityBo extends RpcBaseBo {
	
	private static final long serialVersionUID = 2641496398106619049L;
	
	private String identityId;
	private String name;
	private String cerType;
	private String cerNo;
	private String mobile;
	private String approveMsg;
	private String regOrg;
	private String flag;
	private String lockSign ;
	private String createTime;
	private String submitDate;
	private String approverDate;
	
	private String personSign = "1";
	
	private List<IdentityPictureBo> pictures = new ArrayList<IdentityPictureBo>();
	
	public String getIdentityId() {
		return identityId;
	}
	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	/**
	 * @return 姓名 或 企业名称
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return 证件类型。
	 */
	public String getCerType() {
		return cerType;
	}
	public void setCerType(String cerType) {
		this.cerType = cerType;
	}
	/**
	 * @return 证件号码，或企业统一社会信用代码/注册号。
	 */
	public String getCerNo() {
		return cerNo;
	}
	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}
	/**
	 * 
	 * @return 移动电话 或 企业联系人移动电话
	 */
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	public String getApproveMsg() {
		return approveMsg;
	}
	public void setApproveMsg(String approveMsg) {
		this.approveMsg = approveMsg;
	}
	public String getRegOrg() {
		return regOrg;
	}
	public void setRegOrg(String regOrg) {
		this.regOrg = regOrg;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	
	public String getLockSign() {
		return lockSign;
	}
	public void setLockSign(String lockSign) {
		this.lockSign = lockSign;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	public String getApproverDate() {
        return approverDate;
    }
    public void setApproverDate(String approverDate) {
        this.approverDate = approverDate;
    }
    public List<IdentityPictureBo> getPictures() {
		return pictures;
	}
	public void setPictures(List<IdentityPictureBo> pictures) {
		this.pictures = pictures;
	}
	/**
	 * 0-企业，1-个人
	 * @return
	 */
	public String getPersonSign() {
		return personSign;
	}
	public void setPersonSign(String personSign) {
		this.personSign = personSign;
	}
	
	
	
}
