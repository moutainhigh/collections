package cn.gwssi.dw.rd.metadata.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class SysRdClaimView extends BaseTable
{
   public SysRdClaimView()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
	   //
	   registerSQLFunction( "queryClaimViewList", DaoFunction.SQL_ROWSET, "查询已认领视图列表" );
	   registerSQLFunction( "queryClaimFunctionList", DaoFunction.SQL_ROWSET, "查询已认领函数列表" );
	   registerSQLFunction( "queryClaimProcedureList", DaoFunction.SQL_ROWSET, "查询已认领存储过程列表" );
	   registerSQLFunction( "QueryView", DaoFunction.SQL_ROWSET,  "认领视图情况" );
	   registerSQLFunction( "QueryFunction", DaoFunction.SQL_ROWSET,  "认领函数情况" );
	   registerSQLFunction( "QueryProcedure", DaoFunction.SQL_ROWSET,  "认领存储过程情况" );
   
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
   
   /**
    * 查询已认领视图列表
    * @param request
    * @param inputData
    * @return
    * @throws TxnException
    */
	public SqlStatement queryClaimViewList(TxnContext request,DataBus inputData) throws TxnException
	{	   
		/*SqlStatement stmt = new SqlStatement();
		String db_name = inputData.getValue("db_name");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select a.sys_rd_data_source_id,a.db_name,b.object_type,count(*) as cnt from sys_rd_data_source a,sys_rd_claim_view b where a.sys_rd_data_source_id=b.sys_rd_data_source_id and b.object_type='V'");
		if (db_name!=null && !"".equals(db_name)){
			sqlBuffer.append(" and a.db_name='" + db_name + "'");
		}
		sqlBuffer.append(" group by a.sys_rd_data_source_id,a.db_name,b.object_type");
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt("select count(1) from (" + sqlBuffer.toString() + ")");*/
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String sys_rd_data_source_id = inputData.getValue("sys_rd_data_source_id");
		String view_name = inputData.getValue("view_name");
		sqlBuffer.append("select a.sys_rd_claimed_view_id,a.sys_rd_data_source_id,a.view_name,a.view_use,a.claim_operator,a.claim_date from sys_rd_claim_view a,sys_rd_data_source b where b.sys_rd_data_source_id=a.sys_rd_data_source_id and a.object_type='V'");
		if (!"".equals(sys_rd_data_source_id) && sys_rd_data_source_id != null) {
			sqlBuffer.append(" and b.sys_rd_data_source_id='" + sys_rd_data_source_id + "'");
		}
		if (!"".equals(view_name) && view_name != null) {
			sqlBuffer.append(" and a.view_name like '").append(view_name + "%")
					.append("'");
		}
		
		sqlBuffer.append(" order by a.sys_rd_data_source_id,a.view_name");
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt("select count(1) from (" + sqlBuffer.toString()+ ")");
		return stmt;
	}
	
	/**
	    * 查询已认领函数列表
	    * @param request
	    * @param inputData
	    * @return
	    * @throws TxnException
	    */
		public SqlStatement queryClaimFunctionList(TxnContext request,DataBus inputData) throws TxnException
		{	   
			/*SqlStatement stmt = new SqlStatement();
			String db_name = inputData.getValue("db_name");
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append("select a.sys_rd_data_source_id,a.db_name,b.object_type,count(*) as cnt from sys_rd_data_source a,sys_rd_claim_view b where a.sys_rd_data_source_id=b.sys_rd_data_source_id and b.object_type='F'");
			if (db_name!=null && !"".equals(db_name)){
				sqlBuffer.append(" and a.db_name='" + db_name + "'");
			}
			sqlBuffer.append(" group by a.sys_rd_data_source_id,a.db_name,b.object_type");
			stmt.addSqlStmt(sqlBuffer.toString());
			stmt.setCountStmt("select count(1) from (" + sqlBuffer.toString() + ")");*/
			SqlStatement stmt = new SqlStatement();
			StringBuffer sqlBuffer = new StringBuffer();
			String sys_rd_data_source_id = inputData.getValue("sys_rd_data_source_id");
			String view_name = inputData.getValue("view_name");
			sqlBuffer.append("select a.sys_rd_claimed_view_id,a.sys_rd_data_source_id,a.view_name,a.view_use,a.claim_operator,a.claim_date from sys_rd_claim_view a,sys_rd_data_source b where b.sys_rd_data_source_id=a.sys_rd_data_source_id and a.object_type='F'");
			if (!"".equals(sys_rd_data_source_id) && sys_rd_data_source_id != null) {
				sqlBuffer.append(" and b.sys_rd_data_source_id='" + sys_rd_data_source_id + "'");
			}
			if (!"".equals(view_name) && view_name != null) {
				sqlBuffer.append(" and a.view_name like '").append(view_name + "%")
						.append("'");
			}
			
			sqlBuffer.append(" order by a.sys_rd_data_source_id,a.view_name");
			stmt.addSqlStmt(sqlBuffer.toString());
			stmt.setCountStmt("select count(1) from (" + sqlBuffer.toString()+ ")");
			return stmt;
		}
		
		/**
		    * 查询已认领存储过程列表
		    * @param request
		    * @param inputData
		    * @return
		    * @throws TxnException
		    */
			public SqlStatement queryClaimProcedureList(TxnContext request,DataBus inputData) throws TxnException
			{	   
				/*SqlStatement stmt = new SqlStatement();
				String db_name = inputData.getValue("db_name");
				StringBuffer sqlBuffer = new StringBuffer();
				sqlBuffer.append("select a.sys_rd_data_source_id,a.db_name,b.object_type,count(*) as cnt from sys_rd_data_source a,sys_rd_claim_view b where a.sys_rd_data_source_id=b.sys_rd_data_source_id and b.object_type='P'");
				if (db_name!=null && !"".equals(db_name)){
					sqlBuffer.append(" and a.db_name='" + db_name + "'");
				}
				sqlBuffer.append(" group by a.sys_rd_data_source_id,a.db_name,b.object_type");
				stmt.addSqlStmt(sqlBuffer.toString());
				stmt.setCountStmt("select count(1) from (" + sqlBuffer.toString() + ")");*/
				SqlStatement stmt = new SqlStatement();
				StringBuffer sqlBuffer = new StringBuffer();
				String sys_rd_data_source_id = inputData.getValue("sys_rd_data_source_id");
				String view_name = inputData.getValue("view_name");
				sqlBuffer.append("select a.sys_rd_claimed_view_id,a.sys_rd_data_source_id,a.view_name,a.view_use,a.claim_operator,a.claim_date from sys_rd_claim_view a,sys_rd_data_source b where b.sys_rd_data_source_id=a.sys_rd_data_source_id and a.object_type='P'");
				if (!"".equals(sys_rd_data_source_id) && sys_rd_data_source_id != null) {
					sqlBuffer.append(" and b.sys_rd_data_source_id='" + sys_rd_data_source_id + "'");
				}
				if (!"".equals(view_name) && view_name != null) {
					sqlBuffer.append(" and a.view_name like '").append(view_name + "%")
							.append("'");
				}
				sqlBuffer.append(" order by a.sys_rd_data_source_id,a.view_name");
				stmt.addSqlStmt(sqlBuffer.toString());
				stmt.setCountStmt("select count(1) from (" + sqlBuffer.toString()+ ")");
				return stmt;
			}
			
			/**
			 * 查询视图认领情况
			 * @param context
			 * @return
			 */
			public SqlStatement QueryView(TxnContext request,DataBus inputData){
				SqlStatement stmt = new SqlStatement();
				String object_type="V";
				String dbName = request.getRecord("select-key").getValue("db_name");
				StringBuffer sql = new StringBuffer();
				sql.append("select s.db_name,temp.* from (");
				sql.append("select a.sys_rd_data_source_id,nvl(a.cnt1,0) as total,nvl(b.cnt2,0) as yrl,nvl(nvl(a.cnt1,0)-nvl(b.cnt2,0),0) as wrl from ");
				sql.append("(select sys_rd_data_source_id,count(*) as cnt1 from sys_rd_unclaim_table where data_object_type='" + object_type + "' group by sys_rd_data_source_id) a left join");
				sql.append("(select sys_rd_data_source_id,count(*) as cnt2 from sys_rd_claim_view where object_type='" + object_type + "' group by sys_rd_data_source_id) b on a.sys_rd_data_source_id=b.sys_rd_data_source_id");
				sql.append(") temp,SYS_RD_DATA_SOURCE s where 1=1 and s.sys_rd_data_source_id=temp.sys_rd_data_source_id");
				if (dbName!=null && !"".equals(dbName)){
					sql.append(" and s.db_name='" + dbName + "'");
				}
				//System.out.println("========:"+sql.toString());
				stmt.addSqlStmt(sql.toString());
				stmt.setCountStmt("select count(1) from (" + sql.toString() + ")");
			    return stmt;
			}
			
			/**
			 * 查询函数认领情况
			 * @param context
			 * @return
			 */
			public SqlStatement QueryFunction(TxnContext request,DataBus inputData){
				SqlStatement stmt = new SqlStatement();
				String object_type="F";
				String dbName = request.getRecord("select-key").getValue("db_name");
				StringBuffer sql = new StringBuffer();
				sql.append("select s.db_name,temp.* from (");
				sql.append("select a.sys_rd_data_source_id,nvl(a.cnt1,0) as total,nvl(b.cnt2,0) as yrl,nvl(nvl(a.cnt1,0)-nvl(b.cnt2,0),0) as wrl from ");
				sql.append("(select sys_rd_data_source_id,count(*) as cnt1 from sys_rd_unclaim_table where data_object_type='" + object_type + "' group by sys_rd_data_source_id) a left join");
				sql.append("(select sys_rd_data_source_id,count(*) as cnt2 from sys_rd_claim_view where object_type='" + object_type + "' group by sys_rd_data_source_id) b on a.sys_rd_data_source_id=b.sys_rd_data_source_id");
				sql.append(") temp,SYS_RD_DATA_SOURCE s where 1=1 and s.sys_rd_data_source_id=temp.sys_rd_data_source_id");
				if (dbName!=null && !"".equals(dbName)){
					sql.append(" and s.db_name='" + dbName + "'");
				}
				//System.out.println("========:"+sql.toString());
				stmt.addSqlStmt(sql.toString());
				stmt.setCountStmt("select count(1) from (" + sql.toString() + ")");
			    return stmt;
			}
			/**
			 * 查询存储过程认领情况
			 * @param context
			 * @return
			 */
			public SqlStatement QueryProcedure(TxnContext request,DataBus inputData){
				SqlStatement stmt = new SqlStatement();
				String object_type="P";
				String db_name = request.getRecord("select-key").getValue("db_name");
				StringBuffer sql = new StringBuffer();
				sql.append("select s.db_name,temp.* from (");
				sql.append("select a.sys_rd_data_source_id,nvl(a.cnt1,0) as total,nvl(b.cnt2,0) as yrl,nvl(nvl(a.cnt1,0)-nvl(b.cnt2,0),0) as wrl from ");
				sql.append("(select sys_rd_data_source_id,count(*) as cnt1 from sys_rd_unclaim_table where data_object_type='" + object_type + "' group by sys_rd_data_source_id) a left join");
				sql.append("(select sys_rd_data_source_id,count(*) as cnt2 from sys_rd_claim_view where object_type='" + object_type + "' group by sys_rd_data_source_id) b on a.sys_rd_data_source_id=b.sys_rd_data_source_id");
				sql.append(") temp,SYS_RD_DATA_SOURCE s where 1=1 and s.sys_rd_data_source_id=temp.sys_rd_data_source_id");
				
				if (db_name!=null && !"".equals(db_name)){
					sql.append(" and s.db_name='" + db_name + "'");
				}
				//System.out.println("========:"+sql.toString());
				stmt.addSqlStmt(sql.toString());
				stmt.setCountStmt("select count(1) from (" + sql.toString() + ")");
			    return stmt;
			}
			
}
