package com.gwssi.optimus.plugin.auth.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.model.SmFunctionBO;
import com.gwssi.application.model.SmRoleBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.core.web.event.WebContext;
import com.gwssi.optimus.plugin.auth.OptimusAuthManager;
import com.gwssi.application.model.SmSysIntegrationBO;
import com.gwssi.optimus.plugin.auth.model.User;

@Service(value = "funcService")
public class FuncService extends BaseService {

	/**
	 * 权限管理---菜单管理中所需要的数据
	 * 
	 * @param sms
	 */
	public List findMenu(List<String> list, SmSysIntegrationBO sms)
			throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		if (sms == null || sms.equals("") || sms.equals(null)) {
			List<String> str = new ArrayList<String>(); // ？传值
			sql.append("select * from SM_SYS_INTEGRATION ");
			sql.append("where PK_SYS_INTEGRATION in (");
			sql.append(prepareSqlIn(list.size()));
			sql.append(")").append("  And EFFECTIVE_MARKER =").append("?")
					.append("  order by ORDER_NO");
			for (String s : list) {
				str.add(s);
			}

			str.add(AppConstants.EFFECTIVE_Y);
			List list1 = dao.pageQueryForList(sql.toString(), str);
			return list1;
		} else {
			List<String> str = new ArrayList<String>(); // ？传值
			String s1 = null;
			sql.append(" select * from  SM_SYS_INTEGRATION ");
			sql.append("where PK_SYS_INTEGRATION in (");
			sql.append(prepareSqlIn(list.size()));
			sql.append(")").append("  And EFFECTIVE_MARKER =").append("?  ");

			for (String s : list) {
				str.add(s);
			}
			str.add(AppConstants.EFFECTIVE_Y);
			if (sms != null) {
				String systemName = sms.getSystemName();
				if (!"".equals(systemName)) {
					sql.append(" and  LOWER(SYSTEM_NAME) like ? ");
					str.add("%" + systemName + "%");
				}
				String systemCode = sms.getSystemCode();
				if (!"".equals(systemCode)) {
					sql.append(" and  LOWER(SYSTEM_CODE) like ? ");
					str.add("%" + systemCode + "%");
				}
			}
			sql.append(" order by MODIFIER_TIME DESC");
			List list1 = dao.pageQueryForList(sql.toString(), str);
			return list1;
		}
	}

	public List findfuncitonid(List<String> list) throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "select FUNCTION_CODE from SM_ROLE_FUNC where ROLE_CODE=?";
		List list1 = dao.queryForList(sql, list);
		return list1;
	}

	public int findFunctionById(String pkFunction) throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();

		if (StringUtils.isBlank(pkFunction)) {
			return 1;
		}
		int smfunction = 0;
		List<String> str = new ArrayList<String>(); // ？传值
		StringBuffer sql = new StringBuffer();
		sql.append("select t.* from SM_FUNCTION t where t.FUNCTION_CODE= ")
				.append("?").append("");
		str.add(pkFunction);
		List userList = dao.queryForList(SmFunctionBO.class, sql.toString(),
				str);
		if (userList != null && !userList.isEmpty())
			smfunction = (int) userList.size();
		return smfunction;
	}

	/**
	 * 权限管理--菜单管理--菜单维护--具体细节（新增功能） 主系统下的新增功能
	 * 
	 * @param smfunction
	 * @throws OptimusException
	 */
	public void insertSmfunction(SmFunctionBO smfunction)
			throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(smfunction);
	}

	/**
	 * 权限管理 --菜单管理 （获取所有的系统）
	 * 
	 * @param sms
	 *            （为null 表示超级管理员 获取全部菜单）
	 * @return
	 * @throws OptimusException
	 */
	public List findMenu(SmSysIntegrationBO sms) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		List<String> str = new ArrayList<String>(); // ？传值
		StringBuffer sql = new StringBuffer();
		if (sms == null || sms.equals("") || sms.equals(null)) {
			sql.append(
					"select * from SM_SYS_INTEGRATION where EFFECTIVE_MARKER = ? ")
			// .append(AppConstants.EFFECTIVE_Y)
					.append(" order by ORDER_NO");
			str.add(AppConstants.EFFECTIVE_Y);
		} else {
			sql.append(" select * from  SM_SYS_INTEGRATION where 1=1 and EFFECTIVE_MARKER = ?   ");
			str.add(AppConstants.EFFECTIVE_Y);
			if (sms != null) {
				String systemName = sms.getSystemName();
				if (!"".equals(systemName)) {
					sql.append(" and  SYSTEM_NAME like ? ");
					str.add("%" + systemName + "%");
				}
				String systemCode = sms.getSystemCode();
				if (!"".equals(systemCode)) {
					sql.append(" and  LOWER(SYSTEM_CODE) like ? ");
					str.add("%" + systemCode.toLowerCase() + "%");
				}
			}
			sql.append(" order by MODIFIER_TIME DESC");
		}
		List list1 = dao.pageQueryForList(sql.toString(), str);
		return list1;
	}

	/**
	 * 权限管理--菜单管理 --菜单维护（获取系统的名字） 通过主键获取系统名字
	 * 
	 * @param pkSysIntegration
	 *            系统主键
	 * @return
	 * @throws OptimusException
	 */
	public SmSysIntegrationBO findSystem(String pkSysIntegration)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		SmSysIntegrationBO sms = dao.queryByKey(SmSysIntegrationBO.class,
				pkSysIntegration);
		return sms;
	}

	/**
	 * 权限管理--菜单管理--菜单维护（查询功能 ） 查询某些功能 或者全部功能
	 * 
	 * @param sysid
	 *            系统主键
	 * @param funcName
	 *            系统名字 like
	 * @return
	 * @throws OptimusException
	 */
	public List findFuncMenu(List<String> sysid, String funcName)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();

		StringBuffer sql = new StringBuffer();
		// List<String> str = new ArrayList<String>(); //？传值
		if (funcName == null || funcName.equals("") || funcName.equals(null)) {
			sql.append("select * from SM_FUNCTION where PK_SYS_INTEGRATION =?")
					.append("and EFFECTIVE_MARKER = ").append("?")
					.append(" order by ORDER_NO");
			sysid.add(AppConstants.EFFECTIVE_Y);

		} else {
			sql.append(
					"select * from SM_FUNCTION where PK_SYS_INTEGRATION =? and FUNCTION_NAME like ")
					.append("?").append("").append(" And EFFECTIVE_MARKER =")
					.append("?").append(" order by ORDER_NO");
			sysid.add("%" + funcName + "%");
			sysid.add(AppConstants.EFFECTIVE_Y);
		}
		List funcList = dao.pageQueryForList(sql.toString(), sysid);
		return funcList;
	}

	/**
	 * 权限管理--菜单管理--菜单维护--具体维护 某个系统的所有子系统返回树
	 * 
	 * @param pksys
	 * @param pkFunction
	 * @return
	 * @throws OptimusException
	 */
	public List findFuncSuperMenu(List<String> pksys, String pkFunction)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select FUNCTION_CODE as id,FUNCTION_NAME as name,SUPER_FUNC_CODE as P_ID from SM_FUNCTION  where  PK_SYS_INTEGRATION =?")
				.append("and EFFECTIVE_MARKER = ").append("?").append("");
		pksys.add(AppConstants.EFFECTIVE_Y);
		/*
		 * sql.append("  and FUNCTION_TYPE= ? ");
		 * pksys.add(AppConstants.FUNC_TYPE_1);
		 */
		if (pkFunction.equals(null) || pkFunction.equals("")) {

		} else {
			sql.append(" and FUNCTION_CODE != ").append("?").append("");
			pksys.add(pkFunction);
		}
		sql.append("order by ORDER_NO");
		List list1 = dao.queryForList(sql.toString(), pksys);
		return list1;
	}

	/**
	 * 权限管理--菜单管理--菜单维护--具体维护 获取某个模块的下层菜单
	 * 
	 * @param pksys
	 * @param pkFunction
	 * @return
	 * @throws OptimusException
	 */
	public List findFuncDownMenu(List<String> pksys, String pkFunction)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select FUNCTION_CODE as id,FUNCTION_NAME as name,SUPER_FUNC_CODE as P_ID from SM_FUNCTION  where    PK_SYS_INTEGRATION =?")
				.append("and EFFECTIVE_MARKER = ").append("?").append("");
		sql.append(" and FUNCTION_CODE != ").append("?").append("");
		sql.append("START WITH FUNCTION_CODE =? ");
		sql.append("   CONNECT BY SUPER_FUNC_CODE = PRIOR FUNCTION_CODE");
		sql.append("   order by ORDER_NO");
		List<String> str = new ArrayList<String>();
		str.add(pksys.get(0).toString());
		str.add(AppConstants.EFFECTIVE_Y);
		str.add(pkFunction);
		str.add(pkFunction);
		List list1 = dao.queryForList(sql.toString(), str);
		return list1;
	}

	public void deleteSomeFunc(SmFunctionBO smf) throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(smf);// 不是正在意义上的删除
		// dao.delete(smf);
	}

	public SmFunctionBO findFuncMenuByPK(String pkFunction)
			throws OptimusException {
		if (StringUtils.isEmpty(pkFunction))
			return null;
		IPersistenceDAO dao = getPersistenceDAO();
		SmFunctionBO smf = dao.queryByKey(SmFunctionBO.class, pkFunction);
		return smf;
	}

	public void updateSmfunction(SmFunctionBO smfunction)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(smfunction);

	}

	public void UpdateSmSysIntegration(SmSysIntegrationBO sms)
			throws OptimusException {
		// TODO Auto-generated method stub
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(sms);
	}

	public void deleteSomeSystem(SmSysIntegrationBO sms)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.delete(sms);
	}

	/**
	 * 限管理--菜单管理--菜单维护（获取系统的所有功能名称）
	 * 
	 * @param sysid
	 *            系统主键
	 * @return
	 * @throws OptimusException
	 */
	public List findFuncName(List<String> sysid) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select FUNCTION_NAME as text,FUNCTION_CODE as value from SM_FUNCTION where PK_SYS_INTEGRATION =?")
				.append("and EFFECTIVE_MARKER =").append("?")
				.append(" order by ORDER_NO");
		sysid.add(AppConstants.EFFECTIVE_Y);
		List funcList = dao.queryForList(sql.toString(), sysid);
		return funcList;
	}

	/**
	 * 权限管理--菜单管理--菜单维护--具体细节（获取某个系统的所有功能） 获取某个系统的所有功能
	 * 
	 * @param pksys
	 * @param superfunction
	 * @return
	 * @throws OptimusException
	 */
	public List<SmFunctionBO> findFuncNumer(List<String> pksys,
			String superfunction) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>();
		if (superfunction == null || superfunction.equals(null)
				|| superfunction.equals("")) {
			sql.append("select * from SM_FUNCTION where PK_SYS_INTEGRATION =?")
					.append(" And SUPER_FUNC_CODE is null")
					.append(" order by ORDER_NO");
			str.add(pksys.get(0).toString());
		} else {
			sql.append("select * from SM_FUNCTION where PK_SYS_INTEGRATION =?")
					.append(" And SUPER_FUNC_CODE = ").append("?")
					.append(" order by ORDER_NO");
			str.add(pksys.get(0).toString());
			str.add(superfunction);
		}
		List<SmFunctionBO> list1 = dao.queryForList(SmFunctionBO.class,
				sql.toString(), str);
		return list1;
	}

	public List<SmFunctionBO> findFuncMenuBycode(String superFuncCode,
			List<String> pksys) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from SM_FUNCTION where PK_SYS_INTEGRATION =?")
				.append(" And FUNCTION_CODE = ").append("?")
				.append(" order by ORDER_NO");
		pksys.add(superFuncCode);
		List<SmFunctionBO> l1 = dao.queryForList(SmFunctionBO.class,
				sql.toString(), pksys);
		return l1;
	}

	public List findFuncNullNumer() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from SM_FUNCTION where ")
				.append("SUPER_FUNC_CODE is null").append(" order by ORDER_NO");
		List list1 = dao.queryForList(sql.toString(), null);
		return list1;
	}

	public List findFuncCodeNumer(List<String> pksys, String functionCode)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>();
		str.add(pksys.get(0).toString());
		sql.append("select * from SM_FUNCTION where PK_SYS_INTEGRATION = ?")
				.append(" And FUNCTION_CODE = ").append("?")
				.append(" order by ORDER_NO");
		str.add(functionCode);
		// System.out.println("sql---:"+sql.toString());
		List list1 = dao.queryForList(sql.toString(), str);
		return list1;
	}

	/**
	 * 获取所有下级功能
	 * 
	 * @param smf
	 * @return
	 * @throws OptimusException
	 */

	public List<SmFunctionBO> findleaf(SmFunctionBO oldsmfunction)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		List<String> str = new ArrayList<String>(); // ？传值
		StringBuffer sql = new StringBuffer();
		sql.append("select * from SM_FUNCTION m where PK_SYS_INTEGRATION= ")
				.append("?").append("").append(" and FUNCTION_CODE != ")
				.append("?").append("  ").append("start with m.FUNCTION_CODE=")
				.append("?").append("  ")
				.append("  connect by m.SUPER_FUNC_CODE=prior m.FUNCTION_CODE")
				.append(" order by ORDER_NO");
		str.add(oldsmfunction.getPkSysIntegration());
		str.add(oldsmfunction.getFunctionCode());
		str.add(oldsmfunction.getFunctionCode());

		List<SmFunctionBO> list1 = dao.queryForList(SmFunctionBO.class,
				sql.toString(), str);
		return list1;
	}

	public List findFuncByPKTree(String pkSysIntegration)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>(); // ？传值
		sql.append(
				"SELECT FUNCTION_CODE AS ID, FUNCTION_NAME AS NAME, SUPER_FUNC_CODE AS pid FROM SM_FUNCTION sm_f")
				.append("  where SM_F.PK_SYS_INTEGRATION= ").append("?")
				.append("").append(" and EFFECTIVE_MARKER = ").append("?")
				.append(" order by ORDER_NO");
		str.add(pkSysIntegration);
		str.add(AppConstants.EFFECTIVE_Y);
		List list1 = dao.queryForList(sql.toString(), str);
		return list1;
	}

	public List<SmFunctionBO> findPkfunctionbyPKsysFuncCode(
			String functioncode, String pkSysIntegration)
			throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		List<String> str = new ArrayList<String>(); // ？传值
		StringBuffer sql = new StringBuffer();
		sql.append("select * from SM_FUNCTION where PK_SYS_INTEGRATION = ")
				.append("?").append(" ").append(" And FUNCTION_CODE = ")
				.append("?").append(" order by ORDER_NO");
		str.add(pkSysIntegration);
		str.add(functioncode);

		List<SmFunctionBO> list1 = dao.queryForList(SmFunctionBO.class,
				sql.toString(), str);
		return list1;
	}

	/**
	 * 权限管理-角色分配-岗位资源树
	 * 
	 * @param paramObject
	 *            系统查询字段对象集合
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findPostTree() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append(" select b.ORGAN_NAME as NAME,b.ORGAN_ID as ID ,b.PARENT_ID as PID ,b.ORGAN_TYPE as TYPE from ");
		sql.append(" ( select * from (select o.ORGAN_NAME,o.ORGAN_CODE, o.ORGAN_TYPE,s.PARENT_ID,s.STRU_PATH,s.STRU_ORDER,o.ORGAN_ID,s.STRU_ID from HR_ORGAN o, HR_STRU s where o.ORGAN_CODE = s.ORGAN_ID ) A start with PARENT_ID= '1' ");
		sql.append(" connect by prior ORGAN_CODE = PARENT_ID and ORGAN_TYPE <> '8' ) b union select POST_NAME as NAME , ID , ORGAN_ID as PID , '8' as TYPE from HR_POST ");
		List<Map> RoleList = dao.queryForList(sql.toString(), null);

		return RoleList;
	}

	/**
	 * 菜单管理--菜单维护--查询功能树
	 * 
	 * @param pkSysIntegration
	 *            系统主键
	 * @return
	 * @throws OptimusException
	 */
	public List findFuncTree(String pkSysIntegration) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>(); // ？传值
		sql.append(
				"SELECT FUNCTION_CODE AS ID, FUNCTION_NAME ||'('|| FUNCTION_CODE ||')'  AS NAME, "
						+ "SUPER_FUNC_CODE AS pid,FUNCTION_TYPE as functype "
						+ " , FUNCTION_URL as gnurl   "
						+ "FROM SM_FUNCTION sm_f")
				.append("  where SM_F.PK_SYS_INTEGRATION= ").append("?")
				.append("").append(" and EFFECTIVE_MARKER = ").append("?")
				.append(" order by function_code,order_no");
		str.add(pkSysIntegration);
		str.add(AppConstants.EFFECTIVE_Y);
		List list1 = dao.queryForList(sql.toString(), str);
		return list1;
	}

	/**
	 * 权限管理--菜单管理--删除功能（同时删除角色关联）
	 * 
	 * @param smf
	 * @throws OptimusException
	 */
	public void deleteRoleFunc(SmFunctionBO smf) throws OptimusException {
		List<String> str = new ArrayList<String>(); // ？传值
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "delete from SM_ROLE_FUNC t where t.FUNCTION_CODE= ? ";
		str.add(smf.getFunctionCode());
		dao.execute(sql, str);
	}

	/**
	 * 权限管理--菜单管理--菜单维护（改变list的pid）
	 * 
	 * @param funcList
	 * @param str
	 *            pid改为str
	 * @return
	 */
	public List getChangeList(List<Map<String, Object>> funcList, String str) {
		for (Map<String, Object> m : funcList) {

			if (m.get("pid") == null) {
				m.put("pid", str);
			}
		}
		return funcList;
	}

	/**
	 * 获取功能通过主键
	 * 
	 * @param pkFunction
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findFunByPk(String pkFunction) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List<String> str = new ArrayList<String>();

		sql.append("select * from SM_FUNCTION where FUNCTION_CODE = ?").append(
				" order by ORDER_NO");

		str.add(pkFunction);

		List list1 = dao.queryForList(sql.toString(), str);
		return list1;
	}

	/**
	 * 把字符串时间转换为Calendar类型
	 * 
	 * @param str
	 * @return
	 */
	public Calendar changeStringToCalendar(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;

	}

	/**
	 * 新增 没有上层功能代码 获取层次码 和功能代码
	 * 
	 * @param sm
	 *            原来的sm
	 * @param supermenu
	 *            当前开始数量
	 * @param roleCodeleft
	 *            左侧内容
	 * @return
	 * @throws OptimusException
	 */
	public SmFunctionBO findLeveCode(SmFunctionBO sm, int supermenu,
			String roleCodeleft) throws OptimusException {
		List<String> pksys = new ArrayList<String>();
		pksys.add(sm.getPkSysIntegration());
		boolean functionCodeExist = true;// 判断功能代码有没有
		// 有为true 没有为false
		int i = supermenu;
		int funcodeNumber;
		while (functionCodeExist) {// 判断功能代码是否存在
			i = i + 1;
			if (i <= 9) {
				sm.setFunctionCode(roleCodeleft + "0" + i);
				sm.setLevelCode(roleCodeleft + "0" + i);
			} else {
				sm.setFunctionCode(roleCodeleft + "" + i);
				sm.setLevelCode(roleCodeleft + "" + i);
			}
			funcodeNumber = findFuncCodeNumer(pksys, sm.getFunctionCode())
					.size();
			if (funcodeNumber > 0) {
				functionCodeExist = true;
			} else {
				functionCodeExist = false;
			}
		}
		return sm;
	}

	/**
	 * 新增 有上层代码 修改层次码 和功能代码
	 * 
	 * @param sm
	 * @param supermenu
	 * @param roleCodeleft
	 * @param sm1
	 * @return
	 * @throws OptimusException
	 */
	public SmFunctionBO findLeveCodeHaveSuperCode(SmFunctionBO sm,
			int supermenu, String roleCodeleft, SmFunctionBO sm1)
			throws OptimusException {
		List<String> pksys = new ArrayList<String>();
		pksys.add(sm.getPkSysIntegration());
		boolean functionCodeExist = true;// 判断功能代码有没有
		// 有为true 没有为false
		int i = supermenu;
		int funcodeNumber;
		while (functionCodeExist) {// 判断功能代码是否存在
			i = i + 1;
			if (i <= 9) {
				sm.setFunctionCode(sm1.getFunctionCode() + "0" + i);
				sm.setLevelCode("" + sm.getFunctionCode() + "."
						+ sm1.getLevelCode());
			} else {
				sm.setFunctionCode(sm1.getFunctionCode() + "" + i);
				sm.setLevelCode("" + sm.getFunctionCode() + "."
						+ sm1.getLevelCode());
			}
			funcodeNumber = findFuncCodeNumer(pksys, sm.getFunctionCode())
					.size();
			if (funcodeNumber > 0) {
				functionCodeExist = true;
			} else {
				functionCodeExist = false;
			}
		}
		return sm;
	}

	/**
	 * 改变上级菜单的功能类型
	 * 
	 * @param superFuncCode
	 * @throws OptimusException
	 */
	public void updateFuncType(String superFuncCode) throws OptimusException {
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);// 读取静态User

		SmFunctionBO smf1 = findFuncMenuByPK(superFuncCode);
		if (!smf1.equals("AppConstants.FUNC_TYPE_1")) {
			smf1.setFunctionType(AppConstants.FUNC_TYPE_1);
			smf1.setModifierId(user.getUserId());
			smf1.setModifierName(user.getUserName());
			smf1.setModifierTime(Calendar.getInstance());
		}

		updateSmfunction(smf1);

	}

	/**
	 * 获取当前前用户的所有角色
	 * 
	 * @param roleList
	 * @return
	 * @throws OptimusException
	 */
	public List<SmRoleBO> findRolebyPKList(List roleList)
			throws OptimusException {
		if (roleList == null)
			return null;
		List<Map> rolel = roleList;
		List<String> role = new ArrayList();
		for (Map r1 : rolel) {
			role.add((String) r1.get("roleCode"));
		}

		StringBuffer sql = new StringBuffer();

		sql.append("select * from SM_ROLE   ");
		sql.append("where role_code in (");
		sql.append(prepareSqlIn(roleList.size()));
		sql.append(")");
		IPersistenceDAO dao = getPersistenceDAO();
		List<SmRoleBO> smf = dao.queryForList(SmRoleBO.class, sql.toString(),
				role);

		return smf;
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
	 * 判断是否是管理员 
	 * 返回Map<String,String>1 isAdmin "Y" "N"  2 isSuperAdmin  "Y" "N"
	 * @return
	 * @throws OptimusException
	 */
	public Map isadmin() throws OptimusException{
		Map<String,String> map1 =new HashMap<String,String>();
		
		boolean isSuperAdmin = false; // 是否是超级管理员
		boolean isAdmin = false; // 是否是本系统管理员

		// 从缓存中获取当前登录用户信息
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);

		// 获取当前登录用户角色列表
		List <Map>roleList = user.getRoleList();
		
		// 判断是否是超级管理员，系统管理员，普通用户
		SmRoleBO bo = new SmRoleBO();
		List <SmRoleBO> smrolelist =findRolebyPKList(roleList);

		List <String> pksys = new ArrayList();
		for (SmRoleBO smrole:smrolelist) {
			
			//判断启用的角色是否为管理员
			if (AppConstants.ROLE_TYPE_SUPER.equals(smrole.getRoleType())&&AppConstants.ROLE_STATE_ON.equals(smrole.getRoleState())) {
				isSuperAdmin=true;
			}
			if(AppConstants.ROLE_TYPE_SYS.equals(smrole.getRoleType())&&AppConstants.ROLE_STATE_ON.equals(smrole.getRoleState())){
				pksys.add(smrole.getPkSysIntegration());
				isAdmin=true;
			}
			
		}
		List zhuxitong =null;
		if(isSuperAdmin){//超级管理员
			map1.put("isSuperAdmin", "Y");
		}else{
			map1.put("isSuperAdmin", "N");
		}
		
		if(isAdmin&&!isSuperAdmin){
			map1.put("isAdmin", "Y");
		}else{
			map1.put("isAdmin", "N");
		}  
		
		
		return map1;
		
	}	
	
	/**
	 * 获取当前用户的子系统主键
	 * @return
	 * @throws OptimusException
	 */
	public List <String> getAdminList() throws OptimusException{
		// 从缓存中获取当前登录用户信息
		HttpSession session = WebContext.getHttpSession();
		User user = (User) session.getAttribute(OptimusAuthManager.USER);

		// 获取当前登录用户角色列表
		List <Map>roleList = user.getRoleList();

		List <String> pksys = new ArrayList();
		List <SmRoleBO> smrolelist =findRolebyPKList(roleList);
		
		for (SmRoleBO smrole:smrolelist) {
			
			//启用的角色所具有的系统主键
			if(AppConstants.ROLE_TYPE_SYS.equals(smrole.getRoleType())&&AppConstants.ROLE_STATE_ON.equals(smrole.getRoleState())){
				pksys.add(smrole.getPkSysIntegration());
			}
		}
		return pksys;
		
	}	
	
}
