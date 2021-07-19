package com.gwssi.sysmgr.priv.func.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[operrolefun]的处理类
 * @author Administrator
 *
 */
public class Operrolefun extends BaseTable
{
	public Operrolefun()
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
		// table.executeFunction( "loadOperrolefunList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadOperrolefunList", DaoFunction.SQL_ROWSET, "获取角色功能权限列表" );
		registerSQLFunction( "loadRoleTxn", DaoFunction.SQL_ROWSET, "根据角色ID获取交易代码信息" );
		registerSQLFunction( "selectFunc", DaoFunction.SQL_ROWSET, "根据角色ID获取交易代码信息" );	
		registerSQLFunction( "selectOperRoleId", DaoFunction.SQL_ROWSET, "根据条件获取交易代码信息" );
		registerSQLFunction( "deleteOperRoleFun", DaoFunction.SQL_DELETE, "根据条件删除交易代码信息" );	
		registerSQLFunction( "deleteDispByObjIdS", DaoFunction.SQL_DELETE, "根据条件删除数据权限分配信息" );		
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
	public SqlStatement loadOperrolefunList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from operrolefun" );
		stmt.setCountStmt( "select count(*) from operrolefun" );
		return stmt;
	}
	 */
	
	/**
	 * 生成SQL语句
	 */
	private String[] generateRoleTxnSql(TxnContext request) throws TxnException{
		String field = "select A.ROLEACCID,A.ROLEID,A.TXNCODE,B.TXNNAME ";
		String from = " from OPERROLEFUN A LEFT JOIN " +
				"(select max(TXNNAME) AS TXNNAME,TXNCODE  from functxn_new " +
				"GROUP BY TXNCODE) B ON A.TXNCODE = B.TXNCODE";
		String count = "select count(*) ";
		String roleId = request.getRecord("select-key").getValue("roleid");
		String cond = " where a.roleid = " + roleId;
		String orderBy = " order by a.txncode";
		
		return new String[]{
				field + from + cond + orderBy,
				count + from + cond
		};
	}
	
	/**
	 * 根据角色获取角色可访问的交易代码
	 * @param request 交易的上下文
	 * @param inputData 生成语句的输入节点
	 * @return
	 * @throws TxnException 
	 */
	public SqlStatement loadRoleTxn( TxnContext request, DataBus inputData ) throws TxnException
	{
		String[] result = generateRoleTxnSql(request);
		String sql = result[0];
		String countSql = result[1];	
		System.out.println(sql);
		SqlStatement stmt = new SqlStatement( );
		stmt.setPageRows(-1);
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	}
	/**
	 * 查询功能
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement selectFunc(TxnContext request,DataBus inputData){
		
		SqlStatement stmt=new SqlStatement();
		DataBus dataBus=request.getRecord("select-key");
		String funcList=dataBus.getValue("funcList"); 	
		String sql = "select funccode  from functxn_new  " +
		" where funccode not in (" + funcList + ")"  ;
		stmt.addSqlStmt(sql);
		return stmt;
	}
	/**
	 * 获取交易代码
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement selectOperRoleId(TxnContext request,DataBus inputData){
		
		SqlStatement stmt=new SqlStatement();
		DataBus dataBus=request.getRecord("select-key");
		String roleid=dataBus.getValue("roleid"); 	
		String txncode=dataBus.getValue("txncode");
		String sql = "select RoleAccId  from OperRoleFun  " +
		" where roleid =" + roleid + " and TxnCode like '" + txncode + "%'"  ;
		stmt.addSqlStmt(sql);
		return stmt;
	}
	/**
	 * 删除交易代码
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement deleteOperRoleFun(TxnContext request,DataBus inputData){
		
		SqlStatement stmt=new SqlStatement();
		DataBus dataBus=request.getRecord("select-key");
		String roleid=dataBus.getValue("roleid"); 	
		String txncode=dataBus.getValue("txncode");
		String sql = "delete  from OperRoleFun  " +
		" where roleid =" + roleid + " and TxnCode like '" + txncode + "%'"  ;
		stmt.addSqlStmt(sql);
		return stmt;
	}
	/**
	 * 删除权限分配
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement deleteDispByObjIdS(TxnContext request,DataBus inputData){
		
		SqlStatement stmt=new SqlStatement();
		DataBus dataBus=request.getRecord("select-key");
		String objectid=dataBus.getValue("objectid"); 	
		String sql = "delete from dataaccdisp where objectid in (" +
		objectid +" )";
		stmt.addSqlStmt(sql);
		return stmt;
	}		
}

