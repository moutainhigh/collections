package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_standard]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdStandardPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201205020221520008L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_STANDARD_ID = "sys_rd_standard_id" ;	/* 案件序列号 */
	
	/**
	 * 构造函数
	 */
	public VoSysRdStandardPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdStandardPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 案件序列号 : String */
	public String getSys_rd_standard_id()
	{
		return getValue( ITEM_SYS_RD_STANDARD_ID );
	}

	public void setSys_rd_standard_id( String sys_rd_standard_id1 )
	{
		setValue( ITEM_SYS_RD_STANDARD_ID, sys_rd_standard_id1 );
	}

}

