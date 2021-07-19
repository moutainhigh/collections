package com.gwssi.collect.webservice.dao;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.util.AnalyCollectFile;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.ftp.download.DownloadFile;
import com.gwssi.webservice.client.AnalyzeWsdl;
import com.gwssi.webservice.client.wsdl.OperationInfo;
import com.gwssi.webservice.client.wsdl.ParameterInfo;
import com.gwssi.webservice.client.wsdl.ServiceInfo;
import com.gwssi.webservice.server.ServiceDAO;
import com.gwssi.webservice.server.ServiceDAOImpl;

public class CollectTask extends BaseTable
{
   public CollectTask()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
	   registerSQLFunction("queryCollectTaskList", DaoFunction.SQL_ROWSET,"查询采集任务");
	   registerSQLFunction("queryCollectTaskNewList", DaoFunction.SQL_ROWSET,"查询采集任务");
	   registerSQLFunction("getCollectTask", DaoFunction.SQL_ROWSET, "获取采集任务信息" );
	   registerSQLFunction("getCollectTask2", DaoFunction.SQL_ROWSET, "获取采集任务信息2" );
	   registerSQLFunction("getFunctionByTask", DaoFunction.SQL_ROWSET, "获取webservice采集任务对应方法" );
	   registerSQLFunction("getFtpByTask", DaoFunction.SQL_ROWSET, "获取ftp采集任务对应文件" );
	   registerSQLFunction("deleteTaskItem", DaoFunction.SQL_DELETE, "删除服务表对应方法及参数" );
	   registerSQLFunction("getFuncAndParam", DaoFunction.SQL_DELETE,"获取方法及参数");
	   registerSQLFunction("getFtpFile", DaoFunction.SQL_DELETE, "获取ftp文件" );
	   registerSQLFunction("downLoadFtpFile", DaoFunction.SQL_UPDATE, "下载FTP文件并获取数据入库" );
	   registerSQLFunction("queryFilePath", DaoFunction.SQL_ROWSET,"查询附件路径信息");
	   registerSQLFunction("queryCollectTableInfo", DaoFunction.SQL_ROWSET,"查询采集表信息");
	   registerSQLFunction("getFileUploadInfo", DaoFunction.SQL_ROWSET,"获取文件上传采集信息");
	   registerSQLFunction("deleteFileUpload", DaoFunction.SQL_DELETE, "删除文件上传任务" );
	   registerSQLFunction("queryKeyColumns", DaoFunction.SQL_ROWSET,"查询主键字段信息");
	   registerSQLFunction("querySerTarName", DaoFunction.SQL_ROWSET,"查询服务对象名称");
	   registerSQLFunction("getCollectFileResult", DaoFunction.SQL_ROWSET,"查询采集文件结果信息");
	   registerSQLFunction("queryTaskScheduleListForIndex", DaoFunction.SQL_ROWSET,"查询任务调度首页用");
	   registerSQLFunction("getDBByTask", DaoFunction.SQL_ROWSET, "获取数据库采集任务对应的采集数据表" );
	   registerSQLFunction("deleteTaskDatabaseItem", DaoFunction.SQL_DELETE, "删除服务表对应方法数" );
	   registerSQLFunction("getInfoByType", DaoFunction.SQL_ROWSET,"根据指定代码集获得服务统计信息");
	   registerSQLFunction("getInfoByTarget", DaoFunction.SQL_ROWSET,"根据服务对象获得服务统计信息");
	   registerSQLFunction("getCollectTaskTableAndDataitems", DaoFunction.SQL_ROWSET,"根据服务对象获得服务统计信息");
	   registerSQLFunction("getCollectTaskInfo", DaoFunction.SQL_ROWSET,"根据采集任务ID获得统计信息");
	   registerSQLFunction("getInfoByTaskStatus", DaoFunction.SQL_ROWSET,"根据指定代码级获取采集任务信息");
	   registerSQLFunction("getInfoByCollectType", DaoFunction.SQL_ROWSET,"根据指定代码级获取采集任务信息");
	   registerSQLFunction("getInfoByTargetType", DaoFunction.SQL_ROWSET,"根据指定代码级获取采集任务信息");
	   registerSQLFunction("getFuncTest", DaoFunction.SQL_ROWSET,"查询连接信息");
	   registerSQLFunction("getFileInfoTree", DaoFunction.SQL_ROWSET,"根据FTP采集任务ID获取文件信息");
	   registerSQLFunction("getFileInfo", DaoFunction.SQL_ROWSET,"根据文件ID获取文件信息");
	   registerSQLFunction("getFTPTaskInfo", DaoFunction.SQL_ROWSET,"获取任务信息");
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
    * getInfoByTargetType(根据服务对象类型获取采集任务统计信息)
    * @param request
    * @param inputData
    * @return        
    * SqlStatement       
    * @Exception 异常对象    
    * @since  CodingExample　Ver(编码范例查看) 1.1
    */
   public SqlStatement getInfoByTargetType(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String table = inputData.getValue("table_name");
		String column = inputData.getValue("col_name");
		String title = inputData.getValue("col_title");
		if (StringUtils.isNotBlank(table) && StringUtils.isNotBlank(column)
				&& StringUtils.isNotBlank(title)) {
			sqlBuffer
				.append("with a as (select tar." + column + " as key, tar." + title
						+ " as title, NVL(t.mt, 0) as amount")
				.append(",tar.service_targets_type ")
				.append(" ,tar.show_order, tar.last_modify_time ")
				.append("from " + table + " tar, ")
				.append(" (select count(v.collect_task_id) mt, s.service_targets_type,s.service_targets_id")
				.append(" from VIEW_COLLECT_TASK v, res_service_targets s")   
				.append(" where v.service_targets_id = s.service_targets_id")      
				.append(" group by s.service_targets_type,s.service_targets_id) t")
				.append(" where tar.is_markup = 'Y' and tar." + column
						+ " = t." + column + "(+)) ")
				.append(" select * from (select * from a")
				.append(" where service_targets_type = '000'")			
				.append(" order by show_order) union all ")
				.append(" select * from(select * from a")
				.append(" where service_targets_type <> '000'")
				.append(" order by service_targets_type,title)");
		
		}
		stmt.addSqlStmt(sqlBuffer.toString());
		
		System.out.println("对象类型：sqlBuffer="+sqlBuffer.toString());
		return stmt;
	}
   /**
    * 
    * getInfoByCollectType(根据采集任务类型获取采集任务统计信息)    
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
   public SqlStatement getInfoByCollectType(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String codeType = inputData.getValue("codetype");
		String column = inputData.getValue("column");
		if (StringUtils.isNotBlank(codeType) && StringUtils.isNotBlank(column)) {
			sqlBuffer
					.append("select cd.codename as title, cd.codevalue as key, NVL(t.mt, 0) as amount ")
					.append("from codedata cd, ")
					.append("(select svr.")
					.append(column)
					.append(", count(svr.collect_task_id) as mt ")
					.append("from view_collect_task svr  group by svr.")
					.append(column).append(") t where cd.codetype = '")
					.append(codeType).append("' and cd.codevalue = t.")
					.append(column).append("(+) order by key");
			stmt.addSqlStmt(sqlBuffer.toString());
		}
		
		//System.out.println("采集类型：sqlBuffer="+sqlBuffer.toString());
		return stmt;
	}
   /**
    * 
    * getInfoByTaskStatus(根据指定代码级获取采集任务信息)    
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
   public SqlStatement getInfoByTaskStatus(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String codeType = inputData.getValue("codetype");
		String column = inputData.getValue("column");
		if (StringUtils.isNotBlank(codeType) && StringUtils.isNotBlank(column)) {
			sqlBuffer
					.append("select cd.codename as title, cd.codevalue as key, NVL(t.mt, 0) as amount ")
					.append("from codedata cd, ")
					.append("(select itf.")
					.append(column)
					.append(", count(itf.collect_task_id) as mt ")
					.append("from view_collect_task itf  group by itf.")
					.append(column).append(") t where cd.codetype = '")
					.append(codeType).append("'  and cd.codevalue = t.")
					.append(column).append("(+) order by amount desc");
			stmt.addSqlStmt(sqlBuffer.toString());
			// stmt.setCountStmt("select count(*) from (" +
			// sqlBuffer.toString()+ ")");
		}
		//System.out.println("任务状态：sqlBuffer="+sqlBuffer.toString());
		return stmt;
	}
   /**
    * 根据采集类型和任务状态查询采集任务
    * @param request
    * @param inputData
    * @return
    */
   public SqlStatement getInfoByType(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String codeType = inputData.getValue("codetype");
		String column = inputData.getValue("column");
		if (StringUtils.isNotBlank(codeType) && StringUtils.isNotBlank(column)) {
			sqlBuffer
					.append("select cd.codename as title, cd.codevalue as key, NVL(t.mt, 0) as amount ")
					.append("from codedata cd, ")
					.append("(select svr.")
					.append(column)
					.append(", count(svr.collect_task_id) as mt ")
					.append("from collect_task svr where svr.is_markup = 'Y' group by svr.")
					.append(column).append(") t where cd.codetype = '")
					.append(codeType).append("' and cd.codevalue = t.")
					.append(column).append("(+) order by amount desc");
			stmt.addSqlStmt(sqlBuffer.toString());
		}
		return stmt;
	}
   /**
    * 根据所属服务对象查询采集任务
    * @param request
    * @param inputData
    * @return
    */
	public SqlStatement getInfoByTarget(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String table = inputData.getValue("table_name");
		String column = inputData.getValue("col_name");
		String title = inputData.getValue("col_title");
		if (StringUtils.isNotBlank(table) && StringUtils.isNotBlank(column)
				&& StringUtils.isNotBlank(title)) {
			sqlBuffer
					.append("select tar." + column + " as key, tar." + title
							+ " as title, NVL(t.mt, 0) as amount, tar.service_targets_type ")
					.append("from " + table + " tar, ")
					.append("(select s." + column
							+ ", count(s.collect_task_id) as mt ")
					.append("from collect_task s where s.is_markup = 'Y' ")
					.append("group by s." + column + ") t ")
					.append("where tar.is_markup = 'Y' and mt!='0' and t." + column
							+ " = tar." + column + "(+) ")
					.append("order by amount desc, tar.last_modify_time ");
			stmt.addSqlStmt(sqlBuffer.toString());
		}

		return stmt;
	}
   /**
    * 
    * queryCollectTaskList(查询采集任务列表)   
    * @param request
    * @param inputData
    * @return
    * @throws TxnException        
    * SqlStatement       
    * @Exception 异常对象    
    * @since  CodingExample　Ver(编码范例查看) 1.1
    */
 	public SqlStatement queryCollectTaskList(TxnContext request,
 			DataBus inputData) throws TxnException
 	{
 		SqlStatement stmt = new SqlStatement();

 		String service_targets_id = request.getRecord("select-key").getValue("service_targets_id");//服务对象ID
 		String task_name = request.getRecord("select-key").getValue("task_name");//任务名称
 		String collect_type = request.getRecord("select-key").getValue("collect_type");//采集类型
 		String collect_status = request.getRecord("select-key").getValue("collect_status");//采集状态
 		String task_status = request.getRecord("select-key").getValue("task_status");//任务状态
 		String created_time_start = request.getRecord("select-key").getValue("created_time_start");//创建时间开始
        String created_time_end = request.getRecord("select-key").getValue("created_time_end");//创建时间结束
 		
 		/*DataBus db = request.getRecord("select-key");
		String service_targets_id = db.getValue("service_targets_id");
		String service_targets_type = db.getValue("service_targets_type");
		String collect_type = db.getValue("collect_type");
		String task_status = db.getValue("task_status");
		String created_time_start = db.getValue("created_time_start");
		String created_time_end = db.getValue("created_time_end");*/
		
		
		
 		StringBuffer querySql = new StringBuffer();
 		querySql.append("select t.collect_task_id,service_targets_id,data_source_id,t.task_name,collect_type,task_description,record,task_status,t.is_markup,t.creator_id,substr(t.created_time, 0, 10) created_time,t.last_modify_id,t.last_modify_time,log_file_path,collect_status,c.scheduling_day1,c.start_time from collect_task t , collect_task_scheduling c where t.collect_task_id = c.collect_task_id and t.is_markup = 'Y' and c.is_markup = 'Y'");
 		if (service_targets_id != null && !"".equals(service_targets_id)) {//服务对象ID
 			querySql.append(" and service_targets_id = '" + service_targets_id + "'");
 		}
 		if (collect_type != null && !"".equals(collect_type)) {//采集类型
 			querySql.append(" and collect_type = '" + collect_type + "'");
 		}
 		if (collect_status != null && !"".equals(collect_status)) {//采集状态
 			querySql.append(" and collect_status = '" + collect_status + "'");
 		}
 		if (task_status != null && !"".equals(task_status)) {//任务状态
 			querySql.append(" and task_status = '" + task_status + "'");
 		}
 		if (task_name != null && !"".equals(task_name)) {//任务名称
 			querySql
 					.append(" and task_name like '%" + task_name + "%'");
 		}
 		if (created_time_start != null && !"".equals(created_time_start)) {//创建时间
			querySql.append(" and created_time >= '" + created_time_start
					+ "'");
		}
		if (created_time_end != null && !"".equals(created_time_end)) {
			querySql.append(" and created_time <= '" + created_time_end + " 24:00:00'");
		}
 		querySql.append(" order by t.created_time desc");
 		//System.out.println("查询采集任务列表 sql======"+querySql.toString());
 		stmt.addSqlStmt(querySql.toString());
 		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
 		return stmt;

 	}
 	
