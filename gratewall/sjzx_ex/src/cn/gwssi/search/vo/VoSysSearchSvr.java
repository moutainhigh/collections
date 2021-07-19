package cn.gwssi.search.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_search_svr]的数据对象类
 * @author Administrator
 *
 */
public class VoSysSearchSvr extends VoBase
{
	private static final long serialVersionUID = 201210091825470002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_SEARCH_SVR_ID = "sys_search_svr_id" ;	/* 检索服务ID */
	public static final String ITEM_SVR_NAME = "svr_name" ;			/* 服务名称 */
	public static final String ITEM_SVR_DB = "svr_db" ;				/* 检索数据库 */
	public static final String ITEM_SVR_QUERY = "svr_query" ;		/* 检索查询串 */
	public static final String ITEM_SVR_TEMPLATE = "svr_template" ;	/* 检索服务模板 */
	
	/**
	 * 构造函数
	 */
	public VoSysSearchSvr()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysSearchSvr(DataBus value)
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

	/* 服务名称 : String */
	public String getSvr_name()
	{
		return getValue( ITEM_SVR_NAME );
	}

	public void setSvr_name( String svr_name1 )
	{
		setValue( ITEM_SVR_NAME, svr_name1 );
	}

	/* 检索数据库 : String */
	public String getSvr_db()
	{
		return getValue( ITEM_SVR_DB );
	}

	public void setSvr_db( String svr_db1 )
	{
		setValue( ITEM_SVR_DB, svr_db1 );
	}

	/* 检索查询串 : String */
	public String getSvr_query()
	{
		return getValue( ITEM_SVR_QUERY );
	}

	public void setSvr_query( String svr_query1 )
	{
		setValue( ITEM_SVR_QUERY, svr_query1 );
	}

	/* 检索服务模板 : String */
	public String getSvr_template()
	{
		return getValue( ITEM_SVR_TEMPLATE );
	}

	public void setSvr_template( String svr_template1 )
	{
		setValue( ITEM_SVR_TEMPLATE, svr_template1 );
	}

}

