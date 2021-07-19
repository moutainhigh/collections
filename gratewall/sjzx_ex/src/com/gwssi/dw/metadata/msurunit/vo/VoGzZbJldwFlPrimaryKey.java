package com.gwssi.dw.metadata.msurunit.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[gz_zb_jldw_fl]�����ݶ�����
 * @author Administrator
 *
 */
public class VoGzZbJldwFlPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200707251356150012L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DWLB_DM = "dwlb_dm" ;			/* ��λ������ */
	
	/**
	 * ���캯��
	 */
	public VoGzZbJldwFlPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoGzZbJldwFlPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ��λ������ : String */
	public String getDwlb_dm()
	{
		return getValue( ITEM_DWLB_DM );
	}

	public void setDwlb_dm( String dwlb_dm1 )
	{
		setValue( ITEM_DWLB_DM, dwlb_dm1 );
	}

}

