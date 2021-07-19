package com.gwssi.webservice.client.collect;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.log.collectlog.dao.CollectLogVo;
import com.gwssi.webservice.client.SQLHelper;
import com.gwssi.webservice.client.TaskInfo;

public class DataHandle extends DataHandleCollect
{
	// 日志
	protected static Logger	logger		= TxnLogger.getLogger(DataHandle.class
												.getName());

	// 任务信息
	TaskInfo				taskInfo	= new TaskInfo();

	/**
	 * 将数据插入到采集库中
	 */
	public int insertData(String tableId, String collectMode, Map domMap,
			CollectLogVo collectLogVo) throws DBException
	{
		// 开始时间
		Long start = System.currentTimeMillis();
		// 插入条数
		int count = 0;
		// 根据tableId查询表
		logger.debug("开始查询采集结果对应哪张采集表...");
		Map tableMap = taskInfo.queryTable(tableId);

		if (!tableMap.isEmpty()) {
			logger.debug("查询采集结果对应哪张采集表完毕...");
			String tableName = tableMap
					.get(CollectConstants.COLLECT_TABLE_NAME).toString();// 获取对应表的名称
			collectLogVo.setCollect_table_name(tableName);
			logger.debug("采集结果对应采集表为[" + tableName + "]...");
			// 根据表ID获取表里的所有数据项
			logger.debug("开始查询采集表里的数据项...");
			List columnList = taskInfo.queryDataitemName(tableId);

			if (columnList.size() > 0) {
				logger.debug("查询采集表里的数据项完毕...");
				logger.debug("开始往采集表里插入数据...");
				Connection conn = DbUtils.getConnection("5");
				Statement st = null;
				try {
					conn.setAutoCommit(false);
					st = conn.createStatement();
					logger.debug(domMap.get(CollectConstants.CLIENT_COLLECT_PARAM_FHDM));
					// 格式化获得的返回结果
					if (null != domMap.get(CollectConstants.CLIENT_COLLECT_PARAM_FHDM)
							&& CollectConstants.CLIENT_FHDM_SUCCESS
									.equals(domMap
											.get(CollectConstants.CLIENT_COLLECT_PARAM_FHDM))) {
						collectLogVo
								.setReturn_codes(String
										.valueOf(domMap
												.get(CollectConstants.CLIENT_COLLECT_PARAM_FHDM)));
						logger.debug("采集数据返回码为成功...");
						if (CollectConstants.COLLECT_MODE_ALL
								.equals(collectMode)) {
							logger.debug("采集方式为全量，先删除表里的所有记录...");
							st.execute(SQLHelper.deleteCollectData(tableName)
									.toString());
							logger.debug("删除成功...");
						}

						Map mm = new HashMap();
						List dataList = new ArrayList();
						if (!domMap.isEmpty()) {
							if (domMap.get("data") != null
									&& !"".equals(domMap.get("data"))) {
								mm = (Map) domMap.get("data");
								if (null != mm.get("row")
										&& !"".equals(mm.get("row"))) {
									if (mm.get("row") instanceof Map) {
										if (!mm.isEmpty())
											dataList.add(mm.get("row"));
									} else {
										dataList = (List) mm.get("row");
									}
								}
							}
						}
						Map m = new HashMap();
						StringBuffer insertSql = new StringBuffer();
						StringBuffer columnSql = new StringBuffer();
						insertSql.append("INSERT INTO ");
						insertSql.append(tableName);
						insertSql.append(" (");
						for (int i = 0; i < columnList.size(); i++) {
							insertSql.append(columnList.get(i));
							columnSql.append(columnList.get(i));
							if (i < columnList.size() - 1) {
								insertSql.append(",");
								columnSql.append(",");
							} else if (i == columnList.size() - 1) {
								insertSql.append(") VALUES (");
								columnSql.append("");
							}
						}
						collectLogVo.setCollect_column_name(columnSql
								.toString());
						for (int i = 0; i < dataList.size(); i++) {
							m = (Map) dataList.get(i);
							StringBuffer sql = SQLHelper.insertCollectData(
									insertSql, columnList, m);
							st.addBatch(sql.toString());
						}

						int[] countList = st.executeBatch();
						conn.commit(); // 提交事务
						count = countList.length; // 插入条数

						Long end = System.currentTimeMillis(); // 结束时间
						logger.debug("往表" + tableName + "批量执行条" + count
								+ "Insert操作，共耗时：" + (end - start) / 1000f
								+ "秒！");
					} else {
						collectLogVo
								.setReturn_codes(String
										.valueOf(domMap
												.get(CollectConstants.CLIENT_COLLECT_PARAM_FHDM)));
						logger
								.debug("采集数据返回码为["
										+ domMap
												.get(CollectConstants.CLIENT_COLLECT_PARAM_FHDM)
										+ "],采集数据不成功...");
					}
				} catch (BatchUpdateException e) {
					int[] c = e.getUpdateCounts();
					logger.debug("错误的数据为第几条..." + c.length);
					logger.debug("SQL语句错误..." + e);
					e.printStackTrace();
				} catch (SQLException e) { // 查询数据报错
					collectLogVo
							.setReturn_codes(CollectConstants.CLIENT_FHDM_SQL_ERROR);
					logger.debug("SQL语句错误..." + e);
					e.printStackTrace();
				} finally {
					Long end = System.currentTimeMillis();
					String consumeTime = String
							.valueOf(((end - start) / 1000f));
					collectLogVo.setInsert_consume_time(consumeTime);
					try {
						if (null != st)
							st.close();
						if (null != conn)
							conn.setAutoCommit(true);
						DbUtils.freeConnection(conn);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				collectLogVo
						.setReturn_codes(CollectConstants.CLIENT_FHDM_COL_TBL_ERROR);
				logger.debug("错误： 该采集表里没有数据项...");
			}
		} else {
			collectLogVo
					.setReturn_codes(CollectConstants.CLIENT_FHDM_COL_TBL_ERROR);
			logger.debug("错误： 该采集结果未对应采集表...");
		}
		return count;
	}

	public int insertDataForQs(String tableId, String collectMode,
			List dataList, CollectLogVo collectLogVo) throws DBException
	{
		// 开始时间
		Long start = System.currentTimeMillis();
		// 插入条数
		int count = 0;
		// 根据tableId查询表
		logger.debug("开始查询采集结果对应哪张采集表...");
		Map tableMap = taskInfo.queryTable(tableId);

		if (!tableMap.isEmpty()) {
			logger.debug("查询采集结果对应哪张采集表完毕...");
			String tableName = tableMap
					.get(CollectConstants.COLLECT_TABLE_NAME).toString();// 获取对应表的名称
			collectLogVo.setCollect_table_name(tableName);
			logger.debug("采集结果对应采集表为[" + tableName + "]...");
			// 根据表ID获取表里的所有数据项
			logger.debug("开始查询采集表里的数据项...");
			List columnList = taskInfo.queryDataitemName(tableId);

			if (columnList.size() > 0) {
				logger.debug("查询采集表里的数据项完毕...");
				logger.debug("开始往采集表里插入数据...");
				Connection conn = DbUtils.getConnection("5");
				Statement st = null;
				try {
					conn.setAutoCommit(false);
					st = conn.createStatement();
					// 格式化获得的返回结果

					logger.debug("采集数据返回码为成功...");
					if (CollectConstants.COLLECT_MODE_ALL.equals(collectMode)) {
						logger.debug("采集方式为全量，先删除表里的所有记录...");
						st.execute(SQLHelper.deleteCollectData(tableName)
								.toString());
						logger.debug("删除成功...");
					}

					StringBuffer insertSql = new StringBuffer();
					StringBuffer columnSql = new StringBuffer();
					insertSql.append("INSERT INTO ");
					insertSql.append(tableName);
					insertSql.append(" (");
					for (int i = 0; i < columnList.size(); i++) {
						insertSql.append(columnList.get(i));
						columnSql.append(columnList.get(i));
						if (i < columnList.size() - 1) {
							insertSql.append(",");
							columnSql.append(",");
						} else if (i == columnList.size() - 1) {
							insertSql.append(") VALUES (");
							columnSql.append("");
						}
					}
					collectLogVo.setCollect_column_name(columnSql.toString());
					Map m = new HashMap();
					for (int i = 0; i < dataList.size(); i++) {
						m = (Map) dataList.get(i);
						StringBuffer sql = SQLHelper.insertCollectData(
								insertSql, columnList, m);
						st.addBatch(sql.toString());
					}

					int[] countList = st.executeBatch();
					conn.commit(); // 提交事务
					count = countList.length; // 插入条数

					Long end = System.currentTimeMillis(); // 结束时间
					logger.debug("往表" + tableName + "批量执行条" + count
							+ "Insert操作，共耗时：" + (end - start) / 1000f + "秒！");

				} catch (BatchUpdateException e) {
					int[] c = e.getUpdateCounts();
					logger.debug("错误的数据为第几条..." + c.length);
					logger.debug("SQL语句错误..." + e);
					e.printStackTrace();
				} catch (SQLException e) { // 查询数据报错
					collectLogVo
							.setReturn_codes(CollectConstants.CLIENT_FHDM_SQL_ERROR);
					logger.debug("SQL语句错误..." + e);
					e.printStackTrace();
				} finally {
					Long end = System.currentTimeMillis();
					String consumeTime = String
							.valueOf(((end - start) / 1000f));
					collectLogVo.setInsert_consume_time(consumeTime);
					try {
						if (null != st)
							st.close();
						if (null != conn)
							conn.setAutoCommit(true);
						DbUtils.freeConnection(conn);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				collectLogVo
						.setReturn_codes(CollectConstants.CLIENT_FHDM_COL_TBL_ERROR);
				logger.debug("错误： 该采集表里没有数据项...");
			}
		} else {
			collectLogVo
					.setReturn_codes(CollectConstants.CLIENT_FHDM_COL_TBL_ERROR);
			logger.debug("错误： 该采集结果未对应采集表...");
		}
		return count;
	}

}
