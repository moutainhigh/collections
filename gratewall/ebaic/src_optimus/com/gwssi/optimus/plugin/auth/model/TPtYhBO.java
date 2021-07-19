package com.gwssi.optimus.plugin.auth.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import java.util.Calendar;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;

/**
 * T_PT_YH表对应的实体类
 */
@Entity
@Table(name = "T_PT_YH")
public class TPtYhBO extends AbsDaoBussinessObject {
	
	public TPtYhBO(){}

	private String userId;	
	private String orgId;	
	private String loginName;	
	private String userPwd;	
	private String userName;	
	private String zyJsId;	
	private String sex;	
	private String telphone;	
	private String email;	
	private String mobile;	
	private String pwdQuestion;	
	private String pwdAnswer;	
	private String idCard;	
	private String address;	
	private String zipCode;	
	private String countryCity;	
	private String cerType;	
	private String cerNo;	
	private String userType;	
	private String facilityId;	
	private Calendar createTime;	
	private String yxBj;	
	
	@Id
	@Column(name = "USER_ID")
	public String getUserId(){
		return userId;
	}
	public void setUserId(String userId){
		this.userId = userId;
		markChange("userId", userId);
	}
	@Column(name = "ORG_ID")
	public String getOrgId(){
		return orgId;
	}
	public void setOrgId(String orgId){
		this.orgId = orgId;
		markChange("orgId", orgId);
	}
	@Column(name = "LOGIN_NAME")
	public String getLoginName(){
		return loginName;
	}
	public void setLoginName(String loginName){
		this.loginName = loginName;
		markChange("loginName", loginName);
	}
	@Column(name = "USER_PWD")
	public String getUserPwd(){
		return userPwd;
	}
	public void setUserPwd(String userPwd){
		this.userPwd = userPwd;
		markChange("userPwd", userPwd);
	}
	@Column(name = "USER_NAME")
	public String getUserName(){
		return userName;
	}
	public void setUserName(String userName){
		this.userName = userName;
		markChange("userName", userName);
	}
	@Column(name = "ZY_JS_ID")
	public String getZyJsId(){
		return zyJsId;
	}
	public void setZyJsId(String zyJsId){
		this.zyJsId = zyJsId;
		markChange("zyJsId", zyJsId);
	}
	@Column(name = "SEX")
	public String getSex(){
		return sex;
	}
	public void setSex(String sex){
		this.sex = sex;
		markChange("sex", sex);
	}
	@Column(name = "TELPHONE")
	public String getTelphone(){
		return telphone;
	}
	public void setTelphone(String telphone){
		this.telphone = telphone;
		markChange("telphone", telphone);
	}
	@Column(name = "EMAIL")
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
		markChange("email", email);
	}
	@Column(name = "MOBILE")
	public String getMobile(){
		return mobile;
	}
	public void setMobile(String mobile){
		this.mobile = mobile;
		markChange("mobile", mobile);
	}
	@Column(name = "PWD_QUESTION")
	public String getPwdQuestion(){
		return pwdQuestion;
	}
	public void setPwdQuestion(String pwdQuestion){
		this.pwdQuestion = pwdQuestion;
		markChange("pwdQuestion", pwdQuestion);
	}
	@Column(name = "PWD_ANSWER")
	public String getPwdAnswer(){
		return pwdAnswer;
	}
	public void setPwdAnswer(String pwdAnswer){
		this.pwdAnswer = pwdAnswer;
		markChange("pwdAnswer", pwdAnswer);
	}
	@Column(name = "ID_CARD")
	public String getIdCard(){
		return idCard;
	}
	public void setIdCard(String idCard){
		this.idCard = idCard;
		markChange("idCard", idCard);
	}
	@Column(name = "ADDRESS")
	public String getAddress(){
		return address;
	}
	public void setAddress(String address){
		this.address = address;
		markChange("address", address);
	}
	@Column(name = "ZIP_CODE")
	public String getZipCode(){
		return zipCode;
	}
	public void setZipCode(String zipCode){
		this.zipCode = zipCode;
		markChange("zipCode", zipCode);
	}
	@Column(name = "COUNTRY_CITY")
	public String getCountryCity(){
		return countryCity;
	}
	public void setCountryCity(String countryCity){
		this.countryCity = countryCity;
		markChange("countryCity", countryCity);
	}
	@Column(name = "CER_TYPE")
	public String getCerType(){
		return cerType;
	}
	public void setCerType(String cerType){
		this.cerType = cerType;
		markChange("cerType", cerType);
	}
	@Column(name = "CER_NO")
	public String getCerNo(){
		return cerNo;
	}
	public void setCerNo(String cerNo){
		this.cerNo = cerNo;
		markChange("cerNo", cerNo);
	}
	@Column(name = "USER_TYPE")
	public String getUserType(){
		return userType;
	}
	public void setUserType(String userType){
		this.userType = userType;
		markChange("userType", userType);
	}
	@Column(name = "FACILITY_ID")
	public String getFacilityId(){
		return facilityId;
	}
	public void setFacilityId(String facilityId){
		this.facilityId = facilityId;
		markChange("facilityId", facilityId);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	public Calendar getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Calendar createTime){
		this.createTime = createTime;
		markChange("createTime", createTime);
	}
	@Column(name = "YX_BJ")
	public String getYxBj(){
		return yxBj;
	}
	public void setYxBj(String yxBj){
		this.yxBj = yxBj;
		markChange("yxBj", yxBj);
	}
}