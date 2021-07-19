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
 * SM_DM_EFFECTIVE表对应的实体类
 */
@Entity
@Table(name = "SM_DM_EFFECTIVE")
public class SmDmEffectiveBO extends AbsDaoBussinessObject {
	
	public SmDmEffectiveBO(){}

	private String effectiveCode;	
	private String name;	
	private String nameShort;	
	private String fCode;	
	private String chooseMark;	
	private String createrId;	
	private Calendar createrTime;	
	private String modifierId;	
	private Calendar modifierTime;	
	private String effectiveMarker;
	
	@Id
	@Column(name = "EFFECTIVE_CODE")
	
	public String getEffectiveCode() {
		return effectiveCode;
	}
	public void setEffectiveCode(String effectiveCode) {
		this.effectiveCode = effectiveCode;
		markChange("effectiveCode", effectiveCode);
	}
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		markChange("name", name);
	}
	
	@Column(name = "NAME_SHORT")
	public String getNameShort() {
		return nameShort;
	}
	public void setNameShort(String nameShort) {
		this.nameShort = nameShort;
		markChange("nameShort", nameShort);
	}
	
	@Column(name = "F_CODE")
	public String getfCode() {
		return fCode;
	}
	public void setfCode(String fCode) {
		this.fCode = fCode;
		markChange("fCode", fCode);
	}
	
	@Column(name = "CHOOSE_MARK")
	public String getChooseMark() {
		return chooseMark;
	}
	public void setChooseMark(String chooseMark) {
		this.chooseMark = chooseMark;
		markChange("chooseMark", chooseMark);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATER_TIME")
	public Calendar getCreaterTime() {
		return createrTime;
	}
	public void setCreaterTime(Calendar createrTime) {
		this.createrTime = createrTime;
		markChange("createrTime", createrTime);
	}
	
	@Column(name = "MODIFIER_ID")
	public String getModifierId() {
		return modifierId;
	}
	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
		markChange("modifierId", modifierId);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFIER_TIME")
	public Calendar getModifierTime() {
		return modifierTime;
	}
	public void setModifierTime(Calendar modifierTime) {
		this.modifierTime = modifierTime;
		markChange("modifierTime", modifierTime);
	}
	
	
	@Column(name = "CREATER_ID")
	public String getCreaterId(){
		return createrId;
	}
	public void setCreaterId(String createrId){
		this.createrId = createrId;
		markChange("createrId", createrId);
	}
	@Column(name = "EFFECTIVE_MARKER")
	public String getEffectiveMarker(){
		return effectiveMarker;
	}
	public void setEffectiveMarker(String effectiveMarker){
		this.effectiveMarker = effectiveMarker;
		markChange("effectiveMarker", effectiveMarker);
	}

}