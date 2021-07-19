package com.gwssi.dw.aic.bj.newmon.tm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[mon_tm_bas_info]的数据对象类
 * @author Administrator
 *
 */
public class VoMonTmBasInfo extends VoBase
{
	private static final long serialVersionUID = 200811171515250002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_MON_TM_BAS_INFO_ID = "mon_tm_bas_info_id" ;	/* mon_tm_bas_info_id */
	public static final String ITEM_TM_ENT_ID = "tm_ent_id" ;		/* tm_ent_id */
	public static final String ITEM_TM_REG_ID = "tm_reg_id" ;		/* 商标注册证号 */
	public static final String ITEM_TM_TYPE = "tm_type" ;			/* 商标类别(dm) */
	public static final String ITEM_TM_REG_DATE = "tm_reg_date" ;	/* 注册日期 */
	public static final String ITEM_PRIORITY = "priority" ;			/* 优先权 */
	public static final String ITEM_TM_NAME = "tm_name" ;			/* 商标名称 */
	public static final String ITEM_EFF_FROM = "eff_from" ;			/* 有效期限自 */
	public static final String ITEM_EFF_TO = "eff_to" ;				/* 有效期限至 */
	public static final String ITEM_ASS_COLOR = "ass_color" ;		/* 指定颜色 */
	public static final String ITEM_TM_SIGN = "tm_sign" ;			/* 商标标识(dm) */
	public static final String ITEM_ABA_PROP = "aba_prop" ;			/* 放弃专用权 */
	public static final String ITEM_TM_AGT = "tm_agt" ;				/* 商标代理人 */
	public static final String ITEM_TM_REGER = "tm_reger" ;			/* 商标注册人 */
	public static final String ITEM_TM_REG_ENG_NAME = "tm_reg_eng_name" ;	/* 商标注册人英文名 */
	public static final String ITEM_MAR_ADDR = "mar_addr" ;			/* 地址 */
	public static final String ITEM_ADD_ENG = "add_eng" ;			/* 地址英文 */
	public static final String ITEM_G_OR_S = "g_or_s" ;				/* 商品或者服务 */
	public static final String ITEM_PROMPT = "prompt" ;				/* 续展提示(dm) */
	public static final String ITEM_PROMPT_DATE = "prompt_date" ;	/* 提示日期 */
	public static final String ITEM_D_OR_F = "d_or_f" ;				/* 国别(dm) */
	public static final String ITEM_COUNTRY = "country" ;			/* 国家(dm) */
	public static final String ITEM_FORB_SELL = "forb_sell" ;		/* 是否禁售(dm) */
	public static final String ITEM_FRT_EXA_ISSUE = "frt_exa_issue" ;	/* 初审期号 */
	public static final String ITEM_REG_ISSUE = "reg_issue" ;		/* 注册期号 */
	public static final String ITEM_PRIORITY_DATE = "priority_date" ;	/* 优先权日期 */
	public static final String ITEM_MAIN_ID = "main_id" ;			/* main_id */
	
	/**
	 * 构造函数
	 */
	public VoMonTmBasInfo()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoMonTmBasInfo(DataBus value)
	{
		super(value);
	}
	
	/* mon_tm_bas_info_id : String */
	public String getMon_tm_bas_info_id()
	{
		return getValue( ITEM_MON_TM_BAS_INFO_ID );
	}

	public void setMon_tm_bas_info_id( String mon_tm_bas_info_id1 )
	{
		setValue( ITEM_MON_TM_BAS_INFO_ID, mon_tm_bas_info_id1 );
	}

	/* tm_ent_id : String */
	public String getTm_ent_id()
	{
		return getValue( ITEM_TM_ENT_ID );
	}

	public void setTm_ent_id( String tm_ent_id1 )
	{
		setValue( ITEM_TM_ENT_ID, tm_ent_id1 );
	}

	/* 商标注册证号 : String */
	public String getTm_reg_id()
	{
		return getValue( ITEM_TM_REG_ID );
	}

	public void setTm_reg_id( String tm_reg_id1 )
	{
		setValue( ITEM_TM_REG_ID, tm_reg_id1 );
	}

	/* 商标类别(dm) : String */
	public String getTm_type()
	{
		return getValue( ITEM_TM_TYPE );
	}

	public void setTm_type( String tm_type1 )
	{
		setValue( ITEM_TM_TYPE, tm_type1 );
	}

	/* 注册日期 : String */
	public String getTm_reg_date()
	{
		return getValue( ITEM_TM_REG_DATE );
	}

	public void setTm_reg_date( String tm_reg_date1 )
	{
		setValue( ITEM_TM_REG_DATE, tm_reg_date1 );
	}

	/* 优先权 : String */
	public String getPriority()
	{
		return getValue( ITEM_PRIORITY );
	}

	public void setPriority( String priority1 )
	{
		setValue( ITEM_PRIORITY, priority1 );
	}

	/* 商标名称 : String */
	public String getTm_name()
	{
		return getValue( ITEM_TM_NAME );
	}

	public void setTm_name( String tm_name1 )
	{
		setValue( ITEM_TM_NAME, tm_name1 );
	}

	/* 有效期限自 : String */
	public String getEff_from()
	{
		return getValue( ITEM_EFF_FROM );
	}

