package cn.gwssi.dw.rd.log.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[log_report_use]�����ݶ�����
 * @author Administrator
 *
 */
public class VoLogReportUse extends VoBase
{
	private static final long serialVersionUID = 201208061616070006L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_LOG_REPORT_USE_ID = "log_report_use_id" ;	/* ��־�������id */
	public static final String ITEM_LOG_REPORT_CREATE_ID = "log_report_create_id" ;	/* ��־��������id */
	public static final String ITEM_BROWSER_PERSON = "browser_person" ;	/* ����� */
	public static final String ITEM_BROWSER_DATE = "browser_date" ;	/* ������� */
	public static final String ITEM_OPERATE = "operate" ;			/* ���� */
	public static final String ITEM_FILENAME = "filename" ;			/* �ļ��� */
	public static final String ITEM_PATH = "path" ;					/* ·�� */
	public static final String ITEM_TIMESTAMP = "timestamp" ;		/* ʱ��� */
	
	/**
	 * ���캯��
	 */
	public VoLogReportUse()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoLogReportUse(DataBus value)
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

	/* ��־��������id : String */
	public String getLog_report_create_id()
	{
		return getValue( ITEM_LOG_REPORT_CREATE_ID );
	}

	public void setLog_report_create_id( String log_report_create_id1 )
	{
		setValue( ITEM_LOG_REPORT_CREATE_ID, log_report_create_id1 );
	}

	/* ����� : String */
	public String getBrowser_person()
	{
		return getValue( ITEM_BROWSER_PERSON );
	}

	public void setBrowser_person( String browser_person1 )
	{
		setValue( ITEM_BROWSER_PERSON, browser_person1 );
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

	/* ���� : String */
	public String getOperate()
	{
		return getValue( ITEM_OPERATE );
	}

	public void setOperate( String operate1 )
	{
		setValue( ITEM_OPERATE, operate1 );
	}

	/* �ļ��� : String */
	public String getFilename()
	{
		return getValue( ITEM_FILENAME );
	}

	public void setFilename( String filename1 )
	{
		setValue( ITEM_FILENAME, filename1 );
	}

	/* ·�� : String */
	public String getPath()
	{
		return getValue( ITEM_PATH );
	}

	public void setPath( String path1 )
	{
		setValue( ITEM_PATH, path1 );
	}

	/* ʱ��� : String */
	public String getTimestamp()
	{
		return getValue( ITEM_TIMESTAMP );
	}

	public void setTimestamp( String timestamp1 )
	{
		setValue( ITEM_TIMESTAMP, timestamp1 );
	}

}

