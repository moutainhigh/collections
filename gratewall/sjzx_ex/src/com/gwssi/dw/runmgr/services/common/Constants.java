package com.gwssi.dw.runmgr.services.common;

public class Constants
{
	//�Զ������涨���ṹ
	public static final String SELF_SERVICE_PACKAGE = "com.gwssi.dw.runmgr.services.impl.";
	
	//��������ֶηָ���
	public static final String SERVICE_COLUMN_SEPARATOR = ",";
	
	/** ��ͬ�ֶ��ж����ѯ����ʱ��ֵ�ķָ��� */
	public static final String MULTI_COLUMN_CONDITION_SEPARATOR = ",";
	
	/** �ֶβ�ѯ����ΪINʱ��ֵ�ķָ��� */
	public static final String MULTI_COLUMN_IN_SEPARATOR = "\\|";
	
	/**�ڲ��û�*/
	public static final int USER_TYPE_INNER = 0;
	
	/**�ⲿ�û�*/
	public static final int USER_TYPE_OUTTER = 1;
	
	//����������ࣺһ�����
	public static final String SERVICE_TYPE_GENERAL = "һ�����";
	
	//����������ࣺ�Զ������
	public static final String SERVICE_TYPE_SELF = "�Զ������";
	
	//web service����Ҫ��Ե�˰�û��������ڸ�ʽ
	public static final String WS_QUERY_DATE_FORMAT = "yyyyMMdd";
	
	/** �ҷ����ݿ��д洢�����ڸ�ʽ */
	public static final String DB_DATE_FORMAT = "yyyy-MM-dd";
	
	//��ҵ״̬����ҵ
	public static final int SERVICE_QY_STATE_OPEN = 12;
	
	//��ҵ״̬��ע��
	public static final int SERVICE_QY_STATE_ZX = 13;
	
	//��ҵ״̬������
	public static final int SERVICE_QY_STATE_DX = 14;
	
	//��ҵ״̬�����
	public static final int SERVICE_QY_STATE_BG = 15;
	
	//�ֶ����ͣ�������
	public static final String SERVICE_COLUMN_TYPE_NUMBER = "N";
	
	//�ֶ����ͣ�����
	public static final String SERVICE_COLUMN_TYPE_DATE = "T";
	
	public static final int SERVICE_DEFAULT_MAX_RECORDS = 2000;
	
	//�ͻ��˵��õı����������
	/** ��¼�� */
	public static final String SERVICE_IN_PARAM_LOGIN_NAME 	= "LOGIN_NAME";
	/** ��¼���� */
	public static final String SERVICE_IN_PARAM_LOGIN_PASSWORD = "PASSWORD";
	/** ����¼�� */
	public static final String SERVICE_IN_PARAM_MAX_RECORDS 	= "MAX_RECORDS";
	/** ������� */
	public static final String SERVICE_IN_PARAM_SERVICE_CODE 	= "SVR_CODE";
	public static final String SERVICE_IN_PARAM_START_PARAM = "KS_";
	public static final String SERVICE_IN_PARAM_END_PARAM = "JS_";
	/** webservice�ͻ����ṩ�����ڸ�ʽ */
	public static final String SERVICE_IN_PARAM_DATE_FORMAT = "yyyyMMdd";
	/** �����������ѯ�������� */
	public static final long SERVICE_IN_PARAM_MAX_QUERY_DATE = 7 * 24 * 60 * 60 * 1000;
	
	//���񷵻صĲ�������
	/** ���ش��� */
	public static final String SERVICE_OUT_PARAM_FHDM = "FHDM";
	/** ��ʼ��¼�� */
	public static final String SERVICE_OUT_PARAM_KSJLS = "KSJLS";
	/** ������¼�� */
	public static final String SERVICE_OUT_PARAM_JSJLS = "JSJLS";
	/** ��ѯ�õ����ܼ�¼�� */
	public static final String SERVICE_OUT_PARAM_ZTS = "ZTS";
	/** ��¼�б���Map�е�keyֵ */
	public static final String SERVICE_OUT_PARAM_ARRAY = "GSDJ_INFO_ARRAY";
	//���񷵻ش�������
	/** ��������¼������ */
	public static final String SERVICE_FHDM_OVER_MAX    	  = "BAIC0020";
	/** �ɹ����� */
	public static final String SERVICE_FHDM_SUCCESS     	  = "BAIC0000";
	/** �޷��������ļ�¼ */
	public static final String SERVICE_FHDM_NO_RESULT   	  = "BAIC0010";
	/** �û��ṩ�Ĳ������� */
	public static final String SERVICE_FHDM_INPUT_PARAM_ERROR = "BAIC0030";
	/** ����������ڲ�ѯ���� */
	public static final String SERVICE_FHDM_OVER_DATE_RANGE   = "BAIC0040";
	/** ϵͳ���� */
	public static final String SERVICE_FHDM_SYSTEM_ERROR      = "BAIC0050";
	/** ���Ӳ��Է��ش��� */
	public static final String SERVICE_FHDM_LJ_QUERY          = "BAIC0060";
	/** ��¼ʧ�� */
	public static final String SERVICE_FHDM_LOGIN_FAIL        = "BAIC0070";
	/** δ֪���� */
	public static final String SERVICE_FHDM_UNKNOWN_ERROR     = "BAIC9999";
	/** ��������ͣ */
	public static final String SERVICE_FHDM_SERVICE_PAUSE = "BAIC0200";
	
	public static final String COLUMN_TYPE_DATE = "2";

	//DC2-jufeng-2012-07-07
	/** �û�IP����*/
	public static final String SERVICE_FHDM_ERROR_IP            = "BAIC0101";
	/** �û�ĳ������ķ���Ȩ����Ϣ����*/
	public static final String SERVICE_FHDM_ERROR_LIMIT         = "BAIC0100";
	/** �����û�ĳ�������������*/
	public static final String SERVICE_FHDM_LOCK_WEEK           = "BAIC0102";
	/** �����û�ĳ���������ʱ������*/
	public static final String SERVICE_FHDM_LOCK_TIME           = "BAIC0103";
	/** �����û�ĳ��������ʴ�������*/
	public static final String SERVICE_FHDM_LOCK_NUMBER         = "BAIC0104";
	/** �����û�ĳ�����������������������*/
	public static final String SERVICE_FHDM_LOCK_TOTAL          = "BAIC0105";
	/** �����û�ĳ����������Ѽ���*/
	public static final String SERVICE_FHDM_LOCKED_TODAY        = "BAIC0109";
	
}
