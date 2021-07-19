package com.gwssi.dw.metadata.msurunit.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[gz_zb_jldw]�����ݶ�����
 * @author Administrator
 *
 */
public class VoGzZbJldwSelectKey extends VoBase
{
	private static final long serialVersionUID = 200707251356150015L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_JLDW_DM = "jldw_dm" ;			/* ������λ���� */
	public static final String ITEM_JLDW_CN_MC = "jldw_cn_mc" ;			/* ������λ�������� */
	public static final String ITEM_DWLB_CN_MC = "dwlb_cn_mc" ;		/* ��λ����������� */
	/**
	 * ���캯��
	 */
	public VoGzZbJldwSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoGzZbJldwSelectKey(DataBus value)
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
	
	
	/* ������λ�������� : String */
	public String getJldw_cn_mc()
	{
		return getValue( ITEM_JLDW_CN_MC);
	}
	
	public void setJldw_cn_mc( String jldw_cn_mc1 )
	{
		setValue( ITEM_JLDW_CN_MC, jldw_cn_mc1 );
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
	
}

