package com.ly.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate(true)
@Table(indexes = { @Index(name = "userid", columnList = "userid") })
public class User implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String userid;
	private String name;
	private String pwd;
	private String limitTime;
	@Column
	public String getLimitTime() {
		return limitTime;
	}
	@Column
	public String getName() {
		return name;
	}
	@Column
	public String getPwd() {
		return pwd;
	}

	@Id
	public String getUserid() {
		return userid;
	}

	public void setLimitTime(String limitTime) {
		this.limitTime = limitTime;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column
	public void setUserid(String userid) {
		this.userid = userid;
	}

}
