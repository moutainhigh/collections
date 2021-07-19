package com.gwssi.sysmgr.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_popwindow]的数据对象类
 * @author Administrator
 *
 */
public class VoSysPopwindow extends VoBase
{
	private static final long serialVersionUID = 200907271007430002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_POPWINDOW_ID = "sys_popwindow_id" ;	/* 系统任务ID */
	public static final String ITEM_CONTENT = "content" ;			/* 内容 */
	public static final String ITEM_PUBLISH_DATE = "publish_date" ;	/* 发布日期 */
	public static final String ITEM_EXPIRE_DATE = "expire_date" ;	/* 到期日期 */
	public static final String ITEM_ROLES = "roles" ;				/* 角色列表 */
	
	/**
	 * 构造函数
	 */
	public VoSysPopwindow()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysPopwindow(DataBus value)
	{
		super(value);
	}
	
	/* 系统任务ID : String */
	public String getSys_popwindow_id()
	{
		return getValue( ITEM_SYS_POPWINDOW_ID );
	}

	public void setSys_popwindow_id( String sys_popwindow_id1 )
	{
		setValue( ITEM_SYS_POPWINDOW_ID, sys_popwindow_id1 );
	}

	/* 内容 : String */
	public String getContent()
	{
		return getValue( ITEM_CONTENT );
	}

	public void setContent( String content1 )
	{
		setValue( ITEM_CONTENT, content1 );
	}

	/* 发布日期 : String */
	public String getPublish_date()
	{
		return getValue( ITEM_PUBLISH_DATE );
	}

	public void setPublish_date( String publish_date1 )
	{
		setValue( ITEM_PUBLISH_DATE, publish_date1 );
	}

	/* 到期日期 : String */
	public String getExpire_date()
	{
		return getValue( ITEM_EXPIRE_DATE );
	}

	public void setExpire_date( String expire_date1 )
	{
		setValue( ITEM_EXPIRE_DATE, expire_date1 );
	}

	/* 角色列表 : String */
	public String getRoles()
	{
		return getValue( ITEM_ROLES );
	}

	public void setRoles( String roles1 )
	{
		setValue( ITEM_ROLES, roles1 );
	}

}

