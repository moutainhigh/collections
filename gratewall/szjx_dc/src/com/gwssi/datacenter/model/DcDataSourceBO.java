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
 * DC_DATA_SOURCE表对应的实体类
 */
@Entity
@Table(name = "DC_DATA_SOURCE")
public class DcDataSourceBO extends AbsDaoBussinessObject {
	
	public DcDataSourceBO(){}

	private String pkDcDataSource;	
	private String pkDcBusiObject;	
	private String busiSys;	
	private String dataSourceName;	
	private String dataSourceType;	
	private String dbType;	
	private String dataSourceIp;	
	private String accessPort;	
	private String dbInstance;	
	private String userName;	
	private String pwd;	
	private String dataSourceStatus;	
	private BigDecimal orderNo;	
	private String effectiveMarker;	
	private String remarks;	
	private String createrId;	
	private String createrName;	
	private Calendar createrTime;	
	private String modifierId;	
	private String modifierName;	
	private Calendar modifierTime;	
	private String firstLoaderId;	
	private String firstLoaderName;	
	private Calendar firstLoadTime;	
	private String lastModifierId;	
	private String lastModifierName;	
	private Calendar lastLoadTime;	
	
	@Id
	@Column(name = "PK_DC_DATA_SOURCE")
	public String getPkDcDataSource(){
		return pkDcDataSource;
	}
	public void setPkDcDataSource(String pkDcDataSource){
		this.pkDcDataSource = pkDcDataSource;
		markChange("pkDcDataSource", pkDcDataSource);
	}
	@Column(name = "PK_DC_BUSI_OBJECT")
	public String getPkDcBusiObject(){
		return pkDcBusiObject;
	}
	public void setPkDcBusiObject(String pkDcBusiObject){
		this.pkDcBusiObject = pkDcBusiObject;
		markChange("pkDcBusiObject", pkDcBusiObject);
	}
	@Column(name = "BUSI_SYS")
	public String getBusiSys(){
		return busiSys;
	}
	public void setBusiSys(String busiSys){
		this.busiSys = busiSys;
		markChange("busiSys", busiSys);
	}
	@Column(name = "DATA_SOURCE_NAME")
	public String getDataSourceName(){
		return dataSourceName;
	}
	public void setDataSourceName(String dataSourceName){
		this.dataSourceName = dataSourceName;
		markChange("dataSourceName", dataSourceName);
	}
	@Column(name = "DATA_SOURCE_TYPE")
	public String getDataSourceType(){
		return dataSourceType;
	}
	public void setDataSourceType(String dataSourceType){
		this.dataSourceType = dataSourceType;
		markChange("dataSourceType", dataSourceType);
	}
	@Column(name = "DB_TYPE")
	public String getDbType(){
		return dbType;
	}
	public void setDbType(String dbType){
		this.dbType = dbType;
		markChange("dbType", dbType);
	}
	@Column(name = "DATA_SOURCE_IP")
	public String getDataSourceIp(){
		return dataSourceIp;
	}
	public void setDataSourceIp(String dataSourceIp){
		this.dataSourceIp = dataSourceIp;
		markChange("dataSourceIp", dataSourceIp);
	}
	@Column(name = "ACCESS_PORT")
	public String getAccessPort(){
		return accessPort;
	}
	public void setAccessPort(String accessPort){
		this.accessPort = accessPort;
		markChange("accessPort", accessPort);
	}
	@Column(name = "DB_INSTANCE")
	public String getDbInstance(){
		return dbInstance;
	}
	public void setDbInstance(String dbInstance){
		this.dbInstance = dbInstance;
		markChange("dbInstance", dbInstance);
	}
	@Column(name = "USER_NAME")
	public String getUserName(){
		return userName;
	}
	public void setUserName(String userName){
		this.userName = userName;
		markChange("userName", userName);
	}
	@Column(name = "PWD")
	public String getPwd(){
		return pwd;
	}
	public void setPwd(String pwd){
		this.pwd = pwd;
		markChange("pwd", pwd);
	}
	@Column(name = "DATA_SOURCE_STATUS")
	public String getDataSourceStatus(){
		return dataSourceStatus;
	}
	public void setDataSourceStatus(String dataSourceStatus){
		this.dataSourceStatus = dataSourceStatus;
		markChange("dataSourceStatus", dataSourceStatus);
	}
	@Column(name = "ORDER_NO")
	public BigDecimal getOrderNo(){
		return orderNo;
	}
	public void setOrderNo(BigDecimal orderNo){
		this.orderNo = orderNo;
		markChange("orderNo", orderNo);
	}
	@Column(name = "EFFECTIVE_MARKER")
	public String getEffectiveMarker(){
		return effectiveMarker;
	}
	public void setEffectiveMarker(String effectiveMarker){
		this.effectiveMarker = effectiveMarker;
		markChange("effectiveMarker", effectiveMarker);
	}
	@Column(name = "REMARKS")
	public String getRemarks(){
		return remarks;
	}
	public void setRemarks(String remarks){
		this.remarks = remarks;
		markChange("remarks", remarks);
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
	@Column(name = "FIRST_LOADER_ID")
	public String getFirstLoaderId(){
		return firstLoaderId;
	}
	public void setFirstLoaderId(String firstLoaderId){
		this.firstLoaderId = firstLoaderId;
		markChange("firstLoaderId", firstLoaderId);
	}
	@Column(name = "FIRST_LOADER_NAME")
	public String getFirstLoaderName(){
		return firstLoaderName;
	}
	public void setFirstLoaderName(String firstLoaderName){
		this.firstLoaderName = firstLoaderName;
		markChange("firstLoaderName", firstLoaderName);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FIRST_LOAD_TIME")
	public Calendar getFirstLoadTime(){
		return firstLoadTime;
	}
	public void setFirstLoadTime(Calendar firstLoadTime){
		this.firstLoadTime = firstLoadTime;
		markChange("firstLoadTime", firstLoadTime);
	}
	@Column(name = "LAST_MODIFIER_ID")
	public String getLastModifierId(){
		return lastModifierId;
	}
	public void setLastModifierId(String lastModifierId){
		this.lastModifierId = lastModifierId;
		markChange("lastModifierId", lastModifierId);
	}
	@Column(name = "LAST_MODIFIER_NAME")
	public String getLastModifierName(){
		return lastModifierName;
	}
	public void setLastModifierName(String lastModifierName){
		this.lastModifierName = lastModifierName;
		markChange("lastModifierName", lastModifierName);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_LOAD_TIME")
	public Calendar getLastLoadTime(){
		return lastLoadTime;
	}
	public void setLastLoadTime(Calendar lastLoadTime){
		this.lastLoadTime = lastLoadTime;
		markChange("lastLoadTime", lastLoadTime);
	}
}