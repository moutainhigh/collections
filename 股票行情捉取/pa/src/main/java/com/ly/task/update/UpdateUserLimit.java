package com.ly.task.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.dao.UserDao;
import com.ly.pojo.User;
@Component
public class UpdateUserLimit {
	
	@Autowired
	private UserDao dao;
	
	@Scheduled(cron = "0 */5 * * * ?")
	public void task01(){
		User user = dao.getUserByParam(null);
		user.setLimitTime("10");
		dao.save(user);
		
	}
}
