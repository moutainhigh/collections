package cn.gwssi.dw.aic.bj.ecomm.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class EbEntLicInfo extends BaseTable
{
   public EbEntLicInfo()
   {
      
   }

   /**
    * ע��SQL���
    */
   protected void register()
   {
	   registerSQLFunction( "queryWebLicInfo", DaoFunction.SQL_ROWSET, "��ѯ��վ��Ϣ" );
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
    * ��ѯ��վ���֤��Ϣ
    * */
   public SqlStatement queryWebLicInfo(TxnContext request,DataBus inputData) throws TxnException
	{	   
	   SqlStatement stmt = new SqlStatement();
		
		//��ҵid
		String reg_bus_ent_id = request.getRecord("select-key").getValue("reg_bus_ent_id");
		String ent_web_site_info_id = request.getRecord("select-key").getValue("ent_web_site_info_id");
		
		StringBuffer querySql = new StringBuffer("select * from eb_ent_lic_info t1 where 1=1 ");
		
		
		if(reg_bus_ent_id !=null && !"".equals(reg_bus_ent_id)){
			querySql.append(" and  t1.reg_bus_ent_id ='"+reg_bus_ent_id+"'");
		}
		if(ent_web_site_info_id !=null && !"".equals(ent_web_site_info_id)){
			querySql.append(" and  t1.ent_web_site_info_id ='"+ent_web_site_info_id+"'");
		}
			
		System.out.println("��ѯ��վ���֤��Ϣ��"+querySql.toString());
			
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		return stmt;
	}

}
