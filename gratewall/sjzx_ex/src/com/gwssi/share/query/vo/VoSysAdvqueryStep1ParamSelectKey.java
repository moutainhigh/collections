package com.gwssi.share.query.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_advquery_step1_param]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysAdvqueryStep1ParamSelectKey extends VoBase
{
	private static final long serialVersionUID = 200809261021030011L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_ADVANCED_QUERY_ID = "sys_advanced_query_id" ;	/* �߼���ѯ��� */
	
	/**
	 * ���캯��
	 */
	public VoSysAdvqueryStep1ParamSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysAdvqueryStep1ParamSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* �߼���ѯ��� : String */
	public String getSys_advanced_query_id()
	{
		return getValue( ITEM_SYS_ADVANCED_QUERY_ID );
	}

	public void setSys_advanced_query_id( String sys_advanced_query_id1 )
	{
		setValue( ITEM_SYS_ADVANCED_QUERY_ID, sys_advanced_query_id1 );
	}

}

