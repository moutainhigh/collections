package com.gwssi.collect.webservice.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

import com.gwssi.common.constant.ExConstant;

public class CollectTaskScheduling extends BaseTable
{
   public CollectTaskScheduling()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
	   registerSQLFunction("queryTaskScheduleList", DaoFunction.SQL_ROWSET,"查询任务调度");
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
    * 
    * queryTaskScheduleList(查询任务调度列表)   
    * @param request
    * @param inputData
    * @return
    * @throws TxnException        
    * SqlStatement       
    * @Exception 异常对象    
    * @since  CodingExample　Ver(编码范例查看) 1.1
    */
 	public SqlStatement queryTaskScheduleList(TxnContext request,
 			DataBus inputData) throws TxnException
 	{
 		SqlStatement stmt = new SqlStatement();

 		String task_scheduling_id = request.getRecord("select-key").getValue("task_scheduling_id");//计划任务ID
 		//String task_name = request.getRecord("select-key").getValue("task_name");//计划任务名称
 		String scheduling_type = request.getRecord("select-key").getValue("scheduling_type");//计划任务类型
 		
 		//String created_time_start = request.getRecord("select-key").getValue("created_time_start");//创建时间开始
       // String created_time_end = request.getRecord("select-key").getValue("created_time_end");//创建时间结束
 		
 		
 		StringBuffer querySql = new StringBuffer(
 				"select task_scheduling_id,scheduling_type,scheduling_day,start_time,end_time,scheduling_day1,scheduling_week,scheduling_count,creator_id,substr(created_time,0,10) created_time,last_modify_id,last_modify_time from collect_task_scheduling "
 						+ " where 1=1 ");
 		
 		if (task_scheduling_id != null && !"".equals(task_scheduling_id)) {//计划任务ID
 			querySql.append(" and task_scheduling_id = '" + task_scheduling_id + "'");
 		}
 		
// 		if (task_name != null && !"".equals(task_name)) {//任务名称
// 			querySql
// 					.append(" and task_name like '%" + task_name + "%'");
// 		}
 		if (scheduling_type != null && !"".equals(scheduling_type)) {//计划任务类型
 			querySql.append(" and scheduling_type = '" + scheduling_type + "'");
 		}
 		/**
 		if (created_time_start != null && !"".equals(created_time_start)) {//创建时间
			querySql.append(" and created_time >= '" + created_time_start
					+ "'");
		}

		if (created_time_end != null && !"".equals(created_time_end)) {
			querySql.append(" and created_time <= '" + created_time_end + "'");
		}
 		*/
 		querySql.append(" and yx_bj = '"+ExConstant.IS_MARKUP_Y+"'");
 		querySql.append(" order by created_time desc");
 		System.out.println("sql======"+querySql.toString());
 		stmt.addSqlStmt(querySql.toString());
 		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
 		return stmt;

 	}
}
