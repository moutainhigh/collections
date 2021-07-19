package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[dataaccgroupitem]的数据对象类
 * @author Administrator
 *
 */
public class VoDataaccgroupitemSelectKey extends VoBase
{
	private static final long serialVersionUID = 200709101706490031L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_DATAACCGRPID = "dataaccgrpid" ;	/* 数据权限分组ID */
	
	/**
	 * 构造函数
	 */
	public VoDataaccgroupitemSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDataaccgroupitemSelectKey(DataBus value)
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

