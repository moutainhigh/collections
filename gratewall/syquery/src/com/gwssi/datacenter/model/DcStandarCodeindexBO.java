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
 * DC_STANDAR_CODEINDEX表对应的实体类
 */
@Entity
@Table(name = "DC_STANDAR_CODEINDEX")
public class DcStandarCodeindexBO extends AbsDaoBussinessObject {
	
	public DcStandarCodeindexBO(){}

	private String pkDcStandarCodeindex;	
	private String pkDcStandardSpec;
	private String identifier;	
	private String codeindexName;	
	private String representation;	
	private String version;	
	private String codeTable;	
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
	@Column(name = "PK_DC_STANDAR_CODEINDEX")
	public String getPkDcStandarCodeindex(){
		return pkDcStandarCodeindex;
	}
	public void setPkDcStandarCodeindex(String pkDcStandarCodeindex){
		this.pkDcStandarCodeindex = pkDcStandarCodeindex;
		markChange("pkDcStandarCodeindex", pkDcStandarCodeindex);
	}
	@Column(name = "PK_DC_STANDARD_SPEC")
	public String getPkDcStandardSpec(){
		return pkDcStandardSpec;
	}
	public void setPkDcStandardSpec(String pkDcStandardSpec){
		this.pkDcStandardSpec = pkDcStandardSpec;
		markChange("pkDcStandardSpec", pkDcStandardSpec);
	}
	@Column(name = "IDENTIFIER")
	public String getIdentifier(){
		return identifier;
	}
	public void setIdentifier(String identifier){
		this.identifier = identifier;
		markChange("identifier", identifier);
	}
	@Column(name = "CODEINDEX_NAME")
	public String getCodeindexName(){
		return codeindexName;
	}
	public void setCodeindexName(String codeindexName){
		this.codeindexName = codeindexName;
		markChange("codeindexName", codeindexName);
	}
	@Column(name = "REPRESENTATION")
	public String getRepresentation(){
		return representation;
	}
	public void setRepresentation(String representation){
		this.representation = representation;
		markChange("representation", representation);
	}
	@Column(name = "VERSION")
	public String getVersion(){
		return version;
	}
	public void setVersion(String version){
		this.version = version;
		markChange("version", version);
	}
	@Column(name = "CODE_TABLE")
	public String getCodeTable(){
		return codeTable;
	}
	public void setCodeTable(String codeTable){
		this.codeTable = codeTable;
		markChange("codeTable", codeTable);
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