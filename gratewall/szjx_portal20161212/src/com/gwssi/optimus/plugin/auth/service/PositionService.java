package com.gwssi.optimus.plugin.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class PositionService extends BaseService{
	
	/**
	 * 岗位管理-系统管理
	 * 
	 *
	 * @return
	 * @throws OptimusException
	 */
	public List findSystemTree(boolean isSuperAdmin)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select t.pk_sys_integration as id , t.SYSTEM_NAME AS name ,'0' as pid from SM_SYS_INTEGRATION t"
						+ " where t.EFFECTIVE_MARKER = ?" + "and t.system_type = ?");

		List<String> str = new ArrayList<String>(); // ？传值
		str.add(AppConstants.EFFECTIVE_Y);
		str.add(AppConstants.SYSTEM_TYPE_SYS);
		List funcList = dao.queryForList(sql.toString(), str);
		return funcList;
	}
	
	
	/**
	 * 岗位管理-角色管理
	 * 
	 *
	 * @return
	 * @throws OptimusException
	 */
	public List findRoleTree(boolean isSuperAdmin)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sqlStr = "SELECT r.ROLE_CODE AS ID,r.role_name AS NAME,i.pk_sys_integration AS pid FROM SM_SYS_INTEGRATION i INNER JOIN JC_ROLE r ON i.system_code = r.APP_CODE where  i.system_type = ? AND i.EFFECTIVE_MARKER = ?";
		List<String> str = new ArrayList<String>(); // ？传值
		str.add(AppConstants.SYSTEM_TYPE_SYS);
		str.add(AppConstants.EFFECTIVE_Y);
		
		List funcList = dao.queryForList(sqlStr, str);
		return funcList;
	}
	
	/**
	 * 删除功能权限
	 * 
	 * @param pkRole
	 * @throws OptimusException
	 */
	public void deleteAllRoleFunc(String pkposition) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "delete from SM_POSITION_ROLE t where t.pk_position= ? ";
		List<String> str = new ArrayList<String>(); // ？传值
		str.add(pkposition);
		dao.execute(sql, str);
	}
	
	/**
	 * 岗位管理-当前角色管理
	 * 
	 *
	 * @return
	 * @throws OptimusException
	 */
	public List findcurrentRole(String pkposition)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sqlStr = "select * from SM_POSITION_ROLE  pr INNER JOIN jc_role r ON r.ROLE_CODE = pr.ROLE_CODE where PR.PK_POSITION=? ";
		List<String> str = new ArrayList<String>(); 
		str.add(pkposition);
		
		List funcList = dao.queryForList(sqlStr, str);
		return funcList;
	}
	
	
	/**
	 * 增加角色功能权限
	 * 
	 * @throws OptimusException
	 */
	public void addPositionFunc(SmPositionRoleBO smpobo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();

		dao.insert(smpobo);
	}
	
	/**
	 * 查询所有角色
	 * 
	 * @throws OptimusException
	 */
	public List<SmPositionRoleBO> queryAllPositionFunc() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "select * from SM_POSITION_ROLE";
		StringBuilder str = new StringBuilder();
		List<SmPositionRoleBO> res = dao.queryForList(SmPositionRoleBO.class,sql.toString(),null);
		return res;
	}
	/**
	 * 根据角色查询系统和功能
	 * 
	 * @throws OptimusException
	 */
	public List findSysByPKRole(String pkRole)
	throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>(); // ？传值
		sql.append(
				"select t.SYSTEM_CODE as id , t.SYSTEM_NAME AS name ,'0' as pid from SM_ROLE s, SM_SYS_INTEGRATION t "
						+ " where s.EFFECTIVE_MARKER = ?" + "and t.EFFECTIVE_MARKER = ?" + "and s.role_code = ?" + "and s.pk_sys_integration = t.pk_sys_integration");
		str.add(AppConstants.EFFECTIVE_Y);
		str.add(AppConstants.EFFECTIVE_Y);
		str.add(pkRole);
		List list1 = dao.queryForList(sql.toString(), str);
		return list1;
}
	
	public List findFuncByPKTree(String pkSysIntegration)
		throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>(); // ？传值
		sql.append("select t.function_code as id, t.function_name as name, s.SYSTEM_CODE as pid from SM_FUNCTION t, SM_SYS_INTEGRATION s where t.pk_sys_integration = s.pk_sys_integration"
				+" and t.pk_sys_integration = ?" + "and t.EFFECTIVE_MARKER = ?" + "and s.EFFECTIVE_MARKER = ?");
		str.add(pkSysIntegration);
		str.add(AppConstants.EFFECTIVE_Y);
		str.add(AppConstants.EFFECTIVE_Y);
		List list1 = dao.queryForList(sql.toString(), str);
		return list1;
}
	
	public List getPositionName(String pkPosition)
		throws OptimusException{
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>();
		sql.append("select t.post_name from HR_POST t where t.id = ? ");
		str.add(pkPosition);
		List list1 = dao.queryForList(sql.toString(), str);
		return list1;
	}
	
	public List findSystemTreeByPK(String pkRole)
			throws OptimusException {
				IPersistenceDAO dao = getPersistenceDAO();
				String sql = "select distinct(i.pk_sys_integration)as ID,i.system_code as NAME,'0' AS pid from SM_POSITION_ROLE pr  INNER JOIN jc_role r ON r.ROLE_CODE = pr.ROLE_CODE INNER JOIN SM_SYS_INTEGRATION i ON i.system_code = r.app_code WHERE pr.role_code=?";
				List<String> str = new ArrayList<String>(); 
				str.add(pkRole);
				List funcList = dao.queryForList(sql, str);
				return funcList;
		}
	
	public List findRoleTreeByPK(String pkRole)
	throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql ="select r.role_code AS ID,r.role_name AS NAME,i.pk_sys_integration AS pid from SM_POSITION_ROLE pr  INNER JOIN jc_role r ON r.ROLE_CODE = pr.ROLE_CODE INNER JOIN SM_SYS_INTEGRATION i ON i.system_code = r.app_code WHERE r.role_code =? and i.EFFECTIVE_MARKER = ? and i.system_type = ?";
		List<String> str = new ArrayList<String>(); // ？传值
		str.add(pkRole);
		str.add(AppConstants.EFFECTIVE_Y);
		str.add(AppConstants.SYSTEM_TYPE_SYS);
		List funcList = dao.queryForList(sql.toString(), str);
		return funcList;
}

	/**
	 * 获取当前岗位所具有的所有角色
	 * @param pkposition
	 * @return 
	 * @throws OptimusException 
	 */
	public List findRoleList(String pkposition) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append("select r.pk_sys_integration as pid ,r.role_code as id ,r.role_name as name from sm_role r where r.role_code in(select t.role_code from sm_position_role t where t.pk_position =? ) ");
		List<String> str = new ArrayList<String>(); // ？传值
		str.add(pkposition);
		List roleList =dao.queryForList(sql.toString(), str);
		return roleList;
	}

	/**
	 * 获取岗位所对应的系统
	 * @param pkposition
	 * @return
	 * @throws OptimusException 
	 */
	public List findPosiSYs(String pkposition) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append("select s.pk_sys_integration as id,s.system_name as name, '0' as pid from sm_sys_integration s where s.pk_sys_integration in(");
		sql.append(" select r.pk_sys_integration from sm_role r where r.role_code in(select t.role_code from sm_position_role t where t.pk_position = ? )");
		sql.append(") order by s.order_no");
		List<String> str = new ArrayList<String>(); // ？传值
		str.add(pkposition);
		List roleList =dao.queryForList(sql.toString(), str);
		return roleList;
	}
	
}
