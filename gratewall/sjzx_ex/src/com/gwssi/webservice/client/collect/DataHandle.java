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
	// ��־
	protected static Logger	logger		= TxnLogger.getLogger(DataHandle.class
												.getName());

	// ������Ϣ
	TaskInfo				taskInfo	= new TaskInfo();

	/**
	 * �����ݲ��뵽�ɼ�����
	 */
	public int insertData(String tableId, String collectMode, Map domMap,
			CollectLogVo collectLogVo) throws DBException
	{
		// ��ʼʱ��
		Long start = System.currentTimeMillis();
		// ��������
		int count = 0;
		// ����tableId��ѯ��
		logger.debug("��ʼ��ѯ�ɼ������Ӧ���Ųɼ���...");
		Map tableMap = taskInfo.queryTable(tableId);

		if (!tableMap.isEmpty()) {
			logger.debug("��ѯ�ɼ������Ӧ���Ųɼ������...");
			String tableName = tableMap
					.get(CollectConstants.COLLECT_TABLE_NAME).toString();// ��ȡ��Ӧ�������
			collectLogVo.setCollect_table_name(tableName);
			logger.debug("�ɼ������Ӧ�ɼ���Ϊ[" + tableName + "]...");
			// ���ݱ�ID��ȡ���������������
			logger.debug("��ʼ��ѯ�ɼ������������...");
			List columnList = taskInfo.queryDataitemName(tableId);

			if (columnList.size() > 0) {
				logger.debug("��ѯ�ɼ���������������...");
				logger.debug("��ʼ���ɼ������������...");
				Connection conn = DbUtils.getConnection("5");
				Statement st = null;
				try {
					conn.setAutoCommit(false);
					st = conn.createStatement();
					logger.debug(domMap.get(CollectConstants.CLIENT_COLLECT_PARAM_FHDM));
					// ��ʽ����õķ��ؽ��
					if (null != domMap.get(CollectConstants.CLIENT_COLLECT_PARAM_FHDM)
							&& CollectConstants.CLIENT_FHDM_SUCCESS
									.equals(domMap
											.get(CollectConstants.CLIENT_COLLECT_PARAM_FHDM))) {
						collectLogVo
								.setReturn_codes(String
										.valueOf(domMap
												.get(CollectConstants.CLIENT_COLLECT_PARAM_FHDM)));
						logger.debug("�ɼ����ݷ�����Ϊ�ɹ�...");
						if (CollectConstants.COLLECT_MODE_ALL
								.equals(collectMode)) {
							logger.debug("�ɼ���ʽΪȫ������ɾ����������м�¼...");
							st.execute(SQLHelper.deleteCollectData(tableName)
									.toString());
							logger.debug("ɾ���ɹ�...");
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
						conn.commit(); // �ύ����
						count = countList.length; // ��������

						Long end = System.currentTimeMillis(); // ����ʱ��
						logger.debug("����" + tableName + "����ִ����" + count
								+ "Insert����������ʱ��" + (end - start) / 1000f
								+ "�룡");
					} else {
						collectLogVo
								.setReturn_codes(String
										.valueOf(domMap
												.get(CollectConstants.CLIENT_COLLECT_PARAM_FHDM)));
						logger
								.debug("�ɼ����ݷ�����Ϊ["
										+ domMap
												.get(CollectConstants.CLIENT_COLLECT_PARAM_FHDM)
										+ "],�ɼ����ݲ��ɹ�...");
					}
				} catch (BatchUpdateException e) {
					int[] c = e.getUpdateCounts();
					logger.debug("���������Ϊ�ڼ���..." + c.length);
					logger.debug("SQL������..." + e);
					e.printStackTrace();
				} catch (SQLException e) { // ��ѯ���ݱ���
					collectLogVo
							.setReturn_codes(CollectConstants.CLIENT_FHDM_SQL_ERROR);
					logger.debug("SQL������..." + e);
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
				logger.debug("���� �òɼ�����û��������...");
			}
		} else {
			collectLogVo
					.setReturn_codes(CollectConstants.CLIENT_FHDM_COL_TBL_ERROR);
			logger.debug("���� �òɼ����δ��Ӧ�ɼ���...");
		}
		return count;
	}

	public int insertDataForQs(String tableId, String collectMode,
			List dataList, CollectLogVo collectLogVo) throws DBException
	{
		// ��ʼʱ��
		Long start = System.currentTimeMillis();
		// ��������
		int count = 0;
		// ����tableId��ѯ��
		logger.debug("��ʼ��ѯ�ɼ������Ӧ���Ųɼ���...");
		Map tableMap = taskInfo.queryTable(tableId);

		if (!tableMap.isEmpty()) {
			logger.debug("��ѯ�ɼ������Ӧ���Ųɼ������...");
			String tableName = tableMap
					.get(CollectConstants.COLLECT_TABLE_NAME).toString();// ��ȡ��Ӧ�������
			collectLogVo.setCollect_table_name(tableName);
			logger.debug("�ɼ������Ӧ�ɼ���Ϊ[" + tableName + "]...");
			// ���ݱ�ID��ȡ���������������
			logger.debug("��ʼ��ѯ�ɼ������������...");
			List columnList = taskInfo.queryDataitemName(tableId);

			if (columnList.size() > 0) {
				logger.debug("��ѯ�ɼ���������������...");
				logger.debug("��ʼ���ɼ������������...");
				Connection conn = DbUtils.getConnection("5");
				Statement st = null;
				try {
					conn.setAutoCommit(false);
					st = conn.createStatement();
					// ��ʽ����õķ��ؽ��

					logger.debug("�ɼ����ݷ�����Ϊ�ɹ�...");
					if (CollectConstants.COLLECT_MODE_ALL.equals(collectMode)) {
						logger.debug("�ɼ���ʽΪȫ������ɾ����������м�¼...");
						st.execute(SQLHelper.deleteCollectData(tableName)
								.toString());
						logger.debug("ɾ���ɹ�...");
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
					conn.commit(); // �ύ����
					count = countList.length; // ��������

					Long end = System.currentTimeMillis(); // ����ʱ��
					logger.debug("����" + tableName + "����ִ����" + count
							+ "Insert����������ʱ��" + (end - start) / 1000f + "�룡");

				} catch (BatchUpdateException e) {
					int[] c = e.getUpdateCounts();
					logger.debug("���������Ϊ�ڼ���..." + c.length);
					logger.debug("SQL������..." + e);
					e.printStackTrace();
				} catch (SQLException e) { // ��ѯ���ݱ���
					collectLogVo
							.setReturn_codes(CollectConstants.CLIENT_FHDM_SQL_ERROR);
					logger.debug("SQL������..." + e);
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
				logger.debug("���� �òɼ�����û��������...");
			}
		} else {
			collectLogVo
					.setReturn_codes(CollectConstants.CLIENT_FHDM_COL_TBL_ERROR);
			logger.debug("���� �òɼ����δ��Ӧ�ɼ���...");
		}
		return count;
	}

}
