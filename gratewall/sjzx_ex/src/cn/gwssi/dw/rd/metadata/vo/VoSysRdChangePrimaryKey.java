package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_change]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdChangePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201205091047410004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_CHANGE_ID = "sys_rd_change_id" ;	/* 表主键 */
	
	/**
	 * 构造函数
	 */
	public VoSysRdChangePrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdChangePrimaryKey(DataBus value)
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

}

