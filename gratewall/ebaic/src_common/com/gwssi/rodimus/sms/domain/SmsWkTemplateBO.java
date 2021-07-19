package com.gwssi.rodimus.sms.domain;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

import java.util.Calendar;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;

/**
 * SMS_WK_TEMPLATE表对应的实体类
 */
@Entity
@Table(name = "SMS_WK_TEMPLATE")
public class SmsWkTemplateBO extends AbsDaoBussinessObject {
	
	public SmsWkTemplateBO(){}

	private String id;		
	private String busiType;
	private String subBusiTypeCode;	
	private String subBusiType;	
	private String conetnt;	
	private String priority;
	private String flag;	
	private Calendar timestamp;	
	
	@Id
	@Column(name = "ID")
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
		markChange("id", id);
	}
	
	@Column(name = "BUSI_TYPE")
	public String getBusiType(){
		return busiType;
	}
	public void setBusiType(String busiType){
		this.busiType = busiType;
		markChange("busiType", busiType);
	}
	
	@Column(name = "SUB_BUSI_TYPE_CODE")
	public String getSubBusiTypeCode(){
		return subBusiTypeCode;
	}
	public void setSubBusiTypeCode(String subBusiTypeCode){
		this.subBusiTypeCode = subBusiTypeCode;
		markChange("subBusiTypeCode", subBusiTypeCode);
	}
	
	@Column(name = "SUB_BUSI_TYPE")
	public String getSubBusiType(){
		return subBusiType;
	}
	public void setSubBusiType(String subBusiType){
		this.subBusiType = subBusiType;
		markChange("subBusiType", subBusiType);
	}
	
	@Column(name = "CONETNT")
	public String getConetnt(){
		return conetnt;
	}
	public void setConetnt(String conetnt){
		this.conetnt = conetnt;
		markChange("conetnt", conetnt);
	}
	
	@Column(name = "PRIORITY")
	public String getPriority(){
		return priority;
	}
	public void setPriority(String priority){
		this.priority = priority;
		markChange("priority", priority);
	}
	
	@Column(name = "FLAG")
	public String getFlag(){
		return flag;
	}
	public void setFlag(String flag){
		this.flag = flag;
		markChange("flag", flag);
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TIMESTAMP")
	public Calendar getTimestamp(){
		return timestamp;
	}
	public void setTimestamp(Calendar timestamp){
		this.timestamp = timestamp;
		markChange("timestamp", timestamp);
	}
	
}