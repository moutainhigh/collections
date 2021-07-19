package com.gwssi.dw.aic.bj.newmon.mov.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[mon_mov_dom]�����ݶ�����
 * @author Administrator
 *
 */
public class VoMonMovDomPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200811201508540004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_MON_MOV_DOM_ID = "mon_mov_dom_id" ;	/* ��Ǩס����ID */
	
	/**
	 * ���캯��
	 */
	public VoMonMovDomPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoMonMovDomPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ��Ǩס����ID : String */
	public String getMon_mov_dom_id()
	{
		return getValue( ITEM_MON_MOV_DOM_ID );
	}

	public void setMon_mov_dom_id( String mon_mov_dom_id1 )
	{
		setValue( ITEM_MON_MOV_DOM_ID, mon_mov_dom_id1 );
	}

}

