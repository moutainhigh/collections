package com.gwssi.dw.aic.download.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[download_param]�Ĵ�����
 * @author Administrator
 *
 */
public class DownloadParam extends BaseTable
{
	public DownloadParam()
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
		// table.executeFunction( "loadDownloadParamList", context, inputNode, outputNode );
		registerSQLFunction( "getColumnInfo", DaoFunction.SQL_ROWSET, "��ȡ�ֶβ�����Ϣ" );
		registerSQLFunction( "getDownloadInfo", DaoFunction.SQL_ROWSET, "��ȡ������Ϣ" );
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
	 * ��ȡColumn����Ϣ
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement getColumnInfo( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String download_status_id = request.getRecord("select-key").getValue("download_status_id");
//		String sql = "select c.COLUMN_NO, c.COLUMN_NAME, c.COLUMN_NAME_CN, " +
//				"C.COLUMN_BYNAME, c.EDIT_TYPE, c.DEMO, c.TABLE_NO, t.table_name, " +
//				"t.table_name_cn from SYS_COLUMN_SEMANTIC c, SYS_TABLE_SEMANTIC t, download_param p " +
//				"where c.table_no=t.table_no and c.column_no=p.param_column" +
//				" and c.column_no='" + column_no + "'";
		String sql = "select c.COLUMN_NO, c.COLUMN_NAME, c.COLUMN_NAME_CN, " +
		"C.COLUMN_BYNAME, c.EDIT_TYPE, c.DEMO, c.TABLE_NO, p.* " +
		" from SYS_COLUMN_SEMANTIC c, download_param p " +
		"where c.column_no=p.param_column and p.download_status_id='" + download_status_id + "'";
		sql += "order by p.param_seque";
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( "select count(*) from (" + sql + ")" );
		return stmt;
	}
	
	/**
	 * ��ȡ������Ϣ
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement getDownloadInfo( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String download_status_id = request.getRecord("select-key").getValue("download_status_id");
		String sql = "select * from download_status t " +
			"where t.download_status_id='" + download_status_id + "'"; 
		System.out.println("sql:" + sql);
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( "select count(*) from (" + sql + ")" );
		return stmt;
	}
}

