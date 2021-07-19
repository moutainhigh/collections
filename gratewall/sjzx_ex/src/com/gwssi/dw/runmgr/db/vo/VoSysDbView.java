package com.gwssi.dw.runmgr.db.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoSysDbView extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_DB_VIEW_ID = "sys_db_view_id";			/* ��ͼID */
	public static final String ITEM_VIEW_NAME = "view_name";			/* ��ͼ���� */
	public static final String ITEM_DCZD_DM = "dczd_dm";			/* ������루���ã� */
	public static final String ITEM_TABLE_NO = "table_no";			/* ��ID�����ŷָ��� */
	public static final String ITEM_COLUMN_NO = "column_no";			/* �ֶ�ID�����ŷָ��� */
	public static final String ITEM_CREATE_DATE = "create_date";			/* �������� */
	public static final String ITEM_CREATE_BY = "create_by";			/* ������ */
	public static final String ITEM_VIEW_DESC = "view_desc";			/* ��ͼ���� */
	public static final String ITEM_VIEW_CODE = "view_code";			/* ��ͼ���� */
	public static final String ITEM_MAX_RECORDS = "max_records";			/* ����¼�������ã� */
	public static final String ITEM_VIEW_TYPE = "view_type";			/* ��ͼ���� */
	public static final String ITEM_VIEW_ORDER = "view_order";			/* ��ͼ˳�� */

	public VoSysDbView(DataBus value)
	{
		super(value);
	}

	public VoSysDbView()
	{
		super();
	}

	/* ��ͼID */
	public String getSys_db_view_id()
	{
		return getValue( ITEM_SYS_DB_VIEW_ID );
	}

	public void setSys_db_view_id( String sys_db_view_id1 )
	{
		setValue( ITEM_SYS_DB_VIEW_ID, sys_db_view_id1 );
	}

	/* ��ͼ���� */
	public String getView_name()
	{
		return getValue( ITEM_VIEW_NAME );
	}

	public void setView_name( String view_name1 )
	{
		setValue( ITEM_VIEW_NAME, view_name1 );
	}

	/* ������루���ã� */
	public String getDczd_dm()
	{
		return getValue( ITEM_DCZD_DM );
	}

	public void setDczd_dm( String dczd_dm1 )
	{
		setValue( ITEM_DCZD_DM, dczd_dm1 );
	}

	/* ��ID�����ŷָ��� */
	public String getTable_no()
	{
		return getValue( ITEM_TABLE_NO );
	}

	public void setTable_no( String table_no1 )
	{
		setValue( ITEM_TABLE_NO, table_no1 );
	}

	/* �ֶ�ID�����ŷָ��� */
	public String getColumn_no()
	{
		return getValue( ITEM_COLUMN_NO );
	}

	public void setColumn_no( String column_no1 )
	{
		setValue( ITEM_COLUMN_NO, column_no1 );
	}

	/* �������� */
	public String getCreate_date()
	{
		return getValue( ITEM_CREATE_DATE );
	}

	public void setCreate_date( String create_date1 )
	{
		setValue( ITEM_CREATE_DATE, create_date1 );
	}

	/* ������ */
	public String getCreate_by()
	{
		return getValue( ITEM_CREATE_BY );
	}

	public void setCreate_by( String create_by1 )
	{
		setValue( ITEM_CREATE_BY, create_by1 );
	}

	/* ��ͼ���� */
	public String getView_desc()
	{
		return getValue( ITEM_VIEW_DESC );
	}

	public void setView_desc( String view_desc1 )
	{
		setValue( ITEM_VIEW_DESC, view_desc1 );
	}

	/* ��ͼ���� */
	public String getView_code()
	{
		return getValue( ITEM_VIEW_CODE );
	}

	public void setView_code( String view_code1 )
	{
		setValue( ITEM_VIEW_CODE, view_code1 );
	}

	/* ����¼�������ã� */
	public String getMax_records()
	{
		return getValue( ITEM_MAX_RECORDS );
	}

	public void setMax_records( String max_records1 )
	{
		setValue( ITEM_MAX_RECORDS, max_records1 );
	}

	/* ��ͼ���� */
	public String getView_type()
	{
		return getValue( ITEM_VIEW_TYPE );
	}

	public void setView_type( String view_type1 )
	{
		setValue( ITEM_VIEW_TYPE, view_type1 );
	}

	/* ��ͼ˳�� */
	public String getView_order()
	{
		return getValue( ITEM_VIEW_ORDER );
	}

	public void setView_order( String view_order1 )
	{
		setValue( ITEM_VIEW_ORDER, view_order1 );
	}

}

