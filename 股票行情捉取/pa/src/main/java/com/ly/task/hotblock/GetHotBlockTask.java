package com.ly.task.hotblock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.stocktrade.hotblock.GetHotBlock;

//@Component
public class GetHotBlockTask {

	@Autowired
	private GetHotBlock getHotBlock;
	
	
	@Scheduled(cron="40 26 19 ? * MON-FRI")
	public void task01(){
		//getHotBlock.getInfo();
		
	}
}
