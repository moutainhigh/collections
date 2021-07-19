package com.gwssi.dw.runmgr.db.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoSysDbViewParam extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_DB_VIEW_PARAM_ID = "sys_db_view_param_id";			/* ��ͼ����ID */
	public static final String ITEM_SYS_DB_VIEW_ID = "sys_db_view_id";			/* ��ͼID */
	public static final String ITEM_OPERATOR1 = "operator1";			/* or��and������ */
	public static final String ITEM_LEFT_PAREN = "left_paren";			/* ������ */
	public static final String ITEM_LEFT_TABLE_NO = "left_table_no";			/* ���ID */
	public static final String ITEM_LEFT_COLUMN_NO = "left_column_no";			/* ����ֶ�ID */
	public static final String ITEM_OPERATOR2 = "operator2";			/* ��������>,<,like�ȣ� */
	public static final String ITEM_RIGHT_TABLE_NO = "right_table_no";			/* �ұ�ID */
	public static final String ITEM_RIGHT_COLUMN_NO = "right_column_no";			/* �ұ��ֶ�ID */
	public static final String ITEM_RIGHT_PAREN = "right_paren";			/* ������ */
	public static final String ITEM_PARAM_ORDER = "param_order";			/* ����˳�� */

	public VoSysDbViewParam(DataBus value)
	{
		super(value);
	}

	public VoSysDbViewParam()
	{
		super();
	}

	/* ��ͼ����ID */
	public String getSys_db_view_param_id()
	{
		return getValue( ITEM_SYS_DB_VIEW_PARAM_ID );
	}

	public void setSys_db_view_param_id( String sys_db_view_param_id1 )
	{
		setValue( ITEM_SYS_DB_VIEW_PARAM_ID, sys_db_view_param_id1 );
	}

	/* ��ͼID */
	public String getSys_db_view_id()
	{
		return getValue( ITEM_SYS_DB_VIEW_ID );
	}

	public void setSys_db_view_id( String sys_db_view_id1 )
	{
		setValue( ITEM_SYS_DB_VIEW_ID, sys_db_view_id1 );
	}

	/* or��and������ */
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

	/* ���ID */
	public String getLeft_table_no()
	{
		return getValue( ITEM_LEFT_TABLE_NO );
	}

	public void setLeft_table_no( String left_table_no1 )
	{
		setValue( ITEM_LEFT_TABLE_NO, left_table_no1 );
	}

	/* ����ֶ�ID */
	public String getLeft_column_no()
	{
		return getValue( ITEM_LEFT_COLUMN_NO );
	}

	public void setLeft_column_no( String left_column_no1 )
	{
		setValue( ITEM_LEFT_COLUMN_NO, left_column_no1 );
	}

	/* ��������>,<,like�ȣ� */
	public String getOperator2()
	{
		return getValue( ITEM_OPERATOR2 );
	}

	public void setOperator2( String operator21 )
	{
		setValue( ITEM_OPERATOR2, operator21 );
	}

	/* �ұ�ID */
	public String getRight_table_no()
	{
		return getValue( ITEM_RIGHT_TABLE_NO );
	}

	public void setRight_table_no( String right_table_no1 )
	{
		setValue( ITEM_RIGHT_TABLE_NO, right_table_no1 );
	}

	/* �ұ��ֶ�ID */
	public String getRight_column_no()
	{
		return getValue( ITEM_RIGHT_COLUMN_NO );
	}

	public void setRight_column_no( String right_column_no1 )
	{
		setValue( ITEM_RIGHT_COLUMN_NO, right_column_no1 );
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

