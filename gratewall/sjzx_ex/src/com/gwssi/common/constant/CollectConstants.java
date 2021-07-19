package com.gwssi.common.constant;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�CollectConstants �������� ����ʱ�䣺Mar 27, 2013 1:40:56 PM
 * �޸��ˣ�lizheng �޸�ʱ�䣺Mar 27, 2013 1:40:56 PM �޸ı�ע����ʱδ�޸ı������ڵĴ���ṹ
 * 
 * @version
 * 
 */

public class CollectConstants
{

	public static int			COUNT						= 1;

	/** �Ƿ����� */
	public static final String	IS_KEY						= "1";							// ������

	/** ���������� */
	public static final String	TYPE_CHAR					= "01";						// CHAR

	public static final String	TYPE_VARCHAR2				= "02";						// VARCHAR2

	public static final String	TYPE_INT					= "03";						// INT

	public static final String	TYPE_DATE					= "04";						// DATE

	/** �������� */
	public static final String	TYPE_PARM_STRING			= "01";						// STRING

	public static final String	TYPE_PARM_BOOLEAN			= "02";						// BOOLEAN

	public static final String	TYPE_PARM_INT				= "03";						// INT

	public static final String	TYPE_PARM_DOUBLE			= "04";						// DOUBLE

	public static final String	TYPE_PARM_MAP				= "05";						// MAP

	/** ������ */
	public static final String	TYPE_TABLE_YW				= "00";						// ҵ���

	public static final String	TYPE_TABLE_DM				= "01";						// �����

	/** �Ƿ����� */
	public static final String	IS_CODE_TABLE_Y				= "1";							// �����

	public static final String	IS_CODE_TABLE_N				= "0";							// �Ǵ����

	/** �ɼ���Դ */
	public static final String	TYPE_CJLY_OUT				= "1";							// �ⲿ�ɼ���

	public static final String	TYPE_CJLY_IN				= "2";							// �ڲ��ɼ���

	/** �ɼ���Դ���� */
	public static final String	TYPE_CJLY_OUT_NAME			= "�ⲿ�ɼ���";						// �ⲿ�ɼ���

	public static final String	TYPE_CJLY_IN_NAME			= "�ڲ��ɼ���";						// �ڲ��ɼ���

	/** �ɼ����Ƿ��������ݱ� */
	public static final String	TYPE_IF_CREAT_YES			= "1";							// �����ɱ�

	public static final String	TYPE_IF_CREAT_NO			= "0";							// δ���ɱ�

	/** �ɼ����Ƿ��������ݱ� */
	public static final String	TYPE_IF_CREAT_YES_NAME		= "������";						// �����ɱ�

	public static final String	TYPE_IF_CREAT_NO_NAME		= "δ����";						// δ���ɱ�

	/** �ɼ����� */
	public static final String	TYPE_CJLX_WEBSERVICE		= "00";						// webservice

	public static final String	TYPE_CJLX_FILEUPLOAD		= "01";						// �ļ��ϴ�

	public static final String	TYPE_CJLX_FTP				= "02";						// ftp

	public static final String	TYPE_CJLX_DATABASE			= "03";						// ���ݿ�

	public static final String	TYPE_CJLX_JMS				= "04";						// JMS

	public static final String	TYPE_CJLX_SOCKET			= "05";						// socket

	/** �ɼ��������� */
	public static final String	TYPE_CJLX_WEBSERVICE_NAME	= "WebService";				// webservice

	public static final String	TYPE_CJLX_FTP_NAME			= "FTP";						// ftp

	/** �ɼ�������� */
	public static final String	TYPE_WEBSERVICE_TABLE		= "collect_webservice_task";	// webservice

	// ����

	public static final String	TYPE_FTP_TABLE				= "collect_ftp_task";			// ftp

	// ����

	/** ����ͣ�ñ�� */
	public static final String	TYPE_QY						= "1";							// ����

	public static final String	TYPE_TY						= "0";							// ͣ��

	/** �ͻ��˲��� */
	public static final String	ClIENT_BACK_CODE			= "BACK_CODE";					// ����ֵ

	public static final String	CLIENT_STATE				= "CLIENT_STATE";				// �ͻ���״̬

	public static final String	CLIENT_STATE_YES			= "Y";							// ������

	public static final String	CLIENT_STATE_NO				= "N";							// ��������

	public static final String	WSDL_URL					= "WSDL_URL";					// ����·��

	public static final String	WSDL_IP						= "WSDL_IP";					// ����IP

	public static final String	WSDL_PORT					= "WSDL_PORT";					// ���ʶ˿�

	public static final String	QNAME						= "QNAME";						// ��������

	public static String		WSDL_URL_TEST;												// ���Է��ʵ�ַ

	public static final String	QNAME_ERQI					= "query";						// ���ڷ���

	public static final String	QNAME_NEW					= "queryData";					// �·���

	public static final String	WEB_NAME_SPACE				= "WEB_NAME_SPACE";			// �����ռ�

	public static final String	PARAM_LIST					= "PARAM_LIST";				// �����б�

	public static final String	PARAM_VALUE					= "PARAM_VALUE";				// ����ֵ

	public static final String	CJ_REQ_NAME					= "CJ_REQ_NAME";				// �����������

	public static final String	CJ_RES_NAME					= "CJ_RES_NAME";				// ���ն�������

	/** �ͻ��˲��� */

	public static final String	WSDL_TASK_URL				= "WSDL_TASK_URL";				// webservice�������URL

