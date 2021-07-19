package com.gwssi.collect.database.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class CollectDatabaseTask extends BaseTable
{
   public CollectDatabaseTask()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
	   registerSQLFunction("queryCollectTaskInfo", DaoFunction.SQL_ROWSET, "查询采集任务表" );
	   registerSQLFunction("queryCollectDataBaseTaskInfo", DaoFunction.SQL_ROWSET, "查询数据库表" );
	   registerSQLFunction("queryCollectDataBaseTaskInfoForStep", DaoFunction.SQL_ROWSET, "查询数据库表" );
	   registerSQLFunction("queryTasknum", DaoFunction.SQL_ROWSET, "查询重复任务数" );
	   registerSQLFunction("queryCollectDatabseTreeInfo", DaoFunction.SQL_ROWSET, "查询数据库采集任务树展示信息" );
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
    * queryCollectDatabseTreeInfo(查询数据库采集表的树展示信息)    
    * TODO(这里描述这个方法适用条件 – 可选)    
    * TODO(这里描述这个方法的执行流程 – 可选)    
    * TODO(这里描述这个方法的使用方法 – 可选)    
    * TODO(这里描述这个方法的注意事项 – 可选)    
    * @param request
    * @param inputData
    * @return        
    * SqlStatement       
    * @Exception 异常对象    
    * @since  CodingExample　Ver(编码范例查看) 1.1
    */
   public SqlStatement queryCollectDatabseTreeInfo(TxnContext request, DataBus inputData)
	{
		String collect_task_id = request.getRecord("select-key").getValue("collect_task_id");//数据表ID
		
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.*,t1.table_name_en,t1.table_name_cn from collect_database_task t,res_collect_table t1 where t1.collect_table_id(+) = t.collect_table  ");
		if(!"".equals(collect_task_id)){
			sqlBuffer.append(" and t.collect_task_id = '"+collect_task_id+"' ");
		}
		System.out.println("查询采集任务表信息sql============"+sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt(sqlBuffer.toString());
		return stmt;
	}
   
   /**
    * 查询采集任务表信息
    * @param request
    * @param inputData
    * @return
    */
   
   public SqlStatement queryCollectTaskInfo(TxnContext request, DataBus inputData)
	{
		String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");//数据表ID
		
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from collect_task t ");
		if(!"".equals(collect_task_id)){
			sqlBuffer.append(" where t.collect_task_id = '"+collect_task_id+"' ");
		}
		System.out.println("查询采集任务表信息sql============"+sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt(sqlBuffer.toString());
		return stmt;
	}
   
   /**
    * 查询重复任务数
    * @param request
    * @param inputData
    * @return
    */
   
   public SqlStatement queryTasknum(TxnContext request, DataBus inputData)
	{
		String collect_task_id = request.getRecord("record").getValue("collect_task_id");//数据表ID
		String source_collect_table = request.getRecord("record").getValue("source_collect_table");//源数据表
		String collect_table = request.getRecord("record").getValue("collect_table");//目标数据表
		
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select count(*) as task_num from collect_database_task  m where 1=1 ");
		if(!"".equals(collect_task_id)){
			sqlBuffer.append(" and m.collect_task_id = '"+collect_task_id+"' ");
		}
		if(!"".equals(source_collect_table)){
			sqlBuffer.append(" and m.source_collect_table= '"+source_collect_table+"' ");
		}
		if(!"".equals(collect_table)){
			sqlBuffer.append(" and m.collect_table = '"+collect_table+"' ");
		}
		System.out.println("查询数据库任务数sql============"+sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt(sqlBuffer.toString());
		return stmt;
	}
   
   /**
    * 查询采集数据库详细信息
    * @param request
    * @param inputData
    * @return
    */
   
   public SqlStatement queryCollectDataBaseTaskInfo(TxnContext request, DataBus inputData)
	{
		String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");//数据表ID
		String database_task_id = request.getRecord("primary-key").getValue("database_task_id");//数据表ID
		
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.*,t1.database_task_id,t1.source_collect_table,t1.source_collect_column,t1.collect_mode,t1.collect_table,t1.description from collect_task t, collect_database_task t1 where t1.collect_task_id = t.collect_task_id ");
		if(!"".equals(collect_task_id)){
			sqlBuffer.append(" and t.collect_task_id = '"+collect_task_id+"' ");
		}
		if(!"".equals(database_task_id)){
			sqlBuffer.append(" and t1.database_task_id = '"+database_task_id+"' ");
		}
		System.out.println("查询数据库采集任务表信息sql============"+sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt("select count(1) from ("+sqlBuffer.toString()+")");
		return stmt;
	}
   
   /**
    * 
    * queryCollectDataBaseTaskInfoForStep(这里用一句话描述这个方法的作用)    
    * TODO(这里描述这个方法适用条件 – 可选)    
    * TODO(这里描述这个方法的执行流程 – 可选)    
    * TODO(这里描述这个方法的使用方法 – 可选)    
    * TODO(这里描述这个方法的注意事项 – 可选)    
    * @param request
    * @param inputData
    * @return        
    * SqlStatement       
    * @Exception 异常对象    
    * @since  CodingExample　Ver(编码范例查看) 1.1
    */
   public SqlStatement queryCollectDataBaseTaskInfoForStep(TxnContext request, DataBus inputData)
	{
		//String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");//数据表ID
		//String database_task_id = request.getRecord("primary-key").getValue("database_task_id");//数据表ID
		
		String collect_task_id = request.getRecord("select-key").getValue("collect_task_id");//数据表ID
		String database_task_id = request.getRecord("select-key").getValue("database_task_id");//数据表ID
		
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.*,t1.database_task_id,t1.source_collect_table,t1.source_collect_column,t1.collect_mode,t1.collect_table,t1.description from collect_task t, collect_database_task t1 where t1.collect_task_id = t.collect_task_id ");
		if(!"".equals(collect_task_id)){
			sqlBuffer.append(" and t.collect_task_id = '"+collect_task_id+"' ");
		}
		if(!"".equals(database_task_id)){
			sqlBuffer.append(" and t1.database_task_id = '"+database_task_id+"' ");
		}
		System.out.println("查询数据库采集任务表信息sql============"+sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt(sqlBuffer.toString());
		return stmt;
	}

}
