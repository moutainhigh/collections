package com.gwssi.dw.aic.bj.newmon.ent.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class MonEntTsk extends BaseTable
{
   public MonEntTsk()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
      this.registerSQLFunction("queryMonEntTsk_List", DaoFunction.SQL_ROWSET, "根据主体名称查询检查任务列表");
      this.registerSQLFunction("viewMonEntTsk_Detail", DaoFunction.SQL_SELECT, "查看检查任务详细信息");
      this.registerSQLFunction("queryMonBussQusAndType_List", DaoFunction.SQL_ROWSET, "查看检查任务问题列表");
 
   }
   
   /**
    * 查看检查任务问题列表
    * MonEntTsk:queryMonBussQusAndType_List 
    * @creater - caiwd
    * @creatertime - Nov 20, 2008
    * @param context
    * @param inputData
    * @return
    * @throws TxnException
    * @returnType SqlStatement
    */
   public SqlStatement queryMonBussQusAndType_List(TxnContext context,DataBus inputData)throws TxnException{
	   SqlStatement stmt = new SqlStatement();
	   DataBus dataBus = context.getRecord("record");
	   String mon_mis_id = dataBus.getString("mon_mis_id");

		/*
		 * SELECT
		 * b.mon_buss_qus_id,a.tsk_que_link,b.item_id,b.item_name,b.qus_no,b.qus_to_date,a.mon_mis_id,c.quest_type
		 * FROM tsk_que_link a left join mon_buss_qus b on a.qus_id=b.qus_id
		 * left join quest_type c on a.qus_id=c.qus_id where
		 * a.mon_mis_id='N02003661622'
		 */
	   String querySql = "SELECT b.mon_buss_qus_id,a.tsk_que_link,b.item_id,b.item_name" +
	   		",b.qus_no,b.qus_to_date,a.mon_mis_id,c.quest_type" +
	   		" FROM tsk_que_link a LEFT JOIN mon_buss_qus b ON a.qus_id=b.qus_id" +
	   		" LEFT JOIN quest_type c ON a.qus_id=c.qus_id" +
	   		" WHERE a.mon_mis_id='"+mon_mis_id+"'";
	   String countSql = "SELECT count(1) FROM tsk_que_link t WHERE t.mon_mis_id='"+mon_mis_id+"'";
	   stmt.addSqlStmt(querySql);
	   stmt.setCountStmt(countSql);
	   return stmt;
   }
   
   
 
   /**
    * 查看检查任务详细信息
    * MonEntTsk:viewMonEntTsk_Detail 
    * @creater - caiwd
    * @creatertime - Nov 20, 2008
    * @param context
    * @param inputData
    * @return
    * @throws TxnException
    * @returnType SqlStatement
    */
   public SqlStatement viewMonEntTsk_Detail(TxnContext context,DataBus inputData)throws TxnException{
	   SqlStatement stmt = new SqlStatement();
	   DataBus dataBus = context.getRecord("select-key");
	   String mon_ent_tsk_id = dataBus.getString("mon_ent_tsk_id");
	   String querySql = "SELECT a.*,b.user_name,b.mon_user_persn_id  FROM mon_ent_tsk a " +
	   		" LEFT JOIN mon_user_persn b ON a.res_che = b.user_persn_id " +
	   		" WHERE a.mon_ent_tsk_id='"+mon_ent_tsk_id+"'";

	   stmt.addSqlStmt(querySql);	   
	   return stmt;
   }
   
   /**
    * 根据主体名称查询检查任务列表
    * MonEntTsk:queryMonEntTsk_List 
    * @creater - caiwd
    * @creatertime - Nov 20, 2008
    * @param context
    * @param inputData
    * @return
    * @throws TxnException
    * @returnType SqlStatement
    */
   public SqlStatement queryMonEntTsk_List(TxnContext context,DataBus inputData) throws TxnException{
	   SqlStatement stmt = new SqlStatement();
	   DataBus dataBus = context.getRecord("select-key");
	   //String ent_title = dataBus.getValue("ent_title");
	   String reg_bus_ent_id = dataBus.getValue("reg_bus_ent_id");

	   String querySql = null;
	   String countSql = null;
		/*
		 * SELECT
		 * a.mon_ent_tsk_id,a.che_com_time,a.che_res,a.che_type,a.che_state,b.mon_main_basic_id,b.main_id
		 * FROM mon_main_basic b RIGHT JOIN mon_ent_tsk a ON a.main_id =
		 * b.main_id WHERE b.ent_title=''
		 */
	   querySql = "SELECT" +
	   		" a.mon_ent_tsk_id,a.che_com_time,a.che_res,a.che_type,a.che_state" +
	   		" ,b.mon_main_basic_id,b.main_id" +
	   		" FROM mon_main_basic b  RIGHT JOIN mon_ent_tsk a" +
	   		" ON a.main_id = b.main_id " ;
	   		if(reg_bus_ent_id!=null&&!"".equals(reg_bus_ent_id)){
	   		 querySql =  querySql+ " WHERE b.reg_bus_ent_id='"+reg_bus_ent_id+"'";
	   		}
	   countSql = "SELECT count(1) FROM ("+querySql+")";
	   querySql = querySql+ " ORDER BY a.che_com_time,a.mon_ent_tsk_id DESC";
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
