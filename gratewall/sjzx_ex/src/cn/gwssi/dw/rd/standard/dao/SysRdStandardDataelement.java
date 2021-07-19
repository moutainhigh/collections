package cn.gwssi.dw.rd.standard.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class SysRdStandardDataelement extends BaseTable
{
   public SysRdStandardDataelement()
   {
      
   }

   /**
    * ע��SQL���
    */
   protected void register()
   {
	   registerSQLFunction( "selectonedataelement", DaoFunction.SQL_SELECT, "��ѯ�����¼��" );
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
    * ajax-����Ԫ
    * */
   public SqlStatement selectonedataelement(TxnContext request,DataBus inputData) throws TxnException
	{	   
	   
		SqlStatement stmt = new SqlStatement();
		
		String identifier = request.getRecord("select-key").getValue("identifier");
		
		StringBuffer querySql = new StringBuffer("select sys_rd_standard_dataelement_id,standard_category,identifier,cn_name,en_name,column_nane,data_type,data_length,data_format,value_domain,jc_standar_codeindex,representation,unit,synonyms,version,memo from sys_rd_standard_dataelement");
		if(identifier!=null && !"".equals(identifier)){
			querySql.append(" where identifier = '"+identifier+"'");
		}
		
		
		stmt.addSqlStmt(querySql.toString());
		//stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
		
		return stmt;
	}
}
