package com.gwssi.optimus.plugin.auth.model;

import java.util.List;

public class User {

	/* 登录用户ID,即是用户AD域里的用户名 */
	private String userId;

	/* 登录用户中文名称 */
	private String userName;

	/* 登录用户的岗位信息 */
	private List postList;

	/* 登录用户的角色 信息 */
	private List roleList;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List getPostList() {
		return postList;
	}

	public void setPostList(List postList) {
		this.postList = postList;
	}

	public List getRoleList() {
		return roleList;
	}

	public void setRoleList(List roleList) {
		this.roleList = roleList;
	}

}
