package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_table]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdTablePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201205031231580004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_TABLE_ID = "sys_rd_table_id" ;	/* 数据表ID */
	
	/**
	 * 构造函数
	 */
	public VoSysRdTablePrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdTablePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 数据表ID : String */
	public String getSys_rd_table_id()
	{
		return getValue( ITEM_SYS_RD_TABLE_ID );
	}

	public void setSys_rd_table_id( String sys_rd_table_id1 )
	{
		setValue( ITEM_SYS_RD_TABLE_ID, sys_rd_table_id1 );
	}

}

