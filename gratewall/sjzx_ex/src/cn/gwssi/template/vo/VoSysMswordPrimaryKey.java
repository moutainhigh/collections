package cn.gwssi.template.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_msword]的数据对象类
 * @author Administrator
 *
 */
public class VoSysMswordPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201207301446060004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_MSWORD_ID = "sys_msword_id" ;	/* word模板id */
	
	/**
	 * 构造函数
	 */
	public VoSysMswordPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysMswordPrimaryKey(DataBus value)
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

}

