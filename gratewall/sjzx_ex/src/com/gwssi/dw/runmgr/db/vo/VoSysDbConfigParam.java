package com.gwssi.dw.runmgr.db.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoSysDbConfigParam extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_DB_CONFIG_PARAM_ID = "sys_db_config_param_id";			/* 配置参数ID */
	public static final String ITEM_SYS_DB_CONFIG_ID = "sys_db_config_id";			/* 配置ID */
	public static final String ITEM_OPERATOR1 = "operator1";			/* or或and */
	public static final String ITEM_LEFT_PAREN = "left_paren";			/* 左括号 */
	public static final String ITEM_LEFT_TABLE_NO = "left_table_no";			/* 表ID */
	public static final String ITEM_LEFT_COLUMN_NO = "left_column_no";			/* 字段ID */
	public static final String ITEM_OPERATOR2 = "operator2";			/* 操作符 */
	public static final String ITEM_PARAM_VALUE = "param_value";			/* 值 */
	public static final String ITEM_RIGHT_PAREN = "right_paren";			/* 右括号 */
	public static final String ITEM_PARAM_TYPE = "param_type";			/* 参数类型 */
	public static final String ITEM_PARAM_ORDER = "param_order";			/* 参数顺序 */

	public VoSysDbConfigParam(DataBus value)
	{
		super(value);
	}

	public VoSysDbConfigParam()
	{
		super();
	}

	/* 配置参数ID */
	public String getSys_db_config_param_id()
	{
		return getValue( ITEM_SYS_DB_CONFIG_PARAM_ID );
	}

	public void setSys_db_config_param_id( String sys_db_config_param_id1 )
	{
		setValue( ITEM_SYS_DB_CONFIG_PARAM_ID, sys_db_config_param_id1 );
	}

	/* 配置ID */
	public String getSys_db_config_id()
	{
		return getValue( ITEM_SYS_DB_CONFIG_ID );
	}

	public void setSys_db_config_id( String sys_db_config_id1 )
	{
		setValue( ITEM_SYS_DB_CONFIG_ID, sys_db_config_id1 );
	}

	/* or或and */
	public String getOperator1()
	{
		return getValue( ITEM_OPERATOR1 );
	}

	public void setOperator1( String operator11 )
	{
		setValue( ITEM_OPERATOR1, operator11 );
	}

	/* 左括号 */
	public String getLeft_paren()
	{
		return getValue( ITEM_LEFT_PAREN );
	}

	public void setLeft_paren( String left_paren1 )
	{
		setValue( ITEM_LEFT_PAREN, left_paren1 );
	}

	/* 表ID */
	public String getLeft_table_no()
	{
		return getValue( ITEM_LEFT_TABLE_NO );
	}

	public void setLeft_table_no( String left_table_no1 )
	{
		setValue( ITEM_LEFT_TABLE_NO, left_table_no1 );
	}

	/* 字段ID */
	public String getLeft_column_no()
	{
		return getValue( ITEM_LEFT_COLUMN_NO );
	}

	public void setLeft_column_no( String left_column_no1 )
	{
		setValue( ITEM_LEFT_COLUMN_NO, left_column_no1 );
	}

	/* 操作符 */
	public String getOperator2()
	{
		return getValue( ITEM_OPERATOR2 );
	}

	public void setOperator2( String operator21 )
	{
		setValue( ITEM_OPERATOR2, operator21 );
	}

	/* 值 */
	public String getParam_value()
	{
		return getValue( ITEM_PARAM_VALUE );
	}

	public void setParam_value( String param_value1 )
	{
		setValue( ITEM_PARAM_VALUE, param_value1 );
	}

	/* 右括号 */
	public String getRight_paren()
	{
		return getValue( ITEM_RIGHT_PAREN );
	}

	public void setRight_paren( String right_paren1 )
	{
		setValue( ITEM_RIGHT_PAREN, right_paren1 );
	}

	/* 参数类型 */
	public String getParam_type()
	{
		return getValue( ITEM_PARAM_TYPE );
	}

	public void setParam_type( String param_type1 )
	{
		setValue( ITEM_PARAM_TYPE, param_type1 );
	}

	/* 参数顺序 */
	public String getParam_order()
	{
		return getValue( ITEM_PARAM_ORDER );
	}

	public void setParam_order( String param_order1 )
	{
		setValue( ITEM_PARAM_ORDER, param_order1 );
	}

}

