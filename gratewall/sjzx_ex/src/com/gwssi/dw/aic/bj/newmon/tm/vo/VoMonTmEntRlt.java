package com.gwssi.dw.aic.bj.newmon.tm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoMonTmEntRlt extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_MON_TM_ENT_RLT_ID = "mon_tm_ent_rlt_id";			/* 商标企业关联维护表ID */
	public static final String ITEM_TM_REG_ID = "tm_reg_id";			/* 商标注册证号 */
	public static final String ITEM_IDY_DATE = "idy_date";			/* 认定日期 */
	public static final String ITEM_IDY_NO = "idy_no";			/* 认定方式代码(dm) */
	public static final String ITEM_IDY_NAME = "idy_name";			/* 认定方式名称 */
	public static final String ITEM_EFF_FROM = "eff_from";			/* 有效期限自 */
	public static final String ITEM_EFF_TO = "eff_to";			/* 有效期限至 */
	public static final String ITEM_IDY_SER = "idy_ser";			/* 认定商品/服务 */
	public static final String ITEM_ENT_REG_NO = "ent_reg_no";			/* 企业注册证号 */
	public static final String ITEM_ENT_NAME = "ent_name";			/* 企业名称 */
	public static final String ITEM_PRO_CITY_NAME = "pro_city_name";			/* 省市名称 */
	public static final String ITEM_PRO_CITY_NO = "pro_city_no";			/* 省市代码 */
	public static final String ITEM_ADM_ORG_ID = "adm_org_id";			/* 管辖分局id(dm) */
	public static final String ITEM_ADM_ORG_NAME = "adm_org_name";			/* 管辖分局名称 */
	public static final String ITEM_TOP_BRA_STATE = "top_bra_state";			/* 驰著名商标状态(dm) */
	public static final String ITEM_TOP_BRA_SIG = "top_bra_sig";			/* 驰著名商标标识(dm) */
	public static final String ITEM_DEP_NO = "dep_no";			/* 部门代码(dm) */
	public static final String ITEM_TM_REG_DATE = "tm_reg_date";			/* 注册日期 */

	public VoMonTmEntRlt(DataBus value)
	{
		super(value);
	}

	public VoMonTmEntRlt()
	{
		super();
	}

	/* 商标企业关联维护表ID */
	public String getMon_tm_ent_rlt_id()
	{
		return getValue( ITEM_MON_TM_ENT_RLT_ID );
	}

	public void setMon_tm_ent_rlt_id( String mon_tm_ent_rlt_id1 )
	{
		setValue( ITEM_MON_TM_ENT_RLT_ID, mon_tm_ent_rlt_id1 );
	}

	/* 商标注册证号 */
	public String getTm_reg_id()
	{
		return getValue( ITEM_TM_REG_ID );
	}

	public void setTm_reg_id( String tm_reg_id1 )
	{
		setValue( ITEM_TM_REG_ID, tm_reg_id1 );
	}

	/* 认定日期 */
	public String getIdy_date()
	{
		return getValue( ITEM_IDY_DATE );
	}

	public void setIdy_date( String idy_date1 )
	{
		setValue( ITEM_IDY_DATE, idy_date1 );
	}

	/* 认定方式代码(dm) */
	public String getIdy_no()
	{
		return getValue( ITEM_IDY_NO );
	}

	public void setIdy_no( String idy_no1 )
	{
		setValue( ITEM_IDY_NO, idy_no1 );
	}

	/* 认定方式名称 */
	public String getIdy_name()
	{
		return getValue( ITEM_IDY_NAME );
	}

	public void setIdy_name( String idy_name1 )
	{
		setValue( ITEM_IDY_NAME, idy_name1 );
	}

	/* 有效期限自 */
	public String getEff_from()
	{
		return getValue( ITEM_EFF_FROM );
	}

	public void setEff_from( String eff_from1 )
	{
		setValue( ITEM_EFF_FROM, eff_from1 );
	}

	/* 有效期限至 */
	public String getEff_to()
	{
		return getValue( ITEM_EFF_TO );
	}

	public void setEff_to( String eff_to1 )
	{
		setValue( ITEM_EFF_TO, eff_to1 );
	}

	/* 认定商品/服务 */
	public String getIdy_ser()
	{
		return getValue( ITEM_IDY_SER );
	}

	public void setIdy_ser( String idy_ser1 )
	{
		setValue( ITEM_IDY_SER, idy_ser1 );
	}

	/* 企业注册证号 */
	public String getEnt_reg_no()
	{
		return getValue( ITEM_ENT_REG_NO );
	}

	public void setEnt_reg_no( String ent_reg_no1 )
	{
		setValue( ITEM_ENT_REG_NO, ent_reg_no1 );
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

	/* 省市名称 */
	public String getPro_city_name()
	{
		return getValue( ITEM_PRO_CITY_NAME );
	}

	public void setPro_city_name( String pro_city_name1 )
	{
		setValue( ITEM_PRO_CITY_NAME, pro_city_name1 );
	}

	/* 省市代码 */
	public String getPro_city_no()
	{
		return getValue( ITEM_PRO_CITY_NO );
	}

	public void setPro_city_no( String pro_city_no1 )
	{
		setValue( ITEM_PRO_CITY_NO, pro_city_no1 );
	}

	/* 管辖分局id(dm) */
	public String getAdm_org_id()
	{
		return getValue( ITEM_ADM_ORG_ID );
	}

	public void setAdm_org_id( String adm_org_id1 )
	{
		setValue( ITEM_ADM_ORG_ID, adm_org_id1 );
	}

	/* 管辖分局名称 */
	public String getAdm_org_name()
	{
		return getValue( ITEM_ADM_ORG_NAME );
	}

	public void setAdm_org_name( String adm_org_name1 )
	{
		setValue( ITEM_ADM_ORG_NAME, adm_org_name1 );
	}

	/* 驰著名商标状态(dm) */
	public String getTop_bra_state()
	{
		return getValue( ITEM_TOP_BRA_STATE );
	}

	public void setTop_bra_state( String top_bra_state1 )
	{
		setValue( ITEM_TOP_BRA_STATE, top_bra_state1 );
	}

	/* 驰著名商标标识(dm) */
	public String getTop_bra_sig()
	{
		return getValue( ITEM_TOP_BRA_SIG );
	}

	public void setTop_bra_sig( String top_bra_sig1 )
	{
		setValue( ITEM_TOP_BRA_SIG, top_bra_sig1 );
	}

	/* 部门代码(dm) */
	public String getDep_no()
	{
		return getValue( ITEM_DEP_NO );
	}

	public void setDep_no( String dep_no1 )
	{
		setValue( ITEM_DEP_NO, dep_no1 );
	}

	/* 注册日期 */
	public String getTm_reg_date()
	{
		return getValue( ITEM_TM_REG_DATE );
	}

	public void setTm_reg_date( String tm_reg_date1 )
	{
		setValue( ITEM_TM_REG_DATE, tm_reg_date1 );
	}

}

