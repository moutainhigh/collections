package com.ly.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate(true)
@Table(indexes = { @Index(name = "keyStr", columnList = "keyStr") })
public class TokenKey implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String keyStr;
	
	@Id
	public String getId() {
		return id;
	}
	@Column
	public String getKeyStr() {
		return keyStr;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setKeyStr(String keyStr) {
		this.keyStr = keyStr;
	}
	
	
}
