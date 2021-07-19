package com.gwssi.dw.runmgr.etl.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;


import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.database.DBPoolConnection;
import com.gwssi.common.util.DateUtil;

/**
 * ���ݱ�[sys_notice_info]�Ĵ�����
 * 
 * @author Administrator
 * 
 */
public class SysNoticeInfo extends BaseTable
{
	public SysNoticeInfo()
	{

	}

	/**
	 * ע���û��Զ����SQL���
	 */
	protected void register()
	{

		registerSQLFunction("FCServiceXMLQuery", DaoFunction.SQL_ROWSET,
				"��ҳ����ͳ����Ϣ��ѯ");
		registerSQLFunction("FCCollectXMLQuery", DaoFunction.SQL_ROWSET,
				"��ҳ�ɼ�����ͳ����Ϣ��ѯ");
		registerSQLFunction("indexMonCollect", DaoFunction.SQL_ROWSET, "��ҳ�������");
		registerSQLFunction("indexAllTargets", DaoFunction.SQL_ROWSET, "��ҳ�������");
		registerSQLFunction("previewObjInfo", DaoFunction.SQL_ROWSET,
				"������ȡ�������");
		registerSQLFunction("getShareServiceByObj", DaoFunction.SQL_ROWSET,
				"��ȡĳ������������");
		registerSQLFunction("getCollectTaskByObj", DaoFunction.SQL_ROWSET,
				"��ȡĳ�������ɼ�����");
		registerSQLFunction("FCsharedetailXMLQuery", DaoFunction.SQL_ROWSET,
				"����ֲ������ϸ��Ϣͳ��");
		registerSQLFunction("FCshareServiceXMLQuery", DaoFunction.SQL_ROWSET,
				"����ֲ����ͳ��");
		registerSQLFunction("FCColdetailXMLQuery", DaoFunction.SQL_ROWSET,
				"�ɼ����������ϸ��Ϣͳ��");
		registerSQLFunction("FCColTaskXMLQuery", DaoFunction.SQL_ROWSET,
				"�ɼ��������ͳ��");
		registerSQLFunction("shareLogPriview", DaoFunction.SQL_ROWSET,
				"�����������ͳ��");
		registerSQLFunction("queryStatisticsShareLog", DaoFunction.SQL_ROWSET,
				"ͳ�ƹ�����־��Ϣ");
		registerSQLFunction("statisticsShareService", DaoFunction.SQL_ROWSET,
				"ͳ�ƹ���������");
		registerSQLFunction("statisticsCollectService", DaoFunction.SQL_ROWSET,
				"ͳ�ƹ���������");
		registerSQLFunction("statisticsShareServiceType",
				DaoFunction.SQL_ROWSET, "ͳ�ƹ������ʽ");
		registerSQLFunction("statisticsCollectServiceType",
				DaoFunction.SQL_ROWSET, "ͳ�Ʋɼ�����ʽ");
		registerSQLFunction("indexServiceTargetsInfo", DaoFunction.SQL_ROWSET,
				"�����������ֲ����");
		registerSQLFunction("getShareServiceByType", DaoFunction.SQL_ROWSET,
				"�������ͻ�ȡ������������Ϣ");
		registerSQLFunction("getShareStatById", DaoFunction.SQL_ROWSET,
				"����ID��ȡ����������ͳ����Ϣ");
		registerSQLFunction("getCollectStatById", DaoFunction.SQL_ROWSET,
				"����ID��ȡ�ɼ��������ͳ����Ϣ");
		// getShareInterfaceStatById
		registerSQLFunction("getShareInterfaceStatById",
				DaoFunction.SQL_ROWSET, "����ID��ȡ�������ӿڶ���ͳ����Ϣ");
		registerSQLFunction("getServiceTargetsInfoRoot",
				DaoFunction.SQL_ROWSET, "��ȡ�������ͳ����Ϣ ���ڵ�");
		registerSQLFunction("getServiceTargetsInfoLeaf",
				DaoFunction.SQL_ROWSET, "��ȡ�������ͳ����Ϣ �ӽڵ�");
		registerSQLFunction("getShareErr",
				DaoFunction.SQL_ROWSET, "��ȡ�������ͳ�� ������������Ϣ");
		registerSQLFunction("getCollectErr",
				DaoFunction.SQL_ROWSET, "��ȡ�������ͳ�� �ɼ����������Ϣ");
		registerSQLFunction("getTargetInfo", DaoFunction.SQL_ROWSET, "��ȡ��������б�����ѯ����");
		
	}

	/**
	 * ִ��SQL���ǰ�Ĵ���
	 */
	public void prepareExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{

	}

	/**
	 * �������ͻ�ȡ������������Ϣ
	 */
	public SqlStatement getShareServiceByType(TxnContext request,
			DataBus inputData)
	{

		SqlStatement stmt = new SqlStatement();
		DataBus db = request.getRecord("select-key");
		String type = db.getValue("type");
		System.out.println("type=" + type);
		String sql = " with a as (select t.service_targets_id,count(*) as total from share_service t group by t.service_targets_id) "
				+ " select t.service_targets_id,t.service_targets_type,t.service_targets_name as name,nvl(a.total,'0') as total from res_service_targets t,a "
				+ " where a.service_targets_id(+) = t.service_targets_id and t.service_targets_type='"
				+ type + "' order by t.service_targets_type,t.show_order";
		stmt.addSqlStmt(sql);
		return stmt;
	}

	/**
	 * ���¼��ѯ
	 */
	public SqlStatement queryList(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		DataBus db = request.getRecord("select-key");
		String sys_notice_matter = db.getValue("sys_notice_matter");
		String sys_notice_org = db.getValue("sys_notice_org");
		String sys_notice_promulgator = db.getValue("sys_notice_promulgator");
		String sys_notice_state = db.getValue("sys_notice_state");
		String sys_notice_date_s = db.getValue("sys_notice_date_s");
		String sys_notice_date_e = db.getValue("sys_notice_date_e");

		StringBuffer stringBfSql = new StringBuffer(
				"select sys_notice_id,sys_notice_title,"
						+ " sys_notice_matter,sys_notice_promulgator,"
						+ " sys_notice_org,substr(sys_notice_date,1,10) as sys_notice_date ,sys_notice_state,"
						+ " sys_notice_filepath ");
		StringBuffer stringBfCount = new StringBuffer("select count(*) ");
		StringBuffer buffSql = new StringBuffer(
				" from sys_notice_info where 1=1 ");
		if (sys_notice_matter != null && !sys_notice_matter.equals("")) {
			buffSql.append(" and sys_notice_matter like '%" + sys_notice_matter
					+ "%'");
		}
		if (sys_notice_org != null && !sys_notice_org.equals("")) {
			buffSql.append(" and sys_notice_org ='" + sys_notice_org + "'");
		}
		if (sys_notice_promulgator != null
				&& !sys_notice_promulgator.equals("")) {
			buffSql.append(" and sys_notice_promulgator ='"
					+ sys_notice_promulgator + "'");
		}
		if (sys_notice_state != null && !sys_notice_state.equals("")) {
			buffSql.append(" and sys_notice_state ='" + sys_notice_state + "'");
		}
		if ((sys_notice_date_s != null && !sys_notice_date_s.equals(""))
				|| (sys_notice_date_e != null && !sys_notice_date_e.equals(""))) {
			buffSql.append(" and sys_notice_date is not null ");
			if (sys_notice_date_s != null && !sys_notice_date_s.equals("")) {
				buffSql.append(" and sys_notice_date >='" + sys_notice_date_s
						+ " 00:00:00' ");
			}
			if (sys_notice_date_e != null && !sys_notice_date_e.equals("")) {
				buffSql.append(" and sys_notice_date <='" + sys_notice_date_e
						+ " 23:59:59' ");
			}
		}
		stringBfSql.append(buffSql);
		stringBfSql.append(" order by sys_notice_date desc ");
		stringBfCount.append(buffSql);
		// System.out.println("sql===== " + stringBfSql.toString());
		stmt.addSqlStmt(stringBfSql.toString());
		stmt.setCountStmt(stringBfCount.toString());
		return stmt;
	}

