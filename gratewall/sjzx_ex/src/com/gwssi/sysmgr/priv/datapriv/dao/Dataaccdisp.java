package com.gwssi.sysmgr.priv.datapriv.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[dataaccdisp]的处理类
 * @author Administrator
 *
 */
public class Dataaccdisp extends BaseTable
{
	public Dataaccdisp()
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
		// table.executeFunction( "loadDataaccdispList", context, inputNode, outputNode );
		registerSQLFunction( "loadRoleTxnDataAcc", DaoFunction.SQL_ROWSET, "根据角色访问的交易列表获取数据权限列表" );
		registerSQLFunction( "loadRoleDataAcc", DaoFunction.SQL_ROWSET, "根据角色列表获取数据权限列表" );
		registerSQLFunction( "loadRoleCustomAcc", DaoFunction.SQL_ROWSET, "根据角色访问的交易ID获取自定义权限组ID" );
		registerSQLFunction( "deleteDispByObjIdAndDispObj", DaoFunction.SQL_DELETE, "根据角色功能ID删除分配的数据权限" );
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
	
	/**
	 * XXX:用户自定义的SQL语句
	 * 对于多记录的查询语句，需要生成两个语句：查询语句和取记录数量的语句
	 * 对于其它的语句，只需要生成一个语句
	 * @param request 交易的上下文
	 * @param inputData 生成语句的输入节点
	 * @return
	public SqlStatement loadDataaccdispList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from dataaccdisp" );
		stmt.setCountStmt( "select count(*) from dataaccdisp" );
		return stmt;
	}
	 */
	
	/**
	 * 生成SQL语句
	 */
	private String[] generateRoleTxnDataAccSql(TxnContext request) throws TxnException{
		String roleaccids = request.getRecord("select-key").getValue("roleaccids");
		int roleaccidSize = roleaccids.split(",").length;
		String field = "select distinct a.DATAACCGRPID, a.DATAACCDISPOBJ," +
				"c.DATAACCGRPNAME, c.DATAACCRULE, c.DATAACCTYPE";
		String from = " from DATAACCDISP a left join DATAACCGROUP c " +
				"on a.DATAACCGRPID = c.DATAACCGRPID ";
		String count = "select count(*) ";
		String cond = "where a.DATAACCDISPOBJ = '2' and a.objectid in (" + 
				roleaccids + ") and c.dataacctype = '1' " +
				" and (select count(*) from DATAACCDISP b where b.DATAACCDISPOBJ = '2'" +
				" and a.dataaccgrpid" +
				" = b.dataaccgrpid and a.dataaccdispobj = b.dataaccdispobj" +
				" and b.objectid in(" + roleaccids + ")) = " + roleaccidSize;

		return new String[]{
				field + from + cond,
				count + from + cond
		};
	}
	
	/**
	 * 根据角色访问的交易列表获取数据权限列表
	 * @param request 交易的上下文
	 * @param inputData 生成语句的输入节点
	 * @return
	 * @throws TxnException 
	 */
	public SqlStatement loadRoleTxnDataAcc( TxnContext request, DataBus inputData ) throws TxnException
	{
		String[] result = generateRoleTxnDataAccSql(request);
		String sql = result[0];
		String countSql = result[1];		
		SqlStatement stmt = new SqlStatement( );
		stmt.setPageRows(-1);
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	}

	/**
	 * 生成SQL语句
	 */
	private String[] generateRoleDataAccSql(TxnContext request) throws TxnException{
		String roleids = request.getRecord("select-key").getValue("roleids");
		int roleaccidSize = roleids.split(",").length;
		String field = "select distinct a.DATAACCGRPID, a.DATAACCDISPOBJ," +
				"c.DATAACCGRPNAME, c.DATAACCRULE, c.DATAACCTYPE";
		String from = " from DATAACCDISP a left join DATAACCGROUP c " +
				"on a.DATAACCGRPID = c.DATAACCGRPID ";
		String count = "select count(*) ";
		String cond = "where a.DATAACCDISPOBJ = '1' and a.objectid in (" + 
				roleids + ") and c.dataacctype = '1' " +
				" and (select count(*) from DATAACCDISP b where b.DATAACCDISPOBJ = '1'" +
				" and a.dataaccgrpid" +
				" = b.dataaccgrpid and a.dataaccdispobj = b.dataaccdispobj" +
				" and b.objectid in(" + roleids + ")) = " + roleaccidSize;

		return new String[]{
				field + from + cond,
				count + from + cond
		};
	}
	
	/**
	 * 根据角色访问的交易列表获取数据权限列表
	 * @param request 交易的上下文
	 * @param inputData 生成语句的输入节点
	 * @return
	 * @throws TxnException 
	 */
	public SqlStatement loadRoleDataAcc( TxnContext request, DataBus inputData ) throws TxnException
	{
		String[] result = generateRoleDataAccSql(request);
		String sql = result[0];
		String countSql = result[1];		
		SqlStatement stmt = new SqlStatement( );
		stmt.setPageRows(-1);
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	}

	
	/**
	 * 生成SQL语句
	 */
	private String generateDeleteSql(TxnContext request) throws TxnException{
		String inputNode = "record";
		String sql = "delete from dataaccdisp where objectid = " +
		request.getRecord(inputNode).getValue("objectid") +
		" and dataaccdispobj = '" + request.getRecord("record").getValue("dataaccdispobj")
		+ "'";
		
		return sql;
	}
	
	/**
	 * 根据所有条件删除数据权限项
	 * @param request 交易的上下文
	 * @param inputData 生成语句的输入节点
	 * @return
	 * @throws TxnException 
	 */
	public SqlStatement deleteDispByObjIdAndDispObj( TxnContext request, DataBus inputData ) throws TxnException
	{
		String result = generateDeleteSql(request);
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( result );
		return stmt;
	}

	/**
	 * 生成SQL语句
	 */
	private String[] generateRoleCustomAccSql(TxnContext request) throws TxnException{
		String roleaccids = request.getRecord("select-key").getValue("objectids");
		String dataaccdispobj = request.getRecord("select-key").getValue("dataaccdispobj");
		String field = "select a.OBJECTID, a.DATAACCGRPID, a.DATAACCDISPOBJ," +
				"c.DATAACCGRPNAME, c.DATAACCRULE, c.DATAACCTYPE";
		String from = " from DATAACCDISP a inner join DATAACCGROUP c " +
				"on a.DATAACCGRPID = c.DATAACCGRPID ";
		String count = "select count(*) ";
		String cond = "where a.DATAACCDISPOBJ = '" + dataaccdispobj + "' and a.objectid in (" + 
						roleaccids + ") and c.dataacctype = '0'";

		return new String[]{
				field + from + cond,
				count + from + cond
		};
	}
	
	/**
	 * 根据角色访问的交易ID获取自定义权限组ID
	 * @param request 交易的上下文
	 * @param inputData 生成语句的输入节点
	 * @return
	 * @throws TxnException 
	 */
	public SqlStatement loadRoleCustomAcc( TxnContext request, DataBus inputData ) throws TxnException
	{
		String[] result = generateRoleCustomAccSql(request);
		String sql = result[0];
		String countSql = result[1];		
		SqlStatement stmt = new SqlStatement( );
		stmt.setPageRows(-1);
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	}
}

