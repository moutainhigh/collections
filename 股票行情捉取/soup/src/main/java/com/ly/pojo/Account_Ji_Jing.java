package com.ly.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate(true)
@Table(indexes = { @Index(name = "aid", columnList = "aid") })
public class Account_Ji_Jing implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String isdefault;
	private String operationType;//自动买，不操作
	private String seqs;
	
	@Id
	public String getId() {
		return id;
	}


	@Column
	public String getIsdefault() {
		return isdefault;
	}

	@Column
	public String getOperationType() {
		return operationType;
	}
	@Column
	public String getSeqs() {
		return seqs;
	}


	public void setId(String id) {
		this.id = id;
	}


	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}


	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}




	public void setSeqs(String seqs) {
		this.seqs = seqs;
	}

	
}
