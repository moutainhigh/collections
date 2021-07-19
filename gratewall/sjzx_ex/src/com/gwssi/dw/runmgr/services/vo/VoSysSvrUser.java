package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_svr_user]的数据对象类
 * @author Administrator
 *
 */
public class VoSysSvrUser extends VoBase
{
	private static final long serialVersionUID = 200805050829180002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_SVR_USER_ID = "sys_svr_user_id" ;	/* 服务对象编号 */
	public static final String ITEM_LOGIN_NAME = "login_name" ;		/* 用户名 */
	public static final String ITEM_PASSWORD = "password" ;			/* 密码 */
	public static final String ITEM_CREATE_DATE = "create_date" ;	/* 创建日期 */
	public static final String ITEM_CREATE_BY = "create_by" ;		/* 创建人 */
	public static final String ITEM_STATE = "state" ;				/* 状态 */
	public static final String ITEM_DESC = "desc" ;					/* 描述 */
	public static final String ITEM_USER_TYPE = "user_type" ;		/* 用户类型 */
	
	/**
	 * 构造函数
	 */
	public VoSysSvrUser()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysSvrUser(DataBus value)
	{
		super(value);
	}
	
	/* 服务对象编号 : String */
	public String getSys_svr_user_id()
	{
		return getValue( ITEM_SYS_SVR_USER_ID );
	}

	public void setSys_svr_user_id( String sys_svr_user_id1 )
	{
		setValue( ITEM_SYS_SVR_USER_ID, sys_svr_user_id1 );
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

	/* 密码 : String */
	public String getPassword()
	{
		return getValue( ITEM_PASSWORD );
	}

	public void setPassword( String password1 )
	{
		setValue( ITEM_PASSWORD, password1 );
	}

	/* 创建日期 : String */
	public String getCreate_date()
	{
		return getValue( ITEM_CREATE_DATE );
	}

	public void setCreate_date( String create_date1 )
	{
		setValue( ITEM_CREATE_DATE, create_date1 );
	}

	/* 创建人 : String */
	public String getCreate_by()
	{
		return getValue( ITEM_CREATE_BY );
	}

	public void setCreate_by( String create_by1 )
	{
		setValue( ITEM_CREATE_BY, create_by1 );
	}

	/* 状态 : String */
	public String getState()
	{
		return getValue( ITEM_STATE );
	}

	public void setState( String state1 )
	{
		setValue( ITEM_STATE, state1 );
	}

	/* 描述 : String */
	public String getDesc()
	{
		return getValue( ITEM_DESC );
	}

	public void setDesc( String desc1 )
	{
		setValue( ITEM_DESC, desc1 );
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

