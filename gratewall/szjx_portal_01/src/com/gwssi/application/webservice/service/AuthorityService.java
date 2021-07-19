package com.gwssi.application.webservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service(value = "authorityService")
public class AuthorityService extends BaseService {

	private static Logger logger = Logger.getLogger(AuthorityService.class);

	public String getUserFunc(String paramXml) {
		
		return null;
	}
	
	/**
	 * 获取岗位id所对应的角色类型
	 * @param postId
	 * @param appCode 
	 * @return 角色类型
	 * @throws OptimusException
	 */
	public List<Map> getRoleType(String postId, String appCode) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写sql
		StringBuffer sql = new StringBuffer(); 
		sql.append("select r.PK_SYS_INTEGRATION,r.ROLE_TYPE from SM_POSITION_ROLE pr,SM_ROLE r ,(select PK_SYS_INTEGRATION from SM_SYS_INTEGRATION where SYSTEM_CODE=?) s ")
			.append("where r.ROLE_CODE=pr.ROLE_CODE and pr.PK_POSITION=? and r.PK_SYS_INTEGRATION=s.PK_SYS_INTEGRATION and r.EFFECTIVE_MARKER=?");
		listParam.add(appCode);
		listParam.add(postId);
		listParam.add(AppConstants.EFFECTIVE_Y);
		
		//封装数据
		List list = dao.queryForList(sql.toString(), listParam);
		return list;
		
	}
	
	/**
	 * 获取超级管理员和本系统管理员的功能权限
	 * @param appCode
	 * @return 功能集合
	 * @throws OptimusException
	 */
	public List<Map> getFunction(String appCode) throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select f.FUNCTION_CODE,f.FUNCTION_NAME,f.FUNCTION_NAME_SHORT,f.FUNCTION_TYPE,f.SUPER_FUNC_CODE,")
			.append("f.FUNCTION_URL,f.LEVEL_CODE,f.ORDER_NO from SM_FUNCTION f,SM_SYS_INTEGRATION s where s.SYSTEM_CODE=? ")
			.append("and f.PK_SYS_INTEGRATION=s.PK_SYS_INTEGRATION and f.EFFECTIVE_MARKER=?");
		listParam.add(appCode);
		listParam.add(AppConstants.EFFECTIVE_Y);
		
		//封装数据
		List list = dao.queryForList(sql.toString(), listParam);
		return list;
	}

	/**
	 * 获取普通用户的功能权限
	 * @param appCode,pkSysIntegration
	 * @return 功能集合
	 * @throws OptimusException
	 */
	public List getDefaultFunction(String appCode, String pkSysIntegration) throws OptimusException{
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//编写sql
		StringBuffer sql = new StringBuffer();
		sql.append("select  f.FUNCTION_CODE,f.FUNCTION_NAME,f.FUNCTION_NAME_SHORT,f.FUNCTION_TYPE,")
			.append("f.SUPER_FUNC_CODE,f.FUNCTION_URL,f.LEVEL_CODE,f.ORDER_NO from ")
			.append("(select rf.FUNCTION_CODE from SM_ROLE_FUNC rf,SM_ROLE r where r.ROLE_CODE=rf.ROLE_CODE and r.PK_SYS_INTEGRATION=? ) t, ")
			.append("SM_FUNCTION f,(select PK_SYS_INTEGRATION from SM_SYS_INTEGRATION where SYSTEM_CODE=? ) m ")
			.append("where t.FUNCTION_CODE=f.FUNCTION_CODE and f.PK_SYS_INTEGRATION=m.PK_SYS_INTEGRATION and f.EFFECTIVE_MARKER=?");
		listParam.add(pkSysIntegration);
		listParam.add(appCode);
		listParam.add(AppConstants.EFFECTIVE_Y);
		
		//封装数据
		List list = dao.queryForList(sql.toString(), listParam);
		return list;
	}
}