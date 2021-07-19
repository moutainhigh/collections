package com.ly.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@DynamicUpdate(true)
@Table(indexes={@Index(name="address",columnList="address")})
public class Email_Recevier_Log implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String address;
	private String msg;
	private String time;
	private String flag;
	@Column
	public String getAddress() {
		return address;
	}
	@Column
	public String getFlag() {
		return flag;
	}
	@Id
	@GeneratedValue(generator = "myId")    
	@GenericGenerator(name = "myId", strategy = "uuid")  
	public String getId() {
		return id;
	}
	@Column
	public String getMsg() {
		return msg;
	}
	@Column
	public String getTime() {
		return time;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
