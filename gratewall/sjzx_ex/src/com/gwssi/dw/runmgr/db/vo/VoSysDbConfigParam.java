package com.gwssi.dw.runmgr.db.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoSysDbConfigParam extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_DB_CONFIG_PARAM_ID = "sys_db_config_param_id";			/* ���ò���ID */
	public static final String ITEM_SYS_DB_CONFIG_ID = "sys_db_config_id";			/* ����ID */
	public static final String ITEM_OPERATOR1 = "operator1";			/* or��and */
	public static final String ITEM_LEFT_PAREN = "left_paren";			/* ������ */
	public static final String ITEM_LEFT_TABLE_NO = "left_table_no";			/* ��ID */
	public static final String ITEM_LEFT_COLUMN_NO = "left_column_no";			/* �ֶ�ID */
	public static final String ITEM_OPERATOR2 = "operator2";			/* ������ */
	public static final String ITEM_PARAM_VALUE = "param_value";			/* ֵ */
	public static final String ITEM_RIGHT_PAREN = "right_paren";			/* ������ */
	public static final String ITEM_PARAM_TYPE = "param_type";			/* �������� */
	public static final String ITEM_PARAM_ORDER = "param_order";			/* ����˳�� */

	public VoSysDbConfigParam(DataBus value)
	{
		super(value);
	}

	public VoSysDbConfigParam()
	{
		super();
	}

	/* ���ò���ID */
	public String getSys_db_config_param_id()
	{
		return getValue( ITEM_SYS_DB_CONFIG_PARAM_ID );
	}

	public void setSys_db_config_param_id( String sys_db_config_param_id1 )
	{
		setValue( ITEM_SYS_DB_CONFIG_PARAM_ID, sys_db_config_param_id1 );
	}

	/* ����ID */
	public String getSys_db_config_id()
	{
		return getValue( ITEM_SYS_DB_CONFIG_ID );
	}

	public void setSys_db_config_id( String sys_db_config_id1 )
	{
		setValue( ITEM_SYS_DB_CONFIG_ID, sys_db_config_id1 );
	}

	/* or��and */
	public String getOperator1()
	{
		return getValue( ITEM_OPERATOR1 );
	}

	public void setOperator1( String operator11 )
	{
		setValue( ITEM_OPERATOR1, operator11 );
	}

	/* ������ */
	public String getLeft_paren()
	{
		return getValue( ITEM_LEFT_PAREN );
	}

	public void setLeft_paren( String left_paren1 )
	{
		setValue( ITEM_LEFT_PAREN, left_paren1 );
	}

	/* ��ID */
	public String getLeft_table_no()
	{
		return getValue( ITEM_LEFT_TABLE_NO );
	}

	public void setLeft_table_no( String left_table_no1 )
	{
		setValue( ITEM_LEFT_TABLE_NO, left_table_no1 );
	}

	/* �ֶ�ID */
	public String getLeft_column_no()
	{
		return getValue( ITEM_LEFT_COLUMN_NO );
	}

	public void setLeft_column_no( String left_column_no1 )
	{
		setValue( ITEM_LEFT_COLUMN_NO, left_column_no1 );
	}

	/* ������ */
	public String getOperator2()
	{
		return getValue( ITEM_OPERATOR2 );
	}

	public void setOperator2( String operator21 )
	{
		setValue( ITEM_OPERATOR2, operator21 );
	}

	/* ֵ */
	public String getParam_value()
	{
		return getValue( ITEM_PARAM_VALUE );
	}

	public void setParam_value( String param_value1 )
	{
		setValue( ITEM_PARAM_VALUE, param_value1 );
	}

	/* ������ */
	public String getRight_paren()
	{
		return getValue( ITEM_RIGHT_PAREN );
	}

	public void setRight_paren( String right_paren1 )
	{
		setValue( ITEM_RIGHT_PAREN, right_paren1 );
	}

	/* �������� */
	public String getParam_type()
	{
		return getValue( ITEM_PARAM_TYPE );
	}

	public void setParam_type( String param_type1 )
	{
		setValue( ITEM_PARAM_TYPE, param_type1 );
	}

	/* ����˳�� */
	public String getParam_order()
	{
		return getValue( ITEM_PARAM_ORDER );
	}

	public void setParam_order( String param_order1 )
	{
		setValue( ITEM_PARAM_ORDER, param_order1 );
	}

}

