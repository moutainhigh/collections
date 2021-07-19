package com.ly.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
@Entity
@DynamicUpdate(true)
@Table(indexes = { @Index(name = "id", columnList = "id") })
public class Setting  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String isdefault;
	private String percent_value;
	private String text;
	private String type;
	
	@Id
	public String getId() {
		return id;
	}
	@Column
	public String getIsdefault() {
		return isdefault;
	}
	@Column
	public String getPercent_value() {
		return percent_value;
	}
	@Column
	public String getText() {
		return text;
	}
	@Column
	public String getType() {
		return type;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}
	public void setPercent_value(String percent_value) {
		this.percent_value = percent_value;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	

	
}
