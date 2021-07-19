package com.gwssi.dw.metadata.basecode.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[gz_dm_jcdm]�����ݶ�����
 * @author Administrator
 *
 */
public class VoGzDmJcdmSelectKey extends VoBase
{
	private static final long serialVersionUID = 200708261321550002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_JC_DM_ID = "jc_dm_id" ;			/* ��������ID */
	
	/**
	 * ���캯��
	 */
	public VoGzDmJcdmSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoGzDmJcdmSelectKey(DataBus value)
	{
		super(value);
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

