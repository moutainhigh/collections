package com.gwssi.rodimus.step.domain;

import java.util.Calendar;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
/**
 * 操作步骤进度说明
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Entity
@Table(name = "SYS_STEP_CONFIG")
public class SysStepConfigBO  extends AbsDaoBussinessObject{
	
	private String stepId = "";
	private String configId = "";
	private String sn = "";
	private String trigerExpr = "";
	private String stepCode = "";
	private String stepName = "";
	private String stepUrl = "";
	private String flag = "";
	private Calendar timestamp = null;
	
	@Id
	@Column(name = "STEP_ID")
	public String getStepId() {
		return stepId;
	}
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}
	@Column(name = "CONFIG_ID")
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	@Column(name = "SN")
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	@Column(name = "TRIGER_EXPR")
	public String getTrigerExpr() {
		return trigerExpr;
	}
	public void setTrigerExpr(String trigerExpr) {
		this.trigerExpr = trigerExpr;
	}
	@Column(name = "STEP_CODE")
	public String getStepCode() {
		return stepCode;
	}
	public void setStepCode(String stepCode) {
		this.stepCode = stepCode;
	}
	@Column(name = "STEP_NAME")
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	@Column(name = "STEP_URL")
	public String getStepUrl() {
		return stepUrl;
	}
	public void setStepUrl(String stepUrl) {
		this.stepUrl = stepUrl;
	}
	@Column(name = "FLAG")
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
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
