package com.ly.intercepter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ly.placeholder.DESUtils;
import com.ly.pojo.User;

import redis.RedisDao;

@Component
public class MyService {
	@Autowired
	private RedisDao redisDao;

	public boolean isLogin(String token) {
		String key = DESUtils.getDecryptString(token.trim());
		User user = redisDao.get(key, User.class);
		if (user != null) {
			return true;
		} else {
			return false;
		}
	}
}
