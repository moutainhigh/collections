package com.gwssi.optimus.demo.domain;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import java.util.Calendar;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import java.io.Serializable;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;

/**
 * BE_WK_ENT Bo 类。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Entity
@Table(name = "BE_WK_ENTDEMO")
public class BeWkEntDemoBO extends AbsDaoBussinessObject implements Serializable{
	
	private static final long serialVersionUID = -8480342967917009981L;

	public BeWkEntDemoBO(){}

	private String entId;		
	private String entName;	
	private String entType;	
	private String regOrg;
	private Calendar appDate;
	private Calendar timestamp;
	
	@Id
	@Column(name = "ENT_ID")
	public String getEntId(){
		return entId;
	}
	public void setEntId(String entId){
		this.entId = entId;
		markChange("entId", entId);
	}
	
	@Column(name = "ENT_NAME")
	public String getEntName(){
		return entName;
	}
	public void setEntName(String entName){
		this.entName = entName;
		markChange("entName", entName);
	}
	@Column(name = "ENT_TYPE")
	public String getEntType(){
		return entType;
	}
	public void setEntType(String entType){
		this.entType = entType;
		markChange("entType", entType);
	}

	@Column(name = "REG_ORG")
	public String getRegOrg(){
		return regOrg;
	}
	public void setRegOrg(String regOrg){
		this.regOrg = regOrg;
		markChange("regOrg", regOrg);
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "APP_DATE")
	public Calendar getAppDate(){
		return appDate;
	}
	public void setAppDate(Calendar appDate){
		this.appDate = appDate;
		markChange("appDate", appDate);
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APP_DATE")
	public Calendar getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Calendar timestamp) {
		this.timestamp = timestamp;
	}
	
	
}