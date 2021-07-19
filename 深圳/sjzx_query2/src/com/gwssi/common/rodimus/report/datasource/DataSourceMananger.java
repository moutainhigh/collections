package com.gwssi.common.rodimus.report.datasource;

import com.gwssi.common.rodimus.report.dao.BaseDao;
import com.gwssi.common.rodimus.report.util.SpringUtil;


public class DataSourceMananger {
	
	public static BaseDao getDao(){
		BaseDao ret = (BaseDao)SpringUtil.getBean("baseDao");
		return ret; 
	}
}
