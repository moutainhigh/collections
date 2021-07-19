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
 * DC_CHANGE表对应的实体类
 */
@Entity
@Table(name = "DC_CHANGE")
public class DcChangeBO extends AbsDaoBussinessObject {
	
	public DcChangeBO(){}

	private String pkDcChange;	
	private String pkDcDataSource;	
	private String tableNameEn;	
	private String tableNameCn;	
	private String columnNameEn;	
	private String columnNameCn;	
	private String changeItem;	
	private String changeBefore;	
	private String changeAfter;	
	private Calendar changeTime;	
	private String changeReason;	
	private String createrId;	
	private String createrName;	
	private Calendar createrTime;	
	private String modifierId;	
	private String modifierName;	
	private Calendar modifierTime;	
	
	@Id
	@Column(name = "PK_DC_CHANGE")
	public String getPkDcChange(){
		return pkDcChange;
	}
	public void setPkDcChange(String pkDcChange){
		this.pkDcChange = pkDcChange;
		markChange("pkDcChange", pkDcChange);
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
	@Column(name = "CHANGE_ITEM")
	public String getChangeItem(){
		return changeItem;
	}
	public void setChangeItem(String changeItem){
		this.changeItem = changeItem;
		markChange("changeItem", changeItem);
	}
	@Column(name = "CHANGE_BEFORE")
	public String getChangeBefore(){
		return changeBefore;
	}
	public void setChangeBefore(String changeBefore){
		this.changeBefore = changeBefore;
		markChange("changeBefore", changeBefore);
	}
	@Column(name = "CHANGE_AFTER")
	public String getChangeAfter(){
		return changeAfter;
	}
	public void setChangeAfter(String changeAfter){
		this.changeAfter = changeAfter;
		markChange("changeAfter", changeAfter);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHANGE_TIME")
	public Calendar getChangeTime(){
		return changeTime;
	}
	public void setChangeTime(Calendar changeTime){
		this.changeTime = changeTime;
		markChange("changeTime", changeTime);
	}
	@Column(name = "CHANGE_REASON")
	public String getChangeReason(){
		return changeReason;
	}
	public void setChangeReason(String changeReason){
		this.changeReason = changeReason;
		markChange("changeReason", changeReason);
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