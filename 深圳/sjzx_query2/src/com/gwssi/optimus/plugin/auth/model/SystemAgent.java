package com.gwssi.optimus.plugin.auth.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * T_PT_YH表对应的实体类
 */
@Entity
@Table(name = "SYSTEM_AGENT")
public class SystemAgent extends AbsDaoBussinessObject {
	
	public SystemAgent(){}

	private String facilityId;	
	private String logonName;	
	private String password;	
	private String facilityName;	
	private String agentRegNo;	
	private String contactMan;	
	private String tel;	
	private String email;	
	private String address;	
	private String userStatus;	
	private String ableFlag;	
	private String opeId;	
	private String opeDateTime;
	
	@Id
	@Column(name = "FACILITY_ID")
	public String getFacilityId(){
		return facilityId;
	}
	public void setFacilityId(String facilityId){
		this.facilityId = facilityId;
		markChange("facilityId", facilityId);
	}
	@Column(name = "LOGON_NAME")
	public String getLogonName(){
		return logonName;
	}
	public void setLogonName(String logonName){
		this.logonName = logonName;
		markChange("logonName", logonName);
	}
	@Column(name = "PASSWORD")
	public String getPassword(){
		return password;
	}
	public void setPassword(String password){
		this.password = password;
		markChange("password", password);
	}
	@Column(name = "FACILITY_NAME")
	public String getFacilityName(){
		return facilityName;
	}
	public void setFacilityName(String facilityName){
		this.facilityName = facilityName;
		markChange("facilityName", facilityName);
	}
	@Column(name = "AGENT_REG_NO")
	public String getAgentRegNo(){
		return agentRegNo;
	}
	public void setAgentRegNo(String agentRegNo){
		this.agentRegNo = agentRegNo;
		markChange("agentRegNo", agentRegNo);
	}
	@Column(name = "CONTACT_MAN")
	public String getContactMan(){
		return contactMan;
	}
	public void setContactMan(String contactMan){
		this.contactMan = contactMan;
		markChange("contactMan", contactMan);
	}
	@Column(name = "TEL")
	public String getTel(){
		return tel;
	}
	public void setTel(String tel){
		this.tel = tel;
		markChange("tel", tel);
	}
	@Column(name = "EMAIL")
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
		markChange("email", email);
	}
	@Column(name = "ADDRESS")
	public String getAddress(){
		return address;
	}
	public void setAddress(String address){
		this.address = address;
		markChange("address", address);
	}
	@Column(name = "USERSTATUS")
	public String getUserStatus(){
		return userStatus;
	}
	public void setUserStatus(String userStatus){
		this.userStatus = userStatus;
		markChange("userStatus", userStatus);
	}
	@Column(name = "ABLEFLAG")
	public String getAbleFlag(){
		return ableFlag;
	}
	public void setAbleFlag(String ableFlag){
		this.ableFlag = ableFlag;
		markChange("ableFlag", ableFlag);
	}
	@Column(name = "OPEID")
	public String getOpeId(){
		return opeId;
	}
	public void setOpeId(String opeId){
		this.opeId = opeId;
		markChange("opeId", opeId);
	}
	@Column(name = "OPEDATETIME")
	public String getOpeDateTime(){
		return opeDateTime;
	}
	public void setOpeDateTime(String opeDateTime){
		this.opeDateTime = opeDateTime;
		markChange("opeDateTime", opeDateTime);
	}
}