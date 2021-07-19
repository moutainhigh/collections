package cn.gwssi.dw.rd.log.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[view_sys_app_log_query]的数据对象类
 * @author Administrator
 *
 */
public class VoViewSysAppLogQueryPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201208010743400004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_USERNAME = "username" ;			/* 用户姓名 */
	
	/**
	 * 构造函数
	 */
	public VoViewSysAppLogQueryPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoViewSysAppLogQueryPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 用户姓名 : String */
	public String getUsername()
	{
		return getValue( ITEM_USERNAME );
	}

	public void setUsername( String username1 )
	{
		setValue( ITEM_USERNAME, username1 );
	}

}

