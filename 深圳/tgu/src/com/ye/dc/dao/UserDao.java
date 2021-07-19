package com.ye.dc.dao;

import java.util.Map;

import com.ye.dc.pojo.User;

public interface UserDao {

	public void save(User user);
	
	public User  getUserIsExit(Map map) ;
	
}
