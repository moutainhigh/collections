package com.gwssi.dw.runmgr.db.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoSysDbView extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_DB_VIEW_ID = "sys_db_view_id";			/* 视图ID */
	public static final String ITEM_VIEW_NAME = "view_name";			/* 视图名称 */
	public static final String ITEM_DCZD_DM = "dczd_dm";			/* 主题代码（备用） */
	public static final String ITEM_TABLE_NO = "table_no";			/* 表ID（逗号分隔） */
	public static final String ITEM_COLUMN_NO = "column_no";			/* 字段ID（逗号分隔） */
	public static final String ITEM_CREATE_DATE = "create_date";			/* 创建日期 */
	public static final String ITEM_CREATE_BY = "create_by";			/* 创建人 */
	public static final String ITEM_VIEW_DESC = "view_desc";			/* 视图描述 */
	public static final String ITEM_VIEW_CODE = "view_code";			/* 视图代码 */
	public static final String ITEM_MAX_RECORDS = "max_records";			/* 最大记录数（无用） */
	public static final String ITEM_VIEW_TYPE = "view_type";			/* 视图类型 */
	public static final String ITEM_VIEW_ORDER = "view_order";			/* 视图顺序 */

	public VoSysDbView(DataBus value)
	{
		super(value);
	}

	public VoSysDbView()
	{
		super();
	}

	/* 视图ID */
	public String getSys_db_view_id()
	{
		return getValue( ITEM_SYS_DB_VIEW_ID );
	}

	public void setSys_db_view_id( String sys_db_view_id1 )
	{
		setValue( ITEM_SYS_DB_VIEW_ID, sys_db_view_id1 );
	}

	/* 视图名称 */
	public String getView_name()
	{
		return getValue( ITEM_VIEW_NAME );
	}

	public void setView_name( String view_name1 )
	{
		setValue( ITEM_VIEW_NAME, view_name1 );
	}

	/* 主题代码（备用） */
	public String getDczd_dm()
	{
		return getValue( ITEM_DCZD_DM );
	}

	public void setDczd_dm( String dczd_dm1 )
	{
		setValue( ITEM_DCZD_DM, dczd_dm1 );
	}

	/* 表ID（逗号分隔） */
	public String getTable_no()
	{
		return getValue( ITEM_TABLE_NO );
	}

	public void setTable_no( String table_no1 )
	{
		setValue( ITEM_TABLE_NO, table_no1 );
	}

	/* 字段ID（逗号分隔） */
	public String getColumn_no()
	{
		return getValue( ITEM_COLUMN_NO );
	}

	public void setColumn_no( String column_no1 )
	{
		setValue( ITEM_COLUMN_NO, column_no1 );
	}

	/* 创建日期 */
	public String getCreate_date()
	{
		return getValue( ITEM_CREATE_DATE );
	}

	public void setCreate_date( String create_date1 )
	{
		setValue( ITEM_CREATE_DATE, create_date1 );
	}

	/* 创建人 */
	public String getCreate_by()
	{
		return getValue( ITEM_CREATE_BY );
	}

	public void setCreate_by( String create_by1 )
	{
		setValue( ITEM_CREATE_BY, create_by1 );
	}

	/* 视图描述 */
	public String getView_desc()
	{
		return getValue( ITEM_VIEW_DESC );
	}

	public void setView_desc( String view_desc1 )
	{
		setValue( ITEM_VIEW_DESC, view_desc1 );
	}

	/* 视图代码 */
	public String getView_code()
	{
		return getValue( ITEM_VIEW_CODE );
	}

	public void setView_code( String view_code1 )
	{
		setValue( ITEM_VIEW_CODE, view_code1 );
	}

	/* 最大记录数（无用） */
	public String getMax_records()
	{
		return getValue( ITEM_MAX_RECORDS );
	}

	public void setMax_records( String max_records1 )
	{
		setValue( ITEM_MAX_RECORDS, max_records1 );
	}

	/* 视图类型 */
	public String getView_type()
	{
		return getValue( ITEM_VIEW_TYPE );
	}

	public void setView_type( String view_type1 )
	{
		setValue( ITEM_VIEW_TYPE, view_type1 );
	}

	/* 视图顺序 */
	public String getView_order()
	{
		return getValue( ITEM_VIEW_ORDER );
	}

	public void setView_order( String view_order1 )
	{
		setValue( ITEM_VIEW_ORDER, view_order1 );
	}

}

