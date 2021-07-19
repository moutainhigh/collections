package com.gwssi.dw.runmgr.services;

import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;
import com.gwssi.dw.runmgr.services.common.UserBean;

public class CommonServiceDAOImpl
{
	DBOperation operation = null;
	
	public CommonServiceDAOImpl()
	{
		operation = DBOperationFactory.createOperation();
	}

	public UserBean queryUser(String sql) throws DBException
	{
		Map result = operation.selectOne(sql);
		UserBean user = null;
		if(result.size() > 0 ){
			user = new UserBean();
			user.setId(""+result.get("SYS_SVR_USER_ID"));
			user.setCreateBy(""+result.get("CREATE_BY"));
			user.setCreateDate(""+result.get("CREATE_DATE"));
			user.setDesc(""+result.get("USER_DESC"));
			user.setName(""+result.get("LOGIN_NAME"));
			user.setPwd(""+result.get("PASSWORD"));
			user.setState(""+result.get("STATE"));
			user.setType(""+result.get("USER_TYPE"));
		}
		
		return user;
	}
	
	public Map queryTable(String sql) throws DBException
	{
		Map result = operation.selectOne(sql);
		return result;
	}


	public Map queryService(String sql) throws DBException
	{
		Map result = operation.selectOne(sql);
		return result;
	}

	public Map queryColumn(String sql) throws DBException
	{
		Map result = operation.selectOne(sql);
		return result;
	}

	public Map queryConfig(String sql) throws DBException
	{
		Map result = operation.selectOne(sql);
		return result;
	}

	public List query(String sql) throws DBException
	{
		List result = operation.select(sql);
		return result;
	}
	
	
	
}
