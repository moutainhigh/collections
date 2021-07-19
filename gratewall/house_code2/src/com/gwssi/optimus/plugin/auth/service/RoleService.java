package com.gwssi.optimus.plugin.auth.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.model.SmPositionRoleBO;
import com.gwssi.application.model.SmRoleBO;
import com.gwssi.application.model.SmRoleFuncBO;
import com.gwssi.application.model.SmSysIntegrationBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

@Service
public class RoleService extends BaseService {

	public List queryRoleTree() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "select t.js_id as id, t.js_mc as name from t_pt_js t";
		List roleList = dao.queryForList(sql, null);
		return roleList;
	}

	public SmSysIntegrationBO getSystem(String pkSysIntegration)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		SmSysIntegrationBO sms = dao.queryByKey(SmSysIntegrationBO.class,
				pkSysIntegration);
		return sms;
	}

	/**
	 * 通过系统表主键获取所有角色
	 * 
	 * @param pksys
	 * @return
	 */
	public List<SmRoleBO> findSmrole(List<String> pksys)
			throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from SM_ROLE where PK_SYS_INTEGRATION =?");
		List<SmRoleBO> list1 = dao.queryForList(SmRoleBO.class, sql.toString(),
				pksys);
		return list1;
	}

	public List<SmRoleBO> findSmroleByPKCode(String roleCode)
			throws OptimusException {
		List<String> str = new ArrayList<String>(); // ？传值

		str.add(roleCode);
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from SM_ROLE where ROLE_CODE = ").append("?")
				.append("");
		List<SmRoleBO> list1 = dao.queryForList(SmRoleBO.class, sql.toString(),
				str);
		return list1;
	}

	public void addSmRole(SmRoleBO smrole) throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(smrole);
	}

	public List findRoleByPK(String pkRole) throws OptimusException {
		if (StringUtils.isEmpty(pkRole))
			return null;
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "select * from  SM_ROLE where ROLE_CODE= ? ";
		List<String> str = new ArrayList<String>(); // ？传值
		str.add(pkRole);
		List list = dao.queryForList(sql, str);

		// SmRoleBO smf = dao.queryByKey(SmRoleBO.class, pkRole);
		return list;
	}

	public void updateSmRole(SmRoleBO smrole) throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(smrole);
	}

	/**
	 * 角色id
	 * 
	 * @param jsId
	 * @return
	 * @throws OptimusException
	 */
	public List findAuthFunc(String jsId) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "select t.FUNCTION_CODE AS ID from SM_FUNCTION  t, SM_ROLE_FUNC kk where  "
				+ "t.FUNCTION_CODE =KK.FUNCTION_CODE " + "and kk.ROLE_CODE= ? ";
		List<String> str = new ArrayList<String>(); // ？传值
		str.add(jsId);
		List authFuncList = dao.queryForList(sql, str);
		return authFuncList;
	}

	/**
	 * 增加角色功能权限
	 * 
	 * @throws OptimusException
	 */
	public void addRoleFunc(SmRoleFuncBO smrobo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();

		dao.insert(smrobo);
	}

	/**
	 * 删除功能权限
	 * 
	 * @param pkRole
	 * @throws OptimusException
	 */
	public void deleteAllRoleFunc(String pkRole) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "delete from SM_ROLE_FUNC t where t.ROLE_CODE= ? ";
		List<String> str = new ArrayList<String>(); // ？传值
		str.add(pkRole);
		dao.execute(sql, str);
	}

	/**
	 * 获取SmRoleFuncBO
	 * 
	 * @return
	 * @throws OptimusException
	 */
	public List<SmRoleFuncBO> findAllSmRoleFunc() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from SM_ROLE_FUNC ");
		List<SmRoleFuncBO> list1 = dao.queryForList(SmRoleFuncBO.class,
				sql.toString(), null);
		return list1;
	}

	public SmRoleFuncBO findSmRoleFuncByPK(String str) throws OptimusException {
		if (StringUtils.isEmpty(str))
			return null;
		IPersistenceDAO dao = getPersistenceDAO();
		SmRoleFuncBO smf = dao.queryByKey(SmRoleFuncBO.class, str);
		return smf;
	}

	public List findAuthPosi(String pkRole) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "select PK_POSITION as id from SM_POSITION_ROLE where ROLE_CODE= ? ";
		List<String> str = new ArrayList<String>(); // ？传值
		str.add(pkRole);
		List authFuncList = dao.queryForList(sql, str);
		return authFuncList;
	}

	/**
	 * 删除某个角色的所有岗位
	 * 
	 * @param pkRole
	 * @throws OptimusException
	 */
	public void deleteAllRolePosi(String pkRole) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "delete from SM_POSITION_ROLE t where t.ROLE_CODE= ? ";
		List<String> str = new ArrayList<String>(); // ？传值
		str.add(pkRole);
		dao.execute(sql, str);
	}

	/**
	 * 获取所有的角色与岗位关联数据
	 * 
	 * @return
	 * @throws OptimusException
	 */
	public List<SmPositionRoleBO> findAllSmRolePosi() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from SM_POSITION_ROLE ");
		List<SmPositionRoleBO> list1 = dao.queryForList(SmPositionRoleBO.class,
				sql.toString(), null);
		return list1;
	}

	public SmPositionRoleBO findSmRolePosicByPK(String str)
			throws OptimusException {
		if (StringUtils.isEmpty(str))
			return null;
		IPersistenceDAO dao = getPersistenceDAO();
		SmPositionRoleBO smf = dao.queryByKey(SmPositionRoleBO.class, str);
		return smf;
	}

	public void addRolePosi(SmPositionRoleBO smrobo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();

		dao.insert(smrobo);
	}

	/**
	 * 角色管理 --角色维护（返回角色树） 返回角色树（只是返回了 普通角色的）
	 * 
	 * @param pkSysIntegration
	 * @return
	 * @throws OptimusException
	 */
	public List findRoleTree(String pkSysIntegration, boolean isSuperAdmin)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select ROLE_CODE as id , ROLE_NAME ||'('|| ROLE_CODE ||')' AS name ,'0' as pid from SM_ROLE"
						+ " where PK_SYS_INTEGRATION =?")
				.append(" and EFFECTIVE_MARKER = ").append("?").append("");

		List<String> str = new ArrayList<String>(); // ？传值
		str.add(pkSysIntegration);
		str.add(AppConstants.EFFECTIVE_Y);
		if (isSuperAdmin) {
			sql.append("  and (ROLE_TYPE= ?").append("or ROLE_TYPE=?)");
			str.add(AppConstants.ROLE_TYPE_DEFAULT);
			str.add(AppConstants.ROLE_TYPE_SYS);
		} else {
			sql.append("and ROLE_TYPE= ?");
			str.add(AppConstants.ROLE_TYPE_DEFAULT);
		}

		List funcList = dao.pageQueryForList(sql.toString(), str);
		return funcList;
	}

	public String findRoleCode(int rolecoderight, String roleCodeleft)
			throws OptimusException {
		String rolecode = null;

		boolean tr = true;// 判断是否存在 存在为true不存在为false
		while (tr) {
			rolecoderight = rolecoderight + 1;
			if (rolecoderight < 10) {
				rolecode = roleCodeleft + "00" + rolecoderight;
			} else if (rolecoderight < 100) {
				rolecode = roleCodeleft + "0" + rolecoderight;
			} else {
				rolecode = roleCodeleft + "" + rolecoderight;
			}

			List<SmRoleBO> now = findSmroleByPKCode(rolecode);// 找到当前下面的数量
			if (now.size() > 0) {

			} else {
				tr = false;
			}
		}
		return rolecode;
	}

	/**
	 * 获取普通角色列表
	 * 
	 * @param sysid
	 * @param funcName
	 * @return
	 * @throws OptimusException
	 */
	public List findRolebyPKsyIntenew(List<String> sysid, String funcName)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		List<String> str = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from SM_ROLE where PK_SYS_INTEGRATION =?")
				.append(" and EFFECTIVE_MARKER = ").append("?").append("")
				.append("  and ROLE_TYPE= ?");
		str.add(sysid.get(0).toString());
		str.add(AppConstants.EFFECTIVE_Y);
		str.add(AppConstants.ROLE_TYPE_DEFAULT);
		List funcList = dao.pageQueryForList(sql.toString(), str);
		return funcList;
	}

	/**
	 * 角色管理--角色维护（获取某个系统的角色内容）
	 * 
	 * @param sysid
	 * @return
	 * @throws OptimusException
	 */
	public List findRoleName(List<String> sysid) throws OptimusException {

		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append("select ROLE_NAME as text ,ROLE_CODE as value  from SM_ROLE  where PK_SYS_INTEGRATION =?");
		List funcList = dao.queryForList(sql.toString(), sysid);
		return funcList;

	}

	public List findRolebyPKsyInte(List<String> sysid, String funcName)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();

		StringBuffer sql = new StringBuffer();

		if (funcName == null || funcName.equals("") || funcName.equals(null)) {
			sql.append("select * from SM_ROLE where PK_SYS_INTEGRATION =?")
					.append(" and EFFECTIVE_MARKER = ").append("?").append("");
			sysid.add(AppConstants.EFFECTIVE_Y);
		} else {
			sql.append(
					"select * from SM_ROLE where PK_SYS_INTEGRATION =? and ROLE_NAME like ")
					.append("?").append("").append(" and EFFECTIVE_MARKER = ")
					.append("?").append("");
			sysid.add("%" + funcName + "%");
			sysid.add(AppConstants.EFFECTIVE_Y);
		}
		List funcList = dao.pageQueryForList(sql.toString(), sysid);
		return funcList;
	}

	public SmRoleBO findRoleByPKBO(String pkRole) throws OptimusException {
		if (StringUtils.isEmpty(pkRole))
			return null;
		IPersistenceDAO dao = getPersistenceDAO();
		SmRoleBO smf = dao.queryByKey(SmRoleBO.class, pkRole);
		return smf;
	}

	public void deleteSomeRole(SmRoleBO sms) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(sms);
	}
}
