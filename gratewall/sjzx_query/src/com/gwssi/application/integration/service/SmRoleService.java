/**
 * 
 */
package com.gwssi.application.integration.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gwssi.application.common.AppConstants;
import com.gwssi.application.model.SmRoleBO;
import com.gwssi.application.model.SmSysIntegrationBO;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;

/**
 * @author xiaohan
 *
 */
@Service
public class SmRoleService extends BaseService {

	/**
	 * 应用集成-权限新增
	 * 
	 * @param rolebo 权限bo对象
	 * @return
	 * @throws OptimusException
	 */
	public void saveRole(SmRoleBO rolebo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(rolebo);
	}
	
	/**
	 * 应用集成-根据id权限查询
	 * 
	 * @param roleCode 权限id主键
	 * @return
	 * @throws OptimusException
	 */
	public SmRoleBO findRoleById(String roleCode) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		SmRoleBO roleBo = dao.queryByKey(SmRoleBO.class, roleCode);
		return roleBo;
	}
	
	/**
	 * 应用集成-权限更新
	 * 
	 * @param roleBo 权限bo对象
	 * @return
	 * @throws OptimusException
	 */
	public void updateSmRole(SmRoleBO roleBo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.update(roleBo);
	}
	
	/**
	 * 应用集成-权限查询
	 * 
	 * @param paramObject 系统查询字段对象集合
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findRole(Map<String,String> parmas) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append(" select b.SYSTEM_NAME,b.SYSTEM_CODE,a.ROLE_CODE,a.ROLE_NAME,a.ROLE_STATE,a.CREATER_TIME from  SM_ROLE a ,SM_SYS_INTEGRATION b  where a.EFFECTIVE_MARKER = ? and  a.PK_SYS_INTEGRATION = b.PK_SYS_INTEGRATION and a.ROLE_TYPE = ? ");
		List listParam = new ArrayList();
		listParam.add(AppConstants.EFFECTIVE_Y);
		listParam.add(AppConstants.ROLE_TYPE_SYS);
		if(parmas != null){
			String systemName = parmas.get("systemName").trim();
			if(!"".equals(systemName)){
				sql.append(" and  b.SYSTEM_NAME like ? ");
				listParam.add("%"+systemName+"%");
			}
			String systemCode = parmas.get("roleName").trim();
			if(!"".equals(systemCode)){
				sql.append(" and  a.ROLE_NAME like ? ");
				listParam.add("%"+systemCode+"%");
			}
		}
		sql.append(" order by a.MODIFIER_TIME DESC"); 
		List<Map> RoleList = dao.pageQueryForList(sql.toString(), listParam);
		return RoleList;
	}
	
	/**
	 * 应用集成-角色权限总个数
	 * 
	 * @return int
	 * @throws OptimusException
	 */
	public int getNumIntegration() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		String sql = "select count(1) from SM_ROLE"; 
		return dao.queryForInt(sql, null);
	}
	
	/**
	 * 应用集成-权限分配-岗位资源树
	 * 
	 * @param paramObject 系统查询字段对象集合
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findPostTree() throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append(" select b.ORGAN_NAME as NAME,b.ORGAN_ID as ID ,b.PARENT_ID as P_ID ,b.ORGAN_TYPE as TYPE from ");
		sql.append(" ( select * from (select o.ORGAN_NAME,o.ORGAN_CODE, o.ORGAN_TYPE,s.PARENT_ID,s.STRU_PATH,s.STRU_ORDER,o.ORGAN_ID,s.STRU_ID from HR_ORGAN o, HR_STRU s where o.ORGAN_CODE = s.ORGAN_ID ) A start with PARENT_ID= '1' ");
		sql.append(" connect by prior ORGAN_CODE = PARENT_ID and ORGAN_TYPE <> '8' ) b union select POST_NAME as NAME , ID , ORGAN_ID as PID , '8' as TYPE from HR_POST ");
		List<Map> RoleList = dao.queryForList(sql.toString(), null);
		return RoleList;
	}
	
	/**
	 * 应用集成-权限-岗位角色新增
	 * 
	 * @param postListBo 岗位角色关系bo对象集合
	 * @return
	 * @throws OptimusException
	 */
	public void savePosttionRole(List postListBo) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		dao.insert(postListBo);
	}
	
	/**
	 * 应用集成-权限-岗位角色删除
	 * 
	 * @param pkRole 岗位对应的角色
	 * @return
	 * @throws OptimusException
	 */
	public void deletePosttionRole(String pkRole) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		List listParam = new ArrayList();
		listParam.add(pkRole);
		sql.append(" delete from SM_POSITION_ROLE where ROLE_CODE = ? ");
		dao.execute(sql.toString(), listParam);
	}
	
	/**
	 * 应用集成-权限查询
	 * 
	 * @param paramObject 系统查询字段对象集合
	 * @return
	 * @throws OptimusException
	 */
	public List<Map> findPostitonRole(String pkRole) throws OptimusException {
		IPersistenceDAO dao = getPersistenceDAO();
		StringBuffer sql = new StringBuffer();
		sql.append(" select PK_POSITION from SM_POSITION_ROLE where ROLE_CODE = ? ");
		List listParam = new ArrayList();
		listParam.add(pkRole);
		List<Map> RoleList = dao.queryForList(sql.toString(), listParam);
		return RoleList;
	}

}
