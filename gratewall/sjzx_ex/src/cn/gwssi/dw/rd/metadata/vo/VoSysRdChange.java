package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_change]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdChange extends VoBase
{
	private static final long serialVersionUID = 201205091047410002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_CHANGE_ID = "sys_rd_change_id" ;	/* ������ */
	public static final String ITEM_SYS_RD_DATA_SOURCE_ID = "sys_rd_data_source_id" ;	/* ����ԴID */
	public static final String ITEM_DB_NAME = "db_name" ;			/* ����Դ���� */
	public static final String ITEM_DB_USERNAME = "db_username" ;	/* �û����� */
	public static final String ITEM_TABLE_NAME = "table_name" ;		/* ����� */
	public static final String ITEM_TABLE_NAME_CN = "table_name_cn" ;	/* ����������� */
	public static final String ITEM_COLUMN_NAME = "column_name" ;	/* �ֶ� */
	public static final String ITEM_COLUMN_NAME_CN = "column_name_cn" ;	/* �ֶ������� */
	public static final String ITEM_CHANGE_ITEM = "change_item" ;	/* ������� */
	public static final String ITEM_CHANGE_BEFORE = "change_before" ;	/* ���ǰ���� */
	public static final String ITEM_CHANGE_AFTER = "change_after" ;	/* ��������� */
	public static final String ITEM_CHANGE_RESULT = "change_result" ;	/* ������ */
	public static final String ITEM_CHANGE_OPRATER = "change_oprater" ;	/* ����� */
	public static final String ITEM_CHANGE_TIME = "change_time" ;	/* ���ʱ�� */
	public static final String ITEM_CHANGE_REASON = "change_reason" ;	/* ���ԭ�� */
	public static final String ITEM_TIMESTAMP = "timestamp" ;		/* ʱ��� */
	
	/**
	 * ���캯��
	 */
	public VoSysRdChange()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdChange(DataBus value)
	{
		super(value);
	}
	
	/* ������ : String */
	public String getSys_rd_change_id()
	{
		return getValue( ITEM_SYS_RD_CHANGE_ID );
	}

	public void setSys_rd_change_id( String sys_rd_change_id1 )
	{
		setValue( ITEM_SYS_RD_CHANGE_ID, sys_rd_change_id1 );
	}

	/* ����ԴID : String */
	public String getSys_rd_data_source_id()
	{
		return getValue( ITEM_SYS_RD_DATA_SOURCE_ID );
	}

	public void setSys_rd_data_source_id( String sys_rd_data_source_id1 )
	{
		setValue( ITEM_SYS_RD_DATA_SOURCE_ID, sys_rd_data_source_id1 );
	}

	/* ����Դ���� : String */
	public String getDb_name()
	{
		return getValue( ITEM_DB_NAME );
	}

	public void setDb_name( String db_name1 )
	{
		setValue( ITEM_DB_NAME, db_name1 );
	}

	/* �û����� : String */
	public String getDb_username()
	{
		return getValue( ITEM_DB_USERNAME );
	}

	public void setDb_username( String db_username1 )
	{
		setValue( ITEM_DB_USERNAME, db_username1 );
	}

	/* ����� : String */
	public String getTable_name()
	{
		return getValue( ITEM_TABLE_NAME );
	}

	public void setTable_name( String table_name1 )
	{
		setValue( ITEM_TABLE_NAME, table_name1 );
	}

	/* ����������� : String */
	public String getTable_name_cn()
	{
		return getValue( ITEM_TABLE_NAME_CN );
	}

	public void setTable_name_cn( String table_name_cn1 )
	{
		setValue( ITEM_TABLE_NAME_CN, table_name_cn1 );
	}

	/* �ֶ� : String */
	public String getColumn_name()
	{
		return getValue( ITEM_COLUMN_NAME );
	}

	public void setColumn_name( String column_name1 )
	{
		setValue( ITEM_COLUMN_NAME, column_name1 );
	}

	/* �ֶ������� : String */
	public String getColumn_name_cn()
	{
		return getValue( ITEM_COLUMN_NAME_CN );
	}

	public void setColumn_name_cn( String column_name_cn1 )
	{
		setValue( ITEM_COLUMN_NAME_CN, column_name_cn1 );
	}

	/* ������� : String */
	public String getChange_item()
	{
		return getValue( ITEM_CHANGE_ITEM );
	}

	public void setChange_item( String change_item1 )
	{
		setValue( ITEM_CHANGE_ITEM, change_item1 );
	}

	/* ���ǰ���� : String */
	public String getChange_before()
	{
		return getValue( ITEM_CHANGE_BEFORE );
	}

	public void setChange_before( String change_before1 )
	{
		setValue( ITEM_CHANGE_BEFORE, change_before1 );
	}

	/* ��������� : String */
	public String getChange_after()
	{
		return getValue( ITEM_CHANGE_AFTER );
	}

	public void setChange_after( String change_after1 )
	{
		setValue( ITEM_CHANGE_AFTER, change_after1 );
	}

	/* ������ : String */
	public String getChange_result()
	{
		return getValue( ITEM_CHANGE_RESULT );
	}

	public void setChange_result( String change_result1 )
	{
		setValue( ITEM_CHANGE_RESULT, change_result1 );
	}

	/* ����� : String */
	public String getChange_oprater()
	{
		return getValue( ITEM_CHANGE_OPRATER );
	}

	public void setChange_oprater( String change_oprater1 )
	{
		setValue( ITEM_CHANGE_OPRATER, change_oprater1 );
	}

	/* ���ʱ�� : String */
	public String getChange_time()
	{
		return getValue( ITEM_CHANGE_TIME );
	}

	public void setChange_time( String change_time1 )
	{
		setValue( ITEM_CHANGE_TIME, change_time1 );
	}

	/* ���ԭ�� : String */
	public String getChange_reason()
	{
		return getValue( ITEM_CHANGE_REASON );
	}

	public void setChange_reason( String change_reason1 )
	{
		setValue( ITEM_CHANGE_REASON, change_reason1 );
	}

	/* ʱ��� : String */
	public String getTimestamp()
	{
		return getValue( ITEM_TIMESTAMP );
	}

	public void setTimestamp( String timestamp1 )
	{
		setValue( ITEM_TIMESTAMP, timestamp1 );
	}

}

