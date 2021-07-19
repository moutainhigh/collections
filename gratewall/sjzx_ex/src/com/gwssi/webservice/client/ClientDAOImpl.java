package com.gwssi.webservice.client;

import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�ClientDAOImpl ���������ṩ�ͻ��˲������ݿ�ķ���ʵ���� �����ˣ�lizheng
 * ����ʱ�䣺Apr 10, 2013 10:25:29 AM �޸��ˣ�lizheng �޸�ʱ�䣺Apr 10, 2013 10:25:29 AM �޸ı�ע��
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
	 * ��ѯ�ɼ������������
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
	 * ���ݷ������ID��ѯ���������Ϣ
	 */
	public Map querySrvTager(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	/**
	 * ��������ID��ѯ������Ϣ
	 */
	public Map queryTask(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	/**
	 * ����taskId��ѯ����Դ��Ϣ
	 */
	public Map queryDataSource(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	/**
	 * ����taskID��ѯ���������ж��ٷ���
	 */
	public List queryMethodList(String sql) throws DBException
	{
		return operation.selectInOrder(sql);
	}

	/**
	 * ����webserviceTaskId��ѯ����
	 */
	public List queryParam(String sql) throws DBException
	{
		return operation.selectInOrder(sql);
	}

	/**
	 * ����paramId��ѯ����ֵ
	 */
	public List queryParamValue(String sql) throws DBException
	{
		return operation.selectInOrder(sql);
	}

	/**
	 * ����tableId��ѯ����Ϣ
	 */
	public Map queryTable(String sql) throws DBException
	{
		return operation.selectOne(sql);
	}

	/**
	 * ���ݱ�ID��ѯ�ñ�����ֶ�
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
	 * queryWebTask ����webserviceTaskId��ѯ����
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             List
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public Map queryWebTask(String sql) throws DBException{
		return operation.selectOne(sql);
	}
	

	/**
	 * ������־
	 */
	public int insertCollectLog(String sql) throws DBException
	{
		return operation.execute(sql, false);
	}

}