	/**
	 * ��û�������
	 */
	public SqlStatement getOrgName(TxnContext request, DataBus inputData)
	{

		SqlStatement stmt = new SqlStatement();
		DataBus db = request.getRecord("select-key");
		String org_code = db.getValue("org_code");
		String sql = "select jgmc from xt_zzjg_jg where jgid_pk='" + org_code
				+ "'";
		stmt.addSqlStmt(sql);
		return stmt;
	}

	/**
	 * ��ҳ���ݲ�ѯ
	 */
	public SqlStatement mianQuery(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String sql = "Select * from (select * from sys_notice_info where sys_notice_state='1' order by sys_notice_date desc) Where Rownum<11";
		stmt.addSqlStmt(sql);
		return stmt;
	}

	/**
	 * ��ҳ�������ͳ����Ϣ��ѯ
	 */
	public SqlStatement FCServiceXMLQuery(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();

		// oracle��ͬ�汾�����ݿ⣬��ѯ����sql�����һ�£�Ҫ���ж�һЩsql�Ľ��
		DBPoolConnection conn = new DBPoolConnection(
				CollectConstants.DATASOURCE_DEFAULT);
		String count_sql = "select rownum from dual connect by rownum<=2 ";
		List list = conn.selectBySql(count_sql);
		String stn = list.size() == 2 ? "<=" : "<";

		String sql = "select d1.datey, nvl(record_amount, 0) record_amount, nvl(exec_count, 0) exec_count "
				+ "from (select t.year || '-' || LPAD(t.month, 2, '0') datex, "
				+ "sum(t.sum_record_amount) record_amount, "
				+ "sum(t.sum_consume_time) consume_time, "
				+ "sum(t.exec_count) exec_count "
				+ "from share_log_statistics t "
				+ "group by t.year, t.month) t, "
				+ "(select to_char(add_months(sysdate, -5 + rownum), 'yyyy-MM') datey "
				+ "from dual "
				+ "connect by rownum "
				+ stn
				+ " 5) d1 "
				+ "where t.datex(+) = d1.datey order by d1.datey asc";
		// System.out.println("-----FusionChartsXMLQuery1----"+sql);
		stmt.addSqlStmt(sql);
		return stmt;
	}

	/**
	 * ��ҳ�ɼ�����ͳ����Ϣ��ѯ
	 */
	public SqlStatement FCCollectXMLQuery(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();

		// oracle��ͬ�汾�����ݿ⣬��ѯ����sql�����һ�£�Ҫ���ж�һЩsql�Ľ��
		DBPoolConnection conn = new DBPoolConnection(
				CollectConstants.DATASOURCE_DEFAULT);
		String count_sql = "select rownum from dual connect by rownum<=2 ";
		List list = conn.selectBySql(count_sql);
		String stn = list.size() == 2 ? "<=" : "<";

		String sql = "select d1.datey, nvl(record_amount, 0) record_amount, nvl(total_times, 0) total_times "
				+ "from (select substr(cj.task_start_time, 0, 7) as datex, "
				+ "sum(cj.collect_data_amount) as record_amount, "
				+ "count(cj.service_targets_id) as total_times "
				+ "from collect_joumal cj "
				+ "where cj.service_targets_id is not null "
				+ "and cj.service_targets_name is not null "
				+ "group by substr(cj.task_start_time, 0, 7)) t, "
				+ "(select to_char(add_months(sysdate, -5 + rownum), 'yyyy-MM') datey "
				+ "from dual "
				+ "connect by rownum "
				+ stn
				+ " 5) d1 "
				+ "where t.datex(+) = d1.datey " + "order by d1.datey asc";
		stmt.addSqlStmt(sql);
		return stmt;
	}

