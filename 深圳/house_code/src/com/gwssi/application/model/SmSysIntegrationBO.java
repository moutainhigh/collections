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
 * SM_SYS_INTEGRATION表对应的实体类
 */
@Entity
@Table(name = "SM_SYS_INTEGRATION")
public class SmSysIntegrationBO extends AbsDaoBussinessObject {
	
	public SmSysIntegrationBO(){}

	private String pkSysIntegration;	
	private String pkSmLikeman;	
	private String modifierName;	
	private String systemName;	
	private String systemCode;	
	private String integratedUrl;	
	private String systemImgUrl;	
	private String systemState;	
	private BigDecimal orderNo;	
	private String remarks;	
	private String effectiveMarker;	
	private String createrId;	
	private String createrName;	
	private Calendar createrTime;	
	private String modifierId;	
	private Calendar modifierTime;	
	private String pkSmFirm;	
	private String systemImgUrlCode;	
	
	@Id
	@Column(name = "PK_SYS_INTEGRATION")
	public String getPkSysIntegration(){
		return pkSysIntegration;
	}
	public void setPkSysIntegration(String pkSysIntegration){
		this.pkSysIntegration = pkSysIntegration;
		markChange("pkSysIntegration", pkSysIntegration);
	}
	@Column(name = "PK_SM_LIKEMAN")
	public String getPkSmLikeman(){
		return pkSmLikeman;
	}
	public void setPkSmLikeman(String pkSmLikeman){
		this.pkSmLikeman = pkSmLikeman;
		markChange("pkSmLikeman", pkSmLikeman);
	}
	@Column(name = "MODIFIER_NAME")
	public String getModifierName(){
		return modifierName;
	}
	public void setModifierName(String modifierName){
		this.modifierName = modifierName;
		markChange("modifierName", modifierName);
	}
	@Column(name = "SYSTEM_NAME")
	public String getSystemName(){
		return systemName;
	}
	public void setSystemName(String systemName){
		this.systemName = systemName;
		markChange("systemName", systemName);
	}
	@Column(name = "SYSTEM_CODE")
	public String getSystemCode(){
		return systemCode;
	}
	public void setSystemCode(String systemCode){
		this.systemCode = systemCode;
		markChange("systemCode", systemCode);
	}
	@Column(name = "INTEGRATED_URL")
	public String getIntegratedUrl(){
		return integratedUrl;
	}
	public void setIntegratedUrl(String integratedUrl){
		this.integratedUrl = integratedUrl;
		markChange("integratedUrl", integratedUrl);
	}
	@Column(name = "SYSTEM_IMG_URL")
	public String getSystemImgUrl(){
		return systemImgUrl;
	}
	public void setSystemImgUrl(String systemImgUrl){
		this.systemImgUrl = systemImgUrl;
		markChange("systemImgUrl", systemImgUrl);
	}
	@Column(name = "SYSTEM_STATE")
	public String getSystemState(){
		return systemState;
	}
	public void setSystemState(String systemState){
		this.systemState = systemState;
		markChange("systemState", systemState);
	}
	@Column(name = "ORDER_NO")
	public BigDecimal getOrderNo(){
		return orderNo;
	}
	public void setOrderNo(BigDecimal orderNo){
		this.orderNo = orderNo;
		markChange("orderNo", orderNo);
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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFIER_TIME")
	public Calendar getModifierTime(){
		return modifierTime;
	}
	public void setModifierTime(Calendar modifierTime){
		this.modifierTime = modifierTime;
		markChange("modifierTime", modifierTime);
	}
	@Column(name = "PK_SM_FIRM")
	public String getPkSmFirm(){
		return pkSmFirm;
	}
	public void setPkSmFirm(String pkSmFirm){
		this.pkSmFirm = pkSmFirm;
		markChange("pkSmFirm", pkSmFirm);
	}
	@Column(name = "SYSTEM_IMG_URL_CODE")
	public String getSystemImgUrlCode(){
		return systemImgUrlCode;
	}
	public void setSystemImgUrlCode(String systemImgUrlCode){
		this.systemImgUrlCode = systemImgUrlCode;
		markChange("systemImgUrlCode", systemImgUrlCode);
	}
}