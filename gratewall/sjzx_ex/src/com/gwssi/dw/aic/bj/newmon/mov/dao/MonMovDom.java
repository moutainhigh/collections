package com.gwssi.dw.aic.bj.newmon.mov.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class MonMovDom extends BaseTable
{
   public MonMovDom()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
      this.registerSQLFunction("queryMonMovDom_List", DaoFunction.SQL_ROWSET, "异地经营企业信息列表");
      this.registerSQLFunction("viewMonMovDom_Detail", DaoFunction.SQL_SELECT, "查看异地经营详细信息");
   }
   
   /**
    * 查看异地经营详细信息
    * MonMovDom:viewMonMovDom_Detail 
    * @creater - caiwd
    * @creatertime - Nov 20, 2008
    * @param context
    * @param inputData
    * @return
    * @throws TxnException
    * @returnType SqlStatement
    */
   public SqlStatement viewMonMovDom_Detail(TxnContext context,DataBus inputData) throws TxnException{
	   SqlStatement stmt = new SqlStatement();
	   DataBus dataBus = context.getRecord("select-key");
	   String mon_mov_dom_id = dataBus.getString("mon_mov_dom_id");
	   /*SELECT a.*,b.che_type FROM mon_mov_dom a LEFT JOIN mon_ent_tsk b ON a.crt_tsk_id=b.mon_mis_id WHERE a.mon_mov_dom_id =''*/
	   String querySql = "SELECT a.*,b.che_type as tsk_che_type,b.che_state FROM mon_mov_dom a" +
	   		" LEFT JOIN mon_ent_tsk b ON a.crt_tsk_id=b.mon_mis_id" +
	   		" WHERE a.mon_mov_dom_id ='" +mon_mov_dom_id+
	   		"'";
	   stmt.addSqlStmt(querySql);
	   return stmt;
   }
   
   /**
    * 异地经营企业信息列表
    * MonMovDom:queryMonMovDom_List 
    * @creater - caiwd
    * @creatertime - Nov 20, 2008
    * @param context
    * @param inputDate
    * @return
    * @returnType SqlStatement
    */
   public SqlStatement queryMonMovDom_List(TxnContext context,DataBus inputDate) throws TxnException{
	   SqlStatement stmt = new SqlStatement();
	   DataBus dataBus = context.getRecord("select-key");
	   String ent_title = dataBus.getValue("ent_title"); 
	   String reg_bus_ent_id = dataBus.getValue("reg_bus_ent_id");
	   
		/*
		 * SELECT
		 * s.reg_date,s.mon_mov_dom_id,s.main_id,s.prm_set_date,s.now_op_loc,s.reinsp_res,s.grid_id,s.ent_title,t.grid_name,t.mon_buss_grid_id
		 * FROM (select
		 * a.mon_mov_dom_id,a.main_id,a.prm_set_date,a.now_op_loc,a.reinsp_res,a.grid_id,b.ent_title
		 * from mon_mov_dom a,mon_main_basic b where a.main_id=b.main_id and
		 * b.ent_title='') s LEFT JOIN mon_buss_grid t ON s.grid_id=t.grid_id
		 */
	   
	   
	   String querySql = "SELECT" +
	   		" s.reg_date,s.mon_mov_dom_id,s.main_id,s.prm_set_date,s.now_op_loc,s.reinsp_res,s.grid_id,s.ent_title" +
	   		" ,t.grid_name,t.mon_buss_grid_id" +
	   		"  FROM (select " +
	   		" a.reg_date,a.mon_mov_dom_id,a.main_id,a.prm_set_date,a.now_op_loc,a.reinsp_res,a.grid_id,b.ent_title " +
	   		" from mon_mov_dom a,mon_main_basic b where a.main_id=b.main_id and " +
	   		"  b.reg_bus_ent_id='" +reg_bus_ent_id+
	   		"') s" +
	   		" LEFT JOIN mon_buss_grid t ON s.grid_id=t.grid_id ";
	   
	   String countSql = "SELECT count(1) FROM ("+querySql+")";
	   querySql = querySql+"ORDER BY s.reg_date,s.mon_mov_dom_id DESC";
	   
	   
	   stmt.addSqlStmt(querySql);
	   stmt.setCountStmt(countSql);
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
