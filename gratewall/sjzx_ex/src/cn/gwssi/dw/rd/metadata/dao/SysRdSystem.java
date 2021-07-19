package cn.gwssi.dw.rd.metadata.dao;

import org.bouncycastle.asn1.ocsp.Request;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[sys_rd_system]的处理类
 * @author Administrator
 *
 */
public class SysRdSystem extends BaseTable
{
	public SysRdSystem()
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
		registerSQLFunction("selectsortsysrdsystem",DaoFunction.SQL_SELECT,"查询最大排序号");
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
	 * 查询最大排序号
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement selectsortsysrdsystem(TxnContext request,DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String sys_rd_data_source_id = inputData.getValue("sys_rd_data_source_id");
		String db_name = inputData.getValue("db_name");
		String db_username = inputData.getValue("db_username");
		stmt.addSqlStmt("select max(sort) as num from sys_rd_system where sys_rd_data_source_id='"+sys_rd_data_source_id+"' and db_name='"+db_name+"' and db_username='"+db_username+"'");
		stmt.setCountStmt("select count(*) from sys_rd_system where sys_rd_data_source_id='"+sys_rd_data_source_id+"' and db_name='"+db_name+"' and db_username='"+db_username+"'");
		return stmt;
	}
	
}

