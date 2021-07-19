package com.ly.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ly.common.util.Constrant;
import com.ly.dao.BaseDao;

/**
 * Description:
 * https://bbs.csdn.net/topics/390879785
 *https://blog.csdn.net/baidu_38996156/article/details/78689648
 * @version 1.0
 */
public class BaseDaoHibernate4<T>  implements BaseDao<T> {
	
	@Autowired
	public SessionFactory sessionFactory;
	
	public Session getCurrentSession() {
		if (sessionFactory != null) {
			return sessionFactory.getCurrentSession();
		}
		return null;
	}
	
	// 根据ID加载实体
	@SuppressWarnings("unchecked")
	public T get(Class<T> entityClazz, Serializable id) {
		return (T) getCurrentSession().get(entityClazz, id);
	}

	// 保存实体
	public Serializable save(T entity) {
		return getCurrentSession().save(entity);
	}

	// 更新实体
	public void update(T entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

	// 删除实体
	public void delete(T entity) {
		getCurrentSession().delete(entity);
	}

	// 根据ID删除实体
	public void delete(Class<T> entityClazz, Serializable id) {
		getCurrentSession().createQuery("delete " + entityClazz.getSimpleName() + " en where en.id = ?0")
				.setParameter("0", id).executeUpdate();
	}

	// 获取所有实体
	public List<T> findAll(Class<T> entityClazz) {
		return find("select en from " + entityClazz.getSimpleName() + " en");
	}
	// 获取实体总数

	public long findCount(Class<T> entityClazz) {
		List<?> l = find("select count(1) from " + entityClazz.getSimpleName());
		// 返回查询得到的实体总数
		if (l != null && l.size() == 1) {
			return (Long) l.get(0);
		}
		return 0;
	}
	
	
	
	
	public long findCountByParam(Class<T> entityClazz,Object... params) {
		List<?> l = find("select count(1) from " + entityClazz.getSimpleName(),params);
		// 返回查询得到的实体总数
		if (l != null && l.size() == 1) {
			return (Long) l.get(0);
		}
		return 0;
	}

	
	
	
	
	
	
	

	// 根据HQL语句查询实体
	@SuppressWarnings("unchecked")
	public List<T> find(String hql) {
		return (List<T>) getCurrentSession().createQuery(hql).list();
	}

	// 根据带占位符参数HQL语句查询实体
	@SuppressWarnings("unchecked")
	public List<T> find(String hql, Object... params) {
		try {
			// 创建查询
			Query query = getCurrentSession().createQuery(hql);
			// 为包含占位符的HQL语句设置参数
			for (int i = 0, len = params.length; i < len; i++) {
				//query.setParameter(i + "", params[i]);
				query.setParameter(i, params[i]);
			}
			
			
			
			return (List<T>) query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * 使用hql 语句进行分页查询操作
	 * 
	 * @param hql
	 *            需要查询的hql语句
	 * @param pageNo
	 *            查询第pageNo页的记录
	 * @param pageSize
	 *            每页需要显示的记录数
	 * @return 当前页的所有记录
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByPage(String hql, int pageNo, int pageSize) {
		// 创建查询
		return getCurrentSession().createQuery(hql)
				// 执行分页
				.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
	}

	/**
	 * 使用hql 语句进行分页查询操作
	 * 
	 * @param hql
	 *            需要查询的hql语句
	 * @param params
	 *            如果hql带占位符参数，params用于传入占位符参数
	 * @param pageNo
	 *            查询第pageNo页的记录
	 * @param pageSize
	 *            每页需要显示的记录数
	 * @return 当前页的所有记录
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByPage(String hql, int pageNo, int pageSize, Object... params) {
		// 创建查询
		Query query = getCurrentSession().createQuery(hql);
		// 为包含占位符的HQL语句设置参数
		for (int i = 0, len = params.length; i < len; i++) {
			//query.setParameter(i + "", params[i]);
			query.setParameter(i, params[i]);
		}
		// 执行分页，并返回查询结果
		return query.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
	}

		

	@Override
	public void batchSave(List<T> entitys) {
		Session session = null;  
        if (entitys != null && entitys.size() > 0) {  
            try {  
                session = sessionFactory.openSession();
                session.beginTransaction(); // 开启事务  
                T t = null;   
                for (int i = 0; i < entitys.size(); i++) {  
                    t = entitys.get(i);
                    session.save(t); 
                    if ((i != 0 && i % Constrant.DEFAULT_BATCH_SIZE == 0)|| i == entitys.size() - 1) {  
                        session.flush();  
                        session.clear();  
                    }  
                }
                session.getTransaction().commit(); // 提交事务 
            } catch (Exception e) {  
                e.printStackTrace();
                session.getTransaction().rollback(); // 出错将回滚事务 
            } finally {  
            	session.close(); // 关闭Session  
            }  
        } 
	}
	
	
	@Override
	public void batchUpdate(List<T> entitys) {
		Session session = null;  
        if (entitys != null && entitys.size() > 0) {  
            try {  
                session = sessionFactory.openSession();
                session.beginTransaction(); // 开启事务  
                T t = null;   
                for (int i = 0; i < entitys.size(); i++) {  
                    t = entitys.get(i);
                    session.update(t); 
                    if ((i != 0 && i % Constrant.DEFAULT_BATCH_SIZE == 0)|| i == entitys.size() - 1) {  
                        session.flush();  
                        session.clear();  
                    }  
                }
                session.getTransaction().commit(); // 提交事务 
            } catch (Exception e) {  
                e.printStackTrace();
                session.getTransaction().rollback(); // 出错将回滚事务 
            } finally {  
            	session.close(); // 关闭Session  
            }  
        } 
	}

	@Override
	public void truncateTable(Class<T> entityClazz) {
		String tableName = entityClazz.getName().toLowerCase();
		tableName = tableName.substring(tableName.lastIndexOf(".")+1,tableName.length());
		getCurrentSession().createSQLQuery("truncate table " + tableName).executeUpdate();
	}

	@Override
	public void updateByHql(String hql, Object... params) {
		try {
			// 创建查询
			Query query = getCurrentSession().createQuery(hql);
			// 为包含占位符的HQL语句设置参数
			for (int i = 0, len = params.length; i < len; i++) {
				//query.setParameter(i + "", params[i]);
				query.setParameter(i, params[i]);
			}
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
