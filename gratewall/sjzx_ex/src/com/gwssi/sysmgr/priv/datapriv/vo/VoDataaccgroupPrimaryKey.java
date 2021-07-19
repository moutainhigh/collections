package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[dataaccgroup]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDataaccgroupPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200709101706180028L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DATAACCGRPID = "dataaccgrpid" ;	/* ����Ȩ�޷���ID */
	
	/**
	 * ���캯��
	 */
	public VoDataaccgroupPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDataaccgroupPrimaryKey(DataBus value)
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

}

