package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_svr_service_param]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSvrServiceParam extends VoBase
{
	private static final long serialVersionUID = 200809051535250006L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_SVR_SERVICE_PARAM_ID = "sys_svr_service_param_id" ;	/* �����������ӱ�� */
	public static final String ITEM_SYS_SVR_SERVICE_ID = "sys_svr_service_id" ;	/* ��������� */
	public static final String ITEM_OPERATOR1 = "operator1" ;		/* ���ӷ�1 */
	public static final String ITEM_LEFT_PAREN = "left_paren" ;		/* ������ */
	public static final String ITEM_LEFT_TABLE_NO = "left_table_no" ;	/* ��� */
	public static final String ITEM_LEFT_COLUMN_NO = "left_column_no" ;	/* ���ֶ� */
	public static final String ITEM_OPERATOR2 = "operator2" ;		/* ���ӷ�2 */
	public static final String ITEM_RIGHT_TABLE_NO = "right_table_no" ;	/* �ұ� */
	public static final String ITEM_RIGHT_COLUMN_NO = "right_column_no" ;	/* ���ֶ� */
	public static final String ITEM_RIGHT_PAREN = "right_paren" ;	/* ������ */
	
	/**
	 * ���캯��
	 */
	public VoSysSvrServiceParam()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSvrServiceParam(DataBus value)
	{
		super(value);
	}
	
	/* �����������ӱ�� : String */
	public String getSys_svr_service_param_id()
	{
		return getValue( ITEM_SYS_SVR_SERVICE_PARAM_ID );
	}

	public void setSys_svr_service_param_id( String sys_svr_service_param_id1 )
	{
		setValue( ITEM_SYS_SVR_SERVICE_PARAM_ID, sys_svr_service_param_id1 );
	}

	/* ��������� : String */
	public String getSys_svr_service_id()
	{
		return getValue( ITEM_SYS_SVR_SERVICE_ID );
	}

	public void setSys_svr_service_id( String sys_svr_service_id1 )
	{
		setValue( ITEM_SYS_SVR_SERVICE_ID, sys_svr_service_id1 );
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

}

