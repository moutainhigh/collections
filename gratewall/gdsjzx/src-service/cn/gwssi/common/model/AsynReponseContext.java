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
public class AsynReponseContext extends ReponseContextBase implements Serializable{
	
	private List responseResult;
	private String asyClassPath;//异步回调指定的类
	//private String responseResult;
	
	
	private String startNumber;//用来记录包编号（异步使用）
	private String endNumber;//用来记录包编号（异步使用）
	private String flag;//分包标记，true为结束
	private String asyPath;//异步大数据返回路径
	

	public List getResponseResult() {
		return responseResult;
	}

	public void setResponseResult(List responseResult) {
		this.responseResult = responseResult;
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

	@Override
	public String toString() {
		StringBuffer sbf = new StringBuffer();
		sbf.append("返回状态码:").append(getCode());
		sbf.append("返回描述:").append(getDesc());
		sbf.append("返回异常码:").append(getErrorCode());
		sbf.append("返回异常信息:").append(getErrorMsg());
		
		sbf.append("返回结果集:").append(responseResult);
		sbf.append("返回结果:").append(responseResult);
		sbf.append("用来记录包编号:").append(startNumber);
		sbf.append("数据包结束编号:").append(endNumber);
		sbf.append("分包标记:").append(flag);
		sbf.append("异步回调类:").append(asyClassPath);
		
		
		return sbf.toString();
	}
}
