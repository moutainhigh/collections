package com.gwssi.application.integration.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

@Entity
@Table(name = "t_uploadExcel1")
public class EntBo extends AbsDaoBussinessObject {
	private String tid;
	private String regno;
	private String entname;
	private String enttype;

	public EntBo() {
		super();
	}
    
	@Id
	@Column(name = "TID")
	public String getTid() {
		return tid;
	}

	@Column(name = "ENTNAME")
	public String getEntname() {
		return entname;
	}

	@Column(name = "ENTTYPE")
	public String getEnttype() {
		return enttype;
	}

	@Column(name = "REGNO")
	public String getRegno() {
		return regno;
	}

	public void setEntname(String entname) {
		this.entname = entname;
	}

	public void setEnttype(String enttype) {
		this.enttype = enttype;
	}

	public void setRegno(String regno) {
		this.regno = regno;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

}
