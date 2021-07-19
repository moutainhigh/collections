package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_svr_service_param]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSvrServiceParamPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200809051535250008L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_SVR_SERVICE_PARAM_ID = "sys_svr_service_param_id" ;	/* �����������ӱ�� */
	
	/**
	 * ���캯��
	 */
	public VoSysSvrServiceParamPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSvrServiceParamPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �����������ӱ�� : String */
	public String getSys_svr_service_param_id()
	{
		return getValue( ITEM_SYS_SVR_SERVICE_PARAM_ID );
	}

	public void setSys_svr_service_param_id( String sys_svr_service_param_id1 )
	{
		setValue( ITEM_SYS_SVR_SERVICE_PARAM_ID, sys_svr_service_param_id1 );
	}

}

