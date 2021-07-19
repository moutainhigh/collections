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

import com.tz.model.Exam;
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
public class ExamDao extends BaseDao{

	/**
	 * 
	 * 根据名称查询学科信息
	 * @author Administrator
	 * @param name 学科名称
	 * @return
	 * @since JDK 1.6
	 */
	public List<Object[]> findExams(TzParams params){
		StringBuilder builder = new StringBuilder(); 
		builder.append(" SELECT e.*,u.username FROM tz_exam  e  LEFT JOIN tz_user u ON e.user_id = u.id ");
		builder.append(" WHERE e.`status` = 1 AND e.is_delete = 0 ");
		if(params!=null){
			if(params.getSubjectId()!=null){
				builder.append(" and  e.subject_id = "+params.getSubjectId());
			}
			if(params.getFolderId()!=null){
				builder.append(" and  e.folder_id = "+params.getFolderId());
			}
			if(params.getUserId()!=null){
				builder.append(" and  e.user_id = "+params.getUserId());
			}
		}
		builder.append(" ORDER BY e.create_time DESC ");
		return getSession().createSQLQuery(builder.toString()).list();
	}
	
	/**
	 * 保存作用
	 * com.tz.dao 
	 * 方法名：saveExam
	 * 创建人：xuchengfei 
	 * 手机号码:15074816437
	 * 时间：2015年11月14日-上午12:12:39 
	 * @param exam
	 * @return 
	 * 返回类型：Exam
	 * @exception 
	 * @since  1.0.0
	 */
	public Exam saveExam(Exam exam){
		try {
			getSession().save(exam);
			return exam;
		} catch (Exception e) {
			return null;
		}
	}
}

