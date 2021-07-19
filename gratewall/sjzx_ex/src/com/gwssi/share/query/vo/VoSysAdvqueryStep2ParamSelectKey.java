package com.gwssi.share.query.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_advquery_step2_param]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysAdvqueryStep2ParamSelectKey extends VoBase
{
	private static final long serialVersionUID = 200809261020110007L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_ADVANCED_QUERY_ID = "sys_advanced_query_id" ;	/* �߼���ѯ��� */
	
	/**
	 * ���캯��
	 */
	public VoSysAdvqueryStep2ParamSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysAdvqueryStep2ParamSelectKey(DataBus value)
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

