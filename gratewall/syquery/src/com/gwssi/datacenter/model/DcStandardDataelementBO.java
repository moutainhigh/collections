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
 * DC_STANDARD_DATAELEMENT表对应的实体类
 */
@Entity
@Table(name = "DC_STANDARD_DATAELEMENT")
public class DcStandardDataelementBO extends AbsDaoBussinessObject {
	
	public DcStandardDataelementBO(){}

	private String pkDcStandardDataelement;	
	private String pkDcStandardSpec;	
	private String identifier;	
	private String columnNane;	
	private String columnNaneEn;	
	private String columnNaneCn;	
	private String dataType;	
	private BigDecimal dataLength;	
	private String dataFormat;	
	private String columnDesc;	
	private String unit;	
	private String version;	
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
	@Column(name = "PK_DC_STANDARD_DATAELEMENT")
	public String getPkDcStandardDataelement(){
		return pkDcStandardDataelement;
	}
	public void setPkDcStandardDataelement(String pkDcStandardDataelement){
		this.pkDcStandardDataelement = pkDcStandardDataelement;
		markChange("pkDcStandardDataelement", pkDcStandardDataelement);
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
	@Column(name = "COLUMN_NANE")
	public String getColumnNane(){
		return columnNane;
	}
	public void setColumnNane(String columnNane){
		this.columnNane = columnNane;
		markChange("columnNane", columnNane);
	}
	@Column(name = "COLUMN_NANE_EN")
	public String getColumnNaneEn(){
		return columnNaneEn;
	}
	public void setColumnNaneEn(String columnNaneEn){
		this.columnNaneEn = columnNaneEn;
		markChange("columnNaneEn", columnNaneEn);
	}
	@Column(name = "COLUMN_NANE_CN")
	public String getColumnNaneCn(){
		return columnNaneCn;
	}
	public void setColumnNaneCn(String columnNaneCn){
		this.columnNaneCn = columnNaneCn;
		markChange("columnNaneCn", columnNaneCn);
	}
	@Column(name = "DATA_TYPE")
	public String getDataType(){
		return dataType;
	}
	public void setDataType(String dataType){
		this.dataType = dataType;
		markChange("dataType", dataType);
	}
	@Column(name = "DATA_LENGTH")
	public BigDecimal getDataLength(){
		return dataLength;
	}
	public void setDataLength(BigDecimal dataLength){
		this.dataLength = dataLength;
		markChange("dataLength", dataLength);
	}
	@Column(name = "DATA_FORMAT")
	public String getDataFormat(){
		return dataFormat;
	}
	public void setDataFormat(String dataFormat){
		this.dataFormat = dataFormat;
		markChange("dataFormat", dataFormat);
	}
	@Column(name = "COLUMN_DESC")
	public String getColumnDesc(){
		return columnDesc;
	}
	public void setColumnDesc(String columnDesc){
		this.columnDesc = columnDesc;
		markChange("columnDesc", columnDesc);
	}
	@Column(name = "UNIT")
	public String getUnit(){
		return unit;
	}
	public void setUnit(String unit){
		this.unit = unit;
		markChange("unit", unit);
	}
	@Column(name = "VERSION")
	public String getVersion(){
		return version;
	}
	public void setVersion(String version){
		this.version = version;
		markChange("version", version);
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