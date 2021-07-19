package cn.gwssi.dw.rd.standard.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class SysRdStandard extends BaseTable
{
   public SysRdStandard()
   {
      
   }

   /**
    * ע��SQL���
    */
   protected void register()
   {
	   registerSQLFunction( "queryMaxSort", DaoFunction.SQL_SELECT, "��ȡ��������" );
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
   
   public SqlStatement queryMaxSort(TxnContext request,DataBus inputData) throws TxnException
	{	 

		SqlStatement stmt = new SqlStatement();
		String sql = "";
		StringBuffer stringBfSql = new StringBuffer("select max(sort) from sys_rd_standard");	
		sql = stringBfSql.toString();
		stmt.addSqlStmt(sql);
		return stmt;

	}

}