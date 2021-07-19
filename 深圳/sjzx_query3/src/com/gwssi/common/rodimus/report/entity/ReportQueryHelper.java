package com.gwssi.common.rodimus.report.entity;

import java.io.Serializable;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

@Entity
@Table(name = "RPT_CONFIG")
public class ReportQueryHelper extends AbsDaoBussinessObject implements Serializable {

	private static final long serialVersionUID = 8971114661693259921L;
	
	private String id;
	private String groupCode;
	private String label;
	private String scope;
	private String value;
	private String sn;
	
	@Column(name = "ID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
		markChange("id", id);
	}
	@Column(name = "GROUP_CODE")
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
		markChange("groupCode", groupCode);
	}
	@Column(name = "LABEL")
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
		markChange("label", label);
	}
	@Column(name = "VALUE")
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
		markChange("value", value);
	}
	@Column(name = "SCOPE")
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
		markChange("scope", scope);
	}
	@Column(name = "SN")
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
		markChange("sn", sn);
	}
}
