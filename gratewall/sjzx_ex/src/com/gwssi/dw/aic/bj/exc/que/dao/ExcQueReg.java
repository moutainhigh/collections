package com.gwssi.dw.aic.bj.exc.que.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class ExcQueReg extends BaseTable
{
   public ExcQueReg()
   {
      
   }

   /**
    * ע��SQL���
    */
   protected void register()
   {
      this.registerSQLFunction("viewExcQueReg_DetailAndParameters", DaoFunction.SQL_SELECT, "��ܺ�data_souΪ3��4���������Ϣ��ѯ");
      this.registerSQLFunction("viewExcQueReg_Detail", DaoFunction.SQL_SELECT, "��ܺ�data_souΪ3��4���������Ϣ��ѯ");
   }
   
   /**
    * 
    * @param request
    * @param inputData
    * @return
    * @throws TxnException
    */
   public SqlStatement viewExcQueReg_Detail(TxnContext request,
			DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();

		String exc_que_reg_id = request.getString("select-key:exc_que_reg_id");

		/*
		 * select
		 * t.exc_que_reg_id,t.ent_name,t.dom,t.corp_rpt,t.data_sou,t.reg_no,t.civ_id,t.organ_code
		 * from exc_que_reg t where t.exc_que_reg_id=''
		 */

		String querySql ="SELECT * " +
				" FROM exc_que_reg t WHERE t.exc_que_reg_id='"+exc_que_reg_id+"'" ;

		stmt.addSqlStmt(querySql);
		return stmt;
	}
   
   /**
    * test
    * @creator caiwd
    * @createtime 2008-8-31
    *             ����10:39:54
    * @param request
    * @param inputData
    * @return
    * @throws TxnException
    *
    */
   public SqlStatement viewExcQueReg_DetailAndParameters(TxnContext request,
			DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();

		String exc_que_reg_id = request.getString("select-key:exc_que_reg_id");

		/*
		 * select
		 * t.exc_que_reg_id,t.ent_name,t.dom,t.corp_rpt,t.data_sou,t.reg_no,t.civ_id,t.organ_code
		 * from exc_que_reg t where t.exc_que_reg_id=''
		 */

		String querySql ="SELECT t.exc_que_reg_id,t.ent_name,t.dom,t.corp_rpt," +
				"t.data_sou,t.reg_no,t.civ_id,t.organ_code" +
				" FROM exc_que_reg t WHERE t.exc_que_reg_id='"+exc_que_reg_id+"'" ;

		log.debug("querySql:" + querySql);

		stmt.addSqlStmt(querySql);
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
