package com.ly.pojo;

public class SMSPhone  implements java.io.Serializable {
	private static final long serialVersionUID = -3859526119096611813L;

	private String phone;
	private String name;
	private String isSend;
	private Integer count;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsSend() {
		return isSend;
	}

	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
