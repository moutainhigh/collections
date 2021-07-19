package com.gwssi.dw.aic.download.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[download_log]的数据对象类
 * @author Administrator
 *
 */
public class VoDownloadLogSelectKey extends VoBase
{
	private static final long serialVersionUID = 200812261332290003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_OPERNAME = "opername" ;			/* 操作名称 */
	public static final String ITEM_OPERDEPT = "operdept" ;			/* 操作者部门 */
	
	/**
	 * 构造函数
	 */
	public VoDownloadLogSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDownloadLogSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 操作名称 : String */
	public String getOpername()
	{
		return getValue( ITEM_OPERNAME );
	}

	public void setOpername( String opername1 )
	{
		setValue( ITEM_OPERNAME, opername1 );
	}

	/* 操作者部门 : String */
	public String getOperdept()
	{
		return getValue( ITEM_OPERDEPT );
	}

	public void setOperdept( String operdept1 )
	{
		setValue( ITEM_OPERDEPT, operdept1 );
	}

}

