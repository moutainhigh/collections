package com.gwssi.sysmgr.priv.datapriv;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.gwssi.sysmgr.priv.datapriv.exception.DataObjNotFoundException;
import com.gwssi.sysmgr.priv.datapriv.exception.RoleNotFoundException;
import com.gwssi.sysmgr.priv.datapriv.exception.UserNotFoundException;


public interface IDataPurviewItem {
	/**
	 * ��ȡ����Ȩ����
	 * @param parentCode ����Ȩ����ĸ�����룬Ϊ���ʾ�����Ŀ
	 * @param dynamicParams XML�����ļ��еĲ���
	 *  <Param name="table" value="***" />
	 *	<Param name="nameField" value="***" />
	 *	<Param name="idField" value="***" />
	 *	<Param name="codeField" value="***" />
	 *	<Param name="parentCodeField" value="***" />
	 *	<Param name="sortField" value="***" />
	 * @return
	 * @throws SQLException 
	 */
	public List getPurviewItem(String parentCode, Map dynamicParams) throws SQLException;
	
	/**
	 * ��ȡ�û���ĳ������ĳһ�������͵����ݷ������
	 * @param userId �û�id
	 * @param operId ����id
	 * @param dataObj ���ݶ�������
	 * @param dynamicParams ����չ����
	 * @return �������ݷ������
	 * @throws SQLException 
	 * @throws UserNotFoundException 
	 * @throws RoleNotFoundException 
	 * @throws DataObjNotFoundException 
	 */
	public IUserPrivilege getPrivilege(String userId,String operId,String dataObj,Map dynamicParams) throws UserNotFoundException, SQLException, RoleNotFoundException, DataObjNotFoundException;
	
	/**
	 * ��ȡ�û���ĳ������ĳһ�������͵����ݷ������
	 * @param userId �û�id
	 * @param operId ����id
	 * @param dataObj ���ݶ�������
	 * @return �������ݷ������
	 * @throws DataObjNotFoundException 
	 * @throws RoleNotFoundException 
	 * @throws SQLException 
	 * @throws UserNotFoundException 
	 */
	public IUserPrivilege getPrivilege(String userId,String operId,String dataObj) throws UserNotFoundException, SQLException, RoleNotFoundException, DataObjNotFoundException;

	/**
	 * ����Ȩ��ID��ȡ����Ȩ����Ϣ
	 * @param id
	 * @param dynamicParams
	 * @return
	 * @throws SQLException
	 */
	public Map getPrivilegeItemById(String id, Map dynamicParams) throws SQLException;
	/**
	 * ��ջ���
	 */
	public void init();
}
