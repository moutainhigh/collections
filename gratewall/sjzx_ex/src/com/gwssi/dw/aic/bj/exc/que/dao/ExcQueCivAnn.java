package com.gwssi.dw.aic.bj.exc.que.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class ExcQueCivAnn extends BaseTable
{
   public ExcQueCivAnn()
   {
      
   }

   /**
    * ע��SQL���
    */
   protected void register()
   {
	   this.registerSQLFunction("queryExcQueCivAnn_list", DaoFunction.SQL_ROWSET, "������ҵ��λ�����Ϣ");
   }
   
   /**
    * ������ҵ��λ�����Ϣ
    * @creator caiwd
    * @createtime 2008-8-31
    *             ����11:59:58
    * @param request
    * @param inputData
    * @return
    * @throws TxnException
    *
    */
   public SqlStatement queryExcQueCivAnn_list(TxnContext request,
			DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();

		String civ_id = request.getString("select-key:civ_id");

		log.debug( "civ_id:" + civ_id);

		String querySql ="SELECT t.* FROM exc_que_civ_ann t WHERE t.civ_id ='"+civ_id+"' ORDER BY t.an_che_year desc" ;
		String countSql ="SELECT count(1) FROM exc_que_civ_ann t WHERE t.civ_id ='"+civ_id+"' ORDER BY t.an_che_year desc" ;
		

		log.debug("querySql:" + querySql);

		stmt.addSqlStmt(querySql);
		stmt.setCountStmt(countSql);
		return stmt;
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

}
