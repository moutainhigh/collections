package com.gwssi.dw.metadata.msurunit.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[gz_zb_jldw_fl]�����ݶ�����
 * @author Administrator
 *
 */
public class VoGzZbJldwFlSelectKey extends VoBase
{
	private static final long serialVersionUID = 200707251356150011L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_DWLB_DM = "dwlb_dm" ;			/* ��λ������ */
	public static final String ITEM_DWLB_CN_MC = "dwlb_cn_mc" ;		/* ��λ����������� */
	
	/**
	 * ���캯��
	 */
	public VoGzZbJldwFlSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoGzZbJldwFlSelectKey(DataBus value)
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

}

