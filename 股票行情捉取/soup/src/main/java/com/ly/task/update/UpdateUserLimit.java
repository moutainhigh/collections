package com.ly.task.update;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ly.dao.impl.UserDao;
import com.ly.pojo.User;

@Component
public class UpdateUserLimit {
	
	@Autowired
	private UserDao dao;
	
	//@Scheduled(cron = "0 */5 * * * ?")
	//@Scheduled(cron="*/10 * * * * ?")
	@Scheduled(cron = "0 */5 * * * ?")
	public void task01(){
		List<User> list  = dao.findAll(User.class);
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				User user = list.get(i);
				user.setLimitTime("10");
				dao.update(user);
			}
		}
	}
}
