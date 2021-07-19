package com.ly.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

//@Entity
//@DynamicUpdate(true)
//@Table(indexes={@Index(name="code",columnList="code")})
public class StockPointer implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String code;
	
	@Column
	public String getCode() {
		return code;
	}
	
	@Id
	@GeneratedValue(generator = "myId")
	@GenericGenerator(name = "myId", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