	public void setEff_from( String eff_from1 )
	{
		setValue( ITEM_EFF_FROM, eff_from1 );
	}

	/* 有效期限至 : String */
	public String getEff_to()
	{
		return getValue( ITEM_EFF_TO );
	}

	public void setEff_to( String eff_to1 )
	{
		setValue( ITEM_EFF_TO, eff_to1 );
	}

	/* 指定颜色 : String */
	public String getAss_color()
	{
		return getValue( ITEM_ASS_COLOR );
	}

	public void setAss_color( String ass_color1 )
	{
		setValue( ITEM_ASS_COLOR, ass_color1 );
	}

	/* 商标标识(dm) : String */
	public String getTm_sign()
	{
		return getValue( ITEM_TM_SIGN );
	}

	public void setTm_sign( String tm_sign1 )
	{
		setValue( ITEM_TM_SIGN, tm_sign1 );
	}

	/* 放弃专用权 : String */
	public String getAba_prop()
	{
		return getValue( ITEM_ABA_PROP );
	}

	public void setAba_prop( String aba_prop1 )
	{
		setValue( ITEM_ABA_PROP, aba_prop1 );
	}

	/* 商标代理人 : String */
	public String getTm_agt()
	{
		return getValue( ITEM_TM_AGT );
	}

	public void setTm_agt( String tm_agt1 )
	{
		setValue( ITEM_TM_AGT, tm_agt1 );
	}

	/* 商标注册人 : String */
	public String getTm_reger()
	{
		return getValue( ITEM_TM_REGER );
	}

	public void setTm_reger( String tm_reger1 )
	{
		setValue( ITEM_TM_REGER, tm_reger1 );
	}

	/* 商标注册人英文名 : String */
	public String getTm_reg_eng_name()
	{
		return getValue( ITEM_TM_REG_ENG_NAME );
	}

	public void setTm_reg_eng_name( String tm_reg_eng_name1 )
	{
		setValue( ITEM_TM_REG_ENG_NAME, tm_reg_eng_name1 );
	}

	/* 地址 : String */
	public String getMar_addr()
	{
		return getValue( ITEM_MAR_ADDR );
	}

	public void setMar_addr( String mar_addr1 )
	{
		setValue( ITEM_MAR_ADDR, mar_addr1 );
	}

	/* 地址英文 : String */
	public String getAdd_eng()
	{
		return getValue( ITEM_ADD_ENG );
	}

	public void setAdd_eng( String add_eng1 )
	{
		setValue( ITEM_ADD_ENG, add_eng1 );
	}

	/* 商品或者服务 : String */
	public String getG_or_s()
	{
		return getValue( ITEM_G_OR_S );
	}

	public void setG_or_s( String g_or_s1 )
	{
		setValue( ITEM_G_OR_S, g_or_s1 );
	}

	/* 续展提示(dm) : String */
	public String getPrompt()
	{
		return getValue( ITEM_PROMPT );
	}

	public void setPrompt( String prompt1 )
	{
		setValue( ITEM_PROMPT, prompt1 );
	}

	/* 提示日期 : String */
	public String getPrompt_date()
	{
		return getValue( ITEM_PROMPT_DATE );
	}

	public void setPrompt_date( String prompt_date1 )
	{
		setValue( ITEM_PROMPT_DATE, prompt_date1 );
	}

	/* 国别(dm) : String */
	public String getD_or_f()
	{
		return getValue( ITEM_D_OR_F );
	}

	public void setD_or_f( String d_or_f1 )
	{
		setValue( ITEM_D_OR_F, d_or_f1 );
	}

	/* 国家(dm) : String */
	public String getCountry()
	{
		return getValue( ITEM_COUNTRY );
	}

	public void setCountry( String country1 )
	{
		setValue( ITEM_COUNTRY, country1 );
	}

	/* 是否禁售(dm) : String */
	public String getForb_sell()
	{
		return getValue( ITEM_FORB_SELL );
	}

	public void setForb_sell( String forb_sell1 )
	{
		setValue( ITEM_FORB_SELL, forb_sell1 );
	}

	/* 初审期号 : String */
	public String getFrt_exa_issue()
	{
		return getValue( ITEM_FRT_EXA_ISSUE );
	}

	public void setFrt_exa_issue( String frt_exa_issue1 )
	{
		setValue( ITEM_FRT_EXA_ISSUE, frt_exa_issue1 );
	}

	/* 注册期号 : String */
	public String getReg_issue()
	{
		return getValue( ITEM_REG_ISSUE );
	}

	public void setReg_issue( String reg_issue1 )
	{
		setValue( ITEM_REG_ISSUE, reg_issue1 );
	}

	/* 优先权日期 : String */
	public String getPriority_date()
	{
		return getValue( ITEM_PRIORITY_DATE );
	}

	public void setPriority_date( String priority_date1 )
	{
		setValue( ITEM_PRIORITY_DATE, priority_date1 );
	}

	/* main_id : String */
	public String getMain_id()
	{
		return getValue( ITEM_MAIN_ID );
	}

	public void setMain_id( String main_id1 )
	{
		setValue( ITEM_MAIN_ID, main_id1 );
	}

}

