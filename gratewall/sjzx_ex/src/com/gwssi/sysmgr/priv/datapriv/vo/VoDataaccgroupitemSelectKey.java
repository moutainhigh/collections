package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[dataaccgroupitem]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDataaccgroupitemSelectKey extends VoBase
{
	private static final long serialVersionUID = 200709101706490031L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DATAACCGRPID = "dataaccgrpid" ;	/* ����Ȩ�޷���ID */
	
	/**
	 * ���캯��
	 */
	public VoDataaccgroupitemSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDataaccgroupitemSelectKey(DataBus value)
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

