package com.ly.task.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.dao.SMSSenderDao;

@Component
public class UpdateSMSTimes {

	@Autowired
	private SMSSenderDao smsSenderDao;

	@Scheduled(cron = "0 0 1 1 * ?")
	public void task01() {
		smsSenderDao.updateSms();
	}
}
