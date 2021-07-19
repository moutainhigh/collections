package com.gwssi.dw.runmgr.services;

import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;

public class ServiceDAOImpl implements ServiceDAO
{
	DBOperation operation = null;
	
	public ServiceDAOImpl()
	{
		operation = DBOperationFactory.createOperation();
	}
	
	public Map queryUser(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	public List query(String sql) throws DBException
	{
		return operation.selectInOrder(sql);
	}

	public Map queryColumn(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	public Map queryConfig(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	public Map queryService(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	public Map queryTable(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	public List queryConfigParam(String sql) throws DBException
	{
		return operation.select(sql);
	}

	public java.math.BigDecimal count(String sql) throws DBException
	{
		Map result = operation.selectOne(sql);
		Object o = result.get("TOTALS");
		
		return o == null ? null : ((java.math.BigDecimal)o);
	}

	public int writeLog(String sql) throws DBException
	{
		return operation.execute(sql, false);
	}
	
	public int writeLock(String sql) throws DBException
	{
		return operation.execute(sql, false);
	}
	
	public Map queryLocked(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}
	
	/**
	 * 获取用户服务限定条件
	 * @param sql
	 * @return
	 * @throws DBException
	 */
	public List querySysLimit(String sql) throws DBException
	{
		return operation.selectInOrder(sql);
	}
	
	/**
	 * 获取当日用户服务访问次数
	 * @param sql
	 * @return
	 * @throws DBException
	 */
	public java.math.BigDecimal visitedCount(String sql) throws DBException{
		Map result = operation.selectOne(sql);
		Object o = result.get("TOTALS");
		return o == null ? null : ((java.math.BigDecimal)o);
	}
	/**
	 * 获取当日用户服务访问总条数
	 * @param sql
	 * @return
	 * @throws DBException
	 */
	public java.math.BigDecimal visitedSum(String sql) throws DBException{
		Map result = operation.selectOne(sql);
		Object o = result.get("TOTALS");
		return o == null ? null : ((java.math.BigDecimal)o);
	}
}
