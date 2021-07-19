package com.gwssi.sysmgr.org.dao;

import com.gwssi.sysmgr.org.vo.VoXt_zzjg_jg;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class Xt_zzjg_jg extends BaseTable
{
   public Xt_zzjg_jg()
   {
      
   }

   /**
    * ע��SQL���
    */
   protected void register()
   {
	   registerSQLFunction("load_org" , DaoFunction.SQL_ROWSET, "��ȡ�����б�");
	   registerSQLFunction("getSjjgName" , DaoFunction.SQL_ROWSET, "��ȡ�ϼ���������");
	   registerSQLFunction("deleteAllUser" , DaoFunction.SQL_DELETE, "ɾ���������û�");	   
   }

   /**
    * ִ��SQL���ǰ�Ĵ���
    */
   public SqlStatement load_org( TxnContext request, DataBus inputData )
   {   
	   
          SqlStatement stmt=new SqlStatement();
	
          String sql = request.getConttrolData().getValue("sqlSelect");
	     stmt.addSqlStmt(sql);
	     return stmt;
   }
   /**
    * ִ��SQL���ǰ�Ĵ���
    */
   public SqlStatement getSjjgName( TxnContext request, DataBus inputData )
   {   
	   
         SqlStatement stmt=new SqlStatement();	
         DataBus db = request.getRecord("record");
 		 String jgid = db.getValue("jgid_pk");
         String sql = "select sjjgid_fk,jgmc from xt_zzjg_jg where jgid_pk='"+jgid+"'" ;
	     stmt.addSqlStmt(sql);
	     return stmt;
   } 
   public SqlStatement deleteAllUser( TxnContext request, DataBus inputData )
   {   	   
         SqlStatement stmt=new SqlStatement();	
         DataBus db = request.getRecord("primary-key");
 		 String jgid = db.getValue("jgid_pk");
         String sql = "delete from xt_zzjg_yh_new where jgid_fk='"+jgid+"'" ;
	     stmt.addSqlStmt(sql);
	     return stmt;
   }    
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

}
