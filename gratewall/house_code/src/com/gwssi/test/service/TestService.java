package com.gwssi.test.service;

import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
@Service
public class TestService extends BaseService {
	IPersistenceDAO dao = this.getPersistenceDAO("dc_dc");
	
	public String testDao() throws OptimusException {
		return String.valueOf(dao.queryForInt("select count(1) from dual",null));
	}
}

