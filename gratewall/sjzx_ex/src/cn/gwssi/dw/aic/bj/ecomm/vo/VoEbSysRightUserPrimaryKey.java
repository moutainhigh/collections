package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[eb_sys_right_user]的数据对象类
 * @author Administrator
 *
 */
public class VoEbSysRightUserPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201209261622180004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_EB_SYS_RIGHT_USER_ID = "eb_sys_right_user_id" ;	/* 用户id */
	
	/**
	 * 构造函数
	 */
	public VoEbSysRightUserPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoEbSysRightUserPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 用户id : String */
	public String getEb_sys_right_user_id()
	{
		return getValue( ITEM_EB_SYS_RIGHT_USER_ID );
	}

	public void setEb_sys_right_user_id( String eb_sys_right_user_id1 )
	{
		setValue( ITEM_EB_SYS_RIGHT_USER_ID, eb_sys_right_user_id1 );
	}

}

