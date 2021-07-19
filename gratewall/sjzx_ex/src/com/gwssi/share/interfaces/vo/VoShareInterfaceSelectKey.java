package com.gwssi.share.interfaces.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[share_interface]�����ݶ�����
 * @author Administrator
 *
 */
public class VoShareInterfaceSelectKey extends VoBase
{
	private static final long serialVersionUID = 201303121022120003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_INTERFACE_NAME = "interface_name" ;	/* �ӿ����� */
	public static final String ITEM_INTERFACE_STATE = "interface_state" ;	/* �ӿ�״̬ */
	
	/**
	 * ���캯��
	 */
	public VoShareInterfaceSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoShareInterfaceSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* �ӿ����� : String */
	public String getInterface_name()
	{
		return getValue( ITEM_INTERFACE_NAME );
	}

	public void setInterface_name( String interface_name1 )
	{
		setValue( ITEM_INTERFACE_NAME, interface_name1 );
	}

	/* �ӿ�״̬ : String */
	public String getInterface_state()
	{
		return getValue( ITEM_INTERFACE_STATE );
	}

	public void setInterface_state( String interface_state1 )
	{
		setValue( ITEM_INTERFACE_STATE, interface_state1 );
	}

}

