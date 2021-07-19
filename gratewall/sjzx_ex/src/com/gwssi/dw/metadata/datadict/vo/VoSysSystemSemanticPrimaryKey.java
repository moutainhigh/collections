package com.gwssi.dw.metadata.datadict.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_system_semantic]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSystemSemanticPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200804181523310004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_ID = "sys_id" ;				/* ϵͳ���� */
	
	/**
	 * ���캯��
	 */
	public VoSysSystemSemanticPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSystemSemanticPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ϵͳ���� : String */
	public String getSys_id()
	{
		return getValue( ITEM_SYS_ID );
	}

	public void setSys_id( String sys_id1 )
	{
		setValue( ITEM_SYS_ID, sys_id1 );
	}

}

