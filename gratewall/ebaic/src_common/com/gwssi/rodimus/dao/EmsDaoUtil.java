package com.gwssi.rodimus.dao;

import com.gwssi.optimus.core.persistence.dao.DAOManager;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;

public class EmsDaoUtil extends BaseDaoUtil{
	public IPersistenceDAO getDao(){
		String dataSourceName = "emsConfigDataSource";
		IPersistenceDAO dao = DAOManager.getPersistenceDAO(dataSourceName);
		return dao;
	}
	public static BaseDaoUtil getInstance() {
		return new EmsDaoUtil();
	}
}
