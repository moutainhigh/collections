package com.gwssi.dw.aic.bj.exc.que.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class ExcQueAuth extends BaseTable
{
   public ExcQueAuth()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
      this.registerSQLFunction("queryExcQueAuthBase_detail", DaoFunction.SQL_ROWSET, "查询编办基本信息");
      
   }
   
   /**
    * 查询编办基本信息
    * @creator caiwd
    * @createtime 2008-8-29
    *             下午04:03:10
    * @param request
    * @param inputData
    * @return
    * @throws TxnException
    *
    */
   public SqlStatement queryExcQueAuthBase_detail(TxnContext request,
			DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();

		String exc_que_reg_id = request.getString("select-key:exc_que_reg_id");

		String querySql ="SELECT a.reg_no,a.organ_code,a.ent_name,a.dom,a.corp_rpt," +
				"a.ent_state,a.est_date,a.ent_type_code,a.op_scope,a.ent_type,a.industry_code," +
				"a.distr_code,b.reg_cap,b.supe_org,b.exa_auth,b.postal_code,b.card_date,b.chan_date,b.revok_date " +
				"FROM exc_que_reg a left join exc_que_auth b on a.reg_no = b.reg_no " +
				"WHERE a.exc_que_reg_id ='"+exc_que_reg_id+"' " ;
		

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
