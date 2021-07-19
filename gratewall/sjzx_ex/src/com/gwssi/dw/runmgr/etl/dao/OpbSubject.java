package com.gwssi.dw.runmgr.etl.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[opb_subject]的处理类
 * @author Administrator
 *
 */
public class OpbSubject extends BaseTable
{
	public OpbSubject()
	{
		
	}
	
	/**
	 * 注册用户自定义的SQL语句
	 */
	protected void register( )
	{
		registerSQLFunction( "loadOpbSubjectList", DaoFunction.SQL_ROWSET, "获取test列表" );
		registerSQLFunction( "getSourceAndTarget", DaoFunction.SQL_ROWSET, "查询对应的SourceAndTarget" );
		registerSQLFunction( "loadWorkflowRunList", DaoFunction.SQL_ROWSET, "根据WorkdlowId查询Workflow运行列表" );
		registerSQLFunction( "loadWorkflowRunLog", DaoFunction.SQL_ROWSET, "查询单条WorkFlow的日志" );
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
	 * 抽取服务管理查询列表DAO配置
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement loadOpbSubjectList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String sql = "select f.subj_id rep_folderid,f.subj_name rep_foldername,wf.WORKFLOW_ID," +
				"wf.WORKFLOW_NAME wf_name,wf.WORKFLOW_COMMENTS wf_ms, wf.domain_name, wf.server_name, wf.dbuser from opb_subject f, " +
				"gw_workflows wf where f.subj_id = wf.SUBJECT_ID";
		String countSql = "select count(1) from opb_subject f, gw_workflows wf " +
				"where f.subj_id = wf.SUBJECT_ID";
		String rep_id = request.getRecord("select-key").getValue("rep_id");
		if (rep_id != null && !rep_id.equals("")){
			sql += " and wf.WORKFLOW_NAME like '"+ rep_id +"_%'";
			countSql += " and wf.WORKFLOW_NAME like '"+ rep_id +"_%'";
		}
		sql += " order by f.subj_name";
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	}
	
	/**
	 * 根据workflowId查询source和target
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement getSourceAndTarget( TxnContext request, DataBus inputData )
	{
		String strWorkflowId = request.getRecord("select-key").getValue("workflow_id");
		String dbuser = request.getRecord("select-key").getValue("dbuser");
		SqlStatement stmt = new SqlStatement( );
		String sql = "select CONNECTION_NAME,IS_TARGET from gw_rel_session_workflow " +
				"where WORKFLOW_ID = '"+strWorkflowId+"' and dbuser='"+dbuser+"'";
		String countSql = "select count(1) from gw_rel_session_workflow " +
		"where WORKFLOW_ID = '"+strWorkflowId+"' and dbuser='"+dbuser+"'";
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	}
	
	public SqlStatement loadWorkflowRunList( TxnContext request, DataBus inputData )
	{
		String strWorkflowId = request.getRecord("select-key").getValue("workflow_id");
		String dbuser = request.getRecord("select-key").getValue("dbuser");
		SqlStatement stmt = new SqlStatement( );
		String sql = "select workflow_id, workflow_run_id, mapping_name, first_error_code," +
				" start_time, end_time from "+dbuser+".gw_log where workflow_id = '"+
				strWorkflowId+"' order by start_time desc";
		String countSql = "select count(1) from "+dbuser+".gw_log where workflow_id = '"+
				strWorkflowId+"'";
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	}
	
	public SqlStatement loadWorkflowRunLog( TxnContext request, DataBus inputData )
	{
		String strWorkflowRunId = request.getRecord("select-key").getValue("workflow_run_id");
		String strMappingName = request.getRecord("select-key").getValue("mapping_name");
		String dbuser = request.getRecord("select-key").getValue("dbuser");
		SqlStatement stmt = new SqlStatement( );
		String sql = "select workflow_id, mapping_name, src_success_rows, " +
				"src_failed_rows, targ_success_rows, targ_failed_rows," +
				"total_trans_errs,first_error_code, first_error_msg,start_time, " +
				"end_time from "+dbuser+".gw_log where workflow_run_id = '"+strWorkflowRunId+"' and " +
				"mapping_name = '" + strMappingName + "'";
		String countSql = "select count(1) from "+dbuser+".gw_log where workflow_run_id = '"+strWorkflowRunId+
				"' and " + "mapping_name = '" + strMappingName + "'";
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	}
	
	
	/**
	 * XXX:用户自定义的SQL语句
	 * 对于多记录的查询语句，需要生成两个语句：查询语句和取记录数量的语句
	 * 对于其它的语句，只需要生成一个语句
	 * @param request 交易的上下文
	 * @param inputData 生成语句的输入节点
	 * @return
	public SqlStatement loadOpbSubjectList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from opb_subject" );
		stmt.setCountStmt( "select count(*) from opb_subject" );
		return stmt;
	}
	 */
	
}

