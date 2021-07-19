package com.gwssi.ebaic.domain;

import java.math.BigDecimal;
import java.util.Calendar;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * 工作库 - 申请单。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Entity
@Table(name = "BE_WK_REQUISITION")
public class BeWkReqBO extends AbsDaoBussinessObject{

	private String requisitionId;	
	private String entId;	
	private String cpEntId;	 
	private String nameId;	
	private String nameAppId;	
	private String operationType;	
	private String appForm;	
	private Calendar appDate;	
	private String agentRegNo;	
	private String agentName;	
	private BigDecimal copyNo;	
	private String linkman;	
	private String certType;	
	private String certNo;	
	private String tel;	
	private String mobTel;
	private String memo;	
	private String regOrg;	
	private String preStep;	
	private String curStep;	
	private BigDecimal flowId;	
	private String censorUserId;
	private String censorNo;
	private Calendar censorDate;	
	private String censorName;	
	private String censorNotion;	
	private String censorResult;	
	private Calendar approveDate;	
	private String approveNo;	
	private String approveUserId;
	private String approveName;	
	private String approveNotion;	
	private String approveResult;	
	private String apprize;	
	private String lockUser;	
	private String certReceiveForm;
	private String appUserId;
	private String submitUserId;
	private Calendar submitDate;
	private String state;	
	private String catId;	
	private String entName;
	private Calendar timestamp;		
	private String gid;	
	private String authType;
	private BigDecimal version;	
	


	
	@Column(name = "SUBMIT_DATE")
	public Calendar getSubmitDate(){
		return submitDate;
	}
	public void setSubmitDate(Calendar submitDate){
		this.submitDate = submitDate;
		markChange("submitDate", submitDate);
	}
	
	
	@Id
	@Column(name = "REQUISITION_ID")
	public String getRequisitionId(){
		return requisitionId;
	}
	public void setRequisitionId(String requisitionId){
		this.requisitionId = requisitionId;
		markChange("requisitionId", requisitionId);
	}
	
	@Column(name = "ENT_ID")
	public String getEntId(){
		return entId;
	}
	public void setEntId(String entId){
		this.entId = entId;
		markChange("entId", entId);
	}
	@Column(name = "CP_ENT_ID")
	public String getCpEntId(){
		return cpEntId;
	}
	public void setCpEntId(String cpEntId){
		this.cpEntId = cpEntId;
		markChange("cpEntId", cpEntId);
	}
	@Column(name = "NAME_ID")
	public String getNameId(){
		return nameId;
	}
	public void setNameId(String nameId){
		this.nameId = nameId;
		markChange("nameId", nameId);
	}
	@Column(name = "NAME_APP_ID")
	public String getNameAppId(){
		return nameAppId;
	}
	public void setNameAppId(String nameAppId){
		this.nameAppId = nameAppId;
		markChange("nameAppId", nameAppId);
	}
	@Column(name = "OPERATION_TYPE")
	public String getOperationType(){
		return operationType;
	}
	public void setOperationType(String operationType){
		this.operationType = operationType;
		markChange("operationType", operationType);
	}
	@Column(name = "APP_FORM")
	public String getAppForm(){
		return appForm;
	}
	public void setAppForm(String appForm){
		this.appForm = appForm;
		markChange("appForm", appForm);
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "APP_DATE")
	public Calendar getAppDate(){
		return appDate;
	}
	public void setAppDate(Calendar appDate){
		this.appDate = appDate;
		markChange("appDate", appDate);
	}
	@Column(name = "AGENT_REG_NO")
	public String getAgentRegNo(){
		return agentRegNo;
	}
	public void setAgentRegNo(String agentRegNo){
		this.agentRegNo = agentRegNo;
		markChange("agentRegNo", agentRegNo);
	}
	@Column(name = "AGENT_NAME")
	public String getAgentName(){
		return agentName;
	}
	public void setAgentName(String agentName){
		this.agentName = agentName;
		markChange("agentName", agentName);
	}
	@Column(name = "COPY_NO")
	public BigDecimal getCopyNo(){
		return copyNo;
	}
	public void setCopyNo(BigDecimal copyNo){
		this.copyNo = copyNo;
		markChange("copyNo", copyNo);
	}
	@Column(name = "LINKMAN")
	public String getLinkman(){
		return linkman;
	}
	public void setLinkman(String linkman){
		this.linkman = linkman;
		markChange("linkman", linkman);
	}
	@Column(name = "CERT_TYPE")
	public String getCertType(){
		return certType;
	}
	public void setCertType(String certType){
		this.certType = certType;
		markChange("certType", certType);
	}
	@Column(name = "CERT_NO")
	public String getCertNo(){
		return certNo;
	}
	public void setCertNo(String certNo){
		this.certNo = certNo;
		markChange("certNo", certNo);
	}
	@Column(name = "TEL")
	public String getTel(){
		return tel;
	}
	public void setTel(String tel){
		this.tel = tel;
		markChange("tel", tel);
	}
	@Column(name = "MOB_TEL")
	public String getMobTel(){
		return mobTel;
	}
	public void setMobTel(String mobTel){
		this.mobTel = mobTel;
		markChange("mobTel", mobTel);
	}
	
