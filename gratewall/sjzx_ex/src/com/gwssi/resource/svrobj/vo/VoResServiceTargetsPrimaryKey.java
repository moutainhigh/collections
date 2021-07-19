package com.gwssi.resource.svrobj.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[res_service_targets]�����ݶ�����
 * @author Administrator
 *
 */
public class VoResServiceTargetsPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303131040540004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* �������ID */
	
	/**
	 * ���캯��
	 */
	public VoResServiceTargetsPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoResServiceTargetsPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �������ID : String */
	public String getService_targets_id()
	{
		return getValue( ITEM_SERVICE_TARGETS_ID );
	}

	public void setService_targets_id( String service_targets_id1 )
	{
		setValue( ITEM_SERVICE_TARGETS_ID, service_targets_id1 );
	}

}

