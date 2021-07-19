package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[download_column]�����ݶ�����
 * @author Administrator
 *
 */
public class VoDownloadColumnPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200902201130080008L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_COLUMN_NO = "column_no" ;		/* �ֶα��� */
	
	/**
	 * ���캯��
	 */
	public VoDownloadColumnPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoDownloadColumnPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �ֶα��� : String */
	public String getColumn_no()
	{
		return getValue( ITEM_COLUMN_NO );
	}

	public void setColumn_no( String column_no1 )
	{
		setValue( ITEM_COLUMN_NO, column_no1 );
	}

}

