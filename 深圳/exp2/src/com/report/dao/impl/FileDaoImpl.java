package com.report.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.report.bean.FileDown;
import com.report.dao.BaseDao;
import com.report.util.PageInfo;
import com.report.util.StrUtil;


//http://www.cnblogs.com/mfrbuaa/p/3815422.html
@Transactional
@Repository
public class FileDaoImpl extends BaseDao {

	public void save(FileDown fiDown) {
		this.getSession().save(fiDown);
	}

	
	public void updateSatus(FileDown file) {
		String hql = "update FileDown t set t.dataStatus = ? where t.fileId = ?";
		this.getSession().createQuery(hql).setInteger(0, file.getDataStatus()).setString(1, file.getFileId()).executeUpdate();
		
	}
	
	
	public List getFailList() {
		String hql = "from FileDown where 1 = 1 and dataStatus ='0'";
		List list = this.getSession().createQuery(hql).list();
		return list;
	}
	
	
	
	
	public void delete(List lists ) throws Exception {
		String hql = "delete  from  FileDown t where  t.id in (:lists)";
		Query query = this.getSession().createQuery(hql);
		query.setParameterList("lists",lists);//第二个参数idList为传入参数，idList中的值的类型与DB中ID类型一致
		query.executeUpdate();
	}
	
	
   //https://zhidao.baidu.com/question/367429680.html
	public List getFileToDown(String time, PageInfo dgparam) {
		Query query = null;
		String hql = null;
		List list = null;
		try {
			hql = "from FileDown where 1 = 1";
			if (!StrUtil.isEmpty(time)) {
				hql += " and dateQueryTime = :dateQueryTime order by dateQueryTime desc";
				query = this.getSession().createQuery(hql);
				query.setParameter("dateQueryTime", time);
				int start = dgparam.getPage()*dgparam.getRows();
				query.setFirstResult(start);
				query.setMaxResults(dgparam.getRows());
			} else {
				hql+=" order by dateQueryTime desc ";
				query = this.getSession().createQuery(hql);
				int start = dgparam.getPage()*dgparam.getRows();
				query.setFirstResult(start);
				query.setMaxResults(dgparam.getRows());
			}
			list  = query.list();
			return list;
		} catch (Exception e) {
			return null;
		}
	}


	public Integer getTotal(String time, PageInfo dgparam) {
		try {
			String hqlString = "select count(f.id) from FileDown f  where 1 = 1 ";
			Query query = null;
			if (!StrUtil.isEmpty(time)) {
				hqlString += " and dateQueryTime=:dateQueryTime ";
				query = this.getSession().createQuery(hqlString);
				query.setParameter("dateQueryTime", time);
			} else {
				query = this.getSession().createQuery(hqlString);
			}
			Integer count = ((Number) query.uniqueResult()).intValue();
			return count;
		} catch (Exception e) {
			return 0;
		}
	}
}
