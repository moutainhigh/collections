package com.ly.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ly.pojo.Stock_K_line_Day_DataList;
import com.ly.vo.StockVo;
@Repository
@Transactional
public class Stock_K_line_Day_Data_List_Dao extends BaseDaoHibernate4<Stock_K_line_Day_DataList>{
	//https://blog.csdn.net/lw5885799/article/details/81103183
	public Double getNum(String sql,String params){
		Double num = (Double) this.getCurrentSession().createSQLQuery(sql).setParameter(0, params).uniqueResult();
		return num;
	}
	
	public List  getStock(String hql,Object... params){
		SQLQuery query = this.getCurrentSession().createSQLQuery(hql);
		if(params!=null&&params.length>0){
			for (int i = 0, len = params.length; i < len; i++) {
				query.setParameter(i, params[i]);
			}
		}
		//query.setResultTransformer(Transformers.aliasToBean(StockVo.class));
		List list = query.list();
		return list;
	}
	
	
	
	public List  getStock(String sql){
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		List list = query.list();
		return list;
	}
}
