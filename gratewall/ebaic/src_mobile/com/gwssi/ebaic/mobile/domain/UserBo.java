package com.gwssi.ebaic.mobile.domain;

import com.gwssi.rodimus.rpc.RpcBaseBo;

/**
 * <h2>用户Bo</h2>
 * 
 * <p>所有属性都为字符串类型。</p>
 * <p>避免浮点数误差和日期格式化问题。</p>
 * 
 * @author liuxiangqian 
 */
public class UserBo extends RpcBaseBo {
	
	private static final long serialVersionUID = 3197795383558189003L;
	
	private String userId;	
	private String loginName;	
	private String userPwd;	
	private String userName;
	private String sex;	
	private String telphone;	
	private String email;	
	private String mobile;	
	private String pwdQuestion;
	private String pwdAnswer;
	
	private String cerNo;
	private String address;
	private String zipCode;
	
	public String getPwdQuestion() {
		return pwdQuestion;
	}
	public void setPwdQuestion(String pwdQuestion) {
		this.pwdQuestion = pwdQuestion;
	}
	public String getPwdAnswer() {
		return pwdAnswer;
	}
	public void setPwdAnswer(String pwdAnswer) {
		this.pwdAnswer = pwdAnswer;
	}
	public String getCerNo() {
		return cerNo;
	}
	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return 用户编号，由服务器端生成，用户注册时不需要传。
	 */
	public String getUserId(){
		return userId;
	}
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getLoginName(){
		return loginName;
	}
	public void setLoginName(String loginName){
		this.loginName = loginName;
	}
	public String getUserPwd(){
		return userPwd;
	}
	public void setUserPwd(String userPwd){
		this.userPwd = userPwd;
	}
	public String getUserName(){
		return userName;
	}
	public void setUserName(String userName){
		this.userName = userName;
	}
	public String getSex(){
		return sex;
	}
	public void setSex(String sex){
		this.sex = sex;
	}
	public String getTelphone(){
		return telphone;
	}
	public void setTelphone(String telphone){
		this.telphone = telphone;
	}
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getMobile(){
		return mobile;
	}

}