 	/**
     * 
     * queryCollectTaskNewList(查询采集任务列表)   
     * @param request
     * @param inputData
     * @return
     * @throws TxnException        
     * SqlStatement       
     * @Exception 异常对象    
     * @since  CodingExample　Ver(编码范例查看) 1.1
     */
  	public SqlStatement queryCollectTaskNewList(TxnContext request,
  			DataBus inputData) throws TxnException
  	{
  		SqlStatement stmt = new SqlStatement();
  		//System.out.println(request.getRecord("select-key"));
  		String service_targets_id = request.getRecord("select-key").getValue("service_targets_id");//服务对象ID
  		String task_name = request.getRecord("select-key").getValue("task_name");//任务名称
  		String collect_type = request.getRecord("select-key").getValue("collect_type");//采集类型
  		String collect_status = request.getRecord("select-key").getValue("collect_status");//采集状态
  		String task_status = request.getRecord("select-key").getValue("task_status");//任务状态
  		String service_targets_type =request.getRecord("select-key").getValue("service_targets_type"); 
		// String created_time_start =
		// request.getRecord("select-key").getValue("created_time_start");//创建时间开始
		// String created_time_end =
		// request.getRecord("select-key").getValue("created_time_end");//创建时间结束
		//
  		StringBuffer querySql = new StringBuffer();
  		querySql.append("select v.collect_task_id,v.service_targets_id,v.service_targets_id as service_targets_id1," +
  				"v.task_name,v.collect_type,v.scheduling_day1,v.start_time,v.collect_status,v.task_status,v.log_file_path," +
  				"nvl(substr(t.last_modify_time,0,10),substr(t.created_time,0,10))  time,nvl(y2.yhxm,y1.yhxm) name " +
  				"from view_collect_task v,collect_task t,xt_zzjg_yh_new y1,xt_zzjg_yh_new y2,res_service_targets r where 1=1");
  		if (service_targets_id != null && !"".equals(service_targets_id)) {//服务对象ID
  			querySql.append(" and v.service_targets_id = '" + service_targets_id + "'");
  		}
  		if (collect_type != null && !"".equals(collect_type)) {//采集类型
  			querySql.append(" and v.collect_type = '" + collect_type + "'");
  		}
  		if (collect_status != null && !"".equals(collect_status)) {//采集状态
  			querySql.append(" and v.collect_status = '" + collect_status + "'");
  		}
  		if (task_status != null && !"".equals(task_status)) {//任务状态
  			querySql.append(" and v.task_status = '" + task_status + "'");
  		}
  		if (task_name != null && !"".equals(task_name)) {//任务名称
  			querySql.append(" and v.task_name like '%" + task_name + "%'");
  		}
  		if (service_targets_type != null && !"".equals(service_targets_type)) {//任务名称
  			querySql.append(" and r.service_targets_type ='")
  				.append(service_targets_type)
  				.append("'");
  				
  		}
  		querySql.append(" and r.service_targets_id=v.service_targets_id and v.collect_task_id = t.collect_task_id(+) and t.creator_id=y1.yhid_pk(+) and t.last_modify_id=y2.yhid_pk(+)");
//  		if (created_time_start != null && !"".equals(created_time_start)) {//创建时间
// 			querySql.append(" and created_time >= '" + created_time_start
// 					+ "'");
// 		}
// 		if (created_time_end != null && !"".equals(created_time_end)) {
// 			querySql.append(" and created_time <= '" + created_time_end + " 24:00:00'");
// 		}
  		querySql.append(" order by nvl(time,'1900-01-01') desc,collect_type, to_number(replace(v.start_time,':','')) ");
  		//System.out.println("查询采集任务列表 sql======"+querySql.toString());
  		stmt.addSqlStmt(querySql.toString());
  		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
  		return stmt;

  	}
 	
