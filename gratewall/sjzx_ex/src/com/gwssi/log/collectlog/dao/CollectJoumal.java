package com.gwssi.log.collectlog.dao;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

import com.gwssi.common.util.DateUtil;

public class CollectJoumal extends BaseTable
{
	public CollectJoumal()
	{

	}

	/**
	 * 注册SQL语句
	 */
	protected void register()
	{
		registerSQLFunction("queryCollectLog", DaoFunction.SQL_ROWSET,
				"查看采集日志信息");
		registerSQLFunction("queryCollectLogList", DaoFunction.SQL_ROWSET,
				"查看采集日志");
		registerSQLFunction("getInfoByTarget", DaoFunction.SQL_ROWSET, "查看服务对象");
		registerSQLFunction("queryShareServiceListOrder",
				DaoFunction.SQL_ROWSET, "查看服务对象");
		registerSQLFunction("updateCollectLog", DaoFunction.SQL_UPDATE,
				"采集日志归档");
		registerSQLFunction("getTargetInfo", DaoFunction.SQL_ROWSET, "获取服务对象列表做查询条件");
		registerSQLFunction("queryLogData", DaoFunction.SQL_ROWSET, "根据查询条件获取日志信息");
		
	}

	public SqlStatement queryCollectLog(TxnContext request, DataBus inputData)
			throws TxnException
	{
		DataBus db = request.getRecord("select-key");
		String collect_joumal_id = db.getValue("collect_joumal_id");

		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		sql
				.append(
						" select collect_joumal_id,collect_task_id,task_name,method_name_cn,collect_table_name,service_targets_id,service_targets_name,collect_type,task_id,service_no,task_start_time,task_end_time,task_consume_time,collect_data_amount,task_status,patameter,return_codes ")
				.append("from collect_joumal where collect_joumal_id='");
		sql.append(collect_joumal_id);
		sql.append("'");
		sql.append(" and task_status is null ");
		//System.out.println(sql.toString());
		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt("select count(*) from (" + sql.toString() + ")");

		return stmt;
	}

	public SqlStatement queryCollectLogList(TxnContext request,
			DataBus inputData) throws TxnException
	{
		DataBus db = request.getRecord("select-key");
		String collect_joumal_id = db.getValue("collect_joumal_id");
		String created_time_start = db.getValue("created_time_start");
		String created_time_end = db.getValue("created_time_end");
		String task_name = db.getValue("task_name");
		String task_status = db.getValue("task_status");
		String service_targets_name = db.getValue("service_targets_name");

		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		sql
				.append(
						" select collect_joumal_id,collect_task_id,task_name,method_name_cn,collect_table_name,service_targets_id,service_targets_name,collect_type,task_id,service_no,task_start_time,task_end_time,task_consume_time,collect_data_amount,task_status,patameter,return_codes ")
				.append("from collect_joumal where 1=1");

		if (task_name != null && !"".equals(task_name)) {
			sql.append(" and lower(task_name) like lower('%" + task_name + "%')");
		}

		if (service_targets_name != null && !"".equals(service_targets_name)) {

			sql.append(" and service_targets_id = '" + service_targets_name
					+ "'");
		}

		if (task_status != null && !"".equals(task_status)) {
			if (task_status.equals("00"))
				sql.append(" and return_codes = 'BAIC0000'");
			else
				sql.append(" and return_codes <> 'BAIC0000'");
		}

		if (created_time_start != null && !created_time_start.equals("")) {
			sql.append(" and task_start_time >= '" + created_time_start
					+ " 00:00:00'");
		}
		if (created_time_end != null && !created_time_end.equals("")) {
			sql.append(" and task_start_time <= '" + created_time_end
					+ " 24:00:00'");
		}

		sql.append(" and task_status is null ");
		sql.append(" order by task_start_time desc, task_end_time desc");

		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt("select count(*) from (" + sql.toString() + ")");

		return stmt;
	}

