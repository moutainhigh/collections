package com.gwssi.dw.runmgr.services.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_svr_service]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSvrServiceSelectKey extends VoBase
{
	private static final long serialVersionUID = 200805061510580007L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SVR_CODE = "svr_code" ;			/* ������� */
	public static final String ITEM_NAME = "name" ;					/* ����������� */
	public static final String ITEM_CREATE_BY = "create_by" ;		/* ������ */
	public static final String ITEM_CREATE_DATE = "create_date" ;	/* �������� */
	
	/**
	 * ���캯��
	 */
	public VoSysSvrServiceSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSvrServiceSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ������� : String */
	public String getSvr_code()
	{
		return getValue( ITEM_SVR_CODE );
	}

	public void setSvr_code( String svr_code1 )
	{
		setValue( ITEM_SVR_CODE, svr_code1 );
	}

	/* ����������� : String */
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

