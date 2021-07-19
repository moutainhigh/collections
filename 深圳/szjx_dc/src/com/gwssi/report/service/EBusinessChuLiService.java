package com.gwssi.report.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class EBusinessChuLiService extends BaseService {


	private static Logger log = Logger.getLogger(EBusinessChuLiService.class);
	
	public List getChuLiList() {
		IPersistenceDAO dao = this.getPersistenceDAO("defaultDataSource");
		return null;
	}
}
