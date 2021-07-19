/**
 * 
 */
package com.gwssi.sysmgr.priv.datapriv.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gwssi.sysmgr.priv.datapriv.exception.RoleNotFoundException;



/**
 * @author 周扬
 * 角色信息持久类
 */
public class RoleDAO {
	private static final Logger log = Logger.getLogger(DBase.class);
	private static RoleDAO inst = new RoleDAO(); 		// 唯一实例
	private Map rolesMap = new HashMap();
	
	protected RoleDAO(){
	}
	
	static public RoleDAO getInst(){
		return inst;
	}

	/**
	 * 清空缓存
	 **/
	public void init(){
		rolesMap.clear();
	}
	/**
	 * 获取角色可访问的功能列表
	 * @param roleId 角色ID
	 * @return 功能列表
	 * @throws SQLException
	 * @throws RoleNotFoundException
	 */
	public List getRoleFuncs(String roleId) throws SQLException, RoleNotFoundException{
		Map role = getRoleInfo(roleId);
		return (List) role.get("Funcs");
	}
	
	/**
	 * 获取角色可访问的数据权限列表
	 * @param roleId 角色ID
	 * @return 数据权限列表
	 * @throws SQLException
	 * @throws RoleNotFoundException
	 */
	public List getRoleDataAccGroups(String roleId) throws SQLException, RoleNotFoundException{
		Map role = getRoleInfo(roleId);
		return (List) role.get("DataAccGrp");		
	}

	/**
	 * 获取角色在某功能下可访问的数据权限列表
	 * @param roleId 角色ID
	 * @param operId 功能ID
	 * @return 数据权限列表
	 * @throws SQLException
	 * @throws RoleNotFoundException
	 */
	public List getRoleDataAccGroups(String roleId,String operId) throws SQLException, RoleNotFoundException{
		Map role = getRoleInfo(roleId);
		List dataAccGrps = (List) role.get("FuncDataAccGrp");
		List result = new ArrayList();
		for(int i = 0; i < dataAccGrps.size(); i++){
			Map dataAccGrp = (Map) dataAccGrps.get(i);
			if(dataAccGrp.get("TXNCODE").equals(operId)){
				result.add(dataAccGrp.get("DATAACCGRPID"));
			}
		}
		log.debug("RoleTxnGroupId:Txn:" + operId + "Grpid:" + result);
		return result;
	}
	
	/**
	 * 获取角色信息
	 * @param roleId
	 * @return 角色信息Map
	 * @throws SQLException
	 * @throws RoleNotFoundException
	 */
	public Map getRoleInfo(String roleId) throws SQLException, RoleNotFoundException{
		if(rolesMap.containsKey(roleId)){
			Map user = (Map) rolesMap.get(roleId);
			log.debug("已从缓存中载入角色信息！");
			return user;
		}
		
		// 从数据库获取角色信息
		String sql = "select ROLEID,ROLENAME,FUNCLIST from operrole_new " +
				"WHERE ROLEID = " + roleId ;
		List roles = DBase.getInst().query(sql);
		if(roles.size() > 0){
			Map role = (Map) roles.get(0);
			// 生成角色功能列表
			List funcs = new ArrayList();
			try{
				String funcidString = role.get("FUNCLIST").toString();
				String[] funids = funcidString.split(",");
				for(int i = 0; i < funids.length; i++){
					funcs.add(funids[i]);
				}
			} catch(NullPointerException e){
				log.debug("角色未分配功能");
			}
			role.put("Funcs", funcs);
			role.put("FuncDataAccGrp", getDataAccsFromRoleFunction(roleId));
			role.put("DataAccGrp", getDataAccsFromRole(roleId));
			rolesMap.put(roleId, role);
			log.debug("ID为[" + roleId + "]的角色信息已缓存");
			
			return role;
		}
		
		log.debug("ID为[" + roleId + "]的角色不存在");
		throw new RoleNotFoundException();
	}
	
	/**
	 * 获取角色可访问的数据权限组列表
	 * @param roleId 角色ID
	 * @return 数据权限组列表
	 * @throws SQLException
	 */
	private List getDataAccsFromRole(String roleId) throws SQLException{
		// 获得角色的数据权限组列表
		String sql = "select distinct DATAACCGRPID from DATAACCDISP where DATAACCDISPOBJ = '1' and " +
				"OBJECTID = " + roleId;
		List dataAccs = DBase.getInst().getFieldList(sql);
		return dataAccs;
	}
	
	/**
	 * 获取角色交易与数据权限组的关联集合
	 * @param roleId 角色
	 * @return 交易与数据权限组的关联集合
	 * @throws SQLException
	 */
	private List getDataAccsFromRoleFunction(String roleId) throws SQLException{
		// 获得角色的数据权限组列表
		String sql = "select A.TXNCODE,B.DATAACCGRPID from OperRoleFun A " +
				"inner join DATAACCDISP B ON A.ROLEACCID = B.OBJECTID AND " +
				"B.DATAACCDISPOBJ = '2' where A.ROLEID = " + roleId; 
		List dataAccs = DBase.getInst().query(sql);
		return dataAccs;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DBase.getInst().open();
			RoleDAO.getInst().getRoleFuncs("101");
			RoleDAO.getInst().getRoleFuncs("101");
			RoleDAO.getInst().getRoleFuncs("10021054");
			RoleDAO.getInst().getRoleFuncs("10021054");
			DBase.getInst().close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (RoleNotFoundException e) {
			e.printStackTrace();
		}
	}
}
