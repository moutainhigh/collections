package com.gwssi.dw.aic.bj.exc.que.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class ExcQueAuthAnn extends BaseTable
{
   public ExcQueAuthAnn()
   {
      
   }

   /**
    * ע��SQL���
    */
   protected void register()
   {
      this.registerSQLFunction("queryExcQueAuthAnn_list", DaoFunction.SQL_ROWSET, "�����ҵ�����Ϣ");
   }
   
   /**
    * �����ҵ�����Ϣ
    * @creator caiwd
    * @createtime 2008-8-31
    *             ����09:48:15
    * @param request
    * @param inputData
    * @return
    * @throws TxnException
    *
    */
   public SqlStatement queryExcQueAuthAnn_list(TxnContext request,
			DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();

		String reg_no = request.getString("select-key:reg_no");

		log.debug( ";organ_code:" + reg_no);

		String querySql ="SELECT t.* FROM exc_que_auth_ann t WHERE t.reg_no ='"+reg_no+"' ORDER BY t.an_che_year desc" ;
		String countSql ="SELECT count(1) FROM exc_que_auth_ann t WHERE t.reg_no ='"+reg_no+"' ORDER BY t.an_che_year desc" ;

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
