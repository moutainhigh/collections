package com.gwssi.resource.share.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[res_share_dataitem]�����ݶ�����
 * @author Administrator
 *
 */
public class VoResShareDataitemPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303191809040008L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SHARE_DATAITEM_ID = "share_dataitem_id" ;	/* ����������ID */
	
	/**
	 * ���캯��
	 */
	public VoResShareDataitemPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoResShareDataitemPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ����������ID : String */
	public String getShare_dataitem_id()
	{
		return getValue( ITEM_SHARE_DATAITEM_ID );
	}

	public void setShare_dataitem_id( String share_dataitem_id1 )
	{
		setValue( ITEM_SHARE_DATAITEM_ID, share_dataitem_id1 );
	}

}

