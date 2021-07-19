package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_svr_config]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSvrConfigPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200809081645150004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_SVR_CONFIG_ID = "sys_svr_config_id" ;	/* ����������ñ��� */
	
	/**
	 * ���캯��
	 */
	public VoSysSvrConfigPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSvrConfigPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ����������ñ��� : String */
	public String getSys_svr_config_id()
	{
		return getValue( ITEM_SYS_SVR_CONFIG_ID );
	}

	public void setSys_svr_config_id( String sys_svr_config_id1 )
	{
		setValue( ITEM_SYS_SVR_CONFIG_ID, sys_svr_config_id1 );
	}

}

