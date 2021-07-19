package com.gwssi.share.ftp.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[share_ftp_srv_param]�����ݶ�����
 * @author Administrator
 *
 */
public class VoShareFtpSrvParamPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201308211700020012L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SRV_PARAM_ID = "srv_param_id" ;	/* ����ֵID */
	
	/**
	 * ���캯��
	 */
	public VoShareFtpSrvParamPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoShareFtpSrvParamPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ����ֵID : String */
	public String getSrv_param_id()
	{
		return getValue( ITEM_SRV_PARAM_ID );
	}

	public void setSrv_param_id( String srv_param_id1 )
	{
		setValue( ITEM_SRV_PARAM_ID, srv_param_id1 );
	}

}

