package com.ly.pojo;

public class MyExcepitonLog  implements java.io.Serializable {
	private static final long serialVersionUID = -3859526119096611813L;
	private String url;
	private String logContent;
	private String createTime;
	private String logType;// 0 平安证券常规请求问题，1平安证券需要登录问题。3其他类型

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLogContent() {
		return logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

}
