package com.gwssi.dw.aic.bj.xb.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[xb_appeal_base]�����ݶ�����
 * @author Administrator
 *
 */
public class VoXbAppealBasePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200809111008440004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_XB_APPEAL_BASE_ID = "xb_appeal_base_id" ;	/* ���߻�����ϢID */
	
	/**
	 * ���캯��
	 */
	public VoXbAppealBasePrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoXbAppealBasePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ���߻�����ϢID : String */
	public String getXb_appeal_base_id()
	{
		return getValue( ITEM_XB_APPEAL_BASE_ID );
	}

	public void setXb_appeal_base_id( String xb_appeal_base_id1 )
	{
		setValue( ITEM_XB_APPEAL_BASE_ID, xb_appeal_base_id1 );
	}

}