	@Column(name = "MEMO")
	public String getMemo(){
		return memo;
	}
	public void setMemo(String memo){
		this.memo = memo;
		markChange("memo", memo);
	}
	@Column(name = "REG_ORG")
	public String getRegOrg(){
		return regOrg;
	}
	public void setRegOrg(String regOrg){
		this.regOrg = regOrg;
		markChange("regOrg", regOrg);
	}
	@Column(name = "PRE_STEP")
	public String getPreStep(){
		return preStep;
	}
	public void setPreStep(String preStep){
		this.preStep = preStep;
		markChange("preStep", preStep);
	}
	@Column(name = "CUR_STEP")
	public String getCurStep(){
		return curStep;
	}
	public void setCurStep(String curStep){
		this.curStep = curStep;
		markChange("curStep", curStep);
	}
	@Column(name = "FLOW_ID")
	public BigDecimal getFlowId(){
		return flowId;
	}
	public void setFlowId(BigDecimal flowId){
		this.flowId = flowId;
		markChange("flowId", flowId);
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "CENSOR_DATE")
	public Calendar getCensorDate(){
		return censorDate;
	}
	public void setCensorDate(Calendar censorDate){
		this.censorDate = censorDate;
		markChange("censorDate", censorDate);
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "APPROVE_DATE")
	public Calendar getApproveDate(){
		return approveDate;
	}
	public void setApproveDate(Calendar approveDate){
		this.approveDate = approveDate;
		markChange("approveDate", approveDate);
	}
	@Column(name = "APPROVE_NAME")
	public String getApproveName(){
		return approveName;
	}
	public void setApproveName(String approveName){
		this.approveName = approveName;
		markChange("approveName", approveName);
	}
	@Column(name = "APPROVE_NOTION")
	public String getApproveNotion(){
		return approveNotion;
	}
	public void setApproveNotion(String approveNotion){
		this.approveNotion = approveNotion;
		markChange("approveNotion", approveNotion);
	}
	@Column(name = "APPROVE_RESULT")
	public String getApproveResult(){
		return approveResult;
	}
	public void setApproveResult(String approveResult){
		this.approveResult = approveResult;
		markChange("approveResult", approveResult);
	}
	@Column(name = "CENSOR_NAME")
	public String getCensorName(){
		return censorName;
	}
	public void setCensorName(String censorName){
		this.censorName = censorName;
		markChange("censorName", censorName);
	}
	@Column(name = "CENSOR_NOTION")
	public String getCensorNotion(){
		return censorNotion;
	}
	public void setCensorNotion(String censorNotion){
		this.censorNotion = censorNotion;
		markChange("censorNotion", censorNotion);
	}
	@Column(name = "CENSOR_RESULT")
	public String getCensorResult(){
		return censorResult;
	}
	public void setCensorResult(String censorResult){
		this.censorResult = censorResult;
		markChange("censorResult", censorResult);
	}
	@Column(name = "APPRIZE")
	public String getApprize(){
		return apprize;
	}
	public void setApprize(String apprize){
		this.apprize = apprize;
		markChange("apprize", apprize);
	}
	@Column(name = "LOCK_USER")
	public String getLockUser(){
		return lockUser;
	}
	public void setLockUser(String lockUser){
		this.lockUser = lockUser;
		markChange("lockUser", lockUser);
	}
	@Column(name = "GID")
	public String getGid(){
		return gid;
	}
	public void setGid(String gid){
		this.gid = gid;
		markChange("gid", gid);
	}
	@Column(name = "STATE")
	public String getState(){
		return state;
	}
	public void setState(String state){
		this.state = state;
		markChange("state", state);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TIMESTAMP")
	public Calendar getTimestamp(){
		return timestamp;
	}
	public void setTimestamp(Calendar timestamp){
		this.timestamp = timestamp;
		markChange("timestamp", timestamp);
	}
	
	@Column(name = "ENT_NAME")
	public String getEntName(){
		return entName;
	}
	public void setEntName(String entName){
		this.entName = entName;
		markChange("entName", entName);
	}

	@Column(name = "CERT_RECEIVE_FORM")
	public String getCertReceiveForm(){
		return certReceiveForm;
	}
	public void setCertReceiveForm(String certReceiveForm){
		this.certReceiveForm = certReceiveForm;
		markChange("certRecieveForm", certReceiveForm);
	}

	@Column(name = "CENSOR_NO")
	public String getCensorNo(){
		return censorNo;
	}
	
	public void setCensorNo(String censorNo){
		this.censorNo = censorNo;
		markChange("censorNo", censorNo);
	}
	@Column(name = "APPROVE_NO")
	public String getApproveNo(){
		return approveNo;
	}
	
	public void setApproveNo(String approveNo){
		this.approveNo = approveNo;
		markChange("approveNo", approveNo);
	}
	@Column(name = "CENSOR_USER_ID")
	public String getCensorUserId() {
		return censorUserId;
	}
	public void setCensorUserId(String censorUserId) {
		this.censorUserId = censorUserId;
		markChange("censorUserId", censorUserId);
	}
	@Column(name = "APPROVE_USER_ID")
	public String getApproveUserId() {
		return approveUserId;
	}
	public void setApproveUserId(String approveUserId) {
		this.approveUserId = approveUserId;
		markChange("approveUserId", approveUserId);
	}
	@Column(name = "APP_USER_ID")
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
		markChange("appUserId", appUserId);
	}
	@Column(name = "SUBMIT_USER_ID")
	public String getSubmitUserId() {
		return submitUserId;
	}
	public void setSubmitUserId(String submitUserId) {
		this.submitUserId = submitUserId;
		markChange("submitUserId", submitUserId);
	}
	@Column(name = "CAT_ID")
	public String getCatId() {
		return catId;
	}
	public void setCatId(String catId) {
		this.catId = catId;
		markChange("catId", catId);
	}
	@Column(name = "VERSION")
	public BigDecimal getVersion() {
		return version;
	}
	public void setVersion(BigDecimal version) {
		this.version = version;
		markChange("version", version);
	}
	@Column(name = "AUTH_TYPE")
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
		markChange("authType", authType);
	}
}
