package com.gwssi.dw.aic.bj.xb.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[xb_report_base]�����ݶ�����
 * @author Administrator
 *
 */
public class VoXbReportBasePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200809170932520004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_XB_REPORT_BASE_ID = "xb_report_base_id" ;	/* �ٱ�������ϢID */
	
	/**
	 * ���캯��
	 */
	public VoXbReportBasePrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoXbReportBasePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �ٱ�������ϢID : String */
	public String getXb_report_base_id()
	{
		return getValue( ITEM_XB_REPORT_BASE_ID );
	}

	public void setXb_report_base_id( String xb_report_base_id1 )
	{
		setValue( ITEM_XB_REPORT_BASE_ID, xb_report_base_id1 );
	}

}

