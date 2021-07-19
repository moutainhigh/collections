package com.gwssi.common.rodimus.report.util;

import com.gwssi.common.rodimus.report.dao.BaseDao;
import com.gwssi.optimus.core.persistence.dao.DAOManager;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;

public class QueryDaoUtil extends BaseDao {
	
	public IPersistenceDAO getDao(){
		IPersistenceDAO dao = DAOManager.getPersistenceDAO("dc_dc");
		return dao;
	}
	public static BaseDao getInstance() {
		return new QueryDaoUtil();
	}
}
