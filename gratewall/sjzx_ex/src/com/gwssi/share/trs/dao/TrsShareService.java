package com.gwssi.share.trs.dao;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.component.logger.TxnLogger;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

import com.gwssi.common.util.DateUtil;
import com.gwssi.webservice.server.ExcuteService;
import com.trs.client.TRSConnection;
import com.trs.client.TRSException;
import com.trs.client.TRSResultSet;

/**
 * 数据表[trs_share_service]的处理类
 * 
 * @author Administrator
 * 
 */
public class TrsShareService extends BaseTable
{
	// 日志
	protected static Logger	logger	= TxnLogger.getLogger(ExcuteService.class
											.getName());

	private static String	SEARCH_HOST;

	private static String	SEARCH_PORT;

	private static String	SEARCH_USER;

	private static String	SEARCH_PASSWORD;

	private static String	SEARCH_STATS;

	static {
		SEARCH_HOST = java.util.ResourceBundle.getBundle("trs").getString(
				"searchHost");
		SEARCH_PORT = java.util.ResourceBundle.getBundle("trs").getString(
				"searchPort");
		SEARCH_USER = java.util.ResourceBundle.getBundle("trs").getString(
				"searchUser");
		SEARCH_PASSWORD = java.util.ResourceBundle.getBundle("trs").getString(
				"searchPassword");
		SEARCH_STATS = java.util.ResourceBundle.getBundle("trs").getString(
				"searchStats");
	}

	public TrsShareService()
	{

	}

