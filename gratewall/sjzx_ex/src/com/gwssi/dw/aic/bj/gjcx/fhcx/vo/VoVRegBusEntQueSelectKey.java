package com.gwssi.dw.aic.bj.gjcx.fhcx.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[v_reg_bus_ent_que]的数据对象类
 * @author Administrator
 *
 */
public class VoVRegBusEntQueSelectKey extends VoBase
{
	private static final long serialVersionUID = 200809101405040003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_ENT_NAME = "ent_name" ;			/* 企业名称 */
	public static final String ITEM_REG_NO = "reg_no" ;				/* 注册号 */
	public static final String ITEM_CORP_RPT = "corp_rpt" ;			/* 法定代表人 */
	public static final String ITEM_DOM = "dom" ;					/* 住所 */
	public static final String ITEM_PT_BUS_SCOPE = "pt_bus_scope" ;	/* 经营范围 */
	public static final String ITEM_REG_CAP = "reg_cap" ;			/* 注册资本 */
	public static final String ITEM_EST_DATE_START = "est_date_start" ;	/* 成立日期 */
	public static final String ITEM_EST_DATE_END = "est_date_end" ;	/* 成立日期 */
	public static final String ITEM_LOCAL_ADM = "local_adm" ;		/* 区县代码 */
	public static final String ITEM_ENT_SORT = "ent_sort" ;			/* 企业分类 */
	public static final String ITEM_INDUSTRY_CO = "industry_co" ;	/* 行业代码 */
	public static final String ITEM_ENT_STATE = "ent_state" ;		/* 企业状态 */
	public static final String ITEM_ENT_TYPE = "ent_type" ;			/* 企业类型 */
	
	/**
	 * 构造函数
	 */
	public VoVRegBusEntQueSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoVRegBusEntQueSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 企业名称 : String */
	public String getEnt_name()
	{
		return getValue( ITEM_ENT_NAME );
	}

	public void setEnt_name( String ent_name1 )
	{
		setValue( ITEM_ENT_NAME, ent_name1 );
	}

	/* 注册号 : String */
	public String getReg_no()
	{
		return getValue( ITEM_REG_NO );
	}

	public void setReg_no( String reg_no1 )
	{
		setValue( ITEM_REG_NO, reg_no1 );
	}

	/* 法定代表人 : String */
	public String getCorp_rpt()
	{
		return getValue( ITEM_CORP_RPT );
	}

	public void setCorp_rpt( String corp_rpt1 )
	{
		setValue( ITEM_CORP_RPT, corp_rpt1 );
	}

	/* 住所 : String */
	public String getDom()
	{
		return getValue( ITEM_DOM );
	}

	public void setDom( String dom1 )
	{
		setValue( ITEM_DOM, dom1 );
	}

	/* 经营范围 : String */
	public String getPt_bus_scope()
	{
		return getValue( ITEM_PT_BUS_SCOPE );
	}

	public void setPt_bus_scope( String pt_bus_scope1 )
	{
		setValue( ITEM_PT_BUS_SCOPE, pt_bus_scope1 );
	}

	/* 注册资本 : String */
	public String getReg_cap()
	{
		return getValue( ITEM_REG_CAP );
	}

	public void setReg_cap( String reg_cap1 )
	{
		setValue( ITEM_REG_CAP, reg_cap1 );
	}

	/* 成立日期 : String */
	public String getEst_date_start()
	{
		return getValue( ITEM_EST_DATE_START );
	}

	public void setEst_date_start( String est_date_start1 )
	{
		setValue( ITEM_EST_DATE_START, est_date_start1 );
	}

	/* 成立日期 : String */
	public String getEst_date_end()
	{
		return getValue( ITEM_EST_DATE_END );
	}

	public void setEst_date_end( String est_date_end1 )
	{
		setValue( ITEM_EST_DATE_END, est_date_end1 );
	}

	/* 区县代码 : String */
	public String getLocal_adm()
	{
		return getValue( ITEM_LOCAL_ADM );
	}

	public void setLocal_adm( String local_adm1 )
	{
		setValue( ITEM_LOCAL_ADM, local_adm1 );
	}

	/* 企业分类 : String */
	public String getEnt_sort()
	{
		return getValue( ITEM_ENT_SORT );
	}

	public void setEnt_sort( String ent_sort1 )
	{
		setValue( ITEM_ENT_SORT, ent_sort1 );
	}

	/* 行业代码 : String */
	public String getIndustry_co()
	{
		return getValue( ITEM_INDUSTRY_CO );
	}

	public void setIndustry_co( String industry_co1 )
	{
		setValue( ITEM_INDUSTRY_CO, industry_co1 );
	}

	/* 企业状态 : String */
	public String getEnt_state()
	{
		return getValue( ITEM_ENT_STATE );
	}

	public void setEnt_state( String ent_state1 )
	{
		setValue( ITEM_ENT_STATE, ent_state1 );
	}

	/* 企业类型 : String */
	public String getEnt_type()
	{
		return getValue( ITEM_ENT_TYPE );
	}

	public void setEnt_type( String ent_type1 )
	{
		setValue( ITEM_ENT_TYPE, ent_type1 );
	}

}

