package com.gwssi.dw.aic.bj.exc.que.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[exc_que_civ]的数据对象类
 * @author Administrator
 *
 */
public class VoExcQueCiv extends VoBase
{
	private static final long serialVersionUID = 200808291334510010L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_EXC_QUE_CIV_ID = "exc_que_civ_id" ;	/* 民政局扩展信息ID */
	public static final String ITEM_ORGAN_CODE = "organ_code" ;		/* 组织机构代码(新) */
	public static final String ITEM_SUPE_ORG = "supe_org" ;			/* 开办单位 */
	public static final String ITEM_REG_CAP = "reg_cap" ;			/* 注册资本 */
	public static final String ITEM_CAP_CUR = "cap_cur" ;			/* 币种 */
	public static final String ITEM_CARD_ORG = "card_org" ;			/* 登记证发证机构 */
	public static final String ITEM_CARD_DATE = "card_date" ;		/* 登记证有效日期 */
	public static final String ITEM_REVOK_DATE = "revok_date" ;		/* 吊销时间 */
	public static final String ITEM_CHAN_DATE = "chan_date" ;		/* 变更时间 */
	
	/**
	 * 构造函数
	 */
	public VoExcQueCiv()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoExcQueCiv(DataBus value)
	{
		super(value);
	}
	
	/* 民政局扩展信息ID : String */
	public String getExc_que_civ_id()
	{
		return getValue( ITEM_EXC_QUE_CIV_ID );
	}

	public void setExc_que_civ_id( String exc_que_civ_id1 )
	{
		setValue( ITEM_EXC_QUE_CIV_ID, exc_que_civ_id1 );
	}

	/* 组织机构代码(新) : String */
	public String getOrgan_code()
	{
		return getValue( ITEM_ORGAN_CODE );
	}

	public void setOrgan_code( String organ_code1 )
	{
		setValue( ITEM_ORGAN_CODE, organ_code1 );
	}

	/* 开办单位 : String */
	public String getSupe_org()
	{
		return getValue( ITEM_SUPE_ORG );
	}

	public void setSupe_org( String supe_org1 )
	{
		setValue( ITEM_SUPE_ORG, supe_org1 );
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

	/* 币种 : String */
	public String getCap_cur()
	{
		return getValue( ITEM_CAP_CUR );
	}

	public void setCap_cur( String cap_cur1 )
	{
		setValue( ITEM_CAP_CUR, cap_cur1 );
	}

	/* 登记证发证机构 : String */
	public String getCard_org()
	{
		return getValue( ITEM_CARD_ORG );
	}

	public void setCard_org( String card_org1 )
	{
		setValue( ITEM_CARD_ORG, card_org1 );
	}

	/* 登记证有效日期 : String */
	public String getCard_date()
	{
		return getValue( ITEM_CARD_DATE );
	}

	public void setCard_date( String card_date1 )
	{
		setValue( ITEM_CARD_DATE, card_date1 );
	}

	/* 吊销时间 : String */
	public String getRevok_date()
	{
		return getValue( ITEM_REVOK_DATE );
	}

	public void setRevok_date( String revok_date1 )
	{
		setValue( ITEM_REVOK_DATE, revok_date1 );
	}

	/* 变更时间 : String */
	public String getChan_date()
	{
		return getValue( ITEM_CHAN_DATE );
	}

	public void setChan_date( String chan_date1 )
	{
		setValue( ITEM_CHAN_DATE, chan_date1 );
	}

}

