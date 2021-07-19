package cn.gwssi.dw.rd.metadata.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * 数据表[sys_rd_data_source]的处理类
 * @author Administrator
 *
 */
public class SysRdDataSource extends BaseTable
{
	public SysRdDataSource()
	{
		
	}
	
	/**
	 * 注册用户自定义的SQL语句
	 */
	protected void register( )
	{
		// 以下是注册用户自定义函数的过程
		// 包括三个参数：SQL语句的名称，类型，描述
		// 业务类可以通过以下函数调用:
		registerSQLFunction("selectDataSourceExsit",DaoFunction.SQL_ROWSET,"查询数据源是否存在(insert)");
		registerSQLFunction("selectDataSourceExsitForUpdate",DaoFunction.SQL_ROWSET,"查询数据源是否存在(update)");
		registerSQLFunction("updateSyncDate",DaoFunction.SQL_UPDATE,"更新同步时间");
		registerSQLFunction("queryVFP",DaoFunction.SQL_ROWSET,"获取视图、函数、存储过程信息");
		registerSQLFunction("selectClaimTableList",DaoFunction.SQL_ROWSET,"查询已认领表信息");
		registerSQLFunction("selectUnclaimTableContent",DaoFunction.SQL_ROWSET,"查询未认领表信息");
		registerSQLFunction("selectClaimColumnList",DaoFunction.SQL_ROWSET,"查询已认领表字段信息");
		registerSQLFunction("selectUnclaimColumnContent",DaoFunction.SQL_ROWSET,"查询未认领表字段信息");
		registerSQLFunction("selectColumnAddList",DaoFunction.SQL_ROWSET,"查询源表增加字段");
		registerSQLFunction("pugreRecyclebin",DaoFunction.SQL_UPDATE,"清空回收站");
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
	 * 查询数据源是否存在(insert)
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement selectDataSourceExsit(TxnContext request,DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String db_name = request.getRecord("record").getValue("db_name");
		stmt.addSqlStmt("SELECT count(*) count FROM SYS_RD_DATA_SOURCE WHERE DB_NAME='"+db_name+"'");
		stmt.setCountStmt("SELECT count(*) count FROM SYS_RD_DATA_SOURCE WHERE DB_NAME='"+db_name+"'");
		return stmt;
	}
	/**
	 * 查询数据源是否存在(update)
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement selectDataSourceExsitForUpdate(TxnContext request,DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String db_name = request.getRecord("record").getValue("db_name");
		String sys_rd_data_source_id = request.getRecord("record").getValue("sys_rd_data_source_id");
		stmt.addSqlStmt("SELECT count(*) count FROM SYS_RD_DATA_SOURCE WHERE DB_NAME='"+db_name+"' AND sys_rd_data_source_id !='"+sys_rd_data_source_id+"'");
		stmt.setCountStmt("SELECT count(*) count FROM SYS_RD_DATA_SOURCE WHERE DB_NAME='"+db_name+"' AND sys_rd_data_source_id !='"+sys_rd_data_source_id+"'");
		return stmt;
	}
	/**
	 * 更新数据源同步时间
	 * @param request
	 * @param inputData
	 */
	public SqlStatement updateSyncDate(TxnContext request,DataBus inputData){
		SqlStatement stmt = new SqlStatement();
		String sys_rd_data_source_id = inputData.getValue("sys_rd_data_source_id");
		String sync_date = inputData.getValue("sync_date");
		if(sys_rd_data_source_id!=null && !"".equals(sys_rd_data_source_id)){
			String sql="update sys_rd_data_source set SYNC_FLAG='1',SYNC_DATE='" + sync_date + "' where sys_rd_data_source_id='" + sys_rd_data_source_id + "'";
			stmt.addSqlStmt(sql);
		}
		return stmt;
	}
	/**
	 * 获取视图、函数、存储过程信息
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryVFP(TxnContext request,DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt("select owner,object_name,substr(object_type,1,1) object_type from dba_objects where owner=user and (object_type='VIEW' or object_type='FUNCTION'or object_type='PROCEDURE')");
		stmt.setCountStmt("select count(*) from dba_objects where owner=user and (object_type='VIEW' or object_type='FUNCTION'or object_type='PROCEDURE')"); 
		return stmt;
	}
	/**
	 * 根据数据源查询已认领表信息
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement selectClaimTableList(TxnContext request,DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String sys_rd_data_source_id = inputData.getValue("sys_rd_data_source_id");
		if(sys_rd_data_source_id !=null && !"".equals(sys_rd_data_source_id)){
			String sql="select table_code,table_name,table_primary_key,object_schema from sys_rd_table where sys_rd_data_source_id='"+sys_rd_data_source_id+"'";
			stmt.addSqlStmt(sql);
		}
		return stmt;
	}
	/**
	 * 根据数据源及表名查询未认领表信息
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement selectUnclaimTableContent(TxnContext request,DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String sys_rd_data_source_id = inputData.getValue("sys_rd_data_source_id");
		String object_schema = inputData.getValue("object_schema");
		String unclaim_table_code = inputData.getValue("unclaim_table_code");
		if(sys_rd_data_source_id!=null && !"".equals(sys_rd_data_source_id) && unclaim_table_code!=null && !"".equals(unclaim_table_code)){
			String sql = "select sys_rd_unclaim_table_id,tb_pk_columns from sys_rd_unclaim_table where sys_rd_data_source_id='"+sys_rd_data_source_id+"' and object_schema='"+object_schema+"' and unclaim_table_code='"+unclaim_table_code+"'";
			stmt.addSqlStmt(sql);
		}
		return stmt;
	}
	/**
	 * 查询已认领表字段信息
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement selectClaimColumnList(TxnContext request,DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String sys_rd_data_source_id = inputData.getValue("sys_rd_data_source_id");
		String table_code = inputData.getValue("table_code");
		if(sys_rd_data_source_id != null && !"".equals(sys_rd_data_source_id) && table_code != null && !"".equals(table_code)){
			String sql = "select column_code,column_name,column_type,column_length from sys_rd_column where sys_rd_data_source_id='"+sys_rd_data_source_id+"' and table_code='"+table_code+"'";
			stmt.addSqlStmt(sql);
		}
		return stmt;
	}
	/**
	 * 查询未认领表字段信息
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement selectUnclaimColumnContent(TxnContext request,DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String sys_rd_data_source_id = inputData.getValue("sys_rd_data_source_id");
		String unclaim_table_code = inputData.getValue("unclaim_table_code");
		String unclaim_column_code = inputData.getValue("unclaim_column_code");
		if(sys_rd_data_source_id != null && !"".equals(sys_rd_data_source_id) && unclaim_table_code != null && !"".equals(unclaim_table_code) && unclaim_column_code != null && !"".equals(unclaim_column_code)){
			String sql = "select unclaim_column_type,unclaim_column_length from sys_rd_unclaim_column where sys_rd_data_source_id='"+sys_rd_data_source_id+"' and unclaim_tab_code='"+unclaim_table_code+"' and unclaim_column_code='"+unclaim_column_code+"'";
			stmt.addSqlStmt(sql);
		}
		return stmt;
	}
	/**
	 * 查询源表字段增加
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement selectColumnAddList(TxnContext request,DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String sys_rd_data_source_id = inputData.getValue("sys_rd_data_source_id");
		String unclaim_table_code = inputData.getValue("unclaim_table_code");
		if(sys_rd_data_source_id != null && !"".equals(sys_rd_data_source_id) && unclaim_table_code != null && !"".equals(unclaim_table_code)){
			String sql = "select unclaim_column_code,unclaim_column_name from sys_rd_unclaim_column a where sys_rd_data_source_id='"+sys_rd_data_source_id+"' and unclaim_tab_code='"+unclaim_table_code+"' and not exists (select column_code from sys_rd_column b where sys_rd_data_source_id='"+sys_rd_data_source_id+"' and table_code='"+unclaim_table_code+"' and a.unclaim_column_code=b.column_code)";
			stmt.addSqlStmt(sql);
		}
		return stmt;
	}
	
	/**
	 * 清空回收站
	 * @param request
	 * @param inputData
	 */
	public SqlStatement pugreRecyclebin(TxnContext request,DataBus inputData){
		SqlStatement stmt = new SqlStatement();
		String sql="PURGE RECYCLEBIN";
		stmt.addSqlStmt(sql);
		return stmt;
	}
	
}

