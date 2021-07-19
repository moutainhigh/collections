package com.gwssi.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[common_standard]�����ݶ�����
 * @author Administrator
 *
 */
public class VoCommonStandardPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304121530000004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_STANDARD_ID = "standard_id" ;	/* ��׼ID */
	
	/**
	 * ���캯��
	 */
	public VoCommonStandardPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoCommonStandardPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ��׼ID : String */
	public String getStandard_id()
	{
		return getValue( ITEM_STANDARD_ID );
	}

	public void setStandard_id( String standard_id1 )
	{
		setValue( ITEM_STANDARD_ID, standard_id1 );
	}

}

