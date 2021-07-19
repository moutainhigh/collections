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
 * DC_COLUMN表对应的实体类
 */
@Entity
@Table(name = "DC_COLUMN")
public class DcColumnBO extends AbsDaoBussinessObject {
	
	public DcColumnBO(){}

	private String pkDcColumn;	
	private String pkDcDataSource;	
	private String pkDcTable;	
	private String columnNameEn;	
	private String columnNameCn;	
	private String columnCode;	
	private String columnType;	
	private BigDecimal columnLength;	
	private String isNull;	
	private String isPrimaryKey;	
	private String isIndex;	
	private String isOrderBy;	
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
	private String isStandard;	
	private String standardCode;	
	private String isCodedata;	
	private String pkDcStandardDataelement;	
	private String pkDcStandarCodeindex;	
	private String dcDmId;	
	
	@Id
	@Column(name = "PK_DC_COLUMN")
	public String getPkDcColumn(){
		return pkDcColumn;
	}
	public void setPkDcColumn(String pkDcColumn){
		this.pkDcColumn = pkDcColumn;
		markChange("pkDcColumn", pkDcColumn);
	}
	@Column(name = "PK_DC_DATA_SOURCE")
	public String getPkDcDataSource(){
		return pkDcDataSource;
	}
	public void setPkDcDataSource(String pkDcDataSource){
		this.pkDcDataSource = pkDcDataSource;
		markChange("pkDcDataSource", pkDcDataSource);
	}
	@Column(name = "PK_DC_TABLE")
	public String getPkDcTable(){
		return pkDcTable;
	}
	public void setPkDcTable(String pkDcTable){
		this.pkDcTable = pkDcTable;
		markChange("pkDcTable", pkDcTable);
	}
	@Column(name = "COLUMN_NAME_EN")
	public String getColumnNameEn(){
		return columnNameEn;
	}
	public void setColumnNameEn(String columnNameEn){
		this.columnNameEn = columnNameEn;
		markChange("columnNameEn", columnNameEn);
	}
	@Column(name = "COLUMN_NAME_CN")
	public String getColumnNameCn(){
		return columnNameCn;
	}
	public void setColumnNameCn(String columnNameCn){
		this.columnNameCn = columnNameCn;
		markChange("columnNameCn", columnNameCn);
	}
	@Column(name = "COLUMN_CODE")
	public String getColumnCode(){
		return columnCode;
	}
	public void setColumnCode(String columnCode){
		this.columnCode = columnCode;
		markChange("columnCode", columnCode);
	}
	@Column(name = "COLUMN_TYPE")
	public String getColumnType(){
		return columnType;
	}
	public void setColumnType(String columnType){
		this.columnType = columnType;
		markChange("columnType", columnType);
	}
	@Column(name = "COLUMN_LENGTH")
	public BigDecimal getColumnLength(){
		return columnLength;
	}
	public void setColumnLength(BigDecimal columnLength){
		this.columnLength = columnLength;
		markChange("columnLength", columnLength);
	}
	@Column(name = "IS_NULL")
	public String getIsNull(){
		return isNull;
	}
	public void setIsNull(String isNull){
		this.isNull = isNull;
		markChange("isNull", isNull);
	}
	@Column(name = "IS_PRIMARY_KEY")
	public String getIsPrimaryKey(){
		return isPrimaryKey;
	}
	public void setIsPrimaryKey(String isPrimaryKey){
		this.isPrimaryKey = isPrimaryKey;
		markChange("isPrimaryKey", isPrimaryKey);
	}
	@Column(name = "IS_INDEX")
	public String getIsIndex(){
		return isIndex;
	}
	public void setIsIndex(String isIndex){
		this.isIndex = isIndex;
		markChange("isIndex", isIndex);
	}
	@Column(name = "IS_ORDER_BY")
	public String getIsOrderBy(){
		return isOrderBy;
	}
	public void setIsOrderBy(String isOrderBy){
		this.isOrderBy = isOrderBy;
		markChange("isOrderBy", isOrderBy);
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
	@Column(name = "IS_STANDARD")
	public String getIsStandard(){
		return isStandard;
	}
	public void setIsStandard(String isStandard){
		this.isStandard = isStandard;
		markChange("isStandard", isStandard);
	}
	@Column(name = "STANDARD_CODE")
	public String getStandardCode(){
		return standardCode;
	}
	public void setStandardCode(String standardCode){
		this.standardCode = standardCode;
		markChange("standardCode", standardCode);
	}
	@Column(name = "IS_CODEDATA")
	public String getIsCodedata(){
		return isCodedata;
	}
	public void setIsCodedata(String isCodedata){
		this.isCodedata = isCodedata;
		markChange("isCodedata", isCodedata);
	}
	@Column(name = "PK_DC_STANDARD_DATAELEMENT")
	public String getPkDcStandardDataelement(){
		return pkDcStandardDataelement;
	}
	public void setPkDcStandardDataelement(String pkDcStandardDataelement){
		this.pkDcStandardDataelement = pkDcStandardDataelement;
		markChange("pkDcStandardDataelement", pkDcStandardDataelement);
	}
	@Column(name = "PK_DC_STANDAR_CODEINDEX")
	public String getPkDcStandarCodeindex(){
		return pkDcStandarCodeindex;
	}
	public void setPkDcStandarCodeindex(String pkDcStandarCodeindex){
		this.pkDcStandarCodeindex = pkDcStandarCodeindex;
		markChange("pkDcStandarCodeindex", pkDcStandarCodeindex);
	}
	@Column(name = "DC_DM_ID")
	public String getDcDmId(){
		return dcDmId;
	}
	public void setDcDmId(String dcDmId){
		this.dcDmId = dcDmId;
		markChange("dcDmId", dcDmId);
	}
}