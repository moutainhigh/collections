package com.gwssi.sysmgr.priv.datapriv;

import java.util.List;

/**
 * ����Ȩ�����֤
 * @author ����
 */
public class DataPrivilege implements IUserPrivilege {
	private String rule = "";
	private List privilegeList;
	private String privilegeSql = "";
	
	/**
	 * ��ȡ����Ȩ���б�
	 */
	public List getPrivilegeList() {
		return privilegeList;
	}
	
	/**
	 * ��������Ȩ���б�
	 * @param privilegeList ����Ȩ���б�
	 */
	protected void setPrivilegeList(List privilegeList){
		this.privilegeList = privilegeList;
	}

	/**
	 * ��ȡ����Ȩ���б��ѯSQL
	 */
	public String getPrivilegeSql() {
		return privilegeSql;
	}
	
	/**
	 * ��������Ȩ���б��ѯSQL
	 * @param privilegeSql ��Ȩ���б��ѯSQ
	 */
	protected void setPrivilegeSql(String privilegeSql) {
		this.privilegeSql = privilegeSql;
	}
	
	/**
	 * ��ȡ����Ȩ�޷��ʹ���(in��not in)
	 */
	public String getRule() {
		return rule;
	}
	/**
	 * ��������Ȩ�޷��ʹ���(in��not in)
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
