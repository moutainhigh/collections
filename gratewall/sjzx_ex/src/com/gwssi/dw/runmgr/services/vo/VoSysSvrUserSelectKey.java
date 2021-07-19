package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_svr_user]的数据对象类
 * @author Administrator
 *
 */
public class VoSysSvrUserSelectKey extends VoBase
{
	private static final long serialVersionUID = 200805050829180003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_LOGIN_NAME = "login_name" ;		/* 用户名 */
	public static final String ITEM_USER_TYPE = "user_type" ;		/* 用户类型 */
	
	/**
	 * 构造函数
	 */
	public VoSysSvrUserSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysSvrUserSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 用户名 : String */
	public String getLogin_name()
	{
		return getValue( ITEM_LOGIN_NAME );
	}

	public void setLogin_name( String login_name1 )
	{
		setValue( ITEM_LOGIN_NAME, login_name1 );
	}

	/* 用户类型 : String */
	public String getUser_type()
	{
		return getValue( ITEM_USER_TYPE );
	}

	public void setUser_type( String user_type1 )
	{
		setValue( ITEM_USER_TYPE, user_type1 );
	}

}

