package com.gwssi.dw.runmgr.services.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[sys_svr_config]�Ĵ�����
 * @author Administrator
 *
 */
public class SysSvrConfig extends BaseTable
{
	public SysSvrConfig()
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
		// table.executeFunction( "loadSysSvrConfigList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadSysSvrConfigList", DaoFunction.SQL_ROWSET, "��ȡ������������б�" );
//		registerSQLFunction("queryUserServices", DaoFunction.SQL_ROWSET, "��ѯ�����������й������" );
//		registerSQLFunction("queryServiceById", DaoFunction.SQL_ROWSET, "���ݷ�������ѯ�������" );
//		registerSQLFunction("queryColumnName", DaoFunction.SQL_ROWSET, "�����ֶα����ѯ�ֶ�����" );
//		registerSQLFunction("queryAllColumns", DaoFunction.SQL_ROWSET, "�����ֶα����ѯ�ֶ�����" );
//		registerSQLFunction( "getUserServices", DaoFunction.SQL_ROWSET, "��ѯ�û��ķ���" );
//		registerSQLFunction( "query_sql", DaoFunction.SQL_ROWSET, "��ѯ�����õ�SQL�������webservice" );
		registerSQLFunction( "queryUserServiceList", DaoFunction.SQL_ROWSET, "��ѯĳ�û��������ܵķ����б�" );
		registerSQLFunction( "deleteSysConfig", DaoFunction.SQL_DELETE, "ɾ�������������" );
		registerSQLFunction( "queryList", DaoFunction.SQL_ROWSET, "��ѯ���������б�" );
		registerSQLFunction( "querySvrSysParams", DaoFunction.SQL_ROWSET, "��ѯ����ϵͳ���������б�" );
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
	
	public SqlStatement queryList(TxnContext request, DataBus inputData){
		SqlStatement stmt = new SqlStatement( );
		DataBus dataBus = request.getRecord("select-key");
		String login_name = dataBus.getValue("login_name");
		String uId = dataBus.getValue("sys_svr_user_id");
		String svr_code = dataBus.getValue("svr_code");
		String svrId = dataBus.getValue("sys_svr_service_id");
		String state = dataBus.getValue("state");
		String create_date_from = dataBus.getValue("create_date_from");
		String create_date_to = dataBus.getValue("create_date_to");
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT conf.sys_svr_config_id, conf.sys_svr_service_id,  conf.sys_svr_user_id, svr.svr_code, svr.svr_name, u.user_name, u.login_name, u.state, conf.create_by, conf.create_date FROM sys_svr_config conf, sys_svr_service svr, sys_svr_user u WHERE conf.sys_svr_service_id = svr.sys_svr_service_id AND conf.sys_svr_user_id = u.sys_svr_user_id ");
		if(login_name != null && !login_name.trim().equals("")){
			sql.append(" AND u.login_name like '%").append(login_name).append("%' ");
		}
		if(uId != null && !uId.trim().equals("")){
			sql.append(" AND conf.sys_svr_user_id='").append(uId).append("' ");
		}
		if(svrId != null && !svrId.trim().equals("")){
			sql.append(" AND conf.sys_svr_service_id='").append(svrId).append("' ");
		}
		if(svr_code != null && !svr_code.trim().equals("")){
			sql.append(" AND svr.svr_code like '%").append(svr_code).append("%' ");
		}
		if(state != null && !state.trim().equals("")){
			sql.append(" AND u.state='").append(state).append("' ");
		}
		if(create_date_from != null && !create_date_from.trim().equals("")){
			sql.append(" AND conf.create_date>='").append(create_date_from).append("' ");
		}
		if(create_date_to != null && !create_date_to.trim().equals("")){
			sql.append(" AND conf.create_date<='").append(create_date_to).append("' ");
		}
		sql.append(" ORDER BY config_order DESC ");
		System.out.println(sql);
		stmt.addSqlStmt( sql.toString() );
		String countSql = "select count(1) from (" + sql + ") t";
		stmt.setCountStmt(countSql);
		return stmt;
	}
	
