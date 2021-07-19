package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_standard_table]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdStandardTable extends VoBase
{
	private static final long serialVersionUID = 201205031723560002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_STANDARD_TABLE_ID = "sys_rd_standard_table_id" ;	/* 实体信息ID */
	public static final String ITEM_SYS_RD_STANDARD_ID = "sys_rd_standard_id" ;	/* 标准ID */
	public static final String ITEM_STANDARD_NAME = "standard_name" ;	/* 标准名称 */
	public static final String ITEM_TABLE_NAME = "table_name" ;		/* 实体信息名称 */
	public static final String ITEM_TABLE_BELONGS = "table_belongs" ;	/* 所属体系 */
	public static final String ITEM_MEMO = "memo" ;					/* 备注 */
	public static final String ITEM_SORT = "sort" ;					/* 排序号 */
	public static final String ITEM_TIMESTAMP = "timestamp" ;		/* 时间戳 */
	
	/**
	 * 构造函数
	 */
	public VoSysRdStandardTable()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdStandardTable(DataBus value)
	{
		super(value);
	}
	
	/* 实体信息ID : String */
	public String getSys_rd_standard_table_id()
	{
		return getValue( ITEM_SYS_RD_STANDARD_TABLE_ID );
	}

	public void setSys_rd_standard_table_id( String sys_rd_standard_table_id1 )
	{
		setValue( ITEM_SYS_RD_STANDARD_TABLE_ID, sys_rd_standard_table_id1 );
	}

	/* 标准ID : String */
	public String getSys_rd_standard_id()
	{
		return getValue( ITEM_SYS_RD_STANDARD_ID );
	}

	public void setSys_rd_standard_id( String sys_rd_standard_id1 )
	{
		setValue( ITEM_SYS_RD_STANDARD_ID, sys_rd_standard_id1 );
	}

	/* 标准名称 : String */
	public String getStandard_name()
	{
		return getValue( ITEM_STANDARD_NAME );
	}

	public void setStandard_name( String standard_name1 )
	{
		setValue( ITEM_STANDARD_NAME, standard_name1 );
	}

	/* 实体信息名称 : String */
	public String getTable_name()
	{
		return getValue( ITEM_TABLE_NAME );
	}

	public void setTable_name( String table_name1 )
	{
		setValue( ITEM_TABLE_NAME, table_name1 );
	}

	/* 所属体系 : String */
	public String getTable_belongs()
	{
		return getValue( ITEM_TABLE_BELONGS );
	}

	public void setTable_belongs( String table_belongs1 )
	{
		setValue( ITEM_TABLE_BELONGS, table_belongs1 );
	}

	/* 备注 : String */
	public String getMemo()
	{
		return getValue( ITEM_MEMO );
	}

	public void setMemo( String memo1 )
	{
		setValue( ITEM_MEMO, memo1 );
	}

	/* 排序号 : String */
	public String getSort()
	{
		return getValue( ITEM_SORT );
	}

	public void setSort( String sort1 )
	{
		setValue( ITEM_SORT, sort1 );
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

