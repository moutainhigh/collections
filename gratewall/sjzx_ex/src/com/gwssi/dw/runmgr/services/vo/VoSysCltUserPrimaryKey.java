package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_clt_user]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysCltUserPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200809052041060004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_CLT_USER_ID = "sys_clt_user_id" ;	
	
	/**
	 * ���캯��
	 */
	public VoSysCltUserPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysCltUserPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	public String getSys_clt_user_id()
	{
		return getValue( ITEM_SYS_CLT_USER_ID );
	}

	public void setSys_clt_user_id( String sys_clt_user_id1 )
	{
		setValue( ITEM_SYS_CLT_USER_ID, sys_clt_user_id1 );
	}

}

