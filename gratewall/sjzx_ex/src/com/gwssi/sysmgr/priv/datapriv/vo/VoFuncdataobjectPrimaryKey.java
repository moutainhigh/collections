package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[funcdataobject]�����ݶ�����
 * @author Administrator
 *
 */
public class VoFuncdataobjectPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200709101623210016L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_FUNCODE = "funcode" ;			/* ���ܴ��� */
	public static final String ITEM_OBJECTID = "objectid" ;			/* ���ݶ������ */
	
	/**
	 * ���캯��
	 */
	public VoFuncdataobjectPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoFuncdataobjectPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ���ܴ��� : String */
	public String getFuncode()
	{
		return getValue( ITEM_FUNCODE );
	}

	public void setFuncode( String funcode1 )
	{
		setValue( ITEM_FUNCODE, funcode1 );
	}

	/* ���ݶ������ : String */
	public String getObjectid()
	{
		return getValue( ITEM_OBJECTID );
	}

	public void setObjectid( String objectid1 )
	{
		setValue( ITEM_OBJECTID, objectid1 );
	}

}

