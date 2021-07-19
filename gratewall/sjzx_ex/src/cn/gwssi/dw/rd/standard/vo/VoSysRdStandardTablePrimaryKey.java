package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_standard_table]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdStandardTablePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201205031723560004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_STANDARD_TABLE_ID = "sys_rd_standard_table_id" ;	/* 实体信息ID */
	
	/**
	 * 构造函数
	 */
	public VoSysRdStandardTablePrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdStandardTablePrimaryKey(DataBus value)
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

}

