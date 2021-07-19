package com.gwssi.dw.runmgr.db.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoSysDbViewParam extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_DB_VIEW_PARAM_ID = "sys_db_view_param_id";			/* 视图参数ID */
	public static final String ITEM_SYS_DB_VIEW_ID = "sys_db_view_id";			/* 视图ID */
	public static final String ITEM_OPERATOR1 = "operator1";			/* or或and操作符 */
	public static final String ITEM_LEFT_PAREN = "left_paren";			/* 左括号 */
	public static final String ITEM_LEFT_TABLE_NO = "left_table_no";			/* 左表ID */
	public static final String ITEM_LEFT_COLUMN_NO = "left_column_no";			/* 左表字段ID */
	public static final String ITEM_OPERATOR2 = "operator2";			/* 操作符（>,<,like等） */
	public static final String ITEM_RIGHT_TABLE_NO = "right_table_no";			/* 右表ID */
	public static final String ITEM_RIGHT_COLUMN_NO = "right_column_no";			/* 右表字段ID */
	public static final String ITEM_RIGHT_PAREN = "right_paren";			/* 右括号 */
	public static final String ITEM_PARAM_ORDER = "param_order";			/* 参数顺序 */

	public VoSysDbViewParam(DataBus value)
	{
		super(value);
	}

	public VoSysDbViewParam()
	{
		super();
	}

	/* 视图参数ID */
	public String getSys_db_view_param_id()
	{
		return getValue( ITEM_SYS_DB_VIEW_PARAM_ID );
	}

	public void setSys_db_view_param_id( String sys_db_view_param_id1 )
	{
		setValue( ITEM_SYS_DB_VIEW_PARAM_ID, sys_db_view_param_id1 );
	}

	/* 视图ID */
	public String getSys_db_view_id()
	{
		return getValue( ITEM_SYS_DB_VIEW_ID );
	}

	public void setSys_db_view_id( String sys_db_view_id1 )
	{
		setValue( ITEM_SYS_DB_VIEW_ID, sys_db_view_id1 );
	}

	/* or或and操作符 */
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

	/* 左表ID */
	public String getLeft_table_no()
	{
		return getValue( ITEM_LEFT_TABLE_NO );
	}

	public void setLeft_table_no( String left_table_no1 )
	{
		setValue( ITEM_LEFT_TABLE_NO, left_table_no1 );
	}

	/* 左表字段ID */
	public String getLeft_column_no()
	{
		return getValue( ITEM_LEFT_COLUMN_NO );
	}

	public void setLeft_column_no( String left_column_no1 )
	{
		setValue( ITEM_LEFT_COLUMN_NO, left_column_no1 );
	}

	/* 操作符（>,<,like等） */
	public String getOperator2()
	{
		return getValue( ITEM_OPERATOR2 );
	}

	public void setOperator2( String operator21 )
	{
		setValue( ITEM_OPERATOR2, operator21 );
	}

	/* 右表ID */
	public String getRight_table_no()
	{
		return getValue( ITEM_RIGHT_TABLE_NO );
	}

	public void setRight_table_no( String right_table_no1 )
	{
		setValue( ITEM_RIGHT_TABLE_NO, right_table_no1 );
	}

	/* 右表字段ID */
	public String getRight_column_no()
	{
		return getValue( ITEM_RIGHT_COLUMN_NO );
	}

	public void setRight_column_no( String right_column_no1 )
	{
		setValue( ITEM_RIGHT_COLUMN_NO, right_column_no1 );
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

