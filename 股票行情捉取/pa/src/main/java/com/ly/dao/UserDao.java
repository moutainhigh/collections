package com.ly.dao;

import java.util.Map;

import com.ly.pojo.User;

public interface UserDao {
	
	public User getUserByParam(Map map);
	
	public void save(User user);
	
}
