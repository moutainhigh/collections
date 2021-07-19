package com.gwssi.dw.metadata.msurunit.dao;

import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.component.exception.TxnDataException;

/**
 * ���ݱ�[gz_zb_jldw_fl]�Ĵ�����
 * @author Administrator
 *
 */
public class GzZbJldwFl extends BaseTable
{
	public GzZbJldwFl()
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
		// table.executeFunction( "loadGzZbJldwFlList", context, inputNode, outputNode );
         registerSQLFunction( "queryrowset", DaoFunction.SQL_ROWSET, "��ȡ������λ�����б�" );
         registerSQLFunction( "query", DaoFunction.SQL_ROWSET, "��ѯ������λ" );
         registerSQLFunction( "selectSjfx", DaoFunction.SQL_ROWSET, "��ѯ������λ" );
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
		String dwlb_cn_mc=data.getValue("dwlb_cn_mc");
		SqlStatement stmt = new SqlStatement( );
		String sql="select dwlb_dm,dwlb_cn_mc,dwlb_cn_ms,dwlb_en_mc,dwlb_en_ms from gz_zb_jldw_fl where dwlb_dm like '%'";
		String sql2= "select count(*) from gz_zb_jldw_fl where dwlb_dm like '%'";
		if (dwlb_dm == null || dwlb_dm.length() <= 0) 
        {

		} 
        else 
        {
			sql = sql + "and dwlb_dm = '" + dwlb_dm + "'";
			sql2 = sql2 + "and dwlb_dm = '" + dwlb_dm + "'";
		}
		if (dwlb_cn_mc == null || dwlb_cn_mc.length() <= 0) 
		{

		} 
		else 
		{
			sql = sql + "and dwlb_cn_mc = '" + dwlb_cn_mc + "'";
			sql2 = sql2 + "and dwlb_cn_mc = '" + dwlb_cn_mc + "' ";
		}
		sql=sql + "order by dwlb_dm";
		// System.out.println(sql2);
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( sql2 );
		return stmt;
	}
	
	//�ڼ�����λ�����ѯָ���ļ�����λ�������µļ�����λ
	public SqlStatement query( TxnContext request, DataBus inputData ) throws TxnException
	{
		DataBus data=request.getRecord("select-key");
		String dwlbdm=data.getValue("dwlb_dm");
		if(dwlbdm==null||dwlbdm.length()<=0)
		{
			throw new TxnDataException("","������λ������Ϊ��");
		}	
		String sql="select jldw_dm from GZ_ZB_JLDW where DWLB_DM='"+dwlbdm+"'";
		String sql2="select count(*) from GZ_ZB_JLDW where DWLB_DM='"+dwlbdm+"'";
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( sql2 );
		return stmt;
	}
	
//	�ڼ�����λ�����ѯָ���ļ�����λ�������µļ�����λ����	
	public SqlStatement selectSjfx(TxnContext request,DataBus inputData)
	{		
		
		SqlStatement stmt=new SqlStatement();
		DataBus fzx_idDataBus=request.getRecord("select-key");
		String dwlb_dm=fzx_idDataBus.getValue("dwlb_dm");
		stmt.addSqlStmt("select count(*) as count from GZ_ZB_JLDW where dwlb_dm='"+dwlb_dm+"'");		
		return stmt;
	}
	
}