 	 /**
	 * getCollectTask 
	 * 根据采集任务ID查询对应所有方法
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
   
	public SqlStatement getCollectTask(TxnContext request, DataBus inputData)
	{
		String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");//采集任务ID
		//System.out.println(" collect_task_id========="+collect_task_id);
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.*,k.task_scheduling_id,k.scheduling_type,k.scheduling_week,k.scheduling_day,k.start_time,k.end_time,k.scheduling_day1,k.scheduling_count,k.interval_time from collect_task t left join collect_task_scheduling k ");
		if(collect_task_id!=null){
			sqlBuffer.append(" on t.collect_task_id = k.collect_task_id where t.collect_task_id = '"+collect_task_id+"' and t.is_markup = '"+ExConstant.IS_MARKUP_Y+"'");
		}
		//System.out.println("根据采集任务ID查询采集任务信息 sql==="+sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt("select count(1) from ("+sqlBuffer.toString()+")");
		return stmt;
	}
	
	 /**
		 * getCollectTask2 
		 * 根据采集任务ID查询对应所有方法
		 * 
		 * @param request
		 * @param inputData
		 * @return SqlStatement
		 * @since CodingExample Ver(编码范例查看) 1.1
		 */
	   
		public SqlStatement getCollectTask2(TxnContext request, DataBus inputData)
		{
			String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");//采集任务ID
			//System.out.println(" collect_task_id========="+collect_task_id);
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append("select t1.*,t1.created_time as cretime,nvl(t1.last_modify_time,t1.created_time)as modtime, " +
					"yh1.yhxm as crename,nvl(yh2.yhxm,yh1.yhxm) as modname from collect_task t1,xt_zzjg_yh_new yh1,xt_zzjg_yh_new yh2  ");
			if(collect_task_id!=null){
				sqlBuffer.append("  where t1.collect_task_id = '"+collect_task_id+"' and t1.creator_id = yh1.yhid_pk(+)  and t1.last_modify_id = yh2.yhid_pk(+) and t1.is_markup = '"+ExConstant.IS_MARKUP_Y+"'");
			}
			//System.out.println("根据采集任务ID查询采集任务信息 sql==="+sqlBuffer.toString());
			SqlStatement stmt = new SqlStatement();
			stmt.addSqlStmt(sqlBuffer.toString());
			stmt.setCountStmt("select count(1) from ("+sqlBuffer.toString()+")");
			return stmt;
		}
	
