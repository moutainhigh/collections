package com.gwssi.resource.share.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[res_share_table]�Ĵ�����
 * @author Administrator
 *
 */
public class ResShareTable extends BaseTable
{
	public ResShareTable()
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
		// table.executeFunction( "loadResShareTableList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadResShareTableList", DaoFunction.SQL_ROWSET, "��ȡ�������Ϣ���б�" );
		registerSQLFunction( "quertShareTableByTopic", DaoFunction.SQL_ROWSET, "�������ѯ�������Ϣ��" );
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
	
	
	public SqlStatement quertShareTableByTopic( TxnContext request, DataBus inputData )
	{
		DataBus db = request.getRecord("select-key");
		String business_topics_id = db.getValue("business_topics_id");
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from res_share_table t where t.business_topics_id='");
		sql.append(business_topics_id);
		sql.append("'");
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( sql.toString() );
		stmt.setCountStmt( "select count(*) from ("+sql.toString()+")" );
		return stmt;
	}
	/**
	 * XXX:�û��Զ����SQL���
	 * ���ڶ��¼�Ĳ�ѯ��䣬��Ҫ����������䣺��ѯ����ȡ��¼���������
	 * ������������䣬ֻ��Ҫ����һ�����
	 * @param request ���׵�������
	 * @param inputData ������������ڵ�
	 * @return
	public SqlStatement loadResShareTableList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from res_share_table" );
		stmt.setCountStmt( "select count(*) from res_share_table" );
		return stmt;
	}
	 */
	
}

