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
 * SM_SERVICES表对应的实体类
 */
@Entity
@Table(name = "SM_SERVICES")
public class SmServicesBO extends AbsDaoBussinessObject {
	
	public SmServicesBO(){}

	private String pkSmServices;	
	private String pkSysIntegration;	
	private String pkSmFirm;	
	private String pkSmLikeman;	
	private String serviceName;	
	private BigDecimal serviceNo;	
	private String serviceType;	
	private String serviceDisc;	
	private String agreementType;	
	private String serviceUrl;	
	private String effectiveMarker;	
	private String createrId;	
	private String createrName;	
	private Calendar createrTime;	
	private String modifierId;	
	private String modifierName;	
	private Calendar modifierTime;	
	
	@Id
	@Column(name = "PK_SM_SERVICES")
	public String getPkSmServices(){
		return pkSmServices;
	}
	public void setPkSmServices(String pkSmServices){
		this.pkSmServices = pkSmServices;
		markChange("pkSmServices", pkSmServices);
	}
	@Column(name = "PK_SYS_INTEGRATION")
	public String getPkSysIntegration(){
		return pkSysIntegration;
	}
	public void setPkSysIntegration(String pkSysIntegration){
		this.pkSysIntegration = pkSysIntegration;
		markChange("pkSysIntegration", pkSysIntegration);
	}
	@Column(name = "PK_SM_FIRM")
	public String getPkSmFirm(){
		return pkSmFirm;
	}
	public void setPkSmFirm(String pkSmFirm){
		this.pkSmFirm = pkSmFirm;
		markChange("pkSmFirm", pkSmFirm);
	}
	@Column(name = "PK_SM_LIKEMAN")
	public String getPkSmLikeman(){
		return pkSmLikeman;
	}
	public void setPkSmLikeman(String pkSmLikeman){
		this.pkSmLikeman = pkSmLikeman;
		markChange("pkSmLikeman", pkSmLikeman);
	}
	@Column(name = "SERVICE_NAME")
	public String getServiceName(){
		return serviceName;
	}
	public void setServiceName(String serviceName){
		this.serviceName = serviceName;
		markChange("serviceName", serviceName);
	}
	@Column(name = "SERVICE_NO")
	public BigDecimal getServiceNo(){
		return serviceNo;
	}
	public void setServiceNo(BigDecimal serviceNo){
		this.serviceNo = serviceNo;
		markChange("serviceNo", serviceNo);
	}
	@Column(name = "SERVICE_TYPE")
	public String getServiceType(){
		return serviceType;
	}
	public void setServiceType(String serviceType){
		this.serviceType = serviceType;
		markChange("serviceType", serviceType);
	}
	@Column(name = "SERVICE_DISC")
	public String getServiceDisc(){
		return serviceDisc;
	}
	public void setServiceDisc(String serviceDisc){
		this.serviceDisc = serviceDisc;
		markChange("serviceDisc", serviceDisc);
	}
	@Column(name = "AGREEMENT_TYPE")
	public String getAgreementType(){
		return agreementType;
	}
	public void setAgreementType(String agreementType){
		this.agreementType = agreementType;
		markChange("agreementType", agreementType);
	}
	@Column(name = "SERVICE_URL")
	public String getServiceUrl(){
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl){
		this.serviceUrl = serviceUrl;
		markChange("serviceUrl", serviceUrl);
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