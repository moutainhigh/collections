package com.gwssi.dw.aic.bj.newmon.que.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoTskQueLink extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_TSK_QUE_LINK = "tsk_que_link";			/* ��������������ID */
	public static final String ITEM_TSK_QUE_ID = "tsk_que_id";			/* �����������ID */
	public static final String ITEM_MON_MIS_ID = "mon_mis_id";			/* ���misid */
	public static final String ITEM_QUS_ID = "qus_id";			/* ����id */

	public VoTskQueLink(DataBus value)
	{
		super(value);
	}

	public VoTskQueLink()
	{
		super();
	}

	/* ��������������ID */
	public String getTsk_que_link()
	{
		return getValue( ITEM_TSK_QUE_LINK );
	}

	public void setTsk_que_link( String tsk_que_link1 )
	{
		setValue( ITEM_TSK_QUE_LINK, tsk_que_link1 );
	}

	/* �����������ID */
	public String getTsk_que_id()
	{
		return getValue( ITEM_TSK_QUE_ID );
	}

	public void setTsk_que_id( String tsk_que_id1 )
	{
		setValue( ITEM_TSK_QUE_ID, tsk_que_id1 );
	}

	/* ���misid */
	public String getMon_mis_id()
	{
		return getValue( ITEM_MON_MIS_ID );
	}

	public void setMon_mis_id( String mon_mis_id1 )
	{
		setValue( ITEM_MON_MIS_ID, mon_mis_id1 );
	}

	/* ����id */
	public String getQus_id()
	{
		return getValue( ITEM_QUS_ID );
	}

	public void setQus_id( String qus_id1 )
	{
		setValue( ITEM_QUS_ID, qus_id1 );
	}

}