 	 /**
	 * getFunctionByTask s
	 * 根据采集任务ID查询对应所有方法
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
   
	public SqlStatement getFunctionByTask(TxnContext request, DataBus inputData)
	{
		String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");//采集任务ID
		//System.out.println(" collect_task_id========="+collect_task_id);
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from collect_webservice_task t ");
		if(collect_task_id!=null){
			sqlBuffer.append(" where t.collect_task_id = '"+collect_task_id+"' and t.method_status = '"+CollectConstants.TYPE_QY+"' order by service_no");
		}
		//System.out.println("根据采集任务ID查询对应所有方法       sql==="+sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt("select count(1) from ("+sqlBuffer.toString()+")");
		return stmt;
	}
	
	/**
	 * getDBByTask 
	 * 根据采集任务ID查询对应数据表
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
   
	public SqlStatement getDBByTask(TxnContext request, DataBus inputData)
	{
		String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");//采集任务ID
		
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from collect_database_task t ");
		if(collect_task_id!=null){
			sqlBuffer.append(" where t.collect_task_id = '"+collect_task_id+"' order by created_time");
		}
		
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt("select count(1) from ("+sqlBuffer.toString()+")");
		return stmt;
	}
	 /**
	 * getFtpByTask 
	 * 根据ftp采集任务ID查询对应所有文件
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
   
	public SqlStatement getFtpByTask(TxnContext request, DataBus inputData)
	{
		String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");//采集任务ID
		//System.out.println(" collect_task_id========="+collect_task_id);
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from collect_ftp_task t ");
		if(collect_task_id!=null){
			sqlBuffer.append(" where t.collect_task_id = '"+collect_task_id+"' and t.file_status = '"+CollectConstants.TYPE_QY+"' order by service_no");
		}
		//System.out.println("根据ftp采集任务ID查询对应所有文件       sql==="+sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt("select count(1) from ("+sqlBuffer.toString()+")");
		return stmt;
	}
	
	 /**
	 * deleteTaskItem 
	 * 根据服务表ID删除对应所有方法及参数
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @throws DBException 
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement deleteTaskItem(TxnContext request, DataBus inputData) throws DBException
	{
		String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");//服务ID
		if(collect_task_id==null||"".equals(collect_task_id)){
			collect_task_id = request.getRecord("record").getValue("collect_task_id");
		}
		StringBuffer sqlBuffer = new StringBuffer();
		SqlStatement stmt = new SqlStatement();
		if(collect_task_id!=null&&!"".equals(collect_task_id)){
			sqlBuffer.append("select * from collect_task t where t.collect_task_id = '"+collect_task_id+"'");
			ServiceDAO	daoTable	= new ServiceDAOImpl();; // 操作数据表Dao
			// 获取数据表名称
			Map tablepMap = daoTable.queryService(sqlBuffer.toString());
			String type=null;//采集类型
			if(tablepMap!=null&&!tablepMap.isEmpty()){
				type=(String) tablepMap.get("COLLECT_TYPE");
			}
			sqlBuffer = new StringBuffer();
			if(type!=null&&(type.equals(CollectConstants.TYPE_CJLX_WEBSERVICE)
					||type.equals(CollectConstants.TYPE_CJLX_SOCKET)||type.equals(CollectConstants.TYPE_CJLX_JMS))){//webservice socket jms
				sqlBuffer.append("delete from  collect_webservice_patameter where webservice_task_id in ( ");
				sqlBuffer.append("select webservice_task_id from collect_webservice_task a where a.collect_task_id = '"+collect_task_id+"')");//删除参数表
				stmt.addSqlStmt(sqlBuffer.toString());
				
				
				
				sqlBuffer = new StringBuffer();
				sqlBuffer.append("delete from collect_webservice_task t ");
				if(collect_task_id!=null){
					sqlBuffer.append(" where t.collect_task_id = '"+collect_task_id+"'");//删除方法表
				}
			}else if(type!=null&&type.equals(CollectConstants.TYPE_CJLX_FTP)){//ftp
				sqlBuffer.append("delete from collect_ftp_task t ");
				if(collect_task_id!=null){
					sqlBuffer.append(" where t.collect_task_id = '"+collect_task_id+"'");//删除ftp文件表
				}
			}
			//System.out.println("删除采集任务 sql==="+sqlBuffer.toString());
			stmt.addSqlStmt(sqlBuffer.toString());
			
			sqlBuffer = new StringBuffer();
			sqlBuffer.append("delete from collect_task_scheduling t ");
			if(collect_task_id!=null){
				sqlBuffer.append(" where t.collect_task_id = '"+collect_task_id+"'");//删除任务调度表
			}
			stmt.addSqlStmt(sqlBuffer.toString());
		}
		return stmt;
	}
	
	public SqlStatement getFuncTest(TxnContext request, DataBus inputData) {
		SqlStatement stmt = new SqlStatement();
		String data_source_id=request.getRecord("record").getValue("data_source_id");//数据源表ID
		String collect_task_id=request.getRecord("record").getValue("collect_task_id");//数据表ID
		String sql="select access_url from res_data_source where data_source_id = '"+data_source_id+"'";
	
		System.out.println("data_source_id is "+data_source_id);
		System.out.println("collect_task_id is "+collect_task_id);
		System.out.println("sql is "+sql);
		
		stmt.addSqlStmt(sql);
		stmt.setCountStmt("select count(1) from ("+sql+")");
		return stmt;
		
	}
	
	
	 /**
	 * getFuncAndParam 
	 * 根据服务表ID删除对应所有方法及参数
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @throws DBException 
	 * @throws TxnDataException 
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement getFuncAndParam(TxnContext request, DataBus inputData) throws DBException, TxnDataException
	{
		SqlStatement stmt = new SqlStatement();
		String data_source_id=request.getRecord("record").getValue("data_source_id");//数据源表ID
		String collect_task_id=request.getRecord("record").getValue("collect_task_id");//数据表ID
		String sql="select access_url from res_data_source where data_source_id = '"+data_source_id+"'";
	
		try {
			ServiceDAO	daoTable	= new ServiceDAOImpl(); // 操作数据表Dao
			Map tablepMap = daoTable.queryService(sql);
			String access_url=(String) tablepMap.get("ACCESS_URL");//访问URL
			
			ServiceInfo serviceInfo=AnalyzeWsdl.getService(access_url);
			String idParam=null;
			String idFunc=null;
			String service_no=null;
			String methodName=null;
			String nameParam=null;
			String typeParam=null;
			String methodNameTemp=null;
			Iterator iter = serviceInfo.getOperations();
			String web_name_space=serviceInfo.getTargetnamespace();
			if(web_name_space==null){
				web_name_space="";
			}
			int i = 0, j = 0;
			int count=-1;
			count=this.getNum(CollectConstants.TYPE_WEBSERVICE_TABLE,CollectConstants.TYPE_CJLX_WEBSERVICE, daoTable);
			StringBuffer insertSql=new StringBuffer();
			insertSql= new StringBuffer();
			
			StringBuffer selectSqlBuffer= new StringBuffer();
			selectSqlBuffer.append("select *  from  collect_webservice_patameter where webservice_task_id in ( ");
			selectSqlBuffer.append("select webservice_task_id from collect_webservice_task a where a.collect_task_id = '"+collect_task_id+"')");//查询参数表
			List paramList=daoTable.query(selectSqlBuffer.toString());
			
			selectSqlBuffer= new StringBuffer();
			selectSqlBuffer.append("select * from  collect_webservice_task where collect_task_id = '"+collect_task_id+"'");//查询方法表
			
			List methodList=daoTable.query(selectSqlBuffer.toString());
			
			insertSql.append("delete from  collect_webservice_patameter where webservice_task_id in ( ");
			insertSql.append("select webservice_task_id from collect_webservice_task a where a.collect_task_id = '"+collect_task_id+"')");//删除参数表
			//System.out.println("删除参数表sql==="+insertSql);
			stmt.addSqlStmt(insertSql.toString());
			
			insertSql= new StringBuffer();
			insertSql.append("delete from  collect_webservice_task where collect_task_id = '"+collect_task_id+"'");//删除方法表
			stmt.addSqlStmt(insertSql.toString());
			//System.out.println("删除方法表sql==="+insertSql);
			
			while (iter.hasNext()) {
				i++;
				OperationInfo oper = (OperationInfo) iter.next();
					
					count=count+1;
					//System.out.println("count==="+count);
					if(count<10){
						service_no="SERVICE_00"+count;
					}else if(count>=10&&count<=99){
						service_no="SERVICE_0"+count;
					}else{
						service_no="SERVICE_"+count;
					}
				
					
				methodName=oper.getTargetMethodName();//方法名
				
				if(methodName!=null&&!"".equals(methodName)){
					
					if(methodList!=null&&methodList.size()>0){
						Map mapTemp = new HashMap();
						String fanfaName=null;
						for(int k=0;k<methodList.size();k++){
							mapTemp = (Map) methodList.get(k);
							fanfaName=(String)mapTemp.get("METHOD_NAME_EN");
							//System.out.println("方法名=="+fanfaName);
							//System.out.println("methodName=="+methodName);
							if(fanfaName!=null&&fanfaName.equals(methodName)){
								idFunc=(String)mapTemp.get("WEBSERVICE_TASK_ID");
								break;
							}else if(k==methodList.size()){
								idFunc = UuidGenerator.getUUID();
							}
						}
						
					}else{
						idFunc = UuidGenerator.getUUID();
					}	
							insertSql= new StringBuffer();
							insertSql.append("insert into collect_webservice_task(webservice_task_id,collect_task_id,service_no,method_name_en,method_status,web_name_space) values('");
							insertSql.append(idFunc+"','"+collect_task_id+"','"+service_no+"','"+methodName+"','"+CollectConstants.TYPE_QY+"','"+web_name_space+"')");
							//System.out.println("insertSql插入方法表==="+insertSql);
							stmt.addSqlStmt(insertSql.toString());
				}
				List inps = oper.getInparameters();
				if (inps.size() == 0) {
					System.out.println("此操作所需的输入参数为:");
					System.out.println("执行此操作不需要输入任何参数!");
				} else {
					
					for (Iterator iterator1 = inps.iterator(); iterator1
							.hasNext();) {
						ParameterInfo element = (ParameterInfo) iterator1.next();
						nameParam=element.getName();
						typeParam=element.getKind();
						idParam = UuidGenerator.getUUID();
						insertSql= new StringBuffer();
						insertSql.append("insert into collect_webservice_patameter(webservice_patameter_id,webservice_task_id,patameter_type,patameter_name) values('");
						insertSql.append(idParam+"','"+idFunc+"','"+typeParam+"','"+nameParam+"')");
						//System.out.println("insertSql插入参数表==="+insertSql);
						stmt.addSqlStmt(insertSql.toString());
						
					}
				}
				//System.out.println("插入成功!===");
			}//重新获取方法及参数
			updateFun(methodList,stmt,daoTable);//更新方法
			updateParam(paramList,stmt,daoTable);//更新参数
		
		}catch (Exception e) {
			e.printStackTrace();
			throw new TxnDataException("error", "连接数据源失败!");
		}
		return stmt;
	}
	/**
	 * 更新方法
	 * @param methodList
	 * @param stmt
	 * @throws DBException 
	 */
	public void updateFun(List methodList,SqlStatement stmt,ServiceDAO	daoTable) throws DBException{
		
		//把原方法属性值更新到新方法中
		Map methodMap = new HashMap();
		String sql=null;
		Map tablepMap = new HashMap();
		String methodName=null;
		String method_name_en=null;
		String method_name_cn=null;//方法中文名
		String service_no=null;//任务编号
		String collect_table=null;//对应采集表
		String collect_mode=null;//代码表增量全量
		String is_encryption=null;//代码表是否
		String encrypt_mode=null;//代码表DES3DSAES
		String method_description=null;//方法描述
		String  webservice_task_id = null;//方法ID
		String web_name_space=null;//命名空间
		StringBuffer updateSql= new StringBuffer();
		if(methodList!=null&&methodList.size()>0){
			for(int i=0;i<methodList.size();i++){
			updateSql= new StringBuffer();
			methodMap = (Map) methodList.get(i);
			System.out.println("方法en名======"+methodMap.get("METHOD_NAME_EN"));//方法EN名
			method_name_en=(String)methodMap.get("METHOD_NAME_EN");
			System.out.println("方法cn名======"+methodMap.get("METHOD_NAME_CN"));//方法CN名
			method_name_cn=(String)methodMap.get("METHOD_NAME_CN");
			System.out.println("SERVICE_NO======"+methodMap.get("SERVICE_NO"));//service_no
			service_no=(String)methodMap.get("SERVICE_NO");
			System.out.println("对应采集表======"+methodMap.get("COLLECT_TABLE"));//对应采集表
			collect_table=(String)methodMap.get("COLLECT_TABLE");
			System.out.println("代码表增量全量======"+methodMap.get("COLLECT_MODE"));//代码表增量全量
			collect_mode=(String)methodMap.get("COLLECT_MODE");
			System.out.println("代码表是否======"+methodMap.get("IS_ENCRYPTION"));//代码表是否
			is_encryption=(String)methodMap.get("IS_ENCRYPTION");
			System.out.println("代码表DES3DSAES======"+methodMap.get("ENCRYPT_MODE"));//代码表DES3DSAES
			encrypt_mode=(String)methodMap.get("ENCRYPT_MODE");
			System.out.println("方法描述======"+methodMap.get("METHOD_DESCRIPTION"));//方法描述
			method_description=(String)methodMap.get("METHOD_DESCRIPTION");
			System.out.println("方法ID======"+methodMap.get("WEBSERVICE_TASK_ID"));//方法ID
			webservice_task_id=(String)methodMap.get("WEBSERVICE_TASK_ID");//方法ID
			System.out.println("命名空间======"+methodMap.get("WEB_NAME_SPACE"));//命名空间
			web_name_space=(String)methodMap.get("WEB_NAME_SPACE");//命名空间
			sql="select method_name_en from collect_webservice_task where webservice_task_id = '"+webservice_task_id+"'";
			tablepMap= new HashMap();
			tablepMap = daoTable.queryService(sql);
			updateSql.append(" update collect_webservice_task set");
			if(tablepMap!=null&&!"".equals(tablepMap)){
				methodName=(String)tablepMap.get("METHOD_NAME_EN");
				if(methodName!=null&&!"".equals(methodName)){//有此方法
					if(method_name_cn!=null&&!"".equals(method_name_cn)){//方法中文名
					updateSql.append(" method_name_cn = '"+method_name_cn+"',");
					}
					if(service_no!=null&&!"".equals(service_no)){//任务编号
						updateSql.append(" service_no = '"+service_no+"',");
					}
					if(collect_table!=null&&!"".equals(collect_table)){//对应采集表
						updateSql.append(" collect_table = '"+collect_table+"',");
					}
					if(collect_mode!=null&&!"".equals(collect_mode)){//代码表增量全量
						updateSql.append(" collect_mode = '"+collect_mode+"',");
					}
					if(is_encryption!=null&&!"".equals(is_encryption)){//代码表是否
						updateSql.append(" is_encryption = '"+is_encryption+"',");
					}
					if(encrypt_mode!=null&&!"".equals(encrypt_mode)){//代码表DES3DSAES
						updateSql.append(" encrypt_mode = '"+encrypt_mode+"',");
					}
					if(web_name_space!=null&&!"".equals(web_name_space)){//命名空间
						updateSql.append(" web_name_space = '"+web_name_space+"',");
					}
					if(method_description!=null&&!"".equals(method_description)){//方法描述
						updateSql.append(" method_description = '"+method_description+"',");
					}
					if(updateSql.toString().endsWith(",")){//更新方法
						String updateMethodSql=updateSql.toString();
						updateMethodSql=updateMethodSql.substring(0,updateMethodSql.length()-1);
						updateMethodSql+=" where webservice_task_id = '"+webservice_task_id+"'";
						System.out.println("更新方法表sql======"+updateMethodSql);//方法ID
						stmt.addSqlStmt(updateMethodSql);
					}
			 }//有此方法
		 }//有此方法
	  }//方法循环
    }//判断方法List是否为空
  }
	
