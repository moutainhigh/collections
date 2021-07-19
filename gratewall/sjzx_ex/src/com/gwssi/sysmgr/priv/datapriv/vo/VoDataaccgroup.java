package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[dataaccgroup]的数据对象类
 * @author Administrator
 *
 */
public class VoDataaccgroup extends VoBase
{
	private static final long serialVersionUID = 200709101706180026L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_DATAACCGRPID = "dataaccgrpid" ;	/* 数据权限分组ID */
	public static final String ITEM_DATAACCGRPNAME = "dataaccgrpname" ;	/* 数据权限分组名称 */
	public static final String ITEM_DATAACCRULE = "dataaccrule" ;	/* 角色权限认证规则 */
	public static final String ITEM_DATAACCTYPE = "dataacctype" ;	/* 数据权限分组类型 */
	public static final String ITEM_DATAACCGRPDESC = "dataaccgrpdesc" ;	/* 数据权限分组描述 */
	
	/**
	 * 构造函数
	 */
	public VoDataaccgroup()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDataaccgroup(DataBus value)
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

	/* 数据权限分组名称 : String */
	public String getDataaccgrpname()
	{
		return getValue( ITEM_DATAACCGRPNAME );
	}

	public void setDataaccgrpname( String dataaccgrpname1 )
	{
		setValue( ITEM_DATAACCGRPNAME, dataaccgrpname1 );
	}

	/* 角色权限认证规则 : String */
	public String getDataaccrule()
	{
		return getValue( ITEM_DATAACCRULE );
	}

	public void setDataaccrule( String dataaccrule1 )
	{
		setValue( ITEM_DATAACCRULE, dataaccrule1 );
	}

	/* 数据权限分组类型 : String */
	public String getDataacctype()
	{
		return getValue( ITEM_DATAACCTYPE );
	}

	public void setDataacctype( String dataacctype1 )
	{
		setValue( ITEM_DATAACCTYPE, dataacctype1 );
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

