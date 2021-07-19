package com.gwssi.dw.aic.bj.exc.que.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoExcQueReg extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_EXC_QUE_REG_ID = "exc_que_reg_id";			/* �ʼ취�˿�ID */
	public static final String ITEM_ORGAN_CODE = "organ_code";			/* ��֯��������(��) */
	public static final String ITEM_ENT_NAME = "ent_name";			/* ��ҵ���� */
	public static final String ITEM_DOM = "dom";			/* ס�� */
	public static final String ITEM_ENT_TYPE_CODE = "ent_type_code";			/* ��֯�������ʹ��� */
	public static final String ITEM_CORP_RPT = "corp_rpt";			/* ���������� */
	public static final String ITEM_REG_NO = "reg_no";			/* ��ҵע��� */
	public static final String ITEM_INDUSTRY_CODE = "industry_code";			/* ��ҵ���� */
	public static final String ITEM_TEL = "tel";			/* ��ϵ�绰 */
	public static final String ITEM_DISTR_CODE = "distr_code";			/* ������������ */
	public static final String ITEM_ENT_TYPE = "ent_type";			/* ��ҵ���� */
	public static final String ITEM_OP_SCOPE = "op_scope";			/* ��ɾ�Ӫ��Χ */
	public static final String ITEM_EST_DATE = "est_date";			/* �������� */
	public static final String ITEM_ENT_STATE = "ent_state";			/* ��ҵ״̬ */
	public static final String ITEM_DATA_SOU = "data_sou";			/* ������Դ */
	public static final String ITEM_CIV_ID = "civ_id";			/* ��������ID */

	public VoExcQueReg(DataBus value)
	{
		super(value);
	}

	public VoExcQueReg()
	{
		super();
	}

	/* �ʼ취�˿�ID */
	public String getExc_que_reg_id()
	{
		return getValue( ITEM_EXC_QUE_REG_ID );
	}

	public void setExc_que_reg_id( String exc_que_reg_id1 )
	{
		setValue( ITEM_EXC_QUE_REG_ID, exc_que_reg_id1 );
	}

	/* ��֯��������(��) */
	public String getOrgan_code()
	{
		return getValue( ITEM_ORGAN_CODE );
	}

	public void setOrgan_code( String organ_code1 )
	{
		setValue( ITEM_ORGAN_CODE, organ_code1 );
	}

	/* ��ҵ���� */
	public String getEnt_name()
	{
		return getValue( ITEM_ENT_NAME );
	}

	public void setEnt_name( String ent_name1 )
	{
		setValue( ITEM_ENT_NAME, ent_name1 );
	}

	/* ס�� */
	public String getDom()
	{
		return getValue( ITEM_DOM );
	}

	public void setDom( String dom1 )
	{
		setValue( ITEM_DOM, dom1 );
	}

	/* ��֯�������ʹ��� */
	public String getEnt_type_code()
	{
		return getValue( ITEM_ENT_TYPE_CODE );
	}

	public void setEnt_type_code( String ent_type_code1 )
	{
		setValue( ITEM_ENT_TYPE_CODE, ent_type_code1 );
	}

	/* ���������� */
	public String getCorp_rpt()
	{
		return getValue( ITEM_CORP_RPT );
	}

	public void setCorp_rpt( String corp_rpt1 )
	{
		setValue( ITEM_CORP_RPT, corp_rpt1 );
	}

	/* ��ҵע��� */
	public String getReg_no()
	{
		return getValue( ITEM_REG_NO );
	}

	public void setReg_no( String reg_no1 )
	{
		setValue( ITEM_REG_NO, reg_no1 );
	}

	/* ��ҵ���� */
	public String getIndustry_code()
	{
		return getValue( ITEM_INDUSTRY_CODE );
	}

	public void setIndustry_code( String industry_code1 )
	{
		setValue( ITEM_INDUSTRY_CODE, industry_code1 );
	}

	/* ��ϵ�绰 */
	public String getTel()
	{
		return getValue( ITEM_TEL );
	}

	public void setTel( String tel1 )
	{
		setValue( ITEM_TEL, tel1 );
	}

	/* ������������ */
	public String getDistr_code()
	{
		return getValue( ITEM_DISTR_CODE );
	}

	public void setDistr_code( String distr_code1 )
	{
		setValue( ITEM_DISTR_CODE, distr_code1 );
	}

	/* ��ҵ���� */
	public String getEnt_type()
	{
		return getValue( ITEM_ENT_TYPE );
	}

	public void setEnt_type( String ent_type1 )
	{
		setValue( ITEM_ENT_TYPE, ent_type1 );
	}

	/* ��ɾ�Ӫ��Χ */
	public String getOp_scope()
	{
		return getValue( ITEM_OP_SCOPE );
	}

	public void setOp_scope( String op_scope1 )
	{
		setValue( ITEM_OP_SCOPE, op_scope1 );
	}

	/* �������� */
	public String getEst_date()
	{
		return getValue( ITEM_EST_DATE );
	}

	public void setEst_date( String est_date1 )
	{
		setValue( ITEM_EST_DATE, est_date1 );
	}

	/* ��ҵ״̬ */
	public String getEnt_state()
	{
		return getValue( ITEM_ENT_STATE );
	}

	public void setEnt_state( String ent_state1 )
	{
		setValue( ITEM_ENT_STATE, ent_state1 );
	}

	/* ������Դ */
	public String getData_sou()
	{
		return getValue( ITEM_DATA_SOU );
	}

	public void setData_sou( String data_sou1 )
	{
		setValue( ITEM_DATA_SOU, data_sou1 );
	}

	/* ��������ID */
	public String getCiv_id()
	{
		return getValue( ITEM_CIV_ID );
	}

	public void setCiv_id( String civ_id1 )
	{
		setValue( ITEM_CIV_ID, civ_id1 );
	}

}

