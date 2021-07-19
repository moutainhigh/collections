package cn.gwssi.search.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_search_svr]的数据对象类
 * @author Administrator
 *
 */
public class VoSysSearchSvrPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201210091825470004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_SEARCH_SVR_ID = "sys_search_svr_id" ;	/* 检索服务ID */
	
	/**
	 * 构造函数
	 */
	public VoSysSearchSvrPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysSearchSvrPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 检索服务ID : String */
	public String getSys_search_svr_id()
	{
		return getValue( ITEM_SYS_SEARCH_SVR_ID );
	}

	public void setSys_search_svr_id( String sys_search_svr_id1 )
	{
		setValue( ITEM_SYS_SEARCH_SVR_ID, sys_search_svr_id1 );
	}

}

