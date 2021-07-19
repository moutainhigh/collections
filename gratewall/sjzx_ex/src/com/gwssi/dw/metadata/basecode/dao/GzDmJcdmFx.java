package com.gwssi.dw.metadata.basecode.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[gz_dm_jcdm_fx]�Ĵ�����
 * @author Administrator
 *
 */
public class GzDmJcdmFx extends BaseTable
{
	public GzDmJcdmFx()
	{
		
	}
	
	/**
	 * ע���û��Զ����SQL���
	 */
	protected void register( )
	{
		registerSQLFunction( "getFirstNode", DaoFunction.SQL_ROWSET, "��ȡ��һ��ڵ�" );
		registerSQLFunction( "getSecondNode", DaoFunction.SQL_ROWSET, "��ȡ�ڶ���ڵ�" );
		registerSQLFunction( "selectSx", DaoFunction.SQL_SELECT, "�õ�˳����" );
		registerSQLFunction( "selectFjd", DaoFunction.SQL_SELECT, "�����Ƿ�Ϊ���һ���ӽ��" );
		registerSQLFunction( "getSerchNode", DaoFunction.SQL_ROWSET, "�������ݷ���ڵ�ģ����ѯ" );
		registerSQLFunction( "verifyJcsjfxdm", DaoFunction.SQL_SELECT, "���������������" );
		registerSQLFunction( "selectZb", DaoFunction.SQL_SELECT, "������������Ƿ�����" );
		registerSQLFunction( "selectFz", DaoFunction.SQL_SELECT, "������������Ƿ�����" );
		registerSQLFunction( "rightQuery", DaoFunction.SQL_ROWSET, "��ģ����ѯ" );
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
	public SqlStatement loadGzDmJcdmFxList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from gz_dm_jcdm_fx" );
		stmt.setCountStmt( "select count(*) from gz_dm_jcdm_fx" );
		return stmt;
	}
	 */
	
	public SqlStatement getFirstNode( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement();
		DataBus data=request.getRecord("select-key");
		String jc_dm_id=data.getValue("jc_dm_id");
		stmt.addSqlStmt( "select jc_dm_id, jcsjfx_id,jcsjfx_mc, jcsjfx_dm, jcsjfx_cjm,jcsjfx_fjd," +
		"szcc,xssx,sfmx,fx_ms,sy_zt  from gz_dm_jcdm_fx where jc_dm_id='"+jc_dm_id+"' and (jcsjfx_fjd='' or jcsjfx_fjd is null) order by xssx"  );
		stmt.setCountStmt("select count(*) from gz_dm_jcdm_fx where jc_dm_id='"+jc_dm_id+"' and (jcsjfx_fjd='' or jcsjfx_fjd is null)" );
		return stmt;
	}
	
	public SqlStatement getSecondNode(TxnContext request,DataBus inputData)
	{
		
		SqlStatement stmt=new SqlStatement();
		DataBus dataBus=request.getRecord("select-key");
		String jcsjfx_fjd=dataBus.getValue("jcsjfx_fjd"); 
		stmt.addSqlStmt("select jc_dm_id, jcsjfx_id,jcsjfx_mc, jcsjfx_dm, jcsjfx_cjm,jcsjfx_fjd," +
				"szcc,xssx,sfmx,fx_ms,sy_zt  from gz_dm_jcdm_fx where jcsjfx_fjd='"+jcsjfx_fjd+"' order by xssx");
		stmt.setCountStmt("select count(*) from gz_dm_jcdm_fx where jcsjfx_fjd='"+jcsjfx_fjd+"'");
		return stmt;
	}
	
	public SqlStatement selectSx(TxnContext request,DataBus inputData)
	{		
		
		SqlStatement stmt=new SqlStatement();
		DataBus jcsj_idDataBus=request.getRecord("record");
		String jcsjfx_id=jcsj_idDataBus.getValue("jcsjfx_id");		
		String jc_dm_id=jcsj_idDataBus.getValue("jc_dm_id");
		String sql = "";
		if(jcsjfx_id!=null&&!jcsjfx_id.equals("")){
			sql = "select Max(xssx)+1 as xssx from gz_dm_jcdm_fx where jc_dm_id='"+jc_dm_id+"' and jcsjfx_fjd='"+jcsjfx_id+"'";
		}else{
			sql = "select Max(xssx)+1 as xssx from gz_dm_jcdm_fx where jc_dm_id='"+jc_dm_id+"' and ( jcsjfx_fjd='' or jcsjfx_fjd is null ) ";
		}
		stmt.addSqlStmt(sql);		
		return stmt;
	}
	
