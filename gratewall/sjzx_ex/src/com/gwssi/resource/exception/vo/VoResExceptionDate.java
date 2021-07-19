package com.gwssi.resource.exception.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[res_exception_date]�����ݶ�����
 * @author Administrator
 *
 */
public class VoResExceptionDate extends VoBase
{
	private static final long serialVersionUID = 201303131312090002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_EXCEPTION_DATE_ID = "exception_date_id" ;	/* ��������ID */
	public static final String ITEM_EXCEPTION_DATE_NAME = "exception_date_name" ;	/* �������� */
	public static final String ITEM_EXCEPTION_TYPE = "exception_type" ;	/* �������� */
	public static final String ITEM_EXCEPTION_DESC = "exception_desc" ;	/* �������� */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* ��Ч��� */
	public static final String ITEM_CREATOR_ID = "creator_id" ;		/* ������ID */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* ����ʱ�� */
	public static final String ITEM_LAST_MODIFY_ID = "last_modify_id" ;	/* ����޸���ID */
	public static final String ITEM_LAST_MODIFY_TIME = "last_modify_time" ;	/* ����޸�ʱ�� */
	public static final String ITEM_EXCEPTION_ID = "exception_id" ;	/* ������ */
	public static final String ITEM_EXCEPTION_DATE = "exception_date" ;	/* �������� */
	
	/**
	 * ���캯��
	 */
	public VoResExceptionDate()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoResExceptionDate(DataBus value)
	{
		super(value);
	}
	
	/* ��������ID : String */
	public String getException_date_id()
	{
		return getValue( ITEM_EXCEPTION_DATE_ID );
	}

	public void setException_date_id( String exception_date_id1 )
	{
		setValue( ITEM_EXCEPTION_DATE_ID, exception_date_id1 );
	}

	/* �������� : String */
	public String getException_date_name()
	{
		return getValue( ITEM_EXCEPTION_DATE_NAME );
	}

	public void setException_date_name( String exception_date_name1 )
	{
		setValue( ITEM_EXCEPTION_DATE_NAME, exception_date_name1 );
	}

	/* �������� : String */
	public String getException_type()
	{
		return getValue( ITEM_EXCEPTION_TYPE );
	}

	public void setException_type( String exception_type1 )
	{
		setValue( ITEM_EXCEPTION_TYPE, exception_type1 );
	}

	/* �������� : String */
	public String getException_desc()
	{
		return getValue( ITEM_EXCEPTION_DESC );
	}

	public void setException_desc( String exception_desc1 )
	{
		setValue( ITEM_EXCEPTION_DESC, exception_desc1 );
	}

	/* ��Ч��� : String */
	public String getIs_markup()
	{
		return getValue( ITEM_IS_MARKUP );
	}

	public void setIs_markup( String is_markup1 )
	{
		setValue( ITEM_IS_MARKUP, is_markup1 );
	}

	/* ������ID : String */
	public String getCreator_id()
	{
		return getValue( ITEM_CREATOR_ID );
	}

	public void setCreator_id( String creator_id1 )
	{
		setValue( ITEM_CREATOR_ID, creator_id1 );
	}

	/* ����ʱ�� : String */
	public String getCreated_time()
	{
		return getValue( ITEM_CREATED_TIME );
	}

	public void setCreated_time( String created_time1 )
	{
		setValue( ITEM_CREATED_TIME, created_time1 );
	}

	/* ����޸���ID : String */
	public String getLast_modify_id()
	{
		return getValue( ITEM_LAST_MODIFY_ID );
	}

	public void setLast_modify_id( String last_modify_id1 )
	{
		setValue( ITEM_LAST_MODIFY_ID, last_modify_id1 );
	}

	/* ����޸�ʱ�� : String */
	public String getLast_modify_time()
	{
		return getValue( ITEM_LAST_MODIFY_TIME );
	}

	public void setLast_modify_time( String last_modify_time1 )
	{
		setValue( ITEM_LAST_MODIFY_TIME, last_modify_time1 );
	}

	/* ������ : String */
	public String getException_id()
	{
		return getValue( ITEM_EXCEPTION_ID );
	}

	public void setException_id( String exception_id1 )
	{
		setValue( ITEM_EXCEPTION_ID, exception_id1 );
	}

	/* �������� : String */
	public String getException_date()
	{
		return getValue( ITEM_EXCEPTION_DATE );
	}

	public void setException_date( String exception_date1 )
	{
		setValue( ITEM_EXCEPTION_DATE, exception_date1 );
	}

}

