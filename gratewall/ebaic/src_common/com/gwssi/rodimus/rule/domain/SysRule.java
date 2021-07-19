package com.gwssi.rodimus.rule.domain;

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
@Table(name = "SYS_RULE_APP")
public class SysRule extends AbsDaoBussinessObject{

	private String ruleId = "";
	private String ruleCategory = "";
	private String ruleCode = "";
	private String ruleName = "";
	private String ruleMode = "";
	private String ruleDesc = "";
	private String flag = "";
	private Calendar timestamp = null;

	@Id
	@Column(name = "RULE_ID")
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	@Column(name = "RULE_CODE")
	public String getRuleCode() {
		return ruleCode;
	}
	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	
	@Column(name = "RULE_NAME")
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	
	@Column(name = "FLAG")
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name = "RULE_CATEGORY")
	public String getRuleCategory() {
		return ruleCategory;
	}
	public void setRuleCategory(String ruleCategory) {
		this.ruleCategory = ruleCategory;
	}
	
	@Column(name = "RULE_DESC")
	public String getRuleDesc() {
		return ruleDesc;
	}
	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	@Column(name = "RULE_MODE")
	public String getRuleMode() {
		return ruleMode;
	}
	public void setRuleMode(String ruleMode) {
		this.ruleMode = ruleMode;
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
