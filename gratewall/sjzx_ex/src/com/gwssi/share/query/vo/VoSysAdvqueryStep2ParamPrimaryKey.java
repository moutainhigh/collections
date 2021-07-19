package com.gwssi.share.query.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_advquery_step2_param]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysAdvqueryStep2ParamPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200809261020110008L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_ADVQUERY_STEP2_PARAM_ID = "sys_advquery_step2_param_id" ;	/* �߼���ѯ������������ */
	
	/**
	 * ���캯��
	 */
	public VoSysAdvqueryStep2ParamPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysAdvqueryStep2ParamPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �߼���ѯ������������ : String */
	public String getSys_advquery_step2_param_id()
	{
		return getValue( ITEM_SYS_ADVQUERY_STEP2_PARAM_ID );
	}

	public void setSys_advquery_step2_param_id( String sys_advquery_step2_param_id1 )
	{
		setValue( ITEM_SYS_ADVQUERY_STEP2_PARAM_ID, sys_advquery_step2_param_id1 );
	}

}

