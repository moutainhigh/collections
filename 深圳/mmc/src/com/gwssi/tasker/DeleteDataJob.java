package com.gwssi.tasker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gwssi.dao.EBaseinfoDao;


@Component
public class DeleteDataJob {
	
	
	@Autowired
	private EBaseinfoDao ebdao;
	
	
	
	@Scheduled(cron = "0 15 10,21 25 * ?")
	public void delete03() {
		ebdao.delete03();
	}
	
	@Scheduled(cron = "0 19 10,21 25 * ?")
	public void delete6100() {
		ebdao.delete6100();
	}
	
	
	//@Scheduled(cron="*/50 * * * * ?")
	@Scheduled(cron = "0 22 10,21 25 * ?")
	public void delete6299() {
		
		ebdao.delete6299();
	}
	
	
	
}
