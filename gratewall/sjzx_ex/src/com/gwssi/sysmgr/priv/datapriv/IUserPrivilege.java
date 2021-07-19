package com.gwssi.sysmgr.priv.datapriv;

import java.util.List;

/**
 *    �û��ķ�����ɽӿڣ�������Աͨ��getRule�õ����������in��not in��ͨ��
 * getPrivilegeList��getPrivilegeSql�õ�������Χ
 * @author ����
 * */
public interface IUserPrivilege {
	/**
	 * ��ȡ��ɹ��� in(����) not in(������)
	 * @return ������ɹ���
	 */
	public String getRule();
	/**
	 * ��ȡ��������б�
	 * @return ��������б�
	 */
	public List getPrivilegeList();
	/**
	 *    ��ȡ������ɲ�ѯSQL�ַ�������SQL��䷵��һ��ֻ����һ��Code�ֶε����ݼ���
	 * SQL�ִ���ʽ���£�
	 *    select code from table where userid='ss'
	 * @return
	 */
	public String getPrivilegeSql();
}
