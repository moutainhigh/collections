package com.gwssi.dw.runmgr.etl.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[etl_table_count]�Ĵ�����
 * @author Administrator
 *
 */
public class EtlTableCount extends BaseTable
{
	public EtlTableCount()
	{
		
	}
	
	/**
	 * ע���û��Զ����SQL���
	 */
	protected void register( )
	{
		registerSQLFunction( "queryCountList", DaoFunction.SQL_ROWSET, "���¼��ѯ" );
		registerSQLFunction("getValue", DaoFunction.SQL_ROWSET, "�õ���������");
		registerSQLFunction("queryCountByMc", DaoFunction.SQL_ROWSET, "��ѯ������");
	}
	
	/**
	 * ���¼��ѯ
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryCountList( TxnContext request, DataBus inputData ){		
		SqlStatement stmt = new SqlStatement();
		String sql = " select sum(t.i_count) As i_count ,sum(t.c_count) " +
				" As c_count , substr(to_char(max(t.etl_date),'yyyy-mm-dd'),1,10)" +
				" As etl_date , t.ent_stats As ent_stats ,j.jcsjfx_mc As ent_type " +
				" From etl_table_count t left join gz_dm_jcdm_fx j on  t.ent_type=j.jcsjfx_dm" +
				" Where to_char(t.etl_date,'yyyy-mm-dd')=(Select to_char(Max(a.etl_date),'yyyy-mm-dd')" +
				" From etl_table_count a ) And j.jc_dm_id='15062702' And t.ent_stats " +
				" is not null and t.ent_type is not null  group by t.ent_stats, to_char(t.etl_date,'yyyy-mm-dd')" +
				" ,j.xssx,j.jcsjfx_mc Order By j.xssx  ";
		stmt.addSqlStmt(sql);
//		stmt.addSqlStmt("select sum(t.i_count) As i_count ,sum(t.c_count)" +
//				" As c_count,max(t.etl_date) As etl_date,t.ent_stats As" +
//				" ent_stats ,t.ent_type As ent_type from etl_table_count t " +
//				" where t.ent_stats is not null and t.ent_type is not null " +
//				" group by t.ent_stats,t.ent_type,to_char(t.etl_date,'yyyy-mm-dd')" +
//				" having to_char(t.etl_date,'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd')");
		return stmt;
	}
	
	   public SqlStatement getValue(TxnContext request, DataBus inputData) throws TxnException{
		   
		   SqlStatement stmt = new SqlStatement();	   
		   String jcsjfx_dm = request.getString("select-key:ent_type");
		   String sql = "select jcsjfx_mc from gz_dm_jcdm_fx  where jcsjfx_dm = '"+jcsjfx_dm+"' and jc_dm_id='15062702' ";
		   stmt.addSqlStmt(sql);
		   return stmt;	   
	   }
	   public SqlStatement queryCountByMc(TxnContext request, DataBus inputData) throws TxnException{
		   
		   SqlStatement stmt = new SqlStatement();	   
		   String sys_name = request.getString("select-key:sys_name");
		   String sql = " select table_name_cn,table_name,ent_type,ent_stats," +
		   		" c_count,i_count,to_char(etl_date,'yyyy-mm-dd hh24:mi:ss')" +
		   		" as etl_date ,to_char(last_etl_date,'yyyy-mm-dd hh24:mi:ss') " +
		   		" as last_etl_date,sys_order,table_order from etl_table_count t where t.sys_name = '"+sys_name+"' " +
		   		" and to_char(t.etl_date,'yyyy-mm-dd')=(Select to_char(Max(a.etl_date),'yyyy-mm-dd') From etl_table_count a ) order by table_order ";
		   stmt.setCountStmt("select count(*) from etl_table_count t where " +
		   		"t.sys_name = '"+sys_name+"' and to_char(t.etl_date,'yyyy-mm-dd')=(Select to_char(Max(a.etl_date),'yyyy-mm-dd') From etl_table_count a )");
		   stmt.addSqlStmt(sql);
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
	public SqlStatement loadEtlTableCountList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from etl_table_count" );
		stmt.setCountStmt( "select count(*) from etl_table_count" );
		return stmt;
	}
	 */
	
}

