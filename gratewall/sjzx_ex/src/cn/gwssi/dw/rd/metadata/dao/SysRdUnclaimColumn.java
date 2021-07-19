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
    * ע��SQL���
    */
   protected void register()
   {
	   registerSQLFunction("deleteColumnByDataSource",DaoFunction.SQL_DELETE,"��������Դɾ��δ������ֶ�");
   }

   /**
    * ִ��SQL���ǰ�Ĵ���
    */
   public void prepareExecuteStmt(DaoFunction func, TxnContext request, 
		   DataBus[] inputData, String outputNode) throws TxnException
   {
      
   }

   /**
    * ִ����SQL����Ĵ���
    */
   public void afterExecuteStmt(DaoFunction func, TxnContext request, 
		   DataBus[] inputData, String outputNode) throws TxnException
   {
      
   }
   
   /**
    * ��������Դɾ��δ������ֶ�
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
