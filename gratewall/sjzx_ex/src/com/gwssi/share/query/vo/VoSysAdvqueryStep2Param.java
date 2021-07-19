package com.gwssi.share.query.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_advquery_step2_param]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysAdvqueryStep2Param extends VoBase
{
	private static final long serialVersionUID = 200809261020110006L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_ADVQUERY_STEP2_PARAM_ID = "sys_advquery_step2_param_id" ;	/* �߼���ѯ������������ */
	public static final String ITEM_SYS_ADVANCED_QUERY_ID = "sys_advanced_query_id" ;	/* �߼���ѯ��� */
	public static final String ITEM_OPERATOR1 = "operator1" ;		/* ���ӷ�1 */
	public static final String ITEM_LEFT_PAREN = "left_paren" ;		/* ������ */
	public static final String ITEM_LEFT_TABLE_NO = "left_table_no" ;	/* ��� */
	public static final String ITEM_LEFT_COLUMN_NO = "left_column_no" ;	/* ���ֶ� */
	public static final String ITEM_OPERATOR2 = "operator2" ;		/* ���ӷ�2 */
	public static final String ITEM_PARAM_VALUE = "param_value" ;	/* ����ֵ */
	public static final String ITEM_RIGHT_PAREN = "right_paren" ;	/* ������ */
	public static final String ITEM_PARAM_ORDER = "param_order" ;	/* �����ֶ� */
	
	/**
	 * ���캯��
	 */
	public VoSysAdvqueryStep2Param()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysAdvqueryStep2Param(DataBus value)
	{
		super(value);
	}
	
	/* �߼���ѯ������������ : String */
	public String getSys_advquery_step2_param_id()
	{
		return getValue( ITEM_SYS_ADVQUERY_STEP2_PARAM_ID );
	}

	public void setSys_advquery_step2_param_id( String sys_advquery_step2_param_id1 )
	{
		setValue( ITEM_SYS_ADVQUERY_STEP2_PARAM_ID, sys_advquery_step2_param_id1 );
	}

	/* �߼���ѯ��� : String */
	public String getSys_advanced_query_id()
	{
		return getValue( ITEM_SYS_ADVANCED_QUERY_ID );
	}

	public void setSys_advanced_query_id( String sys_advanced_query_id1 )
	{
		setValue( ITEM_SYS_ADVANCED_QUERY_ID, sys_advanced_query_id1 );
	}

	/* ���ӷ�1 : String */
	public String getOperator1()
	{
		return getValue( ITEM_OPERATOR1 );
	}

	public void setOperator1( String operator11 )
	{
		setValue( ITEM_OPERATOR1, operator11 );
	}

	/* ������ : String */
	public String getLeft_paren()
	{
		return getValue( ITEM_LEFT_PAREN );
	}

	public void setLeft_paren( String left_paren1 )
	{
		setValue( ITEM_LEFT_PAREN, left_paren1 );
	}

	/* ��� : String */
	public String getLeft_table_no()
	{
		return getValue( ITEM_LEFT_TABLE_NO );
	}

	public void setLeft_table_no( String left_table_no1 )
	{
		setValue( ITEM_LEFT_TABLE_NO, left_table_no1 );
	}

	/* ���ֶ� : String */
	public String getLeft_column_no()
	{
		return getValue( ITEM_LEFT_COLUMN_NO );
	}

	public void setLeft_column_no( String left_column_no1 )
	{
		setValue( ITEM_LEFT_COLUMN_NO, left_column_no1 );
	}

	/* ���ӷ�2 : String */
	public String getOperator2()
	{
		return getValue( ITEM_OPERATOR2 );
	}

	public void setOperator2( String operator21 )
	{
		setValue( ITEM_OPERATOR2, operator21 );
	}

	/* ����ֵ : String */
	public String getParam_value()
	{
		return getValue( ITEM_PARAM_VALUE );
	}

	public void setParam_value( String param_value1 )
	{
		setValue( ITEM_PARAM_VALUE, param_value1 );
	}

	/* ������ : String */
	public String getRight_paren()
	{
		return getValue( ITEM_RIGHT_PAREN );
	}

	public void setRight_paren( String right_paren1 )
	{
		setValue( ITEM_RIGHT_PAREN, right_paren1 );
	}

	/* �����ֶ� : String */
	public String getParam_order()
	{
		return getValue( ITEM_PARAM_ORDER );
	}

	public void setParam_order( String param_order1 )
	{
		setValue( ITEM_PARAM_ORDER, param_order1 );
	}

}

