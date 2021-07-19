package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[dataaccdisp]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDataaccdisp extends VoBase
{
	private static final long serialVersionUID = 200709101705430022L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_OBJECTID = "objectid" ;			/* �������� */
	public static final String ITEM_DATAACCGRPID = "dataaccgrpid" ;	/* ����Ȩ�޷������� */
	public static final String ITEM_DATAACCDISPOBJ = "dataaccdispobj" ;	/* ������� */
	
	/**
	 * ���캯��
	 */
	public VoDataaccdisp()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDataaccdisp(DataBus value)
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

	/* ������� : String */
	public String getDataaccdispobj()
	{
		return getValue( ITEM_DATAACCDISPOBJ );
	}

	public void setDataaccdispobj( String dataaccdispobj1 )
	{
		setValue( ITEM_DATAACCDISPOBJ, dataaccdispobj1 );
	}

}

