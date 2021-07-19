package cn.gwssi.template.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_msword]的数据对象类
 * @author Administrator
 *
 */
public class VoSysMsword extends VoBase
{
	private static final long serialVersionUID = 201207301446060002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_MSWORD_ID = "sys_msword_id" ;	/* word模板id */
	public static final String ITEM_SYS_MSWORD_NAME = "sys_msword_name" ;	/* word模板名称 */
	public static final String ITEM_SYS_MSWORD_BOOKMARKS = "sys_msword_bookmarks" ;	/* 书签列表 */
	public static final String ITEM_SYS_MSWORD_TEMPLATE = "sys_msword_template" ;	/* 模板文件 */
	public static final String ITEM_SYS_MSWORD_DESP = "sys_msword_desp" ;	/* 模板描述 */
	
	/**
	 * 构造函数
	 */
	public VoSysMsword()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysMsword(DataBus value)
	{
		super(value);
	}
	
	/* word模板id : String */
	public String getSys_msword_id()
	{
		return getValue( ITEM_SYS_MSWORD_ID );
	}

	public void setSys_msword_id( String sys_msword_id1 )
	{
		setValue( ITEM_SYS_MSWORD_ID, sys_msword_id1 );
	}

	/* word模板名称 : String */
	public String getSys_msword_name()
	{
		return getValue( ITEM_SYS_MSWORD_NAME );
	}

	public void setSys_msword_name( String sys_msword_name1 )
	{
		setValue( ITEM_SYS_MSWORD_NAME, sys_msword_name1 );
	}

	/* 书签列表 : String */
	public String getSys_msword_bookmarks()
	{
		return getValue( ITEM_SYS_MSWORD_BOOKMARKS );
	}

	public void setSys_msword_bookmarks( String sys_msword_bookmarks1 )
	{
		setValue( ITEM_SYS_MSWORD_BOOKMARKS, sys_msword_bookmarks1 );
	}

	/* 模板文件 : String */
	public String getSys_msword_template()
	{
		return getValue( ITEM_SYS_MSWORD_TEMPLATE );
	}

	public void setSys_msword_template( String sys_msword_template1 )
	{
		setValue( ITEM_SYS_MSWORD_TEMPLATE, sys_msword_template1 );
	}

	/* 模板描述 : String */
	public String getSys_msword_desp()
	{
		return getValue( ITEM_SYS_MSWORD_DESP );
	}

	public void setSys_msword_desp( String sys_msword_desp1 )
	{
		setValue( ITEM_SYS_MSWORD_DESP, sys_msword_desp1 );
	}

}

