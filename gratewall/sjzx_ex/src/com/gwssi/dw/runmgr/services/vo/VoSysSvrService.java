package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_svr_service]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSvrService extends VoBase
{
	private static final long serialVersionUID = 200805061510580006L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_SVR_SERVICE_ID = "sys_svr_service_id" ;	/* ��������� */
	public static final String ITEM_NAME = "name" ;					/* ����������� */
	public static final String ITEM_DCZD_DM = "dczd_dm" ;			/* ������� */
	public static final String ITEM_TABLE_NO = "table_no" ;			/* ���ݱ���� */
	public static final String ITEM_COLUMN_NO = "column_no" ;		/* �ֶα��� */
	public static final String ITEM_CREATE_DATE = "create_date" ;	/* �������� */
	public static final String ITEM_CREATE_BY = "create_by" ;		/* ������ */
	public static final String ITEM_PARAM_COLUMNS = "param_columns" ;	/* �����Զ� */
	public static final String ITEM_COMMENT = "comment" ;			/* ��ע */
	public static final String ITEM_SVR_CODE = "svr_code" ;			/* ������� */
	
	/**
	 * ���캯��
	 */
	public VoSysSvrService()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSvrService(DataBus value)
	{
		super(value);
	}
	
	/* ��������� : String */
	public String getSys_svr_service_id()
	{
		return getValue( ITEM_SYS_SVR_SERVICE_ID );
	}

	public void setSys_svr_service_id( String sys_svr_service_id1 )
	{
		setValue( ITEM_SYS_SVR_SERVICE_ID, sys_svr_service_id1 );
	}

	/* ����������� : String */
	public String getName()
	{
		return getValue( ITEM_NAME );
	}

	public void setName( String name1 )
	{
		setValue( ITEM_NAME, name1 );
	}

	/* ������� : String */
	public String getDczd_dm()
	{
		return getValue( ITEM_DCZD_DM );
	}

	public void setDczd_dm( String dczd_dm1 )
	{
		setValue( ITEM_DCZD_DM, dczd_dm1 );
	}

	/* ���ݱ���� : String */
	public String getTable_no()
	{
		return getValue( ITEM_TABLE_NO );
	}

	public void setTable_no( String table_no1 )
	{
		setValue( ITEM_TABLE_NO, table_no1 );
	}

	/* �ֶα��� : String */
	public String getColumn_no()
	{
		return getValue( ITEM_COLUMN_NO );
	}

	public void setColumn_no( String column_no1 )
	{
		setValue( ITEM_COLUMN_NO, column_no1 );
	}

	/* �������� : String */
	public String getCreate_date()
	{
		return getValue( ITEM_CREATE_DATE );
	}

	public void setCreate_date( String create_date1 )
	{
		setValue( ITEM_CREATE_DATE, create_date1 );
	}

	/* ������ : String */
	public String getCreate_by()
	{
		return getValue( ITEM_CREATE_BY );
	}

	public void setCreate_by( String create_by1 )
	{
		setValue( ITEM_CREATE_BY, create_by1 );
	}

	/* �����Զ� : String */
	public String getParam_columns()
	{
		return getValue( ITEM_PARAM_COLUMNS );
	}

	public void setParam_columns( String param_columns1 )
	{
		setValue( ITEM_PARAM_COLUMNS, param_columns1 );
	}

	/* ��ע : String */
	public String getComment()
	{
		return getValue( ITEM_COMMENT );
	}

	public void setComment( String comment1 )
	{
		setValue( ITEM_COMMENT, comment1 );
	}

	/* ������� : String */
	public String getSvr_code()
	{
		return getValue( ITEM_SVR_CODE );
	}

	public void setSvr_code( String svr_code1 )
	{
		setValue( ITEM_SVR_CODE, svr_code1 );
	}

}

