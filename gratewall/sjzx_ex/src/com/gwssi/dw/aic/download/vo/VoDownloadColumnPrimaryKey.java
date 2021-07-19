package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[download_column]的数据对象类
 * @author Administrator
 *
 */
public class VoDownloadColumnPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200902201130080008L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_COLUMN_NO = "column_no" ;		/* 字段编码 */
	
	/**
	 * 构造函数
	 */
	public VoDownloadColumnPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDownloadColumnPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 字段编码 : String */
	public String getColumn_no()
	{
		return getValue( ITEM_COLUMN_NO );
	}

	public void setColumn_no( String column_no1 )
	{
		setValue( ITEM_COLUMN_NO, column_no1 );
	}

}

