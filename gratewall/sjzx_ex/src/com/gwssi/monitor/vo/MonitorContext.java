package com.gwssi.monitor.vo;

import cn.gwssi.common.context.TxnContext;

/**
 * ���ݱ�[]�����ݶ�����
 * @author Administrator
 *
 */
public class MonitorContext extends TxnContext
{
	private static final long serialVersionUID = 201307171231310001L;
	
	/**
	 * ���캯��
	 */
	public MonitorContext()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public MonitorContext(TxnContext value)
	{
		super(value);
	}
	

}

