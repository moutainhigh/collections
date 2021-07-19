package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[dataobject]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDataobjectPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200709101621250012L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_OBJECTID = "objectid" ;			/* ����Ȩ�����ʹ��� */
	
	/**
	 * ���캯��
	 */
	public VoDataobjectPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDataobjectPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ����Ȩ�����ʹ��� : String */
	public String getObjectid()
	{
		return getValue( ITEM_OBJECTID );
	}

	public void setObjectid( String objectid1 )
	{
		setValue( ITEM_OBJECTID, objectid1 );
	}

}

