package com.gwssi.dw.aic.bj.gjcx.fhcx.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[v_reg_bus_ent_que]的数据对象类
 * @author Administrator
 *
 */
public class VoVRegBusEntQuePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200809101405040004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_REG_BUS_ENT_ID = "reg_bus_ent_id" ;	/* 企业ID */
	
	/**
	 * 构造函数
	 */
	public VoVRegBusEntQuePrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoVRegBusEntQuePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 企业ID : String */
	public String getReg_bus_ent_id()
	{
		return getValue( ITEM_REG_BUS_ENT_ID );
	}

	public void setReg_bus_ent_id( String reg_bus_ent_id1 )
	{
		setValue( ITEM_REG_BUS_ENT_ID, reg_bus_ent_id1 );
	}

}

