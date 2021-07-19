package com.ly.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate(true)
@Table(indexes={@Index(name="address",columnList="address")})
public class Email_Recevier implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String address;
	private String name;
	private String isSend;
	private String addtime;
	private String id;
	
	@Column
	public String getAddress() {
		return address;
	}
	@Column
	public String getAddtime() {
		return addtime;
	}

	/*private Set<Email_Recevier_Log> email_recevier_log = new HashSet<Email_Recevier_Log>(0);*/
	@Id
	public String getId() {
		return id;
	}
	//https://blog.csdn.net/u011781521/article/details/71159518
	//https://blog.csdn.net/qq_38157516/article/details/80146547
	//https://blog.csdn.net/maoyeqiu/article/details/50397391
	/*@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="id") 
	public Set<Email_Recevier_Log> getEmail_recevier_log() {
		return email_recevier_log;
	}*/
	@Column
	public String getIsSend() {
		return isSend;
	}
	
	@Column
	public String getName() {
		return name;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public void setId(String id) {
		this.id = id;
	}
	/*public void setEmail_recevier_log(Set<Email_Recevier_Log> email_recevier_log) {
		this.email_recevier_log = email_recevier_log;
	}*/
	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
