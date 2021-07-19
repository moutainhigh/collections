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
 * SMS_WK_MESSAGE表对应的实体类
 */
@Entity
@Table(name = "SMS_WK_MESSAGE")
public class SmsWkMessageBO extends AbsDaoBussinessObject {
	
	public SmsWkMessageBO(){}

	private String id;	
	private String templateId;	
	private String mobile;	
	private String content;	
	private String flag;	
	private Calendar sentTime;	
	private Calendar createTime;	
	private String priority;	
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
	
	public String busiType;
	@Column(name = "BUSI_TYPE")
	public String getBusiType(){
		return busiType;
	}
	public void setBusiType(String busiType){
		this.busiType = busiType;
		markChange("busiType", busiType);
	}
	
	@Column(name = "TEMPLATE_ID")
	public String getTemplateId(){
		return templateId;
	}
	public void setTemplateId(String templateId){
		this.templateId = templateId;
		markChange("templateId", templateId);
	}
	@Column(name = "MOBILE")
	public String getMobile(){
		return mobile;
	}
	public void setMobile(String mobile){
		this.mobile = mobile;
		markChange("mobile", mobile);
	}
	@Column(name = "CONTENT")
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content = content;
		markChange("content", content);
	}
	@Column(name = "FLAG")
	public String getFlag(){
		return flag;
	}
	public void setFlag(String flag){
		this.flag = flag;
		markChange("flag", flag);
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "SENT_TIME")
	public Calendar getSentTime(){
		return sentTime;
	}
	public void setSentTime(Calendar sentTime){
		this.sentTime = sentTime;
		markChange("sentTime", sentTime);
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME")
	public Calendar getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Calendar createTime){
		this.createTime = createTime;
		markChange("createTime", createTime);
	}
	@Column(name = "PRIORITY")
	public String getPriority(){
		return priority;
	}
	public void setPriority(String priority){
		this.priority = priority;
		markChange("priority", priority);
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
	
	public String gid;
	@Column(name = "GID")
	public String getGid(){
		return gid;
	}
	public void setGid(String gid){
		this.gid = gid;
		markChange("gid", gid);
	}
}