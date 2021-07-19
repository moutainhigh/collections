package cn.gwssi.common.model;

import java.io.Serializable;

/**
 * T_PT_FWRZJBXX表对应的实体类
 */
@SuppressWarnings("serial")
public class TPtFwrzjbxxBO implements Serializable{
	
	public TPtFwrzjbxxBO(){}

	private String fwrzjbid;	
	private String callername;	//调用者
	private String callertime;	//调用者开始时间
	private String callerenttime;	//调用者结束时间
	private String executecase;	
	private String executeway;	
	private String executetime;	//时间差
	private String callerparameter;	//参数
	private String executeresult;//执行结果
	private String callerip;	
	private String executetype;	
	private String calleer;//提供者
	private String fwmc;//服务名称
	
	public String getFwrzjbid(){
		return fwrzjbid;
	}
	public void setFwrzjbid(String fwrzjbid){
		this.fwrzjbid = fwrzjbid;
	}
	public String getCallername(){
		return callername;
	}
	public void setCallername(String callername){
		this.callername = callername;
	}
	public String getCallertime(){
		return callertime;
	}
	public void setCallertime(String callertime){
		this.callertime = callertime;
	}
	public String getExecutecase(){
		return executecase;
	}
	public void setExecutecase(String executecase){
		this.executecase = executecase;
	}
	public String getExecuteway(){
		return executeway;
	}
	public void setExecuteway(String executeway){
		this.executeway = executeway;
	}
	public String getExecutetime(){
		return executetime;
	}
	public void setExecutetime(String executetime){
		this.executetime = executetime;
	}
	public String getCallerparameter(){
		return callerparameter;
	}
	public void setCallerparameter(String callerparameter){
		this.callerparameter = callerparameter;
	}
	public String getExecuteresult(){
		return executeresult;
	}
	public void setExecuteresult(String executeresult){
		this.executeresult = executeresult;
	}
	public String getCallerip(){
		return callerip;
	}
	public void setCallerip(String callerip){
		this.callerip = callerip;
	}
	public String getExecutetype(){
		return executetype;
	}
	public void setExecutetype(String executetype){
		this.executetype = executetype;
	}
	public String getCalleer() {
		return calleer;
	}
	public void setCalleer(String calleer) {
		this.calleer = calleer;
	}
	public String getCallerenttime() {
		return callerenttime;
	}
	public void setCallerenttime(String callerenttime) {
		this.callerenttime = callerenttime;
	}
	public String getFwmc() {
		return fwmc;
	}
	public void setFwmc(String fwmc) {
		this.fwmc = fwmc;
	}
	
}