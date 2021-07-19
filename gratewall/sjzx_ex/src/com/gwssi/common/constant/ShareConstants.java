package com.gwssi.common.constant;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�ShareConstants ��������webservice�ӿڹ������� �����ˣ�lizheng
 * ����ʱ�䣺Mar 27, 2013 1:40:56 PM �޸��ˣ�lizheng �޸�ʱ�䣺Mar 27, 2013 1:40:56 PM
 * �޸ı�ע����ʱδ�޸ı������ڵĴ���ṹ
 * 
 * @version
 * 
 */

public class ShareConstants
{
	/** �Զ������涨���ṹ */
	public static final String	SELF_SERVICE_PACKAGE				= "com.gwssi.dw.runmgr.services.impl.";

	/** ��������ֶηָ��� */
	public static final String	SERVICE_COLUMN_SEPARATOR			= ",";

	/** ��ͬ�ֶ��ж����ѯ����ʱ��ֵ�ķָ��� */
	public static final String	MULTI_COLUMN_CONDITION_SEPARATOR	= ",";

	/** �ֶβ�ѯ����ΪINʱ��ֵ�ķָ��� */
	public static final String	MULTI_COLUMN_IN_SEPARATOR			= "\\|";

	/** �ڲ��û� */
	public static final int		USER_TYPE_INNER						= 0;

	/** �ⲿ�û� */
	public static final int		USER_TYPE_OUTTER					= 1;

	/** ����������ࣺһ����� */
	public static final String	SERVICE_TYPE_GENERAL				= "һ�����";

	/** ����������ࣺ�Զ������ */
	public static final String	SERVICE_TYPE_SELF					= "�Զ������";

	/** web service����Ҫ��Ե�˰�û��������ڸ�ʽ */
	public static final String	WS_QUERY_DATE_FORMAT				= "yyyyMMdd";

	/** �ҷ����ݿ��д洢�����ڸ�ʽ */
	public static final String	DB_DATE_FORMAT						= "yyyy-MM-dd";

	/** ��ҵ״̬����ҵ */
	public static final int		SERVICE_QY_STATE_OPEN				= 12;

	/** ��ҵ״̬��ע�� */
	public static final int		SERVICE_QY_STATE_ZX					= 13;

	/** ��ҵ״̬������ */
	public static final int		SERVICE_QY_STATE_DX					= 14;

	/** ��ҵ״̬����� */
	public static final int		SERVICE_QY_STATE_BG					= 15;

	/** �ֶ����ͣ������� */
	public static final String	SERVICE_COLUMN_TYPE_NUMBER			= "N";

	/** �ֶ����ͣ����� */
	public static final String	SERVICE_COLUMN_TYPE_DATE			= "T";

	/** */
	public static final String	COLUMN_TYPE_DATE					= "2";

	/** Ĭ��ÿ�η��ʿ�ʼ�� */
	public static final int		SERVICE_DEFAULT_STAR_RECORDS		= 1;

	/** Ĭ��ÿ�η�������� */
	public static final int		SERVICE_DEFAULT_MAX_RECORDS			= 2000;

	// �ͻ��˵��õı����������
	/** ��¼�� */
	public static final String	SERVICE_IN_PARAM_LOGIN_NAME			= "LOGIN_NAME";

	/** ��¼���� */
	public static final String	SERVICE_IN_PARAM_LOGIN_PASSWORD		= "PASSWORD";

	/** ����¼�� */
	public static final String	SERVICE_IN_PARAM_MAX_RECORDS		= "MAX_RECORDS";

	/** ������� */
	public static final String	SERVICE_IN_PARAM_SERVICE_CODE		= "SVR_CODE";

	/** ��ʼ */
	public static final String	SERVICE_IN_PARAM_START_PARAM		= "KS_";

	/** ���� */
	public static final String	SERVICE_IN_PARAM_END_PARAM			= "JS_";

	/** webservice�ͻ����ṩ�����ڸ�ʽ */
	public static final String	SERVICE_IN_PARAM_DATE_FORMAT		= "yyyyMMdd";

	/** ��������ִ�е�SQL */
	public static final String	SERVICE_QUERY_SQL					= "QUERY_SQL";

	/** �����������ѯ�������� */
	public static final long	SERVICE_IN_PARAM_MAX_QUERY_DATE		= 7
																			* 24
																			* 60
																			* 60
																			* 1000;

	// �ͻ��˵��õı����������

	// ���񷵻صĲ�������
	/** �����Ƿ��ܱ�ʹ�� */
	public static final String	SERVICE_CAN_BE_USED					= "IS_USED";

	/** ���ش��� */
	public static final String	SERVICE_OUT_PARAM_FHDM				= "FHDM";

	/** ��ʼ��¼�� */
	public static final String	SERVICE_OUT_PARAM_KSJLS				= "KSJLS";

