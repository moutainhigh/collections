package com.gwssi.dw.aic.bj.xb.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[xb_report_base]�����ݶ�����
 * @author Administrator
 *
 */
public class VoXbReportBaseSelectKey extends VoBase
{
	private static final long serialVersionUID = 200809170932520003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_RPT_REG_DATE = "rpt_reg_date" ;	/* �Ǽ����� */
	public static final String ITEM_ENT_NAME = "ent_name" ;			/* ���߷����� */
	public static final String ITEM_REG_NO = "reg_no" ;				/* ���߷�ע��� */
	public static final String ITEM_MDSE_NAME = "mdse_name" ;		/* ��Ʒ/�������� */
	
	/**
	 * ���캯��
	 */
	public VoXbReportBaseSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoXbReportBaseSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* �Ǽ����� : String */
	public String getRpt_reg_date()
	{
		return getValue( ITEM_RPT_REG_DATE );
	}

	public void setRpt_reg_date( String rpt_reg_date1 )
	{
		setValue( ITEM_RPT_REG_DATE, rpt_reg_date1 );
	}

	/* ���߷����� : String */
	public String getEnt_name()
	{
		return getValue( ITEM_ENT_NAME );
	}

	public void setEnt_name( String ent_name1 )
	{
		setValue( ITEM_ENT_NAME, ent_name1 );
	}

	/* ���߷�ע��� : String */
	public String getReg_no()
	{
		return getValue( ITEM_REG_NO );
	}

	public void setReg_no( String reg_no1 )
	{
		setValue( ITEM_REG_NO, reg_no1 );
	}

	/* ��Ʒ/�������� : String */
	public String getMdse_name()
	{
		return getValue( ITEM_MDSE_NAME );
	}

	public void setMdse_name( String mdse_name1 )
	{
		setValue( ITEM_MDSE_NAME, mdse_name1 );
	}

}

