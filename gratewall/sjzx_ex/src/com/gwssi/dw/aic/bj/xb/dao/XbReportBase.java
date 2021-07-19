package com.gwssi.dw.aic.bj.xb.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class XbReportBase extends BaseTable
{
	public XbReportBase()
	{

	}

	/**
	 * 注册SQL语句
	 */
	protected void register()
	{
		this.registerSQLFunction("query_xb_report_base",
				DaoFunction.SQL_ROWSET, "根据查询条件检索举报信息");
		this.registerSQLFunction("select_xb_report_base",
				DaoFunction.SQL_ROWSET, "根据查询条件检索举报信息");
		this.registerSQLFunction("select_xb_report_result",
				DaoFunction.SQL_ROWSET, "根据查询条件检索举报处理信息");
		this.registerSQLFunction("select_xb_report_result_case",
				DaoFunction.SQL_ROWSET, "根据查询条件检索带案件举报处理信息");
		this.registerSQLFunction("select_xb_rpt_confi_goods",
				DaoFunction.SQL_ROWSET, "根据查询条件检索罚没物品信息");
		this.registerSQLFunction("select_xb_rpt_trea_fake_goods",
				DaoFunction.SQL_ROWSET, "根据查询条件检索假冒伪劣物资信息");
		this.registerSQLFunction("select_case_bus_mater",
				DaoFunction.SQL_ROWSET, "根据查询条件检索带案件罚没物品信息");
		this.registerSQLFunction("select_xb_report_result_case_for_dcqk",
				DaoFunction.SQL_ROWSET, "查询调查情况");

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

	public SqlStatement query_xb_report_base(TxnContext request,
			DataBus inputData) throws TxnException
	{

		StringBuffer bufsql = new StringBuffer();

		String reg_time_start = request.getString("select-key:reg_time_start");
		String reg_time_end = request.getString("select-key:reg_time_end");
		String enter_date_start = request
				.getString("select-key:enter_date_start");
		String enter_date_end = request.getString("select-key:enter_date_end");
		String reg_no = request.getString("select-key:reg_no");
		String ent_name = request.getString("select-key:ent_name");
		String mdse_name = request.getString("select-key:mdse_name");
		String info_type = request.getString("select-key:info_type");
		String ent_id = request.getString("select-key:ent_id");
		String status = request.getString("select-key:status");
		String reg_id = request.getString("select-key:reg_id");
		SqlStatement stmt = new SqlStatement();
		String selsql = "select a.reg_id, a.reg_no, a.name, a.ent_name, b.mdse_name, a.reg_dep, "
				+ "a.rec_unit_thi,a.status from at_reg_info a,at_reg_repo_info b "
				+ "where a.reg_id=b.reg_id ";

		String countSql = "select count(a.reg_id) from at_reg_info a,at_reg_repo_info b "
			+ "where a.reg_id = b.reg_id ";
		
		if (!"".equals(info_type) && null != info_type) {
			bufsql.append(" and a.info_type='" + info_type+"'");
		}
		if (!"".equals(reg_id) && null != reg_id) {
			bufsql.append(" and a.reg_id = '" + reg_id + "'");
		}
		if (!"".equals(reg_no) && null != reg_no) {
			bufsql.append(" and a.reg_no = '" + reg_no + "'");
		}
		if (!"".equals(ent_name) && null != ent_name) {
			bufsql.append(" and a.ent_name like '%" + ent_name + "%'");
		}
		if (!"".equals(mdse_name) && null != mdse_name) {
			bufsql.append(" and b.mdse_name like '%" + mdse_name + "%'");
		}
		if (!"".equals(reg_time_start) && null != reg_time_start) {
			bufsql.append(" and a.reg_time >= '" + reg_time_start + " 00:00:00"
					+ "'");
		}
		if (!"".equals(reg_time_end) && null != reg_time_end) {
			bufsql.append(" and a.reg_time <= '" + reg_time_end + " 23:59:59"
					+ "'");
		}
		if (!"".equals(enter_date_start) && null != enter_date_start) {
			bufsql.append(" and b.enter_date >= '" + enter_date_start
					+ " 00:00:00" + "'");
		}
		if (!"".equals(enter_date_end) && null != enter_date_end) {
			bufsql.append(" and b.enter_date <= '" + enter_date_end
					+ " 23:59:59" + "'");
		}
		if (!"".equals(ent_id) && null != ent_id) {
			bufsql.append(" and a.ent_id = '" + ent_id + "'");
		}
		if (!"".equals(status) && null != status) {
			bufsql.append(" and a.status = '" + status + "'");
		}

		countSql = countSql + bufsql;
		System.out.println("举报Count-sql:" + countSql);
		System.out.println("举报sql:" + selsql + bufsql);
		stmt.addSqlStmt(selsql + bufsql);
		stmt.setCountStmt(countSql);

		return stmt;
	}

	/**
	 * 根据查询条件检索举报信息
	 * 
	 * @creator caiwd
	 * @createtime 2008-10-24 下午01:42:33
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 * 
	 * 
	 */
	public SqlStatement select_xb_report_base(TxnContext request,
			DataBus inputData) throws TxnException
	{

		String reg_id = request.getString("select-key:reg_id");
		SqlStatement stmt = new SqlStatement();
		String selsql = "select a.status,d.case_name,a.rec_unit_thi,b.work_type1,b.work_type,work_type2,a.reg_id, a.reg_dep, to_char(to_date(a.reg_time,'YYYY-MM-DD   HH24:MI:SS'),'yyyy-mm-dd' ) reg_time, a.acc_reg_per, a.info_ori_fir, a.inc_form, a.limit, a.feed_requ, a.feed_requ_unit, a.name, a.sex, a.per_type, a.per_ide, a.tel, a.post_code, a.addr, a.ent_name, a.reg_no, a.pt_type, a.ub_ind_type, a.reg_addr, a.pros_addr, b.goods_type, b.mdse_name, b.goods_class,  b.ques_fir, b.ques_sec, b.acci_lev, to_char(to_date(b.acc_time,'YYYY-MM-DD   HH24:MI:SS'),'yyyy-mm-dd' ) acc_time, b.repe_count, b.demand, b.bri_st,b.brand_name,b.is_case from at_reg_info a left join at_reg_repo_info b on a.reg_id=b.reg_id  left join at_classes_case_info d on a.reg_id = d.reg_id  where a.reg_id='"
				+ reg_id + "'";
		stmt.addSqlStmt(selsql);

		return stmt;
	}

	public SqlStatement select_xb_report_result_case(TxnContext request,
			DataBus inputData) throws TxnException
	{

		String reg_id = request.getString("select-key:reg_id");

		SqlStatement stmt = new SqlStatement();
		String selsql = "select  a.un_proc_reas,to_char(to_date(a.case_fi_date,'YYYY-MM-DD   HH24:MI:SS'),'yyyy-mm-dd' ) case_fi_date,to_char(to_date(a.end_cas_date,'YYYY-MM-DD   HH24:MI:SS'),'yyyy-mm-dd' ) end_cas_date,a.hand_unit_name,a.hand_per,to_char(to_date(a.enter_date_fj,'YYYY-MM-DD   HH24:MI:SS'),'yyyy-mm-dd' ) enter_date_fj,a.is_clo_case,a.clo_case_rea,to_char(to_date(a.clo_case_date,'YYYY-MM-DD   HH24:MI:SS'),'yyyy-mm-dd' ) clo_case_date,a.com_direc,a.oth_ad,a.break_dl,a.case_bel,a.gruel_no,a.instr_limit,a.accp_tranf_auth_fir,a.accp_tranf_auth_sec,to_char(to_date(a.tranf_date,'YYYY-MM-DD   HH24:MI:SS'),'yyyy-mm-dd' ) tranf_date,a.tranf_rea_type,a.is_proc, a.dilatation, a.app_procedure,  a.case_id,  a.case_chr, a.puni_type, a.illeg_income, a.pen_am, a.forf_am, a.pena_expro, a.victim_num,a.sac_num, a.inj_num, b.case_bus_case_id   from at_oper_repo_info a left join case_bus_case b on a.case_id=b.case_no where reg_id='"
				+ reg_id + "'";

		stmt.addSqlStmt(selsql);

		return stmt;
	}

	public SqlStatement select_xb_report_result(TxnContext request,
			DataBus inputData) throws TxnException
	{

		// String reg_bus_ent_id = request
		// .getString("select-key:reg_bus_ent_id");
		// log.debug("mon_bus_day_check_id>>>>" + reg_bus_ent_id);

		String xb_report_result_id = request
				.getString("select-key:xb_report_result_id");

		log.debug("xb_report_result_id>>>>" + xb_report_result_id);

		SqlStatement stmt = new SqlStatement();
		String selsql = "select * from xb_report_result where xb_report_result_id= '"
				+ xb_report_result_id + "'";

		stmt.addSqlStmt(selsql);

		return stmt;
	}

	public SqlStatement select_xb_rpt_confi_goods(TxnContext request,
			DataBus inputData) throws TxnException
	{

		// String reg_bus_ent_id = request
		// .getString("select-key:reg_bus_ent_id");
		// log.debug("mon_bus_day_check_id>>>>" + reg_bus_ent_id);

		String xb_report_result_id = request
				.getString("select-key:xb_report_result_id");

		log.debug("xb_report_result_id>>>>" + xb_report_result_id);

		SqlStatement stmt = new SqlStatement();
		String selsql = "select * from xb_rpt_confi_goods where xb_report_result_id= '"
				+ xb_report_result_id + "'";

		log.debug("querySql:" + selsql);

		stmt.addSqlStmt(selsql);

		return stmt;
	}

	public SqlStatement select_case_bus_mater(TxnContext request,
			DataBus inputData) throws TxnException
	{

		// String reg_bus_ent_id = request
		// .getString("select-key:reg_bus_ent_id");
		// log.debug("mon_bus_day_check_id>>>>" + reg_bus_ent_id);

		String pen_dec_no = request.getString("select-key:pen_dec_no");

		log.debug("pen_dec_no>>>>" + pen_dec_no);

		SqlStatement stmt = new SqlStatement();
		// String selsql="select * from xb_rpt_confi_goods where
		// xb_report_result_id= '" + xb_report_result_id +"'";

		String selsql = "select a.Case_Bus_Mater_ID XB_RPT_CONFI_GOODS_ID,a.Mater_Name	RPT_Forf_GOODS_NAME,a.Quan	RPT_Forf_GOODS_NUM,a.Mea_Unit	RPT_Forf_GOODS_UNIT,"
				+ "a.Price	RPT_Forf_GOODS_VALUE from Case_Bus_Mater a, case_bus_case_new b "
				+ "where a.case_no=b.case_no and b.doc_no='" + pen_dec_no + "'";
		log.debug("querySql:" + selsql);

		stmt.addSqlStmt(selsql);

		return stmt;
	}

	public SqlStatement select_xb_rpt_trea_fake_goods(TxnContext request,
			DataBus inputData) throws TxnException
	{

		// String reg_bus_ent_id = request
		// .getString("select-key:reg_bus_ent_id");
		// log.debug("mon_bus_day_check_id>>>>" + reg_bus_ent_id);

		String xb_report_result_id = request
				.getString("select-key:xb_report_result_id");

		log.debug("xb_report_result_id>>>>" + xb_report_result_id);

		SqlStatement stmt = new SqlStatement();
		String selsql = "select * from xb_rpt_trea_fake_goods where xb_report_result_id= '"
				+ xb_report_result_id + "'";

		// log.debug("querySql:" +selsql );

		stmt.addSqlStmt(selsql);

		return stmt;
	}

	public SqlStatement select_xb_report_result_case_for_dcqk(
			TxnContext request, DataBus inputData) throws TxnException
	{

		String reg_id = request.getString("select-key:reg_id");

		SqlStatement stmt = new SqlStatement();
		String selsql = " select  b.inqu_resu  from at_oper_repo_info a left join at_oper_repo_proc b on a.proc_id = b.proc_id   where reg_id='"
				+ reg_id + "'";

		stmt.addSqlStmt(selsql);

		return stmt;
	}

}
