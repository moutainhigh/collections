package com.gwssi.application.webservice.client;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class PostService extends BaseService {

	private static Logger logger = Logger.getLogger(PostService.class);

	public HrUsersBO getLoginUser(String userId) throws OptimusException {

		IPersistenceDAO dao = getPersistenceDAO();

		// 编写查询sql
		StringBuffer sql = new StringBuffer();
		sql.append("select * from hr_users t where t.user_id = '");
		sql.append(userId);
		sql.append("'");
		logger.info("查询user的sql  " + sql);

		// 封装结果集
		List list = dao.queryForList(HrUsersBO.class, sql.toString(), null);
		HrUsersBO bo = new HrUsersBO();
		if (list.size() > 0)
			bo = (HrUsersBO) list.get(0);
		return bo;
	}

	public List getPostByUser(String userId) throws OptimusException {

		IPersistenceDAO dao = getPersistenceDAO();

		// 编写查询sql
		StringBuffer sql = new StringBuffer();
		sql.append("select p.id,p.post_name,p.organ_id from hr_post p, hr_post_personnel hp, hr_users u where p.id = hp.post_id and hp.organ_id = u.organ_id and u.user_id = '");
		sql.append(userId);
		sql.append("'");

		// 封装结果集
		logger.info("查询岗位sql  " + sql);
		List list = dao.queryForList(sql.toString(), null);
		return list;
	}

	
}