	/** ������¼�� */
	public static final String	SERVICE_OUT_PARAM_JSJLS				= "JSJLS";

	/** ��ѯ�õ����ܼ�¼�� */
	public static final String	SERVICE_OUT_PARAM_ZTS				= "ZTS";

	/** RN */
	public static final String	SERVICE_OUT_RN						= "RN";

	/** ETL_TIMESTAMP */
	public static final String	SERVICE_OUT_ETL_TIMESTAMP			= "ETL_TIMESTAMP";

	/** ��¼�б���Map�е�keyֵ */
	public static final String	SERVICE_OUT_PARAM_ARRAY				= "GSDJ_INFO_ARRAY";

	/** �������������� */
	public static final String	SERVICE_OUT_TOTALS					= "totals";

	// ���񷵻صĲ�������

	// ���񷵻ش�������
	/** ��������¼������ */
	public static final String	SERVICE_FHDM_OVER_MAX				= "BAIC0020";

	/** �ɹ����� */
	public static final String	SERVICE_FHDM_SUCCESS				= "BAIC0000";

	/** �޷��������ļ�¼ */
	public static final String	SERVICE_FHDM_NO_RESULT				= "BAIC0010";

	/** �û��ṩ�Ĳ������� */
	public static final String	SERVICE_FHDM_INPUT_PARAM_ERROR		= "BAIC0030";

	/** ����������ڲ�ѯ���� */
	public static final String	SERVICE_FHDM_OVER_DATE_RANGE		= "BAIC0040";

	/** ϵͳ���� */
	public static final String	SERVICE_FHDM_SYSTEM_ERROR			= "BAIC0050";

	/** ���Ӳ��Է��ش��� */
	public static final String	SERVICE_FHDM_LJ_QUERY				= "BAIC0060";

	/** ��¼ʧ�� */
	public static final String	SERVICE_FHDM_LOGIN_FAIL				= "BAIC0070";

	/** �û������ʧ�� */
	public static final String	SERVICE_FHDM_USER_ERROR				= "BAIC0071";

	/** ������ʧ�� */
	public static final String	SERVICE_FHDM_PWD_ERROR				= "BAIC0072";

	/** �û���Ч */
	public static final String	SERVICE_FHDM_USER_FAIL				= "BAIC0073";
	
	/** ����ʱ�䲻�ڱ��·�Χ�� */
	public static final String	SERVICE_FHDM_OVER_MONTH				= "BAIC0074";
	
	/** ��ѯ��������������� */
	public static final String	SERVICE_FHDM_OVER_DATE				= "BAIC0075";

	/** δ֪���� */
	public static final String	SERVICE_FHDM_UNKNOWN_ERROR			= "BAIC9999";

	/** ��������ͣ */
	public static final String	SERVICE_FHDM_SERVICE_PAUSE			= "BAIC0200";

	/** �û�IP���� */
	public static final String	SERVICE_FHDM_ERROR_IP				= "BAIC0101";

	/** �û�ĳ������ķ���Ȩ����Ϣ���� */
	public static final String	SERVICE_FHDM_ERROR_LIMIT			= "BAIC0100";

	/** �����û�ĳ������������� */
	public static final String	SERVICE_FHDM_LOCK_WEEK				= "BAIC0102";

	/** �����û�ĳ���������ʱ������ */
	public static final String	SERVICE_FHDM_LOCK_TIME				= "BAIC0103";

	/** �����û�ĳ��������ʴ������� */
	public static final String	SERVICE_FHDM_LOCK_NUMBER			= "BAIC0104";

	/** �����û�ĳ����������������������� */
	public static final String	SERVICE_FHDM_LOCK_TOTAL				= "BAIC0105";
	
	/** ���շ����������� */
	public static final String	SERVICE_FHDM_LOCK_TIME_COUNT		= "BAIC0106";

	/** �����û�ĳ����������Ѽ��� */
	public static final String	SERVICE_FHDM_LOCKED_TODAY			= "BAIC0109";

	/** ����δ�ҵ� */
	public static final String	SERVICE_FHDM_SERVICE_NOT_FOUND		= "BAIC0110";

	/** ������ͨ�� */
	public static final String	SERVICE_FHDM_SERVICE_PASS			= "BAIC0111";

	/** ��ѯSQL���� */
	public static final String	SERVICE_FHDM_SQL_ERROR				= "BAIC2020";
	
	/** ��֤ʧ�� */
	public static final String	SERVICE_FHDM_VERIFY_ERROR			= "BAIC2030";
	// ���񷵻ش�������
	
	//��ȡ�ӿڲ��������ļ�
	public static final String GET_INTERFACE_DATA_FAIL = "00";
	public static final String GET_INTERFACE_DATA_SUCCESS = "01";
	
	public static final String LIMIT_MONTH_DATA_YES = "Y";
	public static final String LIMIT_MONTH_DATA_NO = "N";
}
