package com.gwssi.dw.runmgr.etl.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[sys_etl_wf]的处理类
 * @author Administrator
 *
 */
public class SysEtlWf extends BaseTable
{
	public SysEtlWf()
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
		// table.executeFunction( "loadSysEtlWfList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadSysEtlWfList", DaoFunction.SQL_ROWSET, "获取抽取服务管理列表" );
		registerSQLFunction("queryEtlWf", DaoFunction.SQL_ROWSET, "查询抽取服务和etl项目的关联表" );
		registerSQLFunction("querySingleEtlWf", DaoFunction.SQL_SELECT, "查询一条抽取服务和etl项目的关联表" );
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
	 * 查询抽取服务和etl项目的关联表
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryEtlWf( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		DataBus dataBus = request.getRecord("select-key");
		String rep_id = dataBus.getValue("rep_id");
		StringBuffer buffSql = new StringBuffer("select t1.sys_etl_wf_id as sys_etl_wf_id,t1.rep_id as rep_id,t1.rep_foldername as rep_foldername,t1.wf_name as wf_name,t1.wf_ms as wf_ms," +
				"t2.etl_hostname as etl_hostname,t2.etl_portno as etl_portno,t2.etl_domainname as etl_domainname,t2.rep_name_en as rep_name_en,t2.rep_name_cn as rep_name_cn,t2.user_id as user_id,t2.user_password as user_password " +
				"from sys_etl_wf t1,sys_etl_rep t2 where t1.rep_id=t2.sys_etl_rep_id");
		StringBuffer buffSqlCount = new StringBuffer("select count(1) from sys_etl_wf t1,sys_etl_rep t2 where t1.rep_id=t2.sys_etl_rep_id");
		if(rep_id!=null&&!"".equals(rep_id)){
			buffSql.append(" and t1.rep_id='").append(rep_id).append("'");
			buffSqlCount.append(" and t1.rep_id='").append(rep_id).append("'");
		}
		stmt.addSqlStmt(buffSql.toString());
		stmt.setCountStmt(buffSqlCount.toString());
		return stmt;
	}
	/**
	 * 查询一条抽取服务和etl项目的关联表
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement querySingleEtlWf(TxnContext request, DataBus inputData ){
		SqlStatement stmt = new SqlStatement( );
		DataBus dataBus = request.getRecord("record");
		String rep_id = dataBus.getValue("rep_id");
		String wf_id = dataBus.getValue("sys_etl_wf_id");
		StringBuffer buffSql = new StringBuffer("select t1.sys_etl_wf_id as sys_etl_wf_id,t1.rep_id as rep_id,t1.rep_foldername as rep_foldername,t1.wf_name as wf_name,t1.wf_ms as wf_ms," +
				"t2.etl_hostname as etl_hostname,t2.etl_portno as etl_portno,t2.etl_domainname as etl_domainname,t2.rep_name_en as rep_name_en,t2.rep_name_cn as rep_name_cn,t2.user_id as user_id,t2.user_password as user_password " +
				"from sys_etl_wf t1,sys_etl_rep t2 where t1.rep_id=t2.sys_etl_rep_id");
		if(rep_id!=null&&!"".equals(rep_id)){
			buffSql.append(" and t1.rep_id='").append(rep_id).append("'");
		}
		if(wf_id!=null&&!"".equals(wf_id)){
			buffSql.append(" and t1.sys_etl_wf_id='").append(wf_id).append("'");
		}		
		stmt.addSqlStmt(buffSql.toString());
		return stmt;	
	}
	/**
	 * XXX:用户自定义的SQL语句
	 * 对于多记录的查询语句，需要生成两个语句：查询语句和取记录数量的语句
	 * 对于其它的语句，只需要生成一个语句
	 * @param request 交易的上下文
	 * @param inputData 生成语句的输入节点
	 * @return
	public SqlStatement loadSysEtlWfList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from sys_etl_wf" );
		stmt.setCountStmt( "select count(*) from sys_etl_wf" );
		return stmt;
	}
	 */
	
}

