package com.gwssi.dw.aic.download.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[download_check_purv]的处理类
 * @author Administrator
 *
 */
public class DownloadCheckPurv extends BaseTable
{
	public DownloadCheckPurv()
	{
		
	}
	
	/**
	 * 注册用户自定义的SQL语句
	 */
	protected void register( )
	{
		// 以下是注册用户自定义函数的过程
		// 包括三个参数：SQL语句的名称，类型，描述
		// 业务类可以通过以下函数调用:
		registerSQLFunction( "getDownloadCheckPurvInfo", DaoFunction.SQL_ROWSET, "根据roleid来获取审批权限列表" );
	}
	
	/**
	 * 执行SQL语句前的处理
	 */
	public void prepareExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{
		
	}
	
	/**
	 * 执行完SQL语句后的处理
	 */
	public void afterExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{
		
	}
	
	public SqlStatement getDownloadCheckPurvInfo( TxnContext request, DataBus inputData )
	{
		String roleList = request.getRecord("roleInfo").getValue("role-list");
		SqlStatement stmt = new SqlStatement( );
		String sql = "select t.*, p.agency_id from download_check_purv t,download_purv p where " +
				"t.download_purv_id = p.download_purv_id and t.roleid in (" + roleList + ")";
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( "select count(*) from (" + sql + ")" );
		return stmt;
	}
}

