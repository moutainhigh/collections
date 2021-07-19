package com.gwssi.dw.runmgr.db.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoSysDbConfig extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_DB_CONFIG_ID = "sys_db_config_id";			/* ����ID */
	public static final String ITEM_SYS_DB_VIEW_ID = "sys_db_view_id";			/* ��ͼID */
	public static final String ITEM_SYS_DB_USER_ID = "sys_db_user_id";			/* ����ID */
	public static final String ITEM_CONFIG_NAME = "config_name";			/* �������� */
	public static final String ITEM_PERMIT_COLUMN = "permit_column";			/* �����ֶ�ID�����ŷָ��� */
	public static final String ITEM_CREATE_DATE = "create_date";			/* �������� */
	public static final String ITEM_CREATE_BY = "create_by";			/* ������ */
	public static final String ITEM_CONFIG_ORDER = "config_order";			/* ����˳�� */
	public static final String ITEM_QUERY_SQL = "query_sql";			/* ��ѯsql */

	public VoSysDbConfig(DataBus value)
	{
		super(value);
	}

	public VoSysDbConfig()
	{
		super();
	}

	/* ����ID */
	public String getSys_db_config_id()
	{
		return getValue( ITEM_SYS_DB_CONFIG_ID );
	}

	public void setSys_db_config_id( String sys_db_config_id1 )
	{
		setValue( ITEM_SYS_DB_CONFIG_ID, sys_db_config_id1 );
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

	/* ����ID */
	public String getSys_db_user_id()
	{
		return getValue( ITEM_SYS_DB_USER_ID );
	}

	public void setSys_db_user_id( String sys_db_user_id1 )
	{
		setValue( ITEM_SYS_DB_USER_ID, sys_db_user_id1 );
	}

	/* �������� */
	public String getConfig_name()
	{
		return getValue( ITEM_CONFIG_NAME );
	}

	public void setConfig_name( String config_name1 )
	{
		setValue( ITEM_CONFIG_NAME, config_name1 );
	}

	/* �����ֶ�ID�����ŷָ��� */
	public String getPermit_column()
	{
		return getValue( ITEM_PERMIT_COLUMN );
	}

	public void setPermit_column( String permit_column1 )
	{
		setValue( ITEM_PERMIT_COLUMN, permit_column1 );
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

	/* ����˳�� */
	public String getConfig_order()
	{
		return getValue( ITEM_CONFIG_ORDER );
	}

	public void setConfig_order( String config_order1 )
	{
		setValue( ITEM_CONFIG_ORDER, config_order1 );
	}

	/* ��ѯsql */
	public String getQuery_sql()
	{
		return getValue( ITEM_QUERY_SQL );
	}

	public void setQuery_sql( String query_sql1 )
	{
		setValue( ITEM_QUERY_SQL, query_sql1 );
	}

}

