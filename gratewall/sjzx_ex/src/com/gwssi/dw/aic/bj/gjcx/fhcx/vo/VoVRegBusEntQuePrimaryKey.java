package com.gwssi.dw.aic.bj.gjcx.fhcx.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[v_reg_bus_ent_que]�����ݶ�����
 * @author Administrator
 *
 */
public class VoVRegBusEntQuePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200809101405040004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_REG_BUS_ENT_ID = "reg_bus_ent_id" ;	/* ��ҵID */
	
	/**
	 * ���캯��
	 */
	public VoVRegBusEntQuePrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoVRegBusEntQuePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ��ҵID : String */
	public String getReg_bus_ent_id()
	{
		return getValue( ITEM_REG_BUS_ENT_ID );
	}

	public void setReg_bus_ent_id( String reg_bus_ent_id1 )
	{
		setValue( ITEM_REG_BUS_ENT_ID, reg_bus_ent_id1 );
	}

}

