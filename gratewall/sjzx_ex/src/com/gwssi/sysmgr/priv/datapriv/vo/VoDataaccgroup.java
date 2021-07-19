package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[dataaccgroup]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDataaccgroup extends VoBase
{
	private static final long serialVersionUID = 200709101706180026L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DATAACCGRPID = "dataaccgrpid" ;	/* ����Ȩ�޷���ID */
	public static final String ITEM_DATAACCGRPNAME = "dataaccgrpname" ;	/* ����Ȩ�޷������� */
	public static final String ITEM_DATAACCRULE = "dataaccrule" ;	/* ��ɫȨ����֤���� */
	public static final String ITEM_DATAACCTYPE = "dataacctype" ;	/* ����Ȩ�޷������� */
	public static final String ITEM_DATAACCGRPDESC = "dataaccgrpdesc" ;	/* ����Ȩ�޷������� */
	
	/**
	 * ���캯��
	 */
	public VoDataaccgroup()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDataaccgroup(DataBus value)
	{
		super(value);
	}
	
	/* ����Ȩ�޷���ID : String */
	public String getDataaccgrpid()
	{
		return getValue( ITEM_DATAACCGRPID );
	}

	public void setDataaccgrpid( String dataaccgrpid1 )
	{
		setValue( ITEM_DATAACCGRPID, dataaccgrpid1 );
	}

	/* ����Ȩ�޷������� : String */
	public String getDataaccgrpname()
	{
		return getValue( ITEM_DATAACCGRPNAME );
	}

	public void setDataaccgrpname( String dataaccgrpname1 )
	{
		setValue( ITEM_DATAACCGRPNAME, dataaccgrpname1 );
	}

	/* ��ɫȨ����֤���� : String */
	public String getDataaccrule()
	{
		return getValue( ITEM_DATAACCRULE );
	}

	public void setDataaccrule( String dataaccrule1 )
	{
		setValue( ITEM_DATAACCRULE, dataaccrule1 );
	}

	/* ����Ȩ�޷������� : String */
	public String getDataacctype()
	{
		return getValue( ITEM_DATAACCTYPE );
	}

	public void setDataacctype( String dataacctype1 )
	{
		setValue( ITEM_DATAACCTYPE, dataacctype1 );
	}

	/* ����Ȩ�޷������� : String */
	public String getDataaccgrpdesc()
	{
		return getValue( ITEM_DATAACCGRPDESC );
	}

	public void setDataaccgrpdesc( String dataaccgrpdesc1 )
	{
		setValue( ITEM_DATAACCGRPDESC, dataaccgrpdesc1 );
	}

}

