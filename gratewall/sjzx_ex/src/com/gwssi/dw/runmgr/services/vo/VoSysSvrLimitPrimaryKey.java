package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_svr_limit]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSvrLimitPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201207121643140004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_SVR_LIMIT_ID = "sys_svr_limit_id" ;	/* �û������������� */
	
	/**
	 * ���캯��
	 */
	public VoSysSvrLimitPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSvrLimitPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �û������������� : String */
	public String getSys_svr_limit_id()
	{
		return getValue( ITEM_SYS_SVR_LIMIT_ID );
	}

	public void setSys_svr_limit_id( String sys_svr_limit_id1 )
	{
		setValue( ITEM_SYS_SVR_LIMIT_ID, sys_svr_limit_id1 );
	}

}

