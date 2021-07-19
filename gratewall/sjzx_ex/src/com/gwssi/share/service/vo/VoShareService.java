package com.gwssi.share.service.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[share_service]�����ݶ�����
 * @author Administrator
 *
 */
public class VoShareService extends VoBase
{
	private static final long serialVersionUID = 201303261650000006L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SERVICE_ID = "service_id" ;		/* ����ID */
	public static final String ITEM_INTERFACE_ID = "interface_id" ;	/* �ӿ�ID */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* ����������� */
	public static final String ITEM_SERVICE_NAME = "service_name" ;	/* �������� */
	public static final String ITEM_SERVICE_TYPE = "service_type" ;	/* �������� */
	public static final String ITEM_SERVICE_NO = "service_no" ;		/* ������ */
	public static final String ITEM_COLUMN_NO = "column_no" ;		/* ������ID */
	public static final String ITEM_COLUMN_NAME_CN = "column_name_cn" ;	/* �������������� */
	public static final String ITEM_COLUMN_ALIAS = "column_alias" ;	/* ��������� */
	public static final String ITEM_SORT_COLUMN = "sort_column" ;	/* �����ֶ� */
	public static final String ITEM_SQL = "sql" ;					/* SQL */
	public static final String ITEM_SQL_ONE = "sql_one" ;			/* SQL1 */
	public static final String ITEM_SQL_TWO = "sql_two" ;			/* SQL2 */
	public static final String ITEM_SERVICE_DESCRIPTION = "service_description" ;	/* ����˵�� */
	public static final String ITEM_REGIST_DESCRIPTION = "regist_description" ;	/* ����˵�� */
	public static final String ITEM_SERVICE_STATE = "service_state" ;	/* ����״̬ */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* ��Ч��� */
	public static final String ITEM_CREATOR_ID = "creator_id" ;		/* ������ID */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* ����ʱ�� */
	public static final String ITEM_LAST_MODIFY_ID = "last_modify_id" ;	/* ����޸���ID */
	public static final String ITEM_LAST_MODIFY_TIME = "last_modify_time" ;	/* ����޸�ʱ�� */
	public static final String ITEM_IS_MONTH_DATA = "is_month_data"; /* �Ƿ�����ֻ�ܷ��ʱ������� */
	public static final String ITEM_VISIT_PERIOD = "visit_period"; /* ��������ʱ���� */
	
	/**
	 * ���캯��
	 */
	public VoShareService()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoShareService(DataBus value)
	{
		super(value);
	}
	
	/* ����ID : String */
	public String getService_id()
	{
		return getValue( ITEM_SERVICE_ID );
	}

	public void setService_id( String service_id1 )
	{
		setValue( ITEM_SERVICE_ID, service_id1 );
	}

	/* �ӿ�ID : String */
	public String getInterface_id()
	{
		return getValue( ITEM_INTERFACE_ID );
	}

	public void setInterface_id( String interface_id1 )
	{
		setValue( ITEM_INTERFACE_ID, interface_id1 );
	}

	/* ����������� : String */
	public String getService_targets_id()
	{
		return getValue( ITEM_SERVICE_TARGETS_ID );
	}

	public void setService_targets_id( String service_targets_id1 )
	{
		setValue( ITEM_SERVICE_TARGETS_ID, service_targets_id1 );
	}

	/* �������� : String */
	public String getService_name()
	{
		return getValue( ITEM_SERVICE_NAME );
	}

	public void setService_name( String service_name1 )
	{
		setValue( ITEM_SERVICE_NAME, service_name1 );
	}

	/* �������� : String */
	public String getService_type()
	{
		return getValue( ITEM_SERVICE_TYPE );
	}

	public void setService_type( String service_type1 )
	{
		setValue( ITEM_SERVICE_TYPE, service_type1 );
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

	/* ������ID : String */
	public String getColumn_no()
	{
		return getValue( ITEM_COLUMN_NO );
	}

	public void setColumn_no( String column_no1 )
	{
		setValue( ITEM_COLUMN_NO, column_no1 );
	}

	/* �������������� : String */
	public String getColumn_name_cn()
	{
		return getValue( ITEM_COLUMN_NAME_CN );
	}

	public void setColumn_name_cn( String column_name_cn1 )
	{
		setValue( ITEM_COLUMN_NAME_CN, column_name_cn1 );
	}

	/* ��������� : String */
	public String getColumn_alias()
	{
		return getValue( ITEM_COLUMN_ALIAS );
	}

	public void setColumn_alias( String column_alias1 )
	{
		setValue( ITEM_COLUMN_ALIAS, column_alias1 );
	}

	/* �����ֶ� : String */
	public String getSort_column()
	{
		return getValue( ITEM_SORT_COLUMN );
	}

	public void setSort_column( String sort_column1 )
	{
		setValue( ITEM_SORT_COLUMN, sort_column1 );
	}

	/* SQL : String */
	public String getSql()
	{
		return getValue( ITEM_SQL );
	}

	public void setSql( String sql1 )
	{
		setValue( ITEM_SQL, sql1 );
	}

	/* SQL1 : String */
	public String getSql_one()
	{
		return getValue( ITEM_SQL_ONE );
	}

	public void setSql_one( String sql_one1 )
	{
		setValue( ITEM_SQL_ONE, sql_one1 );
	}

	/* SQL2 : String */
	public String getSql_two()
	{
		return getValue( ITEM_SQL_TWO );
	}

	public void setSql_two( String sql_two1 )
	{
		setValue( ITEM_SQL_TWO, sql_two1 );
	}

	/* ����˵�� : String */
	public String getService_description()
	{
		return getValue( ITEM_SERVICE_DESCRIPTION );
	}

	public void setService_description( String service_description1 )
	{
		setValue( ITEM_SERVICE_DESCRIPTION, service_description1 );
	}

	/* ����˵�� : String */
	public String getRegist_description()
	{
		return getValue( ITEM_REGIST_DESCRIPTION );
	}

	public void setRegist_description( String regist_description1 )
	{
		setValue( ITEM_REGIST_DESCRIPTION, regist_description1 );
	}

	/* ����״̬ : String */
	public String getService_state()
	{
		return getValue( ITEM_SERVICE_STATE );
	}

	public void setService_state( String service_state1 )
	{
		setValue( ITEM_SERVICE_STATE, service_state1 );
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
	
	/* �Ƿ���ʱ�������: String */
	public String getIs_month_data()
	{
		return getValue( ITEM_IS_MONTH_DATA );
	}

	public void setIs_month_data( String is_month_data1 )
	{
		setValue( ITEM_IS_MONTH_DATA, is_month_data1 );
	}

	public String getVisit_period(){
		return getValue( ITEM_VISIT_PERIOD );
	}
	
	public void setVisit_period( String visit_period1 ){
		setValue( ITEM_VISIT_PERIOD, visit_period1 );
	}
}