	public static final String	LOGIN_NAME					= "LOGIN_NAME";				// ��¼����

	public static final String	PASSWORD					= "PASSWORD";					// ����

	public static final String	KSJLS						= "KSJLS";						// ��ʼ��¼��

	public static final String	JSJLS						= "JSJLS";						// ������¼��

	public static final String	ZTS							= "ZTS";						// ������

	public static final String	WEBSERVICE_TASK_ID			= "WEBSERVICE_TASK_ID";		// webservice����ID

	public static final String	METHOD_NAME					= "METHOD_NAME_EN";			// ��������

	public static final String	COLLECT_TABLE_ID			= "COLLECT_TABLE";				// ��ID

	public static final String	COLLECT_TABLE_NAME			= "TABLE_NAME_EN";				// ������

	public static final String	COLLECT_DATAITEM_NAME		= "DATAITEM_NAME_EN";			// ����������

	public static final String	EXLIMP_TABLE_STATUS_Y		= "1";							// ��Ч

	public static final String	EXLIMP_TABLE_STATUS_N		= "0";							// ��Ч

	/** �ɼ��ļ����ݷָ��� */
	public static final String	COLLECT_FILE_SPEARTOR		= "\\|";						// �ɼ��ļ��ָ���

	public static final String	COLLECT_MODE				= "COLLECT_MODE";				// ���ݲɼ���ʽ

	public static final String	COLLECT_MODE_ADD			= "1";							// ���ݲɼ���ʽ����

	public static final String	COLLECT_MODE_ALL			= "2";							// ���ݲɼ���ʽȫ��

	/** ���ش��� */
	public static final String	CLIENT_COLLECT_PARAM_FHDM	= "FHDM";						// �ͻ��˷��ش���

	/** �ɼ����ݳɹ� */
	public static final String	CLIENT_FHDM_SUCCESS			= "BAIC0000";					// �ɼ����ݳɹ�

	/** ϵͳ���� */
	public static final String	CLIENT_FHDM_SYS_ERROR		= "CODE0001";					// ϵͳ����

	/** ִ��SQL������ */
	public static final String	CLIENT_FHDM_SQL_ERROR		= "CODE0002";					// ִ��SQL������

	/** �ɼ������ */
	public static final String	CLIENT_FHDM_COL_TBL_ERROR	= "CODE0003";					// �ɼ������

	/** �ɼ���������� */
	public static final String	CLIENT_FHDM_DATAITEM_ERROR	= "CODE0004";					// �ɼ����������

	/** �ɼ�������� */
	public static final String	CLIENT_FHDM_TASK_ERROR		= "CODE0005";					// �ɼ��������

	/** webService������� */
	public static final String	CLIENT_FHDM_WS_ERROR		= "CODE0006";					// webService�������

	/** webService����URL���� */
	public static final String	CLIENT_FHDM_WS_URL_ERROR	= "CODE0007";					// webService����URL����

	/** ���÷������ */
	public static final String	CLIENT_FHDM_INVOKE_ERROR	= "CODE0008";					// ���÷������

	/** ������ת����dom��ʽ���� */
	public static final String	CLIENT_FHDM_TODOM_ERROR		= "CODE0009";					// ������ת����dom��ʽ����

	/** ���Ϊ�� */
	public static final String	CLIENT_FHDM_FAIL			= "CODE0010";					// ���Ϊ��

	/** �ɼ�״̬ */
	public static final String	COLLECT_STATUS_NOT			= "00";						// δ�ɼ�

	public static final String	COLLECT_STATUS_ING			= "01";						// ���ڲɼ�

	public static final String	COLLECT_STATUS_SUCCESS		= "02";						// �ɼ��ɹ�

	public static final String	COLLECT_STATUS_FAIL			= "03";						// �ɼ�ʧ��

	public static final String	DATASOURCE_CJK				= "5";							// �ɼ�������Դkey

	public static final String	DATASOURCE_GXK				= "6";							// �ɼ�������Դkey

	public static final String	DATASOURCE_DEFAULT			= "2";							// ƽ̨����Դkey
	
	
	//�ַ�
	public static final String	TYPE_PARAM_STYLE_01			= "01";						// �ɼ�����_������ʽ�ַ���
	//XML
	public static final String	TYPE_PARAM_STYLE_02			= "02";						// �ɼ�����_������ʽxml
	
	//�ַ���
	public static final String	PARAM_STYLE_00				= "00";						// �ɼ�����_���������ַ���
	
	//����
	public static final String	PARAM_STYLE_01				= "01";						// �ɼ�����_������������
	
	//����
	public static final String	PARAM_STYLE_02				= "02";						// �ɼ�����_��������������

	public static final String	DATE_STYLE_00				= "00";						// �ɼ�����_���ڸ�ʽ��

	public static final String	DATE_STYLE_01				= "01";						// �ɼ�����_���ڸ�ʽyyyymmdd

	public static final String	DATE_STYLE_02				= "02";						// �ɼ�����_���ڸ�ʽyyyy/mm/dd

	public static final String	DATE_STYLE_03				= "03";						// �ɼ�����_���ڸ�ʽyyyy-mm-dd

	public static final String	TODAY						= "TODAY";						// ����

	public static final String	JMS_SERVER_URL				=  "http://160.99.2.2:8089";	// JMS //http://172.30.18.50:8089

	// MQ��������ַ

	public static final String	CJ_RES_GSJ					= "CjResGsj";					// ���ض�������

}
