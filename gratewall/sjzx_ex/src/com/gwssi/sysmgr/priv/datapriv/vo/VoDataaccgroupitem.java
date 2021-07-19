package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[dataaccgroupitem]的数据对象类
 * @author Administrator
 *
 */
public class VoDataaccgroupitem extends VoBase
{
	private static final long serialVersionUID = 200709101706480030L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_DATAACCGRPID = "dataaccgrpid" ;	/* 数据权限分组ID */
	public static final String ITEM_OBJECTID = "objectid" ;			/* 数据权限类型代码 */
	public static final String ITEM_DATAACCID = "dataaccid" ;		/* 数据权限内码 */
	
	/**
	 * 构造函数
	 */
	public VoDataaccgroupitem()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDataaccgroupitem(DataBus value)
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

	/* 数据权限类型代码 : String */
	public String getObjectid()
	{
		return getValue( ITEM_OBJECTID );
	}

	public void setObjectid( String objectid1 )
	{
		setValue( ITEM_OBJECTID, objectid1 );
	}

	/* 数据权限内码 : String */
	public String getDataaccid()
	{
		return getValue( ITEM_DATAACCID );
	}

	public void setDataaccid( String dataaccid1 )
	{
		setValue( ITEM_DATAACCID, dataaccid1 );
	}

}

