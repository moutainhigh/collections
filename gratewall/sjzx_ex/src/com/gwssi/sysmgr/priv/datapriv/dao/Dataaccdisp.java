package com.gwssi.sysmgr.priv.datapriv.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[dataaccdisp]�Ĵ�����
 * @author Administrator
 *
 */
public class Dataaccdisp extends BaseTable
{
	public Dataaccdisp()
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
		// table.executeFunction( "loadDataaccdispList", context, inputNode, outputNode );
		registerSQLFunction( "loadRoleTxnDataAcc", DaoFunction.SQL_ROWSET, "���ݽ�ɫ���ʵĽ����б��ȡ����Ȩ���б�" );
		registerSQLFunction( "loadRoleDataAcc", DaoFunction.SQL_ROWSET, "���ݽ�ɫ�б��ȡ����Ȩ���б�" );
		registerSQLFunction( "loadRoleCustomAcc", DaoFunction.SQL_ROWSET, "���ݽ�ɫ���ʵĽ���ID��ȡ�Զ���Ȩ����ID" );
		registerSQLFunction( "deleteDispByObjIdAndDispObj", DaoFunction.SQL_DELETE, "���ݽ�ɫ����IDɾ�����������Ȩ��" );
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
	public SqlStatement loadDataaccdispList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from dataaccdisp" );
		stmt.setCountStmt( "select count(*) from dataaccdisp" );
		return stmt;
	}
	 */
	
	/**
	 * ����SQL���
	 */
	private String[] generateRoleTxnDataAccSql(TxnContext request) throws TxnException{
		String roleaccids = request.getRecord("select-key").getValue("roleaccids");
		int roleaccidSize = roleaccids.split(",").length;
		String field = "select distinct a.DATAACCGRPID, a.DATAACCDISPOBJ," +
				"c.DATAACCGRPNAME, c.DATAACCRULE, c.DATAACCTYPE";
		String from = " from DATAACCDISP a left join DATAACCGROUP c " +
				"on a.DATAACCGRPID = c.DATAACCGRPID ";
		String count = "select count(*) ";
		String cond = "where a.DATAACCDISPOBJ = '2' and a.objectid in (" + 
				roleaccids + ") and c.dataacctype = '1' " +
				" and (select count(*) from DATAACCDISP b where b.DATAACCDISPOBJ = '2'" +
				" and a.dataaccgrpid" +
				" = b.dataaccgrpid and a.dataaccdispobj = b.dataaccdispobj" +
				" and b.objectid in(" + roleaccids + ")) = " + roleaccidSize;

		return new String[]{
				field + from + cond,
				count + from + cond
		};
	}
	
	/**
	 * ���ݽ�ɫ���ʵĽ����б��ȡ����Ȩ���б�
	 * @param request ���׵�������
	 * @param inputData ������������ڵ�
	 * @return
	 * @throws TxnException 
	 */
	public SqlStatement loadRoleTxnDataAcc( TxnContext request, DataBus inputData ) throws TxnException
	{
		String[] result = generateRoleTxnDataAccSql(request);
		String sql = result[0];
		String countSql = result[1];		
		SqlStatement stmt = new SqlStatement( );
		stmt.setPageRows(-1);
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	}

	/**
	 * ����SQL���
	 */
	private String[] generateRoleDataAccSql(TxnContext request) throws TxnException{
		String roleids = request.getRecord("select-key").getValue("roleids");
		int roleaccidSize = roleids.split(",").length;
		String field = "select distinct a.DATAACCGRPID, a.DATAACCDISPOBJ," +
				"c.DATAACCGRPNAME, c.DATAACCRULE, c.DATAACCTYPE";
		String from = " from DATAACCDISP a left join DATAACCGROUP c " +
				"on a.DATAACCGRPID = c.DATAACCGRPID ";
		String count = "select count(*) ";
		String cond = "where a.DATAACCDISPOBJ = '1' and a.objectid in (" + 
				roleids + ") and c.dataacctype = '1' " +
				" and (select count(*) from DATAACCDISP b where b.DATAACCDISPOBJ = '1'" +
				" and a.dataaccgrpid" +
				" = b.dataaccgrpid and a.dataaccdispobj = b.dataaccdispobj" +
				" and b.objectid in(" + roleids + ")) = " + roleaccidSize;

		return new String[]{
				field + from + cond,
				count + from + cond
		};
	}
	
	/**
	 * ���ݽ�ɫ���ʵĽ����б��ȡ����Ȩ���б�
	 * @param request ���׵�������
	 * @param inputData ������������ڵ�
	 * @return
	 * @throws TxnException 
	 */
	public SqlStatement loadRoleDataAcc( TxnContext request, DataBus inputData ) throws TxnException
	{
		String[] result = generateRoleDataAccSql(request);
		String sql = result[0];
		String countSql = result[1];		
		SqlStatement stmt = new SqlStatement( );
		stmt.setPageRows(-1);
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	}

	
	/**
	 * ����SQL���
	 */
	private String generateDeleteSql(TxnContext request) throws TxnException{
		String inputNode = "record";
		String sql = "delete from dataaccdisp where objectid = " +
		request.getRecord(inputNode).getValue("objectid") +
		" and dataaccdispobj = '" + request.getRecord("record").getValue("dataaccdispobj")
		+ "'";
		
		return sql;
	}
	
	/**
	 * ������������ɾ������Ȩ����
	 * @param request ���׵�������
	 * @param inputData ������������ڵ�
	 * @return
	 * @throws TxnException 
	 */
	public SqlStatement deleteDispByObjIdAndDispObj( TxnContext request, DataBus inputData ) throws TxnException
	{
		String result = generateDeleteSql(request);
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( result );
		return stmt;
	}

	/**
	 * ����SQL���
	 */
	private String[] generateRoleCustomAccSql(TxnContext request) throws TxnException{
		String roleaccids = request.getRecord("select-key").getValue("objectids");
		String dataaccdispobj = request.getRecord("select-key").getValue("dataaccdispobj");
		String field = "select a.OBJECTID, a.DATAACCGRPID, a.DATAACCDISPOBJ," +
				"c.DATAACCGRPNAME, c.DATAACCRULE, c.DATAACCTYPE";
		String from = " from DATAACCDISP a inner join DATAACCGROUP c " +
				"on a.DATAACCGRPID = c.DATAACCGRPID ";
		String count = "select count(*) ";
		String cond = "where a.DATAACCDISPOBJ = '" + dataaccdispobj + "' and a.objectid in (" + 
						roleaccids + ") and c.dataacctype = '0'";

		return new String[]{
				field + from + cond,
				count + from + cond
		};
	}
	
	/**
	 * ���ݽ�ɫ���ʵĽ���ID��ȡ�Զ���Ȩ����ID
	 * @param request ���׵�������
	 * @param inputData ������������ڵ�
	 * @return
	 * @throws TxnException 
	 */
	public SqlStatement loadRoleCustomAcc( TxnContext request, DataBus inputData ) throws TxnException
	{
		String[] result = generateRoleCustomAccSql(request);
		String sql = result[0];
		String countSql = result[1];		
		SqlStatement stmt = new SqlStatement( );
		stmt.setPageRows(-1);
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	}
}

