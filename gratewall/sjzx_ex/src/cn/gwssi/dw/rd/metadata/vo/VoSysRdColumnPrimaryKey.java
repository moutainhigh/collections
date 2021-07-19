package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_column]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdColumnPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201205071133200012L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_COLUMN_ID = "sys_rd_column_id" ;	/* 字段ID */
	
	/**
	 * 构造函数
	 */
	public VoSysRdColumnPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdColumnPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 字段ID : String */
	public String getSys_rd_column_id()
	{
		return getValue( ITEM_SYS_RD_COLUMN_ID );
	}

	public void setSys_rd_column_id( String sys_rd_column_id1 )
	{
		setValue( ITEM_SYS_RD_COLUMN_ID, sys_rd_column_id1 );
	}

}

