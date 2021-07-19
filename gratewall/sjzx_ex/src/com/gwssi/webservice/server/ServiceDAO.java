package com.gwssi.webservice.server;

import java.util.List;
import java.util.Map;

import com.genersoft.frame.base.database.DBException;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�ServiceDAO ���������ṩ������������ݿⷽ���ӿ� �����ˣ�lizheng ����ʱ�䣺Mar 27,
 * 2013 4:16:22 PM �޸��ˣ�lizheng �޸�ʱ�䣺Mar 27, 2013 4:16:22 PM �޸ı�ע��
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
	 * queryService ���ݲ�����ѯ�����
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	Map queryService(String sql) throws DBException;

	Map queryServiceById(String sql) throws DBException;

	/**
	 * 
	 * query ��ѯ�����
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             List
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	List query(String sql) throws DBException;

	/**
	 * 
	 * count ��ѯ���������
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             java.math.BigDecimal
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	java.math.BigDecimal count(String sql) throws DBException;

	/**
	 * 
	 * queryLoginInfo��ѯ���������û�������
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	Map queryLoginInfo(String sql) throws DBException;

	/**
	 * 
	 * queryServiceState ��ѯ�����״̬
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	Map queryServiceState(String sql) throws DBException;

	/**
	 * 
	 * queryServiceIPInfo ��ѯ�����Ƿ��IP
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	Map queryServiceIPInfo(String sql) throws DBException;

	/**
	 * 
	 * queryServiceDate ��ѯ��������ʱ��
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	String queryServiceDate(String sql) throws DBException;

	/**
	 * 
	 * queryServiceRule ��ѯ�������
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	Map queryServiceRule(String sql) throws DBException;
	
	String queryDateRule(String sql) throws DBException;

	/**
	 * 
	 * queryServiceLog ��ѯ������־
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	Map queryServiceLog(String sql) throws DBException;

	/**
	 * 
	 * insertShareLog ������־
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             int
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	int insertShareLog(String sql) throws DBException;

	/**
	 * 
	 * queryFtpService ��������ԴID��ȡ����Դ����ϸ��Ϣ
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	Map queryFTPDatasource(String sql) throws DBException;
	
	/**
	 * 
	 * queryParam ��ѯ����
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
	 * queryFtpService ����service id��ѯFTP����
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	Map queryFtpService(String sql) throws DBException;

	/**
	 * 
	 * queryFtpParam ��ѯFTP����
	 * 
	 * @param sql
	 * @return
	 * @throws DBException
	 *             List
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	List queryFtpParam(String sql) throws DBException;
	
	String queryTableById(String sql) throws DBException;
	
	List queryTableName(String sql) throws DBException;
	
	/**
	 * ��ѯ�����Ƿ���
	 */
	List queryServiceLoked(String sql) throws DBException;

}
