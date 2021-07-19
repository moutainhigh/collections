package com.gwssi.webservice.server;

import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;

/**
 * 
 * 
 * 项目名称：bjgs_exchange 类名称：ServiceDAO 类描述：提供服务操作的数据库方法接口 创建人：lizheng 创建时间：Mar 27,
 * 2013 4:16:22 PM 修改人：lizheng 修改时间：Mar 27, 2013 4:16:22 PM 修改备注：
 * 
 * @version
 * 
 */
public interface ServiceDAO
{
	Map queryOldSerCode(String sql) throws DBException;

	Map queryTrsSerCode(String sql) throws DBException;

	/**
	 * 
	 * queryService 根据参数查询服务表
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	Map queryService(String sql) throws DBException;

	Map queryServiceById(String sql) throws DBException;

	/**
	 * 
	 * query 查询结果集
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             List
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	List query(String sql) throws DBException;

	/**
	 * 
	 * count 查询结果总条数
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             java.math.BigDecimal
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	java.math.BigDecimal count(String sql) throws DBException;

	/**
	 * 
	 * queryLoginInfo查询服务对象的用户名密码
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	Map queryLoginInfo(String sql) throws DBException;

	/**
	 * 
	 * queryServiceState 查询服务的状态
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	Map queryServiceState(String sql) throws DBException;

	/**
	 * 
	 * queryServiceIPInfo 查询服务是否绑定IP
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	Map queryServiceIPInfo(String sql) throws DBException;

	/**
	 * 
	 * queryServiceDate 查询服务例外时间
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	String queryServiceDate(String sql) throws DBException;

	/**
	 * 
	 * queryServiceRule 查询服务规则
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	Map queryServiceRule(String sql) throws DBException;
	
	String queryDateRule(String sql) throws DBException;

	/**
	 * 
	 * queryServiceLog 查询服务日志
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	Map queryServiceLog(String sql) throws DBException;

	/**
	 * 
	 * insertShareLog 插入日志
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             int
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	int insertShareLog(String sql) throws DBException;

	/**
	 * 
	 * queryFtpService 根据数据源ID获取数据源的详细信息
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	Map queryFTPDatasource(String sql) throws DBException;
	
	/**
	 * 
	 * queryParam 查询参数
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
	 * queryFtpService 根据service id查询FTP服务
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	Map queryFtpService(String sql) throws DBException;

	/**
	 * 
	 * queryFtpParam 查询FTP参数
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             List
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	List queryFtpParam(String sql) throws DBException;
	
	String queryTableById(String sql) throws DBException;
	
	List queryTableName(String sql) throws DBException;
	
	/**
	 * 查询服务是否被锁
	 */
	List queryServiceLoked(String sql) throws DBException;

}
