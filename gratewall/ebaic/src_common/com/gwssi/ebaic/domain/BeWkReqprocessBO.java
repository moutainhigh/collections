package com.gwssi.ebaic.domain;

import java.io.Serializable;
import java.util.Calendar;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;


/**
 * 业务审批记录表。
 * 
 * @author shigaozhan
 */
@Entity // 实体注解
@Table(name = "BE_WK_REQPROCESS") // 映射注解
public class BeWkReqprocessBO extends AbsDaoBussinessObject implements Serializable{
	
	private static final long serialVersionUID = -3982732395463603166L;
	
		//字段
		private String reqprocessId;
		private String requisitionId;
		private Calendar processDate;
		private String userId;
		private String userName;
		private String processStep;
		private String processNotion;
		private String processResult;
		private Calendar proEndDate;
		private String state;
		private String regOrg;
		private String gid;
		private Calendar timestamp;
		
		
		//属性
		@Id
		@Column(name="REQPROCESS_ID")
		public String getReqprocessId() {
			return reqprocessId;
		}
		public void setReqprocessId(String reqprocessId) {
			this.reqprocessId = reqprocessId;
			markChange("reqprocessId",reqprocessId);
		}
		
		@Column(name="REQUISITION_ID")
		public String getRequisitionId() {
			return requisitionId;
		}
		public void setRequisitionId(String requisitionId) {
			this.requisitionId = requisitionId;
		}
		
		@Column(name="PROCESS_DATE")
		public Calendar getProcessDate() {
			return processDate;
		}
		public void setProcessDate(Calendar processDate) {
			this.processDate = processDate;
		}
		
		@Column(name="USER_ID")
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		
		@Column(name="USER_NAME")
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		
		@Column(name="PROCESS_STEP")
		public String getProcessStep() {
			return processStep;
		}
		public void setProcessStep(String processStep) {
			this.processStep = processStep;
		}
		
		@Column(name="PROCESS_NOTION")
		public String getProcessNotion() {
			return processNotion;
		}
		public void setProcessNotion(String processNotion) {
			this.processNotion = processNotion;
		}
		
		@Column(name="PROCESS_RESULT")
		public String getProcessResult() {
			return processResult;
		}
		public void setProcessResult(String processResult) {
			this.processResult = processResult;
		}
		
		@Column(name="PRO_END_DATE")
		public Calendar getProEndDate() {
			return proEndDate;
		}
		public void setProEndDate(Calendar proEndDate) {
			this.proEndDate = proEndDate;
		}
		
		@Column(name="STATE")
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		
		@Column(name="REG_ORG")
		public String getRegOrg() {
			return regOrg;
		}
		public void setRegOrg(String regOrg) {
			this.regOrg = regOrg;
		}
		
		@Column(name="GID")
		public String getGid() {
			return gid;
		}
		public void setGid(String gid) {
			this.gid = gid;
		}
		
		@Column(name="TIMESTAMP")
		public Calendar getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(Calendar timestamp) {
			this.timestamp = timestamp;
		}
		
}
