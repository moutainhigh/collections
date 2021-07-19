package com.gwssi.dw.aic.bj.xb.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[xb_report_base]的数据对象类
 * @author Administrator
 *
 */
public class VoXbReportBasePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200809170932520004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_XB_REPORT_BASE_ID = "xb_report_base_id" ;	/* 举报基本信息ID */
	
	/**
	 * 构造函数
	 */
	public VoXbReportBasePrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoXbReportBasePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 举报基本信息ID : String */
	public String getXb_report_base_id()
	{
		return getValue( ITEM_XB_REPORT_BASE_ID );
	}

	public void setXb_report_base_id( String xb_report_base_id1 )
	{
		setValue( ITEM_XB_REPORT_BASE_ID, xb_report_base_id1 );
	}

}

