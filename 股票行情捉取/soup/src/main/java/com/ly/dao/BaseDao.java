package com.ly.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Description: <br/>
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> <br/>
 * Copyright (C), 2001-2016, Yeeku.H.Lee <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name: <br/>
 * Date:
 * 
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public interface BaseDao<T> {
	// 根据ID加载实体
	 T get(Class<T> entityClazz, Serializable id);

	// 保存实体
	 Serializable save(T entity);

	// 更新实体
	 void update(T entity);

	// 删除实体
	 void delete(T entity);

	// 根据ID删除实体
	 void delete(Class<T> entityClazz, Serializable id);

	// 获取所有实体
	 List<T> findAll(Class<T> entityClazz);

	// 获取实体总数
	 long findCount(Class<T> entityClazz);

	 void batchSave(List<T> list);
	 
	 
	 void batchUpdate(List<T> list);
	
	 void  truncateTable(Class<T> entityClazz);
	 
	 
	 public void updateByHql(String hql,Object...params);

}
