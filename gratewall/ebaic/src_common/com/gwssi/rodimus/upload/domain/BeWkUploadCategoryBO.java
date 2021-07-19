package com.gwssi.rodimus.upload.domain;

import java.util.Calendar;
import java.util.List;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * 文件上传BO。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Entity
@Table(name="BE_WK_UPLOAD_CATEGORY")
public class BeWkUploadCategoryBO  extends AbsDaoBussinessObject{

	private String categoryId = "";
	private String sn = "";
	private String title = "";
	private String sampleUrl = "";
	private String rule = "";
	private String uploadConfigId = "";
	private String gid = "";
	private Calendar timestamp = null;

	private List<BeWkUploadFileBO> files = null;
	
	
	@Id
	@Column(name="CATEGORY_ID")
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name="SN")
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@Column(name="TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="SAMPLE_URL")
	public String getSampleUrl() {
		return sampleUrl;
	}

	public void setSampleUrl(String sampleUrl) {
		this.sampleUrl = sampleUrl;
	}

	@Column(name="RULE")
	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	@Column(name="UPLOAD_CONFIG_ID")
	public String getUploadConfigId() {
		return uploadConfigId;
	}

	public void setUploadConfigId(String uploadConfigId) {
		this.uploadConfigId = uploadConfigId;
	}

	@Column(name="GID")
	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TIMESTAMP")
	public Calendar getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Calendar timestamp) {
		this.timestamp = timestamp;
	}

	
	public List<BeWkUploadFileBO> getFiles() {
		return files;
	}

	public void setFiles(List<BeWkUploadFileBO> files) {
		this.files = files;
	}

}
