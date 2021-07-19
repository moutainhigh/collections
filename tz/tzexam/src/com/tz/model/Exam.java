/**
 * Project Name:tzexam
 * File Name:Subject.java
 * Package Name:com.tz.model
 * Date:2015年10月23日下午10:41:34
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.
 *
 */

package com.tz.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * ClassName:Subject <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年10月23日 下午10:41:34 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.6
 * @see
 */
@Entity
@Table(name = "tz_exam")
public class Exam implements java.io.Serializable {

	// 主键
	private Integer id;
	// 文件名称
	private String name;
	// 后缀
	private String ext;
	// 文件大小
	private String size;
	// 文件描述
	private String description;
	// 创建时间
	private Date createTime;
	// 删除状态(0未删除1删除)
	private Integer isDelete;
	// 0未发布1发布
	private Integer status;
	// 更新时间
	private Date updateTime;
	// 用户id
	private Integer userId;
	/* 学科ID */
	private Integer subjectId;
	/* 文件夹id */
	private Integer folderId;
	/* 连接地址 */
	private String url;

	public Exam() {
		super();
	}

	public Exam(Integer id, String name, String ext, String size,
			String description, Date createTime, Integer isDelete,
			Integer status, Date updateTime, Integer userId, Integer subjectId,
			Integer folderId) {
		super();
		this.id = id;
		this.name = name;
		this.ext = ext;
		this.size = size;
		this.description = description;
		this.createTime = createTime;
		this.isDelete = isDelete;
		this.status = status;
		this.updateTime = updateTime;
		this.userId = userId;
		this.subjectId = subjectId;
		this.folderId = folderId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Lob
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "is_delete", length = 1)
	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name = "status", length = 1)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "update_time")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "user_id")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "subject_id")
	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	@Column(name = "ext", length = 10)
	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	@Column(name = "size", length = 300)
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Column(name = "folder_id")
	public Integer getFolderId() {
		return folderId;
	}

	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}

	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
