package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_etl_wf]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysEtlWf extends VoBase
{
	private static final long serialVersionUID = 200805081507300002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_ETL_WF_ID = "sys_etl_wf_id" ;	/* ID */
	public static final String ITEM_REP_ID = "rep_id" ;				/* ��ĿID */
	public static final String ITEM_REP_FOLDERNAME = "rep_foldername" ;	/* �ļ������� */
	public static final String ITEM_WF_NAME = "wf_name" ;			/* ��ȡ�������� */
	public static final String ITEM_WF_MS = "wf_ms" ;				/* ��ȡ�������� */
	/**
	 * ���캯��
	 */
	public VoSysEtlWf()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysEtlWf(DataBus value)
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

	/* ��ĿID : String */
	public String getRep_id()
	{
		return getValue( ITEM_REP_ID );
	}

	public void setRep_id( String rep_id1 )
	{
		setValue( ITEM_REP_ID, rep_id1 );
	}

	/* �ļ������� : String */
	public String getRep_foldername()
	{
		return getValue( ITEM_REP_FOLDERNAME );
	}

	public void setRep_foldername( String rep_foldername1 )
	{
		setValue( ITEM_REP_FOLDERNAME, rep_foldername1 );
	}

	/* ��ȡ�������� : String */
	public String getWf_name()
	{
		return getValue( ITEM_WF_NAME );
	}

	public void setWf_name( String wf_name1 )
	{
		setValue( ITEM_WF_NAME, wf_name1 );
	}

	/* ��ȡ�������� : String */
	public String getWf_ms()
	{
		return getValue( ITEM_WF_MS );
	}

	public void setWf_ms( String wf_ms1 )
	{
		setValue( ITEM_WF_MS, wf_ms1 );
	}
}

