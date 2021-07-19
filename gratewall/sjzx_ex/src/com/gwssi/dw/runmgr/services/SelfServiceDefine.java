package com.gwssi.dw.runmgr.services;

import java.util.List;
import java.util.Map;

import com.gwssi.dw.runmgr.services.common.ConfigBean;

/**
 * �Զ��干�����ĸ��ӿ�
 * @author wyx
 *	2008-5-10
 *
 */
public interface SelfServiceDefine extends GeneralService
{
	/**
	 * ������й����ֶ�<br>
	 * List�б�����ShareServiceColumn
	 * @return java.util.List
	 */
	List getSharedColumns();
	
	/**
	 * ������в����ֶ�
	 * @return java.util.List
	 */
	List getParamColumns();
	
	/**
	 * ��÷������·��
	 * @return String
	 */
	String getVisitURL();
	
	/**
	 * ���ش��Զ�������SQL���
	 * @return String sql
	 */
	String getQuerySQL(ConfigBean config, Map paramMap);
}
