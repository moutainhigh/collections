package com.gwssi.rodimus.doc.v1.domain;


import java.util.Calendar;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Entity
@Table(name = "SYS_DOC_DATA_CONFIG")
public class SysDocDataConfig  extends AbsDaoBussinessObject{

	private String dataId = "";
	private String docConfigId = "";
	private String chapConfigId = "";
	private String sn = "";
	private String sql = "";
	private String params = "";
	private String var = "";
	private String varType = "";
	private Calendar timestamp = null;

	@Id
	@Column(name = "DATA_ID")
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	@Column(name="DOC_CONFIG_ID")
	public String getDocConfigId() {
		return docConfigId;
	}
	public void setDocConfigId(String docConfigId) {
		this.docConfigId = docConfigId;
	}
	@Column(name="CHAP_CONFIG_ID")
	public String getChapConfigId() {
		return chapConfigId;
	}
	public void setChapConfigId(String chapConfigId) {
		this.chapConfigId = chapConfigId;
	}
	@Column(name = "SN")
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	@Column(name = "SQL")
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	@Column(name = "PARAMS")
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	@Column(name = "VAR")
	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
	}
	@Column(name = "VAR_TYPE")
	public String getVarType() {
		return varType;
	}
	public void setVarType(String varType) {
		this.varType = varType;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TIMESTAMP")
	public Calendar getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Calendar timestamp) {
		this.timestamp = timestamp;
	}
}
