package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[download_log]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDownloadLogSelectKey extends VoBase
{
	private static final long serialVersionUID = 200812261332290003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_OPERNAME = "opername" ;			/* �������� */
	public static final String ITEM_OPERDEPT = "operdept" ;			/* �����߲��� */
	
	/**
	 * ���캯��
	 */
	public VoDownloadLogSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDownloadLogSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* �������� : String */
	public String getOpername()
	{
		return getValue( ITEM_OPERNAME );
	}

	public void setOpername( String opername1 )
	{
		setValue( ITEM_OPERNAME, opername1 );
	}

	/* �����߲��� : String */
	public String getOperdept()
	{
		return getValue( ITEM_OPERDEPT );
	}

	public void setOperdept( String operdept1 )
	{
		setValue( ITEM_OPERDEPT, operdept1 );
	}

}

