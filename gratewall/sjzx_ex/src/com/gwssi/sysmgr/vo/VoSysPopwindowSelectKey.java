package com.gwssi.sysmgr.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_popwindow]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysPopwindowSelectKey extends VoBase
{
	private static final long serialVersionUID = 200907271007430003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_POPWINDOW_ID = "sys_popwindow_id" ;	/* ϵͳ����ID */
	public static final String ITEM_EXPIRE_DATE = "expire_date" ;	/* �������� */
	
	/**
	 * ���캯��
	 */
	public VoSysPopwindowSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysPopwindowSelectKey(DataBus value)
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

	/* �������� : String */
	public String getExpire_date()
	{
		return getValue( ITEM_EXPIRE_DATE );
	}

	public void setExpire_date( String expire_date1 )
	{
		setValue( ITEM_EXPIRE_DATE, expire_date1 );
	}

}

