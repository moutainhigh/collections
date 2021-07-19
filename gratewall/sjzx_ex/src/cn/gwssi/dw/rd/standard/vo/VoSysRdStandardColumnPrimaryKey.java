package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_standard_column]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdStandardColumnPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201205031749460008L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_STANDARD_COLUMN_ID = "sys_rd_standard_column_id" ;	/* 指标项ID */
	
	/**
	 * 构造函数
	 */
	public VoSysRdStandardColumnPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdStandardColumnPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 指标项ID : String */
	public String getSys_rd_standard_column_id()
	{
		return getValue( ITEM_SYS_RD_STANDARD_COLUMN_ID );
	}

	public void setSys_rd_standard_column_id( String sys_rd_standard_column_id1 )
	{
		setValue( ITEM_SYS_RD_STANDARD_COLUMN_ID, sys_rd_standard_column_id1 );
	}

}

