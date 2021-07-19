package cn.gwssi.dw.rd.metadata.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[sys_rd_column]�Ĵ�����
 * @author Administrator
 *
 */
public class SysRdColumn extends BaseTable
{
	public SysRdColumn()
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
		// table.executeFunction( "loadSysRdColumnList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadSysRdColumnList", DaoFunction.SQL_ROWSET, "��ȡ��������ֶα��б�" );
		registerSQLFunction( "queryDistinctColumnListByName", DaoFunction.SQL_ROWSET, "�ֶ���������ѯ" );
		registerSQLFunction( "queryDistinctColumnListByCode", DaoFunction.SQL_ROWSET, "�ֶ�����ѯ" );
		registerSQLFunction( "queryColumnListByName", DaoFunction.SQL_ROWSET, "�ֶ����������ò�ѯ" );
		registerSQLFunction( "queryColumnListByCode", DaoFunction.SQL_ROWSET, "�ֶ������ò�ѯ" );
		registerSQLFunction( "queryColumnType", DaoFunction.SQL_ROWSET, "��ѯ�ֶ����ͺͳ���" );
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
	 * �õ��ֶ�������
	 */
	 public SqlStatement queryDistinctColumnListByName(TxnContext request,DataBus inputData) throws TxnException
		{	 

			SqlStatement stmt = new SqlStatement();
			String sql = "";
			String sqlCount = "";
			StringBuffer stringBfSql = new StringBuffer("select distinct(column_name) from SYS_RD_COLUMN where column_name like  ");
			StringBuffer stringBfCount = new StringBuffer("select count(distinct(column_name)) from SYS_RD_COLUMN where column_name like  ");
			DataBus dataBus = request.getRecord("select-key");	
			String column_name = dataBus.getValue("column_name");	
			stringBfSql.append("'%"+column_name+"%' ");
			stringBfCount.append("'%"+column_name+"%' ");

			sql = stringBfSql.toString();
			sqlCount = stringBfCount.toString();
			stmt.addSqlStmt(sql);	
			stmt.setCountStmt(sqlCount);	
			return stmt;

		}

		/**
		 * ͨ���ֶ��������õ�������Ϣ
		 */
	 public SqlStatement queryColumnListByName(TxnContext request,DataBus inputData) throws TxnException
		{	 
		 SqlStatement stmt = new SqlStatement();
		 String sql = "";
		 String sqlCount = "";
			StringBuffer stringBfSql = new StringBuffer("select  sys_rd_column.column_code,sys_rd_column.column_name,sys_rd_table.table_code,sys_rd_table.table_name from sys_rd_column left join sys_rd_table on sys_rd_column.sys_rd_table_id = sys_rd_table.sys_rd_table_id ");
			StringBuffer stringBfCount = new StringBuffer("select count(*) from sys_rd_column left join sys_rd_table on sys_rd_column.sys_rd_table_id = sys_rd_table.sys_rd_table_id  ");

			String column_name = inputData.getValue("column_name");
			if(column_name==null){
				stringBfSql.append(" ORDER BY sys_rd_column.column_code");
				stringBfCount.append(" ORDER BY sys_rd_column.column_code");
			}else{
			stringBfSql.append("where sys_rd_column.column_name = '"+column_name+"' ORDER BY sys_rd_column.column_code");
			stringBfCount.append("where sys_rd_column.column_name = '"+column_name+"' ORDER BY sys_rd_column.column_code");
			}
			sql = stringBfSql.toString();
			sqlCount = stringBfCount.toString();
			stmt.addSqlStmt(sql);	
			stmt.setCountStmt(sqlCount);
		 return stmt;
		}
	 
