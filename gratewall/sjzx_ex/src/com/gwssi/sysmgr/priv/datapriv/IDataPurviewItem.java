package com.gwssi.sysmgr.priv.datapriv;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.gwssi.sysmgr.priv.datapriv.exception.DataObjNotFoundException;
import com.gwssi.sysmgr.priv.datapriv.exception.RoleNotFoundException;
import com.gwssi.sysmgr.priv.datapriv.exception.UserNotFoundException;


public interface IDataPurviewItem {
	/**
	 * 获取数据权限项
	 * @param parentCode 数据权限项的父项代码，为零表示最顶层项目
	 * @param dynamicParams XML配置文件中的参数
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
	 * 获取用户在某功能下某一数据类型的数据访问许可
	 * @param userId 用户id
	 * @param operId 功能id
	 * @param dataObj 数据对象类型
	 * @param dynamicParams 可扩展参数
	 * @return 返回数据访问许可
	 * @throws SQLException 
	 * @throws UserNotFoundException 
	 * @throws RoleNotFoundException 
	 * @throws DataObjNotFoundException 
	 */
	public IUserPrivilege getPrivilege(String userId,String operId,String dataObj,Map dynamicParams) throws UserNotFoundException, SQLException, RoleNotFoundException, DataObjNotFoundException;
	
	/**
	 * 获取用户在某功能下某一数据类型的数据访问许可
	 * @param userId 用户id
	 * @param operId 功能id
	 * @param dataObj 数据对象类型
	 * @return 返回数据访问许可
	 * @throws DataObjNotFoundException 
	 * @throws RoleNotFoundException 
	 * @throws SQLException 
	 * @throws UserNotFoundException 
	 */
	public IUserPrivilege getPrivilege(String userId,String operId,String dataObj) throws UserNotFoundException, SQLException, RoleNotFoundException, DataObjNotFoundException;

	/**
	 * 根据权限ID获取数据权限信息
	 * @param id
	 * @param dynamicParams
	 * @return
	 * @throws SQLException
	 */
	public Map getPrivilegeItemById(String id, Map dynamicParams) throws SQLException;
	/**
	 * 清空缓存
	 */
	public void init();
}
