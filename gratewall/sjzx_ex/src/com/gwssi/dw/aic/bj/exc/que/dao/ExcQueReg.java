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
    * 注册SQL语句
    */
   protected void register()
   {
      this.registerSQLFunction("viewExcQueReg_DetailAndParameters", DaoFunction.SQL_SELECT, "框架和data_sou为3、4以外基本信息查询");
      this.registerSQLFunction("viewExcQueReg_Detail", DaoFunction.SQL_SELECT, "框架和data_sou为3、4以外基本信息查询");
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
    *             上午10:39:54
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

}
