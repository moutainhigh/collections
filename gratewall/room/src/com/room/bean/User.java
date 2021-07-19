package com.room.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class User {

	private String userId;
	private String userName;
	private Integer isupdate;
	private Integer isdele;

	@Column
	public Integer getIsdele() {
		return isdele;
	}

	@Column
	public Integer getIsupdate() {
		return isupdate;
	}

	@Id
	@GeneratedValue(generator = "myId")
	@GenericGenerator(name = "myId", strategy = "uuid")
	public String getUserId() {
		return userId;
	}

	@Column
	public String getUserName() {
		return userName;
	}

	public void setIsdele(Integer isdele) {
		this.isdele = isdele;
	}

	public void setIsupdate(Integer isupdate) {
		this.isupdate = isupdate;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
