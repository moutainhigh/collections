package com.gwssi.common.constant;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�ExConstant �������������������ݽ���ƽ̨ϵͳ������ �����ˣ�lizheng ����ʱ�䣺Mar
 * 15, 2013 2:02:14 PM �޸��ˣ�lizheng �޸�ʱ�䣺Mar 15, 2013 2:02:14 PM �޸ı�ע��
 * 
 * @version
 * 
 */

public class ExConstant
{

	public static String		SERVER_SYSTEM_TYPE;

	/** �ļ�·�� */
	public static String		FILE_PATH_ROOT;							// �ļ���Ŀ¼

	public static String		SHARE_RECORD;								// �������ļ����·��

	public static String		COLLECT_RECORD;							// �ɼ������ļ����·��

	public static String		REPORT;									// ������·��

	public static String		SHARE_XML;									// ����xml�ļ����·��

	public static String		SHARE_CONFIG;								// ������������ļ����·��

	public static String		COLLECT_XML;								// �ɼ�xml�ļ����·��

	public static String		RES_TBL_RECORD;							// ����ɼ���excel�ļ����·��

	public static String		FILE_UPLOAD;								// �ɼ������ļ��ϴ��ļ����·��

	public static String		FILE_FTP;									// �ɼ�����FTP�ļ����·��

	public static String		FILE_DATABASE;								// �ɼ��������ݿ���־�ļ����·��

	public static String		TRS_TEMPLATE;								// ȫ�ļ���ģ���ļ����·��

	public static String		SERVICE_TARGET;								// �������˵���ļ����·��
	
	public static final String	LOG_TYPE_TEST				= "01";		// ��־type���Է���

	public static final String	LOG_TYPE_USER				= "02";		// ��־type�û�����

	/** ��Ч��� */
	public static final String	IS_MARKUP_Y					= "Y";			// ��Ч

	public static final String	IS_MARKUP_N					= "N";			// ��Ч

	public static final String	IS_BIND_IP_Y				= "Y";			// ��������IP��ַ

	public static final String	IS_BIND_IP_N				= "N";			// ������󲻰�IP��ַ

	public static final String	SERVICE_STATE_Y				= "Y";			// ����Ϊ����״̬

	public static final String	SERVICE_STATE_N				= "N";			// ����Ϊͣ��״̬

	public static final String	SERVICE_STATE_G				= "G";			// ����Ϊ�鵵״̬

	public static final String	BIND_IP_RIGHT				= "3000";		// ��ip�͵�ǰ��½��ַһ��

	public static final String	BIND_IP_WRONG				= "3001";		// ��ip�͵�ǰ��½��ַ��һ��

	public static final String	SERVICE_NOT_FOUND			= "0000";		// ����δ�ҵ�

	public static final String	SERVICE_START				= "1000";		// ������������

	public static final String	SERVICE_END					= "1001";		// ����ֹͣ

	public static final String	SERVICE_ARCHIVE				= "1002";		// ����鵵

	public static final String	SERVICE_RULE_NOTFOUND		= "1003";		// ����δ���÷��ʹ���

	public static final String	LESS_AMOUNT_DAY				= "1004";		// ��������ܷ�����С�ڹ���ֵ

	public static final String	OVER_AMOUNT_DAY				= "1005";		// ����ǰ���ܷ�������������ֵ

	public static final String	LESS_COUNT_DAY				= "1006";		// ��������ܴ���С�ڹ���ֵ

	public static final String	OVER_COUNT_DAY				= "1007";		// ����ǰ���ܴ�����������ֵ

	public static final String	SERVICE_NOT_TIME			= "1008";		// ���������õķ���ʱ�䷶Χ��

	public static final String	SERVICE_IN_TIME				= "1009";		// ���������õķ���ʱ�䷶Χ

	public static final String	SERVICE_CHECK_SUCCESS		= "1100";		// ������ͨ��

	public static final String	SERVICE_CHECK_FAILED		= "1101";		// ������ͨ��

	public static final String	IS_EXCEPTION_Y				= "2000";		// ����������

	public static final String	IS_EXCEPTION_N				= "2001";		// ������������

	public static final String	DATA_SOURCE_SHARE			= "gwssi_gxk";	// ���������Դ

	public static final String	DATA_SOURCE_COLLECT			= "gwssi_cjk";	// �ɼ���

	public static final String	DATA_SOURCE_ZXK				= "gwssi";		// ���Ŀ�

	public static final String	DATA_SOURCE_SynchroTable	= "gwssi_cjk";	// ͬ�������ݿ�����

	public static final String	SERVIVE_OBJECT_IN			= "��������";		// �ڲ��û�
																			// �������

	/** ����Դ���� */
	public static final String	TYPE_SJYLX_WEBSERVICE		= "00";		// webservice

	public static final String	TYPE_SJYLX_DATABASE			= "01";		// ���ݿ�

	public static final String	TYPE_SJYLX_FTP				= "02";		// ftp

	public static final String	TYPE_SJYLX_JMS				= "03";		// jms

	public static final String	TYPE_SJYLX_SOCKET			= "04";

	public static String		INTERFACE_TIME;							// ����ӿڲ�ѯ��ʱ����
}
