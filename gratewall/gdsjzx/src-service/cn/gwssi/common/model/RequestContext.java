package cn.gwssi.common.model;

import java.io.Serializable;
import java.util.*;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 请求参数报文(同步)
 * @author xue
 * @version 1.0
 * @since 2016/4/20
 */
@SuppressWarnings("serial")
@XmlRootElement(name="RequestContext")
public class RequestContext implements Serializable{
	
	private String serviceId;// 服务执行编号
	private String serviceName;// 服务名
	private String serviceUrl;// 服务地址
	private String objectName;//服务使用方
	private String excuteTime;// 服务请求时间
	private String params;//参数
	private Map<String,String> requestParam;//参数存放所有参数
	private String timeOut;//同步超时时间
	private String excuteServicePath;//服务类
	private String asyClassPath;//异步回调指定的类
	private String serverModel;//服务模式
	
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	public Map<String, String> getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(Map<String, String> requestParam) {
		this.requestParam = requestParam;
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

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getExcuteTime() {
		return excuteTime;
	}

	public void setExcuteTime(String excuteTime) {
		this.excuteTime = excuteTime;
	}

	public String getExcuteServicePath() {
		return excuteServicePath;
	}

	public void setExcuteServicePath(String excuteServicePath) {
		this.excuteServicePath = excuteServicePath;
	}

	public String getAsyClassPath() {
		return asyClassPath;
	}

	public void setAsyClassPath(String asyClassPath) {
		this.asyClassPath = asyClassPath;
	}

	public String getServerModel() {
		return serverModel;
	}

	public void setServerModel(String serverModel) {
		this.serverModel = serverModel;
	}

	@Override
	public String toString() {
		StringBuffer sbf = new StringBuffer();
		sbf.append("服务执行编号:").append(serviceId);
		sbf.append("服务名:").append(serviceName);
		sbf.append("服务地址:").append(serviceUrl);
		sbf.append("服务使用方:").append(objectName);
		sbf.append("服务请求时间:").append(excuteTime);
		sbf.append("参数:").append(params);
		sbf.append("参数存放所有参数:").append(requestParam);
		sbf.append("同步超时时间:").append(timeOut);
		sbf.append("服务类:").append(excuteServicePath);
		sbf.append("异步回调指定的类:").append(asyClassPath);
		return sbf.toString();
	}
	
}
