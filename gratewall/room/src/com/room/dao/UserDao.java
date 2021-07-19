package com.room.dao;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.room.basedao.BaseDao;
import com.room.bean.User;


@Repository
public class UserDao extends BaseDao {

	public User checkUser(String name) {
		String hql = "From User u where u.userName = ?";
		Query query = this.getSession().createQuery(hql);
		return null;
	}

	public void saveUser(User user) {
		this.getSession().save(user);
	}

}
