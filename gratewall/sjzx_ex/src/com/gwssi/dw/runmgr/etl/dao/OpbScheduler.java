package com.gwssi.dw.runmgr.etl.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[opb_scheduler]�Ĵ�����
 * @author Administrator
 *
 */
public class OpbScheduler extends BaseTable
{
	public OpbScheduler()
	{
		
	}
	
	/**
	 * ע���û��Զ����SQL���
	 */
	protected void register( )
	{
		// ������ע���û��Զ��庯���Ĺ���
		// ��������������SQL�������ƣ����ͣ�����
		// ҵ�������ͨ�����º�������:
		// table.executeFunction( "loadOpbSchedulerList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadOpbSchedulerList", DaoFunction.SQL_ROWSET, "��ȡ����ִ�������б�" );
		registerSQLFunction( "getSchedulerByWorkFlowId", DaoFunction.SQL_ROWSET, "����WorkFlowID��ѯScheduler��Ϣ" );
		registerSQLFunction( "updateSchedulerInfoByDbuser", DaoFunction.SQL_UPDATE, "����Dbuser����Scheduler��Ϣ" );
		registerSQLFunction( "getWorkFlowExecInfo", DaoFunction.SQL_ROWSET, "����WorkFlowID��ѯworkflowִ����Ϣ" );
		
	}
	
	/**
	 * ִ��SQL���ǰ�Ĵ���
	 */
	public void prepareExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{
		
	}
	
	/**
	 * ִ����SQL����Ĵ���
	 */
	public void afterExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{
		
	}
	
	/**
	 * ����WorkFlowID��ѯ������Ϣ
	 * @param request
	 * @param inputData
	 * @return
	 * Comment:��rep_workflows��ͼȡ������ѯworkflowIDΪ��������������SchedulerID��Ϊ0����
	 * SchedulerIDΪ0��û�����õ�����Ϣ��ʱ���Ĭ��ֵ
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

