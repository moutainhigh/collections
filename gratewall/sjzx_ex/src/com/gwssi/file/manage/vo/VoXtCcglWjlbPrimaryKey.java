package com.gwssi.file.manage.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[xt_ccgl_wjlb]的数据对象类
 * @author Administrator
 *
 */
public class VoXtCcglWjlbPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303271601110004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_CCLBBH_PK = "cclbbh_pk" ;		/* 文件类别编号 */
	
	/**
	 * 构造函数
	 */
	public VoXtCcglWjlbPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoXtCcglWjlbPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 文件类别编号 : String */
	public String getCclbbh_pk()
	{
		return getValue( ITEM_CCLBBH_PK );
	}

	public void setCclbbh_pk( String cclbbh_pk1 )
	{
		setValue( ITEM_CCLBBH_PK, cclbbh_pk1 );
	}

}

