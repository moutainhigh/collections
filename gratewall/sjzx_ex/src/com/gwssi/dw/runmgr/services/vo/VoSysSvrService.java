package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_svr_service]的数据对象类
 * @author Administrator
 *
 */
public class VoSysSvrService extends VoBase
{
	private static final long serialVersionUID = 200805061510580006L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_SVR_SERVICE_ID = "sys_svr_service_id" ;	/* 共享服务编号 */
	public static final String ITEM_NAME = "name" ;					/* 共享服务名称 */
	public static final String ITEM_DCZD_DM = "dczd_dm" ;			/* 主题代码 */
	public static final String ITEM_TABLE_NO = "table_no" ;			/* 数据表编码 */
	public static final String ITEM_COLUMN_NO = "column_no" ;		/* 字段编码 */
	public static final String ITEM_CREATE_DATE = "create_date" ;	/* 创建日期 */
	public static final String ITEM_CREATE_BY = "create_by" ;		/* 创建人 */
	public static final String ITEM_PARAM_COLUMNS = "param_columns" ;	/* 参数自段 */
	public static final String ITEM_COMMENT = "comment" ;			/* 备注 */
	public static final String ITEM_SVR_CODE = "svr_code" ;			/* 服务代码 */
	
	/**
	 * 构造函数
	 */
	public VoSysSvrService()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysSvrService(DataBus value)
	{
		super(value);
	}
	
	/* 共享服务编号 : String */
	public String getSys_svr_service_id()
	{
		return getValue( ITEM_SYS_SVR_SERVICE_ID );
	}

	public void setSys_svr_service_id( String sys_svr_service_id1 )
	{
		setValue( ITEM_SYS_SVR_SERVICE_ID, sys_svr_service_id1 );
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

	/* 主题代码 : String */
	public String getDczd_dm()
	{
		return getValue( ITEM_DCZD_DM );
	}

	public void setDczd_dm( String dczd_dm1 )
	{
		setValue( ITEM_DCZD_DM, dczd_dm1 );
	}

	/* 数据表编码 : String */
	public String getTable_no()
	{
		return getValue( ITEM_TABLE_NO );
	}

	public void setTable_no( String table_no1 )
	{
		setValue( ITEM_TABLE_NO, table_no1 );
	}

	/* 字段编码 : String */
	public String getColumn_no()
	{
		return getValue( ITEM_COLUMN_NO );
	}

	public void setColumn_no( String column_no1 )
	{
		setValue( ITEM_COLUMN_NO, column_no1 );
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

	/* 参数自段 : String */
	public String getParam_columns()
	{
		return getValue( ITEM_PARAM_COLUMNS );
	}

	public void setParam_columns( String param_columns1 )
	{
		setValue( ITEM_PARAM_COLUMNS, param_columns1 );
	}

	/* 备注 : String */
	public String getComment()
	{
		return getValue( ITEM_COMMENT );
	}

	public void setComment( String comment1 )
	{
		setValue( ITEM_COMMENT, comment1 );
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

}

