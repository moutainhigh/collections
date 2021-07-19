package com.gwssi.webservice.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.component.logger.TxnLogger;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.search.txn.TxnSysSearchConfig;
import cn.gwssi.template.freemarker.FreemarkerUtil;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.ShareConstants;
import com.gwssi.common.database.DBConnectUtil;
import com.gwssi.common.util.XmlToMapUtil;
import com.gwssi.log.sharelog.dao.ShareLogVo;
import com.gwssi.share.trs.dao.TrsShareService;
import com.trs.client.TRSConnection;
import com.trs.client.TRSException;
import com.trs.client.TRSResultSet;

/**
 * 
 * 
 * 项目名称：bjgs_exchange 类名称：ExcuteService 类描述：接口实现方法 创建人：lizheng 创建时间：Mar 28, 2013
 * 4:21:15 PM 修改人：lizheng 修改时间：Mar 28, 2013 4:21:15 PM 修改备注：
 * 
 * @version
 * 
 */
public class ExcuteService extends AbsGeneralService
{

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

	public ExcuteService()
	{
	}

	// 日志
	protected static Logger	logger	= TxnLogger.getLogger(ExcuteService.class
											.getName());

	public Map query(Map params, ShareLogVo shareLogVo) throws DBException
	{
		// System.out.println("-----Excute begin params is " + params);
		// 业务逻辑
		// 1.接收参数
		// 2.执行SQL查询
		// 3.封装结果集
		String serviceId = params.get("SERVICE_ID").toString();
		int startNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_KSJLS).toString());// 开始记录数
		int endNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_JSJLS).toString());// 结束记录数
		logger.debug("服务ID为：" + serviceId);
		logger.debug("开始记录数为：" + startNum);
		logger.debug("结束记录数为：" + endNum);
		StringBuffer sql = new StringBuffer(); // 执行的sql
		if (null != params.get(ShareConstants.SERVICE_QUERY_SQL)
				&& !"".equals(params.get(ShareConstants.SERVICE_QUERY_SQL)
						.toString())) {
			sql.append(params.get(ShareConstants.SERVICE_QUERY_SQL));
		}
		// Connection conn = DbUtils.getConnection("6");// 初始化connection
		DBConnectUtil dbConnectUtil = new DBConnectUtil("gwssi_gxk");
		Connection conn = dbConnectUtil.getConnection();
		ResultSet rs = null;
		logger.debug("开始执行SQL,其日志批次号为：" + params.get("batch") + "...");
		String querySql = SQLHelper.getQuerySQL(serviceId, sql.toString(),
				startNum, endNum); // 获取编辑后的SQL
		logger.debug("日志批次号为：" + params.get("batch") + " 执行的SQL为" + querySql);

		logger.debug("开始执行查询总条数的SQL,其日志批次号为：" + params.get("batch") + "...");
		String countSql = SQLHelper.getCountSQL(sql.toString()); // 获取查询总条数的SQL
		logger.debug("日志批次号为：" + params.get("batch") + " 执行查询总条数的SQL为"
				+ countSql);
		try {
			Long start = System.currentTimeMillis();
			rs = conn.createStatement().executeQuery(querySql); // 获取结果集
			Long end = System.currentTimeMillis();
			String consume = String.valueOf(((end - start) / 1000f));
			shareLogVo.setSel_res_consume(consume);
			logger.debug("日志批次号为：" + params.get("batch") + " 查询结果耗时：" + consume
					+ "秒！");		

			Long start2 = System.currentTimeMillis();
			String count = "0";
			ResultSet rsCount = conn.createStatement().executeQuery(countSql); // 获取记录总条数
			while (rsCount.next()) {
				count = rsCount.getString(ShareConstants.SERVICE_OUT_TOTALS);
			}
			Long end2 = System.currentTimeMillis();
			String consume2 = String.valueOf(((end2 - start2) / 1000f));
			shareLogVo.setSel_count_consume(consume2);
			logger.debug("日志批次号为：" + params.get("batch") + " 查询总条数耗时："
					+ consume2 + "秒！");

			shareLogVo.setAll_amount(count);
			Long start1 = System.currentTimeMillis();
			Map map =ResultParser.createResultMapByRs(rs, params, count,
					shareLogVo);
			Long end1 = System.currentTimeMillis();
			logger.debug("日志批次号为：" + params.get("batch") + " 封装结果耗时："
					+ String.valueOf(((end1 - start1) / 1000f)) + "秒！");
			
			
			return map;
		} catch (SQLException e) { // 查询数据报错
			logger.debug("日志批次号为：" + params.get("batch") + " 查询数据报错..." + e);
			e.printStackTrace();
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SQL_ERROR);
			Map excpMap = ResultParser.createSqlErrorMap();
			return excpMap;
		} finally {
			logger.debug("日志批次号为：" + params.get("batch") + " 执行SQL完毕...");
			dbConnectUtil.closeConnection(conn, null, rs);
			// try {
			// if (null != rs)
			// rs.close();
			// if (null != conn)
			// conn.setAutoCommit(true);
			// // DbUtils.freeConnection(conn);
			// dbConnectUtil.closeConnection(conn, null, rs);
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
	}
	
	public Map queryOld(Map params, ShareLogVo shareLogVo) throws DBException
	{
		// System.out.println("-----Excute begin params is " + params);
		// 业务逻辑
		// 1.接收参数
		// 2.执行SQL查询
		// 3.封装结果集
		String serviceId = params.get("SERVICE_ID").toString();
		int startNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_KSJLS).toString());// 开始记录数
		int endNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_JSJLS).toString());// 结束记录数
		logger.debug("服务ID为：" + serviceId);
		logger.debug("开始记录数为：" + startNum);
		logger.debug("结束记录数为：" + endNum);
		StringBuffer sql = new StringBuffer(); // 执行的sql
		if (null != params.get(ShareConstants.SERVICE_QUERY_SQL)
				&& !"".equals(params.get(ShareConstants.SERVICE_QUERY_SQL)
						.toString())) {
			sql.append(params.get(ShareConstants.SERVICE_QUERY_SQL));
		}
		// Connection conn = DbUtils.getConnection("6");// 初始化connection
		DBConnectUtil dbConnectUtil = new DBConnectUtil("gwssi_gxk");
		Connection conn = dbConnectUtil.getConnection();
		ResultSet rs = null;
		logger.debug("开始执行SQL,其日志批次号为：" + params.get("batch") + "...");
		String querySql = SQLHelper.getQuerySQL(serviceId, sql.toString(),
				startNum, endNum); // 获取编辑后的SQL
		logger.debug("日志批次号为：" + params.get("batch") + " 执行的SQL为" + querySql);

		logger.debug("开始执行查询总条数的SQL,其日志批次号为：" + params.get("batch") + "...");
		String countSql = SQLHelper.getCountSQL(sql.toString()); // 获取查询总条数的SQL
		logger.debug("日志批次号为：" + params.get("batch") + " 执行查询总条数的SQL为"
				+ countSql);
		try {
			Long start = System.currentTimeMillis();
			rs = conn.createStatement().executeQuery(querySql); // 获取结果集
			Long end = System.currentTimeMillis();
			String consume = String.valueOf(((end - start) / 1000f));
			shareLogVo.setSel_res_consume(consume);
			logger.debug("日志批次号为：" + params.get("batch") + " 查询结果耗时：" + consume
					+ "秒！");

			Long start1 = System.currentTimeMillis();
			ResultSetMetaData md = rs.getMetaData(); // 获取查询的字段
			int columnCount = md.getColumnCount(); // 获取每行的总列数
			List DataList = new ArrayList();
			Map rowData;
			while (rs.next()) {
				rowData = new HashMap(columnCount);
				for (int i = 1; i <= columnCount; i++) {
					if (null != rs.getObject(i))
						rowData.put(md.getColumnName(i), rs.getObject(i));
					else
						rowData.put(md.getColumnName(i), "");
				}
				DataList.add(rowData);
			}
			Long end1 = System.currentTimeMillis();
			logger.debug("日志批次号为：" + params.get("batch") + " 封装结果耗时："
					+ String.valueOf(((end1 - start1) / 1000f)) + "秒！");

			Long start2 = System.currentTimeMillis();
			String count = "0";
			rs = conn.createStatement().executeQuery(countSql); // 获取记录总条数
			while (rs.next()) {
				count = rs.getString(ShareConstants.SERVICE_OUT_TOTALS);
			}
			Long end2 = System.currentTimeMillis();
			String consume2 = String.valueOf(((end2 - start2) / 1000f));
			shareLogVo.setSel_count_consume(consume2);
			logger.debug("日志批次号为：" + params.get("batch") + " 查询总条数耗时："
					+ consume2 + "秒！");

			shareLogVo.setAll_amount(count);
			return ResultParser.createResultMap(DataList, params, count,
					shareLogVo);
		} catch (SQLException e) { // 查询数据报错
			logger.debug("日志批次号为：" + params.get("batch") + " 查询数据报错..." + e);
			e.printStackTrace();
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SQL_ERROR);
			Map excpMap = ResultParser.createSqlErrorMap();
			return excpMap;
		} finally {
			logger.debug("日志批次号为：" + params.get("batch") + " 执行SQL完毕...");
			dbConnectUtil.closeConnection(conn, null, rs);
			// try {
			// if (null != rs)
			// rs.close();
			// if (null != conn)
			// conn.setAutoCommit(true);
			// // DbUtils.freeConnection(conn);
			// dbConnectUtil.closeConnection(conn, null, rs);
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
	}
	public String queryDataOld(Map params, ShareLogVo shareLogVo)
			throws DBException
	{
		System.out.println("params is " + params);
		// 业务逻辑
		// 1.接收参数
		// 2.执行SQL查询
		// 3.封装结果集
		String serviceId = params.get("SERVICE_ID").toString();
		int startNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_KSJLS).toString());// 开始记录数
		int endNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_JSJLS).toString());// 结束记录数
		logger.debug("服务ID为：" + serviceId);
		logger.debug("开始记录数为：" + startNum);
		logger.debug("结束记录数为：" + endNum);
		StringBuffer sql = new StringBuffer(); // 执行的sql
		if (null != params.get(ShareConstants.SERVICE_QUERY_SQL)
				&& !"".equals(params.get(ShareConstants.SERVICE_QUERY_SQL)
						.toString())) {
			sql.append(params.get(ShareConstants.SERVICE_QUERY_SQL));
		}
		// Connection conn = DbUtils.getConnection("6"); // 初始化connection

		DBConnectUtil dbConnectUtil = new DBConnectUtil("gwssi_gxk");
		Connection conn = dbConnectUtil.getConnection();

		ResultSet rs = null;
		logger.debug("日志批次号为：" + params.get("batch") + " 开始执行SQL...");
		String querySql = SQLHelper.getQuerySQL(serviceId, sql.toString(),
				startNum, endNum); // 获取编辑后的SQL
		logger.debug("日志批次号为：" + params.get("batch") + " 执行的SQL为" + querySql);
		logger.debug("日志批次号为：" + params.get("batch") + " 开始执行查询总条数的SQL...");
		String countSql = SQLHelper.getCountSQL(sql.toString()); // 获取查询总条数的SQL
		logger.debug("日志批次号为：" + params.get("batch") + " 执行查询总条数的SQL为"
				+ countSql);
		try {
			Long start = System.currentTimeMillis();
			rs = conn.createStatement().executeQuery(querySql); // 获取结果集
			Long end = System.currentTimeMillis();
			String consume = String.valueOf(((end - start) / 1000f));
			shareLogVo.setSel_res_consume(consume);
			logger.debug("日志批次号为：" + params.get("batch") + " 查询结果耗时：" + consume
					+ "秒！");

			Long start1 = System.currentTimeMillis();
			ResultSetMetaData md = rs.getMetaData(); // 获取查询的字段
			int columnCount = md.getColumnCount(); // 获取每行的总列数
			List DataList = new ArrayList();
			Map rowData;
			while (rs.next()) {
				rowData = new HashMap(columnCount);
				for (int i = 1; i <= columnCount; i++) {
					// 组合行数据
					if (null != rs.getObject(i))
						rowData.put(md.getColumnName(i), rs.getObject(i));
					else
						rowData.put(md.getColumnName(i), "");
				}
				DataList.add(rowData);
			}
			Long end1 = System.currentTimeMillis();
			logger.debug("日志批次号为：" + params.get("batch") + " 封装结果耗时："
					+ String.valueOf(((end1 - start1) / 1000f)) + "秒！");

			Long start2 = System.currentTimeMillis();
			String count = "0";
			rs = conn.createStatement().executeQuery(countSql); // 获取记录总条数
			while (rs.next()) {
				count = rs.getString(ShareConstants.SERVICE_OUT_TOTALS);
			}
			Long end2 = System.currentTimeMillis();
			String consume2 = String.valueOf(((end2 - start2) / 1000f));
			shareLogVo.setSel_count_consume(consume2);
			logger.debug("日志批次号为：" + params.get("batch") + " 查询总条数耗时："
					+ consume2 + "秒！");

			shareLogVo.setAll_amount(count);
			return ResultParser.createResultXml(DataList, params, count,
					shareLogVo);
		} catch (SQLException e) { // 查询数据报错
			logger.debug("日志批次号为：" + params.get("batch") + " 查询数据报错..." + e);
			e.printStackTrace();
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SQL_ERROR);
			Map excpMap = ResultParser.createSqlErrorMap();
			return XmlToMapUtil.map2Dom(excpMap);
		} finally {
			logger.debug("日志批次号为：" + params.get("batch") + " 执行SQL完毕...");
			dbConnectUtil.closeConnection(conn, null, rs);
			// try {
			// if (null != rs)
			// rs.close();
			// if (null != conn)
			// conn.setAutoCommit(true);
			// //DbUtils.freeConnection(conn);
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
	}

	public String queryData(Map params, ShareLogVo shareLogVo)
			throws DBException
	{
		System.out.println("params is " + params);
		// 业务逻辑
		// 1.接收参数
		// 2.执行SQL查询
		// 3.封装结果集
		String serviceId = params.get("SERVICE_ID").toString();
		int startNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_KSJLS).toString());// 开始记录数
		int endNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_JSJLS).toString());// 结束记录数
		logger.debug("服务ID为：" + serviceId);
		logger.debug("开始记录数为：" + startNum);
		logger.debug("结束记录数为：" + endNum);
		StringBuffer sql = new StringBuffer(); // 执行的sql
		if (null != params.get(ShareConstants.SERVICE_QUERY_SQL)
				&& !"".equals(params.get(ShareConstants.SERVICE_QUERY_SQL)
						.toString())) {
			sql.append(params.get(ShareConstants.SERVICE_QUERY_SQL));
		}

		DBConnectUtil dbConnectUtil = new DBConnectUtil("gwssi_gxk");
		Connection conn = dbConnectUtil.getConnection();

		ResultSet rs = null, rsCount = null;
		logger.debug("日志批次号为：" + params.get("batch") + " 开始执行SQL...");
		String querySql = SQLHelper.getQuerySQL(serviceId, sql.toString(),
				startNum, endNum); // 获取编辑后的SQL
		logger.debug("日志批次号为：" + params.get("batch") + " 执行的SQL为" + querySql);
		logger.debug("日志批次号为：" + params.get("batch") + " 开始执行查询总条数的SQL...");
		String countSql = SQLHelper.getCountSQL(sql.toString()); // 获取查询总条数的SQL
		logger.debug("日志批次号为：" + params.get("batch") + " 执行查询总条数的SQL为"
				+ countSql);
		try {

			// 执行查询语句获取结果集
			Long start = System.currentTimeMillis();
			rs = conn.createStatement().executeQuery(querySql); // 获取结果集
			Long end = System.currentTimeMillis();
			String consume = String.valueOf(((end - start) / 1000f));
			shareLogVo.setSel_res_consume(consume);
			logger.debug("日志批次号为：" + params.get("batch") + " 查询结果耗时：" + consume
					+ "秒！");

			// 执行语句获取总记录数
			Long start2 = System.currentTimeMillis();
			String count = "0";
			rsCount = conn.createStatement().executeQuery(countSql); // 获取记录总条数
			while (rsCount.next()) {
				count = rsCount.getString(ShareConstants.SERVICE_OUT_TOTALS);
			}
			Long end2 = System.currentTimeMillis();
			String consume2 = String.valueOf(((end2 - start2) / 1000f));
			shareLogVo.setSel_count_consume(consume2);
			logger.debug("日志批次号为：" + params.get("batch") + " 查询总条数耗时："
					+ consume2 + "秒！");
			shareLogVo.setAll_amount(count);

			// 结果封装
			Long start1 = System.currentTimeMillis();
			String resultStr = ResultParser.createResultXmlStr(rs, params,
					count, shareLogVo);
			Long end1 = System.currentTimeMillis();
			logger.debug("日志批次号为：" + params.get("batch") + " 封装结果耗时："
					+ String.valueOf(((end1 - start1) / 1000f)) + "秒！");

			return resultStr;
		} catch (SQLException e) { // 查询数据报错
			logger.debug("日志批次号为：" + params.get("batch") + " 查询数据报错..." + e);
			e.printStackTrace();
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SQL_ERROR);
			Map excpMap = ResultParser.createSqlErrorMap();
			return XmlToMapUtil.map2Dom(excpMap);
		} finally {
			logger.debug("日志批次号为：" + params.get("batch") + " 执行SQL完毕...");
			dbConnectUtil.closeConnection(conn, null, rs);
			// try {
			// if (null != rs)
			// rs.close();
			// if (null != conn)
			// conn.setAutoCommit(true);
			// //DbUtils.freeConnection(conn);
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String queryTrsData(Map params, ShareLogVo shareLogVo)
			throws TxnException
	{
		String is_test = null == params.get("USER_TYPE") ? "" : params.get(
				"USER_TYPE").toString();
		// System.out.println("是否是测试:"+is_test);
		String queryStr = params.get("queryStr").toString();// 查询字符串
		String search_db = params.get("search_db").toString();// 查询的库
		String search_column = params.get("search_column").toString();// 查询字段
		String show_column = params.get("show_column").toString();// 展示字段
		// String trs_service_id = params.get("trs_service_id").toString();//
		// 主键用来取模板文件
		String trs_template = params.get("trs_template").toString();// 模板文件
		String countPerPage = (null != params.get("rows")) ? params.get("rows")
				.toString() : "10";// 每页个数
		String currentPage = (null != params.get("nPage")) ? params
				.get("nPage").toString() : "1";// 当前页数
		int curPage = Integer.parseInt(currentPage);
		int rows = Integer.parseInt(countPerPage);
		// 如果每页的条数超2000，最多返回2000
		if (rows > 2000) {
			rows = 2000;
		}
		int IstartIndex = rows * (curPage - 1) + 1;
		int IendIndex = rows * curPage;
		logger.debug("起始条数:" + IstartIndex + "---" + IendIndex);
		String startIndex = String.valueOf(IstartIndex);
		String endIndex = String.valueOf(IendIndex);

		shareLogVo.setRecord_start(startIndex); // 日志记录开始记录数
		shareLogVo.setRecord_end(endIndex); // 日志记录结束记录数

		boolean oneTable = false;// 是否单表
		boolean useTemplate = false;// 是否使用模板

		if (search_db.indexOf(",") < 0) {
			oneTable = true;
		}
		/*
		 * TxnTrsShareService TrsShareService = new TxnTrsShareService();
		 * Map<String, String> templateMap = new HashMap(); // 根据服务ID，看是否有对应模板
		 * try { templateMap = TrsShareService.getOhterColumn(trs_service_id); }
		 * catch (FileNotFoundException e2) { e2.printStackTrace(); } catch
		 * (IOException e2) { e2.printStackTrace(); }
		 */
		if (!(trs_template == null || trs_template.equals(""))) {
			useTemplate = true;
		}

		Map columnMap = new HashMap();
		if (!oneTable) {
			// 组织要查询哪些库和对应的字段 key为库 value为字段
			String columnArr[] = show_column.split(";");
			for (int i = 0; i < columnArr.length; i++) {
				String tmp[] = columnArr[i].split(":");
				columnMap.put(tmp[0], tmp[1]);
			}
		}
		logger.debug("准备连接TRS服务器");
		TrsShareService trsshare = new TrsShareService();
		TRSResultSet trsrs = null;
		TRSConnection trscon = trsshare.getTrsConnection();
		try {
			queryStr = queryStr.trim();
			long begin = System.currentTimeMillis();
			// 访问trs服务，并返回结果
			trsrs = trsshare.queryTrsDataBase(trscon, trsrs, queryStr,
					search_db, search_column, rows, curPage);

			Map result = new HashMap();
			List DataList = new ArrayList();
			if (null != trsrs) {
				long totalSize = trsrs.getRecordCount();
				// 当检索无结果，直接返回
				if (totalSize == 0) {
					result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
							ShareConstants.SERVICE_FHDM_NO_RESULT);// 查询成功但结果条数为0
					shareLogVo
							.setReturn_codes(ShareConstants.SERVICE_FHDM_NO_RESULT);
					shareLogVo.setRecord_amount("0"); // 本次访问的数据量
					return XmlToMapUtil.map2Dom(result);
				}
				if (totalSize > 0) {
					// 如果为单库检索
					if (oneTable) {
						String tmp[] = show_column.split(";")[0].split(":");
						String[] column = tmp[1].trim().split(",");
						// 根据分页 循环取结果集
						for (long i = (curPage - 1) * rows; i < curPage * rows; i++) {
							long t = i + 1;
							if (t > totalSize) {
								break;
							}
							trsrs.moveTo(0, i);
							Map rowData = new HashMap();
							// 取字段信息
							for (int j = 0; j < column.length; j++) {
								String content = trsrs.getString(column[j]);
								if (StringUtils.isNotBlank(content)) {
									TxnSysSearchConfig searchUtil = new TxnSysSearchConfig();
									// 在结果中对检索词进行标红
									String tmp2 = searchUtil.addHighLighting(
											"red", content, queryStr, false);
									rowData.put(column[j], tmp2);
								}
							}
							DataList.add(rowData);
						}
					} else {// 多表情况每个参与的表必须有“表名”这个字段用于辨别
						for (long i = (curPage - 1) * rows; i < curPage * rows; i++) {
							long t = i + 1;
							if (t > totalSize) {
								break;
							}
							trsrs.moveTo(0, i);
							Map rowData = new HashMap();
							String tableName = trsrs.getString("表名");
							// 获取这个表配的字段
							tableName = "trs_" + tableName.toLowerCase();
							String columns = (String) columnMap.get(tableName);
							String[] column = columns.trim().split(",");
							// 取字段信息
							for (int j = 0; j < column.length; j++) {
								String content = trsrs.getString(column[j]);
								if (StringUtils.isNotBlank(content)) {
									TxnSysSearchConfig searchUtil = new TxnSysSearchConfig();
									// 在结果中对检索词进行标红
									String tmp2 = searchUtil.addHighLighting(
											"red", content, queryStr, false);
									rowData.put(column[j], tmp2);
								}
							}
							DataList.add(rowData);
						}
					}
				}
				// 组装返回结果XML部分
				result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
						ShareConstants.SERVICE_FHDM_SUCCESS);
				shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SUCCESS);
				shareLogVo.setRecord_amount(String.valueOf(DataList.size()));// 本次访问的数据量
				// 计算检索耗时
				String consumeTime = String.valueOf(((System
						.currentTimeMillis() - begin) / 1000f));
				// 如果配置有模板
				if (useTemplate && !is_test.equals("TEST")) {
					String templateStr = trs_template;
					Map resultMap = new HashMap();
					resultMap.put("results", DataList);
					resultMap.put("ResultSize", totalSize);
					resultMap.put("StartIndex", startIndex);
					resultMap.put("EndIndex", endIndex);
					resultMap.put("UsedTime", consumeTime);
					resultMap.put("CurPage", currentPage);
					if (totalSize % rows == 0) {
						resultMap.put("PageCount", totalSize / rows);
					} else {
						resultMap.put("PageCount", (totalSize / rows) + 1);
					}
					resultMap.put("Rows", rows);
					resultMap.put("Query", queryStr);
					FreemarkerUtil freeUtil = new FreemarkerUtil();
					// --------验证当日 次数 条数的限制-------------
					CheckService checkService = new CheckService();
					Map dateRuleMap = checkService.checkRules(
							shareLogVo.getService_id(), DataList.size());
					// 如果通过验证，正常返回结果
					if (dateRuleMap.get(ShareConstants.SERVICE_CAN_BE_USED)
							.equals("Y")) {
						String resultT = freeUtil.exportTemplateString(
								templateStr, resultMap);
						return resultT;
					} else {
						result.put(
								ShareConstants.SERVICE_OUT_PARAM_FHDM,
								dateRuleMap.get(
										ShareConstants.SERVICE_OUT_PARAM_FHDM)
										.toString());// 查询成功但结果条数为0
						shareLogVo.setReturn_codes(dateRuleMap.get(
								ShareConstants.SERVICE_OUT_PARAM_FHDM)
								.toString());
						shareLogVo.setRecord_amount("0"); // 本次访问的数据量
						return XmlToMapUtil.map2Dom(result);
					}
				} else {
					Map[] maps = new HashMap[DataList.size()];
					for (int i = 0; i < DataList.size(); i++) {
						Map map = (Map) DataList.get(i);
						if (map.get(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP) != null) {
							map.put(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP,
									map.get(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP)
											.toString());
						}
						maps[i] = map;
					}

					// --------验证当日 次数 条数的限制-------------
					CheckService checkService = new CheckService();
					Map dateRuleMap = checkService.checkRules(
							shareLogVo.getService_id(), DataList.size());
					if (dateRuleMap.get(ShareConstants.SERVICE_CAN_BE_USED)
							.equals("Y")) {
						result.put("ResultSize", totalSize);
						result.put("StartIndex", startIndex);
						result.put("EndIndex", endIndex);
						result.put("UsedTime", consumeTime);
						result.put("CurPage", currentPage);
						if (totalSize % rows == 0) {
							result.put("PageCount", totalSize / rows);
						} else {
							result.put("PageCount", (totalSize / rows) + 1);
						}
						result.put("CollId", "13");
						result.put("Rows", rows);
						result.put("Query", queryStr);
						result.put(ShareConstants.SERVICE_OUT_PARAM_ARRAY, maps);
						return XmlToMapUtil.map2Dom(result);
					} else {
						result.put(
								ShareConstants.SERVICE_OUT_PARAM_FHDM,
								dateRuleMap.get(
										ShareConstants.SERVICE_OUT_PARAM_FHDM)
										.toString());// 查询成功但结果条数为0
						shareLogVo.setReturn_codes(dateRuleMap.get(
								ShareConstants.SERVICE_OUT_PARAM_FHDM)
								.toString());

						shareLogVo.setRecord_amount("0"); // 本次访问的数据量
						return XmlToMapUtil.map2Dom(result);
					}
				}
			} else {
				result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
						ShareConstants.SERVICE_FHDM_NO_RESULT);// 查询成功但结果条数为0
				shareLogVo
						.setReturn_codes(ShareConstants.SERVICE_FHDM_NO_RESULT);
				shareLogVo.setRecord_amount("0"); // 本次访问的数据量
				return XmlToMapUtil.map2Dom(result);
			}

		} catch (TRSException ex) {
			// 输出错误信息
			DataBus dbError = new DataBus();
			dbError.put("code", ex.getErrorCode());
			dbError.setValue("codeStr", ex.getErrorString());
			ex.printStackTrace();
			throw new TxnDataException("error", ex.getErrorString());
		} catch(Exception e){
			e.printStackTrace();
			throw new TxnDataException("error", e.getMessage());
		}finally {
			// 关闭结果集
			if (trsrs != null)
				trsrs.close();
			// 关闭连接
			if (trscon != null)
				trscon.close();
		}
	}
}