	public SqlStatement queryUserServiceList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		DataBus dataBus = request.getRecord("select-key");
		String sys_svr_user_id = dataBus.getValue("sys_svr_user_id");
		String sql =  "SELECT s.sys_svr_service_id, s.svr_name, s.table_no,s.column_no, s.svr_code, s.query_sql, s.shared_columns_en_array, s.shared_columns_cn_array FROM sys_svr_service s WHERE s.sys_svr_service_id NOT IN (SELECT c.sys_svr_service_id FROM sys_svr_config c WHERE c.sys_svr_user_id='"+sys_svr_user_id+"')";
		stmt.addSqlStmt(sql);
		stmt.setCountStmt( "select count(1) from ("+sql+")" );
		return stmt;
	}
	
	public SqlStatement querySvrSysParams( TxnContext request, DataBus inputData ){
		SqlStatement stmt = new SqlStatement( );
		DataBus dataBus = request.getRecord("select-key");
		String svrId = dataBus.getValue("sys_svr_service_id");
		StringBuffer sb1 = new StringBuffer(" select t.operator1||' '||t.left_table_name||'.'||t.left_column_name||' '||t.operator2||t.param_value as param from sys_svr_config_param t where 1=1 ");
		sb1.append(" and t.sys_svr_config_id='")
			.append(svrId)
			.append("'  order by t.param_order");
		stmt.addSqlStmt(sb1.toString());
		stmt.setCountStmt( "select count(1) from ("+sb1.toString()+")" );
		return stmt;
	}
	
	
	public SqlStatement deleteSysConfig( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		DataBus dataBus = request.getRecord("select-key");
		String cfgId = dataBus.getValue("sys_svr_config_id");
		String sql =  "DELETE FROM sys_svr_config WHERE sys_svr_config_id IN ("+cfgId+")";
		stmt.addSqlStmt(sql);
//		stmt.setCountStmt( "select count(1) from ("+sql+")" );
		return stmt;
	}
	
