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
 * DC_DM_DBTYPE表对应的实体类
 */
@Entity
@Table(name = "DC_DM_DBTYPE")
public class DcDmDbtypeBO extends AbsDaoBussinessObject {
	
	public DcDmDbtypeBO(){}

	private String code;	
	private String name;	
	private String nameShort;	
	private String fCode;	
	private String chooseMark;	
	private String createrId;	
	private String createrName;	
	private Calendar createrTime;	
	private String modifierId;	
	private String modifierName;	
	private Calendar modifierTime;	
	
	@Id
	@Column(name = "CODE")
	public String getCode(){
		return code;
	}
	public void setCode(String code){
		this.code = code;
		markChange("code", code);
	}
	@Column(name = "NAME")
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
		markChange("name", name);
	}
	@Column(name = "NAME_SHORT")
	public String getNameShort(){
		return nameShort;
	}
	public void setNameShort(String nameShort){
		this.nameShort = nameShort;
		markChange("nameShort", nameShort);
	}
	@Column(name = "F_CODE")
	public String getFCode(){
		return fCode;
	}
	public void setFCode(String fCode){
		this.fCode = fCode;
		markChange("fCode", fCode);
	}
	@Column(name = "CHOOSE_MARK")
	public String getChooseMark(){
		return chooseMark;
	}
	public void setChooseMark(String chooseMark){
		this.chooseMark = chooseMark;
		markChange("chooseMark", chooseMark);
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