package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_svr_config_param]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSvrConfigParamSelectKey extends VoBase
{
	private static final long serialVersionUID = 200809101030500003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_SVR_CONFIG_ID = "sys_svr_config_id" ;	/* ����������ñ�� */
	
	/**
	 * ���캯��
	 */
	public VoSysSvrConfigParamSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSvrConfigParamSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ����������ñ�� : String */
	public String getSys_svr_config_id()
	{
		return getValue( ITEM_SYS_SVR_CONFIG_ID );
	}

	public void setSys_svr_config_id( String sys_svr_config_id1 )
	{
		setValue( ITEM_SYS_SVR_CONFIG_ID, sys_svr_config_id1 );
	}

}

