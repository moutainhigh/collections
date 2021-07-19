package com.gwssi.upload.pojo;

import java.sql.Timestamp;

/**
 * 获取基本信息
 * @author Administrator
 *
 */
public class BaseInfo {
	
	private String regno;	//注册号
	private String unifsocicrediden;	//统一社会信用代码
	private String entname;	//企业名字
	private Timestamp apprdate;//核准日期
	private Integer count;  //用于判断
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getRegno() {
		return regno;
	}
	public void setRegno(String regno) {
		this.regno = regno;
	}
	public String getUnifsocicrediden() {
		return unifsocicrediden;
	}
	public void setUnifsocicrediden(String unifsocicrediden) {
		this.unifsocicrediden = unifsocicrediden;
	}
	public String getEntname() {
		return entname;
	}
	public void setEntname(String entname) {
		this.entname = entname;
	}
	public Timestamp getApprdate() {
		return apprdate;
	}
	public void setApprdate(Timestamp apprdate) {
		this.apprdate = apprdate;
	}
	@Override
	public String toString() {
		return "BaseInfo [regno=" + regno + ", unifsocicrediden="
				+ unifsocicrediden + ", entname=" + entname + ", apprdate="
				+ apprdate + "]";
	}
	
	
}
