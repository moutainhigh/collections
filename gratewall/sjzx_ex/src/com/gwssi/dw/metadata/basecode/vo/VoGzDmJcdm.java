package com.gwssi.dw.metadata.basecode.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[gz_dm_jcdm]�����ݶ�����
 * @author Administrator
 *
 */
public class VoGzDmJcdm extends VoBase
{
	private static final long serialVersionUID = 200708261321540001L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_JC_DM_ID = "jc_dm_id" ;			/* ��������ID */
	public static final String ITEM_JC_DM_DM = "jc_dm_dm" ;			/* �������� */
	public static final String ITEM_JC_DM_MC = "jc_dm_mc" ;			/* ������������ */
	public static final String ITEM_JC_DM_BZLY = "jc_dm_bzly" ;		/* ��׼��Դ */
	public static final String ITEM_JC_DM_MS = "jc_dm_ms" ;			/* ������������ */
	
	/**
	 * ���캯��
	 */
	public VoGzDmJcdm()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoGzDmJcdm(DataBus value)
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

	/* �������� : String */
	public String getJc_dm_dm()
	{
		return getValue( ITEM_JC_DM_DM );
	}

	public void setJc_dm_dm( String jc_dm_dm1 )
	{
		setValue( ITEM_JC_DM_DM, jc_dm_dm1 );
	}

	/* ������������ : String */
	public String getJc_dm_mc()
	{
		return getValue( ITEM_JC_DM_MC );
	}

	public void setJc_dm_mc( String jc_dm_mc1 )
	{
		setValue( ITEM_JC_DM_MC, jc_dm_mc1 );
	}

	/* ��׼��Դ : String */
	public String getJc_dm_bzly()
	{
		return getValue( ITEM_JC_DM_BZLY );
	}

	public void setJc_dm_bzly( String jc_dm_bzly1 )
	{
		setValue( ITEM_JC_DM_BZLY, jc_dm_bzly1 );
	}

	/* ������������ : String */
	public String getJc_dm_ms()
	{
		return getValue( ITEM_JC_DM_MS );
	}

	public void setJc_dm_ms( String jc_dm_ms1 )
	{
		setValue( ITEM_JC_DM_MS, jc_dm_ms1 );
	}

}

