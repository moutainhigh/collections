package com.gwssi.dw.runmgr.etl.dao;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

import com.gwssi.dw.runmgr.etl.txn.ETLConstants;

public class LoginSessionHs extends BaseTable
{
	public LoginSessionHs()
	{

	}

	/**
	 * 注册SQL语句
	 */
	protected void register()
	{
		registerSQLFunction("queryLoginSessionHsList", DaoFunction.SQL_ROWSET, "系统在线用户");
		registerSQLFunction("statLoginSessionHsList", DaoFunction.SQL_ROWSET, "用户访问统计");
		registerSQLFunction("statUseList", DaoFunction.SQL_ROWSET, "使用情况统计");
		registerSQLFunction( "updateLoginSession", DaoFunction.SQL_UPDATE, "更新用户登录时间" );
	}

	/**
	 * 执行SQL语句前的处理
	 */
	public void prepareExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{

	}

	/**
	 * 执行完SQL语句后的处理
	 */
	public void afterExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{

	}
	public SqlStatement statLoginSessionHsList(TxnContext request,DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String jgid_fk = request.getRecord("select-key").getValue("jgid_fk");
		String login_date_from = request.getRecord("select-key").getValue("login_date_from");
		String login_date_to = request.getRecord("select-key").getValue("login_date_to");
        StringBuffer login_session_table = new StringBuffer("select yhid_pk, count(1) as login_times from login_session_hs where 1=1");
        if(login_date_from!=null&&!"".equals(login_date_from)){
            login_session_table.append(" and login_date>='").append(login_date_from).append("'");
        }       
        if(login_date_to!=null&&!"".equals(login_date_to)){
            login_session_table.append(" and login_date<='").append(login_date_to).append("'");
        }
        login_session_table.append(" group by yhid_pk");
        StringBuffer sql = new StringBuffer("select t.yhid_pk,t.jgmc_all,t.yhxm,decode(t1.login_times,'',0,t1.login_times) as login_times");
        StringBuffer countSql = new StringBuffer("select count(t.yhid_pk)");
        StringBuffer others = new StringBuffer();
        others.append(" from (select a.yhid_pk,a.yhxm,a.jgid_fk,b.sjjgid_fk,")
           .append(" decode(b.sjjgid_fk,'ee1c6e35f0ad4b43a7f66af451ed45f9','市局',b.sjjgname) || b.jgmc as jgmc_all")
           .append(" from xt_zzjg_yh_new a, xt_zzjg_jg b")
           .append(" where a.jgid_fk = b.jgid_pk and a.jgid_fk<>'a0426c26efe541f89a3278349951f0c9') t ");
        others.append(" left join (").append(login_session_table).append(") t1 on t.yhid_pk = t1.yhid_pk");
        if(jgid_fk!=null&&!"".equals(jgid_fk)){
        	others.append(" where t.jgid_fk='").append(jgid_fk).append("'").append(" or t.sjjgid_fk='").append(jgid_fk).append("'");
        }	
        others.append(" order by t.jgmc_all,login_times desc");
		stmt.addSqlStmt(sql.append(others).toString());
		stmt.setCountStmt(countSql.append(others).toString());
		return stmt;
	}

	public SqlStatement queryLoginSessionHsList(TxnContext request,DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String operName = request.getRecord("select-key").getValue("opername");
		String userName = request.getRecord("select-key").getValue("username");
		String rolenames = request.getRecord("select-key").getValue("rolenames");
		String loginDateFrom = request.getRecord("select-key").getValue("login_date_from");
		String loginDateTo = request.getRecord("select-key").getValue("login_date_to");
		String jgid = request.getRecord("select-key").getValue("jgid_fk");
		StringBuffer sql = new StringBuffer("SELECT s.session_id,s.ipaddress,s.login_date,s.login_time,s.jgid_pk,u.yhzh,u.yhxm,u.rolenames from login_session s, xt_zzjg_yh_new u,xt_zzjg_jg v where s.yhid_pk=u.yhid_pk and u.jgid_fk=v.jgid_pk ");
		if(operName != null && !operName.trim().equals("")){
			sql.append(" AND u.yhxm like '%").append(operName).append("%' ");
		}
		if(userName != null && !userName.trim().equals("")){
			sql.append(" AND s.username like '%").append(userName).append("%' ");
		}
		if(rolenames != null && !rolenames.trim().equals("")){
			sql.append(" AND u.rolenames like '%").append(rolenames).append("%' ");
		}
		if(loginDateFrom != null && !loginDateFrom.trim().equals("")){
			sql.append(" AND s.login_date >= '").append(loginDateFrom).append("' ");
		}
		if(loginDateTo != null && !loginDateTo.trim().equals("")){
			sql.append(" AND s.login_date <= '").append(loginDateTo).append("' ");
		}
		if(jgid != null && !jgid.trim().equals("")){
			sql.append(" AND (s.jgid_pk = '").append(jgid).append("' or v.sjjgid_fk='").append(jgid).append("') ");
		}
		sql.append(" order by login_time desc");
//		System.out.println("sql:  "+sql);
		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt("select count(1) from (" + sql + ")");
		return stmt;
	}

