package cn.gwssi.dw.rd.metadata.dao;

import org.bouncycastle.asn1.ocsp.Request;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[sys_rd_system]�Ĵ�����
 * @author Administrator
 *
 */
public class SysRdSystem extends BaseTable
{
	public SysRdSystem()
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
		registerSQLFunction("selectsortsysrdsystem",DaoFunction.SQL_SELECT,"��ѯ��������");
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
	 * ��ѯ��������
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement selectsortsysrdsystem(TxnContext request,DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String sys_rd_data_source_id = inputData.getValue("sys_rd_data_source_id");
		String db_name = inputData.getValue("db_name");
		String db_username = inputData.getValue("db_username");
		stmt.addSqlStmt("select max(sort) as num from sys_rd_system where sys_rd_data_source_id='"+sys_rd_data_source_id+"' and db_name='"+db_name+"' and db_username='"+db_username+"'");
		stmt.setCountStmt("select count(*) from sys_rd_system where sys_rd_data_source_id='"+sys_rd_data_source_id+"' and db_name='"+db_name+"' and db_username='"+db_username+"'");
		return stmt;
	}
	
}

