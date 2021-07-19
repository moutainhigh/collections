package com.gwssi.sysmgr.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[sys_popwindow]�Ĵ�����
 * @author Administrator
 *
 */
public class SysPopwindow extends BaseTable
{
	public SysPopwindow()
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
		// table.executeFunction( "loadSysPopwindowList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadSysPopwindowList", DaoFunction.SQL_ROWSET, "��ȡ���������б�" );
		registerSQLFunction( "getPopWindowCountByRoles", DaoFunction.SQL_ROWSET, "���ݽ�ɫ�б��ȡ��������" );
		registerSQLFunction( "getPopWindowContentByRoles", DaoFunction.SQL_ROWSET, "���ݽ�ɫ�б��ȡ������Ϣ" );
		registerSQLFunction( "getTzList", DaoFunction.SQL_ROWSET, "��ȡ�����б�" );
		
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
	
	
	public SqlStatement getPopWindowCountByRoles( TxnContext request, DataBus inputData )
	{
		String roleList = request.getRecord("oper-data").getValue("role-list");
		if(roleList.endsWith(";")){
			roleList = roleList.substring(0, roleList.length() - 1);
		}
		String[] roles = roleList.split(";");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select count(*) as countNumber from sys_popwindow where ");
		String today = com.gwssi.common.util.DateUtil.getToday();
		sqlBuffer.append("expire_date >= '" + today + "' and (");
		for(int i = 0 ; i < roles.length; i++){
			if(i != 0){
				sqlBuffer.append(" or ");
			}
			sqlBuffer.append("roles like '%" + roles[i] + "%'");
		}
		sqlBuffer.append(")");
		
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( sqlBuffer.toString() );
//		stmt.setCountStmt( "select count(*) from sys_popwindow" );
		return stmt;
	}
	
	public SqlStatement getPopWindowContentByRoles( TxnContext request, DataBus inputData )
	{
		String roleList = request.getRecord("oper-data").getValue("role-list");
		if(roleList.endsWith(";")){
			roleList = roleList.substring(0, roleList.length() - 1);
		}
		String[] roles = roleList.split(";");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from sys_popwindow where ");
		String today = com.gwssi.common.util.DateUtil.getToday();
		
		//sqlBuffer.append("expire_date >= '" + today + "' and (");
		sqlBuffer.append("1=1 and (");
		for(int i = 0 ; i < roles.length; i++){
			if(i != 0){
				sqlBuffer.append(" or ");
			}
			sqlBuffer.append("roles like '%" + roles[i] + "%'");
		}
		sqlBuffer.append(")");
		
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( sqlBuffer.toString() + " order by publish_date desc" );
		System.out.println(sqlBuffer.toString());
		// stmt.setCountStmt( "select count(*) from " );
		return stmt;
	}
	
	 public SqlStatement getTzList( TxnContext request, DataBus inputData )
	   {   		   
	         SqlStatement stmt=new SqlStatement();	
	         String sql = "Select * from (select * from sys_popwindow  order by publish_date desc) Where Rownum<11" ;
		     stmt.addSqlStmt(sql);
		     return stmt;
	   } 
	
	/**
	 * XXX:�û��Զ����SQL���
	 * ���ڶ��¼�Ĳ�ѯ��䣬��Ҫ����������䣺��ѯ����ȡ��¼���������
	 * ������������䣬ֻ��Ҫ����һ�����
	 * @param request ���׵�������
	 * @param inputData ������������ڵ�
	 * @return
	public SqlStatement loadSysPopwindowList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from sys_popwindow" );
		stmt.setCountStmt( "select count(*) from sys_popwindow" );
		return stmt;
	}
	 */
	
}

