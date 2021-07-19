package com.gwssi.optimus.plugin.auth.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gwssi.application.model.SmSysIntegrationBO;

public class User {
    private String Ip;

	public String getIp() {
		return Ip;
	}

	public void setIp(String ip) {
		Ip = ip;
	}

	/* 登录用户ID,即是用户AD域里的用户名 */
	private String userId;

	/* 登录用户中文名称 */
	private String userName;

	/* 登录用户的岗位信息 */
	private List postList;

	/* 登录用户的角色 信息 */
	private List roleList;
	
	/*是否超级管理员*/
	private boolean isSuperAdmin =false;
	
	/*是否当前系统管理员*/
	private boolean isCurrAdmin =false;
	
	/*当前系统所能访问的功能列表*/
	private List<Map> funclist;
	
	/*当前系统所需要验证的url*/
	private List<String> authfunclist;
	
	/*普通管理员系统列表*/
	private List<SmSysIntegrationBO> adminSysList;
	
	/*所具有的访问系统列表*/
	private List<SmSysIntegrationBO> userSysList;
	
	
	
	public List<String> getAuthfunclist() {
		return authfunclist;
	}

	public void setAuthfunclist(List<String> authfunclist) {
		this.authfunclist = authfunclist;
	}

	public List<Map> getFunclist() {
		return funclist;
	}

	public void setFunclist(List<Map> funclist) {
		this.funclist = funclist;
	}

	public boolean getIsCurrAdmin() {
		return isCurrAdmin;
	}

	public void setCurrAdmin(boolean isCurrAdmin) {
		this.isCurrAdmin = isCurrAdmin;
	}

	public boolean getIsSuperAdmin() {
		return isSuperAdmin;
	}

	public void setSuperAdmin(boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public List<SmSysIntegrationBO> getAdminSysList() {
		return adminSysList;
	}

	public void setAdminSysList(List<SmSysIntegrationBO> adminSysList) {
		this.adminSysList = adminSysList;
	}

	public List<SmSysIntegrationBO> getUserSysList() {
		return userSysList;
	}

	public void setUserSysList(List<SmSysIntegrationBO> userSysList) {
		this.userSysList = userSysList;
	}

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