	public SqlStatement statUseList(TxnContext request,DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String startDate = request.getRecord("select-key").getValue("startDate");
		String endDate = request.getRecord("select-key").getValue("endDate");
		
		StringBuffer table1 = new StringBuffer("select count(1) login_times,B.SJJGID_FK from login_session_hs a,xt_zzjg_jg b where a.jgid_pk= b.jgid_pk and a.jgid_pk!='a0426c26efe541f89a3278349951f0c9' and b.sjjgid_fk is not null");
		if(startDate!=null &&!"".equals(startDate)){
			table1.append(" and a.login_date>='").append(startDate).append("'");
		}
		if(endDate!=null &&!"".equals(endDate)){
			table1.append(" and a.login_date<='").append(endDate).append("'");
		}
		table1.append(" group by B.SJJGID_FK");
      
		StringBuffer table2 = new StringBuffer("select sum(COUNT) query_times,B.SJJGID_FK from first_page_query a,xt_zzjg_jg b where a.orgid= b.jgid_pk and a.orgid!='a0426c26efe541f89a3278349951f0c9' and b.sjjgid_fk is not null");
		if(startDate!=null &&!"".equals(startDate)){
			table2.append(" and a.QUERY_DATE>='").append(startDate).append("'");
		}
		if(endDate!=null &&!"".equals(endDate)){
			table2.append(" and a.QUERY_DATE<='").append(endDate).append("'");
		}
		table2.append(" group by b.SJJGID_FK");      
      
		StringBuffer table3 = new StringBuffer("select count(1) download_times,sum(to_number(DOWNLOAD_COUNT)) download_total,B.SJJGID_FK from DOWNLOAD_LOG a,xt_zzjg_jg b where a.OPERDEPT= b.jgid_pk and a.DOWNLOAD_COUNT!='--' and a.OPERDEPT!='a0426c26efe541f89a3278349951f0c9' and a.opername='数据下载' and b.sjjgid_fk is not null");
		if(startDate!=null &&!"".equals(startDate)){
			table3.append(" and a.OPERDATE>='").append(startDate).append("'");
		}
		if(endDate!=null &&!"".equals(endDate)){
			table3.append(" and a.OPERDATE<='").append(endDate).append("'");
		}
		table3.append(" group by b.SJJGID_FK");  
		
		StringBuffer sql = new StringBuffer("select jg.jgmc,jg.jgid_pk,decode(t1.login_times,null,0,t1.login_times) login_times,decode(t2.query_times,null,0,t2.query_times) query_times,decode(t3.download_times,null,0,t3.download_times) download_times,decode(t3.download_total,null,0,t3.download_total) download_total from xt_zzjg_jg jg");
		sql.append(",(").append(table1).append(") t1");
		sql.append(",(").append(table2).append(") t2");
		sql.append(",(").append(table3).append(") t3");
		sql.append(" where JG.JGID_PK = t1.SJJGID_FK(+) and JG.JGID_PK = t2.SJJGID_FK(+) and JG.JGID_PK = t3.SJJGID_FK(+)  and  jg.sjjgid_fk is null and jg.jgid_pk!='a0426c26efe541f89a3278349951f0c9' order by plxh,jgid_pk");
		System.out.println(sql);
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}
	
	public SqlStatement updateLoginSession(TxnContext request, DataBus inputData){
		SqlStatement stmt = new SqlStatement();
		String sessionId = request.getRecord("select-key").getValue("session_id");
		stmt.addSqlStmt( "update login_session set login_time=to_char(sysdate,'yyyy-MM-dd hh24:mi:ss') WHERE session_id='"+ sessionId + "'" );
//		stmt.setCountStmt( "select count(sys_svr_user_id) from SYS_SVR_USER");
		return stmt;
	}
}