	/**
	 * 
	 * getInfoByTarget 根据指定字段获得服务的统计信息 比如每个服务对象配置多少个服务
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement getInfoByTarget(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append("select t.service_targets_id as key, t.service_targets_name as title,service_targets_type ");
		sqlBuffer.append("from res_service_targets t where t.is_markup='Y'");

		stmt.addSqlStmt(sqlBuffer.toString()
				+ " order by t.last_modify_time desc");

		return stmt;
	}

	public SqlStatement queryShareServiceListOrder(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();

		DataBus db = request.getRecord("select-key");
		String collect_joumal_id = db.getValue("collect_joumal_id");

		String service_targets_id = db.getValue("service_targets_id");
		String created_time_start = db.getValue("created_time_start");
		String created_time_end = db.getValue("created_time_end");
		String task_start_time = db.getValue("task_start_time");
		String task_name = db.getValue("task_name");

		StringBuffer sql = new StringBuffer(
				"select t.* from collect_joumal t where 1=1");
		if (StringUtils.isNotBlank(service_targets_id)) {
			sql.append(" and t.service_targets_id = '" + service_targets_id
					+ "'");
		}

		if (StringUtils.isNotBlank(created_time_start)) {
			sql
					.append(" and t.task_start_time >= '" + created_time_start
							+ "'");
		}
		if (StringUtils.isNotBlank(created_time_end)) {
			sql.append(" and t.task_start_time <= '" + created_time_end + "'");
		}

		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt("select count(1) from (" + sql.toString() + ")");
		return stmt;
	}

	public SqlStatement updateCollectLog(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus db = request.getRecord("select-key");
		String time_end = db.getValue("created_time_end");
		String time_start = db.getValue("created_time_start");
		time_start = time_start + " " + "00:00:00";
		time_end = time_end + " " + "23:59:59";
		
		StringBuffer sql = new StringBuffer();
		sql
				.append("update collect_joumal t set t.task_status = '02' where t.task_start_time between ");
		sql.append("'");
		sql.append(time_start);
		sql.append("'");
		sql.append(" and ");
		sql.append("'");
		sql.append(time_end);
		sql.append("'");
		sql.append(" and t.task_status is null");

		stmt.addSqlStmt(sql.toString());
		return stmt;
	}
	/**
	 * 
	 * getTargetInfo 获取服务对象列表做查询条件"
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement getTargetInfo(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		sql.append("with tmp as (select r.service_targets_id   key,")
		.append(" r.service_targets_name title,r.service_targets_type,")
		.append(" show_order from res_service_targets r")
		.append(" where r.is_markup = 'Y' and r.is_formal='Y' group by r.service_targets_id,") 
		.append(" r.service_targets_type,r.service_targets_name, show_order)") 
		.append(" select * from (select * from tmp where service_targets_type = '000' order by show_order)")            
		.append(" union all select *  from (select * from tmp where service_targets_type <> '000' order by service_targets_type, title)");     
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}
	/**
	 * 根据查询条件获取日志信息 /txn6011020.do
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryLogData(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();

		DataBus db = request.getRecord("select-key");
		String type = db.getValue("type");
		String countsql="";	
		String sql="";
		if("c".equals(type)){//只查采集任务日志
			Map map=getCollectLogSql(db);
			countsql=(String)map.get("countsql");
			
			sql=(String)map.get("sql");
			//System.out.println("csql="+sql);
			stmt.addSqlStmt(sql);      
		}else if ("s".equals(type)){//只查共享日志
			Map map=getShareLogSql(db);
			sql=(String)map.get("sql");
			countsql=(String)map.get("countsql");
			//System.out.println("ssql:"+sql);
			stmt.addSqlStmt(sql);
		}else{//全部
			Map smap=getShareLogSql(db);
			Map cmap=getCollectLogSql(db);
			
			countsql=smap.get("countsql").toString()+" union all "+cmap.get("countsql").toString();
			sql=smap.get("sql").toString()
					+" union all select * from ("
					+cmap.get("sql").toString()
					+")";
					
			System.out.println("allsql:"+sql);
			stmt.addSqlStmt(sql);
		}
		System.out.println("Sql1="+sql);
		//System.out.println("Count="+countsql);
		stmt.setCountStmt("select count(1) from ("+countsql+")");
		return stmt;
	}
	/**
	 * 根据查询条件生成采集日志SQL
	 * @param db
	 * @return
	 */
	private Map getCollectLogSql(DataBus db)
	{
		String service_targets_id = db.getValue("service_targets_id");
		String service_targets_type = db.getValue("service_targets_type");
		String start_time = db.getValue("created_time");
		String tsname = db.getValue("tsname");
		String status = db.getValue("status");
		StringBuffer countsql=new StringBuffer();
		StringBuffer sql=new StringBuffer();
			sql.append("select t.service_targets_id,t.service_targets_name,t.method_name_cn tsname,")
				.append("t.task_start_time start_time,")
				.append("t.task_consume_time consume_time,t.collect_data_amount record_amount,")
				.append(" (case when t.RETURN_CODES not in('BAIC0000','BAIC0010','TAX0010') then '失败' else '成功' end) status,")
				.append(" t.collect_joumal_id tsid,'cj' as tablefrom,collect_task_id as service_id")
				.append(" from collect_joumal t where t.task_status is null and t.return_codes is not null ");
			countsql.append("select t.collect_joumal_id tsid")
			.append(" from collect_joumal t where t.task_status is null and t.return_codes is not null ");
        
			if(StringUtils.isNotBlank(service_targets_id)){
            	sql.append(" and t.service_targets_id='")
            		.append(service_targets_id).append("'");
            	countsql.append(" and t.service_targets_id='")
        			.append(service_targets_id).append("'");
            }
            if(StringUtils.isNotBlank(service_targets_type)){
            	sql.append(" and t.service_targets_id in (select r.service_targets_id")
            		.append(" from res_service_targets r where r.service_targets_type='")
                    .append(service_targets_type).append("')");
            	countsql.append(" and t.service_targets_id in (select r.service_targets_id")
        			.append(" from res_service_targets r where r.service_targets_type='")
        			.append(service_targets_type).append("')");
            }
            if (StringUtils.isNotBlank(start_time)) {
    			String[] times = DateUtil.getDateRegionByDatePicker(start_time,
    					false);
    			if (StringUtils.isNotBlank(times[0])) {
    				sql.append(" and t.task_start_time >= '" + times[0] + ":00'");
    				countsql.append(" and t.task_start_time >= '" + times[0] + ":00'");
    				}
    			if (StringUtils.isNotBlank(times[1])) {
    				sql.append(" and t.task_start_time <= '" + times[1] + ":59'");
    				countsql.append(" and t.task_start_time <= '" + times[1] + ":59'");
    			}
    		}else {
    			sql.append(" and t.task_start_time>=to_char(sysdate-1,'yyyy-mm-dd')|| ' 00:00:00'")
    				.append(" and t.task_start_time<=to_char(sysdate,'yyyy-mm-dd hh:mm:ss')");
    			countsql.append(" and t.task_start_time>=to_char(sysdate-1,'yyyy-mm-dd')|| ' 00:00:00'")
					.append(" and t.task_start_time<=to_char(sysdate,'yyyy-mm-dd hh:mm:ss')");
    		}
            if(StringUtils.isNotBlank(tsname)){
            	sql.append(" and t.task_name like '%").append(tsname).append("%'");
            	countsql.append(" and t.task_name like '%").append(tsname).append("%'");
            }
            if(StringUtils.isNotBlank(status)){
            	if("1".equals(status)){//成功状态
            		sql.append(" and t.return_codes in ('BAIC0000','BAIC0010','TAX0010')");
            		countsql.append(" and t.return_codes in ('BAIC0000','BAIC0010','TAX0010')");
            	}else if("0".equals(status)){//失败状态
            		sql.append(" and t.return_codes not in ('BAIC0000','BAIC0010','TAX0010')");
            		countsql.append(" and t.return_codes not in ('BAIC0000','BAIC0010','TAX0010')");
            	}
            }
            sql.append(" order by start_time desc");
        Map map=new HashMap();
        map.put("sql", sql.toString());
        map.put("countsql", countsql.toString());
		return  map;
	}
	/**
	 * 根据查询条件生成共享日志SQL
	 * @param db
	 * @return
	 */
	private Map getShareLogSql(DataBus db)
	{
		String service_targets_id = db.getValue("service_targets_id");
		String service_targets_type = db.getValue("service_targets_type");
		String start_time = db.getValue("created_time");
		String tsname = db.getValue("tsname");
		String status = db.getValue("status");
		String tmptime="";
		boolean isHs=false;
        if (StringUtils.isNotBlank(start_time)) {
			String[] times = DateUtil.getDateRegionByDatePicker(start_time,
					false);
			if (StringUtils.isNotBlank(times[0])) {
				//System.out.println(times[0]);
				tmptime+=(" and service_start_time >= '" + times[0] + ":00'");
				Calendar calendar = Calendar.getInstance();
 				int curyear = calendar.get(Calendar.YEAR);
 				int year = Integer.parseInt(times[0].substring(0,4));
 				System.out.println("cur="+curyear+"---year="+year);
 				if(year<curyear){
 					isHs=true;
 				}
			}else{
				isHs=true;
			}
			if (StringUtils.isNotBlank(times[1])) {
				tmptime+=(" and service_start_time <= '" + times[1] + ":59'");
			}
		}else {
			tmptime=" and service_start_time >=to_char(sysdate-1,'yyyy-mm-dd')|| ' 00:00:00'"
				+" and service_start_time <=to_char(sysdate,'yyyy-mm-dd hh:mm:ss')";
	}
		StringBuffer sql=new StringBuffer();
		StringBuffer countsql=new StringBuffer();
		if(isHs){
			sql.append("select * from (select service_targets_id,service_targets_name,")
				.append(" service_name tsname,service_start_time start_time,")
				.append(" consume_time,record_amount,")
                .append(" (case when RETURN_CODES not in('BAIC0000','BAIC0010','TAX0010') then '失败' else '成功' end)status,") 
                .append(" log_id tsid,'sl' as tablefrom,service_id from share_log where log_type='02'")
                .append(" and service_state is null and return_codes is not null ") 
                .append(tmptime);
			countsql.append("select log_id tsid from share_log where log_type='02'")
            .append(" and service_state is null and return_codes is not null ") 
            .append(tmptime);
			if(StringUtils.isNotBlank(service_targets_id)){
	        	sql.append(" and service_targets_id='")
	        		.append(service_targets_id).append("'");
	        	countsql.append(" and service_targets_id='")
        		.append(service_targets_id).append("'");
	        }
	        if(StringUtils.isNotBlank(service_targets_type)){
	        	sql.append("and targets_type='")
	                .append(service_targets_type).append("'");  
	        	countsql.append("and targets_type='")
                .append(service_targets_type).append("'"); 
	        }
	        
	        if(StringUtils.isNotBlank(tsname)){
	        	sql.append(" and service_name like '%").append(tsname).append("%'");
	        	countsql.append(" and service_name like '%").append(tsname).append("%'");
	        }
	        if(StringUtils.isNotBlank(status)){
	        	if("1".equals(status)){//成功状态
	        		sql.append(" and return_codes in ('BAIC0000','BAIC0010','TAX0010')");
	        		countsql.append(" and return_codes in ('BAIC0000','BAIC0010','TAX0010')");
	        	}else if("0".equals(status)){//失败状态
	        		sql.append(" and return_codes not in ('BAIC0000','BAIC0010','TAX0010')");
	        		countsql.append(" and return_codes not in ('BAIC0000','BAIC0010','TAX0010')");
	        	}
	        }
	        sql.append(" order by service_start_time desc)")
	        	.append(" union all select * from( select service_targets_id,service_targets_name,")
                .append(" service_name tsname,service_start_time start_time,")     
                .append(" to_char(consume_time),record_amount,")  
                .append(" (case when RETURN_CODES not in('BAIC0000','BAIC0010','TAX0010') then '失败' else '成功' end)status,")  
                .append(" log_id tsid,'slh' as tablefrom,service_id from share_log_hs")         
                .append(" where log_type = '02' and service_state is null and return_codes is not null ")         
                .append(tmptime);
	        countsql.append(" union all  select log_id tsid from share_log_hs")         
            .append(" where log_type = '02' and service_state is null and return_codes is not null ")         
            .append(tmptime);
			if(StringUtils.isNotBlank(service_targets_id)){
	        	sql.append(" and service_targets_id='")
	        		.append(service_targets_id).append("'");
	        	countsql.append(" and service_targets_id='")
        		.append(service_targets_id).append("'");
	        }
	        if(StringUtils.isNotBlank(service_targets_type)){
	        	sql.append("and targets_type='")
	                .append(service_targets_type).append("'"); 
	        	countsql.append("and targets_type='")
                .append(service_targets_type).append("'"); 
	        }
	        
	        if(StringUtils.isNotBlank(tsname)){
	        	sql.append(" and service_name like '%").append(tsname).append("%'");
	        	countsql.append(" and service_name like '%").append(tsname).append("%'");
	        }
	        if(StringUtils.isNotBlank(status)){
	        	if("1".equals(status)){//成功状态
	        		sql.append(" and return_codes in ('BAIC0000','BAIC0010','TAX0010')");
	        		countsql.append(" and return_codes in ('BAIC0000','BAIC0010','TAX0010')");
	        	}else if("0".equals(status)){//失败状态
	        		sql.append(" and return_codes not in ('BAIC0000','BAIC0010','TAX0010')");
	        		countsql.append(" and return_codes not in ('BAIC0000','BAIC0010','TAX0010')");
	        	}
	        }
	        sql.append("order by service_start_time desc)");
              
        }else{
        	sql.append("select * from (select service_targets_id,service_targets_name,service_name tsname,")
			.append(" service_start_time start_time,")
			.append(" consume_time,record_amount,")
			.append(" (case when RETURN_CODES not in('BAIC0000','BAIC0010','TAX0010') then '失败' else '成功' end)status,")
			.append(" log_id tsid,'sl' as tablefrom,service_id")
			.append(" from share_log where log_type = '02'")
            .append(" and service_state is null and return_codes is not null ")
        	.append(tmptime);
        	countsql.append("select log_id tsid")
			.append(" from share_log where log_type = '02'")
            .append(" and service_state is null and return_codes is not null ")
        	.append(tmptime);
			if(StringUtils.isNotBlank(service_targets_id)){
	        	sql.append(" and service_targets_id='")
	        		.append(service_targets_id).append("'"); 
	        	countsql.append(" and service_targets_id='")
        		.append(service_targets_id).append("'");    
	        }
	        if(StringUtils.isNotBlank(service_targets_type)){
	        	sql.append("and targets_type='")
	                .append(service_targets_type).append("'"); 
	        	countsql.append("and targets_type='")
                .append(service_targets_type).append("'"); 
	        }
	        
	        if(StringUtils.isNotBlank(tsname)){
	        	sql.append(" and service_name like '%").append(tsname).append("%'");
	        	countsql.append(" and service_name like '%").append(tsname).append("%'");
	        }
	        if(StringUtils.isNotBlank(status)){
	        	if("1".equals(status)){//成功状态
	        		sql.append(" and return_codes in ('BAIC0000','BAIC0010','TAX0010')");
	        		countsql.append(" and return_codes in ('BAIC0000','BAIC0010','TAX0010')");
	        	}else if("0".equals(status)){//失败状态
	        		sql.append(" and return_codes not in ('BAIC0000','BAIC0010','TAX0010')");
	        		countsql.append(" and return_codes not in ('BAIC0000','BAIC0010','TAX0010')");
	        	}
	        }
	        //liz 2.11修改 过滤掉服务对象为空的日志记录
	        countsql.append("  and service_targets_name is not null");
	        sql.append(" and service_targets_name is not null");
	        
        	sql.append(" order by service_start_time desc)");
        }
		
        Map map = new HashMap();
        map.put("sql", sql.toString());
        map.put("countsql", countsql.toString());
		return  map;
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
