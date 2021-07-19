package com.tz.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public  class BaseDao {

	@Autowired
	public SessionFactory sessionFactory;
	
	
	public Session getSession(){
		//return sessionFactory.openSession();
		return sessionFactory.getCurrentSession();
	}
	
}
