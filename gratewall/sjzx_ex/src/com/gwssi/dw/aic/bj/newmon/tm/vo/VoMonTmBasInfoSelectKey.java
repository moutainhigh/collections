package com.gwssi.dw.aic.bj.newmon.tm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[mon_tm_bas_info]的数据对象类
 * @author Administrator
 *
 */
public class VoMonTmBasInfoSelectKey extends VoBase
{
	private static final long serialVersionUID = 200811171515250003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_TM_NAME = "tm_name" ;			/* 商标名称 */
	public static final String ITEM_TM_TYPE = "tm_type" ;			/* 商标类别(dm) */
	public static final String ITEM_TM_REG_ID = "tm_reg_id" ;		/* 商标注册证号 */
	
	/**
	 * 构造函数
	 */
	public VoMonTmBasInfoSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoMonTmBasInfoSelectKey(DataBus value)
	{
		super(value);
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

	/* 商标类别(dm) : String */
	public String getTm_type()
	{
		return getValue( ITEM_TM_TYPE );
	}

	public void setTm_type( String tm_type1 )
	{
		setValue( ITEM_TM_TYPE, tm_type1 );
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

}

