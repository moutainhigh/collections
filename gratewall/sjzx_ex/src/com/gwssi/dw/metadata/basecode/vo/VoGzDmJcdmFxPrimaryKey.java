package com.gwssi.dw.metadata.basecode.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[gz_dm_jcdm_fx]�����ݶ�����
 * @author Administrator
 *
 */
public class VoGzDmJcdmFxPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200708271316220003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_JCSJFX_ID = "jcsjfx_id" ;		/* �������ݷ���ID */
	
	/**
	 * ���캯��
	 */
	public VoGzDmJcdmFxPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoGzDmJcdmFxPrimaryKey(DataBus value)
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

}

