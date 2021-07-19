package com.gwssi.resource.svrobj.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[res_data_source]�����ݶ�����
 * @author Administrator
 *
 */
public class VoResDataSource extends VoBase
{
	private static final long serialVersionUID = 201303141052490002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DATA_SOURCE_ID = "data_source_id" ;	/* ����ԴID */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* �������ID */
	public static final String ITEM_DATA_SOURCE_TYPE = "data_source_type" ;	/* ����Դ���� */
	public static final String ITEM_DATA_SOURCE_NAME = "data_source_name" ;	/* ����Դ���� */
	public static final String ITEM_DATA_SOURCE_IP = "data_source_ip" ;	/* ����ԴIP */
	public static final String ITEM_ACCESS_PORT = "access_port" ;	/* ���ʶ˿� */
	public static final String ITEM_ACCESS_URL = "access_url" ;		/* ����URL */
	public static final String ITEM_DB_TYPE = "db_type" ;			/* ���ݿ����� */
	public static final String ITEM_DB_INSTANCE = "db_instance" ;	/* ����Դʵ�� */
	public static final String ITEM_DB_USERNAME = "db_username" ;	/* ����Դ�û��� */
	public static final String ITEM_DB_PASSWORD = "db_password" ;	/* ����Դ���� */
	public static final String ITEM_DB_DESC = "db_desc" ;			/* ����Դ���� */
	public static final String ITEM_DB_STATUS = "db_status" ;		/* ����Դ״̬ */
	public static final String ITEM_REQ_QUE_NAME = "req_que_name" ;	/* ����������� */
	public static final String ITEM_RES_QUE_NAME = "res_que_name" ;	/* ���ն������� */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* ��Ч��� */
	public static final String ITEM_CREATOR_ID = "creator_id" ;		/* ������ID */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* ����ʱ�� */
	public static final String ITEM_LAST_MODIFY_ID = "last_modify_id" ;	/* ����޸���ID */
	public static final String ITEM_LAST_MODIFY_TIME = "last_modify_time" ;	/* ����޸�ʱ�� */
	
	/**
	 * ���캯��
	 */
	public VoResDataSource()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoResDataSource(DataBus value)
	{
		super(value);
	}
	
	/* ����ԴID : String */
	public String getData_source_id()
	{
		return getValue( ITEM_DATA_SOURCE_ID );
	}

	public void setData_source_id( String data_source_id1 )
	{
		setValue( ITEM_DATA_SOURCE_ID, data_source_id1 );
	}

	/* �������ID : String */
	public String getService_targets_id()
	{
		return getValue( ITEM_SERVICE_TARGETS_ID );
	}

	public void setService_targets_id( String service_targets_id1 )
	{
		setValue( ITEM_SERVICE_TARGETS_ID, service_targets_id1 );
	}

	/* ����Դ���� : String */
	public String getData_source_type()
	{
		return getValue( ITEM_DATA_SOURCE_TYPE );
	}

	public void setData_source_type( String data_source_type1 )
	{
		setValue( ITEM_DATA_SOURCE_TYPE, data_source_type1 );
	}

	/* ����Դ���� : String */
	public String getData_source_name()
	{
		return getValue( ITEM_DATA_SOURCE_NAME );
	}

	public void setData_source_name( String data_source_name1 )
	{
		setValue( ITEM_DATA_SOURCE_NAME, data_source_name1 );
	}

	/* ����ԴIP : String */
	public String getData_source_ip()
	{
		return getValue( ITEM_DATA_SOURCE_IP );
	}

	public void setData_source_ip( String data_source_ip1 )
	{
		setValue( ITEM_DATA_SOURCE_IP, data_source_ip1 );
	}

	/* ���ʶ˿� : String */
	public String getAccess_port()
	{
		return getValue( ITEM_ACCESS_PORT );
	}

	public void setAccess_port( String access_port1 )
	{
		setValue( ITEM_ACCESS_PORT, access_port1 );
	}

	/* ����URL : String */
	public String getAccess_url()
	{
		return getValue( ITEM_ACCESS_URL );
	}

	public void setAccess_url( String access_url1 )
	{
		setValue( ITEM_ACCESS_URL, access_url1 );
	}

	/* ���ݿ����� : String */
	public String getDb_type()
	{
		return getValue( ITEM_DB_TYPE );
	}

	public void setDb_type( String db_type1 )
	{
		setValue( ITEM_DB_TYPE, db_type1 );
	}

	/* ����Դʵ�� : String */
	public String getDb_instance()
	{
		return getValue( ITEM_DB_INSTANCE );
	}

	public void setDb_instance( String db_instance1 )
	{
		setValue( ITEM_DB_INSTANCE, db_instance1 );
	}

	/* ����Դ�û��� : String */
	public String getDb_username()
	{
		return getValue( ITEM_DB_USERNAME );
	}

	public void setDb_username( String db_username1 )
	{
		setValue( ITEM_DB_USERNAME, db_username1 );
	}

	/* ����Դ���� : String */
	public String getDb_password()
	{
		return getValue( ITEM_DB_PASSWORD );
	}

	public void setDb_password( String db_password1 )
	{
		setValue( ITEM_DB_PASSWORD, db_password1 );
	}

	/* ����Դ���� : String */
	public String getDb_desc()
	{
		return getValue( ITEM_DB_DESC );
	}

	public void setDb_desc( String db_desc1 )
	{
		setValue( ITEM_DB_DESC, db_desc1 );
	}

	/* ����Դ״̬ : String */
	public String getDb_status()
	{
		return getValue( ITEM_DB_STATUS );
	}

	public void setDb_status( String db_status1 )
	{
		setValue( ITEM_DB_STATUS, db_status1 );
	}
	
	/* ����������� : String */
	public String getReq_que_name()
	{
		return getValue( ITEM_REQ_QUE_NAME );
	}

	public void setReq_que_name( String req_que_name1 )
	{
		setValue( ITEM_REQ_QUE_NAME, req_que_name1 );
	}
	
	/* ���ն������� : String */
	public String getRes_que_name()
	{
		return getValue( ITEM_RES_QUE_NAME );
	}

	public void setRes_que_name( String res_que_name1 )
	{
		setValue( ITEM_RES_QUE_NAME, res_que_name1 );
	}

	/* ��Ч��� : String */
	public String getIs_markup()
	{
		return getValue( ITEM_IS_MARKUP );
	}

	public void setIs_markup( String is_markup1 )
	{
		setValue( ITEM_IS_MARKUP, is_markup1 );
	}

	/* ������ID : String */
	public String getCreator_id()
	{
		return getValue( ITEM_CREATOR_ID );
	}

	public void setCreator_id( String creator_id1 )
	{
		setValue( ITEM_CREATOR_ID, creator_id1 );
	}

	/* ����ʱ�� : String */
	public String getCreated_time()
	{
		return getValue( ITEM_CREATED_TIME );
	}

	public void setCreated_time( String created_time1 )
	{
		setValue( ITEM_CREATED_TIME, created_time1 );
	}

	/* ����޸���ID : String */
	public String getLast_modify_id()
	{
		return getValue( ITEM_LAST_MODIFY_ID );
	}

	public void setLast_modify_id( String last_modify_id1 )
	{
		setValue( ITEM_LAST_MODIFY_ID, last_modify_id1 );
	}

	/* ����޸�ʱ�� : String */
	public String getLast_modify_time()
	{
		return getValue( ITEM_LAST_MODIFY_TIME );
	}

	public void setLast_modify_time( String last_modify_time1 )
	{
		setValue( ITEM_LAST_MODIFY_TIME, last_modify_time1 );
	}

}

