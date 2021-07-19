package cn.gwssi.sys.feedback.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_feedback]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysFeedbackPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201212311051280004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_FEEDBACK_ID = "sys_feedback_id" ;	/* �������ID */
	
	/**
	 * ���캯��
	 */
	public VoSysFeedbackPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysFeedbackPrimaryKey(DataBus value)
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

}

