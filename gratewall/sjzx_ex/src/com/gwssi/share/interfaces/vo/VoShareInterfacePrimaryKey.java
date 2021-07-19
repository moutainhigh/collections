package com.gwssi.share.interfaces.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[share_interface]�����ݶ�����
 * @author Administrator
 *
 */
public class VoShareInterfacePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303121022120004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_INTERFACE_ID = "interface_id" ;	/* �ӿ�ID */
	
	/**
	 * ���캯��
	 */
	public VoShareInterfacePrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoShareInterfacePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �ӿ�ID : String */
	public String getInterface_id()
	{
		return getValue( ITEM_INTERFACE_ID );
	}

	public void setInterface_id( String interface_id1 )
	{
		setValue( ITEM_INTERFACE_ID, interface_id1 );
	}

}

