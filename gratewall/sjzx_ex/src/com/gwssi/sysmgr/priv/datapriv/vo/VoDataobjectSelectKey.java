package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[dataobject]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDataobjectSelectKey extends VoBase
{
	private static final long serialVersionUID = 200709101621240011L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_OBJECTSOURCE = "objectsource" ;	/* ����Ȩ����Դ */
	
	/**
	 * ���캯��
	 */
	public VoDataobjectSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDataobjectSelectKey(DataBus value)
	{
		super(value);
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

