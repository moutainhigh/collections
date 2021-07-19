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
    * 注册SQL语句
    */
   protected void register()
   {
	   registerSQLFunction("load_org" , DaoFunction.SQL_ROWSET, "获取机构列表");
	   registerSQLFunction("getSjjgName" , DaoFunction.SQL_ROWSET, "获取上级机构名称");
	   registerSQLFunction("deleteAllUser" , DaoFunction.SQL_DELETE, "删除机构下用户");	   
   }

   /**
    * 执行SQL语句前的处理
    */
   public SqlStatement load_org( TxnContext request, DataBus inputData )
   {   
	   
          SqlStatement stmt=new SqlStatement();
	
          String sql = request.getConttrolData().getValue("sqlSelect");
	     stmt.addSqlStmt(sql);
	     return stmt;
   }
   /**
    * 执行SQL语句前的处理
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
    * 执行完SQL语句后的处理
    */
   public void afterExecuteStmt(DaoFunction func, TxnContext request, 
		   DataBus[] inputData, String outputNode) throws TxnException
   {
      
   }

}