	/**
	 * ��ҳ�ɼ���������Ϣ
	 */
	public SqlStatement indexMonCollect(TxnContext request, DataBus inputData)
	{
		System.out.println("ccc");
		SqlStatement stmt = new SqlStatement();
		String service_targets_type = request.getRecord("select-key").getValue(
				"service_targets_type");
		if (StringUtils.isBlank(service_targets_type)) {
			service_targets_type = "000";
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select rst.service_targets_name, ")
				.append("nvl(t.info, '-1') as waming_state, ")
				.append("rst.show_order ")
				.append("from res_service_targets rst, ")
				.append("(select t.service_targets_name, '1' as info ")
				.append("from (select object_name, ")
				.append("service_targets_name, ")
				.append("row_number() over(partition by service_targets_name order by record_time desc) rn ")
				.append("from monitor_event  ")
				.append("where waming_state = '0' ")
				.append("and object_name is not null ")
				.append("and service_targets_name is not null) t ")
				.append("where t.rn <= 1) t ")
				.append("where t.service_targets_name(+) = rst.service_targets_name"
						+ " and rst.service_targets_type = '"
						+ service_targets_type + "' and rst.is_markup = 'Y' ")
				.append("order by t.info, rst.show_order");

		System.out.println("-----FusionChartsQuery2----" + sql.toString());
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}

	/**
	 * ��ҳ12315chart
	 */
	public SqlStatement FusionChartsQuery3(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		/*
		 * String sql = "select week,max( case when
		 * (first_page_total_cls='v_12315_ss_bj') then total_num end) e1 ," + "
		 * max( case when (first_page_total_cls='v_12315_jb_bj') then total_num
		 * end) e2" + " from ( select
		 * f.first_page_total_cls,sum(f.first_page_total_num) total_num,'4' week
		 * from first_page_total_hs f where " + " f.time_day>
		 * to_char(trunc(sysdate,'iw')-8,'yyyy-MM-dd') and f.time_day<
		 * to_char(trunc(sysdate,'iw'),'yyyy-MM-dd')" + " and
		 * f.first_page_total_cls in ('v_12315_ss_bj','v_12315_jb_bj') group by
		 * f.first_page_total_cls" + " union all" + " select
		 * f2.first_page_total_cls,sum(f2.first_page_total_num) total_num,'3'
		 * week from first_page_total_hs f2 where " + " f2.time_day>
		 * to_char(trunc(sysdate,'iw')-15,'yyyy-MM-dd') and f2.time_day<
		 * to_char(trunc(sysdate,'iw')-7,'yyyy-MM-dd')" + " and
		 * f2.first_page_total_cls in ('v_12315_ss_bj','v_12315_jb_bj') group by
		 * f2.first_page_total_cls" + " union all" + " select
		 * f3.first_page_total_cls,sum(f3.first_page_total_num) total_num,'2'
		 * week from first_page_total_hs f3 where" + " f3.time_day>
		 * to_char(trunc(sysdate,'iw')-22,'yyyy-MM-dd') and f3.time_day<
		 * to_char(trunc(sysdate,'iw')-14,'yyyy-MM-dd')" + " and
		 * f3.first_page_total_cls in ('v_12315_ss_bj','v_12315_jb_bj') group by
		 * f3.first_page_total_cls" + " union all" + " select
		 * f4.first_page_total_cls,sum(f4.first_page_total_num) total_num ,'1'
		 * week from first_page_total_hs f4 where" + " f4.time_day>
		 * to_char(trunc(sysdate,'iw')-29,'yyyy-MM-dd') and f4.time_day<
		 * to_char(trunc(sysdate,'iw')-21,'yyyy-MM-dd')" + " and
		 * f4.first_page_total_cls in ('v_12315_ss_bj','v_12315_jb_bj') group by
		 * f4.first_page_total_cls) " + " group by week order by week" ;
		 */
		String sql = "select area_name ss,user_num jb,last_month_num week from sys_log_use where use_type=10001 order by last_month_num";

		// System.out.println("-----FusionChartsQuery3----"+sql);
		stmt.addSqlStmt(sql);
		return stmt;
	}

	/**
	 * ��ҳ���chart
	 */
	public SqlStatement indexAllTargets(TxnContext request, DataBus inputData)
	{
		System.out.println("ddd");
		SqlStatement stmt = new SqlStatement();
		String sql = "select t.service_targets_type, t.service_targets_name from "
				+ "res_service_targets t where t.is_markup='Y' order by t.service_targets_type";
		// System.out.println("-----FusionChartsQuery4----"+sql);
		stmt.addSqlStmt(sql);
		return stmt;
	}

	/**
	 * ��ҳ����ǰ��chart
	 */
	/*
	 * public SqlStatement FusionChartsQuery5( TxnContext request, DataBus
	 * inputData ) { SqlStatement stmt=new SqlStatement(); String sql = "select
	 * t.area_name,t.user_num,t.last_month_num from sys_log_use t where
	 * use_type='2000' order by year " ;
	 * //System.out.println("-----FusionChartsQuery5----"+sql);
	 * stmt.addSqlStmt(sql); return stmt; }
	 */
	/**
	 * ��ҳ�ӿڵ���ǰ��chart
	 */
	/*
	 * public SqlStatement FusionChartsQuery6( TxnContext request, DataBus
	 * inputData ) { SqlStatement stmt=new SqlStatement(); String sql = "select
	 * t.area_name,t.user_num,t.last_month_num from sys_log_use t where
	 * use_type='1000' order by year " ;
	 * //System.out.println("-----FusionChartsQuery6----"+sql);
	 * stmt.addSqlStmt(sql); return stmt; }
	 */
	/**
	 * ����֪ͨ
	 */
	public SqlStatement pubNotice(TxnContext request, DataBus inputData)
	{

		SqlStatement stmt = new SqlStatement();
		DataBus db = request.getRecord("select-key");
		String sys_notice_id = db.getValue("sys_notice_id");
		String sys_notice_date = db.getValue("sys_notice_date");
		String sql = " update sys_notice_info set sys_notice_date='"
				+ sys_notice_date
				+ "',sys_notice_state='1' where sys_notice_id='"
				+ sys_notice_id + "'";
		stmt.addSqlStmt(sql);
		return stmt;
	}

	public SqlStatement getSjjgName(TxnContext request, DataBus inputData)
	{

		SqlStatement stmt = new SqlStatement();
		DataBus db = request.getRecord("record");
		String jgid = db.getValue("jgid_pk");
		String sql = "select sjjgid_fk,jgmc from xt_zzjg_jg where jgid_pk='"
				+ jgid + "'";
		stmt.addSqlStmt(sql);
		return stmt;
	}

	/**
	 * ͨ��DBLINK�ķ�ʽ��ѯ��ض���Ľ���״̬
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 */
	/*
	 * public SqlStatement getMonitorObjStatus( TxnContext request, DataBus
	 * inputData ){ SqlStatement stmt = new SqlStatement(); String sql = "select
	 * m.object_type from monitor_object@zxk_monitor m,
	 * monitor_event@zxk_monitor t" + " where t.state = 0 group by
	 * m.object_type"; sql = "select t2.object_type, (case when t1.object_type
	 * is null then 1 else 0 end) state " + "from (select distinct k.object_type
	 * from monitor_event@zxk_monitor j, monitor_object@zxk_monitor k " + "where
	 * state = 0 and k.object_id = j.object_id) t1,(select distinct object_type
	 * from monitor_object@zxk_monitor) t2 " + "where t1.object_type(+) =
	 * t2.object_type"; stmt.addSqlStmt(sql); return stmt; }
	 */
	/**
	 * ����ҳ����֯��������
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 */

	public SqlStatement previewObjInfo(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer(
				"select service_targets_id,service_targets_name");
		sql.append(",codename,sum(share_num) share_num,sum(collect_num) collect_num from(");
		sql.append("select r.service_targets_id, r.service_targets_name, r.service_targets_type,");
		sql.append("count(1) share_num,0 collect_num  from res_service_targets r, share_service s");
		sql.append(" where r.service_targets_id = s.service_targets_id and r.is_markup='Y' and s.is_markup='Y'");
		sql.append(" group by r.service_targets_id,r.service_targets_name,r.service_targets_type");
		sql.append(" union all select r.service_targets_id, r.service_targets_name,r.service_targets_type,");
		sql.append(" 0 share_num,count(1) collect_num  from res_service_targets r, collect_task c");
		sql.append(" where r.service_targets_id = c.service_targets_id and r.is_markup='Y' and c.is_markup='Y'");
		sql.append(" group by r.service_targets_id,r.service_targets_name,r.service_targets_type)a,");
		sql.append("(select cd.codevalue,cd.codename  from codedata cd where cd.codetype='��Դ����_�����������')b");
		sql.append(" where a.service_targets_type=b.codevalue group by codename,service_targets_id, service_targets_name, service_targets_type");
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}

	public SqlStatement getShareServiceByObj(TxnContext request,
			DataBus inputData)
	{
		DataBus db = request.getRecord("select-key");
		String service_targets_id = db.getValue("service_targets_id");
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer(
				"select s.service_id,s.service_name,");
		sql.append("s.service_no  from share_service s where s.service_targets_id='");
		sql.append(service_targets_id);
		sql.append("' and s.is_markup='Y' order by to_number(substr(s.service_no,8))");
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}

	public SqlStatement getCollectTaskByObj(TxnContext request,
			DataBus inputData)
	{
		DataBus db = request.getRecord("select-key");
		String service_targets_id = db.getValue("service_targets_id");
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer(
				"select s.collect_task_id,s.task_name,s.collect_type, t.service_targets_name ");
		sql.append("from collect_task s, res_service_targets t where ")
				.append(" t.service_targets_id = s.service_targets_id and s.service_targets_id='");
		sql.append(service_targets_id);
		sql.append("' and s.is_markup='Y' and s.is_markup='Y' order by s.created_time");
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}

	/**
	 * ����ֲ���ϸ��Ϣͳ��ͼ
	 */
	public SqlStatement FCsharedetailXMLQuery(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String serviceTypeId = request.getRecord("detail-record").getValue(
				"serviceTypeId");
		if (serviceTypeId == null || serviceTypeId.equals("")) {
			serviceTypeId = "00";
		}
		String sql = " select r.service_targets_name typename,count(1) sernum from res_service_targets r, share_service s"
				+ " where r.service_targets_id = s.service_targets_id and r.service_targets_type='"
				+ serviceTypeId
				+ "' and r.is_markup='Y' and s.is_markup='Y' group by r.service_targets_id,r.service_targets_name order by sernum desc";

		stmt.addSqlStmt(sql);
		return stmt;
	}

	/**
	 * ����ֲ����ͳ��ͼ
	 */
	public SqlStatement FCshareServiceXMLQuery(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();

		String sql = "select b.codename typename, b.codevalue typeid, count(1) sernum"
				+ "  from (select r.service_targets_id, r.service_targets_type"
				+ "  from res_service_targets r, share_service s"
				+ "  where r.service_targets_id = s.service_targets_id and r.is_markup='Y' and s.is_markup='Y') a,"
				+ "  (select codename, codevalue from codedata c"
				+ " where c.codetype = '��Դ����_�����������') b"
				+ " where a.service_targets_type = b.codevalue"
				+ " group by b.codename, b.codevalue" + " order by sernum";
		// System.out.println("-----FusionChartsXMLQuery1----"+sql);
		stmt.addSqlStmt(sql);
		return stmt;
	}

	/**
	 * �ɼ�������ϸ��Ϣͳ��ͼ
	 */
	public SqlStatement FCColdetailXMLQuery(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String serviceTypeId = request.getRecord("coldetail-record").getValue(
				"cdserviceTypeId");
		String firstTypeid = request.getRecord("coltask-record").getValue(
				"firstTypeid");
		if (serviceTypeId == null || serviceTypeId.equals("")) {
			serviceTypeId = firstTypeid;
		}
		String sql = "select b.d coldate,nvl(a.c,0) sernum from(  "
				+ "  select substr(t.task_start_time, 6, 5) d, sum(t.collect_data_amount) c"
				+ "    from collect_joumal t"
				+ "   where t.service_targets_id = '"
				+ serviceTypeId
				+ "'"
				+ "     and t.task_start_time >="
				+ "         to_char(sysdate - 6, 'yyyy-MM-dd') || ' 00:00:00'"
				+ "   group by substr(t.task_start_time, 6, 5))a,("
				+ " select to_char(sysdate-7+rownum,'MM-dd')d from dual connect by rownum<=7)b"
				+ " where a.d(+)=b.d order by b.d";
		stmt.addSqlStmt(sql);
		return stmt;
	}

	/**
	 * �ɼ����ͳ��ͼ
	 */
	public SqlStatement FCColTaskXMLQuery(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String sql = "select r.service_targets_id typeid,r.service_targets_name typename,count(1) sernum "
				+ "from res_service_targets r, collect_task s "
				+ "where r.service_targets_id = s.service_targets_id and s.is_markup='Y' and r.is_markup='Y' "
				+ "group by r.service_targets_id,r.service_targets_name order by sernum desc";

		stmt.addSqlStmt(sql);
		return stmt;
	}

	/**
	 * 
	 * shareLogPriview(������һ�仰�����������������) 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement shareLogPriview(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String sql = "Select t.priview_id,t. share_serv_num,t.share_month_add,t.collect_serv_num,t.collect_month_add,t.avg_share_count,t.avg_share_count_rate as avg_share_count_rate ,t.avg_share_num,t.avg_share_num_rate as avg_share_num_rate,t.avg_collect_count,t.avg_collect_count_rate as avg_collect_count_rate ,t.avg_collect_num,t.avg_collect_num_rate as avg_collect_num_rate from share_log_preview t ";
		stmt.addSqlStmt(sql);
		return stmt;
	}

	/**
	 * 
	 * queryStatisticsShareLog(��ѯ����ͼ������)
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 *             SqlStatement
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement queryStatisticsShareLog(TxnContext request,
			DataBus inputData) throws TxnException
	{
		DataBus db = request.getRecord("select-key");

		String service_targets_id = db.getValue("service_targets_id");
		// String query_index = db.getValue("query_index");
		String startTime = db.getValue("startTime");
		// String statistical_granularity =
		// db.getValue("statistical_granularity");
		String endTime = db.getValue("endTime");

		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		StringBuffer sqlTime = new StringBuffer();
		System.out.println("======�������=====");
		System.out.println("����ID��" + service_targets_id);
		System.out.println("��ʼʱ�䣺" + startTime);
		System.out.println("����ʱ�䣺" + endTime);
		// System.out.println("ͳ�����ȣ�" + statistical_granularity);
		// System.out.println("ָ�꣺" + query_index);
		/*
		 * ͳ������ 00 �� 01 �� 02 �� 03 �� 04 ���� 05 ��
		 */
		String colGroup = "substr(t.log_date, 1, 10)";
		String timeWhere = "";

		// oracle��ͬ�汾�����ݿ⣬��ѯ����sql�����һ�£�Ҫ���ж�һЩsql�Ľ��
		DBPoolConnection conn = new DBPoolConnection(
				CollectConstants.DATASOURCE_DEFAULT);
		String count_sql = "select rownum from dual connect by rownum<=2 ";
		List list = conn.selectBySql(count_sql);
		String stn = list.size() == 2 ? "<=" : "<";

		// Ĭ�϶�����ͳ��
		colGroup = "substr(t.log_date, 1, 10)";
		if (startTime != null && !"".equals(startTime)) {
			timeWhere += "log_date >'" + startTime + " 00:00:00' ";

			if (endTime != null && !"".equals(endTime)) {
				timeWhere += "and log_date <='" + endTime + " 24:00:00' ";

				sqlTime.append(" select to_char(to_date('"
						+ startTime
						+ "', 'yyyy-mm-dd') + rownum - 1,'yyyy-mm-dd') mon from dual connect by rownum "
						+ stn + " (to_date('" + endTime
						+ "', 'yyyy-mm-dd') - to_date('" + startTime
						+ "', 'yyyy-mm-dd') )+1");
			} else {
				sqlTime.append(" select to_char(to_date('"
						+ startTime
						+ "', 'yyyy-mm-dd') + rownum - 1,'yyyy-mm-dd') mon from dual connect by rownum "
						+ stn + " (sysdate - to_date('" + startTime
						+ "', 'yyyy-mm-dd') )+1");

			}
		} else if (endTime != null && !"".equals(endTime)) {
			timeWhere += "log_date <='" + endTime + " 24:00:00' ";
			sqlTime.append(" select to_char(to_date('2013-05-01', 'yyyy-mm-dd') + rownum - 1,'yyyy-mm-dd') mon from dual connect by rownum "
					+ stn
					+ " (to_date('"
					+ endTime
					+ "', 'yyyy-mm-dd') - to_date('2013-05-01', 'yyyy-mm-dd') )+1");

		} else {
			sqlTime.append(" select to_char(sysdate-7 + rownum ,'yyyy-mm-dd') mon from dual connect by rownum "
					+ stn + " 7");
		}

		sql.append(
				" select "
						+ colGroup
						+ " mon, nvl(sum(t.exec_count),0) s , nvl(sum(t.sum_record_amount),0) n,round(avg(t.AVG_CONSUME_TIME),4) ts ")
				.append(" from share_log_statistics t where 1=1 ");

		// String[] service_targets_id_arry = null;
		if (service_targets_id != null && !"".equals(service_targets_id)) {
			// service_targets_id_arry = service_targets_id.split("--");
			sql.append(" and service_targets_id = '" + service_targets_id + "'");
		}

		if (timeWhere != null && !"".equals(timeWhere)) {
			sql.append(" and " + timeWhere);
		}
		sql.append(" group by " + colGroup + " ").append(" order by 1");

		String sqlUse = "select t2.mon,nvl(t1.s,0) s,nvl(t1.n,0) n,nvl(t1.ts, 0) ts  from ("
				+ sql.toString()
				+ ") t1,("
				+ sqlTime.toString()
				+ ") t2   where t1.mon(+)=t2.mon order by t2.mon asc";

		// stmt.addSqlStmt(sql.toString());
		System.out.println("����ҳ������ͼsql=" + sqlUse);
		stmt.addSqlStmt(sqlUse);
		return stmt;
	}

