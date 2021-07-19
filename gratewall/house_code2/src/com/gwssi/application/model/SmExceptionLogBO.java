package com.gwssi.application.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import java.util.Calendar;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import com.gwssi.optimus.core.persistence.annotation.Lob;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;

/**
 * SM_EXCEPTION_LOG表对应的实体类
 */
@Entity
@Table(name = "SM_EXCEPTION_LOG")
public class SmExceptionLogBO extends AbsDaoBussinessObject {
	
	public SmExceptionLogBO(){}

	private String pkExceptionLog;	
	private String createrId;	
	private String clientType;	
	private String clientIp;	
	private Calendar operationTime;	
	private String systemCode;	
	private String functionCode;	
	private String operationType;	
	private String operationState;	
	private String exceptionCode;	
	private String exceptionDesc;	
	
	@Id
	@Column(name = "PK_EXCEPTION_LOG")
	public String getPkExceptionLog(){
		return pkExceptionLog;
	}
	public void setPkExceptionLog(String pkExceptionLog){
		this.pkExceptionLog = pkExceptionLog;
		markChange("pkExceptionLog", pkExceptionLog);
	}
	@Column(name = "CREATER_ID")
	public String getCreaterId(){
		return createrId;
	}
	public void setCreaterId(String createrId){
		this.createrId = createrId;
		markChange("createrId", createrId);
	}
	@Column(name = "CLIENT_TYPE")
	public String getClientType(){
		return clientType;
	}
	public void setClientType(String clientType){
		this.clientType = clientType;
		markChange("clientType", clientType);
	}
	@Column(name = "CLIENT_IP")
	public String getClientIp(){
		return clientIp;
	}
	public void setClientIp(String clientIp){
		this.clientIp = clientIp;
		markChange("clientIp", clientIp);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OPERATION_TIME")
	public Calendar getOperationTime(){
		return operationTime;
	}
	public void setOperationTime(Calendar operationTime){
		this.operationTime = operationTime;
		markChange("operationTime", operationTime);
	}
	@Column(name = "SYSTEM_CODE")
	public String getSystemCode(){
		return systemCode;
	}
	public void setSystemCode(String systemCode){
		this.systemCode = systemCode;
		markChange("systemCode", systemCode);
	}
	@Column(name = "FUNCTION_CODE")
	public String getFunctionCode(){
		return functionCode;
	}
	public void setFunctionCode(String functionCode){
		this.functionCode = functionCode;
		markChange("functionCode", functionCode);
	}
	@Column(name = "OPERATION_TYPE")
	public String getOperationType(){
		return operationType;
	}
	public void setOperationType(String operationType){
		this.operationType = operationType;
		markChange("operationType", operationType);
	}
	@Column(name = "OPERATION_STATE")
	public String getOperationState(){
		return operationState;
	}
	public void setOperationState(String operationState){
		this.operationState = operationState;
		markChange("operationState", operationState);
	}
	@Column(name = "EXCEPTION_CODE")
	public String getExceptionCode(){
		return exceptionCode;
	}
	public void setExceptionCode(String exceptionCode){
		this.exceptionCode = exceptionCode;
		markChange("exceptionCode", exceptionCode);
	}
	@Column(name = "EXCEPTION_DESC")
	public String getExceptionDesc(){
		return exceptionDesc;
	}
	public void setExceptionDesc(String exceptionDesc){
		this.exceptionDesc = exceptionDesc;
		markChange("exceptionDesc", exceptionDesc);
	}
}