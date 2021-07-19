package com.gwssi.dw.runmgr.db.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class SysDbView extends BaseTable
{
   public SysDbView()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
	   registerSQLFunction( "haveAssigned", DaoFunction.SQL_ROWSET, "检查此视图是否已被使用" );		
	   registerSQLFunction("getDBViewList", DaoFunction.SQL_ROWSET,
                             "查询视图列表");
   }

	public SqlStatement haveAssigned(TxnContext request, DataBus inputData){
		SqlStatement stmt = new SqlStatement( );
		DataBus dataBus = request.getRecord("select-key");
		String sys_db_view_id = dataBus.getValue("sys_db_view_id");
		stmt.addSqlStmt( "select SYS_DB_VIEW_ID from SYS_DB_CONFIG where SYS_DB_VIEW_ID = '" + sys_db_view_id + "'" );
		return stmt;
	}

	public SqlStatement getDBViewList(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("select-key");
		String view_code = dataBus.getValue("view_code");
		String view_name = dataBus.getValue("view_name");
		String create_by = dataBus.getValue("create_by");
		String start_date = dataBus.getValue("create_start_date");
		String end_date = dataBus.getValue("create_end_date");
		StringBuffer sql = new StringBuffer("select * from sys_db_view a ");
		StringBuffer countSql = new StringBuffer("select count(1) from sys_db_view a ");
		StringBuffer condition = new StringBuffer();
		
		if(view_code!=null&&view_code.length()>0){
			if(condition.length()>0){
				condition.append(" and a.view_code='").append(view_code).append("' ");
			}else{
				condition.append(" where a.view_code='").append(view_code).append("' ");
			}
		}	
		if(view_name!=null&&view_name.length()>0){
			if(condition.length()>0){
				condition.append(" and a.view_name like '%").append(view_name).append("%' ");
			}else{
				condition.append(" where a.view_name like '%").append(view_name).append("%' ");
			}			
		}			
		if(create_by!=null&&create_by.length()>0){
			if(condition.length()>0){
				condition.append(" and a.create_by='").append(create_by).append("' ");
			}else{
				condition.append(" where a.create_by='").append(create_by).append("' ");
			}	
		}	
		if(start_date!=null&&start_date.length()>0){
			if(condition.length()>0){
				condition.append(" and a.create_date>='").append(start_date).append("' ");
			}else{
				condition.append(" where a.create_date>='").append(start_date).append("' ");
			}	
		}
		if(end_date!=null&&end_date.length()>0){
			if(condition.length()>0){	
				condition.append(" and a.create_date<='").append(end_date).append("' ");
			}else{	
				condition.append(" where a.create_date<='").append(end_date).append("' ");
			}
		}
		sql.append(condition).append("order by a.view_order");
		countSql.append(condition).append("order by a.view_order");
		stmt
				.addSqlStmt(sql.toString());
		stmt
				.setCountStmt(countSql.toString());
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
