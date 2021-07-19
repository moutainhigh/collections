package com.gwssi.help.vo;

import cn.gwssi.common.context.TxnContext;

/**
 * 数据表[]的数据对象类
 * @author Administrator
 *
 */
public class HelpContext extends TxnContext
{
	private static final long serialVersionUID = 201307171231310001L;
	
	/**
	 * 构造函数
	 */
	public HelpContext()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public HelpContext(TxnContext value)
	{
		super(value);
	}
	

}

