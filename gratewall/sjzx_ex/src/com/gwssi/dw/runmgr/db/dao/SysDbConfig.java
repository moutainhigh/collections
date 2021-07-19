package com.gwssi.dw.runmgr.db.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class SysDbConfig extends BaseTable
{
   public SysDbConfig()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
	{
		registerSQLFunction("getDBConfigList", DaoFunction.SQL_ROWSET,
		        "查询所有配置列表");
		registerSQLFunction("getUserConfigList", DaoFunction.SQL_ROWSET,
        		"查询用户视图配置列表");
		/*
		registerSQLFunction("queryUserServices", DaoFunction.SQL_ROWSET,
				"查询服务对象的所有共享服务");
		registerSQLFunction("queryServiceById", DaoFunction.SQL_ROWSET,
				"根据服务编码查询共享服务");
		registerSQLFunction("queryColumnName", DaoFunction.SQL_ROWSET,
				"根据字段编码查询字段名称");
		registerSQLFunction("queryAllColumns", DaoFunction.SQL_ROWSET,
				"根据字段编码查询字段名称");
		
		registerSQLFunction("getUserServices", DaoFunction.SQL_ROWSET,
				"查询用户的服务");
		*/
		registerSQLFunction("selectViewNotOfUser", DaoFunction.SQL_ROWSET,
				"查询用户没有配置的服务");
		registerSQLFunction("getViewAuditLog", DaoFunction.SQL_ROWSET,
		        "查询视图日志");
		registerSQLFunction("queryTablesOfTableIds", DaoFunction.SQL_ROWSET,
                "查询已授权的表");
		registerSQLFunction("updateUserTables", DaoFunction.SQL_UPDATE,
                "更新用户已授权的表");
	}

	public SqlStatement getDBConfigList(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("select-key");
		//String login_name = dataBus.getValue("login_name");
		String config_name = dataBus.getValue("config_name");
		String sys_db_user_id = dataBus.getValue("sys_db_user_id");
		//String view_code = dataBus.getValue("view_code");
		String sys_db_view_id = dataBus.getValue("sys_db_view_id");
		String state = dataBus.getValue("state");
		String config_type = dataBus.getValue("config_type");
		StringBuffer sql = new StringBuffer("select a.sys_db_config_id,a.config_name,a.config_type,a.permit_column,b.sys_db_user_id,b.login_name,b.user_name,b.state,b.user_type,b.grant_table,c.sys_db_view_id,c.view_code,c.view_name,c.table_no from sys_db_config a left join sys_db_user b on a.sys_db_user_id=b.sys_db_user_id left join sys_db_view c on a.sys_db_view_id = c.sys_db_view_id ");
		StringBuffer countSql = new StringBuffer("select count(1) from sys_db_config a left join sys_db_user b on a.sys_db_user_id=b.sys_db_user_id left join sys_db_view c on a.sys_db_view_id = c.sys_db_view_id ");
		StringBuffer condition = new StringBuffer();
		
		if(config_name!=null&&config_name.length()>0){
			if(condition.length()>0){
				condition.append(" and a.config_name='").append(config_name).append("' ");
			}else{
				condition.append(" where a.config_name='").append(config_name).append("' ");
			}
		}	
		if(config_type!=null&&config_type.length()>0){
			if(condition.length()>0){
				condition.append(" and a.config_type='").append(config_type).append("' ");
			}else{
				condition.append(" where a.config_type='").append(config_type).append("' ");
			}			
		}			
		if(sys_db_user_id!=null&&sys_db_user_id.length()>0){
			if(condition.length()>0){
				condition.append(" and b.sys_db_user_id='").append(sys_db_user_id).append("' ");
			}else{
				condition.append(" where b.sys_db_user_id='").append(sys_db_user_id).append("' ");
			}			
		}
//		if(login_name!=null&&login_name.length()>0){
//			condition.append(" and b.login_name='").append(login_name).append("' ");
//		}		
		if(state!=null&&state.length()>0){
			if(condition.length()>0){
				condition.append(" and b.state='").append(state).append("' ");
			}else{
				condition.append(" where b.state='").append(state).append("' ");
			}			
		}
		if(sys_db_view_id!=null&&sys_db_view_id.length()>0){
			if(condition.length()>0){
				condition.append(" and c.sys_db_view_id='").append(sys_db_view_id).append("' ");
			}else{
				condition.append(" where c.sys_db_view_id='").append(sys_db_view_id).append("' ");
			}			
		}
//		if(view_code!=null&&view_code.length()>0){
//			condition.append(" and c.view_code='").append(view_code).append("' ");
//		}
		sql.append(condition).append("order by b.user_name,a.config_order desc");
		countSql.append(condition).append("order by b.user_name,a.config_order desc");
		stmt
				.addSqlStmt(sql.toString());
		stmt
				.setCountStmt(countSql.toString());
		return stmt;
	}
	
	public SqlStatement getUserConfigList(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("select-key");
		String sys_db_user_id = dataBus.getValue("sys_db_user_id");
		StringBuffer sql = new StringBuffer("select a.sys_db_config_id,a.config_name,a.config_type,b.sys_db_user_id,b.login_name,b.user_name,b.state,b.user_type,b.grant_table,c.sys_db_view_id,c.view_code,c.view_name,c.table_no from sys_db_config a, sys_db_user b,sys_db_view c  where a.sys_db_user_id=b.sys_db_user_id and a.sys_db_view_id = c.sys_db_view_id ");
		StringBuffer countSql = new StringBuffer("select count(1) from sys_db_config a, sys_db_user b,sys_db_view c  where a.sys_db_user_id=b.sys_db_user_id and a.sys_db_view_id = c.sys_db_view_id ");
		StringBuffer condition = new StringBuffer();
		
		if(sys_db_user_id!=null&&sys_db_user_id.length()>0){
			condition.append(" and b.sys_db_user_id='").append(sys_db_user_id).append("' ");
		}
		sql.append(condition).append("order by b.user_name,a.config_order desc");
		countSql.append(condition).append("order by b.user_name,a.config_order desc");
		stmt
				.addSqlStmt(sql.toString());
		stmt
				.setCountStmt(countSql.toString());
		return stmt;
	}

	public SqlStatement selectViewNotOfUser(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("select-key");
		String sys_db_user_id = dataBus.getValue("sys_db_user_id");
		StringBuffer sql = new StringBuffer("select a.sys_db_view_id,a.view_name from sys_db_view a left join (select sys_db_view_id from sys_db_config where sys_db_user_id='");
		sql.append(sys_db_user_id).append("') t on a.sys_db_view_id=t.sys_db_view_id where t.sys_db_view_id is null");
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}
	
	public SqlStatement getViewAuditLog(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("select-key");
		String login_name = dataBus.getValue("login_name");
		String config_name = dataBus.getValue("config_name");
		String countSql = "select count(1) from dba_fga_audit_trail where object_schema='"+login_name.toUpperCase()+"' and object_name='"+config_name.toUpperCase()+"'";		
		String sql = "select * from dba_fga_audit_trail where object_schema='"+login_name.toUpperCase()+"' and object_name='"+config_name.toUpperCase()+"'";
		stmt.addSqlStmt(sql);
		stmt.setCountStmt(countSql);
		return stmt;
	}
	
	public SqlStatement queryTablesOfTableIds(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("record");
		String tableIds = dataBus.getValue("table_Ids");
		tableIds = "'"+tableIds.replaceAll(",", "','")+"'";
		String sql = "select sys_id,table_no,table_name,table_name_cn from sys_table_semantic where table_no in ("+tableIds+")";
		stmt.addSqlStmt(sql);
		return stmt;
	}
	
	public SqlStatement updateUserTables(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("record");
		String sys_db_user_id = dataBus.getValue("sys_db_user_id");
		String table_Ids = dataBus.getValue("grant_table");
		String sql = "update sys_db_user set grant_table='"+table_Ids+"' where sys_db_user_id='"+sys_db_user_id+"'";
		stmt.addSqlStmt(sql);
		return stmt;
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

}
