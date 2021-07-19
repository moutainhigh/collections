package com.gwssi.dw.aic.download.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[download_check_purv]�Ĵ�����
 * @author Administrator
 *
 */
public class DownloadCheckPurv extends BaseTable
{
	public DownloadCheckPurv()
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
		registerSQLFunction( "getDownloadCheckPurvInfo", DaoFunction.SQL_ROWSET, "����roleid����ȡ����Ȩ���б�" );
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
	
	public SqlStatement getDownloadCheckPurvInfo( TxnContext request, DataBus inputData )
	{
		String roleList = request.getRecord("roleInfo").getValue("role-list");
		SqlStatement stmt = new SqlStatement( );
		String sql = "select t.*, p.agency_id from download_check_purv t,download_purv p where " +
				"t.download_purv_id = p.download_purv_id and t.roleid in (" + roleList + ")";
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( "select count(*) from (" + sql + ")" );
		return stmt;
	}
}

