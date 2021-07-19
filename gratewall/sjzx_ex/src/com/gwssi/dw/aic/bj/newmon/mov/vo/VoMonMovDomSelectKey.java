package com.gwssi.dw.aic.bj.newmon.mov.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[mon_mov_dom]�����ݶ�����
 * @author Administrator
 *
 */
public class VoMonMovDomSelectKey extends VoBase
{
	private static final long serialVersionUID = 200811201508540003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_MON_MOV_DOM_ID = "mon_mov_dom_id" ;	/* ��Ǩס����ID */
	public static final String ITEM_MAIN_ID = "main_id" ;			/* ����id */
	
	/**
	 * ���캯��
	 */
	public VoMonMovDomSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoMonMovDomSelectKey(DataBus value)
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

	/* ����id : String */
	public String getMain_id()
	{
		return getValue( ITEM_MAIN_ID );
	}

	public void setMain_id( String main_id1 )
	{
		setValue( ITEM_MAIN_ID, main_id1 );
	}

}

