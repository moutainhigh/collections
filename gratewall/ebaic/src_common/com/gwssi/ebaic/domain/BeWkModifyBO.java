package com.gwssi.ebaic.domain;

import java.math.BigDecimal;
import java.util.Calendar;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * 工作库 - 修改记录表。
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
@Entity
@Table(name = "BE_WK_MODIFY")
public class BeWkModifyBO extends AbsDaoBussinessObject{

	private String modifyId;
	private BigDecimal version;
	private String appUserId;
	private String approveUserId;
	private String tableName;
	private String tablePk;
	private String tableField;
	private String after;
	private String before;
	private Calendar timestamp;		
	private String gid;	
	
	@Id
	@Column(name = "MODIFY_ID")
	public String getModifyId(){
		return modifyId;
	}
	public void setModifyId(String modifyId){
		this.modifyId = modifyId;
		markChange("modifyId", modifyId);
	}

	@Column(name = "APP_USER_ID")
	public String getAppUserId() {
		return appUserId;
	}
	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
		markChange("appUserId", appUserId);
	}
	@Column(name = "APPROVE_USER_ID")
	public String getApproveUserId() {
		return approveUserId;
	}
	public void setApproveUserId(String approveUserId) {
		this.approveUserId = approveUserId;
		markChange("approveUserId", approveUserId);
	}
	@Column(name = "TABLE_NAME")
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
		markChange("tableName", tableName);
	}
	@Column(name = "TABLE_PK")
	public String getTablePk() {
		return tablePk;
	}
	public void setTablePk(String tablePk) {
		this.tablePk = tablePk;
		markChange("tablePk", tablePk);
	}
	@Column(name = "TABLE_FIELD")
	public String getTableField() {
		return tableField;
	}
	public void setTableField(String tableField) {
		this.tableField = tableField;
		markChange("tableField", tableField);
	}
	@Column(name = "AFTER")
	public String getAfter() {
		return after;
	}
	public void setAfter(String after) {
		this.after = after;
		markChange("after", after);
	}
	@Column(name = "BEFORE")
	public String getBefore() {
        return before;
    }
    public void setBefore(String before) {
        this.before = before;
        markChange("before", before);
    }
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TIMESTAMP")
	public Calendar getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Calendar timestamp) {
		this.timestamp = timestamp;
		markChange("timestamp", timestamp);
	}
	@Column(name = "GID")
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
		markChange("gid", gid);
	}
	@Column(name = "VERSION")
	public BigDecimal getVersion() {
		return version;
	}
	public void setVersion(BigDecimal version) {
		this.version = version;
		markChange("version", version);
	}
}
