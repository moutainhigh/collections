package com.gwssi.sysmgr.priv.func.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[operrolefun]�Ĵ�����
 * @author Administrator
 *
 */
public class Operrolefun extends BaseTable
{
	public Operrolefun()
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
		// table.executeFunction( "loadOperrolefunList", context, inputNode, outputNode );
		//XXX: registerSQLFunction( "loadOperrolefunList", DaoFunction.SQL_ROWSET, "��ȡ��ɫ����Ȩ���б�" );
		registerSQLFunction( "loadRoleTxn", DaoFunction.SQL_ROWSET, "���ݽ�ɫID��ȡ���״�����Ϣ" );
		registerSQLFunction( "selectFunc", DaoFunction.SQL_ROWSET, "���ݽ�ɫID��ȡ���״�����Ϣ" );	
		registerSQLFunction( "selectOperRoleId", DaoFunction.SQL_ROWSET, "����������ȡ���״�����Ϣ" );
		registerSQLFunction( "deleteOperRoleFun", DaoFunction.SQL_DELETE, "��������ɾ�����״�����Ϣ" );	
		registerSQLFunction( "deleteDispByObjIdS", DaoFunction.SQL_DELETE, "��������ɾ������Ȩ�޷�����Ϣ" );		
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
	public SqlStatement loadOperrolefunList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from operrolefun" );
		stmt.setCountStmt( "select count(*) from operrolefun" );
		return stmt;
	}
	 */
	
	/**
	 * ����SQL���
	 */
	private String[] generateRoleTxnSql(TxnContext request) throws TxnException{
		String field = "select A.ROLEACCID,A.ROLEID,A.TXNCODE,B.TXNNAME ";
		String from = " from OPERROLEFUN A LEFT JOIN " +
				"(select max(TXNNAME) AS TXNNAME,TXNCODE  from functxn_new " +
				"GROUP BY TXNCODE) B ON A.TXNCODE = B.TXNCODE";
		String count = "select count(*) ";
		String roleId = request.getRecord("select-key").getValue("roleid");
		String cond = " where a.roleid = " + roleId;
		String orderBy = " order by a.txncode";
		
		return new String[]{
				field + from + cond + orderBy,
				count + from + cond
		};
	}
	
	/**
	 * ���ݽ�ɫ��ȡ��ɫ�ɷ��ʵĽ��״���
	 * @param request ���׵�������
	 * @param inputData ������������ڵ�
	 * @return
	 * @throws TxnException 
	 */
	public SqlStatement loadRoleTxn( TxnContext request, DataBus inputData ) throws TxnException
	{
		String[] result = generateRoleTxnSql(request);
		String sql = result[0];
		String countSql = result[1];	
		System.out.println(sql);
		SqlStatement stmt = new SqlStatement( );
		stmt.setPageRows(-1);
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( countSql );
		return stmt;
	}
	/**
	 * ��ѯ����
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement selectFunc(TxnContext request,DataBus inputData){
		
		SqlStatement stmt=new SqlStatement();
		DataBus dataBus=request.getRecord("select-key");
		String funcList=dataBus.getValue("funcList"); 	
		String sql = "select funccode  from functxn_new  " +
		" where funccode not in (" + funcList + ")"  ;
		stmt.addSqlStmt(sql);
		return stmt;
	}
	/**
	 * ��ȡ���״���
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement selectOperRoleId(TxnContext request,DataBus inputData){
		
		SqlStatement stmt=new SqlStatement();
		DataBus dataBus=request.getRecord("select-key");
		String roleid=dataBus.getValue("roleid"); 	
		String txncode=dataBus.getValue("txncode");
		String sql = "select RoleAccId  from OperRoleFun  " +
		" where roleid =" + roleid + " and TxnCode like '" + txncode + "%'"  ;
		stmt.addSqlStmt(sql);
		return stmt;
	}
	/**
	 * ɾ�����״���
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement deleteOperRoleFun(TxnContext request,DataBus inputData){
		
		SqlStatement stmt=new SqlStatement();
		DataBus dataBus=request.getRecord("select-key");
		String roleid=dataBus.getValue("roleid"); 	
		String txncode=dataBus.getValue("txncode");
		String sql = "delete  from OperRoleFun  " +
		" where roleid =" + roleid + " and TxnCode like '" + txncode + "%'"  ;
		stmt.addSqlStmt(sql);
		return stmt;
	}
	/**
	 * ɾ��Ȩ�޷���
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement deleteDispByObjIdS(TxnContext request,DataBus inputData){
		
		SqlStatement stmt=new SqlStatement();
		DataBus dataBus=request.getRecord("select-key");
		String objectid=dataBus.getValue("objectid"); 	
		String sql = "delete from dataaccdisp where objectid in (" +
		objectid +" )";
		stmt.addSqlStmt(sql);
		return stmt;
	}		
}

