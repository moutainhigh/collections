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
 * SM_FUNCTION表对应的实体类
 */
@Entity
@Table(name = "SM_FUNCTION")
public class SmFunctionBO extends AbsDaoBussinessObject {
	
	public SmFunctionBO(){}

	private String functionCode;	
	private String pkSysIntegration;	
	private String functionName;	
	private String functionNameShort;	
	private String functionType;	
	private String superFuncCode;	
	private String functionUrl;	
	private String levelCode;	
	private BigDecimal orderNo;	
	private String remarks;	
	private String effectiveMarker;	
	private String createrId;	
	private String createrName;	
	private Calendar createrTime;	
	private String modifierId;	
	private String modifierName;	
	private Calendar modifierTime;	
	
	@Id
	@Column(name = "FUNCTION_CODE")
	public String getFunctionCode(){
		return functionCode;
	}
	public void setFunctionCode(String functionCode){
		this.functionCode = functionCode;
		markChange("functionCode", functionCode);
	}
	@Column(name = "PK_SYS_INTEGRATION")
	public String getPkSysIntegration(){
		return pkSysIntegration;
	}
	public void setPkSysIntegration(String pkSysIntegration){
		this.pkSysIntegration = pkSysIntegration;
		markChange("pkSysIntegration", pkSysIntegration);
	}
	@Column(name = "FUNCTION_NAME")
	public String getFunctionName(){
		return functionName;
	}
	public void setFunctionName(String functionName){
		this.functionName = functionName;
		markChange("functionName", functionName);
	}
	@Column(name = "FUNCTION_NAME_SHORT")
	public String getFunctionNameShort(){
		return functionNameShort;
	}
	public void setFunctionNameShort(String functionNameShort){
		this.functionNameShort = functionNameShort;
		markChange("functionNameShort", functionNameShort);
	}
	@Column(name = "FUNCTION_TYPE")
	public String getFunctionType(){
		return functionType;
	}
	public void setFunctionType(String functionType){
		this.functionType = functionType;
		markChange("functionType", functionType);
	}
	@Column(name = "SUPER_FUNC_CODE")
	public String getSuperFuncCode(){
		return superFuncCode;
	}
	public void setSuperFuncCode(String superFuncCode){
		this.superFuncCode = superFuncCode;
		markChange("superFuncCode", superFuncCode);
	}
	@Column(name = "FUNCTION_URL")
	public String getFunctionUrl(){
		return functionUrl;
	}
	public void setFunctionUrl(String functionUrl){
		this.functionUrl = functionUrl;
		markChange("functionUrl", functionUrl);
	}
	@Column(name = "LEVEL_CODE")
	public String getLevelCode(){
		return levelCode;
	}
	public void setLevelCode(String levelCode){
		this.levelCode = levelCode;
		markChange("levelCode", levelCode);
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