package cn.gwssi.sys.feedback.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_feedback]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysFeedback extends VoBase
{
	private static final long serialVersionUID = 201212311051280002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_FEEDBACK_ID = "sys_feedback_id" ;	/* �������ID */
	public static final String ITEM_CONTENT = "content" ;			/* ����������� */
	public static final String ITEM_PUBLISH_DATE = "publish_date" ;	/* ����ʱ�� */
	public static final String ITEM_AUTHOR = "author" ;				/* ������ */
	public static final String ITEM_STATUS = "status" ;				/* ��Ч��־ */
	public static final String ITEM_DESCRIPTION = "description" ;	/* ������ */
	
	/**
	 * ���캯��
	 */
	public VoSysFeedback()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysFeedback(DataBus value)
	{
		super(value);
	}
	
	/* �������ID : String */
	public String getSys_feedback_id()
	{
		return getValue( ITEM_SYS_FEEDBACK_ID );
	}

	public void setSys_feedback_id( String sys_feedback_id1 )
	{
		setValue( ITEM_SYS_FEEDBACK_ID, sys_feedback_id1 );
	}

	/* ����������� : String */
	public String getContent()
	{
		return getValue( ITEM_CONTENT );
	}

	public void setContent( String content1 )
	{
		setValue( ITEM_CONTENT, content1 );
	}

	/* ����ʱ�� : String */
	public String getPublish_date()
	{
		return getValue( ITEM_PUBLISH_DATE );
	}

	public void setPublish_date( String publish_date1 )
	{
		setValue( ITEM_PUBLISH_DATE, publish_date1 );
	}

	/* ������ : String */
	public String getAuthor()
	{
		return getValue( ITEM_AUTHOR );
	}

	public void setAuthor( String author1 )
	{
		setValue( ITEM_AUTHOR, author1 );
	}

	/* ��Ч��־ : String */
	public String getStatus()
	{
		return getValue( ITEM_STATUS );
	}

	public void setStatus( String status1 )
	{
		setValue( ITEM_STATUS, status1 );
	}

	/* ������ : String */
	public String getDescription()
	{
		return getValue( ITEM_DESCRIPTION );
	}

	public void setDescription( String description1 )
	{
		setValue( ITEM_DESCRIPTION, description1 );
	}

}

