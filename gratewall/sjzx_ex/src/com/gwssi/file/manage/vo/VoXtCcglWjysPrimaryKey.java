package com.gwssi.file.manage.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[xt_ccgl_wjys]�����ݶ�����
 * @author Administrator
 *
 */
public class VoXtCcglWjysPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303271612500008L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_YSBH_PK = "ysbh_pk" ;			/* ӳ���� */
	
	/**
	 * ���캯��
	 */
	public VoXtCcglWjysPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoXtCcglWjysPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ӳ���� : String */
	public String getYsbh_pk()
	{
		return getValue( ITEM_YSBH_PK );
	}

	public void setYsbh_pk( String ysbh_pk1 )
	{
		setValue( ITEM_YSBH_PK, ysbh_pk1 );
	}

}
