package com.gwssi.dw.aic.bj.xb.dao;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class XbAppealBase extends BaseTable
{
	public XbAppealBase()
	{

	}

	/**
	 * 注册SQL语句
	 */
	protected void register()
	{
		this.registerSQLFunction("query_xb_appeal_base",
				DaoFunction.SQL_ROWSET, "根据查询条件检索申诉信息");
		this.registerSQLFunction("query_one_xb_appeal_base",
				DaoFunction.SQL_ROWSET, "根据查询条件检索申诉信息");
		this.registerSQLFunction("query_xb_appeal_result",
				DaoFunction.SQL_ROWSET, "根据查询条件检索申诉处理信息");
		this.registerSQLFunction("query_xb_appeal_to_case",
				DaoFunction.SQL_ROWSET, "根据查询条件检索调解转立案信息");
		this
				.registerSQLFunction("query_zxxx", DaoFunction.SQL_ROWSET,
						"查询咨询信息");
		this
				.registerSQLFunction("query_jyxx", DaoFunction.SQL_ROWSET,
						"查询建议信息");
		this.registerSQLFunction("query_zxxx_detail", DaoFunction.SQL_ROWSET,
				"查询咨询信息详细");
		this.registerSQLFunction("query_jyxx_detail", DaoFunction.SQL_ROWSET,
				"查询建议信息详细");
		this.registerSQLFunction("query_xb_appeal_to_case_for_dcqk",
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

	/**
	 * 根据查询条件检索申诉信息
	 * 
	 * @creator caiwd
	 * @createtime 2008-10-24 上午10:08:45
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 * 
	 */
	public SqlStatement query_xb_appeal_base(TxnContext request,
			DataBus inputData) throws TxnException
	{

		StringBuffer bufsql = new StringBuffer();
		String reg_time_start = request.getRecord("select-key").getValue(
				"reg_time_start");
		String reg_time_end = request.getRecord("select-key").getValue(
				"reg_time_end");
		String enter_date_start = request.getRecord("select-key").getValue(
				"enter_date_start");
		String enter_date_end = request.getRecord("select-key").getValue(
				"enter_date_end");
		String reg_no = request.getRecord("select-key").getValue("reg_no");
		String ent_name = request.getRecord("select-key").getValue("ent_name");
		String mdse_name = request.getRecord("select-key")
				.getValue("mdse_name");
		String info_type = request.getRecord("select-key")
				.getValue("info_type");
		String ent_id = request.getRecord("select-key").getValue("ent_id");
		String status = request.getRecord("select-key").getValue("status");
		String reg_id = request.getRecord("select-key").getValue("reg_id");

		SqlStatement stmt = new SqlStatement();
		String selsql = "select a.status,b.rece_unit_code,a.reg_id, a.reg_no, a.name, a.ent_name, b.mdse_name, a.reg_dep, "
				+ "a.rec_unit_thi, a.info_type,a.reg_unit_code from at_reg_info a,at_reg_app_info b "
				+ "where a.reg_id = b.reg_id ";
		String countSql = "select count(a.reg_id) from at_reg_info a, at_reg_app_info b "
				+ "where a.reg_id = b.reg_id ";

		if (StringUtils.isNotBlank(reg_id)) {
			bufsql.append(" and a.reg_id='" + reg_id+"' ");
		}

		if (!"".equals(info_type) && null != info_type) {
			bufsql.append(" and a.info_type='" + info_type+"' ");
		}
		if (!"".equals(reg_no) && null != reg_no) {
			bufsql.append(" and a.reg_no = '" + reg_no + "'");
		}
		if (!"".equals(ent_name) && null != ent_name) {
			bufsql.append(" and a.ent_name like  '%" + ent_name + "%'");
		}
		if (!"".equals(mdse_name) && null != mdse_name) {
			bufsql.append(" and a.name like '%" + mdse_name + "%'");
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
		log.debug(">>>>>>>status" + status);
		countSql = countSql + bufsql;
		//System.out.println("申诉sql:" + selsql + bufsql);
		//System.out.println("申诉count-sql:" + countSql);
		stmt.addSqlStmt(selsql + bufsql);
		stmt.setCountStmt(countSql);

		return stmt;
	}

	public SqlStatement query_one_xb_appeal_base(TxnContext request,
			DataBus inputData) throws TxnException
	{
		String reg_id = request.getRecord("select-key").getValue("reg_id");

		SqlStatement stmt = new SqlStatement();

		String selsql = "select a.status,d.case_name,c.case_chr, b.work_type,b.work_type1,b.work_type2,a.rec_unit_thi,a.reg_id, a.reg_dep, to_char(to_date(a.reg_time,'YYYY-MM-DD   HH24:MI:SS'),'yyyy-mm-dd' ) reg_time, a.acc_reg_per, a.info_ori_fir, a.inc_form, a.name, a.sex, a.per_type, a.per_ide, a.tel, a.post_code, a.addr, a.ent_name, a.reg_no, a.pt_type, a.ub_ind_type, a.reg_addr, a.pros_addr, b.brand_name, b.mdse_name, b.goods_class, b.ques_fir, b.ques_sec, b.goods_money, b.type_spf, b.quan, b.invo_am, b.bri_st, b.require, b.rece_unit_code,to_char(to_date(b.acc_time,'YYYY-MM-DD   HH24:MI:SS'),'yyyy-mm-dd' ) acc_time,b.is_case from at_reg_info a left join at_reg_app_info b on a.reg_id=b.reg_id  left join at_oper_repo_info2 c on a.reg_id = c.reg_id left join at_classes_case_info d  on a.reg_id = d.reg_id  where a.reg_id= '"
				+ reg_id + "'";

		stmt.addSqlStmt(selsql);
		return stmt;
	}

	public SqlStatement query_xb_appeal_result(TxnContext request,
			DataBus inputData) throws TxnException
	{

		String reg_id = request.getRecord("select-key").getValue("reg_id");

		SqlStatement stmt = new SqlStatement();
		String selsql = "select media_name,case_reas, rece_situ, unre_reas_dl, to_char(to_date(enter_date,'YYYY-MM-DD   HH24:MI:SS'),'yyyy-mm-dd' ) enter_date, media_dep_name, set_per, media_state, rece_unit_code, proc_resul_deta, check_per, rela_am, retu_am, ame_am, red_eco_los, spi_ame_am, dou_ame_am, supp_law, set_date, medi_name  from at_oper_app_info where reg_id='"
				+ reg_id + "'";
		stmt.addSqlStmt(selsql);

		return stmt;
	}

	/**
	 * 根据查询条件检索调解转立案信息
	 * 
	 * @creator caiwd
	 * @createtime 2008-10-24 上午10:36:23
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 * 
	 */
	public SqlStatement query_xb_appeal_to_case(TxnContext request,
			DataBus inputData) throws TxnException
	{

		String reg_id = request.getRecord("select-key").getValue("reg_id");

		SqlStatement stmt = new SqlStatement();
		String selsql = "select a.case_id, to_char(to_date(a.case_fi_date,'YYYY-MM-DD   HH24:MI:SS'),'yyyy-mm-dd' ) case_fi_date, to_char(to_date(a.end_cas_date,'YYYY-MM-DD   HH24:MI:SS'),'yyyy-mm-dd' ) end_cas_date, a.hand_unit_name, a.hand_per, to_char(to_date(a.enter_date_fj,'YYYY-MM-DD   HH24:MI:SS'),'yyyy-mm-dd' ) enter_date_fj, a.is_clo_case, a.clo_case_rea, to_char(to_date(a.clo_case_date,'YYYY-MM-DD   HH24:MI:SS'),'yyyy-mm-dd' ) clo_case_date, b.case_bus_case_id,a.dilatation,a.is_proc,a.un_proc_reas,a.tranf_rea_type,to_char(to_date(a.tranf_date,'YYYY-MM-DD   HH24:MI:SS'),'yyyy-mm-dd' ) tranf_date,a.case_chr,a.accp_tranf_auth_fir,a.accp_tranf_auth_sec,a.puni_type,a.instr_limit,a.gruel_no,a.case_bel,a.com_direc,a.oth_ad,a.break_dl,a.Illeg_Income,a.pen_am,a.forf_am,a.pena_expro,a.victim_num,a.sac_num,a.inj_num from at_oper_repo_info2 a left join case_bus_case b on a.case_id=b.case_no where a.reg_id = '"
				+ reg_id + "'";
		stmt.addSqlStmt(selsql);

		return stmt;
	}

	public SqlStatement query_zxxx(TxnContext request, DataBus inputData)
			throws TxnException
	{
		StringBuffer bufsql = new StringBuffer();

		String reg_time_start = request.getRecord("select-key").getValue(
				"reg_time_start");
		String reg_time_end = request.getRecord("select-key").getValue(
				"reg_time_end");
		String enter_date_start = request.getRecord("select-key").getValue(
				"enter_date_start");
		String enter_date_end = request.getRecord("select-key").getValue(
				"enter_date_end");
		String reg_no = request.getRecord("select-key").getValue("reg_no");
		String mdse_name = request.getRecord("select-key")
				.getValue("mdse_name");
		String info_type = request.getRecord("select-key")
				.getValue("info_type");
		String ent_name = request.getRecord("select-key").getValue("ent_name");
		String ent_id = request.getRecord("select-key").getValue("ent_id");
		String status = request.getRecord("select-key").getValue("status");
		String reg_id = request.getRecord("select-key").getValue("reg_id");

		SqlStatement stmt = new SqlStatement();
		String selsql = "select a.status,a.reg_id, a.reg_no, a.name, b.bus_type,"
				+ " b.indu_org_sec, b.un_indu_org_sec, b.rece_unit_name, c.fee_unit_name "
				+ "from at_reg_info a,at_reg_advi_info b ,at_oper_advi_info c "
				+ " where a.reg_id = b.reg_id ";

		String countSql = "select count(a.reg_id) from at_reg_info a, at_reg_advi_info b "
				+ "where a.reg_id = b.reg_id ";
		if (!"".equals(info_type) && null != info_type) {
			bufsql.append(" and a.info_type='" + info_type+"' ");
		}
		if (StringUtils.isNotBlank(reg_id)) {
			bufsql.append(" and a.reg_id = '" + reg_id + "'");
		}
		if (!"".equals(reg_no) && null != reg_no) {
			bufsql.append(" and a.reg_no = '" + reg_no + "'");
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
		if (!"".equals(ent_name) && null != ent_name) {
			bufsql.append(" and a.ent_name = '" + ent_name + "'");
		}
		if (!"".equals(ent_id) && null != ent_id) {
			bufsql.append(" and a.ent_id = '" + ent_id + "'");
		}
		if (!"".equals(status) && null != status) {
			bufsql.append(" and a.status = '" + status + "'");
		}
		countSql = countSql + bufsql;
		//System.out.println("咨询Count-sql:" + countSql);
		bufsql.append(" and a.reg_id = c.reg_id(+)");
		//System.out.println("咨询sql:" + selsql + bufsql);
		stmt.addSqlStmt(selsql + bufsql);
		stmt.setCountStmt(countSql);
		return stmt;
	}

	public SqlStatement query_jyxx(TxnContext request, DataBus inputData)
			throws TxnException
	{
		StringBuffer bufsql = new StringBuffer();

		String reg_time_start = request.getRecord("select-key").getValue(
				"reg_time_start");
		String reg_time_end = request.getRecord("select-key").getValue(
				"reg_time_end");
		String enter_date_start = request.getRecord("select-key").getValue(
				"enter_date_start");
		String enter_date_end = request.getRecord("select-key").getValue(
				"enter_date_end");
		String reg_no = request.getRecord("select-key").getValue("reg_no");
		String mdse_name = request.getRecord("select-key")
				.getValue("mdse_name");
		String info_type = request.getRecord("select-key")
				.getValue("info_type");
		String ent_name = request.getRecord("select-key").getValue("ent_name");
		String ent_id = request.getRecord("select-key").getValue("ent_id");
		String status = request.getRecord("select-key").getValue("status");
		String reg_id = request.getRecord("select-key").getValue("reg_id");

		SqlStatement stmt = new SqlStatement();
		String selsql = "select a.status,a.reg_id,a.reg_no,a.name, a.ent_name, a.reg_dep,"
				+ "a.rec_unit_thi, a.info_type, a.reg_unit_code from at_reg_info a, at_reg_sugg_info b"
				+ " where a.reg_id = b.reg_id ";

		String countSql = "select count(a.reg_id) from at_reg_info a, at_reg_sugg_info b "
				+ "where a.reg_id = b.reg_id ";
		if (!"".equals(info_type) && null != info_type) {
			bufsql.append(" and a.info_type='" + info_type+"' ");
		}
		if (StringUtils.isNotBlank(reg_id)) {
			bufsql.append(" and a.reg_id = '" + reg_id + "'");
		}
		if (!"".equals(reg_no) && null != reg_no) {
			bufsql.append(" and a.reg_no = '" + reg_no + "'");
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
		if (!"".equals(ent_name) && null != ent_name) {
			bufsql.append(" and a.ent_name like '%" + ent_name + "%'");
		}
		if (!"".equals(ent_id) && null != ent_id) {
			bufsql.append(" and a.ent_id = '" + ent_id + "'");
		}
		if (!"".equals(status) && null != status) {
			bufsql.append(" and a.status = '" + status + "'");
		}
		countSql = countSql + bufsql;
		//System.out.println("建议Count-sql:" + countSql);
		//System.out.println("建议sql:" + selsql + bufsql);
		stmt.addSqlStmt(selsql + bufsql);
		stmt.setCountStmt(countSql);
		return stmt;
	}

	public SqlStatement query_zxxx_detail(TxnContext request, DataBus inputData)
			throws TxnException
	{
		String reg_id = request.getRecord("select-key").getValue("reg_id");
		StringBuffer sql = new StringBuffer();
		sql
				.append(
						"select a.status,d.case_name,b.rela_ques_sec,b.brand_type,a.reg_id, a.tel_no, a.be_call, a.info_ori_fir, a.inc_form, a.reg_dep, a.acc_reg_per, to_char(to_date(a.reg_time,'YYYY-MM-DD   HH24:MI:SS'),'yyyy-mm-dd' ) reg_time, a.name, a.sex, a.age, a.per_ide,  a.per_type, a.tel, a.addr, a.post_code, b.mdse_name, b.goods_class, b.brand_name, b.type_spf, b.ques_fir, b.ques_sec, a.ent_name, a.reg_no, a.pt_type, a.pros_addr, a.tel_2, a.ub_ind_type, b.exc_un_rece, b.exc_un_rece_oth_cont, b.bus_type, b.indu_org_fir, b.indu_org_sec, b.indu_org_cont, b.rela_ques_fir, b.rela_law_fir, b.rela_law_sec, b.bri_st, b.is_case, b.un_indu_org_fir,b.un_indu_org_sec, c.result, c.fee_per_name, to_char(to_date(c.enter_date,'YYYY-MM-DD   HH24:MI:SS'),'yyyy-mm-dd' ) enter_date from at_reg_info a left join at_reg_advi_info b on a.reg_id = b.reg_id left join at_oper_advi_info c on b.reg_id = c.reg_id left join at_classes_case_info d on a.reg_id = d.reg_id   where")
				.append(" a.reg_id='").append(reg_id).append("'");
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}

	public SqlStatement query_jyxx_detail(TxnContext request, DataBus inputData)
			throws TxnException
	{
		String reg_id = request.getRecord("select-key").getValue("reg_id");
		StringBuffer sql = new StringBuffer();
		sql
				.append(
						"select  a.status,b.work_type1,b.work_type2,a.reg_id, to_char(to_date(a.reg_time,'YYYY-MM-DD   HH24:MI:SS'),'yyyy-mm-dd' ) reg_time, a.reg_dep, a.acc_reg_per, a.tel_no, a.be_call, a.inc_form, a.info_ori_fir, a.name, a.sex, a.age, a.per_ide, a.tel, a.post_code, a.addr, a.per_type, b.sys_type, b.work_type, b.sugg_unit_name, a.pros_addr_type,a.pros_addr, a.tel_2, a.pt_type, a.ub_ind_type, b.sugg, b.is_case, c.rece_unit_name, c.proc_stat from at_reg_info a left join at_reg_sugg_info  b on a.reg_id = b.reg_id left join  at_oper_sugg_info c on b.reg_id = c.reg_id where")
				.append(" a.reg_id='").append(reg_id).append("'");
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}

	public SqlStatement query_xb_appeal_to_case_for_dcqk(TxnContext request,
			DataBus inputData) throws TxnException
	{
		String reg_id = request.getRecord("select-key").getValue("reg_id");
		StringBuffer sql = new StringBuffer();
		sql
				.append(
						"select  b.inqu_resu  from at_oper_repo_info2 a left join at_oper_repo_proc b on a.proc_id = b.proc_id  where")
				.append(" a.reg_id='").append(reg_id).append("'");
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sql.toString());
		return stmt;
	}

}
