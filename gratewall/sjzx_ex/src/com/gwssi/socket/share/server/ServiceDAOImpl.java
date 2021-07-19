package com.gwssi.socket.share.server;

import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�ServiceDAOImpl ���������ṩ������������ݿⷽ���ӿڵ�ʵ���� �����ˣ�lizheng
 * ����ʱ�䣺Mar 28, 2013 10:12:06 AM �޸��ˣ�lizheng �޸�ʱ�䣺Mar 28, 2013 10:12:06 AM �޸ı�ע��
 * 
 * @version
 * 
 */
public class ServiceDAOImpl implements ServiceDAO
{
	DBOperation	operation	= null;

	public ServiceDAOImpl()
	{
		operation = DBOperationFactory.createOperation();
	}


	public Map queryOldSerCode(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	/**
	 * ���ݲ�����ѯ�����
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
	 * ��ѯ�����
	 */
	public List query(String sql) throws DBException
	{
		return operation.selectInOrder(sql);
	}

	/**
	 * ��ѯ���������
	 */
	public java.math.BigDecimal count(String sql) throws DBException
	{
		Map result = operation.selectOne(sql);
		Object o = result.get("TOTALS");
		return o == null ? null : ((java.math.BigDecimal) o);
	}

	/**
	 * ��ѯ���������û�������
	 */
	public Map queryLoginInfo(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	/**
	 * ��ѯ�����״̬
	 */
	public Map queryServiceState(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	/**
	 * ��ѯ��������ip��Ϣ
	 */
	public Map queryServiceIPInfo(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	/**
	 * ��ѯ��������ʱ��
	 */
	public String queryServiceDate(String sql) throws DBException
	{
		Map result = operation.selectOne(sql);
		String o = result.get("TOTALS").toString();

		return o == null ? null : o;
	}

	/**
	 * ��ѯ�������
	 */
	public Map queryServiceRule(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	/**
	 * ��ѯ������־
	 */
	public Map queryServiceLog(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	/**
	 * ������־
	 */
	public int insertShareLog(String sql) throws DBException
	{
		return operation.execute(sql, false);
	}

	public List queryParam(String sql) throws DBException
	{
		return operation.selectInOrder(sql);
	}

}
