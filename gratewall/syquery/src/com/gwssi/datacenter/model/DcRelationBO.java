package com.gwssi.datacenter.model;



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
 * DC_RELATION表对应的实体类
 */
@Entity
@Table(name = "DC_RELATION")
public class DcRelationBO extends AbsDaoBussinessObject {
	
	public DcRelationBO(){}

	private String pkDcRelation;	
	private String pkDcDataSource;
	private String pkDcTable;
	private String tableNameEn;	
	private String tableNameCn;	
	private String columnNameEn;	
	private String pkDcDataSourceRelation;
	private String pkDcTableRelation;
	private String tableNameEnRelation;	
	private String tableNameCnRelation;	
	private String columnNameEnRelation;	
	private String columnNameCnRelation;	
	private String columnNameCn;	
	private String createrId;	
	private String createrName;	
	private Calendar createrTime;	
	private String modifierId;	
	private String modifierName;	
	private Calendar modifierTime;	
	private String effectiveMarker;
	
	@Id
	@Column(name = "PK_DC_RELATION")
	public String getPkDcRelation(){
		return pkDcRelation;
	}
	public void setPkDcRelation(String pkDcRelation){
		this.pkDcRelation = pkDcRelation;
		markChange("pkDcRelation", pkDcRelation);
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
	@Column(name = "COLUMN_NAME_EN")
	public String getColumnNameEn(){
		return columnNameEn;
	}
	public void setColumnNameEn(String columnNameEn){
		this.columnNameEn = columnNameEn;
		markChange("columnNameEn", columnNameEn);
	}
	@Column(name = "PK_DC_DATA_SOURCE_RELATION")
	public String getPkDcDataSourceRelation(){
		return pkDcDataSourceRelation;
	}
	public void setPkDcDataSourceRelation(String pkDcDataSourceRelation){
		this.pkDcDataSourceRelation = pkDcDataSourceRelation;
		markChange("pkDcDataSourceRelation", pkDcDataSourceRelation);
	}
	@Column(name = "PK_DC_TABLE_RELATION")
	public String getPkDcTableRelation(){
		return pkDcTableRelation;
	}
	public void setPkDcTableRelation(String pkDcTableRelation){
		this.pkDcTableRelation = pkDcTableRelation;
		markChange("pkDcTableRelation", pkDcTableRelation);
	}
	@Column(name = "TABLE_NAME_EN_RELATION")
	public String getTableNameEnRelation(){
		return tableNameEnRelation;
	}
	public void setTableNameEnRelation(String tableNameEnRelation){
		this.tableNameEnRelation = tableNameEnRelation;
		markChange("tableNameEnRelation", tableNameEnRelation);
	}
	@Column(name = "TABLE_NAME_CN_RELATION")
	public String getTableNameCnRelation(){
		return tableNameCnRelation;
	}
	public void setTableNameCnRelation(String tableNameCnRelation){
		this.tableNameCnRelation = tableNameCnRelation;
		markChange("tableNameCnRelation", tableNameCnRelation);
	}
	@Column(name = "COLUMN_NAME_EN_RELATION")
	public String getColumnNameEnRelation(){
		return columnNameEnRelation;
	}
	public void setColumnNameEnRelation(String columnNameEnRelation){
		this.columnNameEnRelation = columnNameEnRelation;
		markChange("columnNameEnRelation", columnNameEnRelation);
	}
	@Column(name = "COLUMN_NAME_CN_RELATION")
	public String getColumnNameCnRelation(){
		return columnNameCnRelation;
	}
	public void setColumnNameCnRelation(String columnNameCnRelation){
		this.columnNameCnRelation = columnNameCnRelation;
		markChange("columnNameCnRelation", columnNameCnRelation);
	}
	@Column(name = "COLUMN_NAME_CN")
	public String getColumnNameCn(){
		return columnNameCn;
	}
	public void setColumnNameCn(String columnNameCn){
		this.columnNameCn = columnNameCn;
		markChange("columnNameCn", columnNameCn);
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
	@Column(name = "EFFECTIVE_MARKER")
	public String getEffectiveMarker(){
		return effectiveMarker;
	}
	public void setEffectiveMarker(String effectiveMarker){
		this.effectiveMarker = effectiveMarker;
		markChange("effectiveMarker", effectiveMarker);
	}
}