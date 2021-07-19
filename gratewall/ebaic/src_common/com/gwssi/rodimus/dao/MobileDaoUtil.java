package com.gwssi.rodimus.dao;

import com.gwssi.optimus.core.persistence.dao.DAOManager;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;

public class MobileDaoUtil extends BaseDaoUtil{
	
	public IPersistenceDAO getDao(){
		IPersistenceDAO dao = DAOManager.getPersistenceDAO();
		return dao;
	}
	public static BaseDaoUtil getInstance() {
		return new DaoUtil();
	}
}
