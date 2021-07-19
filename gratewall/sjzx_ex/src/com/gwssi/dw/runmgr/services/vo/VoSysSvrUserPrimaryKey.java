package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_svr_user]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSvrUserPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200805050829180004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_SVR_USER_ID = "sys_svr_user_id" ;	/* ��������� */
	
	/**
	 * ���캯��
	 */
	public VoSysSvrUserPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSvrUserPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ��������� : String */
	public String getSys_svr_user_id()
	{
		return getValue( ITEM_SYS_SVR_USER_ID );
	}

	public void setSys_svr_user_id( String sys_svr_user_id1 )
	{
		setValue( ITEM_SYS_SVR_USER_ID, sys_svr_user_id1 );
	}

}

