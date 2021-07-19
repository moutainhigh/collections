package com.gwssi.share.rule.dao;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

public class ShareServiceRule extends BaseTable
{
   public ShareServiceRule()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
	   registerSQLFunction("getRuleByServIDAndWeek", DaoFunction.SQL_SELECT, "查询列表");
	   registerSQLFunction("getServInfoByLog", DaoFunction.SQL_SELECT, "查询共享服务访问次数和记录总数");
	   registerSQLFunction("getServState", DaoFunction.SQL_SELECT, "查询共享服务的状态");
	   registerSQLFunction("isExistServ", DaoFunction.SQL_SELECT, "查询是否存在共享服务");
	   registerSQLFunction("getServiceIP", DaoFunction.SQL_SELECT, "查询服务对象绑定IP");
	   registerSQLFunction("delByService_ID", DaoFunction.SQL_DELETE, "根据服务ID删除");
   }

   /**
    * 执行SQL语句前的处理
    */
   public void prepareExecuteStmt(DaoFunction func, TxnContext request, 
		   DataBus[] inputData, String outputNode) throws TxnException
   {
      
   }
   
   /**
    * 
    * getServInfoByLog 查询共享服务当天访问的总次数和总记录数 
    * @param request
    * @param inputData
    * @return        
    * SqlStatement       
    * @Exception 异常对象    
    * @since  CodingExample　Ver(编码范例查看) 1.1
    */
   public SqlStatement getServInfoByLog(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String servID = request.getRecord("select-key").getValue("service_id");
		sqlBuffer.append("select count(1) times, sum(s.record_amount) amount")
			.append(" from (select t.* from share_log t ")
			.append(" where t.service_id = '").append(servID).append("' ")
			.append(" and t.service_start_time > to_char(sysdate, 'yyyy-mm-dd')")
			.append(" and t.service_end_time < to_char(sysdate+1, 'yyyy-mm-dd'))")
			.append(" s");
		
		stmt.addSqlStmt(sqlBuffer.toString());
		return stmt;
	}
   
   /**
    * 
    * getRuleByServIDAndWeek 根据共享服务Id和星期几查询当天的该共享服务的限制条件
    * @param request
    * @param inputData
    * @return        
    * SqlStatement       
    * @Exception 异常对象    
    * @since  CodingExample　Ver(编码范例查看) 1.1
    */
   public SqlStatement getRuleByServIDAndWeek(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String servID = request.getRecord("select-key").getValue("service_id");
		String week = request.getRecord("select-key").getValue("week");
		StringBuffer sqlstr = new StringBuffer(); 
		sqlstr.append("select r.service_id, r.start_time, r.end_time, r.times_day, r.total_count_day ")
			.append(" from share_service_rule r ")
			.append(" where r.service_id = '").append(servID).append("' ")
			.append(" and r.week = '").append(week).append("'");
		stmt.addSqlStmt(sqlstr.toString());
		//stmt.setCountStmt("SELECT COUNT(1) FROM ("+sql+")");
		return stmt;
	}
   
   /**
    * getServState 根据服务编号获取服务状态   
    * @param request
    * @param inputData
    * @return        
    * SqlStatement       
    * @Exception 异常对象    
    * @since  CodingExample　Ver(编码范例查看) 1.1
    */
   public SqlStatement getServState(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String servID = request.getRecord("primary-key").getValue("service_id");
		String sql="SELECT t.service_id, t.service_state FROM share_service t ";
		if(StringUtils.isNotEmpty(servID)){
			sql += " where t.service_id = '" + servID + "'";
		}
		stmt.addSqlStmt(sql);
		//stmt.setCountStmt("SELECT COUNT(1) FROM ("+sql+")");
		return stmt;
	}
   
   public SqlStatement getServiceIP(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String servID = request.getRecord("primary-key").getValue("service_id");
		StringBuffer sql = new StringBuffer();
		sql.append("select t.is_bind_ip, t.ip ")
			.append(" from res_service_targets t, share_service s ");
		if(StringUtils.isNotEmpty(servID)){
			sql.append(" where s.service_id = '").append(servID)
				.append("' and s.service_targets_id = t.service_targets_id");
		}
		
		stmt.addSqlStmt(sql.toString());
		//stmt.setCountStmt("SELECT COUNT(1) FROM ("+sql+")");
		return stmt;
	}
   
   public SqlStatement isExistServ(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String servID = request.getRecord("primary-key").getValue("service_id");
		String sql="SELECT count(1) num FROM share_service t ";
		if(StringUtils.isNotEmpty(servID)){
			sql += " where t.service_id = '" + servID + "'";
		}
		stmt.addSqlStmt(sql);
		//stmt.setCountStmt("SELECT COUNT(1) FROM ("+sql+")");
		return stmt;
	}
   
   
   /**
    * 
    * delByService_ID(根据服务ID删除)    
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
	public SqlStatement delByService_ID( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String service_id = request.getRecord("primary-key").getValue("service_id");
		stmt.addSqlStmt( "delete from share_service_rule where service_id='"+service_id+"'" );
		return stmt;
	}
	
   /*
    * 

    */
   
   /**
    * 执行完SQL语句后的处理
    */
   public void afterExecuteStmt(DaoFunction func, TxnContext request, 
		   DataBus[] inputData, String outputNode) throws TxnException
   {
      
   }

}
