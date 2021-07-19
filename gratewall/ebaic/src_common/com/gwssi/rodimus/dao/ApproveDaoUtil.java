package com.gwssi.rodimus.dao;

import com.gwssi.optimus.core.persistence.dao.DAOManager;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;

public class ApproveDaoUtil extends BaseDaoUtil {
	
	public IPersistenceDAO getDao(){
		String dataSourceName = "approveDataSource";
		IPersistenceDAO dao = DAOManager.getPersistenceDAO(dataSourceName);
		return dao;
	}
	
	public static BaseDaoUtil getInstance(){
		return new ApproveDaoUtil();
	}
}
