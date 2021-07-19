package com.gwssi.rodimus.upload.domain;

import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

import java.util.Calendar;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;

/**
 * 上传文件BO。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Entity
@Table(name="BE_WK_UPLOAD_FILE")
public class BeWkUploadFileBO extends AbsDaoBussinessObject{

	private String uploadFileId = "";
	private String categoryId = "";
	private String sn = "";
	private String dataId = "";
	private String dataText = "";
	private String dataSql = "";
	private String dataParams = "";
	private String fileId = "";
	private String fileUrl = "";
	private String thumbFileId = "";
	private String thumbUrl = "";
	private String approveMsg = "";
	private String state = "";
	private String gid = "";
	private String refId = "";
	private String refText = "";
	private Calendar timestamp = null;

	@Id
	@Column(name="UPLOAD_FILE_ID")
	public String getUploadFileId() {
		return uploadFileId;
	}
	public void setUploadFileId(String uploadFileId) {
		this.uploadFileId = uploadFileId;
	}
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
	@Column(name="DATA_ID")
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	@Column(name="DATA_TEXT")
	public String getDataText() {
		return dataText;
	}
	public void setDataText(String dataText) {
		this.dataText = dataText;
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
	@Column(name="FILE_ID")
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	@Column(name="FILE_URL")
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	@Column(name="THUMB_FILE_ID")
	public String getThumbFileId() {
		return thumbFileId;
	}
	public void setThumbFileId(String thumbFileId) {
		this.thumbFileId = thumbFileId;
	}
	@Column(name="THUMB_URL")
	public String getThumbUrl() {
		return thumbUrl;
	}
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	@Column(name="APPROVE_MSG")
	public String getApproveMsg() {
		return approveMsg;
	}
	public void setApproveMsg(String approveMsg) {
		this.approveMsg = approveMsg;
	}
	@Column(name="STATE")
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	
	@Column(name="REF_ID")
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	@Column(name="REF_TEXT")
	public String getRefText() {
		return refText;
	}
	public void setRefText(String refText) {
		this.refText = refText;
	}
	

}
