package com.gwssi.datacenter.model;

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
 * DC_PROCEDURE表对应的实体类
 */
@Entity
@Table(name = "DC_PROCEDURE")
public class DcProcedureBO extends AbsDaoBussinessObject {
	
	public DcProcedureBO(){}

	private String pkDcProcedure;	
	private String pkDcDataSource;	
	private String procName;	
	private String procSql;	
	private String procUseDesc;	
	private String isCheck;	
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
	@Column(name = "PK_DC_PROCEDURE")
	public String getPkDcProcedure(){
		return pkDcProcedure;
	}
	public void setPkDcProcedure(String pkDcProcedure){
		this.pkDcProcedure = pkDcProcedure;
		markChange("pkDcProcedure", pkDcProcedure);
	}
	@Column(name = "PK_DC_DATA_SOURCE")
	public String getPkDcDataSource(){
		return pkDcDataSource;
	}
	public void setPkDcDataSource(String pkDcDataSource){
		this.pkDcDataSource = pkDcDataSource;
		markChange("pkDcDataSource", pkDcDataSource);
	}
	@Column(name = "PROC_NAME")
	public String getProcName(){
		return procName;
	}
	public void setProcName(String procName){
		this.procName = procName;
		markChange("procName", procName);
	}
	@Column(name = "PROC_SQL")
	public String getProcSql(){
		return procSql;
	}
	public void setProcSql(String procSql){
		this.procSql = procSql;
		markChange("procSql", procSql);
	}
	@Column(name = "PROC_USE_DESC")
	public String getProcUseDesc(){
		return procUseDesc;
	}
	public void setProcUseDesc(String procUseDesc){
		this.procUseDesc = procUseDesc;
		markChange("procUseDesc", procUseDesc);
	}
	@Column(name = "IS_CHECK")
	public String getIsCheck(){
		return isCheck;
	}
	public void setIsCheck(String isCheck){
		this.isCheck = isCheck;
		markChange("isCheck", isCheck);
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