package cn.gwssi.common.model;

import java.io.Serializable;

/**
 * T_PT_FWRZXXXX表对应的实体类
 */
@SuppressWarnings("serial")
public class TPtFwrzxxxxBO implements Serializable{
	
	public TPtFwrzxxxxBO(){}

	private String fwrzxxid;//服务日志详细主键
	private String fwrzjbid;//服务日志基本主键
	private String startTime;//开始时间
	private String endTime;//结束时间
	private String time;//时间差
	private String obj;//对象
	private String detail;//参数
	private String code;//结果状态
	private String executecontent;	//结果信息
	
	public String getFwrzxxid(){
		return fwrzxxid;
	}
	public void setFwrzxxid(String fwrzxxid){
		this.fwrzxxid = fwrzxxid;
	}
	public String getFwrzjbid(){
		return fwrzjbid;
	}
	public void setFwrzjbid(String fwrzjbid){
		this.fwrzjbid = fwrzjbid;
	}
	public String getDetail(){
		return detail;
	}
	public void setDetail(String detail){
		this.detail = detail;
	}
	public String getTime(){
		return time;
	}
	public void setTime(String time){
		this.time = time;
	}
	public String getExecutecontent(){
		return executecontent;
	}
	public void setExecutecontent(String executecontent){
		this.executecontent = executecontent;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getObj() {
		return obj;
	}
	public void setObj(String obj) {
		this.obj = obj;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}