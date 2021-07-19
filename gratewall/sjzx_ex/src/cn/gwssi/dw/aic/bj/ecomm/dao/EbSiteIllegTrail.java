package cn.gwssi.dw.aic.bj.ecomm.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class EbSiteIllegTrail extends BaseTable
{
   public EbSiteIllegTrail()
   {
      
   }

   /**
    * ע��SQL���
    */
   protected void register()
   {
	   registerSQLFunction( "queryIllegTrail", DaoFunction.SQL_ROWSET, "��ѯΥ������" );
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
    * Υ������
    * */
   public SqlStatement queryIllegTrail(TxnContext request,DataBus inputData) throws TxnException
	{	   
	   SqlStatement stmt = new SqlStatement();
		
		//��ҵid
		String reg_bus_ent_id = request.getRecord("select-key").getValue("reg_bus_ent_id");
		String chr_id = request.getRecord("select-key").getValue("chr_id");
		
		StringBuffer querySql = new StringBuffer("select * from eb_site_illeg_trail t1 where 1=1");
		
		
		if(reg_bus_ent_id !=null && !"".equals(reg_bus_ent_id)){
			querySql.append(" and t1.reg_bus_ent_id ='"+reg_bus_ent_id+"'");
		}
		
		if(chr_id !=null && !"".equals(chr_id)){
			querySql.append(" and  t1.chr_id ='"+chr_id+"'");
		}
			
		//System.out.println("Υ��������"+querySql.toString());
			
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		return stmt;
	}

}
