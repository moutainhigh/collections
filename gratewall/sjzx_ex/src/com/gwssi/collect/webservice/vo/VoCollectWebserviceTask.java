package com.gwssi.collect.webservice.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[collect_webservice_task]�����ݶ�����
 * @author Administrator
 *
 */
public class VoCollectWebserviceTask extends VoBase
{
	private static final long serialVersionUID = 201304101334340002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_WEBSERVICE_TASK_ID = "webservice_task_id" ;	/* WEBSERVICE����ID */
	public static final String ITEM_COLLECT_TASK_ID = "collect_task_id" ;	/* �ɼ�����ID */
	public static final String ITEM_SERVICE_NO = "service_no" ;		/* ������ */
	public static final String ITEM_METHOD_NAME_EN = "method_name_en" ;	/* �������� */
	public static final String ITEM_METHOD_NAME_CN = "method_name_cn" ;	/* ������������ */
	public static final String ITEM_COLLECT_TABLE = "collect_table" ;	/* ��Ӧ�ɼ��� */
	public static final String ITEM_COLLECT_MODE = "collect_mode" ;	/* �ɼ���ʽ */
	public static final String ITEM_IS_ENCRYPTION = "is_encryption" ;	/* �Ƿ���� */
	public static final String ITEM_ENCRYPT_MODE = "encrypt_mode" ;	/* ���ܷ�ʽ */
	public static final String ITEM_METHOD_DESCRIPTION = "method_description" ;	/* �������� */
	public static final String ITEM_METHOD_STATUS = "method_status" ;	/* ����״̬ */
	public static final String ITEM_WEB_NAME_SPACE = "web_name_space" ;	/* �����ռ� */
	
	/**
	 * ���캯��
	 */
	public VoCollectWebserviceTask()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoCollectWebserviceTask(DataBus value)
	{
		super(value);
	}
	
	/* WEBSERVICE����ID : String */
	public String getWebservice_task_id()
	{
		return getValue( ITEM_WEBSERVICE_TASK_ID );
	}

	public void setWebservice_task_id( String webservice_task_id1 )
	{
		setValue( ITEM_WEBSERVICE_TASK_ID, webservice_task_id1 );
	}

	/* �ɼ�����ID : String */
	public String getCollect_task_id()
	{
		return getValue( ITEM_COLLECT_TASK_ID );
	}

	public void setCollect_task_id( String collect_task_id1 )
	{
		setValue( ITEM_COLLECT_TASK_ID, collect_task_id1 );
	}

	/* ������ : String */
	public String getService_no()
	{
		return getValue( ITEM_SERVICE_NO );
	}

	public void setService_no( String service_no1 )
	{
		setValue( ITEM_SERVICE_NO, service_no1 );
	}

	/* �������� : String */
	public String getMethod_name_en()
	{
		return getValue( ITEM_METHOD_NAME_EN );
	}

	public void setMethod_name_en( String method_name_en1 )
	{
		setValue( ITEM_METHOD_NAME_EN, method_name_en1 );
	}

	/* ������������ : String */
	public String getMethod_name_cn()
	{
		return getValue( ITEM_METHOD_NAME_CN );
	}

	public void setMethod_name_cn( String method_name_cn1 )
	{
		setValue( ITEM_METHOD_NAME_CN, method_name_cn1 );
	}

	/* ��Ӧ�ɼ��� : String */
	public String getCollect_table()
	{
		return getValue( ITEM_COLLECT_TABLE );
	}

	public void setCollect_table( String collect_table1 )
	{
		setValue( ITEM_COLLECT_TABLE, collect_table1 );
	}

	/* �ɼ���ʽ : String */
	public String getCollect_mode()
	{
		return getValue( ITEM_COLLECT_MODE );
	}

	public void setCollect_mode( String collect_mode1 )
	{
		setValue( ITEM_COLLECT_MODE, collect_mode1 );
	}

	/* �Ƿ���� : String */
	public String getIs_encryption()
	{
		return getValue( ITEM_IS_ENCRYPTION );
	}

	public void setIs_encryption( String is_encryption1 )
	{
		setValue( ITEM_IS_ENCRYPTION, is_encryption1 );
	}

	/* ���ܷ�ʽ : String */
	public String getEncrypt_mode()
	{
		return getValue( ITEM_ENCRYPT_MODE );
	}

	public void setEncrypt_mode( String encrypt_mode1 )
	{
		setValue( ITEM_ENCRYPT_MODE, encrypt_mode1 );
	}

	/* �������� : String */
	public String getMethod_description()
	{
		return getValue( ITEM_METHOD_DESCRIPTION );
	}

	public void setMethod_description( String method_description1 )
	{
		setValue( ITEM_METHOD_DESCRIPTION, method_description1 );
	}

	/* ����״̬ : String */
	public String getMethod_status()
	{
		return getValue( ITEM_METHOD_STATUS );
	}

	public void setMethod_status( String method_status1 )
	{
		setValue( ITEM_METHOD_STATUS, method_status1 );
	}
	/* ��ռ� : String */
	public String getWEB_name_space()
	{
		return getValue( ITEM_WEB_NAME_SPACE );
	}

	public void setWEB_name_space( String web_name_space1 )
	{
		setValue( ITEM_WEB_NAME_SPACE, web_name_space1 );
	}

}

