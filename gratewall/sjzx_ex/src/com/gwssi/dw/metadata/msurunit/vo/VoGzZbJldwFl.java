package com.gwssi.dw.metadata.msurunit.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[gz_zb_jldw_fl]�����ݶ�����
 * @author Administrator
 *
 */
public class VoGzZbJldwFl extends VoBase
{
	private static final long serialVersionUID = 200707251356140010L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DWLB_DM = "dwlb_dm" ;			/* ��λ������ */
	public static final String ITEM_DWLB_CN_MC = "dwlb_cn_mc" ;		/* ��λ����������� */
	public static final String ITEM_DWLB_CN_MS = "dwlb_cn_ms" ;		/* ��λ����������� */
	public static final String ITEM_DWLB_EN_MC = "dwlb_en_mc" ;		/* ��λ���Ӣ������ */
	public static final String ITEM_DWLB_EN_MS = "dwlb_en_ms" ;		/* ��λ���Ӣ������ */
	
	/**
	 * ���캯��
	 */
	public VoGzZbJldwFl()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoGzZbJldwFl(DataBus value)
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

	/* ��λ����������� : String */
	public String getDwlb_cn_mc()
	{
		return getValue( ITEM_DWLB_CN_MC );
	}

	public void setDwlb_cn_mc( String dwlb_cn_mc1 )
	{
		setValue( ITEM_DWLB_CN_MC, dwlb_cn_mc1 );
	}

	/* ��λ����������� : String */
	public String getDwlb_cn_ms()
	{
		return getValue( ITEM_DWLB_CN_MS );
	}

	public void setDwlb_cn_ms( String dwlb_cn_ms1 )
	{
		setValue( ITEM_DWLB_CN_MS, dwlb_cn_ms1 );
	}

	/* ��λ���Ӣ������ : String */
	public String getDwlb_en_mc()
	{
		return getValue( ITEM_DWLB_EN_MC );
	}

	public void setDwlb_en_mc( String dwlb_en_mc1 )
	{
		setValue( ITEM_DWLB_EN_MC, dwlb_en_mc1 );
	}

	/* ��λ���Ӣ������ : String */
	public String getDwlb_en_ms()
	{
		return getValue( ITEM_DWLB_EN_MS );
	}

	public void setDwlb_en_ms( String dwlb_en_ms1 )
	{
		setValue( ITEM_DWLB_EN_MS, dwlb_en_ms1 );
	}

}

