package com.gwssi.share.query.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_advquery_step1_param]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysAdvqueryStep1ParamPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200809261021030012L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_ADVQUERY_STEP1_PARAM_ID = "sys_advquery_step1_param_id" ;	/* �߼���ѯ����һ������� */
	
	/**
	 * ���캯��
	 */
	public VoSysAdvqueryStep1ParamPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysAdvqueryStep1ParamPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �߼���ѯ����һ������� : String */
	public String getSys_advquery_step1_param_id()
	{
		return getValue( ITEM_SYS_ADVQUERY_STEP1_PARAM_ID );
	}

	public void setSys_advquery_step1_param_id( String sys_advquery_step1_param_id1 )
	{
		setValue( ITEM_SYS_ADVQUERY_STEP1_PARAM_ID, sys_advquery_step1_param_id1 );
	}

}