	/**
	 * 更新参数
	 * @param methodList
	 * @param stmt
	 * @throws DBException 
	 */
	public void updateParam(List paramList,SqlStatement stmt,ServiceDAO	daoTable) throws DBException{
		
		//把原参数属性值更新到新参数中
		Map paramMap = new HashMap();
		StringBuffer sql=new StringBuffer();
		Map tablepMap = new HashMap();
		String paramName=null;
		String patameter_name=null;//参数名
		String patameter_type=null;//参数类型
		String patameter_value=null;//参数值
		String  webservice_task_id = null;//对应方法ID
		
		StringBuffer updateSql= new StringBuffer();
		
		if(paramList!=null&&paramList.size()>0){
			for(int i=0;i<paramList.size();i++){
			updateSql= new StringBuffer();
			sql=new StringBuffer();
			paramMap = (Map) paramList.get(i);
			System.out.println("参数名======"+paramMap.get("PATAMETER_NAME"));//参数名
			patameter_name=(String)paramMap.get("PATAMETER_NAME");
			System.out.println("参数类型======"+paramMap.get("PATAMETER_TYPE"));//参数类型代码表StringINTBOOLEAN
			patameter_type=(String)paramMap.get("PATAMETER_TYPE");//参数类型
			System.out.println("参数值======"+paramMap.get("PATAMETER_VALUE"));//参数值
			patameter_value=(String)paramMap.get("PATAMETER_VALUE");//参数值
			System.out.println("对应方法ID======"+paramMap.get("WEBSERVICE_TASK_ID"));//对应方法ID
			webservice_task_id=(String)paramMap.get("WEBSERVICE_TASK_ID");//对应方法ID
			
			
			sql.append("select patameter_name from collect_webservice_patameter a ");
			sql.append("where a.patameter_name = '"+patameter_name+"' and a.patameter_type = '"+patameter_type+"'");
			sql.append(" and a.webservice_task_id = '"+webservice_task_id+"'");
			System.out.println("查询参数======"+sql.toString());//查询参数
			tablepMap= new HashMap();
			tablepMap = daoTable.queryService(sql.toString());
			
			if(tablepMap!=null&&!"".equals(tablepMap)){
				paramName=(String)tablepMap.get("PATAMETER_NAME");
				if(paramName!=null&&!"".equals(paramName)){//有此参数
					if(patameter_value!=null&&!"".equals(patameter_value)){//参数值
						updateSql.append(" update collect_webservice_patameter a set");
						updateSql.append(" a.patameter_value = '"+patameter_value+"'");
						updateSql.append(" where  a.patameter_name = '"+patameter_name+"' and a.patameter_type = '"+patameter_type+"'");
						updateSql.append(" and a.webservice_task_id = '"+webservice_task_id+"'");
						System.out.println("更新参数表======"+updateSql.toString());//updateSql
						stmt.addSqlStmt(updateSql.toString());
					}
			 }//有此参数
		 }//有此参数
	  }//方法循环
    }//判断方法List是否为空
  }
	
