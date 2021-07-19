package com.gwssi.dw.aic.download.dao;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

/**
 * ���ݱ�[download_log]�Ĵ�����
 * @author Administrator
 *
 */
public class DownloadLog extends BaseTable
{
	public DownloadLog()
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
		// table.executeFunction( "loadDownloadLogList", context, inputNode, outputNode );
		registerSQLFunction( "loadDownloadLogList", DaoFunction.SQL_ROWSET, "��ȡ������־�б�" );
		registerSQLFunction( "queryAdQueryLog", DaoFunction.SQL_ROWSET, "��ȡ��ѯ����־��Ϣ" );
		
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
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement loadDownloadLogList( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String operdept=request.getRecord("select-key").getValue("operdept");
		String opertype=request.getRecord("select-key").getValue("opertype");
		String oper_date_begin=request.getRecord("select-key").getValue("oper_date_begin");
		String oper_date_end=request.getRecord("select-key").getValue("oper_date_end");
		
		 StringBuffer sb=new StringBuffer("select dl.download_log_id,ds.download_status_id,dl.operdate,dl.opertor,")
		 .append("dl.download_count,dl.operdept,ds.apply_name,ds.status,dl.operdate1 from(")
		 .append("select * from (select d.download_log_id,d.opername, d.download_status_id,")
		 .append("d.operdate || ' ' || opertime operdate,d.operdate operdate1,d.opertor,d.download_count,d.operdept,")
		 .append("row_number() over(partition by d.download_status_id order by d.operdate || ' ' || opertime desc) rn")
		 .append(" from download_log d) where rn = 1)dl,download_status ds")
		 .append(" where dl.download_status_id=ds.download_status_id(+)  " );
		 
		 if (StringUtils.isNotBlank(operdept)) {
			 sb.append(" and dl.operdept='").append(operdept).append("'");
		 }
		 if (StringUtils.isNotBlank(opertype)) {
			 if (opertype.equals("0")) {
				 sb.append(" and status is not null");
			}else{
				sb.append(" and status is null");
			}
		 }
		 if (StringUtils.isNotBlank(oper_date_begin)) {
			 sb.append(" and dl.operdate1>='").append(oper_date_begin).append("'");
		 }
		 if (StringUtils.isNotBlank(oper_date_end)) {
			 sb.append(" and dl.operdate1<='").append(oper_date_end).append("'");
		 }
		 sb.append(" order by operdate desc");
   		stmt.addSqlStmt(sb.toString());
		stmt.setCountStmt( "select count(*) from ("+sb.toString()+ ")");
		return stmt;
	}
	
	/**
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement queryAdQueryLog( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		 String download_log_id=request.getRecord("primary-key").getValue("download_log_id");
		 System.out.println("download_log_id:"+download_log_id);
		 StringBuffer sb=new StringBuffer("select d.download_log_id, d.operdate, d.opertime, d.opertor,")
		 .append("d.operdept,d.download_count,d.download_cond from download_log d ")
		 .append("where d.download_log_id='").append(download_log_id).append("'" );
		 System.out.println("download-sql:"+sb.toString());
   		stmt.addSqlStmt(sb.toString());
		return stmt;
	}
	
}