	public SqlStatement selectFjd(TxnContext request,DataBus inputData)
	{		
		
		SqlStatement stmt=new SqlStatement();
		DataBus fzx_idDataBus=request.getRecord("record");
		String jcsjfx_fjd=fzx_idDataBus.getValue("jcsjfx_fjd");	
		String jc_dm_id=fzx_idDataBus.getValue("jc_dm_id");
		String sql = "";
		if(jcsjfx_fjd!=null&&!jcsjfx_fjd.equals("")){
			sql = "select count(*) as count from gz_dm_jcdm_fx where jc_dm_id='"+jc_dm_id+"' and jcsjfx_id='"+jcsjfx_fjd+"'";
		}else{
			sql = "select count(*) as count from gz_dm_jcdm_fx where jc_dm_id='"+jc_dm_id+"' and ( jcsjfx_id='' or jcsjfx_id is null ) ";
		}
		stmt.addSqlStmt(sql);		
		return stmt;
	}	
	/**
	 * ��ȡģ����ѯ�ڵ����
	 * @param request ���׵�������
	 * @param inputData ������������ڵ�
	 * @return
	 */
	public SqlStatement getSerchNode( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		DataBus dataBus = request.getRecord("select-key");
		String jcsjfx_mc = dataBus.getValue("jcsjfx_mc");
		String jc_dm_id = dataBus.getValue("jc_dm_id");
		String sql = "select jc_dm_id, jcsjfx_id,jcsjfx_mc, jcsjfx_dm, jcsjfx_cjm,jcsjfx_fjd," +
				" szcc,xssx,sfmx,fx_ms,sy_zt  from gz_dm_jcdm_fx where jcsjfx_mc like'%"+jcsjfx_mc+"%' " +
				" and jc_dm_id='"+jc_dm_id+"'  order by xssx";
		stmt.addSqlStmt( sql );
		stmt.setCountStmt( "select count(*) from gz_dm_jcdm_fx where jcsjfx_mc like'%"+jcsjfx_mc+"%' " +
				" and jc_dm_id='"+jc_dm_id+"'  order by xssx" );
		return stmt;
	}
	
	/**
	 * ��֤�������ݷ�������Ƿ����
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement verifyJcsjfxdm(TxnContext request,DataBus inputData)
	{		
		
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("record");
		String jc_dm_id = dataBus.getValue("jc_dm_id");
		String jcsjfxdm = dataBus.getValue("jcsjfx_dm");
		stmt.addSqlStmt("select count(*) as count from gz_dm_jcdm_fx where jc_dm_id='"+jc_dm_id+"'" +
				" and jcsjfx_dm='"+jcsjfxdm+"'");		
		return stmt;
	}	
	
	/**
	 * ��֤�������ݷ�������Ƿ�ָ������
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement selectZb(TxnContext request,DataBus inputData)
	{				
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("record");
		String jcsjfx_id = dataBus.getValue("jcsjfx_id");
		stmt.addSqlStmt("select count(*) as count from GZ_ZB_ZB where jcsjfx_id='"+jcsjfx_id+"'" );				
		return stmt;
	}
	
	/**
	 * ��֤�������ݷ�������Ƿ񱻷�������
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement selectFz(TxnContext request,DataBus inputData)
	{				
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("record");
		String jcsjfx_id = dataBus.getValue("jcsjfx_id");
		stmt.addSqlStmt("select count(*) as count from GZ_ZB_FZFX where jcsjfx_id='"+jcsjfx_id+"'" );				
		return stmt;
	}
	
	/**
	    * ��ģ����ѯ
	    * @param request
	    * @param inputData
	    * @return
	    * @throws TxnException
	    */
		public SqlStatement rightQuery(TxnContext request,DataBus inputData) throws TxnException
		{	   
			SqlStatement stmt = new SqlStatement();
			
			String jc_dm_id = request.getRecord("select-key").getValue("jc_dm_id");
			String jcsjfx_dm = request.getRecord("select-key").getValue("jcsjfx_dm");
			String jcsjfx_mc = request.getRecord("select-key").getValue("jcsjfx_mc");
			
			StringBuffer querySql = new StringBuffer("select jcsjfx_id, jc_dm_id, jcsjfx_dm, jcsjfx_mc, jcsjfx_cjm, jcsjfx_fjd, szcc, xssx, sfmx, fx_ms, sy_zt from gz_dm_jcdm_fx where 1=1 ");
			
			if(jc_dm_id!=null && !"".equals(jc_dm_id)){
				querySql.append(" and jc_dm_id = '").append(jc_dm_id).append("'");
			}
			
			if(jcsjfx_dm!=null && !"".equals(jcsjfx_dm)){
				querySql.append(" and jcsjfx_dm like '").append(jcsjfx_dm).append("%'");
			}
			
			if(jcsjfx_mc!=null && !"".equals(jcsjfx_mc)){
				querySql.append(" and jcsjfx_mc like '%").append(jcsjfx_mc).append("%'");
			}
			
			querySql.append(" order by jcsjfx_dm ");
			stmt.addSqlStmt(querySql.toString());
			stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
			return stmt;
		}
	
}

