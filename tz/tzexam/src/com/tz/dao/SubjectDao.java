/**
 * Project Name:tzexam
 * File Name:SubjectDao.java
 * Package Name:com.tz.dao
 * Date:2015年10月23日下午10:47:50
 * Copyright (c) 2015, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.tz.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tz.model.Subject;
import com.tz.util.TzStringUtils;
/**
 * ClassName:SubjectDao <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2015年10月23日 下午10:47:50 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Repository
public class SubjectDao extends BaseDao{

	/**
	 * 
	 * 根据名称查询学科信息
	 * @author Administrator
	 * @param name 学科名称
	 * @return
	 * @since JDK 1.6
	 */
	public List<Subject> findSubjects(String name){
		//hql:他是一种面向对象的sql形式，但是表中的字段没有任何的关系。只能javabean有关系
		String hql =" FROM Subject WHERE isDelete = 0 AND status = 1 ";
		if(TzStringUtils.isNotEmpty(name)){
			hql+=" AND name LIKE '%"+name+"%'";
		}
		hql+=" ORDER BY createTime desc ";
		return getSession().createQuery(hql).list();
	}
}

