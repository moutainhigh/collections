package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[download_column]的数据对象类
 * @author Administrator
 *
 */
public class VoDownloadColumnSelectKey extends VoBase
{
	private static final long serialVersionUID = 200902201130080007L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_TABLE_NO = "table_no" ;			/* 临时表编码 */
	
	/**
	 * 构造函数
	 */
	public VoDownloadColumnSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDownloadColumnSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 临时表编码 : String */
	public String getTable_no()
	{
		return getValue( ITEM_TABLE_NO );
	}

	public void setTable_no( String table_no1 )
	{
		setValue( ITEM_TABLE_NO, table_no1 );
	}

}

