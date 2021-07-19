package com.gwssi.dw.metadata.msurunit.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[gz_zb_jldw]�����ݶ�����
 * @author Administrator
 *
 */
public class VoGzZbJldwPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200707251356150016L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_JLDW_DM = "jldw_dm" ;			/* ������λ���� */
	
	/**
	 * ���캯��
	 */
	public VoGzZbJldwPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoGzZbJldwPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ������λ���� : String */
	public String getJldw_dm()
	{
		return getValue( ITEM_JLDW_DM );
	}

	public void setJldw_dm( String jldw_dm1 )
	{
		setValue( ITEM_JLDW_DM, jldw_dm1 );
	}

}

