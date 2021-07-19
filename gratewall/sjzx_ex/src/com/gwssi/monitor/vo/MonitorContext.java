package com.gwssi.monitor.vo;

import cn.gwssi.common.context.TxnContext;

/**
 * 数据表[]的数据对象类
 * @author Administrator
 *
 */
public class MonitorContext extends TxnContext
{
	private static final long serialVersionUID = 201307171231310001L;
	
	/**
	 * 构造函数
	 */
	public MonitorContext()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public MonitorContext(TxnContext value)
	{
		super(value);
	}
	

}

