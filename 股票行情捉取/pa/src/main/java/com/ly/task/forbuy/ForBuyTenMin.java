package com.ly.task.forbuy;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ForBuyTenMin {
//https://www.cnblogs.com/javahr/p/8318728.html
//http://www.net767.com/book/kxxt/200805/4269.html
	
	private static Logger logger = Logger.getLogger(ForBuyTenMin.class);
	
	
	
	@Scheduled(cron="05 30,40,50 9 ? * MON-FRI")
	public void task01(){
		
		logger.debug("11111");
	}
	
	
	@Scheduled(cron="05 00,10,20,30,40,50 10 ? * MON-FRI")
	public void task010(){
		
		logger.debug("11111");
	}
	
	@Scheduled(cron="05 00,10,20,29 11 ? * MON-FRI")
	public void task011(){
		
		logger.debug("11111");
	}
	
	
	
	
	@Scheduled(cron="05 00,10,20,30,40,50 13 ? * MON-FRI")
	public void task13(){
		
		logger.debug("11111");
	}
	
	
	@Scheduled(cron="05 00,10,20,30,40,50,55 14 ? * MON-FRI")
	public void task14(){
		
		logger.debug("11111");
	}
	
}
