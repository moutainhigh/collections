package cn.gwssi.dw.rd.metadata.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[sys_rd_data_source]�Ĵ�����
 * @author Administrator
 *
 */
public class SysRdDataSource extends BaseTable
{
	public SysRdDataSource()
	{
		
	}
	
	/**
	 * ע���û��Զ����SQL���
	 */
	protected void register( )
	{
		// ������ע���û��Զ��庯���Ĺ���
		// ��������������SQL�������ƣ����ͣ�����
		// ҵ�������ͨ�����º�������:
		registerSQLFunction("selectDataSourceExsit",DaoFunction.SQL_ROWSET,"��ѯ����Դ�Ƿ����(insert)");
		registerSQLFunction("selectDataSourceExsitForUpdate",DaoFunction.SQL_ROWSET,"��ѯ����Դ�Ƿ����(update)");
		registerSQLFunction("updateSyncDate",DaoFunction.SQL_UPDATE,"����ͬ��ʱ��");
		registerSQLFunction("queryVFP",DaoFunction.SQL_ROWSET,"��ȡ��ͼ���������洢������Ϣ");
		registerSQLFunction("selectClaimTableList",DaoFunction.SQL_ROWSET,"��ѯ���������Ϣ");
		registerSQLFunction("selectUnclaimTableContent",DaoFunction.SQL_ROWSET,"��ѯδ�������Ϣ");
		registerSQLFunction("selectClaimColumnList",DaoFunction.SQL_ROWSET,"��ѯ��������ֶ���Ϣ");
		registerSQLFunction("selectUnclaimColumnContent",DaoFunction.SQL_ROWSET,"��ѯδ������ֶ���Ϣ");
		registerSQLFunction("selectColumnAddList",DaoFunction.SQL_ROWSET,"��ѯԴ�������ֶ�");
		registerSQLFunction("pugreRecyclebin",DaoFunction.SQL_UPDATE,"��ջ���վ");
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
	 * ��ѯ����Դ�Ƿ����(insert)
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
	 * ��ѯ����Դ�Ƿ����(update)
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
	 * ��������Դͬ��ʱ��
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
	 * ��ȡ��ͼ���������洢������Ϣ
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
	 * ��������Դ��ѯ���������Ϣ
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
	 * ��������Դ��������ѯδ�������Ϣ
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
	 * ��ѯ��������ֶ���Ϣ
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
	 * ��ѯδ������ֶ���Ϣ
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
	 * ��ѯԴ���ֶ�����
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
	 * ��ջ���վ
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

