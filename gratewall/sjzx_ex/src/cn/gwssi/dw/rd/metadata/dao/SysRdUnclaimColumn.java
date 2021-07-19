package cn.gwssi.dw.rd.metadata.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class SysRdUnclaimColumn extends BaseTable
{
   public SysRdUnclaimColumn()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
	   registerSQLFunction("deleteColumnByDataSource",DaoFunction.SQL_DELETE,"根据数据源删除未认领表字段");
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
    * 根据数据源删除未认领表字段
    * @param request
    * @param inputData
    * @return
    * @throws TxnException
    */
   public SqlStatement deleteColumnByDataSource(TxnContext request,DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		String sys_rd_data_source_id = inputData.getValue("sys_rd_data_source_id");
		if(sys_rd_data_source_id!=null && !"".equals(sys_rd_data_source_id)){
			stmt.addSqlStmt("delete sys_rd_unclaim_column where sys_rd_data_source_id='" + sys_rd_data_source_id + "'");
		}
		return stmt;
	}

}
