package com.gwssi.application.model;

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
 * SM_NOTICE表对应的实体类
 */
@Entity
@Table(name = "SM_NOTICE")
public class SmNoticeBO extends AbsDaoBussinessObject {
	
	public SmNoticeBO(){}

	private String pkNotice;	
	private String title;	
	private String content;	
	private Calendar effectiveTime;	
	private String sendTo;	
	private String effectiveMarker;	
	private String createrId;	
	private String createrName;	
	private Calendar createrTime;	
	private String modifierId;	
	private String modifierName;	
	private Calendar modifierTime;	
	
	@Id
	@Column(name = "PK_NOTICE")
	public String getPkNotice(){
		return pkNotice;
	}
	public void setPkNotice(String pkNotice){
		this.pkNotice = pkNotice;
		markChange("pkNotice", pkNotice);
	}
	@Column(name = "TITLE")
	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title = title;
		markChange("title", title);
	}
	@Column(name = "CONTENT")
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content = content;
		markChange("content", content);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EFFECTIVE_TIME")
	public Calendar getEffectiveTime(){
		return effectiveTime;
	}
	public void setEffectiveTime(Calendar effectiveTime){
		this.effectiveTime = effectiveTime;
		markChange("effectiveTime", effectiveTime);
	}
	@Column(name = "SEND_TO")
	public String getSendTo(){
		return sendTo;
	}
	public void setSendTo(String sendTo){
		this.sendTo = sendTo;
		markChange("sendTo", sendTo);
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