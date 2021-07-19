package com.gwssi.common.rodimus.report.entity;

import java.io.Serializable;
import java.util.Date;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

@Entity
@Table(name = "RPT_CONFIG")
public class ReportConfig extends AbsDaoBussinessObject implements Serializable {

	private static final long serialVersionUID = -7272608772384952492L;
	
	private String id;
	private String code;
	private String name;
	private String type;
	private String category;
	private String flag;
	private String scope;
	private String userId;
	private Date timestamp;
	private String remark;
	private String config;
	
	@Column(name = "ID")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
		//拼接update sql语句使用，如果加了markChange，在保存bo的时候拼接该字段，否则不拼接
		markChange("id", id);
	}
	@Column(name = "CODE")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
		markChange("code", code);
	}
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		markChange("name", name);
	}
	@Column(name = "TYPE")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
		markChange("type", type);
	}
	@Column(name = "CATEGORY")
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
		markChange("category", category);
	}
	@Column(name = "FLAG")
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
		markChange("flag", flag);
	}
	@Column(name = "SCOPE")
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
		markChange("scope", scope);
	}
	@Column(name = "USER_ID")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
		markChange("userId", userId);
	}
	@Column(name = "TIMESTAMP")
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
		markChange("timestamp", timestamp);
	}
	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
		markChange("remark", remark);
	}
	@Column(name = "CONFIG")
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
		markChange("config", config);
	}
}
