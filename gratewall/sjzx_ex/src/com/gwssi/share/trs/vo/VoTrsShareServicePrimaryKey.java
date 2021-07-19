package com.gwssi.share.trs.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[trs_share_service]�����ݶ�����
 * @author Administrator
 *
 */
public class VoTrsShareServicePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201308051642360004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_TRS_SERVICE_ID = "trs_service_id" ;	/* ����ID */
	
	/**
	 * ���캯��
	 */
	public VoTrsShareServicePrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoTrsShareServicePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ����ID : String */
	public String getTrs_service_id()
	{
		return getValue( ITEM_TRS_SERVICE_ID );
	}

	public void setTrs_service_id( String trs_service_id1 )
	{
		setValue( ITEM_TRS_SERVICE_ID, trs_service_id1 );
	}

}

