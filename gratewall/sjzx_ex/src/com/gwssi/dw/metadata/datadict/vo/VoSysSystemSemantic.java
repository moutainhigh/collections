package com.gwssi.dw.metadata.datadict.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_system_semantic]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSystemSemantic extends VoBase
{
	private static final long serialVersionUID = 200804181523310002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_ID = "sys_id" ;				/* ϵͳ���� */
	public static final String ITEM_SYS_NO = "sys_no" ;				/* ϵͳ���� */
	public static final String ITEM_SYS_NAME = "sys_name" ;			/* ϵͳ���� */
	public static final String ITEM_SYS_SIMPLE = "sys_simple" ;		/* ϵͳ��� */
	public static final String ITEM_SYS_ORDER = "sys_order" ;		/* ����˳�� */
	
	/**
	 * ���캯��
	 */
	public VoSysSystemSemantic()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSystemSemantic(DataBus value)
	{
		super(value);
	}
	
	/* ϵͳID : String */
	public String getSys_id()
	{
		return getValue( ITEM_SYS_ID );
	}

	public void setSys_id( String sys_id1 )
	{
		setValue( ITEM_SYS_NO, sys_id1 );
	}	
	
	/* ϵͳ���� : String */
	public String getSys_no()
	{
		return getValue( ITEM_SYS_NO );
	}

	public void setSys_no( String sys_no1 )
	{
		setValue( ITEM_SYS_NO, sys_no1 );
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

	/* ����˳�� : String */
	public String getSys_order()
	{
		return getValue( ITEM_SYS_ORDER );
	}

	public void setSys_order( String sys_order1 )
	{
		setValue( ITEM_SYS_ORDER, sys_order1 );
	}

}

