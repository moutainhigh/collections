package com.gwssi.webservice.client;

import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�ClientDAO ���������ṩ�ͻ��˲������ݿ�ķ��� �����ˣ�lizheng ����ʱ�䣺Apr 10,
 * 2013 10:23:09 AM �޸��ˣ�lizheng �޸�ʱ�䣺Apr 10, 2013 10:23:09 AM �޸ı�ע��
 * 
 * @version
 * 
 */
public interface ClientDAO
{

	/**
	 * 
	 * queryTargetNo ��ѯ�ɼ������������
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	String queryTargetNo(String sql) throws DBException;

	/**
	 * 
	 * queryTask ��������ID��ѯ������Ϣ
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	Map queryTask(String sql) throws DBException;

	/**
	 * 
	 * querySrvTager ���ݷ������ID��ѯ���������Ϣ
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	Map querySrvTager(String sql) throws DBException;

	/**
	 * 
	 * queryDataSource ����taskId��ѯ����Դ��Ϣ
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	Map queryDataSource(String sql) throws DBException;

	/**
	 * 
	 * queryMethodList ����taskID��ѯ���������ж��ٷ���
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             List
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	List queryMethodList(String sql) throws DBException;

	/**
	 * 
	 * queryParam ����webserviceTaskId��ѯ����
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             List
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	List queryParam(String sql) throws DBException;

	/**
	 * 
	 * queryParamValue ����paramId��ѯ����ֵ
	 * 
	 * @param sql
	 * @return List
	 * @throws DBException
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	List queryParamValue(String sql) throws DBException;

	/**
	 * 
	 * queryTable ����tableId��ѯ����Ϣ
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	Map queryTable(String sql) throws DBException;

	/**
	 * 
	 * queryDataitem ���ݱ�ID��ѯ�ñ�����ֶ�
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             List
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	List queryDataitem(String sql) throws DBException;

	/**
	 * 
	 * insertCollectLog ������־
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             int
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	int insertCollectLog(String sql) throws DBException;

	String queryCollectType(String sql) throws DBException;
	
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
	Map queryWebTask(String sql) throws DBException;

}
