package cn.gwssi.mian.model;


import java.util.Calendar;

/**
 * SM_EXCEPTION_LOG表对应的实体类
 */
public class SmExceptionLogBO  {
	
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
	
	public String getPkExceptionLog(){
		return pkExceptionLog;
	}
	public void setPkExceptionLog(String pkExceptionLog){
		this.pkExceptionLog = pkExceptionLog;
	}
	public String getCreaterId(){
		return createrId;
	}
	public void setCreaterId(String createrId){
		this.createrId = createrId;
	}
	public String getClientType(){
		return clientType;
	}
	public void setClientType(String clientType){
		this.clientType = clientType;
	}
	public String getClientIp(){
		return clientIp;
	}
	public void setClientIp(String clientIp){
		this.clientIp = clientIp;
	}
	public Calendar getOperationTime(){
		return operationTime;
	}
	public void setOperationTime(Calendar operationTime){
		this.operationTime = operationTime;
	}
	public String getSystemCode(){
		return systemCode;
	}
	public void setSystemCode(String systemCode){
		this.systemCode = systemCode;
	}
	public String getFunctionCode(){
		return functionCode;
	}
	public void setFunctionCode(String functionCode){
		this.functionCode = functionCode;
	}
	public String getOperationType(){
		return operationType;
	}
	public void setOperationType(String operationType){
		this.operationType = operationType;
	}
	public String getOperationState(){
		return operationState;
	}
	public void setOperationState(String operationState){
		this.operationState = operationState;
	}
	public String getExceptionCode(){
		return exceptionCode;
	}
	public void setExceptionCode(String exceptionCode){
		this.exceptionCode = exceptionCode;
	}
	public String getExceptionDesc(){
		return exceptionDesc;
	}
	public void setExceptionDesc(String exceptionDesc){
		this.exceptionDesc = exceptionDesc;
	}
}