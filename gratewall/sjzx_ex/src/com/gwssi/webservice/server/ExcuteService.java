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
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�ExcuteService ���������ӿ�ʵ�ַ��� �����ˣ�lizheng ����ʱ�䣺Mar 28, 2013
 * 4:21:15 PM �޸��ˣ�lizheng �޸�ʱ�䣺Mar 28, 2013 4:21:15 PM �޸ı�ע��
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

	// ��־
	protected static Logger	logger	= TxnLogger.getLogger(ExcuteService.class
											.getName());

	public Map query(Map params, ShareLogVo shareLogVo) throws DBException
	{
		// System.out.println("-----Excute begin params is " + params);
		// ҵ���߼�
		// 1.���ղ���
		// 2.ִ��SQL��ѯ
		// 3.��װ�����
		String serviceId = params.get("SERVICE_ID").toString();
		int startNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_KSJLS).toString());// ��ʼ��¼��
		int endNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_JSJLS).toString());// ������¼��
		logger.debug("����IDΪ��" + serviceId);
		logger.debug("��ʼ��¼��Ϊ��" + startNum);
		logger.debug("������¼��Ϊ��" + endNum);
		StringBuffer sql = new StringBuffer(); // ִ�е�sql
		if (null != params.get(ShareConstants.SERVICE_QUERY_SQL)
				&& !"".equals(params.get(ShareConstants.SERVICE_QUERY_SQL)
						.toString())) {
			sql.append(params.get(ShareConstants.SERVICE_QUERY_SQL));
		}
		// Connection conn = DbUtils.getConnection("6");// ��ʼ��connection
		DBConnectUtil dbConnectUtil = new DBConnectUtil("gwssi_gxk");
		Connection conn = dbConnectUtil.getConnection();
		ResultSet rs = null;
		logger.debug("��ʼִ��SQL,����־���κ�Ϊ��" + params.get("batch") + "...");
		String querySql = SQLHelper.getQuerySQL(serviceId, sql.toString(),
				startNum, endNum); // ��ȡ�༭���SQL
		logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ִ�е�SQLΪ" + querySql);

		logger.debug("��ʼִ�в�ѯ��������SQL,����־���κ�Ϊ��" + params.get("batch") + "...");
		String countSql = SQLHelper.getCountSQL(sql.toString()); // ��ȡ��ѯ��������SQL
		logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ִ�в�ѯ��������SQLΪ"
				+ countSql);
		try {
			Long start = System.currentTimeMillis();
			rs = conn.createStatement().executeQuery(querySql); // ��ȡ�����
			Long end = System.currentTimeMillis();
			String consume = String.valueOf(((end - start) / 1000f));
			shareLogVo.setSel_res_consume(consume);
			logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ��ѯ�����ʱ��" + consume
					+ "�룡");		

			Long start2 = System.currentTimeMillis();
			String count = "0";
			ResultSet rsCount = conn.createStatement().executeQuery(countSql); // ��ȡ��¼������
			while (rsCount.next()) {
				count = rsCount.getString(ShareConstants.SERVICE_OUT_TOTALS);
			}
			Long end2 = System.currentTimeMillis();
			String consume2 = String.valueOf(((end2 - start2) / 1000f));
			shareLogVo.setSel_count_consume(consume2);
			logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ��ѯ��������ʱ��"
					+ consume2 + "�룡");

			shareLogVo.setAll_amount(count);
			Long start1 = System.currentTimeMillis();
			Map map =ResultParser.createResultMapByRs(rs, params, count,
					shareLogVo);
			Long end1 = System.currentTimeMillis();
			logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ��װ�����ʱ��"
					+ String.valueOf(((end1 - start1) / 1000f)) + "�룡");
			
			
			return map;
		} catch (SQLException e) { // ��ѯ���ݱ���
			logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ��ѯ���ݱ���..." + e);
			e.printStackTrace();
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SQL_ERROR);
			Map excpMap = ResultParser.createSqlErrorMap();
			return excpMap;
		} finally {
			logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ִ��SQL���...");
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
		// ҵ���߼�
		// 1.���ղ���
		// 2.ִ��SQL��ѯ
		// 3.��װ�����
		String serviceId = params.get("SERVICE_ID").toString();
		int startNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_KSJLS).toString());// ��ʼ��¼��
		int endNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_JSJLS).toString());// ������¼��
		logger.debug("����IDΪ��" + serviceId);
		logger.debug("��ʼ��¼��Ϊ��" + startNum);
		logger.debug("������¼��Ϊ��" + endNum);
		StringBuffer sql = new StringBuffer(); // ִ�е�sql
		if (null != params.get(ShareConstants.SERVICE_QUERY_SQL)
				&& !"".equals(params.get(ShareConstants.SERVICE_QUERY_SQL)
						.toString())) {
			sql.append(params.get(ShareConstants.SERVICE_QUERY_SQL));
		}
		// Connection conn = DbUtils.getConnection("6");// ��ʼ��connection
		DBConnectUtil dbConnectUtil = new DBConnectUtil("gwssi_gxk");
		Connection conn = dbConnectUtil.getConnection();
		ResultSet rs = null;
		logger.debug("��ʼִ��SQL,����־���κ�Ϊ��" + params.get("batch") + "...");
		String querySql = SQLHelper.getQuerySQL(serviceId, sql.toString(),
				startNum, endNum); // ��ȡ�༭���SQL
		logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ִ�е�SQLΪ" + querySql);

		logger.debug("��ʼִ�в�ѯ��������SQL,����־���κ�Ϊ��" + params.get("batch") + "...");
		String countSql = SQLHelper.getCountSQL(sql.toString()); // ��ȡ��ѯ��������SQL
		logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ִ�в�ѯ��������SQLΪ"
				+ countSql);
		try {
			Long start = System.currentTimeMillis();
			rs = conn.createStatement().executeQuery(querySql); // ��ȡ�����
			Long end = System.currentTimeMillis();
			String consume = String.valueOf(((end - start) / 1000f));
			shareLogVo.setSel_res_consume(consume);
			logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ��ѯ�����ʱ��" + consume
					+ "�룡");

			Long start1 = System.currentTimeMillis();
			ResultSetMetaData md = rs.getMetaData(); // ��ȡ��ѯ���ֶ�
			int columnCount = md.getColumnCount(); // ��ȡÿ�е�������
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
			logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ��װ�����ʱ��"
					+ String.valueOf(((end1 - start1) / 1000f)) + "�룡");

			Long start2 = System.currentTimeMillis();
			String count = "0";
			rs = conn.createStatement().executeQuery(countSql); // ��ȡ��¼������
			while (rs.next()) {
				count = rs.getString(ShareConstants.SERVICE_OUT_TOTALS);
			}
			Long end2 = System.currentTimeMillis();
			String consume2 = String.valueOf(((end2 - start2) / 1000f));
			shareLogVo.setSel_count_consume(consume2);
			logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ��ѯ��������ʱ��"
					+ consume2 + "�룡");

			shareLogVo.setAll_amount(count);
			return ResultParser.createResultMap(DataList, params, count,
					shareLogVo);
		} catch (SQLException e) { // ��ѯ���ݱ���
			logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ��ѯ���ݱ���..." + e);
			e.printStackTrace();
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SQL_ERROR);
			Map excpMap = ResultParser.createSqlErrorMap();
			return excpMap;
		} finally {
			logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ִ��SQL���...");
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
		// ҵ���߼�
		// 1.���ղ���
		// 2.ִ��SQL��ѯ
		// 3.��װ�����
		String serviceId = params.get("SERVICE_ID").toString();
		int startNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_KSJLS).toString());// ��ʼ��¼��
		int endNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_JSJLS).toString());// ������¼��
		logger.debug("����IDΪ��" + serviceId);
		logger.debug("��ʼ��¼��Ϊ��" + startNum);
		logger.debug("������¼��Ϊ��" + endNum);
		StringBuffer sql = new StringBuffer(); // ִ�е�sql
		if (null != params.get(ShareConstants.SERVICE_QUERY_SQL)
				&& !"".equals(params.get(ShareConstants.SERVICE_QUERY_SQL)
						.toString())) {
			sql.append(params.get(ShareConstants.SERVICE_QUERY_SQL));
		}
		// Connection conn = DbUtils.getConnection("6"); // ��ʼ��connection

		DBConnectUtil dbConnectUtil = new DBConnectUtil("gwssi_gxk");
		Connection conn = dbConnectUtil.getConnection();

		ResultSet rs = null;
		logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ��ʼִ��SQL...");
		String querySql = SQLHelper.getQuerySQL(serviceId, sql.toString(),
				startNum, endNum); // ��ȡ�༭���SQL
		logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ִ�е�SQLΪ" + querySql);
		logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ��ʼִ�в�ѯ��������SQL...");
		String countSql = SQLHelper.getCountSQL(sql.toString()); // ��ȡ��ѯ��������SQL
		logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ִ�в�ѯ��������SQLΪ"
				+ countSql);
		try {
			Long start = System.currentTimeMillis();
			rs = conn.createStatement().executeQuery(querySql); // ��ȡ�����
			Long end = System.currentTimeMillis();
			String consume = String.valueOf(((end - start) / 1000f));
			shareLogVo.setSel_res_consume(consume);
			logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ��ѯ�����ʱ��" + consume
					+ "�룡");

			Long start1 = System.currentTimeMillis();
			ResultSetMetaData md = rs.getMetaData(); // ��ȡ��ѯ���ֶ�
			int columnCount = md.getColumnCount(); // ��ȡÿ�е�������
			List DataList = new ArrayList();
			Map rowData;
			while (rs.next()) {
				rowData = new HashMap(columnCount);
				for (int i = 1; i <= columnCount; i++) {
					// ���������
					if (null != rs.getObject(i))
						rowData.put(md.getColumnName(i), rs.getObject(i));
					else
						rowData.put(md.getColumnName(i), "");
				}
				DataList.add(rowData);
			}
			Long end1 = System.currentTimeMillis();
			logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ��װ�����ʱ��"
					+ String.valueOf(((end1 - start1) / 1000f)) + "�룡");

			Long start2 = System.currentTimeMillis();
			String count = "0";
			rs = conn.createStatement().executeQuery(countSql); // ��ȡ��¼������
			while (rs.next()) {
				count = rs.getString(ShareConstants.SERVICE_OUT_TOTALS);
			}
			Long end2 = System.currentTimeMillis();
			String consume2 = String.valueOf(((end2 - start2) / 1000f));
			shareLogVo.setSel_count_consume(consume2);
			logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ��ѯ��������ʱ��"
					+ consume2 + "�룡");

			shareLogVo.setAll_amount(count);
			return ResultParser.createResultXml(DataList, params, count,
					shareLogVo);
		} catch (SQLException e) { // ��ѯ���ݱ���
			logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ��ѯ���ݱ���..." + e);
			e.printStackTrace();
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SQL_ERROR);
			Map excpMap = ResultParser.createSqlErrorMap();
			return XmlToMapUtil.map2Dom(excpMap);
		} finally {
			logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ִ��SQL���...");
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
		// ҵ���߼�
		// 1.���ղ���
		// 2.ִ��SQL��ѯ
		// 3.��װ�����
		String serviceId = params.get("SERVICE_ID").toString();
		int startNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_KSJLS).toString());// ��ʼ��¼��
		int endNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_JSJLS).toString());// ������¼��
		logger.debug("����IDΪ��" + serviceId);
		logger.debug("��ʼ��¼��Ϊ��" + startNum);
		logger.debug("������¼��Ϊ��" + endNum);
		StringBuffer sql = new StringBuffer(); // ִ�е�sql
		if (null != params.get(ShareConstants.SERVICE_QUERY_SQL)
				&& !"".equals(params.get(ShareConstants.SERVICE_QUERY_SQL)
						.toString())) {
			sql.append(params.get(ShareConstants.SERVICE_QUERY_SQL));
		}

		DBConnectUtil dbConnectUtil = new DBConnectUtil("gwssi_gxk");
		Connection conn = dbConnectUtil.getConnection();

		ResultSet rs = null, rsCount = null;
		logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ��ʼִ��SQL...");
		String querySql = SQLHelper.getQuerySQL(serviceId, sql.toString(),
				startNum, endNum); // ��ȡ�༭���SQL
		logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ִ�е�SQLΪ" + querySql);
		logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ��ʼִ�в�ѯ��������SQL...");
		String countSql = SQLHelper.getCountSQL(sql.toString()); // ��ȡ��ѯ��������SQL
		logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ִ�в�ѯ��������SQLΪ"
				+ countSql);
		try {

			// ִ�в�ѯ����ȡ�����
			Long start = System.currentTimeMillis();
			rs = conn.createStatement().executeQuery(querySql); // ��ȡ�����
			Long end = System.currentTimeMillis();
			String consume = String.valueOf(((end - start) / 1000f));
			shareLogVo.setSel_res_consume(consume);
			logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ��ѯ�����ʱ��" + consume
					+ "�룡");

			// ִ������ȡ�ܼ�¼��
			Long start2 = System.currentTimeMillis();
			String count = "0";
			rsCount = conn.createStatement().executeQuery(countSql); // ��ȡ��¼������
			while (rsCount.next()) {
				count = rsCount.getString(ShareConstants.SERVICE_OUT_TOTALS);
			}
			Long end2 = System.currentTimeMillis();
			String consume2 = String.valueOf(((end2 - start2) / 1000f));
			shareLogVo.setSel_count_consume(consume2);
			logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ��ѯ��������ʱ��"
					+ consume2 + "�룡");
			shareLogVo.setAll_amount(count);

			// �����װ
			Long start1 = System.currentTimeMillis();
			String resultStr = ResultParser.createResultXmlStr(rs, params,
					count, shareLogVo);
			Long end1 = System.currentTimeMillis();
			logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ��װ�����ʱ��"
					+ String.valueOf(((end1 - start1) / 1000f)) + "�룡");

			return resultStr;
		} catch (SQLException e) { // ��ѯ���ݱ���
			logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ��ѯ���ݱ���..." + e);
			e.printStackTrace();
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SQL_ERROR);
			Map excpMap = ResultParser.createSqlErrorMap();
			return XmlToMapUtil.map2Dom(excpMap);
		} finally {
			logger.debug("��־���κ�Ϊ��" + params.get("batch") + " ִ��SQL���...");
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
		// System.out.println("�Ƿ��ǲ���:"+is_test);
		String queryStr = params.get("queryStr").toString();// ��ѯ�ַ���
		String search_db = params.get("search_db").toString();// ��ѯ�Ŀ�
		String search_column = params.get("search_column").toString();// ��ѯ�ֶ�
		String show_column = params.get("show_column").toString();// չʾ�ֶ�
		// String trs_service_id = params.get("trs_service_id").toString();//
		// ��������ȡģ���ļ�
		String trs_template = params.get("trs_template").toString();// ģ���ļ�
		String countPerPage = (null != params.get("rows")) ? params.get("rows")
				.toString() : "10";// ÿҳ����
		String currentPage = (null != params.get("nPage")) ? params
				.get("nPage").toString() : "1";// ��ǰҳ��
		int curPage = Integer.parseInt(currentPage);
		int rows = Integer.parseInt(countPerPage);
		// ���ÿҳ��������2000����෵��2000
		if (rows > 2000) {
			rows = 2000;
		}
		int IstartIndex = rows * (curPage - 1) + 1;
		int IendIndex = rows * curPage;
		logger.debug("��ʼ����:" + IstartIndex + "---" + IendIndex);
		String startIndex = String.valueOf(IstartIndex);
		String endIndex = String.valueOf(IendIndex);

		shareLogVo.setRecord_start(startIndex); // ��־��¼��ʼ��¼��
		shareLogVo.setRecord_end(endIndex); // ��־��¼������¼��

		boolean oneTable = false;// �Ƿ񵥱�
		boolean useTemplate = false;// �Ƿ�ʹ��ģ��

		if (search_db.indexOf(",") < 0) {
			oneTable = true;
		}
		/*
		 * TxnTrsShareService TrsShareService = new TxnTrsShareService();
		 * Map<String, String> templateMap = new HashMap(); // ���ݷ���ID�����Ƿ��ж�Ӧģ��
		 * try { templateMap = TrsShareService.getOhterColumn(trs_service_id); }
		 * catch (FileNotFoundException e2) { e2.printStackTrace(); } catch
		 * (IOException e2) { e2.printStackTrace(); }
		 */
		if (!(trs_template == null || trs_template.equals(""))) {
			useTemplate = true;
		}

		Map columnMap = new HashMap();
		if (!oneTable) {
			// ��֯Ҫ��ѯ��Щ��Ͷ�Ӧ���ֶ� keyΪ�� valueΪ�ֶ�
			String columnArr[] = show_column.split(";");
			for (int i = 0; i < columnArr.length; i++) {
				String tmp[] = columnArr[i].split(":");
				columnMap.put(tmp[0], tmp[1]);
			}
		}
		logger.debug("׼������TRS������");
		TrsShareService trsshare = new TrsShareService();
		TRSResultSet trsrs = null;
		TRSConnection trscon = trsshare.getTrsConnection();
		try {
			queryStr = queryStr.trim();
			long begin = System.currentTimeMillis();
			// ����trs���񣬲����ؽ��
			trsrs = trsshare.queryTrsDataBase(trscon, trsrs, queryStr,
					search_db, search_column, rows, curPage);

			Map result = new HashMap();
			List DataList = new ArrayList();
			if (null != trsrs) {
				long totalSize = trsrs.getRecordCount();
				// �������޽����ֱ�ӷ���
				if (totalSize == 0) {
					result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
							ShareConstants.SERVICE_FHDM_NO_RESULT);// ��ѯ�ɹ����������Ϊ0
					shareLogVo
							.setReturn_codes(ShareConstants.SERVICE_FHDM_NO_RESULT);
					shareLogVo.setRecord_amount("0"); // ���η��ʵ�������
					return XmlToMapUtil.map2Dom(result);
				}
				if (totalSize > 0) {
					// ���Ϊ�������
					if (oneTable) {
						String tmp[] = show_column.split(";")[0].split(":");
						String[] column = tmp[1].trim().split(",");
						// ���ݷ�ҳ ѭ��ȡ�����
						for (long i = (curPage - 1) * rows; i < curPage * rows; i++) {
							long t = i + 1;
							if (t > totalSize) {
								break;
							}
							trsrs.moveTo(0, i);
							Map rowData = new HashMap();
							// ȡ�ֶ���Ϣ
							for (int j = 0; j < column.length; j++) {
								String content = trsrs.getString(column[j]);
								if (StringUtils.isNotBlank(content)) {
									TxnSysSearchConfig searchUtil = new TxnSysSearchConfig();
									// �ڽ���жԼ����ʽ��б��
									String tmp2 = searchUtil.addHighLighting(
											"red", content, queryStr, false);
									rowData.put(column[j], tmp2);
								}
							}
							DataList.add(rowData);
						}
					} else {// ������ÿ������ı�����С�����������ֶ����ڱ��
						for (long i = (curPage - 1) * rows; i < curPage * rows; i++) {
							long t = i + 1;
							if (t > totalSize) {
								break;
							}
							trsrs.moveTo(0, i);
							Map rowData = new HashMap();
							String tableName = trsrs.getString("����");
							// ��ȡ���������ֶ�
							tableName = "trs_" + tableName.toLowerCase();
							String columns = (String) columnMap.get(tableName);
							String[] column = columns.trim().split(",");
							// ȡ�ֶ���Ϣ
							for (int j = 0; j < column.length; j++) {
								String content = trsrs.getString(column[j]);
								if (StringUtils.isNotBlank(content)) {
									TxnSysSearchConfig searchUtil = new TxnSysSearchConfig();
									// �ڽ���жԼ����ʽ��б��
									String tmp2 = searchUtil.addHighLighting(
											"red", content, queryStr, false);
									rowData.put(column[j], tmp2);
								}
							}
							DataList.add(rowData);
						}
					}
				}
				// ��װ���ؽ��XML����
				result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
						ShareConstants.SERVICE_FHDM_SUCCESS);
				shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SUCCESS);
				shareLogVo.setRecord_amount(String.valueOf(DataList.size()));// ���η��ʵ�������
				// ���������ʱ
				String consumeTime = String.valueOf(((System
						.currentTimeMillis() - begin) / 1000f));
				// ���������ģ��
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
					// --------��֤���� ���� ����������-------------
					CheckService checkService = new CheckService();
					Map dateRuleMap = checkService.checkRules(
							shareLogVo.getService_id(), DataList.size());
					// ���ͨ����֤���������ؽ��
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
										.toString());// ��ѯ�ɹ����������Ϊ0
						shareLogVo.setReturn_codes(dateRuleMap.get(
								ShareConstants.SERVICE_OUT_PARAM_FHDM)
								.toString());
						shareLogVo.setRecord_amount("0"); // ���η��ʵ�������
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

					// --------��֤���� ���� ����������-------------
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
										.toString());// ��ѯ�ɹ����������Ϊ0
						shareLogVo.setReturn_codes(dateRuleMap.get(
								ShareConstants.SERVICE_OUT_PARAM_FHDM)
								.toString());

						shareLogVo.setRecord_amount("0"); // ���η��ʵ�������
						return XmlToMapUtil.map2Dom(result);
					}
				}
			} else {
				result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
						ShareConstants.SERVICE_FHDM_NO_RESULT);// ��ѯ�ɹ����������Ϊ0
				shareLogVo
						.setReturn_codes(ShareConstants.SERVICE_FHDM_NO_RESULT);
				shareLogVo.setRecord_amount("0"); // ���η��ʵ�������
				return XmlToMapUtil.map2Dom(result);
			}

		} catch (TRSException ex) {
			// ���������Ϣ
			DataBus dbError = new DataBus();
			dbError.put("code", ex.getErrorCode());
			dbError.setValue("codeStr", ex.getErrorString());
			ex.printStackTrace();
			throw new TxnDataException("error", ex.getErrorString());
		} catch(Exception e){
			e.printStackTrace();
			throw new TxnDataException("error", e.getMessage());
		}finally {
			// �رս����
			if (trsrs != null)
				trsrs.close();
			// �ر�����
			if (trscon != null)
				trscon.close();
		}
	}
}
