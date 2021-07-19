package com.gwssi.sysmgr.role.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[operrolefun]的数据对象类
 * @author Administrator
 *
 */
public class VoOperrolefun extends VoBase
{
	private static final long serialVersionUID = 200709111111510002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_ROLEACCID = "roleaccid" ;		/* 角色权限代码 */
	public static final String ITEM_ROLEID = "roleid" ;				/* 角色编号 */
	public static final String ITEM_TXNCODE = "txncode" ;			/* 交易代码 */
	public static final String ITEM_DATAACCRULE = "dataaccrule" ;	/* 数据权限认证规则 */
	
	/**
	 * 构造函数
	 */
	public VoOperrolefun()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoOperrolefun(DataBus value)
	{
		super(value);
	}
	
	/* 角色权限代码 : String */
	public String getRoleaccid()
	{
		return getValue( ITEM_ROLEACCID );
	}

	public void setRoleaccid( String roleaccid1 )
	{
		setValue( ITEM_ROLEACCID, roleaccid1 );
	}

	/* 角色编号 : String */
	public String getRoleid()
	{
		return getValue( ITEM_ROLEID );
	}

	public void setRoleid( String roleid1 )
	{
		setValue( ITEM_ROLEID, roleid1 );
	}

	/* 交易代码 : String */
	public String getTxncode()
	{
		return getValue( ITEM_TXNCODE );
	}

	public void setTxncode( String txncode1 )
	{
		setValue( ITEM_TXNCODE, txncode1 );
	}

	/* 数据权限认证规则 : String */
	public String getDataaccrule()
	{
		return getValue( ITEM_DATAACCRULE );
	}

	public void setDataaccrule( String dataaccrule1 )
	{
		setValue( ITEM_DATAACCRULE, dataaccrule1 );
	}

}

