package com.gwssi.log.report.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[log_report_create]�����ݶ�����
 * @author Administrator
 *
 */
public class VoLogReportCreatePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201208061604290004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_LOG_REPORT_CREATE_ID = "log_report_create_id" ;	/* ��־����id */
	
	/**
	 * ���캯��
	 */
	public VoLogReportCreatePrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoLogReportCreatePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ��־����id : String */
	public String getLog_report_create_id()
	{
		return getValue( ITEM_LOG_REPORT_CREATE_ID );
	}

	public void setLog_report_create_id( String log_report_create_id1 )
	{
		setValue( ITEM_LOG_REPORT_CREATE_ID, log_report_create_id1 );
	}

}

