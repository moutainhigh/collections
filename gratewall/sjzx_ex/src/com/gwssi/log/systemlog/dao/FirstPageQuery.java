package com.gwssi.log.systemlog.dao;

import org.apache.commons.lang.StringUtils;

import com.gwssi.common.util.DateUtil;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class FirstPageQuery extends BaseTable
{
   public FirstPageQuery()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
		registerSQLFunction( "querySystemLogList", DaoFunction.SQL_ROWSET, "查看系统日志信息" );
		 registerSQLFunction( "querySystemLog", DaoFunction.SQL_ROWSET, "查看系统日志信息" );
		 registerSQLFunction( "getUserInfo", DaoFunction.SQL_ROWSET, "获取操作人信息以供查询" );
   }

   public SqlStatement querySystemLogList(TxnContext request,
			DataBus inputData) throws TxnException
	{
		DataBus db = request.getRecord("select-key");
		
		String created_time_start = db.getValue("creat_start_time");
		String created_time_end = db.getValue("creat_end_time");
		String userid = db.getValue("username");
		String opera_time = db.getValue("created_time");
		/*String service_state = db.getValue("service_state");
		String service_type = db.getValue("service_type");
		String targets_type = db.getValue("targets_type");*/

		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.* ")
				.append("from first_page_query t,xt_zzjg_yh_new x  where t.user_id=x.yhid_pk ");
		if (StringUtils.isNotBlank(opera_time)) {
			String[] times = DateUtil.getDateRegionByDatePicker(opera_time,
					false);
			if (StringUtils.isNotBlank(times[0])) {
				sql.append( " and t.query_time >= '" + times[0] + " 00:00:00'");
				}
			if (StringUtils.isNotBlank(times[1])) {
				sql.append( " and t.query_time <= '" + times[1]+ " 24:00:00'");
			}
		}
		
		if(userid != null && !userid.equals("")){
			sql.append( " and t.user_id='" + userid + "'");
		}
		System.out.println(sql.toString());
		stmt.addSqlStmt(sql.toString()+" order by t.query_time desc");
		stmt.setCountStmt( "select count(*) from ("+sql.toString()+")" );

		return stmt;
	}
  
   
   public SqlStatement querySystemLog(TxnContext request,
			DataBus inputData) throws TxnException
	{
		DataBus db = request.getRecord("select-key");
		String first_page_query_id = db.getValue("first_page_query_id");


		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		sql.append(" select first_page_query_id,count,num,query_date,query_time,username,opername,orgid,orgname,ipaddress,first_cls,second_cls,operfrom ")
				.append("from first_page_query where first_page_query_id='");
	sql.append(first_page_query_id+"'");

		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt( "select count(*) from ("+sql.toString()+")" );

		return stmt;
	}
   
   public SqlStatement getUserInfo(TxnContext request,
			DataBus inputData) throws TxnException
	{		
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		sql.append("select t.yhid_pk key,t.yhxm title from XT_ZZJG_YH_NEW t");
		stmt.addSqlStmt(sql.toString());
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
