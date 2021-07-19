/**
 * 
 */
package com.gwssi.sysmgr.priv.datapriv;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.gwssi.sysmgr.priv.datapriv.dao.DBase;
import com.gwssi.sysmgr.priv.datapriv.dao.DataAccGroupDAO;
import com.gwssi.sysmgr.priv.datapriv.dao.DataObjDAO;
import com.gwssi.sysmgr.priv.datapriv.dao.RoleDAO;
import com.gwssi.sysmgr.priv.datapriv.dao.UserDAO;
import com.gwssi.sysmgr.priv.datapriv.exception.DataObjNotFoundException;
import com.gwssi.sysmgr.priv.datapriv.exception.RoleNotFoundException;
import com.gwssi.sysmgr.priv.datapriv.exception.UserNotFoundException;

/**
 * @author 周扬 基本数据权限实现类
 */
public class BaseDataPurviewItem implements IDataPurviewItem {
	private static final Logger log = Logger
			.getLogger(BaseDataPurviewItem.class);

	public List getPurviewItem(String parentCode, Map dynamicParams)
			throws SQLException {
		return DataObjDAO.getInst().getDataObjItem(
				dynamicParams.get("table").toString(),
				dynamicParams.get("nameField").toString(),
				dynamicParams.get("idField").toString(),
				dynamicParams.get("codeField").toString(),
				dynamicParams.get("parentCodeField").toString(),
				parentCode.equals("0") ? dynamicParams.get("rootCode")
						.toString() : parentCode,
				dynamicParams.get("sortField").toString());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DBase.getInst().open();
			BaseDataPurviewItem item = new BaseDataPurviewItem();
			item.getPrivilege("9ae1e079962f479d9d476bc48a491144", "111", "1");
			DBase.getInst().close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		} catch (RoleNotFoundException e) {
			e.printStackTrace();
		} catch (DataObjNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取用户在某功能下某一数据类型的数据访问许可
	 * 
	 * @param userId
	 *            用户id
	 * @param operId
	 *            功能id
	 * @param dataObj
	 *            数据对象类型
	 * @param dynamicParams
	 *            可扩展参数
	 * @return 返回数据访问许可
	 * @throws SQLException
	 * @throws UserNotFoundException
	 * @throws RoleNotFoundException
	 * @throws DataObjNotFoundException
	 */
	public IUserPrivilege getPrivilege(String userId, String operId,
			String dataObj, Map dynamicParams) throws UserNotFoundException,
			SQLException, RoleNotFoundException, DataObjNotFoundException {
		List roles = UserDAO.getInst().getUserRoles(userId);
		Set dataAccGroupIdSet = getDataAccGroupId(roles, operId);
		if (dataAccGroupIdSet.size() == 0)
			dataAccGroupIdSet = getDataAccGroupId(roles);
		return getPrivilege(dataAccGroupIdSet, dataObj);
	}

	/**
	 * 获取用户在某功能下某一数据类型的数据访问许可
	 * 
	 * @param userId
	 *            用户id
	 * @param operId
	 *            功能id
	 * @param dataObj
	 *            数据对象类型
	 * @return 返回数据访问许可
	 * @throws DataObjNotFoundException
	 * @throws RoleNotFoundException
	 * @throws SQLException
	 * @throws UserNotFoundException
	 */
	public IUserPrivilege getPrivilege(String userId, String operId,
			String dataObj) throws UserNotFoundException, SQLException,
			RoleNotFoundException, DataObjNotFoundException {
		return getPrivilege(userId, operId, dataObj, new HashMap());
	}

	/**
	 * 获取多个数据权限组在某数据全县类型下的数据访问许可
	 * 
	 * @param dataAccGroupIds
	 *            数据权限组ID集合
	 * @param operId
	 *            功能id
	 * @param dataObj
	 *            数据对象类型
	 * @return 返回数据访问许可
	 * @throws SQLException
	 * @throws DataObjNotFoundException
	 */
	private IUserPrivilege getPrivilege(Collection dataAccGroupIds,
			String dataObj) throws SQLException, DataObjNotFoundException {
		DataPrivilege privilege = new DataPrivilege();
		// 找出数据权限组集中not in的数据权限，考虑到权限组集中的权限在相同权限类型下有交集的情况，
		// 将not in与in规则中同时存在的数据集中放在not in规则中，但是要将相同部分过滤掉
		String objectId = DataObjDAO.getInst().getDataObjId(dataObj);

		int count = DataAccGroupDAO.getInst().getGroupsItemCount(
				dataAccGroupIds, objectId, "2");
		if (count > 0) {
			List privilegeList = DataAccGroupDAO.getInst().getGroupsItem(
					dataAccGroupIds, objectId, "2");
			privilege.setPrivilegeList(privilegeList);
			privilege.setRule("not in");
		} else {
			List privilegeList = DataAccGroupDAO.getInst().getGroupsItem(
					dataAccGroupIds, objectId, "1");
			privilege.setPrivilegeList(privilegeList);
			privilege.setRule("in");
		}
		log.debug(privilege);
		return privilege;
	}

	/**
	 * 根据多个角色获取可访问的数据权限组ID集合
	 * 
	 * @param roles
	 *            角色ID集合
	 * @return 返回数据权限组ID集合
	 * @throws SQLException
	 * @throws RoleNotFoundException
	 */
	private Set getDataAccGroupId(List roles) throws SQLException,
			RoleNotFoundException {
		Set dataAccGroupIdSet = new HashSet();

		// 找出用户所可访问的数据权限组ID，并去除相同项
		for (int i = 0; i < roles.size(); i++) {
			List dataAccGroupList = RoleDAO.getInst().getRoleDataAccGroups(
					roles.get(i).toString());
			for (int j = 0; j < dataAccGroupList.size(); j++) {
				dataAccGroupIdSet.add(dataAccGroupList.get(j));
			}
		}
		log.debug("RoleGroupId" + dataAccGroupIdSet);
		return dataAccGroupIdSet;
	}

	/**
	 * 获取多个角色在某交易下可访问的数据权限组ID集合
	 * 
	 * @param roles
	 *            角色ID集合
	 * @param operId
	 *            交易ID
	 * @return 返回数据权限组ID集合
	 * @throws SQLException
	 * @throws RoleNotFoundException
	 */
	private Set getDataAccGroupId(List roles, String operId)
			throws SQLException, RoleNotFoundException {
		Set dataAccGroupIdSet = new HashSet();

		// 找出用户所可访问的数据权限组ID，并去除相同项
		for (int i = 0; i < roles.size(); i++) {
			List dataAccGroupList = RoleDAO.getInst().getRoleDataAccGroups(
					roles.get(i).toString(), operId);
			for (int j = 0; j < dataAccGroupList.size(); j++) {
				dataAccGroupIdSet.add(dataAccGroupList.get(j));
			}
		}
		log.debug("TxnGroupId" + dataAccGroupIdSet);
		return dataAccGroupIdSet;
	}

	/**
	 * 清空缓存
	 */
	public void init() {
		UserDAO.getInst().init();
		RoleDAO.getInst().init();
		DataObjDAO.getInst().init();
		DataAccGroupDAO.getInst().init();
	}

	public Map getPrivilegeItemById(String id, Map dynamicParams)
			throws SQLException {
		return (Map) DataObjDAO.getInst().getDataObjItemById(
				dynamicParams.get("table").toString(),
				dynamicParams.get("nameField").toString(),
				dynamicParams.get("idField").toString(),
				dynamicParams.get("codeField").toString(),
				dynamicParams.get("parentCodeField").toString(), id).get(0);
	}
}
