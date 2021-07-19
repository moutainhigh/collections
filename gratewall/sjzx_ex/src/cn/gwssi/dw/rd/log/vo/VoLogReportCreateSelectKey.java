package cn.gwssi.dw.rd.log.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[log_report_create]�����ݶ�����
 * @author Administrator
 *
 */
public class VoLogReportCreateSelectKey extends VoBase
{
	private static final long serialVersionUID = 201208061604290003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_LOG_REPORT_CREATE_ID = "log_report_create_id" ;	/* ��־����id */
	public static final String ITEM_REPORT_NAME = "report_name" ;	/* �������� */
	public static final String ITEM_PUBLISH_DATE = "publish_date" ;	/* �������� */
	public static final String ITEM_STATE = "state" ;				/* ״̬ */
	public static final String ITEM_CREATE_DATE = "create_date" ;	/* �������� */
	
	/**
	 * ���캯��
	 */
	public VoLogReportCreateSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoLogReportCreateSelectKey(DataBus value)
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

	/* �������� : String */
	public String getReport_name()
	{
		return getValue( ITEM_REPORT_NAME );
	}

	public void setReport_name( String report_name1 )
	{
		setValue( ITEM_REPORT_NAME, report_name1 );
	}

	/* �������� : String */
	public String getPublish_date()
	{
		return getValue( ITEM_PUBLISH_DATE );
	}

	public void setPublish_date( String publish_date1 )
	{
		setValue( ITEM_PUBLISH_DATE, publish_date1 );
	}

	/* ״̬ : String */
	public String getState()
	{
		return getValue( ITEM_STATE );
	}

	public void setState( String state1 )
	{
		setValue( ITEM_STATE, state1 );
	}

	/* �������� : String */
	public String getCreate_date()
	{
		return getValue( ITEM_CREATE_DATE );
	}

	public void setCreate_date( String create_date1 )
	{
		setValue( ITEM_CREATE_DATE, create_date1 );
	}

}

