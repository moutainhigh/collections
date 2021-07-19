package cn.gwssi.sys.feedback.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_feedback]的数据对象类
 * @author Administrator
 *
 */
public class VoSysFeedbackPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201212311051280004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_FEEDBACK_ID = "sys_feedback_id" ;	/* 意见反馈ID */
	
	/**
	 * 构造函数
	 */
	public VoSysFeedbackPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysFeedbackPrimaryKey(DataBus value)
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

}

