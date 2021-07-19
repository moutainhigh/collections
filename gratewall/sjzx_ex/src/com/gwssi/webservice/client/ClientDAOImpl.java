package com.gwssi.webservice.client;

import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;

/**
 * 
 * 
 * 项目名称：bjgs_exchange 类名称：ClientDAOImpl 类描述：提供客户端操作数据库的方法实现类 创建人：lizheng
 * 创建时间：Apr 10, 2013 10:25:29 AM 修改人：lizheng 修改时间：Apr 10, 2013 10:25:29 AM 修改备注：
 * 
 * @version
 * 
 */
public class ClientDAOImpl implements ClientDAO
{

	DBOperation	operation	= null;

	public ClientDAOImpl()
	{
		operation = DBOperationFactory.createOperation();
	}

	/**
	 * 查询采集任务对象名称
	 */
	public String queryTargetNo(String sql) throws DBException
	{
		Map result = operation.selectOne(sql);
		String o = result.get("TARGETNO").toString();
		if (null != o)
			return o;
		else
			return "";
	}

	/**
	 * 根据服务对象ID查询服务对象信息
	 */
	public Map querySrvTager(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	/**
	 * 根据任务ID查询任务信息
	 */
	public Map queryTask(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	/**
	 * 根据taskId查询数据源信息
	 */
	public Map queryDataSource(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	/**
	 * 根据taskID查询此任务下有多少方法
	 */
	public List queryMethodList(String sql) throws DBException
	{
		return operation.selectInOrder(sql);
	}

	/**
	 * 根据webserviceTaskId查询参数
	 */
	public List queryParam(String sql) throws DBException
	{
		return operation.selectInOrder(sql);
	}

	/**
	 * 根据paramId查询参数值
	 */
	public List queryParamValue(String sql) throws DBException
	{
		return operation.selectInOrder(sql);
	}

	/**
	 * 根据tableId查询表信息
	 */
	public Map queryTable(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	/**
	 * 根据表ID查询该表里的字段
	 */
	public List queryDataitem(String sql) throws DBException
	{
		return operation.selectInOrder(sql);
	}

	public String queryCollectType(String sql) throws DBException
	{
		Map result = operation.selectOne(sql);
		String o = result.get("COLLECT_TYPE").toString();
		if (null != o)
			return o;
		else
			return "";
	}
	/**
	 * 
	 * queryWebTask 根据webserviceTaskId查询方法
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             List
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public Map queryWebTask(String sql) throws DBException{
		return operation.selectOne(sql);
	}
	

	/**
	 * 插入日志
	 */
	public int insertCollectLog(String sql) throws DBException
	{
		return operation.execute(sql, false);
	}

}
