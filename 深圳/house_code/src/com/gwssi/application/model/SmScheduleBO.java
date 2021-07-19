package com.gwssi.application.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import java.util.Calendar;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import com.gwssi.optimus.core.persistence.annotation.Lob;
import java.math.BigDecimal;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;

/**
 * SM_SCHEDULE表对应的实体类
 */
@Entity
@Table(name = "SM_SCHEDULE")
public class SmScheduleBO extends AbsDaoBussinessObject {
	
	public SmScheduleBO(){}

	private String pkSchedule;	
	private String title;	
	private String createrId;	
	private String createrUser;	
	private String remindType;	
	private BigDecimal remindValue;	
	private Calendar effectiveTime;	
	private Calendar createrTime;	
	private Calendar modifierTime;
	private Calendar compareTime;
	private String compareState;	
	
	@Id
	@Column(name = "PK_SCHEDULE")
	public String getPkSchedule(){
		return pkSchedule;
	}
	public void setPkSchedule(String pkSchedule){
		this.pkSchedule = pkSchedule;
		markChange("pkSchedule", pkSchedule);
	}
	@Column(name = "TITLE")
	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title = title;
		markChange("title", title);
	}
	@Column(name = "CREATER_ID")
	public String getCreaterId(){
		return createrId;
	}
	public void setCreaterId(String createrId){
		this.createrId = createrId;
		markChange("createrId", createrId);
	}
	@Column(name = "CREATER_USER")
	public String getCreaterUser(){
		return createrUser;
	}
	public void setCreaterUser(String createrUser){
		this.createrUser = createrUser;
		markChange("createrUser", createrUser);
	}
	@Column(name = "REMIND_TYPE")
	public String getRemindType(){
		return remindType;
	}
	public void setRemindType(String remindType){
		this.remindType = remindType;
		markChange("remindType", remindType);
	}
	@Column(name = "REMIND_VALUE")
	public BigDecimal getRemindValue(){
		return remindValue;
	}
	public void setRemindValue(BigDecimal remindValue){
		this.remindValue = remindValue;
		markChange("remindValue", remindValue);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFFECTIVE_TIME")
	public Calendar getEffectiveTime(){
		return effectiveTime;
	}
	public void setEffectiveTime(Calendar effectiveTime){
		this.effectiveTime = effectiveTime;
		markChange("effectiveTime", effectiveTime);
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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFIER_TIME")
	public Calendar getModifierTime(){
		return modifierTime;
	}
	public void setModifierTime(Calendar modifierTime){
		this.modifierTime = modifierTime;
		markChange("modifierTime", modifierTime);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "COMPARE_TIME")
	public Calendar getCompareTime(){
		return compareTime;
	}
	public void setCompareTime(Calendar compareTime){
		this.compareTime = compareTime;
		markChange("compareTime", compareTime);
	}
	@Column(name = "COMPARE_STATE")
	public String getCompareState(){
		return compareState;
	}
	public void setCompareState(String compareState){
		this.compareState = compareState;
		markChange("compareState", compareState);
	}
}