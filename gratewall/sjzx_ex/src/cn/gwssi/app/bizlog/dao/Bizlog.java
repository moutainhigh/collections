package cn.gwssi.app.bizlog.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class Bizlog extends BaseTable
{
   public Bizlog()
   {
      
   }

   /**
    * ע��SQL���
    */
   protected void register()
   {
	   registerSQLFunction("deleteSjSxsj",DaoFunction.SQL_DELETE,"ɾ����������");
	   registerSQLFunction("deleteSjZbsj",DaoFunction.SQL_DELETE,"ɾ������");
	   registerSQLFunction("deleteSjSy",DaoFunction.SQL_DELETE,"ɾ������");
   }
   
    public SqlStatement deleteSjSxsj ( TxnContext request, DataBus inputData ) throws TxnException{
		SqlStatement stmt = new SqlStatement();
		String sql = null;
		sql = "delete from gz_zbcc_sxsj where sj_id in (select a.sj_id from  gz_zbcc_sj a left join gz_zbcc_ly b on a.sj_id = b.sj_id where b.ly_xh is null)";
		stmt.addSqlStmt(sql);
		return stmt;
	}
	
	public SqlStatement deleteSjZbsj ( TxnContext request, DataBus inputData ) throws TxnException{
		SqlStatement stmt = new SqlStatement();
		String sql = null;
		sql = "delete from gz_zbcc_sj where sj_id in (select a.sj_id from  gz_zbcc_sj a left join gz_zbcc_ly b on a.sj_id = b.sj_id where b.ly_xh is null)";
		stmt.addSqlStmt(sql);
		return stmt;
	}
	
	public SqlStatement deleteSjSy ( TxnContext request, DataBus inputData ) throws TxnException{
		SqlStatement stmt = new SqlStatement();
		String sql = null;
		sql = "delete from gz_zbcc_sy where sy_id in (select a.sy_id from  gz_zbcc_sy a left join gz_zbcc_sj b on a.sy_id = b.sy_id where b.sj_id is null)";
		stmt.addSqlStmt(sql);
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
