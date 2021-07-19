package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[dataobject]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDataobject extends VoBase
{
	private static final long serialVersionUID = 200709101621230010L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_OBJECTID = "objectid" ;			/* ����Ȩ�����ʹ��� */
	public static final String ITEM_OBJECTSOURCE = "objectsource" ;	/* ����Ȩ����Դ */
	
	/**
	 * ���캯��
	 */
	public VoDataobject()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDataobject(DataBus value)
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

	/* ����Ȩ����Դ : String */
	public String getObjectsource()
	{
		return getValue( ITEM_OBJECTSOURCE );
	}

	public void setObjectsource( String objectsource1 )
	{
		setValue( ITEM_OBJECTSOURCE, objectsource1 );
	}

}

