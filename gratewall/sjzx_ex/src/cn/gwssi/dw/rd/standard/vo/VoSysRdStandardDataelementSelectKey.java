package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_standard_dataelement]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdStandardDataelementSelectKey extends VoBase
{
	private static final long serialVersionUID = 201205022055520007L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_STANDARD_DATAELEMENT_ID = "sys_rd_standard_dataelement_id" ;	/* 基础数据元ID */
	
	/**
	 * 构造函数
	 */
	public VoSysRdStandardDataelementSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdStandardDataelementSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 基础数据元ID : String */
	public String getSys_rd_standard_dataelement_id()
	{
		return getValue( ITEM_SYS_RD_STANDARD_DATAELEMENT_ID );
	}

	public void setSys_rd_standard_dataelement_id( String sys_rd_standard_dataelement_id1 )
	{
		setValue( ITEM_SYS_RD_STANDARD_DATAELEMENT_ID, sys_rd_standard_dataelement_id1 );
	}

}

