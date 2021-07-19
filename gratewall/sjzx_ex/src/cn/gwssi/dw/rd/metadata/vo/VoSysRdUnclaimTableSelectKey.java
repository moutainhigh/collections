package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_unclaim_table]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdUnclaimTableSelectKey extends VoBase
{
	private static final long serialVersionUID = 201205020916490003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_DATA_SOURCE_ID = "sys_rd_data_source_id" ;	/* 数据源ID */
	public static final String ITEM_UNCLAIM_TABLE_CODE = "unclaim_table_code" ;	/* 未认领表 */
	public static final String ITEM_UNCLAIM_TABLE_NAME = "unclaim_table_name" ;	/* 未认领表名称 */
	public static final String ITEM_OBJECT_SCHEMA = "object_schema" ;	/* 对象模式 */
	
	/**
	 * 构造函数
	 */
	public VoSysRdUnclaimTableSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdUnclaimTableSelectKey(DataBus value)
	{
		super(value);
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

}