	 /**
	 * getFtpFile 
	 * 根据服务表ID删除对应所有ftp文件
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @throws DBException 
	 * @throws TxnDataException 
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement getFtpFile(TxnContext request, DataBus inputData) throws DBException, TxnDataException
	{
		SqlStatement stmt = new SqlStatement();
		String data_source_id=request.getRecord("record").getValue("data_source_id");//数据源表ID
		String collect_task_id=request.getRecord("record").getValue("collect_task_id");//数据表ID
		String sql="select * from res_data_source where data_source_id = '"+data_source_id+"'";
	
		try {
			ServiceDAO	daoTable	= new ServiceDAOImpl(); // 操作数据表Dao
			Map tablepMap = daoTable.queryService(sql);
			List fileList=DownloadFile.downLoadFile(tablepMap);////链接ftp服务器
			
			String idFile=null;
			String service_no=null;
			String fileName=null;
			String nameParam=null;
			String typeParam=null;
			String methodNameTemp=null;
			
			
			int count=-1;
			count=this.getNum(CollectConstants.TYPE_FTP_TABLE,CollectConstants.TYPE_CJLX_FTP, daoTable);
			StringBuffer insertSql=new StringBuffer();
			
			StringBuffer selectSqlBuffer= new StringBuffer();
			selectSqlBuffer.append("select * from  collect_ftp_task where collect_task_id = '"+collect_task_id+"'");//查询ftp文件表
			
			List ftpList=daoTable.query(selectSqlBuffer.toString());
			insertSql.append("delete from  collect_ftp_task where collect_task_id = '"+collect_task_id+"'");//删除ftp文件表
			stmt.addSqlStmt(insertSql.toString());
			System.out.println("删除ftp文件表  sql==="+insertSql);
			Map fileMap= new HashMap();
			if(fileList!=null&&fileList.size()>0){
				for(int i=0;i<fileList.size();i++) {
					fileMap=(Map)fileList.get(i);
					count=count+1;
					System.out.println("count==="+count);
					if(count<10){
						service_no="SERVICE_00"+count;
					}else if(count>=10&&count<=99){
						service_no="SERVICE_0"+count;
					}else{
						service_no="SERVICE_"+count;
					}
				
				fileName=(String)fileMap.get("filename");//文件名
				if(fileName!=null&&!"".equals(fileName)){
					if(ftpList!=null&&ftpList.size()>0){
						Map mapTemp = new HashMap();
						String wjName=null;
						for(int k=0;k<ftpList.size();k++){
							mapTemp = (Map) ftpList.get(k);
							wjName=(String)mapTemp.get("FILE_NAME_EN");
							if(wjName!=null&&wjName.equals(fileName)){
								idFile=(String)mapTemp.get("FTP_TASK_ID");
								System.out.println("id3333333333333333333333333333=========="+idFile);
								break;
							}else if(k==ftpList.size()){
								idFile = UuidGenerator.getUUID();
							}
						}
						
					}else{
						idFile = UuidGenerator.getUUID();
					}	
						insertSql= new StringBuffer();
						insertSql.append("insert into collect_ftp_task(ftp_task_id,collect_task_id,service_no,file_name_en,file_status) values('");
						insertSql.append(idFile+"','"+collect_task_id+"','"+service_no+"','"+fileName+"','"+CollectConstants.TYPE_QY+"')");
						System.out.println("insertSql插入ftp文件表==="+insertSql);
						stmt.addSqlStmt(insertSql.toString());
				}
				
				
			}
			}//重新获取方法及参数
			System.out.println("插入成功!===");
			updateFile(ftpList,stmt,daoTable);//更新方法
		
		}catch (Exception e) {
			//e.printStackTrace();
			throw new TxnDataException("error", "连接ftp数据源失败!");
		}
		return stmt;
	}
	
	/**
	 * 更新Ftp文件
	 * @param methodList
	 * @param stmt
	 * @throws DBException 
	 */
	public void updateFile(List fileList,SqlStatement stmt,ServiceDAO	daoTable) throws DBException{
		
		//把原文件属性值更新到新文件中
		Map fileMap = new HashMap();
		String sql=null;
		Map tablepMap = new HashMap();
		String fileName=null;
		String file_name_en=null;
		String file_name_cn=null;//方法中文名
		String service_no=null;//任务编号
		String collect_table=null;//对应采集表
		String collect_mode=null;//代码表增量全量
		String file_description=null;//方法描述
		String  collect_task_id = null;//任务ID
		StringBuffer updateSql= new StringBuffer();
		if(fileList!=null&&fileList.size()>0){
			for(int i=0;i<fileList.size();i++){
			updateSql= new StringBuffer();
			fileMap = (Map) fileList.get(i);
			System.out.println("文件en名======"+fileMap.get("FILE_NAME_EN"));//文件英文名称
			file_name_en=(String)fileMap.get("FILE_NAME_EN");
			System.out.println("文件cn名======"+fileMap.get("FILE_NAME_CN"));//文件中文名称
			file_name_cn=(String)fileMap.get("FILE_NAME_CN");
			System.out.println("SERVICE_NO======"+fileMap.get("SERVICE_NO"));//service_no
			service_no=(String)fileMap.get("SERVICE_NO");
			System.out.println("对应采集表======"+fileMap.get("COLLECT_TABLE"));//对应采集表
			collect_table=(String)fileMap.get("COLLECT_TABLE");
			System.out.println("代码表增量全量======"+fileMap.get("COLLECT_MODE"));//代码表增量全量
			collect_mode=(String)fileMap.get("COLLECT_MODE");
			System.out.println("文件描述======"+fileMap.get("FILE_DESCRIPTION"));//文件描述
			file_description=(String)fileMap.get("FILE_DESCRIPTION");
			System.out.println("文件ID======"+fileMap.get("FTP_TASK_ID"));//文件ID
			collect_task_id=(String)fileMap.get("COLLECT_TASK_ID");//任务ID
			sql="select file_name_en from collect_ftp_task where collect_task_id = '"+collect_task_id+"' and file_name_en = '"+file_name_en+"'";
			System.out.println("查询ftp文件sql==="+sql);
			tablepMap= new HashMap();
			tablepMap = daoTable.queryService(sql);
			updateSql.append(" update collect_ftp_task set");
			if(tablepMap!=null&&!tablepMap.isEmpty()){
					if(file_name_cn!=null&&!"".equals(file_name_cn)){//文件中文名
					updateSql.append(" file_name_cn = '"+file_name_cn+"',");
					}
					if(service_no!=null&&!"".equals(service_no)){//任务编号
						updateSql.append(" service_no = '"+service_no+"',");
					}
					if(collect_table!=null&&!"".equals(collect_table)){//对应采集表
						updateSql.append(" collect_table = '"+collect_table+"',");
					}
					if(collect_mode!=null&&!"".equals(collect_mode)){//代码表增量全量
						updateSql.append(" collect_mode = '"+collect_mode+"',");
					}
					
					if(file_description!=null&&!"".equals(file_description)){//文件描述
						updateSql.append(" file_description = '"+file_description+"',");
					}
					if(updateSql.toString().endsWith(",")){//更新方法
						String updateMethodSql=updateSql.toString();
						updateMethodSql=updateMethodSql.substring(0,updateMethodSql.length()-1);
						updateMethodSql+=" where collect_task_id = '"+collect_task_id+"' and file_name_en = '"+file_name_en+"'";
						System.out.println("更新ftp文件表sql==="+updateMethodSql);
						stmt.addSqlStmt(updateMethodSql);
					}
			
		 }//有此文件
	  }//方法循环
    }//判断方法List是否为空
  }
	
