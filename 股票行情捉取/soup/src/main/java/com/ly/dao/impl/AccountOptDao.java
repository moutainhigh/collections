package com.ly.dao.impl;

import org.springframework.stereotype.Repository;

import com.ly.pojo.Account_Operation;

@Repository
public class AccountOptDao extends BaseDaoHibernate4<Account_Operation>{

	public void clearOpts(String account) {
		this.getCurrentSession().createQuery("update Account_Operation set isdefault = 0 where aid =? ").setParameter(0, account).executeUpdate();
	}


	public void addOpts(String account, String opt) {
		this.getCurrentSession().createQuery("update Account_Operation set isdefault = 1 where aid = ?  and operationType = ? ").setParameter(0, account).setParameter(1, opt).executeUpdate();
	}
	
	
	public void deleteAllById(String account){
		this.getCurrentSession().createQuery("delete from  Account_Operation where aid = ?  ").setParameter(0, account).executeUpdate();
	}

	
	
	
	public void clearJiJingOpts(String account) {
		this.getCurrentSession().createQuery("update Account_Ji_Jing set isdefault = 0 where aid =? ").setParameter(0, account).executeUpdate();
	}


	public void addJiJingOpts(String account, String opt) {
		this.getCurrentSession().createQuery("update Account_Ji_Jing set isdefault = 1 where aid = ?  and operationType = ? ").setParameter(0, account).setParameter(1, opt).executeUpdate();
	}
	
	
	public void deleteJiJingAllById(String account){
		this.getCurrentSession().createQuery("delete from  Account_Ji_Jing where aid = ?  ").setParameter(0, account).executeUpdate();
	}
}
