package cn.gwssi.common.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 返回状态、结果集等报文
 * @author xue
 * @version 1.0
 * @since 2016/4/20
 */
@SuppressWarnings("serial")
@XmlRootElement(name="reponseContext")
public class SynReponseContext extends ReponseContextBase implements Serializable{
	
	private List responseResult;

	public List getResponseResult() {
		return responseResult;
	}

	public void setResponseResult(List responseResult) {
		this.responseResult = responseResult;
	}

	public static void main(String[] args) {
		SynReponseContext s = new SynReponseContext();
	}
	
	@Override
	public String toString() {
		StringBuffer sbf = new StringBuffer();
		sbf.append("返回状态码:").append(getCode());
		sbf.append("返回描述:").append(getDesc());
		sbf.append("返回异常码:").append(getErrorCode());
		sbf.append("返回异常信息:").append(getErrorMsg());
		
		sbf.append("返回结果集:").append(responseResult);
		
		return sbf.toString();
	}
}
