package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_unclaim_column]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdUnclaimColumn extends VoBase
{
	private static final long serialVersionUID = 201205071126260002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_UNCLAIM_COLUMN_ID = "sys_rd_unclaim_column_id" ;	/* 表主键 */
	public static final String ITEM_SYS_RD_UNCLAIM_TABLE_ID = "sys_rd_unclaim_table_id" ;	/* 物理表ID */
	public static final String ITEM_SYS_RD_DATA_SOURCE_ID = "sys_rd_data_source_id" ;	/* 数据源ID */
	public static final String ITEM_UNCLAIM_TAB_CODE = "unclaim_tab_code" ;	/* 未认领表名 */
	public static final String ITEM_UNCLAIM_COLUMN_CODE = "unclaim_column_code" ;	/* 字段代码 */
	public static final String ITEM_UNCLAIM_COLUMN_NAME = "unclaim_column_name" ;	/* 字段名称 */
	public static final String ITEM_UNCLAIM_COLUMN_TYPE = "unclaim_column_type" ;	/* 字段类型 */
	public static final String ITEM_UNCLAIM_COLUMN_LENGTH = "unclaim_column_length" ;	/* 字段长度 */
	public static final String ITEM_IS_PRIMARY_KEY = "is_primary_key" ;	/* 是否主键 */
	public static final String ITEM_IS_INDEX = "is_index" ;			/* 是否索引 */
	public static final String ITEM_IS_NULL = "is_null" ;			/* 是否允许为空 */
	public static final String ITEM_DEFAULT_VALUE = "default_value" ;	/* 默认值 */
	public static final String ITEM_REMARKS = "remarks" ;			/* 备注 */
	public static final String ITEM_TIMESTAMP = "timestamp" ;		/* 时间戳 */
	
	/**
	 * 构造函数
	 */
	public VoSysRdUnclaimColumn()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdUnclaimColumn(DataBus value)
	{
		super(value);
	}
	
	/* 表主键 : String */
	public String getSys_rd_unclaim_column_id()
	{
		return getValue( ITEM_SYS_RD_UNCLAIM_COLUMN_ID );
	}

	public void setSys_rd_unclaim_column_id( String sys_rd_unclaim_column_id1 )
	{
		setValue( ITEM_SYS_RD_UNCLAIM_COLUMN_ID, sys_rd_unclaim_column_id1 );
	}

	/* 物理表ID : String */
	public String getSys_rd_unclaim_table_id()
	{
		return getValue( ITEM_SYS_RD_UNCLAIM_TABLE_ID );
	}

	public void setSys_rd_unclaim_table_id( String sys_rd_unclaim_table_id1 )
	{
		setValue( ITEM_SYS_RD_UNCLAIM_TABLE_ID, sys_rd_unclaim_table_id1 );
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

	/* 未认领表名 : String */
	public String getUnclaim_tab_code()
	{
		return getValue( ITEM_UNCLAIM_TAB_CODE );
	}

	public void setUnclaim_tab_code( String unclaim_tab_code1 )
	{
		setValue( ITEM_UNCLAIM_TAB_CODE, unclaim_tab_code1 );
	}

	/* 字段代码 : String */
	public String getUnclaim_column_code()
	{
		return getValue( ITEM_UNCLAIM_COLUMN_CODE );
	}

	public void setUnclaim_column_code( String unclaim_column_code1 )
	{
		setValue( ITEM_UNCLAIM_COLUMN_CODE, unclaim_column_code1 );
	}

	/* 字段名称 : String */
	public String getUnclaim_column_name()
	{
		return getValue( ITEM_UNCLAIM_COLUMN_NAME );
	}

	public void setUnclaim_column_name( String unclaim_column_name1 )
	{
		setValue( ITEM_UNCLAIM_COLUMN_NAME, unclaim_column_name1 );
	}

	/* 字段类型 : String */
	public String getUnclaim_column_type()
	{
		return getValue( ITEM_UNCLAIM_COLUMN_TYPE );
	}

	public void setUnclaim_column_type( String unclaim_column_type1 )
	{
		setValue( ITEM_UNCLAIM_COLUMN_TYPE, unclaim_column_type1 );
	}

	/* 字段长度 : String */
	public String getUnclaim_column_length()
	{
		return getValue( ITEM_UNCLAIM_COLUMN_LENGTH );
	}

	public void setUnclaim_column_length( String unclaim_column_length1 )
	{
		setValue( ITEM_UNCLAIM_COLUMN_LENGTH, unclaim_column_length1 );
	}

	/* 是否主键 : String */
	public String getIs_primary_key()
	{
		return getValue( ITEM_IS_PRIMARY_KEY );
	}

	public void setIs_primary_key( String is_primary_key1 )
	{
		setValue( ITEM_IS_PRIMARY_KEY, is_primary_key1 );
	}

	/* 是否索引 : String */
	public String getIs_index()
	{
		return getValue( ITEM_IS_INDEX );
	}

	public void setIs_index( String is_index1 )
	{
		setValue( ITEM_IS_INDEX, is_index1 );
	}

	/* 是否允许为空 : String */
	public String getIs_null()
	{
		return getValue( ITEM_IS_NULL );
	}

	public void setIs_null( String is_null1 )
	{
		setValue( ITEM_IS_NULL, is_null1 );
	}

	/* 默认值 : String */
	public String getDefault_value()
	{
		return getValue( ITEM_DEFAULT_VALUE );
	}

	public void setDefault_value( String default_value1 )
	{
		setValue( ITEM_DEFAULT_VALUE, default_value1 );
	}

	/* 备注 : String */
	public String getRemarks()
	{
		return getValue( ITEM_REMARKS );
	}

	public void setRemarks( String remarks1 )
	{
		setValue( ITEM_REMARKS, remarks1 );
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

