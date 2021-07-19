package com.gwssi.application.home.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.model.SmScheduleBO;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class HomeService extends BaseService {

	private static Logger logger = Logger.getLogger(HomeService.class); // 日志

	/**
	 * 超级管理员能够访问所有系统
	 * 
	 * @return 所有集成系统集合
	 * @throws OptimusException
	 */
	public List<Map<String, Object>> getApp(String pkBusiDomain) throws OptimusException {

		IPersistenceDAO dao = getPersistenceDAO();

		// 编写查询sql
		StringBuffer sql = new StringBuffer();
		//sql.append("select sm.pk_sys_integration,sm.system_name,sm.system_code,sm.system_code as id,sm.integrated_url,sm.system_img_url,sm.system_img_url_code from SM_SYS_INTEGRATION sm where sm.effective_marker = ? and sm.pk_busi_domain = ? order by sm.order_no");
		sql.append("select sm.pk_sys_integration,sm.system_name,sm.system_code,sm.system_code as id,sm.integrated_url,sm.system_img_url,sm.system_img_url_code from SM_SYS_INTEGRATION sm where sm.effective_marker = ? and sm.parent_code = ? order by sm.order_no");
		// 封装参数
		List paramList = new ArrayList();
		paramList.add(AppConstants.EFFECTIVE_Y);
		paramList.add(pkBusiDomain);
		// 封装结果集
		List appList = dao.queryForList(sql.toString(), paramList);
		return appList;
	}

	/**
	 * 子系统管理员获取能够访问的系统
	 * 
	 * @param roleList
	 * @param sysAppCode
	 * @return 系统集合
	 * @throws OptimusException
	 */
	public List<Map> getApp(List roleList, String sysAppCode)
			throws OptimusException {

		List<Map> appList = new ArrayList();

		if (null != roleList && roleList.size() > 0) {
			IPersistenceDAO dao = getPersistenceDAO();

			// 编写查询sql
			StringBuffer sql = new StringBuffer();
			sql.append("select * from (select sm.pk_sys_integration,sm.system_name,sm.system_code,sm.system_code as id,sm.integrated_url,sm.system_img_url,sm.system_img_url_code,sm.order_no from SM_SYS_INTEGRATION sm,SM_ROLE r where sm.PK_SYS_INTEGRATION = r.PK_SYS_INTEGRATION ");

			// 添加查询条件
			sql.append(" and r.role_code in (");
			sql.append(prepareSqlIn(roleList.size()));
			sql.append(")");
			sql.append(" and sm.effective_marker = ? and sm.system_state = ? ");

			sql.append("union select sm.pk_sys_integration,sm.system_name,sm.system_code,sm.system_code as id,sm.integrated_url,sm.system_img_url,sm.system_img_url_code,sm.order_no from SM_SYS_INTEGRATION sm where sm.system_code = ? and  and sm.system_state = ? ) ");

			sql.append(" order by order_no");

			// 封装参数
			List paramList = new ArrayList();
			for (int i = 0; i < roleList.size(); i++) {
				Map map = (Map) roleList.get(i);
				paramList.add(map.get("roleCode"));
			}
			paramList.add(AppConstants.EFFECTIVE_Y);
			paramList.add(AppConstants.SYSTEM_STATE_ON);
			paramList.add(sysAppCode);
			paramList.add(AppConstants.SYSTEM_STATE_ON);

			// 封装结果集
			appList = dao.queryForList(sql.toString(), paramList);
		}

		return appList;
	}

	/**
	 * 根据当前登录用户的角色集合查询系统访问权限
	 * 
	 * @param roleList
	 * @return 系统集合
	 * @throws OptimusException
	 */
	public List<Map> getApp(List roleList) throws OptimusException {

		List<Map> appList = new ArrayList();

		if (null != roleList && roleList.size() > 0) {
			IPersistenceDAO dao = getPersistenceDAO();

			// 编写查询sql
			StringBuffer sql = new StringBuffer();
			sql.append("select sm.pk_sys_integration,sm.system_name,sm.system_code,sm.system_code as id,sm.integrated_url,sm.system_img_url,sm.system_img_url_code from SM_SYS_INTEGRATION sm, SM_ROLE r where sm.PK_SYS_INTEGRATION = r.PK_SYS_INTEGRATION ");

			// 添加查询条件
			sql.append(" and r.role_code in (");
			sql.append(prepareSqlIn(roleList.size()));
			sql.append(")");
			sql.append(" and sm.effective_marker = ? and sm.system_state = ? ");
			sql.append(" order by sm.order_no");

			// 封装参数
			List paramList = new ArrayList();
			for (int i = 0; i < roleList.size(); i++) {
				Map map = (Map) roleList.get(i);
				paramList.add(map.get("roleCode"));
			}
			paramList.add(AppConstants.EFFECTIVE_Y);
			paramList.add(AppConstants.SYSTEM_STATE_ON);
			// 封装结果集
			appList = dao.queryForList(sql.toString(), paramList);
		}

		return appList;
	}

	/**
	 * 获取某个系统的所有菜单
	 * 
	 * @param appCode
	 * @return 功能权限集合
	 * @throws OptimusException
	 */
	public List<Map> getFunc(String appCode) throws OptimusException {

		IPersistenceDAO dao = getPersistenceDAO();

		// 编写查询sql
		StringBuffer sql = new StringBuffer();
		sql.append("select fun.function_code,fun.pk_sys_integration,fun.function_name,fun.function_name_short,fun.function_type,fun.super_func_code,fun.function_url,fun.level_code,fun.remarks from SM_FUNCTION fun where fun.effective_marker = ?");
		sql.append(" and fun.pk_sys_integration = ?");
		sql.append(" order by fun.function_code,fun.order_no");

		Properties optimus = ConfigManager.getProperties("optimus");
		String syspk = optimus.getProperty("system.appID");
		// 封装参数
		List paramList = new ArrayList();
		paramList.add(AppConstants.EFFECTIVE_Y);
		paramList.add(syspk);

		
		// 封装结果集
		List<Map> functionList = dao.queryForList(sql.toString(), paramList);
		return functionList;
	}

	/**
	 * 获取子系统管理员访问系统管理的权限
	 * 
	 * @param appCode
	 * @param sysFuncCode
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> getFunc(String appCode, String sysFuncCode)
			throws OptimusException {

		IPersistenceDAO dao = getPersistenceDAO();
		// 编写查询sql
		StringBuffer sql = new StringBuffer();
		sql.append("select fun.function_code,fun.pk_sys_integration,fun.function_name,fun.function_name_short,fun.function_type,fun.super_func_code,fun.function_url,fun.level_code,fun.remarks from SM_FUNCTION fun where fun.function_code in (");
		sql.append(formatSqlIn(sysFuncCode));
		sql.append(")");
		sql.append(" and fun.effective_marker = ?");
		sql.append(" order by fun.function_code,fun.order_no");

		// 封装参数
		List paramList = new ArrayList();
		paramList.add(AppConstants.EFFECTIVE_Y);

		// 封装结果集
		List<Map> functionList = dao.queryForList(sql.toString(), paramList);
		return functionList;
	}

	/**
	 * 获取当前登录用户某个应用系统的功能权限
	 * 
	 * @param roleList
	 * @param appCode
	 * @return 功能权限集合
	 * @throws OptimusException
	 */
	public List<Map> getFunc(List roleList, String appCode)
			throws OptimusException {

		
		List<Map> functionList = new ArrayList();

		if (null != roleList && roleList.size() > 0 && appCode != null
				&& !"".equals(appCode)) {
			IPersistenceDAO dao = getPersistenceDAO();

			// 编写查询sql
			StringBuffer sql = new StringBuffer();
			sql.append("select distinct fun.function_code,fun.pk_sys_integration,fun.function_name,fun.function_name_short,fun.function_type,fun.super_func_code,fun.function_url,fun.level_code,fun.remarks from SM_FUNCTION fun, SM_ROLE_FUNC rf where fun.function_code = rf.function_code  and fun.effective_marker = ? ");
			sql.append("  and rf.role_code in(");
			sql.append(prepareSqlIn(roleList.size()));
			sql.append(")");
			sql.append(" order by fun.function_code");
			List paramList = new ArrayList();
			paramList.add(AppConstants.EFFECTIVE_Y);
			for (int i = 0; i < roleList.size(); i++) {
				Map map = (Map) roleList.get(i);
				paramList.add(map.get("roleCode"));
			}
			functionList = dao.queryForList(sql.toString(), paramList);
		}
		
		return functionList;
		
/*		List<Map> functionList = new ArrayList();

		if (null != roleList && roleList.size() > 0 && appCode != null
				&& !"".equals(appCode)) {

			IPersistenceDAO dao = getPersistenceDAO();

			// 编写查询sql
			StringBuffer sql = new StringBuffer();
			sql.append("select fun.function_code,fun.pk_sys_integration,fun.function_name,fun.function_name_short,fun.function_type,fun.super_func_code,fun.function_url,fun.level_code,fun.remarks from SM_FUNCTION fun, SM_ROLE_FUNC rf where fun.function_code = rf.function_code ");

			// 添加查询条件
			sql.append(" and rf.ROLE_CODE in (");
			sql.append(prepareSqlIn(roleList.size()));
			sql.append(")");
			sql.append(" and fun.effective_marker = ?");
			sql.append(" and fun.pk_sys_integration = ?");
			sql.append(" order by fun.function_code,fun.order_no");
			
			Properties optimus = ConfigManager.getProperties("optimus");
			String syspk = optimus.getProperty("system.appID");
			// 封装参数
			List paramList = new ArrayList();
			for (int i = 0; i < roleList.size(); i++) {
				Map map = (Map) roleList.get(i);
				paramList.add(map.get("roleCode"));
			}
			paramList.add(AppConstants.EFFECTIVE_Y);
			paramList.add(syspk);

			// 封装结果集
			functionList = dao.queryForList(sql.toString(), paramList);
		}

		return functionList;*/
	}

	/**
	 * 获取各个系统的待办数量
	 * 
	 * @param userid
	 *            用户id
	 * @return 各个系统的待办数
	 * @throws OptimusException
	 */
	public List<Map> getAppCount(String userid) throws OptimusException {

		IPersistenceDAO dao = getPersistenceDAO();

		// 编写查询sql
		StringBuffer sql = new StringBuffer();
		sql.append("select a.SYSTEM_CODE as id,a.TODO_NUM as count from SM_TODO a where TODO_USER = ? ");
		// 封装参数
		List paramList = new ArrayList();
		paramList.add(userid);

		// 封装结果集
		List<Map> functionList = dao.queryForList(sql.toString(), paramList);
		return functionList;
	}

	/**
	 * 获取当前用户的即时消息
	 * 
	 * @param AppId
	 * @throws OptimusException
	 */
	public List<Map> getMessageList(List postList, String type)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer condtion = new StringBuffer();
		if (postList.size() > 0) {
			String[] postcode = formatSqlIn(postList, "organId").toString()
					.split(",");
			for (int i = 0; i < postcode.length; i++) {
				String operate = "or";
				if (i == postcode.length - 1) {
					operate = "";
				}
				condtion.append(" and instr(a.SEND_TO," + postcode[i]
						+ ") > 0 " + operate);
			}
		}

		StringBuffer sql = new StringBuffer();
		sql.append(" select a.* from SM_NOTICE a where 1=1  "
				+ condtion.toString());
		if ("1".equals(type)) {
			sql.append(" order by a.MODIFIER_TIME DESC");
			return dao.pageQueryForList(sql.toString(), null);
		} else {
			sql.append(" and a.EFFECTIVE_TIME >= sysdate and sysdate >= a.MODIFIER_TIME ");
			sql.append(" order by a.MODIFIER_TIME DESC");
			return dao.queryForList(sql.toString(), null);
		}
	}
	
	/**
	 * 保存当前用户的日程安排
	 * 
	 * @param scheduleBo 日程bo对象
	 * @return
	 * @throws OptimusException
	 */
	public void saveSchedule(SmScheduleBO scheduleBo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(scheduleBo);
	}

	/**
	 * 获取当前用户的日程安排数量
	 * 
	 * @param userid
	 *            当前用户账号
	 * @throws OptimusException
	 */
	public List<Map> getScheduleCount(String userid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		List paramList = new ArrayList();
		paramList.add(userid);
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(1) as num,trunc(a.CREATER_TIME) as createrTime from SM_SCHEDULE a where a.CREATER_ID= ? group by  trunc(a.CREATER_TIME) ");
		return dao.queryForList(sql.toString(), paramList);
	}

	/**
	 * 获取当前用户的日程安排内容
	 * 
	 * @param AppId
	 * @throws OptimusException
	 */
	public List<Map> getScheduleContent(String userid, String date)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		List paramList = new ArrayList();
		paramList.add(userid);
		paramList.add(date);
		StringBuffer sql = new StringBuffer();
		sql.append(" select  a.*  from SM_SCHEDULE a  where a.CREATER_ID= ? and trunc(a.CREATER_TIME) =  trunc(to_date( ? ,'yyyy-mm-dd')) ");
		return dao.queryForList(sql.toString(), paramList);
	}

	/**
	 * 当前用户的日程安排删除
	 * 
	 * @param pkSchedule
	 *            日程Id编号
	 * @return
	 * @throws OptimusException
	 */
	public void deleteSchedule(String pkSchedule) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.deleteByKey(SmScheduleBO.class, pkSchedule);
	}
	
	/**
	 * 当前用户的日程安排提示
	 * 
	 * @param 
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> getTipSchedule(String userid,String state) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		List paramList = new ArrayList();
		paramList.add(userid);
		paramList.add(state);
		StringBuffer sql = new StringBuffer();
		sql.append(" select  a.*  from SM_SCHEDULE a  where a.CREATER_ID= ? and sysdate > a.COMPARE_TIME and a.COMPARE_STATE = ? and sysdate <= a.EFFECTIVE_TIME");
		return dao.queryForList(sql.toString(), paramList);
	}
	
	/**
	 * 更新当前用户的日程安排提示
	 * 
	 * @param 
	 * @return
	 * @throws OptimusException
	 */
	public void updateTipSchedule(String userid,String state) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		List paramList = new ArrayList();
		paramList.add(state);
		paramList.add(userid);
		StringBuffer sql = new StringBuffer();
		sql.append(" update SM_SCHEDULE a set a.COMPARE_STATE = ? where a.CREATER_ID= ? and sysdate > a.EFFECTIVE_TIME");
		dao.execute(sql.toString(), paramList);
	}
	
	/**
	 * 获取提醒类型
	 * 
	 * @param 
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> getRemind() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select NAME_SHORT as text , CODE as value from SM_DM_REMIND ");
		return dao.pageQueryForList(sql.toString(), listParam);	
	}

	/**
	 * 封装sql in 查询条件
	 * 
	 * @param length
	 * @return (?,?)
	 */
	private StringBuffer prepareSqlIn(int length) {
		StringBuffer builder = new StringBuffer();
		for (int i = 0; i < length;) {
			builder.append("?");
			if (++i < length) {
				builder.append(",");
			}
		}
		return builder;
	}

	/**
	 * 封装sql in 查询条件
	 * 
	 * @param list
	 * @return sqlin 语句
	 */
	private StringBuffer formatSqlIn(List list, String code) {
		StringBuffer sqlIn = new StringBuffer();
		if (null != list) {
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				if (i == 0) {
					sqlIn.append("'");
					sqlIn.append(map.get(code));
					sqlIn.append("'");
					continue;
				}
				sqlIn.append(",'");
				sqlIn.append(map.get(code));
				sqlIn.append("'");
			}
		}
		return sqlIn;
	}

	public static void main(String[] args) {
		int length = 3;
		StringBuffer builder = new StringBuffer();
		for (int i = 0; i < length;) {
			builder.append("?");
			if (++i < length) {
				builder.append(",");
			}
		}
		System.out.println(builder);
	}

	private StringBuffer formatSqlIn(String str) {
		StringBuffer sqlIn = new StringBuffer();
		String[] inList = str.split(",");
		for (int i = 0; i < inList.length; i++) {
			sqlIn.append("'");
			sqlIn.append(inList[i]);
			sqlIn.append("'");
			if (i < inList.length - 1)
				sqlIn.append(",");
		}
		return sqlIn;
	}
}
