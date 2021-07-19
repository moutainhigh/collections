package com.gwssi.dw.metadata.basecode.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[gz_dm_jcdm]�Ĵ�����
 * @author Administrator
 *
 */
public class GzDmJcdm extends BaseTable
{
	public GzDmJcdm()
	{
		
	}
	
	/**
	 * ע���û��Զ����SQL���
	 */
	protected void register( )
	{
		registerSQLFunction( "selectSjfx", DaoFunction.SQL_SELECT, "����������ݷ�������" );
		
		registerSQLFunction( "verifyJcdmdm", DaoFunction.SQL_SELECT, "���������������" );
		
		registerSQLFunction( "selectSystemCodeTable", DaoFunction.SQL_ROWSET, "��ѯϵͳ���뼯" );
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
	public SqlStatement loadGzDmJcdmList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from gz_dm_jcdm" );
		stmt.setCountStmt( "select count(*) from gz_dm_jcdm" );
		return stmt;
	}
	 */
	public SqlStatement selectSjfx(TxnContext request,DataBus inputData)
	{		
		
		SqlStatement stmt=new SqlStatement();
		DataBus fzx_idDataBus=request.getRecord("select-key");
		String jc_dm_id=fzx_idDataBus.getValue("jc_dm_id");
		
		stmt.addSqlStmt("select count(*) as count from gz_dm_jcdm_fx where jc_dm_id='"+jc_dm_id+"'");		
		return stmt;
	}	
	/**
	 * ��֤������������Ƿ����
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement verifyJcdmdm(TxnContext request,DataBus inputData)
	{		
		
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("record");
		String jc_dm_dm = dataBus.getValue("jc_dm_dm");
		stmt.addSqlStmt("select count(*) as count from gz_dm_jcdm where jc_dm_dm='"+jc_dm_dm+"'");		
		return stmt;
	}	
	
	/**
	    * ��ѯ�������뼯
	    * @param request
	    * @param inputData
	    * @return
	    * @throws TxnException
	    */
		public SqlStatement selectSystemCodeTable(TxnContext request,DataBus inputData) throws TxnException
		{	   
			SqlStatement stmt = new SqlStatement();
			
			String jc_dm_dm = request.getRecord("select-key").getValue("jc_dm_dm");
			String jc_dm_mc = request.getRecord("select-key").getValue("jc_dm_mc");
			String jc_dm_bzly = request.getRecord("select-key").getValue("jc_dm_bzly");
			String jcsjfx_mc = request.getRecord("select-key").getValue("jcsjfx_mc");
			StringBuffer querySql = new StringBuffer("select a.jc_dm_id,a.jc_dm_dm,a.jc_dm_mc,a.jc_dm_bzly,a.jc_dm_ms from gz_dm_jcdm a  ");
			
			querySql.append(" where 1=1 ");
			
			
			if(jc_dm_dm!=null && !"".equals(jc_dm_dm)){
				querySql.append(" and a.jc_dm_dm like '%").append(jc_dm_dm).append("%'");
			}
			
			if(jc_dm_mc!=null && !"".equals(jc_dm_mc)){
				querySql.append(" and a.jc_dm_mc like '%").append(jc_dm_mc).append("%'");
			}
			
			if(jc_dm_bzly!=null && !"".equals(jc_dm_bzly)){
				querySql.append(" and a.jc_dm_bzly ='").append(jc_dm_bzly).append("'");
			}
			
			if(jcsjfx_mc!=null && !"".equals(jcsjfx_mc)){
				querySql.append(" and a.jc_dm_id in ( select jc_dm_id from gz_dm_jcdm_fx where jcsjfx_mc = '").append(jcsjfx_mc).append("')");
			}
			
			
			
			
			querySql.append(" order by a.jc_dm_id ");
			stmt.addSqlStmt(querySql.toString());
			stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
			return stmt;
		}
}

