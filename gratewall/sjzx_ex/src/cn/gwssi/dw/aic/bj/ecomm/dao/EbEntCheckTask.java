package cn.gwssi.dw.aic.bj.ecomm.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class EbEntCheckTask extends BaseTable
{
   public EbEntCheckTask()
   {
      
   }

   /**
    * ע��SQL���
    */
   protected void register()
   {
	   registerSQLFunction( "queryCheckTaskInfo", DaoFunction.SQL_ROWSET, "��ѯ��վ��Ϣ" );
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
   
   /**
    * Ѳ���������Ϣ
    * */
   public SqlStatement queryCheckTaskInfo(TxnContext request,DataBus inputData) throws TxnException
	{	   
	   SqlStatement stmt = new SqlStatement();
		
		//��ҵid
		String ent_web_site_id = request.getRecord("select-key").getValue("ent_web_site_id");
		
		StringBuffer querySql = new StringBuffer("select * from eb_ent_check_task t1 where 1=1 ");
		
		if(ent_web_site_id !=null && !"".equals(ent_web_site_id)){
			querySql.append(" and  t1.ent_web_site_id ='"+ent_web_site_id+"'");
		}
		
		querySql.append(" order by t1.begin_check_date desc");
		//System.out.println("Ѳ�������"+querySql.toString());
			
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		return stmt;
	}

}
