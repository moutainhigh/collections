package com.gwssi.file.demo.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[zw_tzgl_jbxx]�����ݶ�����
 * @author Administrator
 *
 */
public class VoZwTzglJbxxSelectKey extends VoBase
{
	private static final long serialVersionUID = 201303271357410003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_TZMC = "tzmc" ;					/* ֪ͨ���� */
	public static final String ITEM_FBSJ = "fbsj" ;					/* ����ʱ�� */
	public static final String ITEM_TZZT = "tzzt" ;					/* ֪ͨ״̬ */
	
	/**
	 * ���캯��
	 */
	public VoZwTzglJbxxSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoZwTzglJbxxSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ֪ͨ���� : String */
	public String getTzmc()
	{
		return getValue( ITEM_TZMC );
	}

	public void setTzmc( String tzmc1 )
	{
		setValue( ITEM_TZMC, tzmc1 );
	}

	/* ����ʱ�� : String */
	public String getFbsj()
	{
		return getValue( ITEM_FBSJ );
	}

	public void setFbsj( String fbsj1 )
	{
		setValue( ITEM_FBSJ, fbsj1 );
	}

	/* ֪ͨ״̬ : String */
	public String getTzzt()
	{
		return getValue( ITEM_TZZT );
	}

	public void setTzzt( String tzzt1 )
	{
		setValue( ITEM_TZZT, tzzt1 );
	}

}

