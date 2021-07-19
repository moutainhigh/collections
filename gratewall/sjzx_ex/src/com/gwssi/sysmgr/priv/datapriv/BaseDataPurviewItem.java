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
 * @author ���� ��������Ȩ��ʵ����
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
	 * ��ȡ�û���ĳ������ĳһ�������͵����ݷ������
	 * 
	 * @param userId
	 *            �û�id
	 * @param operId
	 *            ����id
	 * @param dataObj
	 *            ���ݶ�������
	 * @param dynamicParams
	 *            ����չ����
	 * @return �������ݷ������
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
	 * ��ȡ�û���ĳ������ĳһ�������͵����ݷ������
	 * 
	 * @param userId
	 *            �û�id
	 * @param operId
	 *            ����id
	 * @param dataObj
	 *            ���ݶ�������
	 * @return �������ݷ������
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
	 * ��ȡ�������Ȩ������ĳ����ȫ�������µ����ݷ������
	 * 
	 * @param dataAccGroupIds
	 *            ����Ȩ����ID����
	 * @param operId
	 *            ����id
	 * @param dataObj
	 *            ���ݶ�������
	 * @return �������ݷ������
	 * @throws SQLException
	 * @throws DataObjNotFoundException
	 */
	private IUserPrivilege getPrivilege(Collection dataAccGroupIds,
			String dataObj) throws SQLException, DataObjNotFoundException {
		DataPrivilege privilege = new DataPrivilege();
		// �ҳ�����Ȩ���鼯��not in������Ȩ�ޣ����ǵ�Ȩ���鼯�е�Ȩ������ͬȨ���������н����������
		// ��not in��in������ͬʱ���ڵ����ݼ��з���not in�����У�����Ҫ����ͬ���ֹ��˵�
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
	 * ���ݶ����ɫ��ȡ�ɷ��ʵ�����Ȩ����ID����
	 * 
	 * @param roles
	 *            ��ɫID����
	 * @return ��������Ȩ����ID����
	 * @throws SQLException
	 * @throws RoleNotFoundException
	 */
	private Set getDataAccGroupId(List roles) throws SQLException,
			RoleNotFoundException {
		Set dataAccGroupIdSet = new HashSet();

		// �ҳ��û����ɷ��ʵ�����Ȩ����ID����ȥ����ͬ��
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
	 * ��ȡ�����ɫ��ĳ�����¿ɷ��ʵ�����Ȩ����ID����
	 * 
	 * @param roles
	 *            ��ɫID����
	 * @param operId
	 *            ����ID
	 * @return ��������Ȩ����ID����
	 * @throws SQLException
	 * @throws RoleNotFoundException
	 */
	private Set getDataAccGroupId(List roles, String operId)
			throws SQLException, RoleNotFoundException {
		Set dataAccGroupIdSet = new HashSet();

		// �ҳ��û����ɷ��ʵ�����Ȩ����ID����ȥ����ͬ��
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
	 * ��ջ���
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
