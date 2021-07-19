package com.gwssi.resource.share.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[res_share_table]�����ݶ�����
 * @author Administrator
 *
 */
public class VoResShareTablePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303191807570004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SHARE_TABLE_ID = "share_table_id" ;	/* �����ID */
	
	/**
	 * ���캯��
	 */
	public VoResShareTablePrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoResShareTablePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �����ID : String */
	public String getShare_table_id()
	{
		return getValue( ITEM_SHARE_TABLE_ID );
	}

	public void setShare_table_id( String share_table_id1 )
	{
		setValue( ITEM_SHARE_TABLE_ID, share_table_id1 );
	}

}

