package com.gwssi.dw.aic.bj.gjcx.fhcx.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[v_reg_bus_ent_que]�����ݶ�����
 * @author Administrator
 *
 */
public class VoVRegBusEntQueSelectKey extends VoBase
{
	private static final long serialVersionUID = 200809101405040003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_ENT_NAME = "ent_name" ;			/* ��ҵ���� */
	public static final String ITEM_REG_NO = "reg_no" ;				/* ע��� */
	public static final String ITEM_CORP_RPT = "corp_rpt" ;			/* ���������� */
	public static final String ITEM_DOM = "dom" ;					/* ס�� */
	public static final String ITEM_PT_BUS_SCOPE = "pt_bus_scope" ;	/* ��Ӫ��Χ */
	public static final String ITEM_REG_CAP = "reg_cap" ;			/* ע���ʱ� */
	public static final String ITEM_EST_DATE_START = "est_date_start" ;	/* �������� */
	public static final String ITEM_EST_DATE_END = "est_date_end" ;	/* �������� */
	public static final String ITEM_LOCAL_ADM = "local_adm" ;		/* ���ش��� */
	public static final String ITEM_ENT_SORT = "ent_sort" ;			/* ��ҵ���� */
	public static final String ITEM_INDUSTRY_CO = "industry_co" ;	/* ��ҵ���� */
	public static final String ITEM_ENT_STATE = "ent_state" ;		/* ��ҵ״̬ */
	public static final String ITEM_ENT_TYPE = "ent_type" ;			/* ��ҵ���� */
	
	/**
	 * ���캯��
	 */
	public VoVRegBusEntQueSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoVRegBusEntQueSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ��ҵ���� : String */
	public String getEnt_name()
	{
		return getValue( ITEM_ENT_NAME );
	}

	public void setEnt_name( String ent_name1 )
	{
		setValue( ITEM_ENT_NAME, ent_name1 );
	}

	/* ע��� : String */
	public String getReg_no()
	{
		return getValue( ITEM_REG_NO );
	}

	public void setReg_no( String reg_no1 )
	{
		setValue( ITEM_REG_NO, reg_no1 );
	}

	/* ���������� : String */
	public String getCorp_rpt()
	{
		return getValue( ITEM_CORP_RPT );
	}

	public void setCorp_rpt( String corp_rpt1 )
	{
		setValue( ITEM_CORP_RPT, corp_rpt1 );
	}

	/* ס�� : String */
	public String getDom()
	{
		return getValue( ITEM_DOM );
	}

	public void setDom( String dom1 )
	{
		setValue( ITEM_DOM, dom1 );
	}

	/* ��Ӫ��Χ : String */
	public String getPt_bus_scope()
	{
		return getValue( ITEM_PT_BUS_SCOPE );
	}

	public void setPt_bus_scope( String pt_bus_scope1 )
	{
		setValue( ITEM_PT_BUS_SCOPE, pt_bus_scope1 );
	}

	/* ע���ʱ� : String */
	public String getReg_cap()
	{
		return getValue( ITEM_REG_CAP );
	}

	public void setReg_cap( String reg_cap1 )
	{
		setValue( ITEM_REG_CAP, reg_cap1 );
	}

	/* �������� : String */
	public String getEst_date_start()
	{
		return getValue( ITEM_EST_DATE_START );
	}

	public void setEst_date_start( String est_date_start1 )
	{
		setValue( ITEM_EST_DATE_START, est_date_start1 );
	}

	/* �������� : String */
	public String getEst_date_end()
	{
		return getValue( ITEM_EST_DATE_END );
	}

	public void setEst_date_end( String est_date_end1 )
	{
		setValue( ITEM_EST_DATE_END, est_date_end1 );
	}

	/* ���ش��� : String */
	public String getLocal_adm()
	{
		return getValue( ITEM_LOCAL_ADM );
	}

	public void setLocal_adm( String local_adm1 )
	{
		setValue( ITEM_LOCAL_ADM, local_adm1 );
	}

	/* ��ҵ���� : String */
	public String getEnt_sort()
	{
		return getValue( ITEM_ENT_SORT );
	}

	public void setEnt_sort( String ent_sort1 )
	{
		setValue( ITEM_ENT_SORT, ent_sort1 );
	}

	/* ��ҵ���� : String */
	public String getIndustry_co()
	{
		return getValue( ITEM_INDUSTRY_CO );
	}

	public void setIndustry_co( String industry_co1 )
	{
		setValue( ITEM_INDUSTRY_CO, industry_co1 );
	}

	/* ��ҵ״̬ : String */
	public String getEnt_state()
	{
		return getValue( ITEM_ENT_STATE );
	}

	public void setEnt_state( String ent_state1 )
	{
		setValue( ITEM_ENT_STATE, ent_state1 );
	}

	/* ��ҵ���� : String */
	public String getEnt_type()
	{
		return getValue( ITEM_ENT_TYPE );
	}

	public void setEnt_type( String ent_type1 )
	{
		setValue( ITEM_ENT_TYPE, ent_type1 );
	}

}

