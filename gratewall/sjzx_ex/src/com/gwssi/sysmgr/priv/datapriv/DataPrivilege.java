package com.gwssi.sysmgr.priv.datapriv;

import java.util.List;

/**
 * 数据权限许可证
 * @author 周扬
 */
public class DataPrivilege implements IUserPrivilege {
	private String rule = "";
	private List privilegeList;
	private String privilegeSql = "";
	
	/**
	 * 获取数据权限列表
	 */
	public List getPrivilegeList() {
		return privilegeList;
	}
	
	/**
	 * 设置数据权限列表
	 * @param privilegeList 数据权限列表
	 */
	protected void setPrivilegeList(List privilegeList){
		this.privilegeList = privilegeList;
	}

	/**
	 * 获取数据权限列表查询SQL
	 */
	public String getPrivilegeSql() {
		return privilegeSql;
	}
	
	/**
	 * 设置数据权限列表查询SQL
	 * @param privilegeSql 据权限列表查询SQ
	 */
	protected void setPrivilegeSql(String privilegeSql) {
		this.privilegeSql = privilegeSql;
	}
	
	/**
	 * 获取数据权限访问规则(in、not in)
	 */
	public String getRule() {
		return rule;
	}
	/**
	 * 设置数据权限访问规则(in、not in)
	 * @param rule
	 */
	protected void setRule(String rule){
		this.rule = rule; 
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("rule:");
		sb.append(rule);
		sb.append("\n");
		sb.append("privilegeList:");
		sb.append(privilegeList);
		sb.append("\n");
		sb.append("privilegeSql:");
		sb.append(privilegeSql);
		return sb.toString();
	}
}
