package com.gwssi.common.rodimus.report.util;

import org.apache.commons.lang3.StringUtils;

import com.gwssi.common.rodimus.report.dao.BaseDao;
import com.gwssi.optimus.core.persistence.dao.DAOManager;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;

public class DaoUtil extends BaseDao {
	
	private String dataSourceName = null;

	public DaoUtil(String dataSourceName){
		this.dataSourceName = dataSourceName ;
	}
	
	public IPersistenceDAO getDao(){
		if(StringUtils.isBlank(this.dataSourceName)){
			throw new RuntimeException("No dataSource name speicificated. ");
		}
		IPersistenceDAO dao = DAOManager.getPersistenceDAO(this.dataSourceName);
		return dao;
	}
	
	public static BaseDao getInstance(String dataSourceName) {
		return new DaoUtil(dataSourceName);
	}

	public static BaseDao getDefaultInstance(){
		return new DaoUtil("defaultDataSource");
	}
	
	public static BaseDao getDictInstance(){
		return new DaoUtil("dc_code");
	}
}
