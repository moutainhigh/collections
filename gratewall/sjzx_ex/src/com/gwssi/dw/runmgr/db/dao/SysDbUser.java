package com.gwssi.dw.runmgr.db.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class SysDbUser extends BaseTable
{
   public SysDbUser()
   {
      
   }

   /**
    * ע��SQL���
    */
   protected void register()
   {
		registerSQLFunction( "updateUserState", DaoFunction.SQL_ROWSET, "���·������״̬" );
		registerSQLFunction( "checkConfig", DaoFunction.SQL_ROWSET, "���·������״̬" );
   }

	public SqlStatement updateUserState(TxnContext request, DataBus inputData){
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt( "update SYS_DB_USER set state='"+
						  request.getRecord("record").getValue("state")+
						  "' WHERE sys_db_user_id='"+
						  request.getRecord("record").getValue("sys_db_user_id")+
						  "'" );
		return stmt;
	}
	
	public SqlStatement checkConfig(TxnContext request, DataBus inputData){
		SqlStatement stmt = new SqlStatement();
		String uId = request.getRecord("record").getValue("sys_db_user_id");
		stmt.addSqlStmt( "SELECT sys_db_config_id FROM sys_db_config WHERE sys_db_user_id='"+uId+"'" );
		return stmt;
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

}
