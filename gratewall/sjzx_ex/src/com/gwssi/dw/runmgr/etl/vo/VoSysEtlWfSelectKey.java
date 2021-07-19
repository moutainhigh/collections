package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_etl_wf]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysEtlWfSelectKey extends VoBase
{
	private static final long serialVersionUID = 200805081507300003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_REP_ID = "rep_id" ;				/* ��ĿID */
	
	/**
	 * ���캯��
	 */
	public VoSysEtlWfSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysEtlWfSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ��ĿID : String */
	public String getRep_id()
	{
		return getValue( ITEM_REP_ID );
	}

	public void setRep_id( String rep_id1 )
	{
		setValue( ITEM_REP_ID, rep_id1 );
	}

}

