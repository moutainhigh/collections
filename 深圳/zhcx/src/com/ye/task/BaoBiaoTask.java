package com.ye.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ye.dc.bus.BiaoBaoBus;
import com.ye.from.bus.BaoBiaoFromBus;

@Component
public class BaoBiaoTask {

	@Autowired
	private BaoBiaoFromBus baoBiaoFromBus;
	
	

	@Autowired
	private BiaoBaoBus baoBiaoBus;
	
	//@Scheduled(cron="0 0 11,14,16,20 ? * MON-FRI" )

	//@Scheduled(cron="*/10 * * * * *" )
	//@Scheduled(cron="*/10 * * * * *" )
	@Scheduled(cron="0 0/40 9-18 ? * MON-FRI" )
	public void task() {
		
		
	   baoBiaoFromBus.saveToDb();
	   baoBiaoBus.savetTOBaoBiaoFormLog();
		
	}
}
