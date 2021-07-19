package com.gwssi.dw.runmgr.etl.txn;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.util.CalendarUtil;

public class TxnFirstPageTotal extends TxnService
{

	// 数据表名称
	private static final String	TABLE_NAME	= "first_page_total";

	/**
	 * 构造函数
	 */
	public TxnFirstPageTotal()
	{

	}

	/**
	 * 初始化函数
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{

	}

	/**
	 * 查询首页总数
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn81000001(TxnContext context) throws TxnException
	{
		String startTime = CalendarUtil
				.getCalendarByFormat(CalendarUtil.FORMAT11)
				+ " 00:00:00";
		String endTime = CalendarUtil
				.getCalendarByFormat(CalendarUtil.FORMAT11)
				+ " 23:59:59";
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		StringBuffer sql = new StringBuffer(
				"select first_page_total_id,first_page_total_cls,first_page_total_num from first_page_total_new t union all select '' as first_page_total_id,'login_cur_num' as first_page_total_cls, online_num as first_page_total_num from (select * from tj_online order by scsj desc) where rownum=1");
		// sql.append(" union all select '' as
		// first_page_total_id,'login_cur_num' as
		// first_page_total_cls,count(t.username) as first_page_total_num from
		// login_session t where t.login_date = to_char(sysdate,'yyyy-mm-dd')")
		// .append(" union all select '' as
		// first_page_total_id,'login_total_num' as
		// first_page_total_cls,count(username) as first_page_total_num from
		// login_session_hs ")
		// .append(" union all select '' as first_page_total_id,'query_cur_num'
		// as first_page_total_cls,count(t.username) as first_page_total_num
		// from first_page_query t where t.query_date =
		// to_char(sysdate,'yyyy-mm-dd')")
		// .append(" union all select '' as
		// first_page_total_id,'query_total_num' as
		// first_page_total_cls,count(username) as first_page_query from
		// first_page_query")
		// .append(" union all select '' as
		// first_page_total_id,'download_cur_num' as
		// first_page_total_cls,sum(t.DOWNLOAD_COUNT) as first_page_total_num
		// from DOWNLOAD_LOG t where t.OPERNAME='数据下载' and t.operdate =
		// to_char(sysdate,'yyyy-mm-dd') and t.DOWNLOAD_COUNT!='--' and
		// t.operdept!='a0426c26efe541f89a3278349951f0c9'")
		// .append(" union all select '' as
		// first_page_total_id,'download_total_num' as
		// first_page_total_cls,sum(DOWNLOAD_COUNT) as first_page_query from
		// DOWNLOAD_LOG where OPERNAME='数据下载' and DOWNLOAD_COUNT!='--' and
		// operdept!='a0426c26efe541f89a3278349951f0c9'")
		// .append(" union all select '' as first_page_total_id,'share_cur_num'
		// as first_page_total_cls,sum(t.records_mount) as first_page_total_num
		// from sys_svr_log t where t.state='成功' and t.execute_start_time >
		// '").append(startTime).append("' and
		// t.execute_start_time<'").append(endTime).append("'")
		// .append(" union all select '' as
		// first_page_total_id,'share_total_num' as
		// first_page_total_cls,sum(records_mount) as first_page_query from
		// sys_svr_log where state='成功'");

		try {
			table.executeRowset(sql.toString(), context, "record");
		} catch (TxnException e) {
			log.debug("没有数据!");
		}

		/*
		 * Attribute.setPageRow( context, outputNode, -1 ); int
		 * count=table.executeFunction( "queryFirstPageTotal", context,
		 * inputNode, outputNode ); String sql = "select 'login_cur_num' as
		 * first_page_total_cls,count(t.username) as first_page_total_num from
		 * login_session t where t.login_date = to_char(sysdate,'yyyy-mm-dd') " +
		 * "union all select 'login_total_num' as
		 * first_page_total_cls,count(username) as first_page_total_num from
		 * login_session_hs ";
		 * 
		 * int login_num = 0; try{ login_num=table.executeRowset(sql, context,
		 * "loginRecord"); }catch(TxnException e){ log.debug("没有数据!"); }
		 * 
		 * sql = "select 'query_cur_num' as
		 * first_page_total_cls,count(t.username) as first_page_total_num from
		 * first_page_query t where t.query_date = to_char(sysdate,'yyyy-mm-dd') " +
		 * "union all select 'query_total_num' as
		 * first_page_total_cls,count(username) as first_page_query from
		 * first_page_query ";
		 * 
		 * int query_num = 0; try{ query_num=table.executeRowset(sql, context,
		 * "queryRecord"); }catch(TxnException e){ log.debug("没有数据!"); }
		 * 
		 * sql = "select 'download_cur_num' as
		 * first_page_total_cls,sum(t.DOWNLOAD_COUNT) as first_page_total_num
		 * from DOWNLOAD_LOG t where t.OPERNAME='数据下载' and t.operdate =
		 * to_char(sysdate,'yyyy-mm-dd') and t.DOWNLOAD_COUNT!='--' and
		 * t.operdept!='a0426c26efe541f89a3278349951f0c9' " + "union all select
		 * 'download_total_num' as first_page_total_cls,sum(DOWNLOAD_COUNT) as
		 * first_page_query from DOWNLOAD_LOG where OPERNAME='数据下载' and
		 * DOWNLOAD_COUNT!='--' and
		 * operdept!='a0426c26efe541f89a3278349951f0c9'";
		 * 
		 * int download_num = 0; try{ download_num=table.executeRowset(sql,
		 * context, "downloadRecord"); }catch(TxnException e){
		 * log.debug("没有数据!"); }
		 * 
		 * String startTime =
		 * CalendarUtil.getCalendarByFormat(CalendarUtil.FORMAT11)+" 00:00:00";
		 * String endTime =
		 * CalendarUtil.getCalendarByFormat(CalendarUtil.FORMAT11)+" 23:59:59";
		 * int svr_num = 0; sql = "select 'share_cur_num' as
		 * first_page_total_cls,sum(t.records_mount) as first_page_total_num
		 * from sys_svr_log t where t.state='成功' and t.execute_start_time >
		 * '"+startTime+"' and t.execute_start_time<'"+endTime + "' union all
		 * select 'share_total_num' as first_page_total_cls,sum(records_mount)
		 * as first_page_query from sys_svr_log where state='成功'";
		 * 
		 * try{ svr_num=table.executeRowset(sql, context, "svrRecord");
		 * }catch(TxnException e){ log.debug("没有数据!"); }
		 * 
		 * Recordset rs = null; if(count>0){ rs =
		 * context.getRecordset(outputNode);
		 * 
		 * }else{ rs = new Recordset(); } if (login_num > 0) {
		 * rs.add(context.getRecordset("loginRecord").toArray()); }
		 * 
		 * if (query_num > 0) {
		 * rs.add(context.getRecordset("queryRecord").toArray()); }
		 * 
		 * if (download_num > 0) {
		 * rs.add(context.getRecordset("downloadRecord").toArray()); }
		 * 
		 * if (svr_num > 0) {
		 * rs.add(context.getRecordset("svrRecord").toArray()); }
		 * context.setValue(outputNode, rs);
		 */
	}

	public void txn81000002(TxnContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction("queryEntStatsDetail", context, inputNode,
				outputNode);
	}

}
