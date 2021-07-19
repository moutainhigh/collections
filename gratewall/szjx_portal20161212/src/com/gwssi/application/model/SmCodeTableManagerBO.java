package com.gwssi.application.model;

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
 * SM_CODE_TABLE_MANAGER表对应的实体类
 */
@Entity
@Table(name = "SM_CODE_TABLE_MANAGER")
public class SmCodeTableManagerBO extends AbsDaoBussinessObject {
	
	public SmCodeTableManagerBO(){}

	private String pkCodeTableManager;	
	private String pkSysIntegration;	
	private String codeTableChName;	
	private String codeTableEnName;	
	private String codeColumn;	
	private String valueColumn;	
	private String codeTableDesc;	
	private String cacheType;	
	private String effectiveMarker;	
	private String createrId;	
	private String createrName;	
	private Calendar createrTime;	
	private String modifierId;	
	private String modifierName;	
	private Calendar modifierTime;	
	
	@Id
	@Column(name = "PK_CODE_TABLE_MANAGER")
	public String getPkCodeTableManager(){
		return pkCodeTableManager;
	}
	public void setPkCodeTableManager(String pkCodeTableManager){
		this.pkCodeTableManager = pkCodeTableManager;
		markChange("pkCodeTableManager", pkCodeTableManager);
	}
	@Column(name = "PK_SYS_INTEGRATION")
	public String getPkSysIntegration(){
		return pkSysIntegration;
	}
	public void setPkSysIntegration(String pkSysIntegration){
		this.pkSysIntegration = pkSysIntegration;
		markChange("pkSysIntegration", pkSysIntegration);
	}
	@Column(name = "CODE_TABLE_CH_NAME")
	public String getCodeTableChName(){
		return codeTableChName;
	}
	public void setCodeTableChName(String codeTableChName){
		this.codeTableChName = codeTableChName;
		markChange("codeTableChName", codeTableChName);
	}
	@Column(name = "CODE_TABLE_EN_NAME")
	public String getCodeTableEnName(){
		return codeTableEnName;
	}
	public void setCodeTableEnName(String codeTableEnName){
		this.codeTableEnName = codeTableEnName;
		markChange("codeTableEnName", codeTableEnName);
	}
	@Column(name = "CODE_COLUMN")
	public String getCodeColumn(){
		return codeColumn;
	}
	public void setCodeColumn(String codeColumn){
		this.codeColumn = codeColumn;
		markChange("codeColumn", codeColumn);
	}
	@Column(name = "VALUE_COLUMN")
	public String getValueColumn(){
		return valueColumn;
	}
	public void setValueColumn(String valueColumn){
		this.valueColumn = valueColumn;
		markChange("valueColumn", valueColumn);
	}
	@Column(name = "CODE_TABLE_DESC")
	public String getCodeTableDesc(){
		return codeTableDesc;
	}
	public void setCodeTableDesc(String codeTableDesc){
		this.codeTableDesc = codeTableDesc;
		markChange("codeTableDesc", codeTableDesc);
	}
	@Column(name = "CACHE_TYPE")
	public String getCacheType(){
		return cacheType;
	}
	public void setCacheType(String cacheType){
		this.cacheType = cacheType;
		markChange("cacheType", cacheType);
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