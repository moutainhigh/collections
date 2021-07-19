package com.gwssi.application.webservice.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
		
		Properties properties = ConfigManager.getProperties("UserRolesGet");
		String databsename = properties.getProperty("yyjc.database.username");
		String  vusers =properties.getProperty("yyjc.database.userview");
		IPersistenceDAO dao = getPersistenceDAO();

		// 编写查询sql
		StringBuffer sql = new StringBuffer();
		
		logger.info("通过系统管理库获取用户名当前用户id："+userName);
		sql.append("select * from  ");
		sql.append(databsename+"."+vusers);
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

	
	//获取当前用户的组织机构
	public String getOrgCode(String userName) throws OptimusException {
		Properties properties = ConfigManager.getProperties("UserRolesGet");
		String databsename = properties.getProperty("yyjc.database.username");
		String  vusers =properties.getProperty("yyjc.database.userview");
		IPersistenceDAO dao = getPersistenceDAO();
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select * from  ");
		sql.append(databsename+"."+vusers);
		sql.append("  t where upper(user_id) = '");
		
		
		userName=userName.toUpperCase();
		sql.append(userName);
		sql.append("'");
		String orgCode = null;
		List<Map> list = dao.queryForList(sql.toString(), null);
		if(list!=null && list.size()>0){
			Map<String,Object> map = list.get(0);
			orgCode=(String) map.get("departmentCode");

		}
		
		return orgCode;

	}
	
	//获取上级组织机构代码
	public String getUpOrgCode(String orgcode) throws OptimusException {
		Properties properties = ConfigManager.getProperties("UserRolesGet");
		String databsename = properties.getProperty("yyjc.database.username");
		String upOrgCode=null;
		IPersistenceDAO dao = getPersistenceDAO();

		String sql ="select * " +
		"  from "+databsename+".jc_public_department t" + 
		" where t.code in (select y.code   from  "+databsename+".jc_public_department  y   where t.parent_code = '0000' and y.flag = '1')" + 
		"   and t.flag = '1'" + 
		" START WITH t.code = ? " + 
		"CONNECT BY PRIOR t.parent_code = t.code";
		
		ArrayList<String> paramlist = new ArrayList<String>();
		paramlist.add(orgcode);
		String orgCode = null;
		List<Map> list = dao.queryForList(sql, paramlist);
		if(list!=null && list.size()>0){
			Map<String,Object> map = list.get(0);
			upOrgCode=(String) map.get("code");
		}
		
		return upOrgCode;
	}
	
	public List getPostByUser(String organId) throws OptimusException {

		IPersistenceDAO dao = getPersistenceDAO();

	
		// 编写查询sql
		StringBuffer sql = new StringBuffer();
		
		//未更改 author ：liz
		//sql.append("select p.id,p.post_name,t.post_id as organ_id,p.organ_id as super_organ_id from hr_post p,hr_post_personnel t where p.id = t.post_id and t.user_id = '");
	
		//修改后 autor：chaihaowei 表结构 变更
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
