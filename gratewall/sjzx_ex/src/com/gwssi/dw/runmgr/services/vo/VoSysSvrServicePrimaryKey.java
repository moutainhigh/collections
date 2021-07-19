package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_svr_service]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSvrServicePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200805061510590008L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_SVR_SERVICE_ID = "sys_svr_service_id" ;	/* ��������� */
	
	/**
	 * ���캯��
	 */
	public VoSysSvrServicePrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSvrServicePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ��������� : String */
	public String getSys_svr_service_id()
	{
		return getValue( ITEM_SYS_SVR_SERVICE_ID );
	}

	public void setSys_svr_service_id( String sys_svr_service_id1 )
	{
		setValue( ITEM_SYS_SVR_SERVICE_ID, sys_svr_service_id1 );
	}

}

