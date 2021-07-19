package com.gwssi.optimus.plugin.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.safehaus.uuid.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.model.SmWhiteFunctionBO;
import com.gwssi.optimus.core.common.ConfigManager;
import com.gwssi.optimus.core.common.Constants;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.mysql.jdbc.log.Log;

@Service
public class AuthService extends BaseService {

	public List<Map> getUserList() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "SELECT dlm from t_pt_yh";
		return dao.queryForList(sql, null);
	}

	/**
	 * 根据用户登录名查询出该用户对应的角色列表
	 * 
	 * @param userId
	 *            用户登录名
	 * @return 用户对应的角色列表，Map中key是jsId
	 * @throws OptimusException
	 */
	public List<Map> getRoleIdListByLoginName(String userId)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append("select b.JS_ID from t_pt_yh a,t_pt_yhjs b ");
		if (StringUtils.isNotEmpty(userId)) {
			sql.append("where a.LOGIN_NAME='" + userId
					+ "' and a.USER_ID=b.USER_ID");
		}
		List roleList = dao.queryForList(sql.toString(), null);
		if (roleList == null)
			roleList = new ArrayList<Map>();
		if (roleList.isEmpty()) {
			Map map = new HashMap();
			map.put("jsId", OptimusAuthManager.DEFAULT_ROLE_ID);
			roleList.add(map);
		}
		return roleList;
	}

	/**
	 * 读取白名单，以便放入缓存
	 * 
	 * @return
	 * @throws OptimusException
	 */
	public List<SmWhiteFunctionBO> getUrlWhiteList() throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "select * from SM_WHITE_FUNCTION ";
		return dao.queryForList(SmWhiteFunctionBO.class, sql, null);
	}

	/**
	 * 根据登录名获取功能树中是菜单的部分
	 * 
	 * @param dlm
	 *            登录名
	 * @param flag
	 *            布尔值表示是否是超级管理员
	 * @return 该登录名下是菜单的功能树
	 * @throws OptimusException
	 */
	public List queryMenu(String login_name, boolean flag)
			throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sqlSbf = null;
		if (Constants.SECURITY_AUTHCHECK && !flag) {
			sqlSbf = new StringBuffer(
					"select gn.gn_id as id, "
							+ "gn.sjgn_id as pid, gn.gn_mc as name ,gn.url as frameUrl "
							+ "from t_pt_gn gn,t_pt_yhjs yhjs,t_pt_jsgn jsgn,t_pt_yh yh "
							+ "WHERE gn.sfcd='1' ");
			sqlSbf.append(
					"AND yh.user_id=yhjs.USER_ID AND yhjs.js_id=jsgn.js_id "
							+ "AND jsgn.gn_id = gn.gn_id AND yh.login_name = '")
					.append(login_name).append("'");
		} else {
			sqlSbf = new StringBuffer(
					"select t.gn_id as id, t.sjgn_id as pid, "
							+ "t.gn_mc as name ,t.url as frameUrl from t_pt_gn t WHERE t.sfcd='1' ");
		}
		sqlSbf.append(" order by xh");
		List funcList = dao.queryForList(sqlSbf.toString(), null);
		return funcList;
	}

	/**
	 * 根据登录名得到登陆后跳转的URL
	 * 
	 * @param user_name
	 *            登录名
	 * @return 登录后跳转的URL列表，其中为空是没有默认跳转URL
	 * @throws OptimusException
	 */
	public List getDefaultUrl(String login_name) throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "SELECT b.DEF_URL from t_pt_yh a,t_pt_js b where a.login_name= '"
				+ login_name + "' and a.ZY_JS_ID = b.JS_ID ";
		List urlList = dao.queryForList(sql, null);
		return urlList;
	}

	/**
	 * 通过岗位id及应用主键，获取角色信息。该方法是暂时用的，因为岗位数据并不在本系统的数据库中
	 * 
	 * @param list
	 * @param appId
	 * @return
	 * @throws OptimusException
	 */
	public List getRoleIdByPost(List postlist) throws OptimusException {

		List roleList = new ArrayList();
		if (postlist.size() > 0) {
			// 当有岗位时
			IPersistenceDAO dao = getPersistenceDAO();
			StringBuffer sql = new StringBuffer();
			sql.append("select r.role_code,r.pk_sys_integration,r.role_name,r.role_type,r.role_state from sm_role r,sm_position_role p where r.role_code = p.role_code ");

			// 添加查询条件
			sql.append(" and p.pk_position in  (");
			sql.append(prepareSqlIn(postlist.size()));
			sql.append(")");
			sql.append(" and r.effective_marker = ?");
			sql.append(" order by r.order_no");

			// 封装参数
			List paramList = new ArrayList();
			for (int i = 0; i < postlist.size(); i++) {
				Map map = (Map) postlist.get(i);
				paramList.add(map.get("id"));
			}
			paramList.add(AppConstants.EFFECTIVE_Y);

			// 封装结果集
			roleList = dao.queryForList(sql.toString(), paramList);
		}

		return roleList;
	}

	/**
	 * 根据岗位查询出角色集合
	 * 
	 * @param post
	 * @return 角色集合
	 * @throws OptimusException
	 */
	public List getRoleByPost(String post) throws OptimusException {

		List roleList = new ArrayList();
		// 当有岗位时
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append("select r.role_code,r.pk_sys_integration,r.role_name,r.role_type,r.role_state from sm_role r,sm_position_role p where r.role_code = p.role_code ");

		// 添加查询条件
		sql.append(" and p.pk_position in  (");
		sql.append(formatSqlIn(post));
		sql.append(")");
		sql.append(" and r.effective_marker = ?");
		sql.append(" order by r.order_no");

		// 封装参数
		List paramList = new ArrayList();
		paramList.add(AppConstants.EFFECTIVE_Y);

		// 封装结果集
		roleList = dao.queryForList(sql.toString(), paramList);

		return roleList;
	}

	private StringBuffer formatSqlIn(List list) {
		StringBuffer sqlIn = new StringBuffer();
		if (null != list) {
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				if (i == 0) {
					sqlIn.append("'");
					sqlIn.append(map.get("id"));
					sqlIn.append("'");
					continue;
				}
				sqlIn.append(",'");
				sqlIn.append(map.get("id"));
				sqlIn.append("'");
			}
		}
		return sqlIn;
	}

	/**
	 * 深圳工商应用集成使用 查询系统所有角色ID
	 * 
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> getRoleIdList() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "select PK_ROLE from SM_ROLE";
		return dao.queryForList(sql.toString(), null);
	}

	/**
	 * 深圳工商应用集成使用
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 角色对应功能url list
	 * @throws OptimusException
	 */
	public List<Map> getUrlList(String roleId) throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		// 1、修改，去除所有白名单验证，所有功能url及页面都需设置权限，只检查.do功能权限/filter不拦截所有，只拦截.do
		// 2、查询功角色对应功能url
		String sql = "select a.FUNCTION_URL from SM_FUNCTION a ,SM_ROLE_FUNC b where a.PK_FUNCTION=b.PK_FUNCTION and b.PK_ROLE='"
				+ roleId + "'";

		List<Map> urlList = dao.queryForList(sql, null);
		return urlList;
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

	/**
	 * 需要拦截的url
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> dofiterUrl() throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		Properties properties = ConfigManager.getProperties("common");
		String sysAppCode = properties.getProperty("common.sys.code");
		String sql= "select t.function_url  from sm_function t, sm_sys_integration s  where t.pk_sys_integration = s.pk_sys_integration"
				+ "   and s.system_code = ? and t.effective_marker =? and s.effective_marker=? and t.function_url is not null";
				
		List<String> paramlist = new ArrayList<String>();
		paramlist.add(sysAppCode);
		paramlist.add(AppConstants.EFFECTIVE_Y);
		paramlist.add(AppConstants.EFFECTIVE_Y);
		
		List<Map> urlList = dao.queryForList(sql, paramlist);
		return urlList;
	}
	
	/**
	 * 通过角色和用户的关联关系获取角色信息 
	 * 修改时间:2016年11月7日14:30:30
	 * 修改人:chaihaowei
	 * @param userId
	 * @return
	 * @throws OptimusException
	 */
	public List getRoleIdByUsersRole(String userId) throws OptimusException {
		
		Properties properties = ConfigManager.getProperties("UserRolesGet");
		String databsename = properties.getProperty("yyjc.database.username");
		String  vusersRolese =properties.getProperty("yyjc.database.userRoles");
		
		List roleList = new ArrayList();
		if(StringUtils.isNotBlank(userId)){
			IPersistenceDAO dao = getPersistenceDAO();
			StringBuffer sql = new StringBuffer();
			sql.append("select * from  ");
			if(StringUtils.isNotBlank(databsename)){
				sql.append(databsename+"." +vusersRolese);
			}
			sql.append("  where upper(user_id) = ? ");
			List paramList = new ArrayList();
			paramList.add(StringUtils.upperCase(userId));
			roleList = dao.queryForList(sql.toString(), paramList);
		}
		return roleList;
		

	}

}
