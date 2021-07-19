package com.gwssi.dw.metadata.msurunit.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[gz_zb_jldw]的数据对象类
 * @author Administrator
 *
 */
public class VoGzZbJldw extends VoBase
{
	private static final long serialVersionUID = 200707251356150014L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_JLDW_DM = "jldw_dm" ;			/* 计量单位代码 */
	public static final String ITEM_DWLB_DM = "dwlb_dm" ;			/* 单位类别代码 */
	public static final String ITEM_JLDW_CN_MC = "jldw_cn_mc" ;		/* 计量单位中文名称 */
	public static final String ITEM_JLDW_SJZ = "jldw_sjz" ;			/* 计量单位数据值 */
	public static final String ITEM_JLDW_EN_MC = "jldw_en_mc" ;		/* 计量单位英文名称 */
	public static final String ITEM_DWLB_CN_MC= "dwlb_cn_mc" ;		/* 单位类别中文名称 */
	
	/**
	 * 构造函数
	 */
	public VoGzZbJldw()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoGzZbJldw(DataBus value)
	{
		super(value);
	}
	
	/* 计量单位代码 : String */
	public String getJldw_dm()
	{
		return getValue( ITEM_JLDW_DM );
	}

	public void setJldw_dm( String jldw_dm1 )
	{
		setValue( ITEM_JLDW_DM, jldw_dm1 );
	}

	/* 单位类别代码 : String */
	public String getDwlb_dm()
	{
		return getValue( ITEM_DWLB_DM );
	}

	public void setDwlb_dm( String dwlb_dm1 )
	{
		setValue( ITEM_DWLB_DM, dwlb_dm1 );
	}

	/* 计量单位中文名称 : String */
	public String getJldw_cn_mc()
	{
		return getValue( ITEM_JLDW_CN_MC );
	}

	public void setJldw_cn_mc( String jldw_cn_mc1 )
	{
		setValue( ITEM_JLDW_CN_MC, jldw_cn_mc1 );
	}

	/* 计量单位数据值 : String */
	public String getJldw_sjz()
	{
		return getValue( ITEM_JLDW_SJZ );
	}

	public void setJldw_sjz( String jldw_sjz1 )
	{
		setValue( ITEM_JLDW_SJZ, jldw_sjz1 );
	}

	/* 计量单位英文名称 : String */
	public String getJldw_en_mc()
	{
		return getValue( ITEM_JLDW_EN_MC );
	}

	public void setJldw_en_mc( String jldw_en_mc1 )
	{
		setValue( ITEM_JLDW_EN_MC, jldw_en_mc1 );
	}
	
	/* 单位类别中文名称 : String */
	public String getDwlb_cn_mc()
	{
		return getValue( ITEM_DWLB_CN_MC );
	}
	
	public void setDwlb_cn_mc( String dwlb_cn_mc1 )
	{
		setValue( ITEM_DWLB_CN_MC, dwlb_cn_mc1 );
	}

}

