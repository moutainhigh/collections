package com.gwssi.datacenter.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import java.util.Calendar;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import com.gwssi.optimus.core.persistence.annotation.Lob;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;

/**
 * DC_BUSI_OBJECT表对应的实体类
 */
@Entity
@Table(name = "DC_BUSI_OBJECT")
public class DcBusiObjectBO extends AbsDaoBussinessObject {
	
	public DcBusiObjectBO(){}

	private String pkDcBusiObject;	
	private String busiObjectName;	
	private String busiObjectCode;	
	private String remarks;	
	private String effectiveMarker;	
	private String createrId;	
	private String createrName;	
	private Calendar createrTime;	
	private String modifierId;	
	private String modifierName;	
	private Calendar modifierTime;	
	
	@Id
	@Column(name = "PK_DC_BUSI_OBJECT")
	public String getPkDcBusiObject(){
		return pkDcBusiObject;
	}
	public void setPkDcBusiObject(String pkDcBusiObject){
		this.pkDcBusiObject = pkDcBusiObject;
		markChange("pkDcBusiObject", pkDcBusiObject);
	}
	@Column(name = "BUSI_OBJECT_NAME")
	public String getBusiObjectName(){
		return busiObjectName;
	}
	public void setBusiObjectName(String busiObjectName){
		this.busiObjectName = busiObjectName;
		markChange("busiObjectName", busiObjectName);
	}
	@Column(name = "BUSI_OBJECT_CODE")
	public String getBusiObjectCode(){
		return busiObjectCode;
	}
	public void setBusiObjectCode(String busiObjectCode){
		this.busiObjectCode = busiObjectCode;
		markChange("busiObjectCode", busiObjectCode);
	}
	@Column(name = "REMARKS")
	public String getRemarks(){
		return remarks;
	}
	public void setRemarks(String remarks){
		this.remarks = remarks;
		markChange("remarks", remarks);
	}
	@Column(name = "EFFECTIVE_MARKER")
	public String getEffectiveMarker(){
		return effectiveMarker;
	}
	public void setEffectiveMarker(String effectiveMarker){
		this.effectiveMarker = effectiveMarker;
		markChange("effectiveMarker", effectiveMarker);
	}
	@Column(name = "CREATER_ID")
	public String getCreaterId(){
		return createrId;
	}
	public void setCreaterId(String createrId){
		this.createrId = createrId;
		markChange("createrId", createrId);
	}
	@Column(name = "CREATER_NAME")
	public String getCreaterName(){
		return createrName;
	}
	public void setCreaterName(String createrName){
		this.createrName = createrName;
		markChange("createrName", createrName);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATER_TIME")
	public Calendar getCreaterTime(){
		return createrTime;
	}
	public void setCreaterTime(Calendar createrTime){
		this.createrTime = createrTime;
		markChange("createrTime", createrTime);
	}
	@Column(name = "MODIFIER_ID")
	public String getModifierId(){
		return modifierId;
	}
	public void setModifierId(String modifierId){
		this.modifierId = modifierId;
		markChange("modifierId", modifierId);
	}
	@Column(name = "MODIFIER_NAME")
	public String getModifierName(){
		return modifierName;
	}
	public void setModifierName(String modifierName){
		this.modifierName = modifierName;
		markChange("modifierName", modifierName);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFIER_TIME")
	public Calendar getModifierTime(){
		return modifierTime;
	}
	public void setModifierTime(Calendar modifierTime){
		this.modifierTime = modifierTime;
		markChange("modifierTime", modifierTime);
	}
}