	/**
	 * 注册用户自定义的SQL语句
	 */
	protected void register()
	{
		// 以下是注册用户自定义函数的过程
		// 包括三个参数：SQL语句的名称，类型，描述
		// 业务类可以通过以下函数调用:
		// table.executeFunction( "loadTrsShareServiceList", context, inputNode,
		// outputNode );
		// XXX: registerSQLFunction( "loadTrsShareServiceList",
		// DaoFunction.SQL_ROWSET, "获取trs服务接口列表" );
		registerSQLFunction("getMaxTrsService_no", DaoFunction.SQL_SELECT,
				"获取最大服务编号");
		registerSQLFunction("getTrsCountBySvrObjType", DaoFunction.SQL_ROWSET,
				"统计TRS和服务对象数量");
		registerSQLFunction("queryTrsShareList", DaoFunction.SQL_ROWSET,
				"统计TRS和服务对象数量");
		registerSQLFunction("getTrsCountBySvrState", DaoFunction.SQL_ROWSET,
				"统计TRS和服务状态数量");
		registerSQLFunction("getTrsServiceRule", DaoFunction.SQL_ROWSET,
				"统计TRS服务规则");
		registerSQLFunction("queryTrsRule", DaoFunction.SQL_ROWSET,
				"查询TRS服务规则");
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
	 * XXX:用户自定义的SQL语句 对于多记录的查询语句，需要生成两个语句：查询语句和取记录数量的语句 对于其它的语句，只需要生成一个语句
	 * 
	 * @param request
	 *            交易的上下文
	 * @param inputData
	 *            生成语句的输入节点
	 * @return public SqlStatement loadTrsShareServiceList( TxnContext request,
	 *         DataBus inputData ) { SqlStatement stmt = new SqlStatement( );
	 *         stmt.addSqlStmt( "select * from trs_share_service" );
	 *         stmt.setCountStmt( "select count(*) from trs_share_service" );
	 *         return stmt; }
	 */

	public SqlStatement getMaxTrsService_no(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt("select * from (select TO_NUMBER(ltrim(t.trs_service_no,'trsService')) as snum  from trs_share_service t  where t.trs_service_no like 'trsService%' order by snum desc) where rownum =1");
		return stmt;
	}

	public SqlStatement getTrsCountBySvrObjType(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String sql = "with tmp as (select service_targets_id key,service_targets_name title,service_targets_type, sum(c) amount,"
				+ " show_order from("
				+ "select a.*,case when b.trs_service_id is null then 0 else 1 end c  from("
				+ "select r.service_targets_id,r.service_targets_type,r.service_targets_name,r.show_order "
				+ "from res_service_targets r where r.is_markup = 'Y')a,(select * from trs_share_service "
				+ "where is_markup = 'Y')b where a.service_targets_id = b.service_targets_id(+)) "
				+ "group by service_targets_id,service_targets_type,service_targets_name,show_order)"
				+ " select * from (select * from tmp where service_targets_type = '000' order by show_order)"
				+ " union all select * from (select * from tmp where service_targets_type <> '000'"
				+ " order by service_targets_type, title)";
		// System.out.println("4300001--"+sql);
		stmt.addSqlStmt(sql);
		return stmt;
	}

	public SqlStatement getTrsCountBySvrState(TxnContext request,
			DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String sql = "select t2.codevalue, t2.codename, nvl(t1.countn, 0) amount"
				+ " from (select t.service_state, count(1) countn"
				+ " from trs_share_service t"
				+ " where t.is_markup = 'Y'"
				+ " group by t.service_state) t1,"
				+ " (select * from codedata c where c.codetype = '资源管理_一般服务状态') t2"
				+ " where t1.service_state(+) = t2.codevalue"
				+ " order by codevalue desc";

		stmt.addSqlStmt(sql);
		return stmt;
	}

	/**
	 * 查询全文检索规则
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement getTrsServiceRule(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String trs_service_id = request.getRecord("primary-key").getValue(
				"trs_service_id");
		String sql = "select week, wm_concat(start_time || '-' || end_time) time_str,";
		sql+="max(t.times_day) times_day,max(t.count_dat) count_dat,max(t.total_count_day)" +
		" total_count_day from share_service_rule t where t.service_id = '"+trs_service_id+
		"' group by week order by week ";
		stmt.addSqlStmt(sql);
		return stmt;
	}
	
	/**
	 * 
	 * getTrsServiceRule(这里用一句话描述这个方法的作用)    
	 * TODO(这里描述这个方法适用条件 – 可选)    
	 * TODO(这里描述这个方法的执行流程 – 可选)    
	 * TODO(这里描述这个方法的使用方法 – 可选)    
	 * TODO(这里描述这个方法的注意事项 – 可选)    
	 * @param request
	 * @param inputData
	 * @return        
	 * SqlStatement       
	 * @Exception 异常对象    
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public SqlStatement queryTrsRule(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String trs_service_id = request.getRecord("primary-key").getValue(
				"trs_service_id");
		String sql = "select * from share_service_rule where service_id='"+trs_service_id+"'";
		
		stmt.addSqlStmt(sql);
		return stmt;
	}

	public SqlStatement queryTrsShareList(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String service_targets_type = request.getRecord("select-key").getValue(
				"service_targets_type");
		String service_state = request.getRecord("select-key").getValue(
				"service_state");
		System.out.println("service_state=" + service_state);
		String service_targets_id = request.getRecord("select-key").getValue(
				"service_targets_id");
		String created_time = request.getRecord("select-key").getValue(
				"created_time");
		StringBuffer sql = new StringBuffer(
				"select t1.*,t1.service_targets_id as service_targets_id1,SUBSTR(t1.last_modify_time,0,10) as lasttime,t2.yhxm from (select * from trs_share_service s where 1=1 ");
		if (StringUtils.isNotBlank(service_targets_type)) {
			sql.append(" and s.service_targets_id in(select service_targets_id from res_service_targets r");
			sql.append(" where r.service_targets_type='").append(
					service_targets_type);
			sql.append("') ");
		}
		if (StringUtils.isNotBlank(service_targets_id)) {
			sql.append(" and s.service_targets_id ='")
					.append(service_targets_id).append("'");
		}
		if (StringUtils.isNotBlank(service_state)) {
			sql.append(" and s.service_state ='").append(service_state)
					.append("'");
		}
		if (StringUtils.isNotBlank(created_time)) {
			if (!created_time.equals("点击选择日期")) {
				String[] times = DateUtil.getDateRegionByDatePicker(
						created_time, true);
				sql.append(" and s.created_time>='").append(times[0])
						.append("'");
				sql.append(" and s.created_time<='").append(times[1])
						.append("'");
			}
		}
		sql.append(")t1,xt_zzjg_yh_new t2 where t1.last_modify_id=t2.yhid_pk(+) order by t1.last_modify_time desc");
		stmt.addSqlStmt(sql.toString());
		System.out.println(sql.toString());
		stmt.setCountStmt("select count(1) from(" + sql.toString() + ")");
		return stmt;
	}

	public TRSConnection getTrsConnection()
	{
		TRSConnection trscon = null;
		try {
			logger.debug("创建链接TRS服务器对象");
			trscon = new TRSConnection();
			logger.debug("开始链接TRS服务器");
			trscon.connect(SEARCH_HOST, SEARCH_PORT, SEARCH_USER,
					SEARCH_PASSWORD, "T10");
			logger.debug("链接TRS服务器成功");

		} catch (TRSException e) {
			logger.debug("链接TRS服务器失败" + e.toString());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return trscon;
	}

	/**
	 * 
	 * queryTrsDataBase(查询TRS服务带结果基本) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程
	 * – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param queryStr
	 *            查询字符
	 * @param search_db
	 *            查询库范围以“,”号分隔
	 * @param trs_search_column
	 *            查询的字符串范围
	 * @return
	 * @throws TxnException
	 *             TRSResultSet
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public TRSResultSet queryTrsDataBase(TRSConnection trscon,
			TRSResultSet trsrs, String queryStr, String search_db,
			String trs_search_column) throws TxnException
	{
		try {
			if (null == trscon) {
				trscon = this.getTrsConnection();
			}
			StringBuffer sbStr = new StringBuffer();
			// 准备查询的词语
			queryStr = queryStr.trim();
			queryStr = preQueryString(queryStr);
			// 准备检索用字符串
			String[] trs_search_column_arry = trs_search_column.split(";");
			if (trs_search_column_arry.length > 1) {// 多表时默认只查询大字段
				sbStr.append("内容=(").append(queryStr).append(")");
			} else {
				String[] tmp = trs_search_column_arry[0].split(":");
				if (tmp.length == 2) {
					if (tmp[1].equals("all")) {
						sbStr.append("内容=(").append(queryStr).append(")");
					} else {
						sbStr.append(tmp[1] + "+=(").append(queryStr)
								.append(")");
					}
				}
			}
			logger.debug("检索条件为： " + sbStr.toString());
			// 排序并裁减保存的结果记录数，同时自动设置"非精确命中点"模式，需要SERVER6.10.3300以上。
			// trscon.SetExtendOption("SORTPRUNE", search_num);
			// 有效排序结果记录数，即部分排序，按排序表达式排序的结果只保证前一部分记录是有序的，需要SERVER6.10.3300以上。
			// trscon.SetExtendOption("SORTVALID", search_num);
			// 2：在进行相关性排序时，用一个记录中命中词的单位向量长度，以及命中词的词频和作为记录的相关度。
			// 即当多条记录命中词的单位向量长度相等时，这些记录将再按命中词的词频和的降序排列；
			trscon.SetExtendOption("RELEVANTMODE", "2");
			// 如果值为1，则表示对排序表达式中不存在的字段，按字段无值时情况处理(记录将排在后面)；否则报错。需要SERVER6.50.4000以上。
			trscon.SetExtendOption("SORTMISCOL", "1");
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String startTime = sDateFormat.format(new java.util.Date()); // 读取服务器当前年月日时分秒
			logger.debug("当前时间为：" + startTime + "开始调用TRS服务...");
			Long start = System.currentTimeMillis(); // 开始时间用于计算耗时
			trsrs = trscon.executeSelect(search_db, sbStr.toString(), false);
			logger.debug("成功返回检索结果,检索结果数量为:" + trsrs.getRecordCount());
			Long end = System.currentTimeMillis();
			String consumeTime = String.valueOf(((end - start) / 1000f));
			logger.debug("TRS检索共耗时：" + consumeTime + "秒！");
			return trsrs;
		} catch (TRSException ex) {
			// 输出错误信息
			DataBus dbError = new DataBus();
			dbError.put("code", ex.getErrorCode());
			dbError.setValue("codeStr", ex.getErrorString());
			ex.printStackTrace();
			logger.error("TRS检索出现异常！");
			throw new TxnDataException("error", ex.getErrorString());
		}
	}

	public static boolean isNumeric(String str)
	{
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean lastCharIsNumeric(String str)
	{
		int chr = str.charAt(str.length() - 1);
		if (chr >= 48 && chr <= 57)
			return true;
		else
			return false;
	}

	/*
	 * 查询字段预处理
	 */
	public static String preQueryString(String queryStr)
	{
		queryStr = queryStr.trim();
		// System.out.println(queryStr);
		// queryStr = queryStr.replaceAll(" ", " OR ");不能单纯替换
		StringBuffer result = new StringBuffer();
		if (queryStr.length() < 50) {
			String[] queryPart = queryStr.split(" ");
			for (int i = 0; i < queryPart.length; i++) {
				String tmp = "";
				if (!queryPart[i].equals("")) {
//					if (lastCharIsNumeric(queryPart[i]))// 最后一位是数字就加个%
//					{
//						tmp = queryPart[i] + "%";
//						// System.out.println(tmp);
//					} else {
//						tmp = queryPart[i];
//					}
					tmp = queryPart[i];
					if (i == 0) {
						result.append("'" + tmp + "'");
					} else {
						result.append(" and '" + tmp + "'");
					}
				}
			}

		} else {
			result.append("LIKE('" + queryStr + "',1)");
		}
		// System.out.println(result.toString());
		return result.toString();
	}

	/**
	 * 
	 * queryTrsDataBase(查询TRS服务带结果带分页) TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 –
	 * 可选)
	 * 
	 * @param queryStr
	 *            查询字符
	 * @param search_db
	 *            查询库范围以“,”号分隔
	 * @param trs_search_column
	 *            查询的字符串范围
	 * @param countPerPage
	 *            每页个数
	 * @param currentPage
	 *            当前页数
	 * @return
	 * @throws TxnException
	 *             TRSResultSet
	 * @throws TRSException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public TRSResultSet queryTrsDataBase(TRSConnection trscon,
			TRSResultSet trsrs, String queryStr, String search_db,
			String trs_search_column, int countPerPage, int currentPage)
			throws TxnException, TRSException
	{
		trsrs = queryTrsDataBase(trscon, trsrs, queryStr, search_db,
				trs_search_column);
		trsrs.setPageSize(countPerPage);
		trsrs.setPage(currentPage);
		return trsrs;
	}

	public static void main(String[] args) throws TRSException
	{
		TrsShareService trsshare = new TrsShareService();
		TRSResultSet trsrs = null;
		TRSConnection trscon = trsshare.getTrsConnection();

		try {
			trsrs = trsshare.queryTrsDataBase(trscon, trsrs, "迪信通",
					"interface_12315",
					"interface_12315:name,name_hs,dom,corp_rpt,reg_no", 10, 5);
			System.out.println(trsrs.getRecordCount());
			System.out.println(trsrs.getPage());
			System.out.println(trsrs.getPageCount());
			System.out.println(trsrs.getPageSize());
			System.out.println(trsrs.getPosition());

		} catch (TxnException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭连接

			if (trscon != null) {
				trscon.close();
			}
			trscon = null;
			if (trsrs != null) {
				trsrs.close();
			}
			trsrs = null;
		}
	}
}
