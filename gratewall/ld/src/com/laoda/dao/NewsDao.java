package com.laoda.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.laoda.basedao.BaseDao;
import com.laoda.bean.News;
import com.laoda.common.Constrant;

@Repository
public class NewsDao extends BaseDao {

	public void save(News news) {
		this.getSession().save(news);
	}

	public List getNewsListByPage(int pageIndex, int  pageSize) {
		String hql = "From News t ";
		Query query = this.getSession().createQuery(hql);
		query.setFirstResult(pageIndex);
		query.setMaxResults(pageSize);
		List list = query.list();
		return list;

	}
}
