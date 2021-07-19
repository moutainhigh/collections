package com.gwssi.dw.metadata.datadict.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_column_semantic]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysColumnSemanticPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200804181524160012L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_COLUMN_NO = "column_no" ;		/* ҵ���ֶα��� */
	
	/**
	 * ���캯��
	 */
	public VoSysColumnSemanticPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysColumnSemanticPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ҵ���ֶα��� : String */
	public String getColumn_no()
	{
		return getValue( ITEM_COLUMN_NO );
	}

	public void setColumn_no( String column_no1 )
	{
		setValue( ITEM_COLUMN_NO, column_no1 );
	}

}

