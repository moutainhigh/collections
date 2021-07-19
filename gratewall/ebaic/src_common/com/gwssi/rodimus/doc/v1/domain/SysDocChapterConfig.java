package com.gwssi.rodimus.doc.v1.domain;

import java.util.Calendar;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Entity
@Table(name = "SYS_DOC_CHAPTER_CONFIG")
public class SysDocChapterConfig extends AbsDaoBussinessObject{

	private String chapConfigId = "";
	private String title = "";
	private String templateUrl = "";
	private String flag = "";
	private Calendar timestamp = null;

	@Id
	@Column(name = "CHAP_CONFIG_ID")
	public String getChapConfigId() {
		return chapConfigId;
	}
	public void setChapConfigId(String chapConfigId) {
		this.chapConfigId = chapConfigId;
	}
	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name = "TEMPLATE_URL")
	public String getTemplateUrl() {
		return templateUrl;
	}
	public void setTemplateUrl(String templateUrl) {
		this.templateUrl = templateUrl;
	}
	
	@Column(name = "FLAG")
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TIMESTAMP")
	public Calendar getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Calendar timestamp) {
		this.timestamp = timestamp;
	}
	
}
