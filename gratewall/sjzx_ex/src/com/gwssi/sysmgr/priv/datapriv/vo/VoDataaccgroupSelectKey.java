package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[dataaccgroup]的数据对象类
 * @author Administrator
 *
 */
public class VoDataaccgroupSelectKey extends VoBase
{
	private static final long serialVersionUID = 200709101706180027L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_DATAACCGRPNAME = "dataaccgrpname" ;	/* 数据权限分组名称 */
	public static final String ITEM_DATAACCGRPDESC = "dataaccgrpdesc" ;	/* 数据权限分组描述 */
	
	/**
	 * 构造函数
	 */
	public VoDataaccgroupSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDataaccgroupSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 数据权限分组名称 : String */
	public String getDataaccgrpname()
	{
		return getValue( ITEM_DATAACCGRPNAME );
	}

	public void setDataaccgrpname( String dataaccgrpname1 )
	{
		setValue( ITEM_DATAACCGRPNAME, dataaccgrpname1 );
	}

	/* 数据权限分组描述 : String */
	public String getDataaccgrpdesc()
	{
		return getValue( ITEM_DATAACCGRPDESC );
	}

	public void setDataaccgrpdesc( String dataaccgrpdesc1 )
	{
		setValue( ITEM_DATAACCGRPDESC, dataaccgrpdesc1 );
	}

}

