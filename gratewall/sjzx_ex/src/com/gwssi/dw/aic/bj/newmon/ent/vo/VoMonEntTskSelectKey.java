package com.gwssi.dw.aic.bj.newmon.ent.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[mon_ent_tsk]�����ݶ�����
 * @author Administrator
 *
 */
public class VoMonEntTskSelectKey extends VoBase
{
	private static final long serialVersionUID = 200811191653120003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_MAIN_ID = "main_id" ;			/* ����ID */
	
	/**
	 * ���캯��
	 */
	public VoMonEntTskSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoMonEntTskSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ����ID : String */
	public String getMain_id()
	{
		return getValue( ITEM_MAIN_ID );
	}

	public void setMain_id( String main_id1 )
	{
		setValue( ITEM_MAIN_ID, main_id1 );
	}

}

