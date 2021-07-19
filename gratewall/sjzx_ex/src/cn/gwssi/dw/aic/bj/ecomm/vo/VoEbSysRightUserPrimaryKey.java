package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[eb_sys_right_user]�����ݶ�����
 * @author Administrator
 *
 */
public class VoEbSysRightUserPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201209261622180004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_EB_SYS_RIGHT_USER_ID = "eb_sys_right_user_id" ;	/* �û�id */
	
	/**
	 * ���캯��
	 */
	public VoEbSysRightUserPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoEbSysRightUserPrimaryKey(DataBus value)
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

}

