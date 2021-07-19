package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_unclaim_table]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdUnclaimTable extends VoBase
{
	private static final long serialVersionUID = 201205020916490002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_UNCLAIM_TABLE_ID = "sys_rd_unclaim_table_id" ;	/* 未认领表ID */
	public static final String ITEM_SYS_RD_DATA_SOURCE_ID = "sys_rd_data_source_id" ;	/* 数据源ID */
	public static final String ITEM_UNCLAIM_TABLE_CODE = "unclaim_table_code" ;	/* 未认领表 */
	public static final String ITEM_UNCLAIM_TABLE_NAME = "unclaim_table_name" ;	/* 未认领表名称 */
	public static final String ITEM_OBJECT_SCHEMA = "object_schema" ;	/* 对象模式 */
	public static final String ITEM_TB_INDEX_NAME = "tb_index_name" ;	/* 索引名称 */
	public static final String ITEM_TB_INDEX_COLUMNS = "tb_index_columns" ;	/* 索引字段 */
	public static final String ITEM_TB_PK_NAME = "tb_pk_name" ;		/* 主键名 */
	public static final String ITEM_TB_PK_COLUMNS = "tb_pk_columns" ;	/* 主键字段 */
	public static final String ITEM_CUR_RECORD_COUNT = "cur_record_count" ;	/* 当前记录数量 */
	public static final String ITEM_REMARK = "remark" ;				/* 备注 */
	public static final String ITEM_DATA_OBJECT_TYPE = "data_object_type" ;	/* 数据对象类型 */
	public static final String ITEM_TIMESTAMP = "timestamp" ;		/* 时间戳 */
	
	/**
	 * 构造函数
	 */
	public VoSysRdUnclaimTable()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdUnclaimTable(DataBus value)
	{
		super(value);
	}
	
	/* 未认领表ID : String */
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

	/* 未认领表 : String */
	public String getUnclaim_table_code()
	{
		return getValue( ITEM_UNCLAIM_TABLE_CODE );
	}

	public void setUnclaim_table_code( String unclaim_table_code1 )
	{
		setValue( ITEM_UNCLAIM_TABLE_CODE, unclaim_table_code1 );
	}

	/* 未认领表名称 : String */
	public String getUnclaim_table_name()
	{
		return getValue( ITEM_UNCLAIM_TABLE_NAME );
	}

	public void setUnclaim_table_name( String unclaim_table_name1 )
	{
		setValue( ITEM_UNCLAIM_TABLE_NAME, unclaim_table_name1 );
	}

	/* 对象模式 : String */
	public String getObject_schema()
	{
		return getValue( ITEM_OBJECT_SCHEMA );
	}

	public void setObject_schema( String object_schema1 )
	{
		setValue( ITEM_OBJECT_SCHEMA, object_schema1 );
	}

	/* 索引名称 : String */
	public String getTb_index_name()
	{
		return getValue( ITEM_TB_INDEX_NAME );
	}

	public void setTb_index_name( String tb_index_name1 )
	{
		setValue( ITEM_TB_INDEX_NAME, tb_index_name1 );
	}

	/* 索引字段 : String */
	public String getTb_index_columns()
	{
		return getValue( ITEM_TB_INDEX_COLUMNS );
	}

	public void setTb_index_columns( String tb_index_columns1 )
	{
		setValue( ITEM_TB_INDEX_COLUMNS, tb_index_columns1 );
	}

	/* 主键名 : String */
	public String getTb_pk_name()
	{
		return getValue( ITEM_TB_PK_NAME );
	}

	public void setTb_pk_name( String tb_pk_name1 )
	{
		setValue( ITEM_TB_PK_NAME, tb_pk_name1 );
	}

	/* 主键字段 : String */
	public String getTb_pk_columns()
	{
		return getValue( ITEM_TB_PK_COLUMNS );
	}

	public void setTb_pk_columns( String tb_pk_columns1 )
	{
		setValue( ITEM_TB_PK_COLUMNS, tb_pk_columns1 );
	}

	/* 当前记录数量 : String */
	public String getCur_record_count()
	{
		return getValue( ITEM_CUR_RECORD_COUNT );
	}

	public void setCur_record_count( String cur_record_count1 )
	{
		setValue( ITEM_CUR_RECORD_COUNT, cur_record_count1 );
	}

	/* 备注 : String */
	public String getRemark()
	{
		return getValue( ITEM_REMARK );
	}

	public void setRemark( String remark1 )
	{
		setValue( ITEM_REMARK, remark1 );
	}

	/* 数据对象类型 : String */
	public String getData_object_type()
	{
		return getValue( ITEM_DATA_OBJECT_TYPE );
	}

	public void setData_object_type( String data_object_type1 )
	{
		setValue( ITEM_DATA_OBJECT_TYPE, data_object_type1 );
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

