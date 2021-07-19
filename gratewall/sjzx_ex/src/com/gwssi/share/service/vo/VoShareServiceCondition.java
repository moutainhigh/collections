package com.gwssi.share.service.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[share_service_condition]的数据对象类
 * @author Administrator
 *
 */
public class VoShareServiceCondition extends VoBase
{
	private static final long serialVersionUID = 201304021334180002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_CONDITION_ID = "condition_id" ;	/* 服务查询条件ID */
	public static final String ITEM_SERVICE_ID = "service_id" ;		/* 服务ID */
	public static final String ITEM_FRIST_CONNECTOR = "frist_connector" ;	/* 连接符1 */
	public static final String ITEM_LEFT_PAREN = "left_paren" ;		/* 左括号 */
	public static final String ITEM_TABLE_NAME_EN = "table_name_en" ;	/* 表名称 */
	public static final String ITEM_TABLE_NAME_CN = "table_name_cn" ;	/* 表中文名称 */
	public static final String ITEM_COLUMN_NAME_EN = "column_name_en" ;	/* 字段名称 */
	public static final String ITEM_COLUMN_NAME_CN = "column_name_cn" ;	/* 字段中文名称 */
	public static final String ITEM_SECOND_CONNECTOR = "second_connector" ;	/* 连接符2 */
	public static final String ITEM_PARAM_VALUE = "param_value" ;	/* 参数值 */
	public static final String ITEM_PARAM_TYPE = "param_type" ;		/* 参数值类型 */
	public static final String ITEM_RIGHT_PAREN = "right_paren" ;	/* 右括号 */
	public static final String ITEM_SHOW_ORDER = "show_order" ;		/* 显示顺序 */
	public static final String ITEM_NEED_INPUT = "need_input" ;		/* 是否需要参数 */
	
	/**
	 * 构造函数
	 */
	public VoShareServiceCondition()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoShareServiceCondition(DataBus value)
	{
		super(value);
	}
	
	/* 服务查询条件ID : String */
	public String getCondition_id()
	{
		return getValue( ITEM_CONDITION_ID );
	}

	public void setCondition_id( String condition_id1 )
	{
		setValue( ITEM_CONDITION_ID, condition_id1 );
	}

	/* 服务ID : String */
	public String getService_id()
	{
		return getValue( ITEM_SERVICE_ID );
	}

	public void setService_id( String service_id1 )
	{
		setValue( ITEM_SERVICE_ID, service_id1 );
	}

	/* 连接符1 : String */
	public String getFrist_connector()
	{
		return getValue( ITEM_FRIST_CONNECTOR );
	}

	public void setFrist_connector( String frist_connector1 )
	{
		setValue( ITEM_FRIST_CONNECTOR, frist_connector1 );
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

	/* 表名称 : String */
	public String getTable_name_en()
	{
		return getValue( ITEM_TABLE_NAME_EN );
	}

	public void setTable_name_en( String table_name_en1 )
	{
		setValue( ITEM_TABLE_NAME_EN, table_name_en1 );
	}

	/* 表中文名称 : String */
	public String getTable_name_cn()
	{
		return getValue( ITEM_TABLE_NAME_CN );
	}

	public void setTable_name_cn( String table_name_cn1 )
	{
		setValue( ITEM_TABLE_NAME_CN, table_name_cn1 );
	}

	/* 字段名称 : String */
	public String getColumn_name_en()
	{
		return getValue( ITEM_COLUMN_NAME_EN );
	}

	public void setColumn_name_en( String column_name_en1 )
	{
		setValue( ITEM_COLUMN_NAME_EN, column_name_en1 );
	}

	/* 字段中文名称 : String */
	public String getColumn_name_cn()
	{
		return getValue( ITEM_COLUMN_NAME_CN );
	}

	public void setColumn_name_cn( String column_name_cn1 )
	{
		setValue( ITEM_COLUMN_NAME_CN, column_name_cn1 );
	}

	/* 连接符2 : String */
	public String getSecond_connector()
	{
		return getValue( ITEM_SECOND_CONNECTOR );
	}

	public void setSecond_connector( String second_connector1 )
	{
		setValue( ITEM_SECOND_CONNECTOR, second_connector1 );
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

	/* 参数值类型 : String */
	public String getParam_type()
	{
		return getValue( ITEM_PARAM_TYPE );
	}

	public void setParam_type( String param_type1 )
	{
		setValue( ITEM_PARAM_TYPE, param_type1 );
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

	/* 显示顺序 : String */
	public String getShow_order()
	{
		return getValue( ITEM_SHOW_ORDER );
	}

	public void setShow_order( String show_order1 )
	{
		setValue( ITEM_SHOW_ORDER, show_order1 );
	}

	/* 是否需要参数 : String */
	public String getNeed_input()
	{
		return getValue( ITEM_NEED_INPUT );
	}

	public void setNeed_input( String need_input1 )
	{
		setValue( ITEM_NEED_INPUT, need_input1 );
	}

}