		/**
		 * �õ��ֶ���
		 */
	 public SqlStatement queryDistinctColumnListByCode(TxnContext request,DataBus inputData) throws TxnException
		{	 
		
		 SqlStatement stmt = new SqlStatement();
			String sql = "";
			String sqlCount = "";
			StringBuffer stringBfSql = new StringBuffer("select distinct(column_code) from SYS_RD_COLUMN where column_code like   ");
			StringBuffer stringBfCount = new StringBuffer("select count(distinct(column_code)) from SYS_RD_COLUMN where column_code like ");
			
			DataBus dataBus = request.getRecord("select-key");	
			String column_code = dataBus.getValue("column_code");
			if(column_code==null){
				column_code="";
			}
			stringBfSql.append("'%"+column_code+"%' ");
			stringBfCount.append("'%"+column_code+"%' ");
			
			sql = stringBfSql.toString();
			sqlCount = stringBfCount.toString();
			stmt.addSqlStmt(sql);	
			stmt.setCountStmt(sqlCount);	
			return stmt;

		}
		/**
		 * ͨ���ֶ����õ�������Ϣ
		 */
	 public SqlStatement queryColumnListByCode(TxnContext request,DataBus inputData) throws TxnException
		{	 
		 SqlStatement stmt = new SqlStatement();
		 String sql = "";
		 String sqlCount = "";
			StringBuffer stringBfSql = new StringBuffer("select  sys_rd_column.column_code,sys_rd_column.column_name,sys_rd_table.table_code,sys_rd_table.table_name from sys_rd_column left join sys_rd_table on sys_rd_column.sys_rd_table_id = sys_rd_table.sys_rd_table_id ");
			StringBuffer stringBfCount = new StringBuffer("select count(*) from sys_rd_column left join sys_rd_table on sys_rd_column.sys_rd_table_id = sys_rd_table.sys_rd_table_id  ");
			
			String column_code = inputData.getValue("column_code");
			if(column_code==null||column_code.equals("")){
				stringBfSql.append(" ORDER BY sys_rd_column.column_code");
				stringBfCount.append("  ORDER BY sys_rd_column.column_code");
			}else{
			stringBfSql.append("where sys_rd_column.column_code='"+column_code+"' ORDER BY sys_rd_column.column_code");
			stringBfCount.append("where sys_rd_column.column_code='"+column_code+"' ORDER BY sys_rd_column.column_code");
			}
			
			sql = stringBfSql.toString();
			sqlCount = stringBfCount.toString();
			stmt.addSqlStmt(sql);	
			stmt.setCountStmt(sqlCount);
		 return stmt;
		
		}
	 
	 /**
	     * ��ѯ�ֶ����ͺͳ����������ýӿڵĵ�������
	     */
	 public SqlStatement queryColumnType(TxnContext request,DataBus inputData) throws TxnException
		{	   
			SqlStatement stmt = new SqlStatement();
			
			//��ȡ�����ֶκ�
			String column_nos = request.getRecord("select-key").getValue("column_no");
			
			//VoUser user = request.getOperData();
			//String userName = user.getOperName();
			
			StringBuffer querySql = new StringBuffer(" select tt.*,tt1.codename typename from(select t.table_name,t1.table_code,column_code,column_name,column_type,column_length from Sys_Rd_Column t1,sys_rd_table t  where t1.sys_rd_table_id = t.sys_rd_table_id ");
			if(column_nos !=null && !"".equals(column_nos)){
				querySql.append(" and t1.column_no in("+column_nos+")");
			}
			
			querySql.append(")tt,(select codevalue,codename from codedata  where codetype='�ֶ���������') tt1 where tt.column_type = tt1.codevalue ");
			querySql.append(" order by tt.table_code");
			//System.out.println("�ֶγ���1��"+querySql.toString());
			stmt.addSqlStmt(querySql.toString());
			stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
			return stmt;
		}
	/**
	 * XXX:�û��Զ����SQL���
	 * ���ڶ��¼�Ĳ�ѯ��䣬��Ҫ����������䣺��ѯ����ȡ��¼���������
	 * ������������䣬ֻ��Ҫ����һ�����
	 * @param request ���׵�������
	 * @param inputData ������������ڵ�
	 * @return
	public SqlStatement loadSysRdColumnList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from sys_rd_column" );
		stmt.setCountStmt( "select count(*) from sys_rd_column" );
		return stmt;
	}
	 */
	
}

