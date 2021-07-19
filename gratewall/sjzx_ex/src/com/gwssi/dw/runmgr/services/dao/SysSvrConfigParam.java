package com.gwssi.dw.runmgr.services.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[sys_svr_config_param]�Ĵ�����
 * @author Administrator
 *
 */
public class SysSvrConfigParam extends BaseTable
{
	public SysSvrConfigParam()
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
		// table.executeFunction( "loadSysSvrConfigParamList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadSysSvrConfigParamList", DaoFunction.SQL_ROWSET, "��ȡ�������ò����б�" );
		registerSQLFunction( "deleteConfigParam", DaoFunction.SQL_DELETE, "ɾ������������ò���" );
	}
	
	public SqlStatement deleteConfigParam( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		DataBus dataBus = request.getRecord("select-key");
		String cfgId = dataBus.getValue("sys_svr_config_id");
		String sql =  "DELETE FROM sys_svr_config_param WHERE sys_svr_config_id IN ("+cfgId+")";
		stmt.addSqlStmt(sql);
//		stmt.setCountStmt( "select count(1) from ("+sql+")" );
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
	public SqlStatement loadSysSvrConfigParamList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from sys_svr_config_param" );
		stmt.setCountStmt( "select count(*) from sys_svr_config_param" );
		return stmt;
	}
	 */
	
}