	/**
	 * ͳ�ƹ������
	 */
	public SqlStatement statisticsShareService(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String sql = "select max(case when service_targets_type=00 then total  end) in_share_total, "
				+ " max(case when service_targets_type=01 then total  end) out_share_total, "
				+ " max(case when service_targets_type=02 then total  end) sheng_share_total"
				+ " from(select m.service_targets_type, count(1) total from share_service t,res_service_targets m  where  t.service_targets_id = m.service_targets_id "
				+ " and t.is_markup='Y' and m.is_markup='Y' group by  m.service_targets_type ) ";

		stmt.addSqlStmt(sql);
		return stmt;
	}

	/**
	 * ͳ�Ʋɼ�����
	 */
	public SqlStatement statisticsCollectService(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String sql = "select max(case when service_targets_type=00 then total else 0 end) in_collect_total, "
				+ " max(case when service_targets_type=01 then total else 0 end) out_collect_total,"
				+ " max(case when service_targets_type=02 then total else 0 end) sheng_collect_total "
				+ " from( select m.service_targets_type, count(1) total from collect_task t,res_service_targets m "
				+ " where  t.service_targets_id = m.service_targets_id and t.is_markup='Y' and m.is_markup='Y' group by  m.service_targets_type ) ";

		stmt.addSqlStmt(sql);
		return stmt;
	}

	/**
	 * �������ʽ
	 */
	public SqlStatement statisticsShareServiceType(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String sql = "select max(case when service_targets_type=00 then share_type end) in_share_type, "
				+ " max(case when service_targets_type=01 then share_type  end) out_share_type, "
				+ " max(case when service_targets_type=02 then share_type  end) sheng_share_type "
				+ " from ( select service_targets_type,wm_concat(codename) share_type from(select t1.service_targets_type,b.codename from ("
				+ " select m.service_targets_type,t.service_type from share_service t,res_service_targets m where t.service_targets_id=m.service_targets_id"
				+ " and t.is_markup='Y' and m.is_markup='Y' group by t.service_type,m.service_targets_type) t1,(select codevalue,codename from codedata  where codetype='��Դ����_����Դ����')b"
				+ " where t1.service_type = b.codevalue )group by service_targets_type)";

		stmt.addSqlStmt(sql);
		return stmt;
	}

	/**
	 * �ɼ�����ʽ
	 */
	public SqlStatement statisticsCollectServiceType(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String sql = "select max(case when service_targets_type=00 then collect_type end) in_collect_type, "
				+ " max(case when service_targets_type=01 then collect_type  end) out_collect_type, "
				+ " max(case when service_targets_type=02 then collect_type  end) sheng_collect_type "
				+ " from ( select service_targets_type,wm_concat(codename) collect_type from( "
				+ " select t1.service_targets_type,b.codename from (select m.service_targets_type,t.collect_type from collect_task t,res_service_targets m"
				+ " where t.service_targets_id=m.service_targets_id and t.is_markup='Y' and m.is_markup='Y' "
				+ " group by t.collect_type,m.service_targets_type order by m.service_targets_type,t.collect_type) t1,(select codevalue,codename from codedata  where codetype='�ɼ�����_�ɼ�����')b "
				+ " where t1.collect_type = b.codevalue  )group by service_targets_type)";
		stmt.addSqlStmt(sql);
		return stmt;
	}

