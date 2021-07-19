package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_change]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdChange extends VoBase
{
	private static final long serialVersionUID = 201205091047410002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_CHANGE_ID = "sys_rd_change_id" ;	/* 表主键 */
	public static final String ITEM_SYS_RD_DATA_SOURCE_ID = "sys_rd_data_source_id" ;	/* 数据源ID */
	public static final String ITEM_DB_NAME = "db_name" ;			/* 数据源名称 */
	public static final String ITEM_DB_USERNAME = "db_username" ;	/* 用户名称 */
	public static final String ITEM_TABLE_NAME = "table_name" ;		/* 物理表 */
	public static final String ITEM_TABLE_NAME_CN = "table_name_cn" ;	/* 物理表中文名 */
	public static final String ITEM_COLUMN_NAME = "column_name" ;	/* 字段 */
	public static final String ITEM_COLUMN_NAME_CN = "column_name_cn" ;	/* 字段中文名 */
	public static final String ITEM_CHANGE_ITEM = "change_item" ;	/* 变更类型 */
	public static final String ITEM_CHANGE_BEFORE = "change_before" ;	/* 变更前内容 */
	public static final String ITEM_CHANGE_AFTER = "change_after" ;	/* 变更后内容 */
	public static final String ITEM_CHANGE_RESULT = "change_result" ;	/* 处理结果 */
	public static final String ITEM_CHANGE_OPRATER = "change_oprater" ;	/* 变更人 */
	public static final String ITEM_CHANGE_TIME = "change_time" ;	/* 变更时间 */
	public static final String ITEM_CHANGE_REASON = "change_reason" ;	/* 变更原因 */
	public static final String ITEM_TIMESTAMP = "timestamp" ;		/* 时间戳 */
	
	/**
	 * 构造函数
	 */
	public VoSysRdChange()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdChange(DataBus value)
	{
		super(value);
	}
	
	/* 表主键 : String */
	public String getSys_rd_change_id()
	{
		return getValue( ITEM_SYS_RD_CHANGE_ID );
	}

	public void setSys_rd_change_id( String sys_rd_change_id1 )
	{
		setValue( ITEM_SYS_RD_CHANGE_ID, sys_rd_change_id1 );
	}

	/* 数据源ID : String */
	public String getSys_rd_data_source_id()
	{
		return getValue( ITEM_SYS_RD_DATA_SOURCE_ID );
	}

	public void setSys_rd_data_source_id( String sys_rd_data_source_id1 )
	{
		setValue( ITEM_SYS_RD_DATA_SOURCE_ID, sys_rd_data_source_id1 );
	}

	/* 数据源名称 : String */
	public String getDb_name()
	{
		return getValue( ITEM_DB_NAME );
	}

	public void setDb_name( String db_name1 )
	{
		setValue( ITEM_DB_NAME, db_name1 );
	}

	/* 用户名称 : String */
	public String getDb_username()
	{
		return getValue( ITEM_DB_USERNAME );
	}

	public void setDb_username( String db_username1 )
	{
		setValue( ITEM_DB_USERNAME, db_username1 );
	}

	/* 物理表 : String */
	public String getTable_name()
	{
		return getValue( ITEM_TABLE_NAME );
	}

	public void setTable_name( String table_name1 )
	{
		setValue( ITEM_TABLE_NAME, table_name1 );
	}

	/* 物理表中文名 : String */
	public String getTable_name_cn()
	{
		return getValue( ITEM_TABLE_NAME_CN );
	}

	public void setTable_name_cn( String table_name_cn1 )
	{
		setValue( ITEM_TABLE_NAME_CN, table_name_cn1 );
	}

	/* 字段 : String */
	public String getColumn_name()
	{
		return getValue( ITEM_COLUMN_NAME );
	}

	public void setColumn_name( String column_name1 )
	{
		setValue( ITEM_COLUMN_NAME, column_name1 );
	}

	/* 字段中文名 : String */
	public String getColumn_name_cn()
	{
		return getValue( ITEM_COLUMN_NAME_CN );
	}

	public void setColumn_name_cn( String column_name_cn1 )
	{
		setValue( ITEM_COLUMN_NAME_CN, column_name_cn1 );
	}

	/* 变更类型 : String */
	public String getChange_item()
	{
		return getValue( ITEM_CHANGE_ITEM );
	}

	public void setChange_item( String change_item1 )
	{
		setValue( ITEM_CHANGE_ITEM, change_item1 );
	}

	/* 变更前内容 : String */
	public String getChange_before()
	{
		return getValue( ITEM_CHANGE_BEFORE );
	}

	public void setChange_before( String change_before1 )
	{
		setValue( ITEM_CHANGE_BEFORE, change_before1 );
	}

	/* 变更后内容 : String */
	public String getChange_after()
	{
		return getValue( ITEM_CHANGE_AFTER );
	}

	public void setChange_after( String change_after1 )
	{
		setValue( ITEM_CHANGE_AFTER, change_after1 );
	}

	/* 处理结果 : String */
	public String getChange_result()
	{
		return getValue( ITEM_CHANGE_RESULT );
	}

	public void setChange_result( String change_result1 )
	{
		setValue( ITEM_CHANGE_RESULT, change_result1 );
	}

	/* 变更人 : String */
	public String getChange_oprater()
	{
		return getValue( ITEM_CHANGE_OPRATER );
	}

	public void setChange_oprater( String change_oprater1 )
	{
		setValue( ITEM_CHANGE_OPRATER, change_oprater1 );
	}

	/* 变更时间 : String */
	public String getChange_time()
	{
		return getValue( ITEM_CHANGE_TIME );
	}

	public void setChange_time( String change_time1 )
	{
		setValue( ITEM_CHANGE_TIME, change_time1 );
	}

	/* 变更原因 : String */
	public String getChange_reason()
	{
		return getValue( ITEM_CHANGE_REASON );
	}

	public void setChange_reason( String change_reason1 )
	{
		setValue( ITEM_CHANGE_REASON, change_reason1 );
	}

	/* 时间戳 : String */
	public String getTimestamp()
	{
		return getValue( ITEM_TIMESTAMP );
	}

	public void setTimestamp( String timestamp1 )
	{
		setValue( ITEM_TIMESTAMP, timestamp1 );
	}

}

