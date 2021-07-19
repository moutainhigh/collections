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
 * SM_FIRM表对应的实体类
 */
@Entity
@Table(name = "SM_FIRM")
public class SmFirmBO extends AbsDaoBussinessObject {
	
	public SmFirmBO(){}

	private String pkSmFirm;	
	private String firmName;	
	private String firmNameShort;	
	private String address;	
	private String phone;	
	private String zipCode;	
	private String fax;	
	private String remarks;	
	private String effectiveMarker;	
	private String createrId;	
	private String createrName;	
	private Calendar createrTime;	
	private String modifierId;	
	private String modifierName;	
	private Calendar modifierTime;	
	
	@Id
	@Column(name = "PK_SM_FIRM")
	public String getPkSmFirm(){
		return pkSmFirm;
	}
	public void setPkSmFirm(String pkSmFirm){
		this.pkSmFirm = pkSmFirm;
		markChange("pkSmFirm", pkSmFirm);
	}
	@Column(name = "FIRM_NAME")
	public String getFirmName(){
		return firmName;
	}
	public void setFirmName(String firmName){
		this.firmName = firmName;
		markChange("firmName", firmName);
	}
	@Column(name = "FIRM_NAME_SHORT")
	public String getFirmNameShort(){
		return firmNameShort;
	}
	public void setFirmNameShort(String firmNameShort){
		this.firmNameShort = firmNameShort;
		markChange("firmNameShort", firmNameShort);
	}
	@Column(name = "ADDRESS")
	public String getAddress(){
		return address;
	}
	public void setAddress(String address){
		this.address = address;
		markChange("address", address);
	}
	@Column(name = "PHONE")
	public String getPhone(){
		return phone;
	}
	public void setPhone(String phone){
		this.phone = phone;
		markChange("phone", phone);
	}
	@Column(name = "ZIP_CODE")
	public String getZipCode(){
		return zipCode;
	}
	public void setZipCode(String zipCode){
		this.zipCode = zipCode;
		markChange("zipCode", zipCode);
	}
	@Column(name = "FAX")
	public String getFax(){
		return fax;
	}
	public void setFax(String fax){
		this.fax = fax;
		markChange("fax", fax);
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