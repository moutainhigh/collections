package cn.gwssi.dw.aic.bj.ecomm.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class EbSiteAdditInfo extends BaseTable
{
   public EbSiteAdditInfo()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
	   registerSQLFunction( "viewSiteAdditInfo", DaoFunction.SQL_ROWSET, "查看建站信息" );
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
    * 查看建站信息
    * */
   public SqlStatement viewSiteAdditInfo(TxnContext request,DataBus inputData) throws TxnException
	{	   
	   SqlStatement stmt = new SqlStatement();
		
		//企业id
		String reg_bus_ent_id = request.getRecord("select-key").getValue("reg_bus_ent_id");
		//reg_bus_ent_id = "F26A4001A1084B0287580CFAF3D48609";
		
		StringBuffer querySql = new StringBuffer("select * from eb_site_addit_info t1 ");
		
		
			if(reg_bus_ent_id !=null && !"".equals(reg_bus_ent_id)){
				querySql.append(" where  t1.reg_bus_ent_id ='"+reg_bus_ent_id+"'");
			}
			
		System.out.println("查看建站信息："+querySql.toString());
			
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		return stmt;
	}

}