	 /**
	 * downLoadFtpFile 
	 * 下载Ftp文件并获取数据入库
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @throws DBException 
	 * @throws TxnDataException 
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement downLoadFtpFile(TxnContext request, DataBus inputData) throws DBException, TxnDataException
	{
		SqlStatement stmt = new SqlStatement();
		String collect_task_id=request.getRecord("primary-key").getValue("collect_task_id");//任务ID
		StringBuffer sql = new StringBuffer();
		
		sql.append("select a.* from ");
		sql.append("res_data_source a,collect_task b ");
		sql.append("where a.data_source_id = b.data_source_id  and b.collect_task_id = '"+collect_task_id+"'");
	    System.out.println("sql==="+sql);
	    List fileList=null;
	    Map tablepMap=null;
	    ServiceDAO	daoTable	= new ServiceDAOImpl(); // 操作数据表Dao
		try {
			
			tablepMap = daoTable.queryService(sql.toString());
			fileList=DownloadFile.downFtpFile(tablepMap,ExConstant.FILE_FTP);////获取ftp文件并下载到服务器上
		}catch (Exception e) {
			//e.printStackTrace();
			throw new TxnDataException("error", "连接ftp数据源失败!");
		}
			Map map= new HashMap();
			String filename=null;
			String ftpFileName=null;
			String filetype=null;
			String filePath=null;
			String resultFile=null;
			String tableName=null;
			String tableId=null;
			String collect_mode=null;
			if(fileList!=null&&fileList.size()>0){
				for(int i=0;i<fileList.size();i++){
					map= (Map)fileList.get(i);
					filename=(String) map.get("filename");
					
					filePath=ExConstant.FILE_FTP+File.separator+filename;
					
					if(filename!=null&&!"".equals(filename)){
						tablepMap= new HashMap();
						filetype=filename.substring(filename.indexOf(".")+1);
						resultFile=ExConstant.FILE_FTP+File.separator+filename.substring(0,filename.indexOf("."))+"_result.txt";
						ftpFileName=filename.substring(filename.indexOf("_")+1);
						System.out.println("type=="+filetype);
						
							AnalyCollectFile file = new AnalyCollectFile();
							sql = new StringBuffer();
							sql.append("select a.*,b.table_name_en from ");
							sql.append("collect_ftp_task a,res_collect_table b ");
							sql.append("where a.collect_table = b.collect_table_id and a.file_name_en = '"+ftpFileName+"'  and a.collect_task_id = '"+collect_task_id+"'");
							System.out.println("查询ftp文件表sql=="+sql);
							tablepMap = daoTable.queryService(sql.toString());
							tableName=(String)tablepMap.get("TABLE_NAME_EN");//采集表
							tableId=(String)tablepMap.get("COLLECT_TABLE");//采集表ID
							System.out.println("tableId=="+tableId);
							System.out.println("tableName=="+tableName);
							collect_mode=(String)tablepMap.get("COLLECT_MODE");//采集方式
							
							//更新ftp文件表 文件路径
							sql = new StringBuffer();
							sql.append("update collect_ftp_task set fj_path = '"+filePath+"'");
							sql.append(" where file_name_en = '"+ftpFileName+"'  and collect_task_id = '"+collect_task_id+"'");
							stmt.addSqlStmt(sql.toString());
							
							sql = new StringBuffer();
							sql.append("select a.* from ");
							sql.append("res_collect_dataitem a ");
							sql.append("where a.collect_table_id = '"+ tableId+"'");
							System.out.println("sql===="+sql);
							List list = daoTable.query(sql.toString());
							Map tableMap= new HashMap();
							HashMap resultMap=new HashMap();
							if(list!=null&&list.size()>0){
								for(int j=0;j<list.size();j++){
									tableMap=(Map)list.get(j);
									resultMap.put(j, tableMap.get("DATAITEM_NAME_EN").toString().toUpperCase());
								}
							}
						if(filetype!=null&&(filetype.equals("xls"))||filetype.equals("xlsx")){//excel
							//file.analyExcelData(request,filePath,tableName,resultMap,collect_mode,resultFile);
							
							
						}else if(filetype!=null&&filetype.equals("txt")){
							//file.analyTxtData(request,filePath,tableName,resultMap,collect_mode,",",resultFile);
							
						}else if(filetype!=null&&filetype.equals("mdb")){
							
						}
					}
					
				}
			
		
		
		}
		return stmt;
	}
	
	/**
	 * 
	 * queryFilePath(查询附件全路径)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException        
	 * SqlStatement       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public SqlStatement queryFilePath(TxnContext request,
 			DataBus inputData) throws TxnException
 	{
 		SqlStatement stmt = new SqlStatement();

 		String fj_fk = request.getRecord("record").getValue("fj_fk");

 		//StringBuffer querySql = new StringBuffer("select a.CCGML||'/'||b.cclj||'/'||b.wybs as file_path from xt_ccgl_wjlb a,xt_ccgl_wjys b where a.cclbbh_pk = b.cclbbh_pk ");
 		StringBuffer querySql = new StringBuffer("select b.cclj||'/'||b.wybs as file_path,a.cclbbh_pk from xt_ccgl_wjlb a,xt_ccgl_wjys b where a.cclbbh_pk = b.cclbbh_pk ");
 		if (fj_fk != null && !"".equals(fj_fk)) {
 			querySql.append(" and  b.ysbh_pk ='" + fj_fk + "'");
 		}
 		
 		System.out.println("查询数据源使用情况："+querySql.toString());
 		stmt.addSqlStmt(querySql.toString());
 		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
 		return stmt;

 	}
	
	/**
	 * 
	 * queryCollectTableInfo(查询采集表字段名)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException        
	 * SqlStatement       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public SqlStatement queryCollectTableInfo(TxnContext request,
 			DataBus inputData) throws TxnException
 	{
 		SqlStatement stmt = new SqlStatement();

 		String collect_table = request.getRecord("record").getValue("collect_table");

 		StringBuffer querySql = new StringBuffer("select t1.table_name_en,t2.dataitem_name_en,t2.dataitem_name_cn from res_collect_table t1,res_collect_dataitem t2 where t1.collect_table_id= t2.collect_table_id ");
 		if (collect_table != null && !"".equals(collect_table)) {
 			querySql.append(" and  t1.collect_table_id ='" + collect_table + "'");
 		}
 		
 		System.out.println("查询采集表字段列名："+querySql.toString());
 		stmt.addSqlStmt(querySql.toString());
 		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
 		return stmt;

 	}
	
	/**
	 * 
	 * querySerTarName(根据服务id查询服务对象名称)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException        
	 * SqlStatement       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public SqlStatement querySerTarName(TxnContext request,
 			DataBus inputData) throws TxnException
 	{
 		SqlStatement stmt = new SqlStatement();

 		String service_targets_id = request.getRecord("record").getValue("service_targets_id");

 		StringBuffer querySql = new StringBuffer("select distinct t2.service_targets_name from collect_task t,res_service_targets t2 where t.service_targets_id = t2.service_targets_id  ");
 		if (service_targets_id != null && !"".equals(service_targets_id)) {
 			querySql.append(" and  t.service_targets_id ='" + service_targets_id + "'");
 		}
 		
 		System.out.println("查询服务对象："+querySql.toString());
 		stmt.addSqlStmt(querySql.toString());
 		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
 		return stmt;

 	}
	
	/**
	 * 
	 * getCollectFileResult(查询文件采集结果信息)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException        
	 * SqlStatement       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public SqlStatement getCollectFileResult(TxnContext request,
 			DataBus inputData) throws TxnException
 	{
 		SqlStatement stmt = new SqlStatement();

 		String collect_joumal_id = request.getRecord("record").getValue("collect_joumal_id");

 		StringBuffer querySql = new StringBuffer("select collect_table_name,collect_data_amount,task_consume_time,return_codes from collect_joumal where 1=1  ");
 		if (collect_joumal_id != null && !"".equals(collect_joumal_id)) {
 			querySql.append(" and  collect_joumal_id ='" + collect_joumal_id + "'");
 		}
 		
 		System.out.println("查询采集文件结果信息："+querySql.toString());
 		stmt.addSqlStmt(querySql.toString());
 		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
 		return stmt;

 	}
	
	/**
	 * 
	 * getFileUploadInfo(获取文件上传采集表信息)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException        
	 * SqlStatement       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public SqlStatement getFileUploadInfo(TxnContext request,
 			DataBus inputData) throws TxnException
 	{
 		SqlStatement stmt = new SqlStatement();

 		String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");

 		StringBuffer querySql = new StringBuffer("select t.collect_task_id,t.service_targets_id,t.data_source_id,t.task_name,t.collect_type,t.task_description,t.record,t.task_status,t.fj_fk,t.fjmc,t.log_file_path,t.collect_status,t1.file_upload_task_id,t1.collect_table,t1.collect_mode,t1.check_result_file_name from collect_task t,collect_file_upload_task t1 where t.collect_task_id=t1.collect_task_id ");
 		if (collect_task_id != null && !"".equals(collect_task_id)) {
 			querySql.append(" and  t.collect_task_id ='" + collect_task_id + "'");
 		}
 		
 		System.out.println("查询文件上传采集信息："+querySql.toString());
 		stmt.addSqlStmt(querySql.toString());
 		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
 		return stmt;

 	}
	/**
	 * 获取任务编号
	 * @param tableName
	 * @param daoTable
	 * @return
	 * @throws DBException
	 */
	public int getNum(String tableName,String type,ServiceDAO daoTable) throws DBException{
		int num=0;
		String sql="select service_no from "+tableName+" a,collect_task b where a.collect_task_id = b.collect_task_id "
			+" and b.collect_type = '"+type+"' order by service_no desc";
		System.out.println("获取任务编号sql=========="+sql);
		List list = daoTable.query(sql);
		Map map=new HashMap();
		if(list!=null&&list.size()>0){
			map=(HashMap)list.get(0);
			String service_no = (String)map.get("SERVICE_NO");//最大值
			if(service_no!=null&&!"".equals(service_no)){
				service_no=service_no.substring(service_no.indexOf("_")+1);
				num =Integer.parseInt(service_no);
			}
		}
		System.out.println("num==="+num);
		return num;
	}
	
