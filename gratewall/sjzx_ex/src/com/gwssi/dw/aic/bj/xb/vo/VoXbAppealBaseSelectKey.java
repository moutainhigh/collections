package com.gwssi.dw.aic.bj.xb.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[xb_appeal_base]的数据对象类
 * @author Administrator
 *
 */
public class VoXbAppealBaseSelectKey extends VoBase
{
	private static final long serialVersionUID = 200809111008440003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_APL_REG_DATE = "apl_reg_date" ;	/* 登记日期 */
	public static final String ITEM_REG_NO = "reg_no" ;				/* 被诉方注册号 */
	public static final String ITEM_ENT_NAME = "ent_name" ;			/* 被诉方名称 */
	public static final String ITEM_MDSE_NAME = "mdse_name" ;		/* 商品/服务名称 */
	
	/**
	 * 构造函数
	 */
	public VoXbAppealBaseSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoXbAppealBaseSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 登记日期 : String */
	public String getApl_reg_date()
	{
		return getValue( ITEM_APL_REG_DATE );
	}

	public void setApl_reg_date( String apl_reg_date1 )
	{
		setValue( ITEM_APL_REG_DATE, apl_reg_date1 );
	}

	/* 被诉方注册号 : String */
	public String getReg_no()
	{
		return getValue( ITEM_REG_NO );
	}

	public void setReg_no( String reg_no1 )
	{
		setValue( ITEM_REG_NO, reg_no1 );
	}

	/* 被诉方名称 : String */
	public String getEnt_name()
	{
		return getValue( ITEM_ENT_NAME );
	}

	public void setEnt_name( String ent_name1 )
	{
		setValue( ITEM_ENT_NAME, ent_name1 );
	}

	/* 商品/服务名称 : String */
	public String getMdse_name()
	{
		return getValue( ITEM_MDSE_NAME );
	}

	public void setMdse_name( String mdse_name1 )
	{
		setValue( ITEM_MDSE_NAME, mdse_name1 );
	}

}

