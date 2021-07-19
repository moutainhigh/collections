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
 * SM_LIKEMAN表对应的实体类
 */
@Entity
@Table(name = "SM_LIKEMAN")
public class SmLikemanBO extends AbsDaoBussinessObject {
	
	public SmLikemanBO(){}

	private String pkSmLikeman;	
	private String pkSmFirm;	
	private String smLikeman;	
	private String phone;	
	private String email;	
	private String remarks;	
	private String effectiveMarker;	
	private String createrId;	
	private String createrName;	
	private Calendar createrTime;	
	private String modifierId;	
	private String modifierName;	
	private Calendar modifierTime;	
	
	@Id
	@Column(name = "PK_SM_LIKEMAN")
	public String getPkSmLikeman(){
		return pkSmLikeman;
	}
	public void setPkSmLikeman(String pkSmLikeman){
		this.pkSmLikeman = pkSmLikeman;
		markChange("pkSmLikeman", pkSmLikeman);
	}
	@Column(name = "PK_SM_FIRM")
	public String getPkSmFirm(){
		return pkSmFirm;
	}
	public void setPkSmFirm(String pkSmFirm){
		this.pkSmFirm = pkSmFirm;
		markChange("pkSmFirm", pkSmFirm);
	}
	@Column(name = "SM_LIKEMAN")
	public String getSmLikeman(){
		return smLikeman;
	}
	public void setSmLikeman(String smLikeman){
		this.smLikeman = smLikeman;
		markChange("smLikeman", smLikeman);
	}
	@Column(name = "PHONE")
	public String getPhone(){
		return phone;
	}
	public void setPhone(String phone){
		this.phone = phone;
		markChange("phone", phone);
	}
	@Column(name = "EMAIL")
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
		markChange("email", email);
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