	/**
	 * 
	 * deleteFileUpload(删除文件上传采集任务)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param request
	 * @param inputData
	 * @return
	 * @throws DBException        
	 * SqlStatement       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public SqlStatement deleteFileUpload(TxnContext request, DataBus inputData) throws DBException
	{
		SqlStatement stmt = new SqlStatement();
		DataBus data = request.getRecord("primary-key");
		String collect_task_id = data.getValue("collect_task_id");
	
		StringBuffer updateSql =new StringBuffer("update collect_task t set is_markup='N' " +
																		"  where t.collect_task_id='"+collect_task_id+"'");
		System.out.println("删除文件上传采集任务:::"+updateSql.toString());
		stmt.addSqlStmt(updateSql.toString());
		return stmt;
	}
	
	/**
	 * 查询每日任务列表
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 */
	public SqlStatement queryTaskScheduleListForIndex(TxnContext request,
			DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String year = request.getRecord("select-key").getValue("year");
		String month = request.getRecord("select-key").getValue("month");
		String day = request.getRecord("select-key").getValue("day");
		if (StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month)
				&& StringUtils.isNotBlank(day)) {
			sqlBuffer
					.append("with t1 as (select ct.collect_task_id,")
					.append(" ct.task_name, cts.start_time, cts.end_time,")
					.append(" cts.scheduling_type, cts.scheduling_week, cts.scheduling_day")
					.append(" from collect_task_scheduling cts, collect_task ct")
					.append(" where cts.is_markup = 'Y' and ct.is_markup = 'Y'")
					.append(" and cts.collect_task_id = ct.collect_task_id)");
			sqlBuffer
				.append("select * from (select t1.collect_task_id, t1.task_name, t1.start_time, t1.end_time ")
				.append("from t1 ")
				.append("where t1.scheduling_type = '01' ")
				.append("union all ")
				.append("select t1.collect_task_id, t1.task_name, t1.start_time, t1.end_time ")
				.append("from t1 ")
				.append("where t1.scheduling_type = '02' ")
				.append("and t1.scheduling_week like ")
				.append("'%' || (select to_char(to_date('"+year+"-"+((month.length()>1) ? month : "0"+month)+"-"+((day.length()>1) ? day : "0"+day)+"', 'yyyy-mm-dd') - 1, 'd') ")
				.append("from dual) || '%' ")
				.append("union all ")
				.append("select t1.collect_task_id, t1.task_name, t1.start_time, t1.end_time ")
				.append("from t1 ")
				.append("where t1.scheduling_type = '03' ")
				.append("and t1.scheduling_day = '"+day+"' ")
				.append(") t order by t.start_time ");
		}
		//System.out.println("taskSql = "+sqlBuffer.toString());
		stmt.addSqlStmt(sqlBuffer.toString());
		return stmt;
	}
	
	/**
	 * 
	 * queryKeyColumns(查询采集表的主键信息)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException        
	 * SqlStatement       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public SqlStatement queryKeyColumns(TxnContext request,
 			DataBus inputData) throws TxnException
 	{
 		SqlStatement stmt = new SqlStatement();

 		String collect_table = request.getRecord("record").getValue("collect_table");

 		StringBuffer querySql = new StringBuffer("select t1.dataitem_name_en from res_collect_dataitem t1 where 1=1 ");
 		if (collect_table != null && !"".equals(collect_table)) {
 			querySql.append(" and  t1.collect_table_id ='" + collect_table + "'");
 		}
 		
 		querySql.append(" and t1.is_key='1' ");
 		System.out.println("查询采集表字段列名："+querySql.toString());
 		stmt.addSqlStmt(querySql.toString());
 		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
 		return stmt;

 	}
	
	/**
	 * deleteTaskItem 
	 * 根据服务表ID删除对应所有方法及参数
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @throws DBException 
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement deleteTaskDatabaseItem(TxnContext request, DataBus inputData) throws DBException
	{
		String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");//服务ID
		if(collect_task_id==null||"".equals(collect_task_id)){
			collect_task_id = request.getRecord("record").getValue("collect_task_id");
		}
		StringBuffer sqlBuffer = new StringBuffer("delete from collect_database_task t ");
		SqlStatement stmt = new SqlStatement();
		if(collect_task_id!=null&&!"".equals(collect_task_id)){
			sqlBuffer.append(" where t.collect_task_id = '"+collect_task_id+"'");
			stmt.addSqlStmt(sqlBuffer.toString());
			
			sqlBuffer = new StringBuffer();
			sqlBuffer.append("delete from collect_task_scheduling t ");
			if(collect_task_id!=null){
				sqlBuffer.append(" where t.collect_task_id = '"+collect_task_id+"'");//删除任务调度表
			}
			stmt.addSqlStmt(sqlBuffer.toString());
			
		}
		
		return stmt;
	}
	
	
	   /**
	    * 根据采集任务ID查询对应采集表和字段
	    * @param request
	    * @param inputData
	    * @return
	    */
		public SqlStatement getCollectTaskTableAndDataitems(TxnContext request, DataBus inputData)
		{
			
			String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");//服务ID
			SqlStatement stmt = new SqlStatement();
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append("select distinct * from view_collect_task_table t where collect_task_id='");
			sqlBuffer.append(collect_task_id);
			sqlBuffer.append("'");
			stmt.addSqlStmt(sqlBuffer.toString());
			//System.out.println(sqlBuffer.toString());
			return stmt;
		}
	/**
    * 根据采集任务ID查询对应统计数据
    * @param request
    * @param inputData
    * @return
    */
	public SqlStatement getCollectTaskInfo(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		DataBus db= request.getRecord("select-key");
		String collect_task_id = db.getValue("collect_task_id");//服务ID
		if(collect_task_id!=null && !"".equals(collect_task_id)){
			String start_time = db.getValue("start_time");
			String end_time = db.getValue("end_time");
			String task_name = db.getValue("task_name");
			if((start_time==null || "".equals(start_time)) && (end_time==null || "".equals(end_time))){
				start_time ="to_char(add_months(sysdate,-2),'yyyy-mm-dd')";
				end_time ="to_char(sysdate,'yyyy-mm-dd')";
			}else{
				if(start_time==null || "".equals(start_time)){
					start_time="to_char(add_months(sysdate,-12),'yyyy-mm-dd')";
				}else{
					start_time="'"+start_time+"'";
				}
				if(end_time==null || "".equals(end_time)){
					end_time="to_char(sysdate,'yyyy-mm-dd')";
					
				}else{
					end_time="'"+end_time+"'";
				}
				
				
			}
			
			
			sql.append("with a as (select d.log_date from (select")
				.append(" to_char(to_date("+start_time+",'YYYY-mm-dd')+ rownum - 1,'YYYY-mm-dd')")
				.append(" as log_date from dual connect by rownum <=")
				.append(" (to_date("+end_time+", 'yyyy-mm-dd') -")
				.append(" to_date("+start_time+", 'yyyy-mm-dd')+1)) d")
			    .append(" where d.log_date not in")              
			    .append(" (select exception_Date log_date from exception_date))")
			    .append(" select a.log_date,nvl(l.sum_num, 0) sum_num,nvl(l.sum_count, 0) sum_count,")     
			    .append(" nvl(l.avg_time, 0) avg_time,nvl(l.error_num, 0) error_num from a,")
			    .append(" (select log_date,sum(t.sum_record_amount) sum_num,sum(t.exec_count) sum_count,")   
			    .append(" TO_CHAR(sum(t.sum_consume_time) / sum(t.exec_count),'fm999999990.99') avg_time,")   
			    .append(" sum(t.error_num) error_num from collect_log_statistics t where 1 = 1")     
			    .append(" and t.log_date >= "+start_time+" and t.log_date <= "+end_time+"")                   
			    .append(" and t.task_id = '"+collect_task_id+"'")           
			    .append(" group by log_date) l where a.log_date = l.log_date(+) order by log_date"); 
			/*
			sql.append("select log_date,sum(t.sum_record_amount) sum_num,")
				.append(" sum(t.exec_count) sum_count,")
				.append(" TO_CHAR(sum(t.sum_consume_time) / sum(t.exec_count),")
				.append(" 'fm999999990.99') avg_time,0 as  error_num")
				.append(" from collect_log_statistics t where 1 = 1");
		    if(start_time==null && end_time==null){//默认当月
		    	sql.append(" and log_date>=to_char(add_months(sysdate, -2), 'yyyy-MM') || '-01'")
					.append(" and log_date < to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01'");
		    }
		    if(start_time!=null){
		    	sql.append(" and t.log_date >= '"+start_time+"'");
		    }
		    if(end_time!=null){
		    	sql.append(" and t.log_date <= '"+end_time+"'");
		    }
		    
		    sql.append(" and t.task_id = '"+collect_task_id+"'")
		    	.append(" and t.log_date not in (select exception_Date from exception_date)")
		    	.append(" group by log_date")
		    	.append(" order by log_date");
		   */
			stmt.addSqlStmt(sql.toString());
		}else{
			stmt.addSqlStmt("");
		}
		
		System.out.println(sql.toString());
		return stmt;
	}
	
	/**
	    * 根据FTP采集任务ID获取文件信息
	    * @param request
	    * @param inputData
	    * @return
	    */
	public SqlStatement getFileInfoTree(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		String collect_task_id = request.getRecord("select-key").getValue(
				"collect_task_id");
		//System.out.println(request);
		sql.append("select * from collect_ftp_task where collect_task_id='")
				.append(collect_task_id).append("'");
		stmt.addSqlStmt(sql.toString());
		
		
		stmt.setCountStmt("select count(1) from ("+sql.toString()+")");
		System.out.println("getFileInfoTree="+sql.toString());
		return stmt;
	}
	/**
	    * 根据文件ID获取文件信息
	    * @param request
	    * @param inputData
	    * @return
	    */
	public SqlStatement getFileInfo(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		String ftp_task_id = request.getRecord("select-key").getValue(
				"ftp_task_id");
		sql.append("select * from collect_ftp_task where ftp_task_id='")
				.append(ftp_task_id).append("'");
		System.out.println("getFileInfo---"+sql.toString());
		stmt.addSqlStmt(sql.toString());
		
		return stmt;
	}
	/**
	    * 获取任务信息
	    * @param request
	    * @param inputData
	    * @return
	    */
	public SqlStatement getFTPTaskInfo(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		//System.out.println(request);
		//System.out.println(inputData);
		String collect_task_id = request.getRecord("select-key").getValue(
				"collect_task_id");
		
			sql.append("select c.*,k.task_scheduling_id,k.scheduling_type,k.scheduling_week,k.scheduling_day,k.start_time,k.end_time,k.scheduling_day1,k.scheduling_count,k.interval_time from (")
			.append("select * from collect_task t where")
			.append(" t.collect_task_id = '")
			.append(collect_task_id).append("') c,")
            .append("(select * from collect_task_scheduling where is_markup = 'Y' ) k" )   
            .append(" where c.collect_task_id = k.collect_task_id(+)"); 
			stmt.addSqlStmt(sql.toString());
			
		System.out.println("getFTPTaskInfo="+sql.toString());
		return stmt;
	}
			
		



}