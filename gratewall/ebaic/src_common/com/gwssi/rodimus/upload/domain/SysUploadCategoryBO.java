package com.gwssi.rodimus.upload.domain;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * 上传文件配置BO。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Entity
@Table(name="SYS_UPLOAD_CATEGORY")
public class SysUploadCategoryBO extends AbsDaoBussinessObject{

	private String uploadId = "";
	private String title = "";
	private String sampleUrl = "";
	private String dataSql = "";
	private String dataParams = "";
	private String rule = "";
	private String flag = "";

	@Id
	@Column(name="UPLOAD_ID")
	public String getUploadId() {
		return uploadId;
	}
	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
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
	@Column(name="DATA_SQL")
	public String getDataSql() {
		return dataSql;
	}
	public void setDataSql(String dataSql) {
		this.dataSql = dataSql;
	}
	@Column(name="DATA_PARAMS")
	public String getDataParams() {
		return dataParams;
	}
	public void setDataParams(String dataParams) {
		this.dataParams = dataParams;
	}
	@Column(name="RULE")
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	@Column(name="FLAG")
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
