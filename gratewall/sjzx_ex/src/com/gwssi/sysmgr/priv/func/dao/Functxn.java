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

   public int batchAddTxnList( TxnContext request, String inputNode, 
      String outputNode ) throws TxnException
   {
	   // ɾ�����м�¼
	   executeFunction( "delete func txns", request, "select-key", null );
	   
	   // ���Ӽ�¼
	   return executeFunction( "insert one functxn", request, inputNode, outputNode );
   }
}
