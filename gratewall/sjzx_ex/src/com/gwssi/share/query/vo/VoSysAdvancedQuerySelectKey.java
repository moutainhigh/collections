package com.gwssi.share.query.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_advanced_query]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysAdvancedQuerySelectKey extends VoBase
{
	private static final long serialVersionUID = 200806261658150003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_NAME = "name" ;					/* �߼���ѯ�������� */
	public static final String ITEM_CREATE_BY = "create_by" ;		/* ������ */
	public static final String ITEM_CREATE_DATE = "create_date" ;	/* �������� */
	
	/**
	 * ���캯��
	 */
	public VoSysAdvancedQuerySelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysAdvancedQuerySelectKey(DataBus value)
	{
		super(value);
	}
	
	/* �߼���ѯ�������� : String */
	public String getName()
	{
		return getValue( ITEM_NAME );
	}

	public void setName( String name1 )
	{
		setValue( ITEM_NAME, name1 );
	}

	/* ������ : String */
	public String getCreate_by()
	{
		return getValue( ITEM_CREATE_BY );
	}

	public void setCreate_by( String create_by1 )
	{
		setValue( ITEM_CREATE_BY, create_by1 );
	}

	/* �������� : String */
	public String getCreate_date()
	{
		return getValue( ITEM_CREATE_DATE );
	}

	public void setCreate_date( String create_date1 )
	{
		setValue( ITEM_CREATE_DATE, create_date1 );
	}

}

