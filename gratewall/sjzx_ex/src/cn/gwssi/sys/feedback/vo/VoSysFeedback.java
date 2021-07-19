package cn.gwssi.sys.feedback.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_feedback]的数据对象类
 * @author Administrator
 *
 */
public class VoSysFeedback extends VoBase
{
	private static final long serialVersionUID = 201212311051280002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_FEEDBACK_ID = "sys_feedback_id" ;	/* 意见反馈ID */
	public static final String ITEM_CONTENT = "content" ;			/* 意见反馈内容 */
	public static final String ITEM_PUBLISH_DATE = "publish_date" ;	/* 发布时间 */
	public static final String ITEM_AUTHOR = "author" ;				/* 发布人 */
	public static final String ITEM_STATUS = "status" ;				/* 有效标志 */
	public static final String ITEM_DESCRIPTION = "description" ;	/* 处理结果 */
	
	/**
	 * 构造函数
	 */
	public VoSysFeedback()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysFeedback(DataBus value)
	{
		super(value);
	}
	
	/* 意见反馈ID : String */
	public String getSys_feedback_id()
	{
		return getValue( ITEM_SYS_FEEDBACK_ID );
	}

	public void setSys_feedback_id( String sys_feedback_id1 )
	{
		setValue( ITEM_SYS_FEEDBACK_ID, sys_feedback_id1 );
	}

	/* 意见反馈内容 : String */
	public String getContent()
	{
		return getValue( ITEM_CONTENT );
	}

	public void setContent( String content1 )
	{
		setValue( ITEM_CONTENT, content1 );
	}

	/* 发布时间 : String */
	public String getPublish_date()
	{
		return getValue( ITEM_PUBLISH_DATE );
	}

	public void setPublish_date( String publish_date1 )
	{
		setValue( ITEM_PUBLISH_DATE, publish_date1 );
	}

	/* 发布人 : String */
	public String getAuthor()
	{
		return getValue( ITEM_AUTHOR );
	}

	public void setAuthor( String author1 )
	{
		setValue( ITEM_AUTHOR, author1 );
	}

	/* 有效标志 : String */
	public String getStatus()
	{
		return getValue( ITEM_STATUS );
	}

	public void setStatus( String status1 )
	{
		setValue( ITEM_STATUS, status1 );
	}

	/* 处理结果 : String */
	public String getDescription()
	{
		return getValue( ITEM_DESCRIPTION );
	}

	public void setDescription( String description1 )
	{
		setValue( ITEM_DESCRIPTION, description1 );
	}

}

