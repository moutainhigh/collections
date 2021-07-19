package com.gwssi.dw.metadata.basecode.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[gz_dm_jcdm_fx]�����ݶ�����
 * @author Administrator
 *
 */
public class VoGzDmJcdmFx extends VoBase
{
	private static final long serialVersionUID = 200708271316210001L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_JCSJFX_ID = "jcsjfx_id" ;		/* �������ݷ���ID */
	public static final String ITEM_JC_DM_ID = "jc_dm_id" ;			/* ��������ID */
	public static final String ITEM_JCSJFX_DM = "jcsjfx_dm" ;		/* �������ݷ������ */
	public static final String ITEM_JCSJFX_MC = "jcsjfx_mc" ;		/* �������ݷ������� */
	public static final String ITEM_JCSJFX_CJM = "jcsjfx_cjm" ;		/* �������ݷ���㼶�� */
	public static final String ITEM_JCSJFX_FJD = "jcsjfx_fjd" ;		/* �������ݷ���ڵ���� */
	public static final String ITEM_SZCC = "szcc" ;					/* ���ڲ�� */
	public static final String ITEM_XSSX = "xssx" ;					/* ��ʾ˳�� */
	public static final String ITEM_SFMX = "sfmx" ;					/* �Ƿ���ϸ */
	public static final String ITEM_FX_MS = "fx_ms" ;				/* �������� */
	public static final String ITEM_SY_ZT = "sy_zt" ;				/* ʹ��״̬ */
	
	/**
	 * ���캯��
	 */
	public VoGzDmJcdmFx()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoGzDmJcdmFx(DataBus value)
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

	/* �������ݷ������ : String */
	public String getJcsjfx_dm()
	{
		return getValue( ITEM_JCSJFX_DM );
	}

	public void setJcsjfx_dm( String jcsjfx_dm1 )
	{
		setValue( ITEM_JCSJFX_DM, jcsjfx_dm1 );
	}

	/* �������ݷ������� : String */
	public String getJcsjfx_mc()
	{
		return getValue( ITEM_JCSJFX_MC );
	}

	public void setJcsjfx_mc( String jcsjfx_mc1 )
	{
		setValue( ITEM_JCSJFX_MC, jcsjfx_mc1 );
	}

	/* �������ݷ���㼶�� : String */
	public String getJcsjfx_cjm()
	{
		return getValue( ITEM_JCSJFX_CJM );
	}

	public void setJcsjfx_cjm( String jcsjfx_cjm1 )
	{
		setValue( ITEM_JCSJFX_CJM, jcsjfx_cjm1 );
	}

	/* �������ݷ���ڵ���� : String */
	public String getJcsjfx_fjd()
	{
		return getValue( ITEM_JCSJFX_FJD );
	}

	public void setJcsjfx_fjd( String jcsjfx_fjd1 )
	{
		setValue( ITEM_JCSJFX_FJD, jcsjfx_fjd1 );
	}

	/* ���ڲ�� : String */
	public String getSzcc()
	{
		return getValue( ITEM_SZCC );
	}

	public void setSzcc( String szcc1 )
	{
		setValue( ITEM_SZCC, szcc1 );
	}

	/* ��ʾ˳�� : String */
	public String getXssx()
	{
		return getValue( ITEM_XSSX );
	}

	public void setXssx( String xssx1 )
	{
		setValue( ITEM_XSSX, xssx1 );
	}

	/* �Ƿ���ϸ : String */
	public String getSfmx()
	{
		return getValue( ITEM_SFMX );
	}

	public void setSfmx( String sfmx1 )
	{
		setValue( ITEM_SFMX, sfmx1 );
	}

	/* �������� : String */
	public String getFx_ms()
	{
		return getValue( ITEM_FX_MS );
	}

	public void setFx_ms( String fx_ms1 )
	{
		setValue( ITEM_FX_MS, fx_ms1 );
	}

	/* ʹ��״̬ : String */
	public String getSy_zt()
	{
		return getValue( ITEM_SY_ZT );
	}

	public void setSy_zt( String sy_zt1 )
	{
		setValue( ITEM_SY_ZT, sy_zt1 );
	}

}

