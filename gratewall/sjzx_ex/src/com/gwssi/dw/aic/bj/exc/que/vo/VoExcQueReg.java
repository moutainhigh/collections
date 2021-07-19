package com.gwssi.dw.aic.bj.exc.que.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoExcQueReg extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_EXC_QUE_REG_ID = "exc_que_reg_id";			/* 质检法人库ID */
	public static final String ITEM_ORGAN_CODE = "organ_code";			/* 组织机构代码(新) */
	public static final String ITEM_ENT_NAME = "ent_name";			/* 企业名称 */
	public static final String ITEM_DOM = "dom";			/* 住所 */
	public static final String ITEM_ENT_TYPE_CODE = "ent_type_code";			/* 组织机构类型代码 */
	public static final String ITEM_CORP_RPT = "corp_rpt";			/* 法定代表人 */
	public static final String ITEM_REG_NO = "reg_no";			/* 企业注册号 */
	public static final String ITEM_INDUSTRY_CODE = "industry_code";			/* 行业代码 */
	public static final String ITEM_TEL = "tel";			/* 联系电话 */
	public static final String ITEM_DISTR_CODE = "distr_code";			/* 行政区划代码 */
	public static final String ITEM_ENT_TYPE = "ent_type";			/* 企业类型 */
	public static final String ITEM_OP_SCOPE = "op_scope";			/* 许可经营范围 */
	public static final String ITEM_EST_DATE = "est_date";			/* 成立日期 */
	public static final String ITEM_ENT_STATE = "ent_state";			/* 企业状态 */
	public static final String ITEM_DATA_SOU = "data_sou";			/* 数据来源 */
	public static final String ITEM_CIV_ID = "civ_id";			/* 民政关联ID */

	public VoExcQueReg(DataBus value)
	{
		super(value);
	}

	public VoExcQueReg()
	{
		super();
	}

	/* 质检法人库ID */
	public String getExc_que_reg_id()
	{
		return getValue( ITEM_EXC_QUE_REG_ID );
	}

	public void setExc_que_reg_id( String exc_que_reg_id1 )
	{
		setValue( ITEM_EXC_QUE_REG_ID, exc_que_reg_id1 );
	}

	/* 组织机构代码(新) */
	public String getOrgan_code()
	{
		return getValue( ITEM_ORGAN_CODE );
	}

	public void setOrgan_code( String organ_code1 )
	{
		setValue( ITEM_ORGAN_CODE, organ_code1 );
	}

	/* 企业名称 */
	public String getEnt_name()
	{
		return getValue( ITEM_ENT_NAME );
	}

	public void setEnt_name( String ent_name1 )
	{
		setValue( ITEM_ENT_NAME, ent_name1 );
	}

	/* 住所 */
	public String getDom()
	{
		return getValue( ITEM_DOM );
	}

	public void setDom( String dom1 )
	{
		setValue( ITEM_DOM, dom1 );
	}

	/* 组织机构类型代码 */
	public String getEnt_type_code()
	{
		return getValue( ITEM_ENT_TYPE_CODE );
	}

	public void setEnt_type_code( String ent_type_code1 )
	{
		setValue( ITEM_ENT_TYPE_CODE, ent_type_code1 );
	}

	/* 法定代表人 */
	public String getCorp_rpt()
	{
		return getValue( ITEM_CORP_RPT );
	}

	public void setCorp_rpt( String corp_rpt1 )
	{
		setValue( ITEM_CORP_RPT, corp_rpt1 );
	}

	/* 企业注册号 */
	public String getReg_no()
	{
		return getValue( ITEM_REG_NO );
	}

	public void setReg_no( String reg_no1 )
	{
		setValue( ITEM_REG_NO, reg_no1 );
	}

	/* 行业代码 */
	public String getIndustry_code()
	{
		return getValue( ITEM_INDUSTRY_CODE );
	}

	public void setIndustry_code( String industry_code1 )
	{
		setValue( ITEM_INDUSTRY_CODE, industry_code1 );
	}

	/* 联系电话 */
	public String getTel()
	{
		return getValue( ITEM_TEL );
	}

	public void setTel( String tel1 )
	{
		setValue( ITEM_TEL, tel1 );
	}

	/* 行政区划代码 */
	public String getDistr_code()
	{
		return getValue( ITEM_DISTR_CODE );
	}

	public void setDistr_code( String distr_code1 )
	{
		setValue( ITEM_DISTR_CODE, distr_code1 );
	}

	/* 企业类型 */
	public String getEnt_type()
	{
		return getValue( ITEM_ENT_TYPE );
	}

	public void setEnt_type( String ent_type1 )
	{
		setValue( ITEM_ENT_TYPE, ent_type1 );
	}

	/* 许可经营范围 */
	public String getOp_scope()
	{
		return getValue( ITEM_OP_SCOPE );
	}

	public void setOp_scope( String op_scope1 )
	{
		setValue( ITEM_OP_SCOPE, op_scope1 );
	}

	/* 成立日期 */
	public String getEst_date()
	{
		return getValue( ITEM_EST_DATE );
	}

	public void setEst_date( String est_date1 )
	{
		setValue( ITEM_EST_DATE, est_date1 );
	}

	/* 企业状态 */
	public String getEnt_state()
	{
		return getValue( ITEM_ENT_STATE );
	}

	public void setEnt_state( String ent_state1 )
	{
		setValue( ITEM_ENT_STATE, ent_state1 );
	}

	/* 数据来源 */
	public String getData_sou()
	{
		return getValue( ITEM_DATA_SOU );
	}

	public void setData_sou( String data_sou1 )
	{
		setValue( ITEM_DATA_SOU, data_sou1 );
	}

	/* 民政关联ID */
	public String getCiv_id()
	{
		return getValue( ITEM_CIV_ID );
	}

	public void setCiv_id( String civ_id1 )
	{
		setValue( ITEM_CIV_ID, civ_id1 );
	}

}

