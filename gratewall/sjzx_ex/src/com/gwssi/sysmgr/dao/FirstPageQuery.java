package com.gwssi.sysmgr.dao;


import java.text.DateFormat;
import java.util.Date;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[first_page_query]�Ĵ�����
 * @author Administrator
 *
 */
public class FirstPageQuery extends BaseTable
{
	public FirstPageQuery()
	{
		
	}
	
	/**
	 * ע���û��Զ����SQL���
	 */
	protected void register( )
	{
		registerSQLFunction( "queryXtjgYh", DaoFunction.SQL_ROWSET, "��ѯ�û���¼����" );
		registerSQLFunction( "queryCountByYh", DaoFunction.SQL_ROWSET, "��ѯ�û���¼����" );	
		registerSQLFunction( "queryDetailYhdlcs", DaoFunction.SQL_ROWSET, "�û���¼��Ϣ" );		
		registerSQLFunction( "queryXtjgFj", DaoFunction.SQL_ROWSET, "��ѯ�־�" );
		registerSQLFunction( "queryCountByFj", DaoFunction.SQL_ROWSET, "��ѯ�־ֵ�¼����" );
		registerSQLFunction( "queryXtjgKs", DaoFunction.SQL_ROWSET, "��ѯ����" );	
		registerSQLFunction( "queryCountByKs", DaoFunction.SQL_ROWSET, "��ѯ���ҵ�¼����" );	
	}
		
