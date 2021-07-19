package com.gwssi.share.query.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_advquery_step2_param]的数据对象类
 * @author Administrator
 *
 */
public class VoSysAdvqueryStep2Param extends VoBase
{
	private static final long serialVersionUID = 200809261020110006L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_ADVQUERY_STEP2_PARAM_ID = "sys_advquery_step2_param_id" ;	/* 高级查询步骤二参数编号 */
	public static final String ITEM_SYS_ADVANCED_QUERY_ID = "sys_advanced_query_id" ;	/* 高级查询编号 */
	public static final String ITEM_OPERATOR1 = "operator1" ;		/* 连接符1 */
	public static final String ITEM_LEFT_PAREN = "left_paren" ;		/* 左括号 */
	public static final String ITEM_LEFT_TABLE_NO = "left_table_no" ;	/* 左表 */
	public static final String ITEM_LEFT_COLUMN_NO = "left_column_no" ;	/* 左字段 */
	public static final String ITEM_OPERATOR2 = "operator2" ;		/* 连接符2 */
	public static final String ITEM_PARAM_VALUE = "param_value" ;	/* 参数值 */
	public static final String ITEM_RIGHT_PAREN = "right_paren" ;	/* 右括号 */
	public static final String ITEM_PARAM_ORDER = "param_order" ;	/* 排序字段 */
	
	/**
	 * 构造函数
	 */
	public VoSysAdvqueryStep2Param()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysAdvqueryStep2Param(DataBus value)
	{
		super(value);
	}
	
	/* 高级查询步骤二参数编号 : String */
	public String getSys_advquery_step2_param_id()
	{
		return getValue( ITEM_SYS_ADVQUERY_STEP2_PARAM_ID );
	}

	public void setSys_advquery_step2_param_id( String sys_advquery_step2_param_id1 )
	{
		setValue( ITEM_SYS_ADVQUERY_STEP2_PARAM_ID, sys_advquery_step2_param_id1 );
	}

	/* 高级查询编号 : String */
	public String getSys_advanced_query_id()
	{
		return getValue( ITEM_SYS_ADVANCED_QUERY_ID );
	}

	public void setSys_advanced_query_id( String sys_advanced_query_id1 )
	{
		setValue( ITEM_SYS_ADVANCED_QUERY_ID, sys_advanced_query_id1 );
	}

	/* 连接符1 : String */
	public String getOperator1()
	{
		return getValue( ITEM_OPERATOR1 );
	}

	public void setOperator1( String operator11 )
	{
		setValue( ITEM_OPERATOR1, operator11 );
	}

	/* 左括号 : String */
	public String getLeft_paren()
	{
		return getValue( ITEM_LEFT_PAREN );
	}

	public void setLeft_paren( String left_paren1 )
	{
		setValue( ITEM_LEFT_PAREN, left_paren1 );
	}

	/* 左表 : String */
	public String getLeft_table_no()
	{
		return getValue( ITEM_LEFT_TABLE_NO );
	}

	public void setLeft_table_no( String left_table_no1 )
	{
		setValue( ITEM_LEFT_TABLE_NO, left_table_no1 );
	}

	/* 左字段 : String */
	public String getLeft_column_no()
	{
		return getValue( ITEM_LEFT_COLUMN_NO );
	}

	public void setLeft_column_no( String left_column_no1 )
	{
		setValue( ITEM_LEFT_COLUMN_NO, left_column_no1 );
	}

	/* 连接符2 : String */
	public String getOperator2()
	{
		return getValue( ITEM_OPERATOR2 );
	}

	public void setOperator2( String operator21 )
	{
		setValue( ITEM_OPERATOR2, operator21 );
	}

	/* 参数值 : String */
	public String getParam_value()
	{
		return getValue( ITEM_PARAM_VALUE );
	}

	public void setParam_value( String param_value1 )
	{
		setValue( ITEM_PARAM_VALUE, param_value1 );
	}

	/* 右括号 : String */
	public String getRight_paren()
	{
		return getValue( ITEM_RIGHT_PAREN );
	}

	public void setRight_paren( String right_paren1 )
	{
		setValue( ITEM_RIGHT_PAREN, right_paren1 );
	}

	/* 排序字段 : String */
	public String getParam_order()
	{
		return getValue( ITEM_PARAM_ORDER );
	}

	public void setParam_order( String param_order1 )
	{
		setValue( ITEM_PARAM_ORDER, param_order1 );
	}

}

