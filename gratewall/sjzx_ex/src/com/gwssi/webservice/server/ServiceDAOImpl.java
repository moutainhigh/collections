package com.gwssi.webservice.server;

import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBConnectUtil;

/**
 * 
 * 
 * 项目名称：bjgs_exchange 类名称：ServiceDAOImpl 类描述：提供服务操作的数据库方法接口的实现类 创建人：lizheng
 * 创建时间：Mar 28, 2013 10:12:06 AM 修改人：lizheng 修改时间：Mar 28, 2013 10:12:06 AM 修改备注：
 * 
 * @version
 * 
 */
public class ServiceDAOImpl implements ServiceDAO
{
	//DBOperation	operation	= null;
	DBConnectUtil operation=null;

	public ServiceDAOImpl()
	{
		//operation = DBOperationFactory.createOperation();
		operation = new DBConnectUtil("gwssi");
	}

	public Map queryOldSerCode(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	public Map queryTrsSerCode(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	/**
	 * 根据参数查询服务表
	 */
	public Map queryService(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	public Map queryServiceById(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	/**
	 * 查询结果集
	 */
	public List query(String sql) throws DBException
	{
		return operation.selectInOrder(sql);
	}

	/**
	 * 查询结果总条数
	 */
	public java.math.BigDecimal count(String sql) throws DBException
	{
		Map result = operation.selectOne(sql);
		Object o = result.get("TOTALS");
		return o == null ? null : ((java.math.BigDecimal) o);
	}

	/**
	 * 查询服务对象的用户名密码
	 */
	public Map queryLoginInfo(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	/**
	 * 查询服务的状态
	 */
	public Map queryServiceState(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	/**
	 * 查询服务对象的ip信息
	 */
	public Map queryServiceIPInfo(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	/**
	 * 查询服务例外时间
	 */
	public String queryServiceDate(String sql) throws DBException
	{
		Map result = operation.selectOne(sql);
		String o = result.get("TOTALS").toString();

		return o == null ? null : o;
	}

	/**
	 * 查询服务规则
	 */
	public Map queryServiceRule(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	public String queryDateRule(String sql) throws DBException
	{
		Map result = operation.selectOne(sql);
		String o = result.get("TOTALS").toString();
		return o == null ? null : o;
	}

	/**
	 * 查询服务日志
	 */
	public Map queryServiceLog(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	/**
	 * 插入日志
	 */
	public int insertShareLog(String sql) throws DBException
	{
		//return operation.execute(sql, false);
		return operation.execute(sql);
	}

	/**
	 * 查询参数
	 */
	public List queryParam(String sql) throws DBException
	{
		return operation.selectInOrder(sql);
	}

	/**
	 * 根据service id查询FTP服务
	 */
	public Map queryFtpService(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	/**
	 * 根据数据源ID获取数据源的详细信息
	 */
	public Map queryFTPDatasource(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	/**
	 * queryFtpParam 查询FTP参数
	 */
	public List queryFtpParam(String sql) throws DBException
	{
		return operation.selectInOrder(sql);
	}

	public String queryTableById(String sql) throws DBException
	{
		Map result = operation.selectOne(sql);
		String o = result.get("TABLE_ID").toString();
		return o == null ? null : o;
	}

	public List queryTableName(String sql) throws DBException
	{
		return operation.selectInOrder(sql);
	}

	/**
	 * 查询服务是否被锁
	 */
	public List queryServiceLoked(String sql) throws DBException
	{
		return operation.selectInOrder(sql);
	}

}
