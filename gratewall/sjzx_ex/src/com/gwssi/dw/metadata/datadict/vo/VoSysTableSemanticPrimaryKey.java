package com.gwssi.dw.metadata.datadict.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_table_semantic]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysTableSemanticPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200804181523530008L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_TABLE_NO = "table_no" ;			/* ҵ������ */
	
	/**
	 * ���캯��
	 */
	public VoSysTableSemanticPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysTableSemanticPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ҵ������ : String */
	public String getTable_no()
	{
		return getValue( ITEM_TABLE_NO );
	}

	public void setTable_no( String table_no1 )
	{
		setValue( ITEM_TABLE_NO, table_no1 );
	}

}

