package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_etl_wf]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysEtlWfPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200805081507300004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_ETL_WF_ID = "sys_etl_wf_id" ;	/* ID */
	
	/**
	 * ���캯��
	 */
	public VoSysEtlWfPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysEtlWfPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ID : String */
	public String getSys_etl_wf_id()
	{
		return getValue( ITEM_SYS_ETL_WF_ID );
	}

	public void setSys_etl_wf_id( String sys_etl_wf_id1 )
	{
		setValue( ITEM_SYS_ETL_WF_ID, sys_etl_wf_id1 );
	}

}

