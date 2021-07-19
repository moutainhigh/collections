package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[download_column]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDownloadColumnSelectKey extends VoBase
{
	private static final long serialVersionUID = 200902201130080007L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_TABLE_NO = "table_no" ;			/* ��ʱ����� */
	
	/**
	 * ���캯��
	 */
	public VoDownloadColumnSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDownloadColumnSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ��ʱ����� : String */
	public String getTable_no()
	{
		return getValue( ITEM_TABLE_NO );
	}

	public void setTable_no( String table_no1 )
	{
		setValue( ITEM_TABLE_NO, table_no1 );
	}

}

