package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_svr_service]的数据对象类
 * @author Administrator
 *
 */
public class VoSysSvrServiceSelectKey extends VoBase
{
	private static final long serialVersionUID = 200805061510580007L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SVR_CODE = "svr_code" ;			/* 服务代码 */
	public static final String ITEM_NAME = "name" ;					/* 共享服务名称 */
	public static final String ITEM_CREATE_BY = "create_by" ;		/* 创建人 */
	public static final String ITEM_CREATE_DATE = "create_date" ;	/* 创建日期 */
	
	/**
	 * 构造函数
	 */
	public VoSysSvrServiceSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysSvrServiceSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 服务代码 : String */
	public String getSvr_code()
	{
		return getValue( ITEM_SVR_CODE );
	}

	public void setSvr_code( String svr_code1 )
	{
		setValue( ITEM_SVR_CODE, svr_code1 );
	}

	/* 共享服务名称 : String */
	public String getName()
	{
		return getValue( ITEM_NAME );
	}

	public void setName( String name1 )
	{
		setValue( ITEM_NAME, name1 );
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

	/* 创建日期 : String */
	public String getCreate_date()
	{
		return getValue( ITEM_CREATE_DATE );
	}

	public void setCreate_date( String create_date1 )
	{
		setValue( ITEM_CREATE_DATE, create_date1 );
	}

}

