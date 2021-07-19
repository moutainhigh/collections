package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[dataaccgroup]的数据对象类
 * @author Administrator
 *
 */
public class VoDataaccgroupPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200709101706180028L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_DATAACCGRPID = "dataaccgrpid" ;	/* 数据权限分组ID */
	
	/**
	 * 构造函数
	 */
	public VoDataaccgroupPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDataaccgroupPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 数据权限分组ID : String */
	public String getDataaccgrpid()
	{
		return getValue( ITEM_DATAACCGRPID );
	}

	public void setDataaccgrpid( String dataaccgrpid1 )
	{
		setValue( ITEM_DATAACCGRPID, dataaccgrpid1 );
	}

}

