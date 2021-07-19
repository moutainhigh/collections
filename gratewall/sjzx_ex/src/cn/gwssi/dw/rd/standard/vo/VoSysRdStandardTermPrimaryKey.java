package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_standard_term]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdStandardTermPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201205021622440004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_STANDAR_TERM_ID = "sys_rd_standar_term_id" ;	/* 术语ID */
	
	/**
	 * 构造函数
	 */
	public VoSysRdStandardTermPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdStandardTermPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 术语ID : String */
	public String getSys_rd_standar_term_id()
	{
		return getValue( ITEM_SYS_RD_STANDAR_TERM_ID );
	}

	public void setSys_rd_standar_term_id( String sys_rd_standar_term_id1 )
	{
		setValue( ITEM_SYS_RD_STANDAR_TERM_ID, sys_rd_standar_term_id1 );
	}

}

