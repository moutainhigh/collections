package com.gwssi.share.trs.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[trs_share_service]�����ݶ�����
 * @author Administrator
 *
 */
public class VoTrsShareServiceSelectKey extends VoBase
{
	private static final long serialVersionUID = 201308051642360003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_TRS_SERVICE_NAME = "trs_service_name" ;	/* �������� */
	
	/**
	 * ���캯��
	 */
	public VoTrsShareServiceSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoTrsShareServiceSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* �������� : String */
	public String getTrs_service_name()
	{
		return getValue( ITEM_TRS_SERVICE_NAME );
	}

	public void setTrs_service_name( String trs_service_name1 )
	{
		setValue( ITEM_TRS_SERVICE_NAME, trs_service_name1 );
	}

}

