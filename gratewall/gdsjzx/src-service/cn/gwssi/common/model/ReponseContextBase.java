package cn.gwssi.common.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 返回状态、结果集等报文
 * @author xue
 * @version 1.0
 * @since 2016/4/20
 */
@SuppressWarnings("serial")
@XmlRootElement(name="reponseContext")
public class ReponseContextBase implements Serializable{
	
	private String serviceId;// 服务执行编号
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

	@Override
	public String toString() {
		StringBuffer sbf = new StringBuffer();
		sbf.append("服务执行编号:").append(serviceId);
		sbf.append("返回状态码:").append(code);
		sbf.append("返回描述:").append(desc);
		sbf.append("返回异常码:").append(errorCode);
		sbf.append("返回异常信息:").append(errorMsg);
		
		return sbf.toString();
	}
}
