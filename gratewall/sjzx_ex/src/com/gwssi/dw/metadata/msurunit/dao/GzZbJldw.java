package com.gwssi.dw.metadata.msurunit.dao;

import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.dao.func.SqlStatement;


/**
 * ���ݱ�[gz_zb_jldw]�Ĵ�����
 * @author Administrator
 *
 */
public class GzZbJldw extends BaseTable
{
	public GzZbJldw()
	{
		
	}
	
	/**
	 * ע���û��Զ����SQL���
	 */
	protected void register( )
	{
		registerSQLFunction( "queryrowset", DaoFunction.SQL_ROWSET, "��ȡ������λ���б�" );
		registerSQLFunction( "iscandelete", DaoFunction.SQL_ROWSET, "��ѯָ���" );
		registerSQLFunction( "iscandelete2", DaoFunction.SQL_ROWSET, "��ѯָ�����Թ�����" );
		registerSQLFunction( "selectSjfx", DaoFunction.SQL_ROWSET, "��ѯ������λ�Ƿ�ʹ��" );
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
	 *  */
	public SqlStatement queryrowset( TxnContext request, DataBus inputData )
	{
		DataBus data=request.getRecord("select-key");
		String dwlb_dm=data.getValue("dwlb_dm");
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select jldw_dm,dwlb_dm,jldw_cn_mc,jldw_sjz,jldw_en_mc from gz_zb_jldw where dwlb_dm='"+dwlb_dm+"' order by jldw_dm" );
		stmt.setCountStmt( "select count(*) from gz_zb_jldw where dwlb_dm='"+dwlb_dm+"'");
		return stmt;
	}
	
	
////��ѯѡ�еļ�����λ��ָ������Ƿ��й���
	public SqlStatement iscandelete( TxnContext request, DataBus inputData ) throws TxnException
	{
		inputData=request.getRecord("select-key");
		String jldw_dm=inputData.getValue("jldw_dm");
		if(jldw_dm==null||jldw_dm.length()<=0)
		{
			throw new TxnDataException("","������λ����Ϊ��1");
		}
		SqlStatement stmt = new SqlStatement( );
		String sql1="select zb_id from GZ_ZB_ZB where JLDW_DM='"+jldw_dm+"'";
		String sql2="select count(*) from GZ_ZB_ZB where JLDW_DM='"+jldw_dm+"'";
		stmt.addSqlStmt( sql1 );
		stmt.setCountStmt(sql2 );
		return stmt;
	}
	
//	��ѯѡ�еļ�����λ��ָ�����Թ��������Ƿ��й���
	public SqlStatement iscandelete2( TxnContext request, DataBus inputData ) throws TxnException
	{
		inputData=request.getRecord("select-key");
		String jldw_dm=inputData.getValue("jldw_dm");
		if(jldw_dm==null||jldw_dm.length()<=0)
		{
			throw new TxnDataException("","������λ����Ϊ��2");
		}
		SqlStatement stmt = new SqlStatement( );
		String sql1="select gl_id from GZ_ZB_ZBSX where JLDW_DM='"+jldw_dm+"'";
		String sql2="select count(*) from GZ_ZB_ZBSX where JLDW_DM='"+jldw_dm+"'";
		stmt.addSqlStmt( sql1 );
		stmt.setCountStmt(sql2 );
		return stmt;
	}
	
	public SqlStatement selectSjfx(TxnContext request,DataBus inputData)
	{		
		
		SqlStatement stmt=new SqlStatement();
		DataBus fzx_idDataBus=request.getRecord("select-key");
		String jldw_dm=fzx_idDataBus.getValue("jldw_dm");
		stmt.addSqlStmt("select count(*) as count from gz_zb_zb,gz_zb_zbsx where gz_zb_zb.jldw_dm='"+jldw_dm+"'or gz_zb_zbsx.jldw_dm='"+jldw_dm+"'");		
		return stmt;
	}
	
}

