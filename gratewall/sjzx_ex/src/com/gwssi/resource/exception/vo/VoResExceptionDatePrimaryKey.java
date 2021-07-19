package com.gwssi.resource.exception.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[res_exception_date]的数据对象类
 * @author Administrator
 *
 */
public class VoResExceptionDatePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303131312090004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_EXCEPTION_DATE_ID = "exception_date_id" ;	/* 例外日期ID */
	
	/**
	 * 构造函数
	 */
	public VoResExceptionDatePrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoResExceptionDatePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 例外日期ID : String */
	public String getException_date_id()
	{
		return getValue( ITEM_EXCEPTION_DATE_ID );
	}

	public void setException_date_id( String exception_date_id1 )
	{
		setValue( ITEM_EXCEPTION_DATE_ID, exception_date_id1 );
	}

}

