package com.gwssi.dw.runmgr.services.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[sys_svr_service_param]�Ĵ�����
 * @author Administrator
 *
 */
public class SysSvrServiceParam extends BaseTable
{
	public SysSvrServiceParam()
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
		// table.executeFunction( "loadSysSvrServiceParamList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadSysSvrServiceParamList", DaoFunction.SQL_ROWSET, "��ȡ�����������б�" );
		registerSQLFunction( "deleteAll", DaoFunction.SQL_DELETE, "ɾ�����з������" );
	}
	
	public SqlStatement deleteAll(TxnContext request, DataBus inputData){
		SqlStatement stmt = new SqlStatement( );
		DataBus dataBus = request.getRecord("record");
		String sys_svr_service_id = dataBus.getValue("sys_svr_service_id");
		StringBuffer newId = new StringBuffer();
		if(sys_svr_service_id != null && !sys_svr_service_id.equals("")){
			String[] ids = sys_svr_service_id.split(",");
			for(int i = 0; i < ids.length; i++){
				newId.append("'").append(ids[i]).append("'");
				if(i != ids.length - 1){
					newId.append(", ");
				}
			}
			
		}
		stmt.addSqlStmt( "DELETE FROM sys_svr_service_param where SYS_SVR_SERVICE_ID IN (" + newId + ")" );
		//stmt.setCountStmt( "select count(SYS_SVR_SERVICE_ID) from SYS_SVR_CONFIG where SYS_SVR_SERVICE_ID IN (" + newId + ")" );
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
	public SqlStatement loadSysSvrServiceParamList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from sys_svr_service_param" );
		stmt.setCountStmt( "select count(*) from sys_svr_service_param" );
		return stmt;
	}
	 */
	
}

