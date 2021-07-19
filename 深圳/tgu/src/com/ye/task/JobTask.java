package com.ye.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ye.dc.bus.ZhcxBus;
import com.ye.from.bus.ZhcxFromBus;

@Component
public class JobTask {

	@Autowired
	private ZhcxFromBus zhcxFromBus;
	
	

	@Autowired
	private ZhcxBus zhcxBus;
	
	@Scheduled(cron="0 0 11,14,16,20 ? * MON-FRI" )
	public void task() {
		zhcxFromBus.saveToDb();
		
		
	//	zhcxBus.saveToDbFromLog();
		
	}
}
