package com.gwssi.dw.aic.bj.exc.que.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class ExcQueCiv extends BaseTable
{
   public ExcQueCiv()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
	   this.registerSQLFunction("queryExcQueCivBase_detail", DaoFunction.SQL_ROWSET, "查询民政事业单位基本信息");
   }
   
   public SqlStatement queryExcQueCivBase_detail(TxnContext request,
			DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();

		/*
		 * select
		 * a.reg_no,a.organ_code,a.ent_name,a.dom,a.corp_rpt,a.ent_state,a.est_date,a.ent_type_code,a.op_scope,a.ent_type,a.industry_code,a.distr_code,a.civ_id,b.reg_cap,b.supe_org,b.card_org,b.card_date,b.chan_date,b.revok_date
		 * FROM exc_que_reg a left join exc_que_civ b on a.civ_id = b.civ_id
		 * WHERE a.exc_que_reg_id ='F0000000000000100000000005475959'
		 */
		
		String exc_que_reg_id = request.getString("select-key:exc_que_reg_id");

		String querySql ="SELECT a.reg_no,a.organ_code,a.ent_name,a.dom,a.corp_rpt," +
				"a.ent_state,a.est_date,a.ent_type_code,a.op_scope,a.ent_type,a.industry_code," +
				"a.distr_code,a.civ_id,b.reg_cap,b.supe_org,b.card_org,b.card_date,b.chan_date,b.revok_date"+
				" FROM exc_que_reg a left join exc_que_civ b on a.civ_id = b.civ_id " +
				" WHERE a.exc_que_reg_id ='"+exc_que_reg_id+"' " ;
		
		log.debug("querySql:" + querySql);

		stmt.addSqlStmt(querySql);
		//stmt.setCountStmt("select count(1) from (" + querySql + ")");
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
