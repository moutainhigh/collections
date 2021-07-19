package com.gwssi.log.report.dao;

import org.apache.commons.lang.StringUtils;

import com.gwssi.common.util.DateUtil;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class LogReportUse extends BaseTable
{
   public LogReportUse()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
	   registerSQLFunction( "queryLogReportUse", DaoFunction.SQL_ROWSET, "查询报告操作情况" );
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
    * 查询系统使用情况报告
    * */
   public SqlStatement queryLogReportUse(TxnContext request,DataBus inputData) throws TxnException
	{	   
	   
		SqlStatement stmt = new SqlStatement();
		
		/*String report_name = request.getRecord("select-key").getValue("report_name");
		String create_date_start = request.getRecord("select-key").getValue("create_date_start");
		String create_date_end = request.getRecord("select-key").getValue("create_date_end");
		*/
		String opera_time=request.getRecord("select-key").getValue("created_time");
		StringBuffer querySql = new StringBuffer("select t.report_name,t1.* from LOG_REPORT_CREATE t,log_report_use t1 " +
														" where t1.log_report_create_id = t.log_report_create_id(+) ");
		
		if (StringUtils.isNotBlank(opera_time)) {
			String[] times = DateUtil.getDateRegionByDatePicker(opera_time,
					false);
			if (StringUtils.isNotBlank(times[0])) {
				querySql.append(" and t1.oper_date >= '"+times[0]+" 00:00:00' ");
				}
			if (StringUtils.isNotBlank(times[1])) {
				querySql.append(" and t1.oper_date <= '"+times[1]+" 23:59:59' ");
			}
		}
		
		querySql.append(" order by t1.oper_date desc ");
		
		//System.out.println("queryLogReportUse>>>"+querySql.toString());
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		
		return stmt;
	}

}
