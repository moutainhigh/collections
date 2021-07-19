package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[funcdataobject]�����ݶ�����
 * @author Administrator
 *
 */
public class VoFuncdataobjectSelectKey extends VoBase
{
	private static final long serialVersionUID = 200709101623210015L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_OBJECTID = "objectid" ;			/* ���ݶ������ */
	
	/**
	 * ���캯��
	 */
	public VoFuncdataobjectSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoFuncdataobjectSelectKey(DataBus value)
	{
		super(value);
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

