package com.gwssi.file.manage.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[xt_ccgl_wjys]的数据对象类
 * @author Administrator
 *
 */
public class VoXtCcglWjysSelectKey extends VoBase
{
	private static final long serialVersionUID = 201303271612500007L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_YSBH_PK = "ysbh_pk" ;			/* 映射编号 */
	
	/**
	 * 构造函数
	 */
	public VoXtCcglWjysSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoXtCcglWjysSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 映射编号 : String */
	public String getYsbh_pk()
	{
		return getValue( ITEM_YSBH_PK );
	}

	public void setYsbh_pk( String ysbh_pk1 )
	{
		setValue( ITEM_YSBH_PK, ysbh_pk1 );
	}

}