//	public SqlStatement queryUserServices( TxnContext request, DataBus inputData )
//	{
//		SqlStatement stmt = new SqlStatement( );
//		DataBus dataBus = request.getRecord("select-key");
//		String sys_svr_user_id = dataBus.getValue("sys_svr_user_id");
//		stmt.addSqlStmt( "select sys_svr_service_id from sys_svr_config where sys_svr_user_id='"+sys_svr_user_id+"'" );
//		stmt.setCountStmt( "select count(sys_svr_service_id) from sys_svr_config where sys_svr_user_id='"+sys_svr_user_id+"'" );
//		return stmt;
//	}
//	
//	public SqlStatement queryServiceById( TxnContext request, DataBus inputData )
//	{
//		SqlStatement stmt = new SqlStatement( );
//		DataBus dataBus = request.getRecord("select-key");
//		String sys_svr_service_id = dataBus.getValue("sys_svr_service_id");
//		stmt.addSqlStmt( "select sys_svr_service_id, name, service_type, table_no, column_no from sys_svr_service where sys_svr_service_id='"+sys_svr_service_id+"'" );
//		stmt.setCountStmt( "select count(sys_svr_service_id) from sys_svr_service where sys_svr_service_id='"+sys_svr_service_id+"'" );
//		return stmt;
//	}
//	
//	public SqlStatement queryColumnName(TxnContext request, DataBus inputData){
//		SqlStatement stmt = new SqlStatement( );
//		DataBus dataBus = request.getRecord("select-key");
//		//String sys_svr_user_id = dataBus.getValue("sys_svr_user_id");
//		String column_no = dataBus.getValue("column_no");
//		stmt.addSqlStmt( "select column_no, column_name, column_name_cn, table_no from sys_column_semantic where column_no='"+column_no+"'" );
//		stmt.setCountStmt( "select count(column_name_cn) from sys_column_semantic where column_no='"+column_no+"'" );
//		return stmt;
//	}
//	
//	public SqlStatement queryAllColumns(TxnContext request, DataBus inputData){
//		SqlStatement stmt = new SqlStatement( );
//		DataBus dataBus = request.getRecord("new-service");
//		String table_no = dataBus.getValue("table_no");
//		stmt.addSqlStmt( "select column_no, column_name_cn from sys_column_semantic where table_no='"+table_no+"'" );
//		stmt.setCountStmt( "select count(column_name_cn) from sys_column_semantic where table_no='"+table_no+"'" );
//		return stmt;
//	}
//	
//	/**
//	 * �����û������ѯ�����õķ����б�
//	 * @param request
//	 * @param inputData
//	 * @return
//	 */
//	public SqlStatement getUserServices( TxnContext request, DataBus inputData )
//	{
//		SqlStatement stmt = new SqlStatement( );
//		
//		DataBus dataBus = request.getRecord("select-key");
//		String serviceId = dataBus.getValue("sys_svr_service_id");
//		String svr_code = dataBus.getValue("svr_code");
//		String name = dataBus.getValue("name");
//		String create_by = dataBus.getValue("create_by");
//		String create_date = dataBus.getValue("create_date");
//		
//		//��ʽ�����ڲ�ѯ����
//		if ((create_date != null) && (create_date.trim().length() > 0)){
//			try{
//				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//				Date d = format.parse(create_date);
//				format.applyPattern("yyyy-MM-dd");
//				create_date = format.format(d);
//			}catch(ParseException pe){
//				pe.printStackTrace();
//			}
//		}
//		
//		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT sys_svr_service_id, name, create_date, create_by, service_type, svr_code, service_desc FROM sys_svr_service WHERE sys_svr_service_id='")
//		.append(serviceId).append("'");
//		if(svr_code != null && svr_code.trim().length() > 0){
//			sql.append(" AND svr_code LIKE '%").append(svr_code).append("%'");
//		}else if (name != null && name.trim().length() > 0){
//			sql.append(" AND name LIKE '%").append(name).append("%'");
//		}else if (create_by != null && create_by.trim().length() > 0){
//			sql.append(" AND create_by LIKE '%").append(create_by).append("%'");
//		}else if ((create_date != null) && (create_date.trim().length() > 0)){
//			sql.append(" AND create_date='").append(create_date).append("'");
//		}
//		stmt.addSqlStmt(sql.toString());
//		
//		sql = new StringBuffer();
//		sql.append("SELECT COUNT(sys_svr_service_id) FROM sys_svr_service WHERE sys_svr_service_id='").append(serviceId).append("'");
//		if(svr_code != null && svr_code.trim().length() > 0){
//			sql.append(" AND svr_code LIKE '%").append(svr_code).append("%'");
//		}else if (name != null && name.trim().length() > 0){
//			sql.append(" AND name LIKE '%").append(name).append("%'");
//		}else if (create_by != null && create_by.trim().length() > 0){
//			sql.append(" AND create_by LIKE '%").append(create_by).append("%'");
//		}else if (create_date != null && create_date.trim().length() > 0){
//			sql.append(" AND create_date='").append(create_date).append("'");
//		}
//		
//		stmt.setCountStmt(sql.toString());
//		return stmt;
//	}
//	
//	public SqlStatement query_sql(TxnContext request, DataBus inputData){
//		SqlStatement stmt = new SqlStatement( );
//		DataBus dataBus = request.getRecord("record");
//		String id = dataBus.getValue("sys_svr_config_id");
//		stmt.addSqlStmt( "select sys_svr_config_id, query_sql from sys_svr_config where sys_svr_config_id='"+id+"'" );
//		stmt.setCountStmt( "select count(sys_svr_config_id) from sys_svr_config where sys_svr_config_id='"+id+"'" );
//		return stmt;
//	}
	
	/**
	 * XXX:�û��Զ����SQL���
	 * ���ڶ��¼�Ĳ�ѯ��䣬��Ҫ����������䣺��ѯ����ȡ��¼���������
	 * ������������䣬ֻ��Ҫ����һ�����
	 * @param request ���׵�������
	 * @param inputData ������������ڵ�
	 * @return
	public SqlStatement loadSysSvrConfigList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from sys_svr_config" );
		stmt.setCountStmt( "select count(*) from sys_svr_config" );
		return stmt;
	}
	 */
	
}

