package cn.gwssi.dw.aic.bj.ecomm.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class EbSiteAdditInfo extends BaseTable
{
   public EbSiteAdditInfo()
   {
      
   }

   /**
    * ע��SQL���
    */
   protected void register()
   {
	   registerSQLFunction( "viewSiteAdditInfo", DaoFunction.SQL_ROWSET, "�鿴��վ��Ϣ" );
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
    * �鿴��վ��Ϣ
    * */
   public SqlStatement viewSiteAdditInfo(TxnContext request,DataBus inputData) throws TxnException
	{	   
	   SqlStatement stmt = new SqlStatement();
		
		//��ҵid
		String reg_bus_ent_id = request.getRecord("select-key").getValue("reg_bus_ent_id");
		//reg_bus_ent_id = "F26A4001A1084B0287580CFAF3D48609";
		
		StringBuffer querySql = new StringBuffer("select * from eb_site_addit_info t1 ");
		
		
			if(reg_bus_ent_id !=null && !"".equals(reg_bus_ent_id)){
				querySql.append(" where  t1.reg_bus_ent_id ='"+reg_bus_ent_id+"'");
			}
			
		System.out.println("�鿴��վ��Ϣ��"+querySql.toString());
			
		stmt.addSqlStmt(querySql.toString());
		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		return stmt;
	}

}
