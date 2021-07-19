package com.gwssi.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[common_standard]�����ݶ�����
 * @author Administrator
 *
 */
public class VoCommonStandardSelectKey extends VoBase
{
	private static final long serialVersionUID = 201304121530000003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_STANDARD_NAME = "standard_name" ;	/* ��׼���� */
	public static final String ITEM_SPECIFICATE_NO = "specificate_no" ;	/* ���ͺ� */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* ����ʱ�� */
	
	/**
	 * ���캯��
	 */
	public VoCommonStandardSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoCommonStandardSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ��׼���� : String */
	public String getStandard_name()
	{
		return getValue( ITEM_STANDARD_NAME );
	}

	public void setStandard_name( String standard_name1 )
	{
		setValue( ITEM_STANDARD_NAME, standard_name1 );
	}

	/* ���ͺ� : String */
	public String getSpecificate_no()
	{
		return getValue( ITEM_SPECIFICATE_NO );
	}

	public void setSpecificate_no( String specificate_no1 )
	{
		setValue( ITEM_SPECIFICATE_NO, specificate_no1 );
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

}

