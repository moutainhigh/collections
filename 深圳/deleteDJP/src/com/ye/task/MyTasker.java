package com.ye.task;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ye.dao.IPDaoMapper;

@Component
public class MyTasker {

	@Autowired
	private IPDaoMapper ipdao;
	
	private static final Logger register = Logger.getLogger("register");
	
	
	//@Scheduled(cron= "0 0 2 * * ?" )
	//@Scheduled(cron= "0/20 * * * * ?" )
	//@Scheduled(cron= "0 0 2 * * ?" )
	//@Scheduled(cron= "0/20 * * * * ?" )
	@Scheduled(cron= "0 0 2 * * ?" )
	public void task() {
		register.info("\t 开始删除"+new Date().toLocaleString());
		ipdao.delete();
	}
}
