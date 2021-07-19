package com.gwssi.dw.metadata.basecode.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[gz_dm_jcdm_fx]�����ݶ�����
 * @author Administrator
 *
 */
public class VoGzDmJcdmFxSelectKey extends VoBase
{
	private static final long serialVersionUID = 200708271316220002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_JCSJFX_ID = "jcsjfx_id" ;		/* �������ݷ���ID */
	public static final String ITEM_JC_DM_ID = "jc_dm_id" ;			/* ��������ID */
	
	/**
	 * ���캯��
	 */
	public VoGzDmJcdmFxSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoGzDmJcdmFxSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* �������ݷ���ID : String */
	public String getJcsjfx_id()
	{
		return getValue( ITEM_JCSJFX_ID );
	}

	public void setJcsjfx_id( String jcsjfx_id1 )
	{
		setValue( ITEM_JCSJFX_ID, jcsjfx_id1 );
	}

	/* ��������ID : String */
	public String getJc_dm_id()
	{
		return getValue( ITEM_JC_DM_ID );
	}

	public void setJc_dm_id( String jc_dm_id1 )
	{
		setValue( ITEM_JC_DM_ID, jc_dm_id1 );
	}

}

