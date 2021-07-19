package com.laoda.basedao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class BaseDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		if (sessionFactory != null) {
			return sessionFactory.getCurrentSession();
		} else {
			return null;
		}
	}
}
