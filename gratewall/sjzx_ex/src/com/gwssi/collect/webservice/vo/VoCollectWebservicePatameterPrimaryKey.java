package com.gwssi.collect.webservice.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[collect_webservice_patameter]�����ݶ�����
 * @author Administrator
 *
 */
public class VoCollectWebservicePatameterPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304101416380004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_WEBSERVICE_PATAMETER_ID = "webservice_patameter_id" ;	/* ����ID */
	
	/**
	 * ���캯��
	 */
	public VoCollectWebservicePatameterPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoCollectWebservicePatameterPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ����ID : String */
	public String getWebservice_patameter_id()
	{
		return getValue( ITEM_WEBSERVICE_PATAMETER_ID );
	}

	public void setWebservice_patameter_id( String webservice_patameter_id1 )
	{
		setValue( ITEM_WEBSERVICE_PATAMETER_ID, webservice_patameter_id1 );
	}

}

