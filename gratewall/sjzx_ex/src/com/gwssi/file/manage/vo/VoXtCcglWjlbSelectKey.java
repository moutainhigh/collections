package com.gwssi.file.manage.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[xt_ccgl_wjlb]的数据对象类
 * @author Administrator
 *
 */
public class VoXtCcglWjlbSelectKey extends VoBase
{
	private static final long serialVersionUID = 201303271601110003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_CCLBMC = "cclbmc" ;				/* 处处类别名称 */
	public static final String ITEM_LBMCBB = "lbmcbb" ;				/* 类别名称版本 */
	public static final String ITEM_ZT = "zt" ;						/* 状态 */
	
	/**
	 * 构造函数
	 */
	public VoXtCcglWjlbSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoXtCcglWjlbSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 处处类别名称 : String */
	public String getCclbmc()
	{
		return getValue( ITEM_CCLBMC );
	}

	public void setCclbmc( String cclbmc1 )
	{
		setValue( ITEM_CCLBMC, cclbmc1 );
	}

	/* 类别名称版本 : String */
	public String getLbmcbb()
	{
		return getValue( ITEM_LBMCBB );
	}

	public void setLbmcbb( String lbmcbb1 )
	{
		setValue( ITEM_LBMCBB, lbmcbb1 );
	}

	/* 状态 : String */
	public String getZt()
	{
		return getValue( ITEM_ZT );
	}

	public void setZt( String zt1 )
	{
		setValue( ITEM_ZT, zt1 );
	}

}

