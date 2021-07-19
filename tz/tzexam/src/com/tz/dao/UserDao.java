package com.tz.dao;

import org.springframework.stereotype.Repository;

import com.tz.model.User;

@Repository
public class UserDao  extends BaseDao{

	
	/**
	 * 
	 * 根据账号和密码查询用户信息
	 * @author Administrator
	 * @param account
	 * @param password
	 * @return
	 * @since JDK 1.6
	 */
	public User getLoginUser(String account,String password){
		return (User)getSession().createQuery(" FROM User WHERE account = ? AND password = ? AND isDelete = 0")
				.setString(0, account)
				.setString(1, password)
				.uniqueResult();
	}
	
}
