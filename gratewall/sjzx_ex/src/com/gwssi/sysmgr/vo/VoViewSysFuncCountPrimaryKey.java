package com.gwssi.sysmgr.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[view_sys_func_count]�����ݶ�����
 * @author Administrator
 *
 */
public class VoViewSysFuncCountPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200907301527550008L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_FUNC_NAME = "func_name" ;		/* ����ģ�� */
	public static final String ITEM_SJJGID_FK = "sjjgid_fk" ;		/* ����ID */
	
	/**
	 * ���캯��
	 */
	public VoViewSysFuncCountPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoViewSysFuncCountPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ����ģ�� : String */
	public String getFunc_name()
	{
		return getValue( ITEM_FUNC_NAME );
	}

	public void setFunc_name( String func_name1 )
	{
		setValue( ITEM_FUNC_NAME, func_name1 );
	}

	/* ����ID : String */
	public String getSjjgid_fk()
	{
		return getValue( ITEM_SJJGID_FK );
	}

	public void setSjjgid_fk( String sjjgid_fk1 )
	{
		setValue( ITEM_SJJGID_FK, sjjgid_fk1 );
	}

}

