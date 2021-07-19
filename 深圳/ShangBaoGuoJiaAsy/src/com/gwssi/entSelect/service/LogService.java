package com.gwssi.entSelect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwssi.entSelect.dao.EntLogDao;
import com.gwssi.entSelect.pojo.EntErrorLog;
@Service
public class LogService {
	@Autowired
	private EntLogDao entLogDao;
	
	
	public void logMsg(EntErrorLog log) {
		
		entLogDao.save(log);
		
	}
}
