package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_svr_config]的数据对象类
 * @author Administrator
 *
 */
public class VoSysSvrConfig extends VoBase
{
	private static final long serialVersionUID = 200809081645150002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_SVR_CONFIG_ID = "sys_svr_config_id" ;	/* 共享服务配置编码 */
	public static final String ITEM_SYS_SVR_SERVICE_ID = "sys_svr_service_id" ;	/* 共享服务编码 */
	public static final String ITEM_SYS_SVR_USER_ID = "sys_svr_user_id" ;	/* 服务对象编码 */
	public static final String ITEM_PERMIT_COLUMN = "permit_column" ;	/* 允许访问的字段 */
	public static final String ITEM_CREATE_DATE = "create_date" ;	/* 创建日期 */
	public static final String ITEM_CREATE_BY = "create_by" ;		/* 创建人 */
	public static final String ITEM_CONFIG_ORDER = "config_order" ;	/* 排序字段 */
	
	/**
	 * 构造函数
	 */
	public VoSysSvrConfig()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysSvrConfig(DataBus value)
	{
		super(value);
	}
	
	/* 共享服务配置编码 : String */
	public String getSys_svr_config_id()
	{
		return getValue( ITEM_SYS_SVR_CONFIG_ID );
	}

	public void setSys_svr_config_id( String sys_svr_config_id1 )
	{
		setValue( ITEM_SYS_SVR_CONFIG_ID, sys_svr_config_id1 );
	}

	/* 共享服务编码 : String */
	public String getSys_svr_service_id()
	{
		return getValue( ITEM_SYS_SVR_SERVICE_ID );
	}

	public void setSys_svr_service_id( String sys_svr_service_id1 )
	{
		setValue( ITEM_SYS_SVR_SERVICE_ID, sys_svr_service_id1 );
	}

	/* 服务对象编码 : String */
	public String getSys_svr_user_id()
	{
		return getValue( ITEM_SYS_SVR_USER_ID );
	}

	public void setSys_svr_user_id( String sys_svr_user_id1 )
	{
		setValue( ITEM_SYS_SVR_USER_ID, sys_svr_user_id1 );
	}

	/* 允许访问的字段 : String */
	public String getPermit_column()
	{
		return getValue( ITEM_PERMIT_COLUMN );
	}

	public void setPermit_column( String permit_column1 )
	{
		setValue( ITEM_PERMIT_COLUMN, permit_column1 );
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

	/* 排序字段 : String */
	public String getConfig_order()
	{
		return getValue( ITEM_CONFIG_ORDER );
	}

	public void setConfig_order( String config_order1 )
	{
		setValue( ITEM_CONFIG_ORDER, config_order1 );
	}

}

