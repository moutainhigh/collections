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
 * SM_ROLE表对应的实体类
 */
@Entity
@Table(name = "SM_ROLE")
public class SmRoleBO extends AbsDaoBussinessObject {
	
	public SmRoleBO(){}

	private String roleCode;	
	private String pkSysIntegration;	
	private String roleName;	
	private String roleType;	
	private String roleState;	
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
	@Column(name = "ROLE_CODE")
	public String getRoleCode(){
		return roleCode;
	}
	public void setRoleCode(String roleCode){
		this.roleCode = roleCode;
		markChange("roleCode", roleCode);
	}
	@Column(name = "PK_SYS_INTEGRATION")
	public String getPkSysIntegration(){
		return pkSysIntegration;
	}
	public void setPkSysIntegration(String pkSysIntegration){
		this.pkSysIntegration = pkSysIntegration;
		markChange("pkSysIntegration", pkSysIntegration);
	}
	@Column(name = "ROLE_NAME")
	public String getRoleName(){
		return roleName;
	}
	public void setRoleName(String roleName){
		this.roleName = roleName;
		markChange("roleName", roleName);
	}
	@Column(name = "ROLE_TYPE")
	public String getRoleType(){
		return roleType;
	}
	public void setRoleType(String roleType){
		this.roleType = roleType;
		markChange("roleType", roleType);
	}
	@Column(name = "ROLE_STATE")
	public String getRoleState(){
		return roleState;
	}
	public void setRoleState(String roleState){
		this.roleState = roleState;
		markChange("roleState", roleState);
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