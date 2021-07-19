package com.gwssi.dw.runmgr.services;

import java.util.List;
import java.util.Map;

import com.gwssi.dw.runmgr.services.common.ConfigBean;

/**
 * 自定义共享服务的父接口
 * @author wyx
 *	2008-5-10
 *
 */
public interface SelfServiceDefine extends GeneralService
{
	/**
	 * 获得所有共享字段<br>
	 * List中必须是ShareServiceColumn
	 * @return java.util.List
	 */
	List getSharedColumns();
	
	/**
	 * 获得所有参数字段
	 * @return java.util.List
	 */
	List getParamColumns();
	
	/**
	 * 获得服务访问路径
	 * @return String
	 */
	String getVisitURL();
	
	/**
	 * 返回此自定义服务的SQL语句
	 * @return String sql
	 */
	String getQuerySQL(ConfigBean config, Map paramMap);
}
