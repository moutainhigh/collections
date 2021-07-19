package com.gwssi.dw.runmgr.services.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[sys_svr_log]�Ĵ�����
 * @author Administrator
 *
 */
public class SysSvrLog extends BaseTable
{
	public SysSvrLog()
	{
		
	}
	
	/**
	 * ע���û��Զ����SQL���
	 */
	protected void register( )
	{
		registerSQLFunction( "queryLogList", DaoFunction.SQL_ROWSET, "��ѯ������־�б�" );
		registerSQLFunction( "queryAllUsers", DaoFunction.SQL_ROWSET, "��ѯ�����û����ڷ�����־ͳ��" );
		registerSQLFunction( "queryLogTongji", DaoFunction.SQL_ROWSET, "��ѯ������־" );
		registerSQLFunction( "queryChangeTongji", DaoFunction.SQL_ROWSET, "��ѯ����ͳ��" );
	}
	
	public SqlStatement queryLogList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		DataBus dataBus = request.getRecord("select-key");
		String sys_svr_user_id = dataBus.getValue("sys_svr_user_id");
		String sys_svr_service_id = dataBus.getValue("sys_svr_service_id");
		String startTime = dataBus.getValue("execute_start_time");
		String endTime = dataBus.getValue("execute_end_time");
		String state = dataBus.getValue("state");
		
		StringBuffer sql = new StringBuffer( "SELECT sys_svr_log_id,sys_svr_user_id,sys_svr_user_name,sys_svr_service_id,sys_svr_service_name,execute_start_time,execute_end_time,state,records_mount,error_msg,client_ip FROM sys_svr_log WHERE 1=1 ");
		if(sys_svr_user_id != null && !sys_svr_user_id.trim().equals("")){
			sql.append(" AND sys_svr_user_id='").append(sys_svr_user_id).append("' ");
		}
		if(sys_svr_service_id != null && !sys_svr_service_id.trim().equals("")){
			sql.append(" AND sys_svr_service_id='").append(sys_svr_service_id).append("' ");
		}
		if(startTime != null && !startTime.trim().equals("")){
			sql.append(" AND execute_start_time >= '").append(startTime).append(" 00:00:00' ");
		}
		if(endTime != null && !endTime.trim().equals("")){
			sql.append(" AND execute_end_time <='").append(endTime).append(" 23:59:59' ");
		}
		if(state != null && !state.trim().equals("")){
			sql.append(" AND state='").append(state).append("' ");
		}
		sql.append(" ORDER BY execute_start_time DESC");
		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt( "select count(1) from ("+sql+")" );
		return stmt;
	}

	/**
	 * ��ѯ���з������
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryAllUsers( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		StringBuffer sql = new StringBuffer();
		sql.append("select u.login_name from sys_svr_user u ")
		   .append(" union all ")
           .append("select u.login_name from sys_db_user u");
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}
	
	public SqlStatement queryLogTongji( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		DataBus dataBus = request.getRecord("select-key");
		String user_id = dataBus.getValue("user_id");
		String sys_svr_service_id = dataBus.getValue("sys_svr_service_id");
		String startTime = dataBus.getValue("execute_start_time");
		String endTime = dataBus.getValue("execute_end_time");
		String state = dataBus.getValue("state");
		String execute_type = dataBus.getValue("execute_type");

		StringBuffer sql = new StringBuffer();
		sql.append("select sys_svr_user_name,sys_svr_service_name,execute_start_time,execute_end_time,execute_type,state,error_msg,client_ip, records_mount ") 
		    .append(" from VIEW_SYS_SVR_LOG ") 
		    .append(" where 1=1 ");
		if(!user_id.trim().equals("") && !user_id.trim().equals("ȫ��")){
			sql.append(" and sys_svr_user_name='").append(user_id).append("'");
		}
		if(!sys_svr_service_id.trim().equals("") && !sys_svr_service_id.trim().equals("ȫ��")){
			sql.append(" and sys_svr_service_name='").append(sys_svr_service_id).append("'");
		}
		if(!startTime.trim().equals("")){
			sql.append(" and execute_start_time >='").append(startTime).append("'");
		}
		if(!endTime.trim().equals("")){
			sql.append(" and execute_end_time <='").append(endTime).append("'");
		}
		if(!state.trim().equals("")){
			sql.append(" and state='").append(state).append("'");
		}
		if(!execute_type.trim().equals("")){
			sql.append(" and execute_type='").append(execute_type).append("'");
		}
		stmt.addSqlStmt(sql.toString());
		System.out.println(sql.toString());
		stmt.setCountStmt( "select count(1) from ("+sql+")" );
		System.out.println("count-sql"+stmt.getCountStmt());
		return stmt;
	}
	
	public SqlStatement queryChangeTongji( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		DataBus dataBus = request.getRecord("select-key");
		String user_id = dataBus.getValue("user_id");
		String sys_svr_service_id = dataBus.getValue("sys_svr_service_id");
		String startTime = dataBus.getValue("execute_start_time");
		String endTime = dataBus.getValue("execute_end_time");
		String execute_type = dataBus.getValue("execute_type");

		StringBuffer sql = new StringBuffer();
		sql.append("select sys_svr_user_name,sys_svr_service_name,sum(records_mount) as records_mount,count(*) as query_times,execute_type  ") 
		    .append(" from VIEW_SYS_SVR_LOG ") 
		    .append(" where 1=1 ");
		if(!user_id.trim().equals("") && !user_id.trim().equals("ȫ��")){
			sql.append(" and sys_svr_user_name='").append(user_id).append("'");
		}
		if(!sys_svr_service_id.trim().equals("") && !sys_svr_service_id.trim().equals("ȫ��")){
			sql.append(" and sys_svr_service_name='").append(sys_svr_service_id).append("'");
		}
		if(!startTime.trim().equals("")){
			sql.append(" and execute_start_time >='").append(startTime).append("'");
		}
		if(!endTime.trim().equals("")){
			sql.append(" and execute_end_time <='").append(endTime).append("'");
		}
		if(!execute_type.trim().equals("")){
			sql.append(" and execute_type='").append(execute_type).append("'");
		}
		sql.append(" group by sys_svr_user_name,sys_svr_service_name,execute_type having sum(records_mount) is not null");
		System.out.println(sql.toString());
		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt( "select count(1) from ("+sql+")" );
		System.out.println("count-sql"+stmt.getCountStmt());
		return stmt;
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
	 * XXX:�û��Զ����SQL���
	 * ���ڶ��¼�Ĳ�ѯ��䣬��Ҫ����������䣺��ѯ����ȡ��¼���������
	 * ������������䣬ֻ��Ҫ����һ�����
	 * @param request ���׵�������
	 * @param inputData ������������ڵ�
	 * @return
	public SqlStatement loadSysSvrLogList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from sys_svr_log" );
		stmt.setCountStmt( "select count(*) from sys_svr_log" );
		return stmt;
	}
	 */
	
}

