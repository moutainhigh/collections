package com.gwssi.application.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import java.util.Calendar;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import com.gwssi.optimus.core.persistence.annotation.Lob;
import java.math.BigDecimal;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;

/**
 * SM_FLOW_LOG表对应的实体类
 */
@Entity
@Table(name = "SM_FLOW_LOG")
public class SmFlowLogBO extends AbsDaoBussinessObject {
	
	public SmFlowLogBO(){}

	private String pkFlowLog;	
	private String processId;	
	private String processName;	
	private String activityId;	
	private String activityName;	
	private String businessId;	
	private String businessName;	
	private String participantId;	
	private String participantName;	
	private Calendar startTime;	
	private Calendar endTime;	
	private BigDecimal timeLimit;	
	private String activityState;	
	
	@Id
	@Column(name = "PK_FLOW_LOG")
	public String getPkFlowLog(){
		return pkFlowLog;
	}
	public void setPkFlowLog(String pkFlowLog){
		this.pkFlowLog = pkFlowLog;
		markChange("pkFlowLog", pkFlowLog);
	}
	@Column(name = "PROCESS_ID")
	public String getProcessId(){
		return processId;
	}
	public void setProcessId(String processId){
		this.processId = processId;
		markChange("processId", processId);
	}
	@Column(name = "PROCESS_NAME")
	public String getProcessName(){
		return processName;
	}
	public void setProcessName(String processName){
		this.processName = processName;
		markChange("processName", processName);
	}
	@Column(name = "ACTIVITY_ID")
	public String getActivityId(){
		return activityId;
	}
	public void setActivityId(String activityId){
		this.activityId = activityId;
		markChange("activityId", activityId);
	}
	@Column(name = "ACTIVITY_NAME")
	public String getActivityName(){
		return activityName;
	}
	public void setActivityName(String activityName){
		this.activityName = activityName;
		markChange("activityName", activityName);
	}
	@Column(name = "BUSINESS_ID")
	public String getBusinessId(){
		return businessId;
	}
	public void setBusinessId(String businessId){
		this.businessId = businessId;
		markChange("businessId", businessId);
	}
	@Column(name = "BUSINESS_NAME")
	public String getBusinessName(){
		return businessName;
	}
	public void setBusinessName(String businessName){
		this.businessName = businessName;
		markChange("businessName", businessName);
	}
	@Column(name = "PARTICIPANT_ID")
	public String getParticipantId(){
		return participantId;
	}
	public void setParticipantId(String participantId){
		this.participantId = participantId;
		markChange("participantId", participantId);
	}
	@Column(name = "PARTICIPANT_NAME")
	public String getParticipantName(){
		return participantName;
	}
	public void setParticipantName(String participantName){
		this.participantName = participantName;
		markChange("participantName", participantName);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_TIME")
	public Calendar getStartTime(){
		return startTime;
	}
	public void setStartTime(Calendar startTime){
		this.startTime = startTime;
		markChange("startTime", startTime);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_TIME")
	public Calendar getEndTime(){
		return endTime;
	}
	public void setEndTime(Calendar endTime){
		this.endTime = endTime;
		markChange("endTime", endTime);
	}
	@Column(name = "TIME_LIMIT")
	public BigDecimal getTimeLimit(){
		return timeLimit;
	}
	public void setTimeLimit(BigDecimal timeLimit){
		this.timeLimit = timeLimit;
		markChange("timeLimit", timeLimit);
	}
	@Column(name = "ACTIVITY_STATE")
	public String getActivityState(){
		return activityState;
	}
	public void setActivityState(String activityState){
		this.activityState = activityState;
		markChange("activityState", activityState);
	}
}