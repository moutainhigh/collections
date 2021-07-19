package com.gwssi.file.demo.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[zw_tzgl_jbxx]�����ݶ�����
 * @author Administrator
 *
 */
public class VoZwTzglJbxxPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303271357410004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_JBXX_PK = "jbxx_pk" ;			/* ֪ͨ���-���� */
	
	/**
	 * ���캯��
	 */
	public VoZwTzglJbxxPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoZwTzglJbxxPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ֪ͨ���-���� : String */
	public String getJbxx_pk()
	{
		return getValue( ITEM_JBXX_PK );
	}

	public void setJbxx_pk( String jbxx_pk1 )
	{
		setValue( ITEM_JBXX_PK, jbxx_pk1 );
	}

}

