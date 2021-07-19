package com.gwssi.dw.metadata.msurunit.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[gz_zb_jldw_fl]的数据对象类
 * @author Administrator
 *
 */
public class VoGzZbJldwFlSelectKey extends VoBase
{
	private static final long serialVersionUID = 200707251356150011L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_DWLB_DM = "dwlb_dm" ;			/* 单位类别代码 */
	public static final String ITEM_DWLB_CN_MC = "dwlb_cn_mc" ;		/* 单位类别中文名称 */
	
	/**
	 * 构造函数
	 */
	public VoGzZbJldwFlSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoGzZbJldwFlSelectKey(DataBus value)
	{
		super(value);
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

