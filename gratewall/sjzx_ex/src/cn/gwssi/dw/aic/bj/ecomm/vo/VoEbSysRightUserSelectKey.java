package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[eb_sys_right_user]�����ݶ�����
 * @author Administrator
 *
 */
public class VoEbSysRightUserSelectKey extends VoBase
{
	private static final long serialVersionUID = 201209261622180003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_EB_SYS_RIGHT_USER_ID = "eb_sys_right_user_id" ;	/* �û�id */
	public static final String ITEM_USER_NAME = "user_name" ;		/* �û����� */
	
	/**
	 * ���캯��
	 */
	public VoEbSysRightUserSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoEbSysRightUserSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* �û�id : String */
	public String getEb_sys_right_user_id()
	{
		return getValue( ITEM_EB_SYS_RIGHT_USER_ID );
	}

	public void setEb_sys_right_user_id( String eb_sys_right_user_id1 )
	{
		setValue( ITEM_EB_SYS_RIGHT_USER_ID, eb_sys_right_user_id1 );
	}

	/* �û����� : String */
	public String getUser_name()
	{
		return getValue( ITEM_USER_NAME );
	}

	public void setUser_name( String user_name1 )
	{
		setValue( ITEM_USER_NAME, user_name1 );
	}

}

