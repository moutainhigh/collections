package com.gwssi.log.systemlog.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[first_page_query]�����ݶ�����
 * @author Administrator
 *
 */
public class VoFirstPageQuerySystemlogSelectKey extends VoBase
{
	private static final long serialVersionUID = 201304251426190003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_CREAT_START_TIME = "creat_start_time" ;	/* ����ʱ�� */
	public static final String ITEM_CREAT_END_TIME = "creat_end_time" ;	/* ����ʱ�� */
	public static final String ITEM_USERNAME = "username" ;			/* �û��� */
	
	/**
	 * ���캯��
	 */
	public VoFirstPageQuerySystemlogSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoFirstPageQuerySystemlogSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ����ʱ�� : String */
	public String getCreat_start_time()
	{
		return getValue( ITEM_CREAT_START_TIME );
	}

	public void setCreat_start_time( String creat_start_time1 )
	{
		setValue( ITEM_CREAT_START_TIME, creat_start_time1 );
	}

	/* ����ʱ�� : String */
	public String getCreat_end_time()
	{
		return getValue( ITEM_CREAT_END_TIME );
	}

	public void setCreat_end_time( String creat_end_time1 )
	{
		setValue( ITEM_CREAT_END_TIME, creat_end_time1 );
	}

	/* �û��� : String */
	public String getUsername()
	{
		return getValue( ITEM_USERNAME );
	}

	public void setUsername( String username1 )
	{
		setValue( ITEM_USERNAME, username1 );
	}

}

