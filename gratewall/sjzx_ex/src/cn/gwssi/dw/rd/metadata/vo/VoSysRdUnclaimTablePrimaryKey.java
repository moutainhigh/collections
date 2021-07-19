package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_unclaim_table]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdUnclaimTablePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201205020916490004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_UNCLAIM_TABLE_ID = "sys_rd_unclaim_table_id" ;	/* 未认领表ID */
	
	/**
	 * 构造函数
	 */
	public VoSysRdUnclaimTablePrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdUnclaimTablePrimaryKey(DataBus value)
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

}

