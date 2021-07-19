package com.gwssi.upload.pojo;

import java.util.Date;

public class MaxTableTime {

	private String regno;
	private String unifsocicrediden;
	private String entname;
	private String flag;
	private String updatetime;

	private Integer count; // 用于判断

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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	@Override
	public String toString() {
		return "MaxTableTime [regno=" + regno + ", unifsocicrediden=" + unifsocicrediden + ", entname=" + entname
				+ ", flag=" + flag + ", updatetime=" + updatetime + "]";
	}

}
