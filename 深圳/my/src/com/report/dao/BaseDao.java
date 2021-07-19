package com.report.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.report.task.ReportCacheBusTask;


public class BaseDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	//sessionFactory要关闭吗?
	//链接池---sessionFactory---->
	public Session getSession() {
		if (sessionFactory != null) {
			//return sessionFactory.openSession(); //一定手动关闭session
			//了解区分一下openSession getCurrentSession的区别场景,get load
			return sessionFactory.getCurrentSession();//必须要结合事务才有意义
		}
		return null;
	}

}
