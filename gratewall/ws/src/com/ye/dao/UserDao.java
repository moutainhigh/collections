package com.ye.dao;

import java.util.List;

import com.bo.domain.User;


public interface UserDao {

	public List<User> selectUserList();
	
	public int save(User user);
	
	public User findUserByName(String name);
	
	
}
