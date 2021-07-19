package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[dataaccgroup]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDataaccgroupSelectKey extends VoBase
{
	private static final long serialVersionUID = 200709101706180027L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DATAACCGRPNAME = "dataaccgrpname" ;	/* ����Ȩ�޷������� */
	public static final String ITEM_DATAACCGRPDESC = "dataaccgrpdesc" ;	/* ����Ȩ�޷������� */
	
	/**
	 * ���캯��
	 */
	public VoDataaccgroupSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDataaccgroupSelectKey(DataBus value)
	{
		super(value);
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

