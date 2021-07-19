package cn.gwssi.search.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_search_config]的数据对象类
 * @author Administrator
 *
 */
public class VoSysSearchConfigPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201208211741000004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_SEARCH_CONFIG_ID = "sys_search_config_id" ;	/* 搜索配置ID */
	
	/**
	 * 构造函数
	 */
	public VoSysSearchConfigPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysSearchConfigPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 搜索配置ID : String */
	public String getSys_search_config_id()
	{
		return getValue( ITEM_SYS_SEARCH_CONFIG_ID );
	}

	public void setSys_search_config_id( String sys_search_config_id1 )
	{
		setValue( ITEM_SYS_SEARCH_CONFIG_ID, sys_search_config_id1 );
	}

}

