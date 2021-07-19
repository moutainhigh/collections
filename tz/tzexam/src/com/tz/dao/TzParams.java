/**
 * Project Name:tzexam
 * File Name:TzParams.java
 * Package Name:com.tz.dao
 * Date:2015年10月25日下午9:51:30
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.
 *
 */

package com.tz.dao;

/**
 * ClassName:TzParams <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年10月25日 下午9:51:30 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.6
 * @see
 */
public class TzParams {

	private Integer subjectId;
	private Integer status;
	private String name;
	private String keyword;
	private Integer userId;
	private Integer folderId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getFolderId() {
		return folderId;
	}

	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
