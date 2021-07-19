package cn.gwssi.template.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_msword]的数据对象类
 * @author Administrator
 *
 */
public class VoSysMswordSelectKey extends VoBase
{
	private static final long serialVersionUID = 201207301446060003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_MSWORD_ID = "sys_msword_id" ;	/* word模板id */
	public static final String ITEM_SYS_MSWORD_NAME = "sys_msword_name" ;	/* word模板名称 */
	
	/**
	 * 构造函数
	 */
	public VoSysMswordSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysMswordSelectKey(DataBus value)
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

}

