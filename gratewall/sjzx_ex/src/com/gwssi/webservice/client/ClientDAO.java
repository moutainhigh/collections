package com.gwssi.webservice.client;

import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;

/**
 * 
 * 
 * 项目名称：bjgs_exchange 类名称：ClientDAO 类描述：提供客户端操作数据库的方法 创建人：lizheng 创建时间：Apr 10,
 * 2013 10:23:09 AM 修改人：lizheng 修改时间：Apr 10, 2013 10:23:09 AM 修改备注：
 * 
 * @version
 * 
 */
public interface ClientDAO
{

	/**
	 * 
	 * queryTargetNo 查询采集任务对象名称
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	String queryTargetNo(String sql) throws DBException;

	/**
	 * 
	 * queryTask 根据任务ID查询任务信息
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	Map queryTask(String sql) throws DBException;

	/**
	 * 
	 * querySrvTager 根据服务对象ID查询服务对象信息
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	Map querySrvTager(String sql) throws DBException;

	/**
	 * 
	 * queryDataSource 根据taskId查询数据源信息
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	Map queryDataSource(String sql) throws DBException;

	/**
	 * 
	 * queryMethodList 根据taskID查询此任务下有多少方法
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             List
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	List queryMethodList(String sql) throws DBException;

	/**
	 * 
	 * queryParam 根据webserviceTaskId查询参数
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             List
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	List queryParam(String sql) throws DBException;

	/**
	 * 
	 * queryParamValue 根据paramId查询参数值
	 * 
	 * @param sql
	 * @return List
	 * @throws DBException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	List queryParamValue(String sql) throws DBException;

	/**
	 * 
	 * queryTable 根据tableId查询表信息
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	Map queryTable(String sql) throws DBException;

	/**
	 * 
	 * queryDataitem 根据表ID查询该表里的字段
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             List
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	List queryDataitem(String sql) throws DBException;

	/**
	 * 
	 * insertCollectLog 插入日志
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             int
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	int insertCollectLog(String sql) throws DBException;

	String queryCollectType(String sql) throws DBException;
	
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
	Map queryWebTask(String sql) throws DBException;

}
