package com.gwssi.dw.runmgr.etl.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[opb_scheduler]的处理类
 * @author Administrator
 *
 */
public class OpbScheduler extends BaseTable
{
	public OpbScheduler()
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
		// table.executeFunction( "loadOpbSchedulerList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadOpbSchedulerList", DaoFunction.SQL_ROWSET, "获取调度执行任务列表" );
		registerSQLFunction( "getSchedulerByWorkFlowId", DaoFunction.SQL_ROWSET, "根据WorkFlowID查询Scheduler信息" );
		registerSQLFunction( "updateSchedulerInfoByDbuser", DaoFunction.SQL_UPDATE, "根据Dbuser更新Scheduler信息" );
		registerSQLFunction( "getWorkFlowExecInfo", DaoFunction.SQL_ROWSET, "根据WorkFlowID查询workflow执行信息" );
		
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
	 * 根据WorkFlowID查询调度信息
	 * @param request
	 * @param inputData
	 * @return
	 * Comment:从rep_workflows视图取数，查询workflowID为传进来的数，且SchedulerID不为0的数
	 * SchedulerID为0是没有设置调度信息的时候的默认值
	 */
	public SqlStatement getSchedulerByWorkFlowId( TxnContext request, DataBus inputData )
	{
		String workflow_id = request.getRecord("select-key").getValue("workflow_id");
		String dbuser = request.getRecord("select-key").getValue("dbuser");
		SqlStatement stmt = new SqlStatement( );
		String sql = "select t.workflow_id, t.scheduler_id, t.scheduler_name, t.start_time, " +
				"t.run_options, t.run_count, t.end_options, t.end_time, t.delta_value from " +
				"gw_workflows t where t.workflow_id='"+workflow_id+"' and t.scheduler_id != '0'" +
				" and t.dbuser='"+ dbuser +"'";
		String countSql = "select count(1) from gw_workflows t where t.workflow_id='"
				+workflow_id+"' and t.scheduler_id != '0' and t.dbuser='"+ dbuser +"'";
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	}
	
	
	public SqlStatement updateSchedulerInfoByDbuser( TxnContext request, DataBus inputData )
	{
		String scheduler_id = request.getRecord("record").getValue("scheduler_id");
		String dbuser = request.getRecord("record").getValue("dbuser");
		String end_options = request.getRecord("record").getValue("end_options");
		String run_options = request.getRecord("record").getValue("run_options");
		String end_time = request.getRecord("record").getValue("end_time");
		String delta_value = request.getRecord("record").getValue("delta_value");
		String start_time = request.getRecord("record").getValue("start_time");
		
		SqlStatement stmt = new SqlStatement( );
		String sql = "update "+dbuser+".opb_scheduler set ";
		if ( end_options != null && !end_options.equals(""))
		{
			sql += "end_options='" + end_options + "', "; 
		}
		if ( run_options != null && !run_options.equals(""))
		{
			sql += "run_options='"+run_options+"', ";
		}
		if ( end_time != null && !end_time.equals(""))
		{
			sql += "end_time='"+end_time+"', ";
		}
		sql += "delta_value='"+delta_value+"', ";
		sql += "start_time='"+start_time+"' where scheduler_id="+scheduler_id;
		stmt.addSqlStmt( sql );
		return stmt;
	}
	
	public SqlStatement getWorkFlowExecInfo( TxnContext request, DataBus inputData ){
		String workflow_id = request.getRecord("select-key").getValue("workflow_id");
		String dbuser = request.getRecord("select-key").getValue("dbuser");
		SqlStatement stmt = new SqlStatement( );
		String sql = "select t.start_time, t.end_time,t.run_status_code,t.has_failed_tasks," +
				"t.run_err_code,t.run_err_msg from "+ dbuser +".opb_wflow_run t where t.workflow_id = "+ 
				workflow_id + " order by t.workflow_run_id desc";
		String countSql = "select count(1) from "+dbuser+".opb_wflow_run t where t.workflow_id = "+workflow_id;
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	}
	
}

