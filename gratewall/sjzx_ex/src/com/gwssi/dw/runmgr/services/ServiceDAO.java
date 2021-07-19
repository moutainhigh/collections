package com.gwssi.dw.runmgr.services;

import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;

public interface ServiceDAO
{
	Map queryUser(String sql) throws DBException;
	
	Map queryConfig(String sql) throws DBException;
	
	Map queryColumn(String sql) throws DBException;
	
	Map queryTable(String sql) throws DBException;

	Map queryService(String sql) throws DBException;

	List query(String sql) throws DBException;
	
	List queryConfigParam(String sql) throws DBException;
	
	java.math.BigDecimal count(String sql) throws DBException;
	
	int writeLog(String sql) throws DBException;
	
	//--------------DC2-jufeng-2012-07-08-------------------------///
	
	int writeLock(String sql) throws DBException; 
	
	Map queryLocked(String sql)throws DBException;
	
	/**
	 * 获取用户服务限定条件
	 * @param sql
	 * @return
	 * @throws DBException
	 */
	List querySysLimit(String sql)throws DBException;
	/**
	 * 获取当日用户服务访问次数
	 * @param sql
	 * @return
	 * @throws DBException
	 */
	java.math.BigDecimal visitedCount(String sql) throws DBException;
	/**
	 * 获取当日用户服务访问总条数
	 * @param sql
	 * @return
	 * @throws DBException
	 */
	java.math.BigDecimal visitedSum(String sql) throws DBException;
}
