package com.ly.dao.impl;

import org.springframework.stereotype.Repository;

import com.ly.pojo.Email_Sender;

@Repository
public class EmailSenderDao extends BaseDaoHibernate4<Email_Sender>{

	
	public void updateDefault(String id){
		this.getCurrentSession().createQuery("update Email_Sender set flag = 0").executeUpdate();
		
		this.getCurrentSession().createQuery("update Email_Sender set flag = 1 where id = ? ").setParameter(0, id).executeUpdate();
	}
	
	
	public void updateBuySellSender(String id){
		this.getCurrentSession().createQuery("update Email_Sender set buy_sell_annouce = 0").executeUpdate();
		
		this.getCurrentSession().createQuery("update Email_Sender set buy_sell_annouce = 1 where id = ? ").setParameter(0, id).executeUpdate();
	}
	
	
	
	
}
