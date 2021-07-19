package com.gwssi.dw.aic.bj.xb.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[xb_appeal_base]�����ݶ�����
 * @author Administrator
 *
 */
public class VoXbAppealBaseSelectKey extends VoBase
{
	private static final long serialVersionUID = 200809111008440003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_APL_REG_DATE = "apl_reg_date" ;	/* �Ǽ����� */
	public static final String ITEM_REG_NO = "reg_no" ;				/* ���߷�ע��� */
	public static final String ITEM_ENT_NAME = "ent_name" ;			/* ���߷����� */
	public static final String ITEM_MDSE_NAME = "mdse_name" ;		/* ��Ʒ/�������� */
	
	/**
	 * ���캯��
	 */
	public VoXbAppealBaseSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoXbAppealBaseSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* �Ǽ����� : String */
	public String getApl_reg_date()
	{
		return getValue( ITEM_APL_REG_DATE );
	}

	public void setApl_reg_date( String apl_reg_date1 )
	{
		setValue( ITEM_APL_REG_DATE, apl_reg_date1 );
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

	/* ���߷����� : String */
	public String getEnt_name()
	{
		return getValue( ITEM_ENT_NAME );
	}

	public void setEnt_name( String ent_name1 )
	{
		setValue( ITEM_ENT_NAME, ent_name1 );
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

