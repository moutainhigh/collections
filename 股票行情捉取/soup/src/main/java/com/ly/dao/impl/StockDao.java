package com.ly.dao.impl;

import org.springframework.stereotype.Repository;

import com.ly.pojo.Stock;

@Repository
public class StockDao extends BaseDaoHibernate4<Stock> {

	public void deleKeChuang() {
		//this.getCurrentSession().createQuery("update Special_Setting set isdefault = 0").executeUpdate();
		
		this.getCurrentSession().createQuery("delete from Stock where code like '688%' ").executeUpdate();
		
	}
}
