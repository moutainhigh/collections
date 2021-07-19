package com.ly.dao.temp;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ly.common.util.Constrant;

//@Component
public class BaseDao2 {
	private static Logger logger = Logger.getLogger("recharge");
	
	
	@Autowired
	private SessionFactory sessionFactory;

	
	public Session getSession() {
		if (sessionFactory != null) {
			//return sessionFactory.openSession(); //一定手动关闭session
			//了解区分一下openSession getCurrentSession的区别场景,get load
			//return sessionFactory.getCurrentSession();//必须要结合事务才有意义
			return sessionFactory.getCurrentSession();
		}
		return null;
	}
	
	
	public void truncateTable(String tableName) {
		this.getSession().createNativeQuery("truncate table " + tableName).executeUpdate();
	}
	
	
	public void save(Object obj){
		this.getSession().save(obj);
	}

	
	//https://blog.csdn.net/FANGAOHUA200/article/details/53577056
	//http://www.cnblogs.com/grey-wolf/p/10209946.html
/*	public int getTotal(Class entityName){
		 String countHql = "select count(1) from  Stock  where and a.approveResult = :approveResult and a.approverId = :approverId";
	     Query countQuery = getSession().createQuery(countHql);
	     countQuery.setParameter("approverId", approverId);
	     int count = ((Long) countQuery.uniqueResult()).intValue();
	     return count;
	}*/
	
	
	
	public <T> void saveBatchWithTruncateTable(List<T> entitys,String tableName){
		truncateTable(tableName);
		Long startTime = System.currentTimeMillis();
		Session session = null;  
        if (entitys != null && entitys.size() > 0) {  
            try {  
                session = this.getSession().getSessionFactory().openSession();
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
            	this.getSession().getSessionFactory().close(); // 关闭Session  
            }  
        }
        Long endTime = System.currentTimeMillis();
		logger.debug("batchCommit耗时：" + (endTime - startTime)/1000 + "秒");
    }
	
	
	
	public <T> void saveBatch(List<T> entitys){
		Session session = null;  
        if (entitys != null && entitys.size() > 0) {  
            try {  
                session = this.getSession().getSessionFactory().openSession();
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
            	this.getSession().getSessionFactory().close(); // 关闭Session  
            }  
        }  
    }
	
	
	
	
	
/*	
	public void bathSave(List<?> ms){
		Object medicine = null; // 创建药品对象
		if (ms != null && ms.size() > 0) {
			try {
				for (int i = 0; i < ms.size(); i++) {
					medicine = ms.get(i); // 获取药品
					this.getSession().save(medicine); // 保存药品对象
					if (i % 50 == 0) {
						System.out.println("====> ");
						this.getSession().flush();
						this.getSession().clear();
					}
					
				}
			} catch (Exception e) {
				this.getSession().getTransaction().rollback(); // 出错将回滚事物
			}
		}
		
	}*/
	
	
	
	/*public void batchSave(List<?> ms) {
		Session session = null;
		if (ms != null && ms.size() > 0) {
			try {
				session = this.getSession(); // 获取Session
				session.beginTransaction(); // 开启事物
				Object medicine = null; // 创建药品对象
				// 循环获取药品对象
				for (int i = 0; i < ms.size(); i++) {
					medicine = ms.get(i); // 获取药品
					session.save(medicine); // 保存药品对象
					// 批插入的对象立即写入数据库并释放内存
					if (i % 50 == 0) {
						session.flush();
						session.clear();
					}
				}
				session.getTransaction().commit(); // 提交事物
			} catch (Exception e) {
				e.printStackTrace(); // 打印错误信息
				session.getTransaction().rollback(); // 出错将回滚事物
			} finally {
				this.getSession().close();// 关闭Session
			}
		}
	}*/
}
