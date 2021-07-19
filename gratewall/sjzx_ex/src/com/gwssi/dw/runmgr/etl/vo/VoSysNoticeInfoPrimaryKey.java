package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_notice_info]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysNoticeInfoPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200810151621200004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_NOTICE_ID = "sys_notice_id" ;	/* ֪ͨͨ��ID */
	
	/**
	 * ���캯��
	 */
	public VoSysNoticeInfoPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysNoticeInfoPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ֪ͨͨ��ID : String */
	public String getSys_notice_id()
	{
		return getValue( ITEM_SYS_NOTICE_ID );
	}

	public void setSys_notice_id( String sys_notice_id1 )
	{
		setValue( ITEM_SYS_NOTICE_ID, sys_notice_id1 );
	}

}

