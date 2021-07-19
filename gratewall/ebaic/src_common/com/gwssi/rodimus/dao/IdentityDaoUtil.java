package com.gwssi.rodimus.dao;

import com.gwssi.optimus.core.persistence.dao.DAOManager;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;

public class IdentityDaoUtil extends BaseDaoUtil{
	
	public IPersistenceDAO getDao(){
		String dataSourceName = "identityDataSource";
		IPersistenceDAO dao = DAOManager.getPersistenceDAO(dataSourceName);
		return dao;
	}
	public static BaseDaoUtil getInstance() {
		return new IdentityDaoUtil();
	}
}
