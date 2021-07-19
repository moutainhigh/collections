package com.gwssi.resource.exception.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[res_exception_date]�����ݶ�����
 * @author Administrator
 *
 */
public class VoResExceptionDatePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303131312090004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_EXCEPTION_DATE_ID = "exception_date_id" ;	/* ��������ID */
	
	/**
	 * ���캯��
	 */
	public VoResExceptionDatePrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoResExceptionDatePrimaryKey(DataBus value)
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

}

