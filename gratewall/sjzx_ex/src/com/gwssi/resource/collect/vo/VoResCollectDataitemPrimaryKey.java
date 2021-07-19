package com.gwssi.resource.collect.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[res_collect_dataitem]�����ݶ�����
 * @author Administrator
 *
 */
public class VoResCollectDataitemPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303221103430008L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_COLLECT_DATAITEM_ID = "collect_dataitem_id" ;	/* �ɼ�������ID */
	
	/**
	 * ���캯��
	 */
	public VoResCollectDataitemPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoResCollectDataitemPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �ɼ�������ID : String */
	public String getCollect_dataitem_id()
	{
		return getValue( ITEM_COLLECT_DATAITEM_ID );
	}

	public void setCollect_dataitem_id( String collect_dataitem_id1 )
	{
		setValue( ITEM_COLLECT_DATAITEM_ID, collect_dataitem_id1 );
	}

}

