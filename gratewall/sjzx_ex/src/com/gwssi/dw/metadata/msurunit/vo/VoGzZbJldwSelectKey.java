package com.gwssi.dw.metadata.msurunit.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[gz_zb_jldw]的数据对象类
 * @author Administrator
 *
 */
public class VoGzZbJldwSelectKey extends VoBase
{
	private static final long serialVersionUID = 200707251356150015L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_JLDW_DM = "jldw_dm" ;			/* 计量单位代码 */
	public static final String ITEM_JLDW_CN_MC = "jldw_cn_mc" ;			/* 计量单位中文名称 */
	public static final String ITEM_DWLB_CN_MC = "dwlb_cn_mc" ;		/* 单位类别中文名称 */
	/**
	 * 构造函数
	 */
	public VoGzZbJldwSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoGzZbJldwSelectKey(DataBus value)
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
	
	
	/* 计量单位中文名称 : String */
	public String getJldw_cn_mc()
	{
		return getValue( ITEM_JLDW_CN_MC);
	}
	
	public void setJldw_cn_mc( String jldw_cn_mc1 )
	{
		setValue( ITEM_JLDW_CN_MC, jldw_cn_mc1 );
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

