package com.gwssi.dw.metadata.datadict.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_system_semantic]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSystemSemanticSelectKey extends VoBase
{
	private static final long serialVersionUID = 200804181523310003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_NAME = "sys_name" ;			/* ϵͳ���� */
	public static final String ITEM_SYS_SIMPLE = "sys_simple" ;		/* ϵͳ��� */
	
	/**
	 * ���캯��
	 */
	public VoSysSystemSemanticSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSystemSemanticSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ϵͳ���� : String */
	public String getSys_name()
	{
		return getValue( ITEM_SYS_NAME );
	}

	public void setSys_name( String sys_name1 )
	{
		setValue( ITEM_SYS_NAME, sys_name1 );
	}

	/* ϵͳ��� : String */
	public String getSys_simple()
	{
		return getValue( ITEM_SYS_SIMPLE );
	}

	public void setSys_simple( String sys_simple1 )
	{
		setValue( ITEM_SYS_SIMPLE, sys_simple1 );
	}

}

