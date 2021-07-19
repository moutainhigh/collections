package cn.gwssi.search.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_search_config]的数据对象类
 * @author Administrator
 *
 */
public class VoSysSearchConfig extends VoBase
{
	private static final long serialVersionUID = 201208211741000002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_SEARCH_CONFIG_ID = "sys_search_config_id" ;	/* 搜索配置ID */
	public static final String ITEM_SYS_SVR_USER_ID = "sys_svr_user_id" ;	/* 用户ID */
	public static final String ITEM_PERMIT_SUBJECT = "permit_subject" ;	/* 授权主题 */
	public static final String ITEM_CREATE_BY = "create_by" ;		/* 创建人 */
	public static final String ITEM_CREATE_DATE = "create_date" ;	/* 创建日期 */
	public static final String ITEM_CONFIG_ORDER = "config_order" ;	/* 配置顺序 */
	public static final String ITEM_IS_PAUSE = "is_pause" ;			/* 是否暂停 */
	
	/**
	 * 构造函数
	 */
	public VoSysSearchConfig()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysSearchConfig(DataBus value)
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

	/* 用户ID : String */
	public String getSys_svr_user_id()
	{
		return getValue( ITEM_SYS_SVR_USER_ID );
	}

	public void setSys_svr_user_id( String sys_svr_user_id1 )
	{
		setValue( ITEM_SYS_SVR_USER_ID, sys_svr_user_id1 );
	}

	/* 授权主题 : String */
	public String getPermit_subject()
	{
		return getValue( ITEM_PERMIT_SUBJECT );
	}

	public void setPermit_subject( String permit_subject1 )
	{
		setValue( ITEM_PERMIT_SUBJECT, permit_subject1 );
	}

	/* 创建人 : String */
	public String getCreate_by()
	{
		return getValue( ITEM_CREATE_BY );
	}

	public void setCreate_by( String create_by1 )
	{
		setValue( ITEM_CREATE_BY, create_by1 );
	}

	/* 创建日期 : String */
	public String getCreate_date()
	{
		return getValue( ITEM_CREATE_DATE );
	}

	public void setCreate_date( String create_date1 )
	{
		setValue( ITEM_CREATE_DATE, create_date1 );
	}

	/* 配置顺序 : String */
	public String getConfig_order()
	{
		return getValue( ITEM_CONFIG_ORDER );
	}

	public void setConfig_order( String config_order1 )
	{
		setValue( ITEM_CONFIG_ORDER, config_order1 );
	}

	/* 是否暂停 : String */
	public String getIs_pause()
	{
		return getValue( ITEM_IS_PAUSE );
	}

	public void setIs_pause( String is_pause1 )
	{
		setValue( ITEM_IS_PAUSE, is_pause1 );
	}

}

