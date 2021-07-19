package com.gwssi.dw.aic.bj.newmon.tm.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class MonTmBasInfo extends BaseTable
{
	public MonTmBasInfo()
	{

	}

	/**
	 * 注册SQL语句
	 */
	protected void register()
	{
		this.registerSQLFunction("queryMonTmBasInfo_List",
				DaoFunction.SQL_ROWSET, "商标监管信息查询");
		this.registerSQLFunction("viewMonTmBasInfo_Detail",
				DaoFunction.SQL_SELECT, "查看商标监管详细信息");
		this.registerSQLFunction("queryMonTmEntRlt_List",
				DaoFunction.SQL_ROWSET, "商标监管认定信息查询");
		this.registerSQLFunction("queryRegMonTmBasInfo_List",
				DaoFunction.SQL_ROWSET, "查询主体名称为条件商标监管列表");
		this.registerSQLFunction("queryRegBusEntForTM_One",
				DaoFunction.SQL_ROWSET, "商标主体信息关联");

	}

	/**
	 * 商标主体信息关联 MonTmBasInfo:queryRegBusEntForTM_One
	 * 
	 * @creater - caiwd
	 * @creatertime - Nov 27, 2008
	 * @param context
	 * @param inputDate
	 * @return
	 * @throws TxnException
	 * @returnType SqlStatement
	 */
	public SqlStatement queryRegBusEntForTM_One(TxnContext context,
			DataBus inputDate) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		String tm_reger = context.getRecord("record").getString("tm_reger") == null ? ""
				: context.getRecord("record").getString("tm_reger");
		String ent_title = context.getRecord("record").getString("ent_title") == null ? ""
				: context.getRecord("record").getString("ent_title");

		String ent_name = (!"".equals(ent_title) ? ent_title : tm_reger);
		String sql = "SELECT t.reg_bus_ent_id,t.ent_sort,t.ent_name,t.reg_no"
				+ " FROM v_reg_bus_ent t" + " WHERE t.ent_name='" + ent_name
				+ "' AND t.ent_sort in ('NZ','WZ','JG','SQ','GT')";

		stmt.addSqlStmt(sql);
		return stmt;
	}

	/**
	 * 
	 * MonTmBasInfo:queryRegMonTmBasInfo_List
	 * 
	 * @creater - caiwd
	 * @creatertime - Nov 27, 2008
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 * @returnType SqlStatement
	 */
	public SqlStatement queryRegMonTmBasInfo_List(TxnContext request,
			DataBus inputData) throws TxnException
	{
		// 获取查询条件
		SqlStatement stmt = new SqlStatement();
		DataBus databus = request.getRecord("select-key");
		String ent_name = databus.getString("ent_name");

		/*
		 * SELECT
		 * a.mon_tm_bas_info_id,a.tm_name,a.tm_reg_id,a.tm_reger,a.tm_reg_date,a.main_id,a.tm_type,b.top_bra_sig
		 * FROM mon_tm_bas_info a LEFT JOIN mon_tm_ent_rlt b ON
		 * a.tm_reg_id=b.tm_reg_id WHERE (b.tm_reg_id is null OR (
		 * b.top_bra_sig='1' AND b.top_bra_state='0') OR
		 * (to_char(sysdate,'YYYY-MM-DD')>= b.eff_from AND
		 * to_char(sysdate,'YYYY-MM-DD')<=b.eff_to AND b.top_bra_sig='2' )) AND
		 * ((a.tm_reger='' AND a.main_id is null) OR a.ent_title='')
		 */

		String querySql = "SELECT "
				+ " a.mon_tm_bas_info_id,a.tm_name,a.tm_reg_id,a.tm_reger,a.ent_title"
				+ " ,a.tm_reg_date,a.main_id,a.tm_type,b.top_bra_sig"
				+ " FROM mon_tm_bas_info a LEFT JOIN mon_tm_ent_rlt b"
				+ " ON a.tm_reg_id=b.tm_reg_id"
				+ " WHERE ("
				+ " b.tm_reg_id is null"
				+ " OR ( b.top_bra_sig='1' AND b.top_bra_state='0')"
				+ " OR (to_char(sysdate,'YYYY-MM-DD')>= b.eff_from AND to_char(sysdate,'YYYY-MM-DD')<=b.eff_to AND b.top_bra_sig='2' )"
				+ " )" + " AND ((a.tm_reger='" + ent_name + "'"
				+ " AND a.ent_title is null) OR a.ent_title='" + ent_name + "'"
				+ " )";
		String countSql = "SELECT count(1) FROM (" + querySql + ")";
		querySql = querySql
				+ "ORDER BY a.tm_reg_date,a.mon_tm_bas_info_id DESC";

		stmt.addSqlStmt(querySql);
		stmt.setCountStmt(countSql);
		return stmt;
	}

	/**
	 * 商标监管认定信息查询 MonTmBasInfo:queryMonTmEntRlt_List
	 * 
	 * @creater - caiwd
	 * @creatertime - Nov 18, 2008
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 * @returnType SqlStatement
	 */
	public SqlStatement queryMonTmEntRlt_List(TxnContext request,
			DataBus inputData) throws TxnException
	{
		// 获取查询条件
		SqlStatement stmt = new SqlStatement();
		DataBus databus = request.getRecord("select-key");
		String tm_reg_id = databus.getString("tm_reg_id");
		String top_bra_sig = databus.getString("top_bra_sig");

		String querySql = null;
		String countSql = null;

		if ("1".equals(top_bra_sig)) {
			/*
			 * select a.* from mon_tm_ent_rlt a where a.top_bra_sig='1' and
			 * a.top_bra_state='0' and a.tm_reg_id ='1061788' order by
			 * a.idy_date,a.mon_tm_ent_rlt_id
			 */
			querySql = "SELECT a.mon_tm_ent_rlt_id,a.tm_reg_id,a.top_bra_sig,a.idy_date"
					+ ",a.eff_from,a.eff_to,a.idy_ser,a.idy_name,a.top_bra_state,a.idy_no,a.ent_name"
					+ " FROM mon_tm_ent_rlt a WHERE "
					+ " a.top_bra_sig='1' AND a.top_bra_state='0'";

		} else if ("2".equals(top_bra_sig)) {
			/*
			 * select a.* from mon_tm_ent_rlt a where a.top_bra_sig='2' and
			 * sysdate >= a.eff_from and sysdate <= a.eff_to and
			 * a.tm_reg_id='3183668' order by a.idy_date,a.mon_tm_ent_rlt_id
			 */
			querySql = "SELECT a.mon_tm_ent_rlt_id,a.tm_reg_id,a.top_bra_sig,a.idy_date"
					+ ",a.eff_from,a.eff_to,a.idy_ser,a.idy_name,a.top_bra_state,a.idy_no,a.ent_name"
					+ " FROM mon_tm_ent_rlt a WHERE "
					+ " a.top_bra_sig='2' AND to_char(sysdate,'YYYY-MM-DD') >= a.eff_from AND to_char(sysdate,'YYYY-MM-DD') <= a.eff_to ";
		} else {
			/*
			 * select a.* from mon_tm_ent_rlt a where ((a.top_bra_sig='1' and
			 * a.top_bra_state='1') or (a.top_bra_sig='2' and sysdate <
			 * a.eff_from) or (a.top_bra_sig='2' and sysdate > a.eff_to)) and
			 * a.tm_reg_id ='' order by a.idy_date,a.mon_tm_ent_rlt_id
			 */

			querySql = "SELECT a.mon_tm_ent_rlt_id,a.tm_reg_id,a.top_bra_sig,a.idy_date"
					+ ",a.eff_from,a.eff_to,a.idy_ser,a.idy_name,a.top_bra_state,a.idy_no,a.ent_name"
					+ " FROM mon_tm_ent_rlt a WHERE "
					+ " ((a.top_bra_sig='1' and a.top_bra_state='1') or "
					+ "  (a.top_bra_sig='2' and to_char(sysdate,'YYYY-MM-DD') < a.eff_from) or "
					+ "  (a.top_bra_sig='2' and to_char(sysdate,'YYYY-MM-DD') > a.eff_to)"
					+ " )";
		}
		querySql = querySql + " AND a.tm_reg_id ='" + tm_reg_id + "'";
		countSql = "SELECT count(1) FROM (" + querySql + ")";
		querySql = querySql + " ORDER BY a.idy_date,a.mon_tm_ent_rlt_id";

		log.debug("querySql>>>>>>>>" + querySql);
		log.debug("countSql>>>>>>>>" + countSql);

		stmt.addSqlStmt(querySql);
		stmt.setCountStmt(countSql);
		return stmt;
	}

	/**
	 * 查看商标监管详细信息 MonTmBasInfo:viewMonTmBasInfo_Detail
	 * 
	 * @creater - caiwd
	 * @creatertime - Nov 18, 2008
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 * @returnType SqlStatement
	 */
	public SqlStatement viewMonTmBasInfo_Detail(TxnContext request,
			DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		DataBus databus = request.getRecord("select-key");
		String mon_tm_bas_info_id = databus.getString("mon_tm_bas_info_id");

		/* b.mon_main_basic_id,b.ent_title,b.reg_no,a.mon_tm_bas_info_id,a.tm_ent_id,a.tm_reg_id,a.tm_type,a.tm_reg_date,a.priority,a.tm_name,a.eff_from,a.eff_to,a.ass_color,a.tm_sign,a.aba_prop,a.tm_agt,a.tm_reger,a.tm_reg_eng_name,a.mar_addr,a.add_eng,a.g_or_s,a.prompt,a.prompt_date,a.d_or_f,a.country,a.forb_sell,a.frt_exa_issue,a.reg_issue,a.priority_date,a.main_id */

		String querySql = "SELECT b.reg_bus_ent_id,b.mon_main_basic_id,b.ent_title,b.reg_no" +
				",a.mon_tm_bas_info_id,a.tm_ent_id,a.tm_reg_id,a.tm_type,a.tm_reg_date" +
				",a.priority,a.tm_name,a.eff_from,a.eff_to,a.ass_color,a.tm_sign" +
				",a.aba_prop,a.tm_agt,a.tm_reger,a.tm_reg_eng_name,a.mar_addr,a.add_eng" +
				",a.g_or_s,a.prompt,a.prompt_date,a.d_or_f,a.country,a.forb_sell" +
				",a.frt_exa_issue,a.reg_issue,a.priority_date,a.main_id "
				+ " FROM mon_tm_bas_info a "
				+ " LEFT JOIN mon_main_basic b ON a.main_id = b.main_id"
				+ " WHERE a.mon_tm_bas_info_id ='" + mon_tm_bas_info_id + "'";

		stmt.addSqlStmt(querySql);
		return stmt;
	}

	/**
	 * 商标监管信息查询 MonTmBasInfo:queryMonTmBasInfo_List
	 * 
	 * @creater - caiwd
	 * @creatertime - Nov 17, 2008
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 * @returnType SqlStatement
	 */
	public SqlStatement queryMonTmBasInfo_List(TxnContext request,
			DataBus inputData) throws TxnException
	{
		// 获取查询条件
		SqlStatement stmt = new SqlStatement();
		DataBus databus = request.getRecord("select-key");
		String tm_name = databus.getValue("tm_name");
		String tm_reg_id = databus.getValue("tm_reg_id");
		String tm_type = databus.getValue("tm_type");
		String top_bra_sig = databus.getValue("top_bra_sig");

		String querySql = null;
		String countSql = null;

		if ("1".equals(top_bra_sig)) {
			querySql = "SELECT "
					+ "a.mon_tm_bas_info_id,a.tm_reg_id,a.tm_ent_id,a.main_id,a.tm_name,a.tm_type,"
					+ "a.tm_reg_date as tm_reg_date,a.tm_reger,b.top_bra_sig,b.mon_tm_ent_rlt_id"
					+ " FROM mon_tm_bas_info a "
					+ " LEFT JOIN mon_tm_ent_rlt b "
					+ " ON a.tm_reg_id = b.tm_reg_id "
					+ " WHERE b.top_bra_state='0' and b.top_bra_sig='1'";
			if (null != tm_type && !"".equals(tm_type)) {
				querySql = querySql + " AND a.tm_name='" + tm_name + "'";
			}
			if (null != tm_reg_id && !"".equals(tm_reg_id)) {
				querySql = querySql + " AND a.tm_reg_id='" + tm_reg_id + "'";
			}
			if (null != tm_name && !"".equals(tm_name)) {
				querySql = querySql + " AND a.tm_name='" + tm_name + "'";
			}
			countSql = "select count(1) from (" + querySql + ")";
			querySql = querySql
					+ " ORDER BY a.tm_reg_date desc,a.mon_tm_bas_info_id ";

		} else if ("2".equals(top_bra_sig)) {

			querySql = "SELECT "
					+ "a.mon_tm_bas_info_id,a.tm_reg_id,a.tm_ent_id,a.main_id,a.tm_name,a.tm_type,"
					+ "a.tm_reg_date as tm_reg_date,a.tm_reger"
					+
					// ",b.top_bra_sig,b.mon_tm_ent_rlt_id" +
					" FROM mon_tm_bas_info a LEFT JOIN mon_tm_ent_rlt b "
					+ " ON a.tm_reg_id = b.tm_reg_id "
					+ " WHERE b.top_bra_sig ='2' AND ( to_char(sysdate,'YYYY-MM-DD') >=b.eff_from and to_char(sysdate,'YYYY-MM-DD') <= b.eff_to)";

			if (null != tm_type && !"".equals(tm_type)) {
				querySql = querySql + " AND a.tm_name='" + tm_name + "'";
			}
			if (null != tm_reg_id && !"".equals(tm_reg_id)) {
				querySql = querySql + " AND a.tm_reg_id='" + tm_reg_id + "'";
			}
			if (null != tm_name && !"".equals(tm_name)) {
				querySql = querySql + " AND a.tm_name='" + tm_name + "'";
			}
			countSql = "select count(1) from (" + querySql + ")";
			querySql = querySql
					+ " ORDER BY a.tm_reg_date desc,a.mon_tm_bas_info_id";
		} else {
			/*
			 * select count(*) from mon_tm_bas_info a LEFT JOIN mon_tm_ent_rlt b
			 * on a.tm_reg_id = b.tm_reg_id where b.top_bra_sig is null or
			 * ((b.top_bra_sig='1' and b.top_bra_state='1') or (b.top_bra_sig =
			 * '2' and sysdate < b.eff_from) or (b.top_bra_sig = '2' and sysdate >
			 * b.eff_to))
			 */

			querySql = "SELECT "
					+ "a.mon_tm_bas_info_id,a.tm_reg_id,a.tm_ent_id,a.main_id,a.tm_name,a.tm_type,"
					+ "a.tm_reg_date as tm_reg_date,a.tm_reger"
					+
					// ",b.top_bra_sig,b.mon_tm_ent_rlt_id" +
					" FROM mon_tm_bas_info a "
					+ " LEFT JOIN mon_tm_ent_rlt b "
					+ " ON a.tm_reg_id = b.tm_reg_id "
					+ " WHERE "
					+ " (b.tm_reg_id IS NULL "
					+ " OR (b.top_bra_sig='1' and b.top_bra_state='1')"
					+ " OR (b.top_bra_sig ='2' and to_char(sysdate,'YYYY-MM-DD') < b.eff_from)"
					+ " OR (b.top_bra_sig = '2' and to_char(sysdate,'YYYY-MM-DD') > b.eff_to)) ";
			if (null != tm_type && !"".equals(tm_type)) {
				querySql = querySql + " AND a.tm_name='" + tm_name + "'";
			}
			if (null != tm_reg_id && !"".equals(tm_reg_id)) {
				querySql = querySql + " AND a.tm_reg_id='" + tm_reg_id + "'";
			}
			if (null != tm_name && !"".equals(tm_name)) {
				querySql = querySql + " AND a.tm_name='" + tm_name + "'";
			}
			countSql = "select count(1) from (" + querySql + ")";
			querySql = querySql
					+ " ORDER BY a.tm_reg_date desc,a.mon_tm_bas_info_id ";
		}

		log.debug(querySql);
		log.debug(countSql);

		stmt.addSqlStmt(querySql);
		stmt.setCountStmt(countSql);
		return stmt;
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

}
