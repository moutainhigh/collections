package com.gwssi.sysmgr.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_popwindow]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysPopwindowPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200907271007440004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_POPWINDOW_ID = "sys_popwindow_id" ;	/* ϵͳ����ID */
	
	/**
	 * ���캯��
	 */
	public VoSysPopwindowPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysPopwindowPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ϵͳ����ID : String */
	public String getSys_popwindow_id()
	{
		return getValue( ITEM_SYS_POPWINDOW_ID );
	}

	public void setSys_popwindow_id( String sys_popwindow_id1 )
	{
		setValue( ITEM_SYS_POPWINDOW_ID, sys_popwindow_id1 );
	}

}

