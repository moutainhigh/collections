package cn.gwssi.common.model;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DataCarrier implements Serializable{
	
	private String serviceId;// 服务执行编号
	private String serviceName;// 服务名
	private String objectName;//服务使用方
	private List responseResults;
	private String asyClassPath;//异步回调指定的类
	
	private String startNumber;//用来记录包编号（异步使用）
	private String endNumber;//用来记录包编号（异步使用）
	private String flag;//分包标记，true为结束
	private String asyPath;//异步大数据返回路径
	
	private String code;//返回状态码//0 成功 1失败
	private String desc;//返回描述
	private String errorCode;//返回异常码
	private String errorMsg;//返回异常信息
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	
	public List getResponseResults() {
		return responseResults;
	}
	public void setResponseResults(List responseResults) {
		this.responseResults = responseResults;
	}
	public String getAsyClassPath() {
		return asyClassPath;
	}
	public void setAsyClassPath(String asyClassPath) {
		this.asyClassPath = asyClassPath;
	}
	public String getStartNumber() {
		return startNumber;
	}
	public void setStartNumber(String startNumber) {
		this.startNumber = startNumber;
	}
	public String getEndNumber() {
		return endNumber;
	}
	public void setEndNumber(String endNumber) {
		this.endNumber = endNumber;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getAsyPath() {
		return asyPath;
	}
	public void setAsyPath(String asyPath) {
		this.asyPath = asyPath;
	}
	
}
