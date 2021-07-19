package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_unclaim_column]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdUnclaimColumnPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201205071126260004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_UNCLAIM_COLUMN_ID = "sys_rd_unclaim_column_id" ;	/* 表主键 */
	
	/**
	 * 构造函数
	 */
	public VoSysRdUnclaimColumnPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdUnclaimColumnPrimaryKey(DataBus value)
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

}

