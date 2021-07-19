package com.gwssi.sysmgr.priv.func.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.iface.DaoFunction;

public class Functxn extends BaseTable
{
   public Functxn()
   {
      
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

   public int batchAddTxnList( TxnContext request, String inputNode, 
      String outputNode ) throws TxnException
   {
	   // 删除所有记录
	   executeFunction( "delete func txns", request, "select-key", null );
	   
	   // 增加记录
	   return executeFunction( "insert one functxn", request, inputNode, outputNode );
   }
}
