package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_svr_config_param]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSvrConfigParam extends VoBase
{
	private static final long serialVersionUID = 200809101030500002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_SVR_CONFIG_PARAM_ID = "sys_svr_config_param_id" ;	/* ����������ò������ */
	public static final String ITEM_SYS_SVR_CONFIG_ID = "sys_svr_config_id" ;	/* ����������ñ�� */
	public static final String ITEM_OPERATOR1 = "operator1" ;		/* ���ӷ�1 */
	public static final String ITEM_LEFT_PAREN = "left_paren" ;		/* ������ */
	public static final String ITEM_LEFT_TABLE_NO = "left_table_no" ;	/* ��� */
	public static final String ITEM_LEFT_COLUMN_NO = "left_column_no" ;	/* ���ֶ� */
	public static final String ITEM_OPERATOR2 = "operator2" ;		/* ���ӷ�2 */
	public static final String ITEM_RIGHT_TABLE_NO = "right_table_no" ;	/* �ұ� */
	public static final String ITEM_RIGHT_COLUMN_NO = "right_column_no" ;	/* ���ֶ� */
	public static final String ITEM_RIGHT_PAREN = "right_paren" ;	/* ������ */
	public static final String ITEM_PARAM_TYPE = "param_type" ;		/* �������ͣ�0 ϵͳ������1 �û����� */
	public static final String ITEM_PARAM_ORDER = "param_order" ;	/* �����ֶ� */
	
	/**
	 * ���캯��
	 */
	public VoSysSvrConfigParam()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSvrConfigParam(DataBus value)
	{
		super(value);
	}
	
	/* ����������ò������ : String */
	public String getSys_svr_config_param_id()
	{
		return getValue( ITEM_SYS_SVR_CONFIG_PARAM_ID );
	}

	public void setSys_svr_config_param_id( String sys_svr_config_param_id1 )
	{
		setValue( ITEM_SYS_SVR_CONFIG_PARAM_ID, sys_svr_config_param_id1 );
	}

	/* ����������ñ�� : String */
	public String getSys_svr_config_id()
	{
		return getValue( ITEM_SYS_SVR_CONFIG_ID );
	}

	public void setSys_svr_config_id( String sys_svr_config_id1 )
	{
		setValue( ITEM_SYS_SVR_CONFIG_ID, sys_svr_config_id1 );
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

	/* �ұ� : String */
	public String getRight_table_no()
	{
		return getValue( ITEM_RIGHT_TABLE_NO );
	}

	public void setRight_table_no( String right_table_no1 )
	{
		setValue( ITEM_RIGHT_TABLE_NO, right_table_no1 );
	}

	/* ���ֶ� : String */
	public String getRight_column_no()
	{
		return getValue( ITEM_RIGHT_COLUMN_NO );
	}

	public void setRight_column_no( String right_column_no1 )
	{
		setValue( ITEM_RIGHT_COLUMN_NO, right_column_no1 );
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

	/* �������ͣ�0 ϵͳ������1 �û����� : String */
	public String getParam_type()
	{
		return getValue( ITEM_PARAM_TYPE );
	}

	public void setParam_type( String param_type1 )
	{
		setValue( ITEM_PARAM_TYPE, param_type1 );
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

