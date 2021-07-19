package com.report.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "aa_yuan_ye_info")
public class FileDown {

	private String id;
	private String createTime;
	private String dateQueryTime;
	private Integer types; // 0自动，1手动
	private String fileId;
	private Integer dataStatus; // 0采集中，1采集完成;

	@Column
	public String getCreateTime() {
		return createTime;
	}
	@Column
	public Integer getDataStatus() {
		return dataStatus;
	}

	@Column
	public String getDateQueryTime() {
		return dateQueryTime;
	}

	
	@Column
	public String getFileId() {
		return fileId;
	}
	@Id
	@GeneratedValue(generator = "myId")
	@GenericGenerator(name = "myId", strategy = "uuid")
	public String getId() {
		return id;
	}
	@Column
	public Integer getTypes() {
		return types;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setDataStatus(Integer dataStatus) {
		this.dataStatus = dataStatus;
	}

	public void setDateQueryTime(String dateQueryTime) {
		this.dateQueryTime = dateQueryTime;
	}


	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTypes(Integer types) {
		this.types = types;
	}

}
