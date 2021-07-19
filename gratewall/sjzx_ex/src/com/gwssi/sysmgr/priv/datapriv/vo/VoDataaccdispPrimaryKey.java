package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[dataaccdisp]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDataaccdispPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200709101705440024L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_OBJECTID = "objectid" ;			/* �������� */
	public static final String ITEM_DATAACCGRPID = "dataaccgrpid" ;	/* ����Ȩ�޷������� */
	
	/**
	 * ���캯��
	 */
	public VoDataaccdispPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDataaccdispPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �������� : String */
	public String getObjectid()
	{
		return getValue( ITEM_OBJECTID );
	}

	public void setObjectid( String objectid1 )
	{
		setValue( ITEM_OBJECTID, objectid1 );
	}

	/* ����Ȩ�޷������� : String */
	public String getDataaccgrpid()
	{
		return getValue( ITEM_DATAACCGRPID );
	}

	public void setDataaccgrpid( String dataaccgrpid1 )
	{
		setValue( ITEM_DATAACCGRPID, dataaccgrpid1 );
	}

}

