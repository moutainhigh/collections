package com.ly.task.jsoup.day;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.task.jsoup.day.cal.CalDayLineUpAsync;
//@Component
public class ByHandCreateData {

	@Autowired
	private CalDayLineUpAsync caldaylineup;
	
	
	@Scheduled(cron = "10 24 16 ? * *")
	public void create(){
		//caldaylineup.task01();
	}
}
