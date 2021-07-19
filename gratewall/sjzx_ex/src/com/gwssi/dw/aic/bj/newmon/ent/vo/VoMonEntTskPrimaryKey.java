package com.gwssi.dw.aic.bj.newmon.ent.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[mon_ent_tsk]�����ݶ�����
 * @author Administrator
 *
 */
public class VoMonEntTskPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200811191653120004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_MON_ENT_TSK_ID = "mon_ent_tsk_id" ;	/* �����������id */
	
	/**
	 * ���캯��
	 */
	public VoMonEntTskPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoMonEntTskPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �����������id : String */
	public String getMon_ent_tsk_id()
	{
		return getValue( ITEM_MON_ENT_TSK_ID );
	}

	public void setMon_ent_tsk_id( String mon_ent_tsk_id1 )
	{
		setValue( ITEM_MON_ENT_TSK_ID, mon_ent_tsk_id1 );
	}

}

