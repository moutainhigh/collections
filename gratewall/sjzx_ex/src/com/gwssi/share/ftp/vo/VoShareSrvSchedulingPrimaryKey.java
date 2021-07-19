package com.gwssi.share.ftp.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[share_srv_scheduling]�����ݶ�����
 * @author Administrator
 *
 */
public class VoShareSrvSchedulingPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201308211658410004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SRV_SCHEDULING_ID = "srv_scheduling_id" ;	/* �������ID */
	
	/**
	 * ���캯��
	 */
	public VoShareSrvSchedulingPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoShareSrvSchedulingPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �������ID : String */
	public String getSrv_scheduling_id()
	{
		return getValue( ITEM_SRV_SCHEDULING_ID );
	}

	public void setSrv_scheduling_id( String srv_scheduling_id1 )
	{
		setValue( ITEM_SRV_SCHEDULING_ID, srv_scheduling_id1 );
	}

}

