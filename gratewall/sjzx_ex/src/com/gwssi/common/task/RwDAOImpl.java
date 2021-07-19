package com.gwssi.common.task;

import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;

public class RwDAOImpl implements RwDAO
{

	DBOperation	operation	= null;

	public RwDAOImpl()
	{
		operation = DBOperationFactory.createOperation();
	}

	
	@Override
	public Map queryDataSource(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}


	@Override
	public List queryMethodList(String sql) throws DBException
	{
		return operation.selectInOrder(sql);
	}

}
