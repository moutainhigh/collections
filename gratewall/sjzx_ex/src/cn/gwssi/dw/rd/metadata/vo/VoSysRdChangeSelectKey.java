package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_change]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdChangeSelectKey extends VoBase
{
	private static final long serialVersionUID = 201205091047410003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_CHANGE_ID = "sys_rd_change_id" ;	/* 表主键 */
	public static final String ITEM_TABLE_NAME = "table_name" ;		/* 物理表 */
	public static final String ITEM_COLUMN_NAME = "column_name" ;	/* 字段 */
	public static final String ITEM_CHANGE_ITEM = "change_item" ;	/* 变更类型 */
	public static final String ITEM_CHANGE_RESULT = "change_result" ;	/* 处理结果 */
	
	/**
	 * 构造函数
	 */
	public VoSysRdChangeSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdChangeSelectKey(DataBus value)
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

	/* 物理表 : String */
	public String getTable_name()
	{
		return getValue( ITEM_TABLE_NAME );
	}

	public void setTable_name( String table_name1 )
	{
		setValue( ITEM_TABLE_NAME, table_name1 );
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

	/* 变更类型 : String */
	public String getChange_item()
	{
		return getValue( ITEM_CHANGE_ITEM );
	}

	public void setChange_item( String change_item1 )
	{
		setValue( ITEM_CHANGE_ITEM, change_item1 );
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

}

