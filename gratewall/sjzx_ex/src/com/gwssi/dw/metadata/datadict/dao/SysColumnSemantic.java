package com.gwssi.dw.metadata.datadict.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[sys_column_semantic]�Ĵ�����
 * @author Administrator
 *
 */
public class SysColumnSemantic extends BaseTable
{
	public SysColumnSemantic()
	{
		
	}
	
	/**
	 * ע���û��Զ����SQL���
	 */
	protected void register( )
	{
		registerSQLFunction( "validateColumnPrimaryKey", DaoFunction.SQL_ROWSET, "����У��" );
		registerSQLFunction( "validateColumnName", DaoFunction.SQL_ROWSET, "У���ֶ���" );
		registerSQLFunction( "validateColumnNameCn", DaoFunction.SQL_ROWSET, "У���ֶ�������" );
		registerSQLFunction( "queryList", DaoFunction.SQL_ROWSET, "���¼��ѯ" );
		registerSQLFunction( "queryColumnByName", DaoFunction.SQL_ROWSET, "У���ֶ���" );
		registerSQLFunction( "queryMaxAlias", DaoFunction.SQL_ROWSET, "��ȡ�����ֶα���" );
		registerSQLFunction( "getColumnListByTableName", DaoFunction.SQL_ROWSET, "���ݱ�����ѯҵ���ֶ��б�" );
	}
	
	/**
	 * ����У��(У�������Ƿ��ظ�)
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement validateColumnPrimaryKey( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String column_no = request.getRecord("select-key").getValue("column_no");
		stmt.addSqlStmt( "select table_no from sys_column_semantic where column_no = '" + column_no + "'");
		stmt.setCountStmt( "select count(*) from sys_column_semantic where column_no = '" + column_no + "'");
		return stmt;
	}
	
	/**
	 * У���ֶ���(�鿴ͬ������ ��û����ͬ���ֶ���)
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement validateColumnName( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String column_name = request.getRecord("select-key").getValue("column_name");
		String table_no = request.getRecord("select-key").getValue("table_no");
		String type = request.getRecord("select-key").getValue("type");
		String column_no = request.getRecord("select-key").getValue("column_no");
		String sql = "select column_name from sys_column_semantic where column_name = '" + column_name + "' and table_no='"+table_no+"'";
		String countSql = "select count(*) from sys_column_semantic where column_name = '" + column_name + "' and table_no='"+table_no+"'";
		if (type!=null && type.equals("modify"))
		{
			sql += " and column_no !='"+column_no+"'";
			countSql += " and column_no !='"+column_no+"'";
		}
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	}
	
	/**
	 * ���ݱ�����ѯҵ���ֶ��б�
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement getColumnListByTableName( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String table_name = request.getRecord("select-key").getValue("table_name");
		String sql = "select c.COLUMN_NO, c.COLUMN_NAME, c.COLUMN_NAME_CN, C.COLUMN_BYNAME, " +
				"c.EDIT_TYPE, c.DEMO, c.TABLE_NO, t.table_name, t.table_name_cn from " +
				"SYS_COLUMN_SEMANTIC c, SYS_TABLE_SEMANTIC t where LOWER(t.TABLE_NAME) = '" + table_name + 
				"' AND c.table_no=t.table_no order by c.COLUMN_ORDER";
		String countSql = "select count(1) from " +
				"SYS_COLUMN_SEMANTIC c, SYS_TABLE_SEMANTIC t where LOWER(t.TABLE_NAME) = '" + table_name + 
				"' AND c.table_no=t.table_no order by c.COLUMN_ORDER";
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	} 
	
	/**
	 * У���ֶ�������(�鿴ͬ������ ��û����ͬ���ֶ�������)
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement validateColumnNameCn( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String column_name_cn = request.getRecord("select-key").getValue("column_name_cn");
		String table_no = request.getRecord("select-key").getValue("table_no");
		String type = request.getRecord("select-key").getValue("type");
		String column_no = request.getRecord("select-key").getValue("column_no");
		String sql = "select column_name_cn from sys_column_semantic where column_name_cn = '" 
				+ column_name_cn + "' and table_no='"+table_no+"'";
		String countSql = "select count(*) from sys_column_semantic where column_name = '" 
				+ column_name_cn + "' and table_no='"+table_no+"'";
		if (type!=null && type.equals("modify"))
		{
			sql += " and column_no !='"+column_no+"'";
			countSql += " and column_no !='"+column_no+"'";
		}
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	} 
	
	/**
	 * ���¼��ѯ
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String sys_id = request.getRecord("select-key").getValue("sys_id");
		String table_no = request.getRecord("select-key").getValue("table_no");
		String column_name = request.getRecord("select-key").getValue("column_name");
		String column_name_cn = request.getRecord("select-key").getValue("column_name_cn");
		StringBuffer sqlBuff = new StringBuffer("select A.table_no, A.column_no, A.column_name, A.column_name_cn, B.table_name||'_'||A.column_order as column_order , " +
				" A.edit_type, A.edit_content, A.demo, B.sys_id, B.table_name_cn, C.sys_no,C.sys_name,B.table_name " );
		StringBuffer conBuff = new StringBuffer("select count(*) ");		
		StringBuffer sb = new StringBuffer();
		sb.append(" from sys_column_semantic A,sys_table_semantic B,sys_system_semantic C ");
		sb.append(" where A.table_no=B.table_no and B.sys_id=C.sys_id ");
		
		if (sys_id!=null && !sys_id.equals(""))
		{
			sb.append(" and B.sys_id='" + sys_id + "' ");
		}	
		if (table_no!=null && !table_no.equals(""))
		{
			sb.append(" and B.table_no='" + table_no + "' ");
		}
		if (column_name!=null && !column_name.equals(""))
		{
			sb.append(" and A.column_name like '%" + column_name + "%' ");
		}
		if (column_name_cn!=null && !column_name_cn.equals(""))
		{
			sb.append(" and A.column_name_cn like '%" + column_name_cn + "%' ");
		}	
		sb.append(" order by C.sys_name,B.table_name_cn,A.column_name_cn " );
		sqlBuff.append(sb);
		conBuff.append(sb);
		String sql = sqlBuff.toString();
		String countSql = conBuff.toString(); 
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	} 	
	/**
	 * У���ֶ���
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryColumnByName( TxnContext request, DataBus inputData ){
		
		SqlStatement stmt = new SqlStatement( );
		String column_name = request.getRecord("select-key").getValue("column_name");
		String sql = "select column_name from sys_column_semantic where column_name = '" + column_name + "' ";
		String countSql = "select count(*) from sys_column_semantic where column_name = '" + column_name + "' ";
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	} 
		
	/**
	 * ��ȡ�����ֶα���
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryMaxAlias( TxnContext request, DataBus inputData ){
		
		SqlStatement stmt = new SqlStatement( );
		String sql = "select max(column_byname) as column_byname from sys_column_semantic";
		stmt.addSqlStmt( sql );
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
	
	/**
	 * XXX:�û��Զ����SQL���
	 * ���ڶ��¼�Ĳ�ѯ��䣬��Ҫ����������䣺��ѯ����ȡ��¼���������
	 * ������������䣬ֻ��Ҫ����һ�����
	 * @param request ���׵�������
	 * @param inputData ������������ڵ�
	 * @return
	public SqlStatement loadSysColumnSemanticList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from sys_column_semantic" );
		stmt.setCountStmt( "select count(*) from sys_column_semantic" );
		return stmt;
	}
	 */
	
}

