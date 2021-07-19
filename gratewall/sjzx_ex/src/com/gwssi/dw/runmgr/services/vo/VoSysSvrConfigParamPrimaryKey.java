package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_svr_config_param]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSvrConfigParamPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200809101030500004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_SVR_CONFIG_PARAM_ID = "sys_svr_config_param_id" ;	/* ����������ò������ */
	
	/**
	 * ���캯��
	 */
	public VoSysSvrConfigParamPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSvrConfigParamPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ����������ò������ : String */
	public String getSys_svr_config_param_id()
	{
		return getValue( ITEM_SYS_SVR_CONFIG_PARAM_ID );
	}

	public void setSys_svr_config_param_id( String sys_svr_config_param_id1 )
	{
		setValue( ITEM_SYS_SVR_CONFIG_PARAM_ID, sys_svr_config_param_id1 );
	}

}

