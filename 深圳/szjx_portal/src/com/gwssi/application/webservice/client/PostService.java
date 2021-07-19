package com.gwssi.application.webservice.client;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class PostService extends BaseService {
	private static Logger logger = Logger.getLogger(PostService.class);
	public HrUsersBO getLoginUser(String userName) throws OptimusException {

		//Properties properties = ConfigManager.getProperties("UserRolesGet");
		//String databsename = properties.getProperty("yyjc.database.username");
		//String  vusers =properties.getProperty("yyjc.database.userview");
		IPersistenceDAO dao = getPersistenceDAO();

		// 编写查询sql
		StringBuffer sql = new StringBuffer();
		
		logger.info("通过系统管理库获取用户名当前用户id："+userName);
		sql.append("select * from  V_HR_USERS ");
		//sql.append(""+"."+vusers);
		sql.append("  t where upper(user_id) = '");
		
		
		userName=userName.toUpperCase();
		sql.append(userName);
		sql.append("'");

		// 封装结果集
		List list = dao.queryForList(HrUsersBO.class, sql.toString(), null);
		HrUsersBO bo = new HrUsersBO();
		if (list.size() > 0)
			bo = (HrUsersBO) list.get(0);
		return bo;
	}

	public List getPostByUser(String organId) throws OptimusException {

		IPersistenceDAO dao = getPersistenceDAO();

		// 编写查询sql
		StringBuffer sql = new StringBuffer();
		//sql.append("select p.id,p.post_name,t.post_id as organ_id,p.organ_id as super_organ_id from hr_post p,hr_post_personnel t where p.id = t.post_id and t.user_id = '");
		sql.append("select p.id, p.post_name,p.organ_id from HR_POST p,hr_post_personnel t where p.id =t.post_id and t.organ_id= '");
		sql.append(organId);
		sql.append("'");

		// 封装结果集
		System.out.println("查询岗位：");
		List list = dao.queryForList(sql.toString(), null);
		System.out.println("当前登录人的岗位为：" + list);

		return list;
	}
}
