package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[view_sys_count_semantic]�����ݶ�����
 * @author Administrator
 *
 */
public class VoViewSysCountSemanticPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200902271135510004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_TABLE_CLASS_ID = "table_class_id" ;	/* ����ID */
	
	/**
	 * ���캯��
	 */
	public VoViewSysCountSemanticPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoViewSysCountSemanticPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ����ID : String */
	public String getTable_class_id()
	{
		return getValue( ITEM_TABLE_CLASS_ID );
	}

	public void setTable_class_id( String table_class_id1 )
	{
		setValue( ITEM_TABLE_CLASS_ID, table_class_id1 );
	}

}

