package com.gwssi.sysmgr.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[sys_popwindow]的处理类
 * @author Administrator
 *
 */
public class SysPopwindow extends BaseTable
{
	public SysPopwindow()
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
		// table.executeFunction( "loadSysPopwindowList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadSysPopwindowList", DaoFunction.SQL_ROWSET, "获取弹窗设置列表" );
		registerSQLFunction( "getPopWindowCountByRoles", DaoFunction.SQL_ROWSET, "根据角色列表获取弹窗数量" );
		registerSQLFunction( "getPopWindowContentByRoles", DaoFunction.SQL_ROWSET, "根据角色列表获取弹窗信息" );
		registerSQLFunction( "getTzList", DaoFunction.SQL_ROWSET, "获取弹窗列表" );
		
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
	
	
	public SqlStatement getPopWindowCountByRoles( TxnContext request, DataBus inputData )
	{
		String roleList = request.getRecord("oper-data").getValue("role-list");
		if(roleList.endsWith(";")){
			roleList = roleList.substring(0, roleList.length() - 1);
		}
		String[] roles = roleList.split(";");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select count(*) as countNumber from sys_popwindow where ");
		String today = com.gwssi.common.util.DateUtil.getToday();
		sqlBuffer.append("expire_date >= '" + today + "' and (");
		for(int i = 0 ; i < roles.length; i++){
			if(i != 0){
				sqlBuffer.append(" or ");
			}
			sqlBuffer.append("roles like '%" + roles[i] + "%'");
		}
		sqlBuffer.append(")");
		
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( sqlBuffer.toString() );
//		stmt.setCountStmt( "select count(*) from sys_popwindow" );
		return stmt;
	}
	
	public SqlStatement getPopWindowContentByRoles( TxnContext request, DataBus inputData )
	{
		String roleList = request.getRecord("oper-data").getValue("role-list");
		if(roleList.endsWith(";")){
			roleList = roleList.substring(0, roleList.length() - 1);
		}
		String[] roles = roleList.split(";");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from sys_popwindow where ");
		String today = com.gwssi.common.util.DateUtil.getToday();
		
		//sqlBuffer.append("expire_date >= '" + today + "' and (");
		sqlBuffer.append("1=1 and (");
		for(int i = 0 ; i < roles.length; i++){
			if(i != 0){
				sqlBuffer.append(" or ");
			}
			sqlBuffer.append("roles like '%" + roles[i] + "%'");
		}
		sqlBuffer.append(")");
		
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( sqlBuffer.toString() + " order by publish_date desc" );
		System.out.println(sqlBuffer.toString());
		// stmt.setCountStmt( "select count(*) from " );
		return stmt;
	}
	
	 public SqlStatement getTzList( TxnContext request, DataBus inputData )
	   {   		   
	         SqlStatement stmt=new SqlStatement();	
	         String sql = "Select * from (select * from sys_popwindow  order by publish_date desc) Where Rownum<11" ;
		     stmt.addSqlStmt(sql);
		     return stmt;
	   } 
	
	/**
	 * XXX:用户自定义的SQL语句
	 * 对于多记录的查询语句，需要生成两个语句：查询语句和取记录数量的语句
	 * 对于其它的语句，只需要生成一个语句
	 * @param request 交易的上下文
	 * @param inputData 生成语句的输入节点
	 * @return
	public SqlStatement loadSysPopwindowList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from sys_popwindow" );
		stmt.setCountStmt( "select count(*) from sys_popwindow" );
		return stmt;
	}
	 */
	
}

