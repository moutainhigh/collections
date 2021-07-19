package com.gwssi.resource.share.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[res_share_dataitem]�Ĵ�����
 * @author Administrator
 *
 */
public class ResShareDataitem extends BaseTable
{
	public ResShareDataitem()
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
		// table.executeFunction( "loadResShareDataitemList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadResShareDataitemList", DaoFunction.SQL_ROWSET, "��ȡ������������Ϣ���б�" );
		registerSQLFunction( "queryCodeMusterById", DaoFunction.SQL_ROWSET, "��ѯ���뼯��Ϣ��" );

	}
	
	
	public SqlStatement queryCodeMusterById(TxnContext request,
			DataBus inputData) throws TxnException
	{
		DataBus db = request.getRecord("select-key");
		String code_table = db.getValue("code_table");
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		sql.append(" select g.jcsjfx_dm,g.jcsjfx_mc from gz_dm_jcdm t,gz_dm_jcdm_fx g where ")
				.append("t.jc_dm_id=g.jc_dm_id and t.jc_dm_dm='");
		sql.append(code_table);
		sql.append("' order by g.jcsjfx_dm");
		stmt.addSqlStmt(sql.toString());
		/*stmt.setCountStmt(sql.toString());*/
		stmt.setCountStmt( "select count(*) from ("+sql.toString()+")" );

//		System.out.println("---------------"+sql.toString());
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
	public SqlStatement loadResShareDataitemList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from res_share_dataitem" );
		stmt.setCountStmt( "select count(*) from res_share_dataitem" );
		return stmt;
	}
	 */
	
}

