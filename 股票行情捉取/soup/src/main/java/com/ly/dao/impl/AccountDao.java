package com.ly.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ly.pojo.Account;

@Repository
public class AccountDao extends BaseDaoHibernate4<Account>{
	
	
	public List getAccounts(){
		List list = this.getCurrentSession().createQuery("select  distinct t from Account t left outer join fetch t.options").list();
		return list;
	}

}
