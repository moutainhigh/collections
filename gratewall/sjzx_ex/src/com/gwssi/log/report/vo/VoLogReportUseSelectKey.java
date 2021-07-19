package com.gwssi.log.report.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[log_report_use]�����ݶ�����
 * @author Administrator
 *
 */
public class VoLogReportUseSelectKey extends VoBase
{
	private static final long serialVersionUID = 201208061616070007L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_LOG_REPORT_USE_ID = "log_report_use_id" ;	/* ��־�������id */
	public static final String ITEM_BROWSER_DATE = "browser_date" ;	/* ������� */
	
	/**
	 * ���캯��
	 */
	public VoLogReportUseSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoLogReportUseSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ��־�������id : String */
	public String getLog_report_use_id()
	{
		return getValue( ITEM_LOG_REPORT_USE_ID );
	}

	public void setLog_report_use_id( String log_report_use_id1 )
	{
		setValue( ITEM_LOG_REPORT_USE_ID, log_report_use_id1 );
	}

	/* ������� : String */
	public String getBrowser_date()
	{
		return getValue( ITEM_BROWSER_DATE );
	}

	public void setBrowser_date( String browser_date1 )
	{
		setValue( ITEM_BROWSER_DATE, browser_date1 );
	}

}