	/**
	 * ��ѯ�û���¼����
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryCountByFj( TxnContext request, DataBus inputData )
	{
		DataBus db = request.getRecord("record");
		String date_start = db.getValue("date_start");
		String date_end = db.getValue("date_end");
		String sjjgid = db.getValue("sjjgid");
				
		SqlStatement stmt = new SqlStatement( );
		String sql = "Select Count(*) as count From (Select Query_Date, Orgid, Username From First_Page_Query t Group By  Query_Date,  Orgid, Username Having Query_Date >= '"+date_start+"' And Query_Date <= '"+date_end+"'";
		if(sjjgid!=null&&!sjjgid.equals("")){
			sql+=" and Orgid in ( select jgid_pk from xt_zzjg_jg where sjjgid_fk='"+sjjgid+"')";
		}
		sql+=" )";
		stmt.addSqlStmt( sql );		
		return stmt;
	}		
	/**
	 * ��ѯ����
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryXtjgFj( TxnContext request, DataBus inputData )
	{
		DataBus db = request.getRecord("select-key");
		String sjjgid = db.getValue("sjjgid");
		String date_start = db.getValue("query_date_start");
		String date_end = db.getValue("query_date_end");
        String queryDate = this.getQueryDate(date_start, date_end);
		SqlStatement stmt = new SqlStatement( );
		String sql = " select '"+ queryDate +"' as query_date ,jgid_pk as sjjgid,jgmc sjjgname from xt_zzjg_jg t where t.sjjgid_fk is null and t.sjjgname is null ";

		if(sjjgid!=null&&!sjjgid.equals("")){
			sql+= " and t.jgid_pk='"+sjjgid+"'";
		}else{
			String orgcode= db.getValue("orgcode");
			if(orgcode.length()>=8){
				if(!(orgcode.substring(0,8)).equals("00100100")){
				    sql+= " and ((t.jgid_pk = ( select sjjgid_fk from xt_zzjg_jg where jgid_pk='"+orgcode+"'))  or (( select sjjgid_fk from xt_zzjg_jg where jgid_pk='"+orgcode+"') is null))";
				}
			}	
		}
		stmt.setCountStmt( "select count(*) from (" +sql+ ")" );
		sql += " order by jgid_pk desc";
		stmt.addSqlStmt( sql );		
		return stmt;
	}	
	
	/**
	 * ��ѯ����
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryXtjgKs( TxnContext request, DataBus inputData )
	{
		DataBus db = request.getRecord("select-key");
		String date_start = db.getValue("query_date_start");
		String date_end = db.getValue("query_date_end");
        String queryDate = this.getQueryDate(date_start, date_end);
		String sjjgid = db.getValue("sjjgid");
		String orgid = db.getValue("orgid");
		SqlStatement stmt = new SqlStatement( );
		String sql = " select '"+ queryDate +"' as query_date ,sjjgid_fk as sjjgid,sjjgname,jgid_pk as orgid,jgmc orgname from xt_zzjg_jg t where t.sjjgid_fk is not null ";
		if(orgid!=null&&!orgid.equals("")){
			sql+= " and t.jgid_pk='"  + orgid + "'";
		}else{
			if(sjjgid!=null&&!sjjgid.equals("")){
				sql+= " and t.jgid_pk in ( select jgid_pk from xt_zzjg_jg where sjjgid_fk='"+sjjgid+"')";
			}else{
				String orgcode= db.getValue("orgcode");
				if(orgcode!=null&&!orgcode.equals("")){
					if(orgcode.length()>=8){
						if(!(orgcode.substring(0,8)).equals("00100100")){
							  sql+= " and ((t.jgid_pk = ( select sjjgid_fk from xt_zzjg_jg where jgid_pk='"+orgcode+"'))  or (( select sjjgid_fk from xt_zzjg_jg where jgid_pk='"+orgcode+"') is null))";  
						}
					}					
				}				
			}
		}		
		stmt.setCountStmt( "select count(*) from (" +sql+ ")" );
		sql += " order by sjjgid_fk,jgid_pk desc";
		System.out.println("�������ң�"+sql);
		stmt.addSqlStmt( sql );		
		return stmt;
	}
	/**
	 * ��ѯ���ҵ�¼����
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryCountByKs( TxnContext request, DataBus inputData )
	{
		DataBus db = request.getRecord("record");
		String date_start = db.getValue("date_start");
		String date_end = db.getValue("date_end");
		String orgid = db.getValue("orgid");
				
		SqlStatement stmt = new SqlStatement( );
		String sql = "Select Count(*) as count From (Select Query_Date, Orgid, Username From First_Page_Query t Group By  Query_Date,  Orgid, Username Having Query_Date >= '"+date_start+"' And Query_Date <= '"+date_end+"'";
		if(orgid!=null&&!orgid.equals("")){
			sql+=" and Orgid ='"+orgid+"'";
		}
		sql+=" )";
		System.out.println("��ѯ���ҵ�½������"+sql);
		stmt.addSqlStmt( sql );		
		return stmt;
	}	
	/**
	 * ��ѯ�û���¼����
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryXtjgYh( TxnContext request, DataBus inputData )
	{
		DataBus db = request.getRecord("select-key");
		String date_start = db.getValue("query_date_start");
		String date_end = db.getValue("query_date_end");
		String opername = db.getValue("opername");
		String sjjgid = db.getValue("sjjgid");
		String orgid = db.getValue("orgid");
		
		String queryDate = this.getQueryDate(date_start, date_end);
				
		SqlStatement stmt = new SqlStatement( );
		String sql = "select '"+ queryDate+"' as query_date, a.Sjjgid_Fk As Sjjgid, a.Sjjgname As Sjjgname, a.Jgid_Pk As Orgid, a.Jgmc Orgname, b.Yhzh As Username, b.Yhxm As Opername,b.sfyx From Xt_Zzjg_Jg a, xt_zzjg_yh_new b Where a.Jgid_Pk = b.Jgid_Fk";
		if(orgid!=null&&!orgid.equals("")){
			sql+= " and a.jgid_pk='"  + orgid + "'";
		}else{
			if(sjjgid!=null&&!sjjgid.equals("")){
				sql+= " and a.jgid_pk in ( select jgid_pk from xt_zzjg_jg where sjjgid_fk='"+sjjgid+"')";
			}else{
				String orgcode= db.getValue("orgcode");
				if(orgcode!=null&&!orgcode.equals("")){
					if(orgcode.length()>=8){
						if(!(orgcode.substring(0,8)).equals("00100100")){
							  sql+= " and ((a.jgid_pk = ( select sjjgid_fk from xt_zzjg_jg where jgid_pk='"+orgcode+"'))  or (( select sjjgid_fk from xt_zzjg_jg where jgid_pk='"+orgcode+"') is null))";  
						}
					}					
				}				
			}
		}
		if(opername!=null&&!opername.equals("")){
			sql+= " and b.yhxm like '%" + opername + "%'";
		}		
		stmt.setCountStmt( "select count(*) from (" +sql+ ")" );
		sql += " order by a.sjjgid_fk,a.jgid_pk,b.yhxm desc";
		stmt.addSqlStmt( sql );		
		return stmt;
	}
	/**
	 * ��ѯ���ҵ�¼����
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryCountByYh( TxnContext request, DataBus inputData )
	{
		DataBus db = request.getRecord("record");
		String date_start = db.getValue("date_start");
		String date_end = db.getValue("date_end");
		String username = db.getValue("username");
				
		SqlStatement stmt = new SqlStatement( );
		String sql = "Select Count(*) as count From (Select Query_Date, Orgid, Username From First_Page_Query t Group By  Query_Date,  Orgid, Username Having Query_Date >= '"+date_start+"' And Query_Date <= '"+date_end+"'";
		if(username!=null&&!username.equals("")){
			sql+=" and upper(Username) ='"+username.toUpperCase()+"'";
		}
		sql+=" )";
		stmt.addSqlStmt( sql );		
		return stmt;
	}	
	/**
	 * �û���¼��Ϣ
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryDetailYhdlcs( TxnContext request, DataBus inputData )
	{
		DataBus db = request.getRecord("select-key");
		String query_date_start = db.getValue("query_date_start");
		String query_date_end = db.getValue("query_date_end");
		String orgid = db.getValue("orgid");
		String opername = db.getValue("opername");
		String username = db.getValue("username");
		
		SqlStatement stmt = new SqlStatement( );
		String sql = "select a.query_date as query_date,a.opername,a.username,a.orgid,count(*) as count" +
				" from (select t.opername as opername,t.query_date as query_date," +
				" t.username as username,t.orgid as orgid from" +
				" first_page_query t group by opername ,query_date," +
				" orgid,username having query_date>='"+query_date_start+"' and" +
				" query_date<='"+query_date_end+"') a group by a.opername," +
				" a.orgid,a.username,a.orgid,a.query_date having a.orgid='" + orgid + "'" +
				" and a.opername='" + opername + "' and a.username='" + username + "'";
		stmt.setCountStmt( "select count(*) from (" +sql+ ")" );
		sql += " order by query_date desc";
		stmt.addSqlStmt( sql );		
		return stmt;
	}
    private String getQueryDate(String date_start,String date_end){
    	
    	String reDate = "";
		String query_date_start = date_start.replaceAll("-0", "-").replaceFirst("-", "��").replaceFirst("-", "��") + "��";
		String query_date_end = date_end.replaceAll("-0", "-").replaceFirst("-", "��").replaceFirst("-", "��") + "��";		    	
		reDate = query_date_start + "-" + query_date_end;
        return reDate;
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
	public SqlStatement loadFirstPageQueryList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		stmt.addSqlStmt( "select * from first_page_query" );
		stmt.setCountStmt( "select count(*) from first_page_query" );
		return stmt;
	}
	 */
	
}

