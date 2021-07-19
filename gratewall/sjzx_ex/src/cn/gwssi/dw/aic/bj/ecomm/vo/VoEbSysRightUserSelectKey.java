package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[eb_sys_right_user]的数据对象类
 * @author Administrator
 *
 */
public class VoEbSysRightUserSelectKey extends VoBase
{
	private static final long serialVersionUID = 201209261622180003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_EB_SYS_RIGHT_USER_ID = "eb_sys_right_user_id" ;	/* 用户id */
	public static final String ITEM_USER_NAME = "user_name" ;		/* 用户姓名 */
	
	/**
	 * 构造函数
	 */
	public VoEbSysRightUserSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoEbSysRightUserSelectKey(DataBus value)
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

	/* 用户姓名 : String */
	public String getUser_name()
	{
		return getValue( ITEM_USER_NAME );
	}

	public void setUser_name( String user_name1 )
	{
		setValue( ITEM_USER_NAME, user_name1 );
	}

}

