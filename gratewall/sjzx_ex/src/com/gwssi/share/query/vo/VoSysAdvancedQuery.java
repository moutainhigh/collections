package com.gwssi.share.query.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_advanced_query]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysAdvancedQuery extends VoBase
{
	private static final long serialVersionUID = 200806261658150002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_ADVANCED_QUERY_ID = "sys_advanced_query_id" ;	/* �߼���ѯ��� */
	public static final String ITEM_NAME = "name" ;					/* �߼���ѯ�������� */
	public static final String ITEM_SYS_ADVANCED_QUERY_TABLE_ID = "sys_advanced_query_table_id" ;	/* �߼���ѯ�������� */
	public static final String ITEM_TABLE_NO = "table_no" ;			/* ���ݱ��������� */
	public static final String ITEM_QUERY_SQL = "query_sql" ;		/* �߼���ѯSQL */
	public static final String ITEM_CREATE_BY = "create_by" ;		/* ������ */
	public static final String ITEM_CREATE_DATE = "create_date" ;	/* �������� */
	public static final String ITEM_LAST_EXEC_DATE = "last_exec_date" ;	/* ���ִ������ */
	public static final String ITEM_EXEC_TOTAL = "exec_total" ;		/* ִ�д��� */
	public static final String ITEM_SQL_STEP_1 = "sql_step_1" ;		/* ��һ���ύ��SQL */
	public static final String ITEM_SQL_STEP_2 = "sql_step_2" ;		/* �ڶ����ύ��SQL */
	public static final String ITEM_SQL_STEP_3 = "sql_step_3" ;		/* �������ύ��SQL */
	public static final String ITEM_DCZD_DM = "dczd_dm" ;			/* ������ */
	
	/**
	 * ���캯��
	 */
	public VoSysAdvancedQuery()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysAdvancedQuery(DataBus value)
	{
		super(value);
	}
	
	/* �߼���ѯ��� : String */
	public String getSys_advanced_query_id()
	{
		return getValue( ITEM_SYS_ADVANCED_QUERY_ID );
	}

	public void setSys_advanced_query_id( String sys_advanced_query_id1 )
	{
		setValue( ITEM_SYS_ADVANCED_QUERY_ID, sys_advanced_query_id1 );
	}

	/* �߼���ѯ�������� : String */
	public String getName()
	{
		return getValue( ITEM_NAME );
	}

	public void setName( String name1 )
	{
		setValue( ITEM_NAME, name1 );
	}

	/* �߼���ѯ�������� : String */
	public String getSys_advanced_query_table_id()
	{
		return getValue( ITEM_SYS_ADVANCED_QUERY_TABLE_ID );
	}

	public void setSys_advanced_query_table_id( String sys_advanced_query_table_id1 )
	{
		setValue( ITEM_SYS_ADVANCED_QUERY_TABLE_ID, sys_advanced_query_table_id1 );
	}

	/* ���ݱ��������� : String */
	public String getTable_no()
	{
		return getValue( ITEM_TABLE_NO );
	}

	public void setTable_no( String table_no1 )
	{
		setValue( ITEM_TABLE_NO, table_no1 );
	}

	/* �߼���ѯSQL : String */
	public String getQuery_sql()
	{
		return getValue( ITEM_QUERY_SQL );
	}

	public void setQuery_sql( String query_sql1 )
	{
		setValue( ITEM_QUERY_SQL, query_sql1 );
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

	/* �������� : String */
	public String getCreate_date()
	{
		return getValue( ITEM_CREATE_DATE );
	}

	public void setCreate_date( String create_date1 )
	{
		setValue( ITEM_CREATE_DATE, create_date1 );
	}

	/* ���ִ������ : String */
	public String getLast_exec_date()
	{
		return getValue( ITEM_LAST_EXEC_DATE );
	}

	public void setLast_exec_date( String last_exec_date1 )
	{
		setValue( ITEM_LAST_EXEC_DATE, last_exec_date1 );
	}

	/* ִ�д��� : String */
	public String getExec_total()
	{
		return getValue( ITEM_EXEC_TOTAL );
	}

	public void setExec_total( String exec_total1 )
	{
		setValue( ITEM_EXEC_TOTAL, exec_total1 );
	}

	/* ��һ���ύ��SQL : String */
	public String getSql_step_1()
	{
		return getValue( ITEM_SQL_STEP_1 );
	}

	public void setSql_step_1( String sql_step_11 )
	{
		setValue( ITEM_SQL_STEP_1, sql_step_11 );
	}

	/* �ڶ����ύ��SQL : String */
	public String getSql_step_2()
	{
		return getValue( ITEM_SQL_STEP_2 );
	}

	public void setSql_step_2( String sql_step_21 )
	{
		setValue( ITEM_SQL_STEP_2, sql_step_21 );
	}

	/* �������ύ��SQL : String */
	public String getSql_step_3()
	{
		return getValue( ITEM_SQL_STEP_3 );
	}

	public void setSql_step_3( String sql_step_31 )
	{
		setValue( ITEM_SQL_STEP_3, sql_step_31 );
	}

	/* ������ : String */
	public String getDczd_dm()
	{
		return getValue( ITEM_DCZD_DM );
	}

	public void setDczd_dm( String dczd_dm1 )
	{
		setValue( ITEM_DCZD_DM, dczd_dm1 );
	}

}

