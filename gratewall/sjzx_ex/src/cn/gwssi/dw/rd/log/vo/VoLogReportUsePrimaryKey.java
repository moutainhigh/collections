package cn.gwssi.dw.rd.log.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[log_report_use]�����ݶ�����
 * @author Administrator
 *
 */
public class VoLogReportUsePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201208061616070008L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_LOG_REPORT_USE_ID = "log_report_use_id" ;	/* ��־�������id */
	
	/**
	 * ���캯��
	 */
	public VoLogReportUsePrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoLogReportUsePrimaryKey(DataBus value)
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

}

