package com.gwssi.dw.runmgr.etl.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[sys_etl_wf]�Ĵ�����
 * @author Administrator
 *
 */
public class SysEtlWf extends BaseTable
{
	public SysEtlWf()
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
		// table.executeFunction( "loadSysEtlWfList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadSysEtlWfList", DaoFunction.SQL_ROWSET, "��ȡ��ȡ��������б�" );
		registerSQLFunction("queryEtlWf", DaoFunction.SQL_ROWSET, "��ѯ��ȡ�����etl��Ŀ�Ĺ�����" );
		registerSQLFunction("querySingleEtlWf", DaoFunction.SQL_SELECT, "��ѯһ����ȡ�����etl��Ŀ�Ĺ�����" );
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
	 * ��ѯ��ȡ�����etl��Ŀ�Ĺ�����
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryEtlWf( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		DataBus dataBus = request.getRecord("select-key");
		String rep_id = dataBus.getValue("rep_id");
		StringBuffer buffSql = new StringBuffer("select t1.sys_etl_wf_id as sys_etl_wf_id,t1.rep_id as rep_id,t1.rep_foldername as rep_foldername,t1.wf_name as wf_name,t1.wf_ms as wf_ms," +
				"t2.etl_hostname as etl_hostname,t2.etl_portno as etl_portno,t2.etl_domainname as etl_domainname,t2.rep_name_en as rep_name_en,t2.rep_name_cn as rep_name_cn,t2.user_id as user_id,t2.user_password as user_password " +
				"from sys_etl_wf t1,sys_etl_rep t2 where t1.rep_id=t2.sys_etl_rep_id");
		StringBuffer buffSqlCount = new StringBuffer("select count(1) from sys_etl_wf t1,sys_etl_rep t2 where t1.rep_id=t2.sys_etl_rep_id");
		if(rep_id!=null&&!"".equals(rep_id)){
			buffSql.append(" and t1.rep_id='").append(rep_id).append("'");
			buffSqlCount.append(" and t1.rep_id='").append(rep_id).append("'");
		}
		stmt.addSqlStmt(buffSql.toString());
		stmt.setCountStmt(buffSqlCount.toString());
		return stmt;
	}
	/**
	 * ��ѯһ����ȡ�����etl��Ŀ�Ĺ�����
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement querySingleEtlWf(TxnContext request, DataBus inputData ){
		SqlStatement stmt = new SqlStatement( );
		DataBus dataBus = request.getRecord("record");
		String rep_id = dataBus.getValue("rep_id");
		String wf_id = dataBus.getValue("sys_etl_wf_id");
		StringBuffer buffSql = new StringBuffer("select t1.sys_etl_wf_id as sys_etl_wf_id,t1.rep_id as rep_id,t1.rep_foldername as rep_foldername,t1.wf_name as wf_name,t1.wf_ms as wf_ms," +
				"t2.etl_hostname as etl_hostname,t2.etl_portno as etl_portno,t2.etl_domainname as etl_domainname,t2.rep_name_en as rep_name_en,t2.rep_name_cn as rep_name_cn,t2.user_id as user_id,t2.user_password as user_password " +
				"from sys_etl_wf t1,sys_etl_rep t2 where t1.rep_id=t2.sys_etl_rep_id");
		if(rep_id!=null&&!"".equals(rep_id)){
			buffSql.append(" and t1.rep_id='").append(rep_id).append("'");
		}
		if(wf_id!=null&&!"".equals(wf_id)){
			buffSql.append(" and t1.sys_etl_wf_id='").append(wf_id).append("'");
		}		
		stmt.addSqlStmt(buffSql.toString());
		return stmt;	
	}
	/**
	 * XXX:�û��Զ����SQL���
	 * ���ڶ��¼�Ĳ�ѯ��䣬��Ҫ����������䣺��ѯ����ȡ��¼���������
	 * ������������䣬ֻ��Ҫ����һ�����
	 * @param request ���׵�������
	 * @param inputData ������������ڵ�
	 * @return
	public SqlStatement loadSysEtlWfList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from sys_etl_wf" );
		stmt.setCountStmt( "select count(*) from sys_etl_wf" );
		return stmt;
	}
	 */
	
}

