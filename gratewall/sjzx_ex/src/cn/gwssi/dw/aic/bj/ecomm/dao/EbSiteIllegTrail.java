package cn.gwssi.dw.aic.bj.ecomm.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class EbSiteIllegTrail extends BaseTable
{
   public EbSiteIllegTrail()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
	   registerSQLFunction( "queryIllegTrail", DaoFunction.SQL_ROWSET, "查询违法线索" );
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
    * 违法线索
    * */
   public SqlStatement queryIllegTrail(TxnContext request,DataBus inputData) throws TxnException
	{	   
	   SqlStatement stmt = new SqlStatement();
		
		//企业id
		String reg_bus_ent_id = request.getRecord("select-key").getValue("reg_bus_ent_id");
		String chr_id = request.getRecord("select-key").getValue("chr_id");
		
		StringBuffer querySql = new StringBuffer("select * from eb_site_illeg_trail t1 where 1=1");
		
		
		if(reg_bus_ent_id !=null && !"".equals(reg_bus_ent_id)){
			querySql.append(" and t1.reg_bus_ent_id ='"+reg_bus_ent_id+"'");
		}
		
		if(chr_id !=null && !"".equals(chr_id)){
			querySql.append(" and  t1.chr_id ='"+chr_id+"'");
		}
			
		//System.out.println("违法线索："+querySql.toString());
			
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		return stmt;
	}

}
