package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[dataaccgroupitem]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDataaccgroupitem extends VoBase
{
	private static final long serialVersionUID = 200709101706480030L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DATAACCGRPID = "dataaccgrpid" ;	/* ����Ȩ�޷���ID */
	public static final String ITEM_OBJECTID = "objectid" ;			/* ����Ȩ�����ʹ��� */
	public static final String ITEM_DATAACCID = "dataaccid" ;		/* ����Ȩ������ */
	
	/**
	 * ���캯��
	 */
	public VoDataaccgroupitem()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDataaccgroupitem(DataBus value)
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

	/* ����Ȩ�����ʹ��� : String */
	public String getObjectid()
	{
		return getValue( ITEM_OBJECTID );
	}

	public void setObjectid( String objectid1 )
	{
		setValue( ITEM_OBJECTID, objectid1 );
	}

	/* ����Ȩ������ : String */
	public String getDataaccid()
	{
		return getValue( ITEM_DATAACCID );
	}

	public void setDataaccid( String dataaccid1 )
	{
		setValue( ITEM_DATAACCID, dataaccid1 );
	}

}