	/**
	 * ��ѯ�������Ĳɼ����񡢹������ֲ����
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement indexServiceTargetsInfo(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		/*
		 * String service_targets_type =
		 * request.getRecord("select-key").getValue( "service_targets_type"); if
		 * (StringUtils.isBlank(service_targets_type)) { service_targets_type =
		 * "000"; }
		 */
		StringBuffer sql = new StringBuffer();
		// select *
		// from (select service_targets_id,
		// service_targets_name,
		// max(service_targets_type) as service_targets_type,
		// sum(share1) as share1,
		// sum(collect1) as collect1,
		// sum(etl1) as etl1,
		// sum(show_order) as show_order
		// from (select tar.service_targets_id,
		// tar.service_targets_name,
		// max(tar.service_targets_type) as service_targets_type,
		// count(ser.service_id) as share1,
		// 0 as collect1,
		// 0 as etl1,
		// tar.show_order
		// from res_service_targets tar
		// left join share_service ser on tar.service_targets_id =
		// ser.service_targets_id
		// group by tar.service_targets_id,
		// tar.service_targets_name,
		// tar.show_order
		// union all
		// select tar.service_targets_id,
		// tar.service_targets_name,
		// max(tar.service_targets_type) as service_targets_type,
		// 0 as share1,
		// count(task.collect_task_id) as collect1,
		// 0 as etl1,
		// 0 as show_order
		// from res_service_targets tar
		// left join collect_task task on tar.service_targets_id =
		// task.service_targets_id
		// group by tar.service_targets_id, tar.service_targets_name) A
		// group by A.service_targets_id, service_targets_name)
		// order by show_order

