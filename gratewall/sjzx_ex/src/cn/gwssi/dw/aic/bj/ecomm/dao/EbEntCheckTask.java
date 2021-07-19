package cn.gwssi.dw.aic.bj.ecomm.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class EbEntCheckTask extends BaseTable
{
   public EbEntCheckTask()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
	   registerSQLFunction( "queryCheckTaskInfo", DaoFunction.SQL_ROWSET, "查询网站信息" );
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
    * 巡查任务表信息
    * */
   public SqlStatement queryCheckTaskInfo(TxnContext request,DataBus inputData) throws TxnException
	{	   
	   SqlStatement stmt = new SqlStatement();
		
		//企业id
		String ent_web_site_id = request.getRecord("select-key").getValue("ent_web_site_id");
		
		StringBuffer querySql = new StringBuffer("select * from eb_ent_check_task t1 where 1=1 ");
		
		if(ent_web_site_id !=null && !"".equals(ent_web_site_id)){
			querySql.append(" and  t1.ent_web_site_id ='"+ent_web_site_id+"'");
		}
		
		querySql.append(" order by t1.begin_check_date desc");
		//System.out.println("巡查任务表："+querySql.toString());
			
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		return stmt;
	}

}
