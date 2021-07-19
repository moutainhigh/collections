package com.gwssi.file.manage.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[xt_ccgl_wjlb]�����ݶ�����
 * @author Administrator
 *
 */
public class VoXtCcglWjlbPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303271601110004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_CCLBBH_PK = "cclbbh_pk" ;		/* �ļ������ */
	
	/**
	 * ���캯��
	 */
	public VoXtCcglWjlbPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoXtCcglWjlbPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �ļ������ : String */
	public String getCclbbh_pk()
	{
		return getValue( ITEM_CCLBBH_PK );
	}

	public void setCclbbh_pk( String cclbbh_pk1 )
	{
		setValue( ITEM_CCLBBH_PK, cclbbh_pk1 );
	}

}