		sql.append("select r.*, rs.service_targets_type from  ( ");
		sql.append("select sw.service_targets_id, sw.service_targets_name, sw.show_order, ");
		sql.append("nvl(sum(decode(sw.share1, 'share', sw.amount, null)), 0) share1, ");
		sql.append("nvl(sum(decode(sw.share1, 'collect', sw.amount, null)), 0) collect1, ");
		sql.append("nvl(sum(decode(sw.share1, 'etl', sw.amount, null)), 0) etl1 ");
		sql.append("from (select t.service_targets_id, t.service_targets_name, 1 amount, ");
		sql.append("'share' share1, t.show_order show_order from share_service s, res_service_targets t ");
		sql.append(" where s.service_targets_id = t.service_targets_id and s.is_markup='Y' union all ");
		sql.append("select rst.service_targets_id, rst.service_targets_name, 1 amount, ");
		sql.append("'collect' share1, rst.show_order show_order from collect_task ct, res_service_targets rst ");
		sql.append("where ct.service_targets_id = rst.service_targets_id and ct.is_markup='Y' union all ");
		sql.append("select rst.service_targets_id, rst.service_targets_name, 1 amount, ");
		sql.append("'etl' share1, rst.show_order show_order ");
		sql.append("from collect_task ct, res_service_targets rst ");
		sql.append("where ct.service_targets_id = rst.service_targets_id  and ct.is_markup='Y' ) sw ");
		sql.append("group by sw.service_targets_id, sw.service_targets_name, sw.show_order) r, ");
		sql.append("res_service_targets rs where r.service_targets_id(+) = rs.service_targets_id ");
		sql.append("and r.service_targets_id is not null and rs.is_markup = 'Y' order by r.show_order");
		stmt.addSqlStmt(sql.toString());
		// System.out.println("��ҳ��������SQL="+sql.toString());
		return stmt;
	}

	public SqlStatement getShareStatById(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String service_target_id = request.getRecord("select-key").getValue(
				"service_targets_id");
		String start_time = request.getRecord("select-key").getValue(
				"start_time");
		String end_time = request.getRecord("select-key").getValue("end_time");
		if ((start_time == null || "".equals(start_time))
				&& (end_time == null || "".equals(end_time))) {
			start_time = "to_char(add_months(sysdate,-6),'yyyy-mm-dd')";
			end_time = "to_char(sysdate,'yyyy-mm-dd')";
		} else {
			start_time = "'" + start_time + "'";
			end_time = "'" + end_time + "'";
		}

		StringBuffer sql = new StringBuffer();
		sql.append("with a as (select d.log_date from (select")
				.append(" to_char(to_date(" + start_time
						+ ",'YYYY-mm-dd')+ rownum - 1,'YYYY-mm-dd')")
				.append(" as log_date from dual connect by rownum <=")
				.append(" (to_date(" + end_time + ", 'yyyy-mm-dd') -")
				.append(" to_date(" + start_time + ", 'yyyy-mm-dd')+1)) d")
				.append(" where d.log_date not in")
				.append(" (select exception_Date log_date from exception_date))")
				.append(" select a.log_date,nvl(l.sum_num, 0) sum_num,nvl(l.sum_count, 0) sum_count,")
				.append(" nvl(l.avg_time, 0) avg_time,nvl(l.error_num, 0) error_num from a,")
				.append(" (select log_date,sum(t.sum_record_amount) sum_num,sum(t.exec_count) sum_count,")
				.append(" TO_CHAR(sum(t.sum_consume_time) / sum(t.exec_count),'fm999999990.99') avg_time,")
				.append(" sum(t.error_num) error_num from share_log_statistics t where 1 = 1")
				.append(" and t.log_date >= " + start_time
						+ " and t.log_date <= " + end_time + "")
				.append(" and t.service_targets_id = '" + service_target_id
						+ "'")
				.append(" group by log_date) l where a.log_date = l.log_date(+) order by log_date");

		/*
		 * StringBuffer sql = new
		 * StringBuffer("select log_date,sum(t.sum_record_amount) sum_num,");
		 * sql.append(
		 * "sum(t.exec_count) sum_count,TO_CHAR(sum(t.sum_consume_time) / sum(t.exec_count),'fm999999990.99') avg_time,"
		 * ); sql.append(
		 * "sum(t.error_num) error_num from share_log_statistics t where 1=1 ");
		 * if
		 * ((start_time==null||"".equals(start_time))&&(end_time==null||"".equals
		 * (end_time))){ sql.append(
		 * " and t.log_date >= to_char(add_months(sysdate,-6),'yyyy-mm-dd') and t.log_date <=to_char(add_months(sysdate,0),'yyyy-mm-dd')"
		 * ); }else{ if(start_time!=null&&!"".equals(start_time)){
		 * sql.append(" and t.log_date >= '"+start_time+"' "); }
		 * if(end_time!=null&&!"".equals(end_time)){
		 * sql.append(" and t.log_date <= '"+end_time+"'  "); } }
		 * 
		 * sql.append(" and t.service_targets_id = '").append(service_target_id).
		 * append("' "); sql.append(
		 * " and t.log_date not in(select exception_Date from exception_date)");
		 * sql.append(" group by log_date order by log_date");
		 */
		stmt.addSqlStmt(sql.toString());
		// System.out.println("��ҳ��������SQL="+sql.toString());
		System.out.println("��������������ͳ��SQL=" + sql.toString());
		return stmt;
	}

	/**
	 * 
	 * getShareInterfaceStatById(ͳ�ƹ���ӿڵ�����) 
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement getShareInterfaceStatById(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String service_id = request.getRecord("primary-key").getValue(
				"service_id");
		String start_time = request.getRecord("primary-key").getValue(
				"start_time");
		String end_time = request.getRecord("primary-key").getValue("end_time");
		/*
		 * if (StringUtils.isBlank(start_time)&&StringUtils.isBlank(end_time)) {
		 * Calendar calendar=Calendar.getInstance();
		 * calendar.add(Calendar.MONTH, -6); DateFormat df=new
		 * SimpleDateFormat("yyyy-MM-dd");
		 * start_time=df.format(calendar.getTime()); end_time=df.format(new
		 * Date()); }
		 */
		if ((start_time == null || "".equals(start_time))
				&& (end_time == null || "".equals(end_time))) {
			start_time = "to_char(add_months(sysdate,-6),'yyyy-mm-dd')";
			end_time = "to_char(sysdate,'yyyy-mm-dd')";
		} else {
			if (start_time == null || "".equals(start_time)) {
				start_time = "to_char(add_months(sysdate,-12),'yyyy-mm-dd')";
			} else {
				start_time = "'" + start_time + "'";
			}
			if (end_time == null || "".equals(end_time)) {
				end_time = "to_char(sysdate,'yyyy-mm-dd')";

			} else {
				end_time = "'" + end_time + "'";
			}

		}

		StringBuffer sql = new StringBuffer();
		sql.append("with a as (select d.log_date from (select")
				.append(" to_char(to_date(")
				.append(start_time)
				.append(",'YYYY-mm-dd')+ rownum - 1,'YYYY-mm-dd')")
				.append(" as log_date from dual connect by rownum <=")
				.append(" (to_date(" + end_time + ", 'yyyy-mm-dd') -")
				.append(" to_date(" + start_time + ", 'yyyy-mm-dd')+1)) d")
				.append(" where d.log_date not in")
				.append(" (select exception_Date log_date from exception_date))")
				.append(" select a.log_date,nvl(l.sum_num, 0) sum_num,nvl(l.sum_count, 0) sum_count,")
				.append(" nvl(l.avg_time, 0) avg_time,nvl(l.error_num, 0) error_num from a,")
				.append(" (select log_date,sum(t.sum_record_amount) sum_num,sum(t.exec_count) sum_count,")
				.append(" TO_CHAR(sum(t.sum_consume_time) / sum(t.exec_count),'fm999999990.99') avg_time,")
				.append(" sum(t.error_num) error_num from share_log_statistics t where 1 = 1")
				.append(" and t.log_date >= " + start_time
						+ " and t.log_date <= " + end_time + "")
				.append(" and t.service_id = '" + service_id + "'")
				.append(" group by log_date) l where a.log_date = l.log_date(+) order by log_date");

		/*
		 * StringBuffer sql = new
		 * StringBuffer("select log_date,sum(t.sum_record_amount) sum_num,");
		 * sql.append(
		 * "sum(t.exec_count) sum_count,TO_CHAR(sum(t.sum_consume_time) / sum(t.exec_count),'fm999999990.99') avg_time,"
		 * ); sql.append(
		 * "sum(t.error_num) error_num from share_log_statistics t where 1=1 ");
		 * if
		 * ((start_time==null||"".equals(start_time))&&(end_time==null||"".equals
		 * (end_time))){ sql.append(
		 * " and t.log_date >= to_char(add_months(sysdate,-6),'yyyy-mm-dd') and t.log_date <=to_char(add_months(sysdate,0),'yyyy-mm-dd')"
		 * ); }else{ if(start_time!=null&&!"".equals(start_time)){
		 * sql.append(" and t.log_date >= '"+start_time+"' "); }
		 * if(end_time!=null&&!"".equals(end_time)){
		 * sql.append(" and t.log_date <= '"+end_time+"' "); } }
		 * 
		 * 
		 * //sql.append(
		 * "sum(t.error_num) error_num from share_log_statistics t where t.log_date >= '"
		 * );
		 * //sql.append(start_time).append("' and t.log_date <='").append(end_time
		 * ).append("' and ");
		 * sql.append(" and t.service_id = '").append(service_id).append("' ");
		 * sql.append(
		 * " and t.log_date not in(select exception_Date from exception_date)");
		 * sql.append(" group by log_date order by log_date");
		 */
		stmt.addSqlStmt(sql.toString());
		// System.out.println("��ҳ��������SQL="+sql.toString());
		System.out.println("�������ӿ�ͳ��SQL=" + sql.toString());
		return stmt;
	}

	public SqlStatement getCollectStatById(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String service_target_id = request.getRecord("select-key").getValue(
				"service_targets_id");
		String start_time = request.getRecord("select-key").getValue(
				"start_time");
		String end_time = request.getRecord("select-key").getValue("end_time");
		// if (StringUtils.isBlank(start_time)) {
		// Calendar calendar=Calendar.getInstance();
		// calendar.add(Calendar.MONTH, -6);
		// DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		// start_time=df.format(calendar.getTime());
		// end_time=df.format(new Date());
		// }

		if ((start_time == null || "".equals(start_time))
				&& (end_time == null || "".equals(end_time))) {
			start_time = "to_char(add_months(sysdate,-6),'yyyy-mm-dd')";
			end_time = "to_char(sysdate,'yyyy-mm-dd')";
		} else {
			start_time = "'" + start_time + "'";
			end_time = "'" + end_time + "'";
		}

		StringBuffer sql = new StringBuffer();
		sql.append("with a as (select d.log_date from (select")
				.append(" to_char(to_date(" + start_time
						+ ",'YYYY-mm-dd')+ rownum - 1,'YYYY-mm-dd')")
				.append(" as log_date from dual connect by rownum <=")
				.append(" (to_date(" + end_time + ", 'yyyy-mm-dd') -")
				.append(" to_date(" + start_time + ", 'yyyy-mm-dd')+1)) d")
				.append(" where d.log_date not in")
				.append(" (select exception_Date log_date from exception_date))")
				.append(" select a.log_date,nvl(l.sum_num, 0) sum_num,nvl(l.sum_count, 0) sum_count,")
				.append(" nvl(l.avg_time, 0) avg_time,nvl(l.error_num, 0) error_num from a,")
				.append(" (select log_date,sum(t.sum_record_amount) sum_num,sum(t.exec_count) sum_count,")
				.append(" TO_CHAR(sum(t.sum_consume_time) / sum(t.exec_count),'fm999999990.99') avg_time,")
				.append(" sum(t.error_num) error_num from collect_log_statistics t where 1 = 1")
				.append(" and t.log_date >= " + start_time
						+ " and t.log_date <= " + end_time + "")
				.append(" and t.service_targets_id = '" + service_target_id
						+ "'")
				.append(" group by log_date) l where a.log_date = l.log_date(+) order by log_date");
		stmt.addSqlStmt(sql.toString());
		System.out.println("�����������ɼ�ͳ��SQL=" + sql.toString());
		return stmt;
	}

	public SqlStatement getServiceTargetsInfoRoot(TxnContext request,
			DataBus inputData)
	{
		String service_targets_id = inputData.get("service_targets_id") == null ? ""
				: inputData.get("service_targets_id").toString();
		String service_targets_type = inputData.get("service_targets_type") == null ? ""
				: inputData.get("service_targets_type").toString();
		String start_time=inputData.get("created_time").toString();
		String dateFrom ="";
		String dateTo ="";
		if (StringUtils.isNotBlank(start_time)) {
			String[] times = DateUtil.getDateRegionByDatePicker(start_time,
					false);
			if (StringUtils.isNotBlank(times[0])) {
				dateFrom=times[0];
			}
			if (StringUtils.isNotBlank(times[1])) {
				dateTo=times[1];
			}
		}
		
		String date = " 1=1 ";
		if (StringUtils.isBlank(dateFrom) && StringUtils.isBlank(dateTo)) {
			// ��Ϊ�� Ĭ�ϲ����������
			date = "log_date>=to_char(add_months(sysdate, -2), 'yyyy-MM') || '-01'"
					+ " and log_date < to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01'";
		} else if (!StringUtils.isBlank(dateFrom)
				&& !StringUtils.isBlank(dateTo)) {
			// ������ ������
			date = "log_date>='" + dateFrom + "'" + " and log_date <= '"
					+ dateTo + "'";
		} else if (!StringUtils.isBlank(dateFrom)
				&& StringUtils.isBlank(dateTo)) {
			// �޽���ʱ��
			date = "log_date>='" + dateFrom + "'";
		} else if (StringUtils.isBlank(dateFrom)
				&& !StringUtils.isBlank(dateTo)) {
			// �޿�ʼʱ��
			date = "log_date <= '" + dateTo + "'";
		}
		StringBuffer rootsql = new StringBuffer();
		// ��ȡ���з�������������� �������ĸ�
		rootsql.append(
				"select r.service_targets_id,nvl(c_count,0) c_count,nvl(c_amount,0) c_amount,nvl(c_err,0) c_err,nvl(s_count,0) s_count,nvl(s_amount,0) s_amount,nvl(s_err,0) s_err,")
				.append(" r.service_targets_name ,r.codename as service_status ,r.is_share,r.is_collect from ")
				.append(" (select (case WHEN c.service_targets_id IS NULL then s.service_targets_id else c.service_targets_id end) as service_targets_id,")
				.append(" c.c_count,c.c_amount,c.c_err,s.s_count,s.s_amount,s.s_err from")
				.append(" (select c1.service_targets_id,nvl(sum(c1.sum_record_amount),0) c_amount,nvl(sum(c1.exec_count),0) c_count,nvl(sum(c1.error_num),0) c_err")
				.append(" from collect_log_statistics c1,collect_task c2 where ")
				.append(date)
				.append(" and c1.log_date not in(select exception_Date from exception_date)")
				.append(" and c1.task_id = c2.collect_task_id and c2.is_markup = 'Y'")
				.append(" group by c1.service_targets_id) c full outer join")
				.append(" (select s1.service_targets_id,nvl(sum(s1.sum_record_amount),0) s_amount,nvl(sum(s1.exec_count),0) s_count,nvl(sum(s1.error_num),0) s_err")
				.append(" from share_log_statistics s1,share_service s2 where ")
				.append(date)
				.append(" and s1.log_date not in(select exception_Date from exception_date)")
				.append(" and s1.service_id = s2.service_id and s2.is_markup = 'Y'")
				.append(" group by s1.service_targets_id) s")
				.append(" on c.service_targets_id=s.service_targets_id ) d,")
				.append(" (select t.*,c.codename from res_service_targets t ,codedata c")
				.append(" where t.is_markup = 'Y' and t.is_formal = 'Y'") 
				.append(" and c.codetype = '��Դ����_һ�����״̬'")
				.append(" and t.service_status = c.codevalue ");
				if (StringUtils.isNotBlank(service_targets_id)) {
					rootsql.append(" and t.service_targets_id='")
						.append(service_targets_id).append("'");
				}
				if (StringUtils.isNotBlank(service_targets_type)) {
					rootsql.append(" and t.service_targets_type='")
						.append(service_targets_type).append("'");
				}
				rootsql.append(" ) r where d.service_targets_id(+)=r.service_targets_id")
					.append(" order by r.service_targets_name");
		System.out.println("rootSQL="+rootsql.toString());

		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(rootsql.toString());
		return stmt;
	}

	public SqlStatement getServiceTargetsInfoLeaf(TxnContext request,
			DataBus inputData)
	{
		String service_targets_id = inputData.get("service_targets_id") == null ? ""
				: inputData.get("service_targets_id").toString();
		String service_targets_type = inputData.get("service_targets_type") == null ? ""
				: inputData.get("service_targets_type").toString();
		String start_time=inputData.get("created_time").toString();
		String dateFrom ="";
		String dateTo ="";
		if (StringUtils.isNotBlank(start_time)) {
			String[] times = DateUtil.getDateRegionByDatePicker(start_time,
					false);
			if (StringUtils.isNotBlank(times[0])) {
				dateFrom=times[0];
			}
			if (StringUtils.isNotBlank(times[1])) {
				dateTo=times[1];
			}
		}
		String date = " 1=1 ";
		if (StringUtils.isBlank(dateFrom) && StringUtils.isBlank(dateTo)) {
			// ��Ϊ�� Ĭ�ϲ����������
			date = "log_date>=to_char(add_months(sysdate, -2), 'yyyy-MM') || '-01'"
					+ " and log_date < to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01'";
		} else if (!StringUtils.isBlank(dateFrom)
				&& !StringUtils.isBlank(dateTo)) {
			// ������ ������
			date = "log_date>='" + dateFrom + "'" + " and log_date <= '"
					+ dateTo + "'";
		} else if (!StringUtils.isBlank(dateFrom)
				&& StringUtils.isBlank(dateTo)) {
			// �޽���ʱ��
			date = "log_date>='" + dateFrom + "'";
		} else if (StringUtils.isBlank(dateFrom)
				&& !StringUtils.isBlank(dateTo)) {
			// �޿�ʼʱ��
			date = "log_date <= '" + dateTo + "'";
		}
		// ��ȡ����ͷ�������ͳ�� ����Ҷ�ӽڵ�
		StringBuffer leafsql = new StringBuffer();
		leafsql.append("select t1.*,r.service_targets_name from(")
				.append(" with a as")
				.append(" (select c.codetype,c.codevalue,c.codename from codedata c")
				.append(" where c.codetype in ('�ɼ�����_�ɼ�����','��Դ����_�鵵����״̬','��Դ����_����Դ����'))")
				.append(" select c2.service_targets_id,c2.collect_task_id taskorservice_id,")
				.append(" '(�ɼ�)' || c2.task_name mc,nvl(c1.c_amount,0) c_amount,")
				.append(" nvl(c1.c_count,0) c_count,nvl(c1.c_err,0) c_err,")
				.append(" null s_count,null s_err,null s_amount,c3.codename ,")
				.append(" c4.codename status_cn,'' service_no,'C' cors")
				.append(" from (select service_targets_id,")
				.append(" nvl(sum(sum_record_amount), 0) c_amount,")
				.append(" nvl(sum(exec_count), 0) c_count,")
				.append(" nvl(sum(error_num), 0) c_err,task_id")
				.append(" from collect_log_statistics where ")
				.append(date)
				.append(" and log_date not in (select exception_Date from exception_date)")
				.append(" group by service_targets_id, task_id) c1,")
				.append(" (select * from collect_task where is_markup = 'Y') c2,")
				.append(" (select a.codevalue,a.codename from a where a.codetype='�ɼ�����_�ɼ�����') c3,")
				.append(" (select a.codevalue,a.codename from a where a.codetype='��Դ����_�鵵����״̬') c4")
				.append(" where c1.task_id(+) = c2.collect_task_id")
				.append(" and c2.task_status=c4.codevalue(+)")
				.append(" and c2.collect_type=c3.codevalue(+)")
				.append(" union all")
				.append(" select c2.service_targets_id,")
				.append(" c2.service_id taskorservice_id,")
				.append(" '(����)' || c2.service_name mc,")
				.append(" null c_count,null c_err,null c_amount,")
				.append(" nvl(c1.s_count,0) s_count,nvl(c1.s_err,0) s_err,")
				.append(" nvl(c1.s_amount,0) s_amount,")
				.append(" c3.codename ,c4.codename status_cn,c2.service_no,'S' cors")
				.append(" from (select service_targets_id,")
				.append(" nvl(sum(sum_record_amount), 0) s_amount,")
				.append(" nvl(sum(exec_count), 0) s_count,")
				.append(" nvl(sum(error_num), 0) s_err,service_id")
				.append(" from share_log_statistics where ")
				.append(date)
				.append(" and log_date not in (select exception_Date from exception_date)")
				.append(" group by service_targets_id, service_id) c1,")
				.append(" (select * from share_service where is_markup = 'Y') c2,")
				.append(" (select a.codevalue,a.codename from a where a.codetype='��Դ����_����Դ����') c3,")
				.append(" (select a.codevalue,a.codename from a where a.codetype='��Դ����_�鵵����״̬') c4")
				.append(" where c1.service_id(+) = c2.service_id")
				.append(" and c2.SERVICE_STATE=c4.codevalue(+)")
				.append(" and c2.SERVICE_TYPE=c3.codevalue(+)) t1,")
				.append(" (select * from res_service_targets where is_markup = 'Y'")
				.append(" and is_formal = 'Y'");
			if (StringUtils.isNotBlank(service_targets_id)) {
				leafsql.append(" and service_targets_id='")
					.append(service_targets_id).append("'");
			}
			if (StringUtils.isNotBlank(service_targets_type)) {
				leafsql.append(" and service_targets_type='")
					.append(service_targets_type).append("'");
			}
				
			leafsql.append(" ) r where t1.service_targets_id(+)=r.service_targets_id ")
				.append(" order by r.service_targets_name, t1.mc");
		System.out.println("leafSQL="+leafsql.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(leafsql.toString());
		return stmt;
	}
	public SqlStatement getCollectErr(TxnContext request,
			DataBus inputData)
	{
		String location = inputData.get("location").toString();
 		String service_targets_id = inputData.get("service_targets_id") == null ? ""
				: inputData.get("service_targets_id").toString();
 		String collect_task_id = inputData.get("collect_task_id") == null ? ""
				: inputData.get("collect_task_id").toString();
		String dateFrom = inputData.get("startTime") == null ? "" : inputData
				.get("startTime").toString();
		String dateTo = inputData.get("endTime") == null ? "" : inputData.get(
				"endTime").toString();
		String date = " 1=1 ";
		if (StringUtils.isBlank(dateFrom) && StringUtils.isBlank(dateTo)) {
			// ��Ϊ�� Ĭ�ϲ����������
			date = "j.task_start_time>=to_char(add_months(sysdate, -2), 'yyyy-MM') || '-01 00:00:00'"
					+ " and j.task_start_time < to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01 00:00:00'";
		} else if (!StringUtils.isBlank(dateFrom)
				&& !StringUtils.isBlank(dateTo)) {
			// ������ ������
			if(dateFrom!=null&&dateFrom.length()>12){
				date = "j.task_start_time>='" + dateFrom + "'" + " and j.task_start_time <= '"
						+ dateTo + "'";
			}else{
				date = "j.task_start_time>='" + dateFrom + " 00:00:00'" + " and j.task_start_time <= '"
						+ dateTo + " 23:59:59'";
			}
			
		} else if (!StringUtils.isBlank(dateFrom)
				&& StringUtils.isBlank(dateTo)) {
			// �޽���ʱ��
			date = "j.task_start_time>='" + dateFrom + " 00:00:00'";
		} else if (StringUtils.isBlank(dateFrom)
				&& !StringUtils.isBlank(dateTo)) {
			// �޿�ʼʱ��
			date = "j.task_start_time <= '" + dateTo + " 23:59:59'";
		}
		
		StringBuffer sql = new StringBuffer();
		if("root".equals(location)){
			sql.append("select sys_guid() row_id,j.task_name name, j.service_targets_name,")
			 .append("j.task_start_time start_time,j.task_end_time end_time,j.return_codes")
			 .append(" from collect_joumal j,collect_task t where ")
			 .append(" j.collect_task_id=t.collect_task_id and t.is_markup='Y' and j.is_formal='Y' and j.return_codes not in('BAIC0000','BAIC0010','TAX0010')") 
			 .append(" and j.service_targets_id='").append(service_targets_id).append("'")
			 .append(" and ").append(date)
			 .append(" and substr(j.task_start_time,0,10) not in (select exception_Date from exception_date)");
		}else if("leaf".equals(location)){
			sql.append("select sys_guid() row_id,j.task_name name, j.service_targets_name,")
			 .append("j.task_start_time start_time,j.task_end_time end_time,j.return_codes")
			 .append(" from collect_joumal j,collect_task t where ")
			 .append(" j.collect_task_id=t.collect_task_id and t.is_markup='Y' and j.is_formal='Y' and j.return_codes not in('BAIC0000','BAIC0010','TAX0010')") 
			 .append(" and j.collect_task_id='").append(collect_task_id).append("'")
			 .append(" and ").append(date)
			 .append(" and substr(j.task_start_time,0,10) not in (select exception_Date from exception_date) order by j.task_start_time desc");
		}
		System.out.println(sql.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt("select count(1) from ("+sql.toString()+")");
		return stmt;
	}
	public SqlStatement getShareErr(TxnContext request,
			DataBus inputData)
	{
		String location = inputData.get("location").toString();
 		String service_targets_id = inputData.get("service_targets_id") == null ? ""
				: inputData.get("service_targets_id").toString();
 		String service_id = inputData.get("service_id") == null ? ""
				: inputData.get("service_id").toString();
		String dateFrom = inputData.get("startTime") == null ? "" : inputData
				.get("startTime").toString();
		String dateTo = inputData.get("endTime") == null ? "" : inputData.get(
				"endTime").toString();
		String date = " 1=1 ";
		if (StringUtils.isBlank(dateFrom) && StringUtils.isBlank(dateTo)) {
			// ��Ϊ�� Ĭ�ϲ����������
			date = "l.service_start_time>=to_char(add_months(sysdate, -2), 'yyyy-MM') || '-01 00:00:00'"
					+ " and l.service_start_time < to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01 00:00:00'";
		} else if (!StringUtils.isBlank(dateFrom)
				&& !StringUtils.isBlank(dateTo)) {
			if(dateFrom!=null&&dateFrom.length()>12){
				// ������ ������
				date = "l.service_start_time>='" + dateFrom + "'" + " and l.service_start_time<= '"
						+ dateTo + "'";
			}else{
				// ������ ������
				date = "l.service_start_time>='" + dateFrom + " 00:00:00'" + " and l.service_start_time<= '"
						+ dateTo + " 23:59:59'";
			}
			
		} else if (!StringUtils.isBlank(dateFrom)
				&& StringUtils.isBlank(dateTo)) {
			// �޽���ʱ��
			date = "l.service_start_time>='" + dateFrom + " 00:00:00'";
		} else if (StringUtils.isBlank(dateFrom)
				&& !StringUtils.isBlank(dateTo)) {
			// �޿�ʼʱ��
			date = "l.service_start_time <= '" + dateTo + " 23:59:59'";
		}
		
	  
	 
		StringBuffer sql = new StringBuffer();
		if("root".equals(location)){
			sql.append("select sys_guid() row_id,l.service_name name,l.service_targets_name,")
			 .append("l.service_start_time start_time,l.service_end_time end_time,l.return_codes")
			 .append(" from share_log_error l where ")
			 .append(" l.service_targets_id='").append(service_targets_id).append("'")
			 .append(" and ").append(date)
			 .append(" and substr(l.service_start_time,0,10) not in (select exception_Date from exception_date)");
			/*sql.append("select sys_guid() row_id,l.service_name name,l.service_targets_name,")
			 .append("l.service_start_time start_time,l.service_end_time end_time,l.return_codes")
			 .append(" from share_log l, share_service s where ")
			 .append(" l.log_type='02' and l.service_id=s.service_id and s.is_markup='Y'  and l.return_codes not in('BAIC0000','BAIC0010')") 
			 .append(" and l.service_targets_id='").append(service_targets_id).append("'")
			 .append(" and ").append(date)
			 .append(" and substr(l.service_start_time,0,10) not in (select exception_Date from exception_date)");*/
		}else if("leaf".equals(location)){
			sql.append("select sys_guid() row_id,l.service_name name,l.service_targets_name,")
			 .append("l.service_start_time start_time,l.service_end_time end_time,l.return_codes")
			 .append(" from share_log_error l where ")
			 .append(" l.service_id='").append(service_id).append("'")
			 .append(" and ").append(date)
			 .append(" and substr(l.service_start_time,0,10) not in (select exception_Date from exception_date) order by l.service_start_time desc");
		}
		System.out.println(sql.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt("select count(1) from ("+sql.toString()+")");
		return stmt;
	}
	
	/**
	 * 
	 * getTargetInfo ��ȡ��������б�����ѯ����"
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement getTargetInfo(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		sql.append("with tmp as (select r.service_targets_id   key,")
		.append(" r.service_targets_name title,r.service_targets_type,")
		.append(" show_order from res_service_targets r")
		.append(" where r.is_markup = 'Y' group by r.service_targets_id,") 
		.append(" r.service_targets_type,r.service_targets_name, show_order)") 
		.append(" select * from (select * from tmp where service_targets_type = '000' order by show_order)")            
		.append(" union all select *  from (select * from tmp where service_targets_type <> '000' order by service_targets_type, title)");     
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}
	
	
	/**
	 * ִ����SQL����Ĵ���
	 */
	public void afterExecuteStmt(DaoFunction func, TxnContext request,
			DataBus[] inputData, String outputNode) throws TxnException
	{

	}

	/**
	 * XXX:�û��Զ����SQL��� ���ڶ��¼�Ĳ�ѯ��䣬��Ҫ����������䣺��ѯ����ȡ��¼��������� ������������䣬ֻ��Ҫ����һ�����
	 * 
	 * @param request
	 *            ���׵�������
	 * @param inputData
	 *            ������������ڵ�
	 * @return public SqlStatement loadSysNoticeInfoList( TxnContext request,
	 *         DataBus inputData ) { SqlStatement stmt = new SqlStatement( );
	 *         stmt.addSqlStmt( "select * from sys_notice_info" );
	 *         stmt.setCountStmt( "select count(*) from sys_notice_info" );
	 *         return stmt; }
	 */

}
