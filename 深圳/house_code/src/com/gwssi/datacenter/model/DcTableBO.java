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
 * DC_TABLE表对应的实体类
 */
@Entity
@Table(name = "DC_TABLE")
public class DcTableBO extends AbsDaoBussinessObject {
	
	public DcTableBO(){}

	private String pkDcTable;	
	private String pkDcDataSource;
	private String dcTopic;
	private String tableNameEn;	
	private String tableNameCn;	
	private String tableCode;	
	private String tableType;	
	private BigDecimal firstRecordCount;	
	private BigDecimal lastRecordCount;	
	private String tableUseDesc;	
	private String isCheck;
	private String isShare;
	private String isQuery;
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
	@Column(name = "PK_DC_TABLE")
	public String getPkDcTable(){
		return pkDcTable;
	}
	public void setPkDcTable(String pkDcTable){
		this.pkDcTable = pkDcTable;
		markChange("pkDcTable", pkDcTable);
	}
	@Column(name = "PK_DC_DATA_SOURCE")
	public String getPkDcDataSource(){
		return pkDcDataSource;
	}
	public void setPkDcDataSource(String pkDcDataSource){
		this.pkDcDataSource = pkDcDataSource;
		markChange("pkDcDataSource", pkDcDataSource);
	}
	@Column(name = "TABLE_NAME_EN")
	public String getTableNameEn(){
		return tableNameEn;
	}
	public void setTableNameEn(String tableNameEn){
		this.tableNameEn = tableNameEn;
		markChange("tableNameEn", tableNameEn);
	}
	@Column(name = "TABLE_NAME_CN")
	public String getTableNameCn(){
		return tableNameCn;
	}
	public void setTableNameCn(String tableNameCn){
		this.tableNameCn = tableNameCn;
		markChange("tableNameCn", tableNameCn);
	}
	@Column(name = "TABLE_CODE")
	public String getTableCode(){
		return tableCode;
	}
	public void setTableCode(String tableCode){
		this.tableCode = tableCode;
		markChange("tableCode", tableCode);
	}
	@Column(name = "TABLE_TYPE")
	public String getTableType(){
		return tableType;
	}
	public void setTableType(String tableType){
		this.tableType = tableType;
		markChange("tableType", tableType);
	}
	@Column(name = "FIRST_RECORD_COUNT")
	public BigDecimal getFirstRecordCount(){
		return firstRecordCount;
	}
	public void setFirstRecordCount(BigDecimal firstRecordCount){
		this.firstRecordCount = firstRecordCount;
		markChange("firstRecordCount", firstRecordCount);
	}
	@Column(name = "LAST_RECORD_COUNT")
	public BigDecimal getLastRecordCount(){
		return lastRecordCount;
	}
	public void setLastRecordCount(BigDecimal lastRecordCount){
		this.lastRecordCount = lastRecordCount;
		markChange("lastRecordCount", lastRecordCount);
	}
	@Column(name = "TABLE_USE_DESC")
	public String getTableUseDesc(){
		return tableUseDesc;
	}
	public void setTableUseDesc(String tableUseDesc){
		this.tableUseDesc = tableUseDesc;
		markChange("tableUseDesc", tableUseDesc);
	}
	@Column(name = "IS_CHECK")
	public String getIsCheck(){
		return isCheck;
	}
	public void setIsCheck(String isCheck){
		this.isCheck = isCheck;
		markChange("isCheck", isCheck);
	}
	@Column(name = "IS_SHARE")
	public String getIsShare(){
		return isShare;
	}
	public void setIsShare(String isShare){
		this.isShare = isShare;
		markChange("isShare", isShare);
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
	public void setIsQuery(String isQuery) {
		this.isQuery = isQuery;
	}
	@Column(name = "IS_QUERY")
	public String getIsQuery() {
		return isQuery;
	}
	public void setDcTopic(String dcTopic) {
		this.dcTopic = dcTopic;
	}
	@Column(name = "DC_TOPIC")
	public String getDcTopic() {
		return dcTopic;
	}
}