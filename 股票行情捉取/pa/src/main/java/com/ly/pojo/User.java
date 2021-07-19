package com.ly.pojo;

//https://blog.csdn.net/p312011150/article/details/82179254
//https://bbs.csdn.net/topics/300218420
//https://blog.csdn.net/p312011150/article/details/82179254
//http://www.cnblogs.com/chiangchou/archive/2016/02/28/session-timeout.html
//https://blog.csdn.net/qq_31692013/article/details/77297827
//https://blog.csdn.net/zhangxing52077/article/details/73136458
//https://blog.csdn.net/liyuling52011/article/details/80013725
public class User implements java.io.Serializable {
	private static final long serialVersionUID = -3859526119096611813L;
	private String name;
	private String pwd;
	private String limitTime;
	private Integer count;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(String limitTime) {
		this.limitTime = limitTime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
