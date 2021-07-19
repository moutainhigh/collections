package com.gwssi.socket.share.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;
import com.gwssi.common.constant.ShareConstants;
import com.gwssi.common.util.XmlToMapUtil;
import com.gwssi.log.sharelog.dao.ShareLogVo;

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

	public ExcuteService()
	{
	}

	// ��־
	protected static Logger	logger	= TxnLogger.getLogger(ExcuteService.class
											.getName());

	

	public String queryData(Map params, ShareLogVo shareLogVo)
			throws DBException
	{
		// ҵ���߼�
		// 1.���ղ���
		// 2.ִ��SQL��ѯ
		// 3.��װ�����
		int startNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_KSJLS).toString());// ��ʼ��¼��
		int endNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_JSJLS).toString());// ������¼��
		logger.debug("��ʼ��¼��Ϊ��" + startNum);
		logger.debug("������¼��Ϊ��" + endNum);
		StringBuffer sql = new StringBuffer(); // ִ�е�sql
		if (null != params.get(ShareConstants.SERVICE_QUERY_SQL)
				&& !"".equals(params.get(ShareConstants.SERVICE_QUERY_SQL)
						.toString())) {
			sql.append(params.get(ShareConstants.SERVICE_QUERY_SQL));
		}
		Connection conn = DbUtils.getConnection("6"); // ��ʼ��connection
		ResultSet rs = null;
		logger.debug("��ʼִ��SQL...");
		String querySql = SQLHelper.getQueryNoOrderSQL(sql.toString(),
				startNum, endNum); // ��ȡ�༭���SQL
		logger.debug("ִ�е�SQLΪ" + querySql);
		logger.debug("��ʼִ�в�ѯ��������SQL...");
		String countSql = SQLHelper.getCountSQL(sql.toString()); // ��ȡ��ѯ��������SQL
		logger.debug("ִ�в�ѯ��������SQLΪ" + countSql);
		try {
			Long start = System.currentTimeMillis();
			rs = conn.createStatement().executeQuery(querySql); // ��ȡ�����
			Long end = System.currentTimeMillis();
			logger.debug("��ѯ�����ʱ��" + String.valueOf(((end - start) / 1000f))
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
			logger.debug("��װ�����ʱ��" + String.valueOf(((end1 - start1) / 1000f))
					+ "�룡");

			Long start2 = System.currentTimeMillis();
			String count = "";
			rs = conn.createStatement().executeQuery(countSql); // ��ȡ��¼������
			while (rs.next()) {
				count = rs.getString(ShareConstants.SERVICE_OUT_TOTALS);
			}
			Long end2 = System.currentTimeMillis();
			logger.debug("��ѯ��������ʱ��" + String.valueOf(((end2 - start2) / 1000f))
					+ "�룡");
			return ResultParser.createResultXml(DataList, params, count,
					shareLogVo);
		} catch (SQLException e) { // ��ѯ���ݱ���
			logger.debug("��ѯ���ݱ���..." + e);
			e.printStackTrace();
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SQL_ERROR);
			Map excpMap = ResultParser.createSqlErrorMap();
			return XmlToMapUtil.map2Dom(excpMap);
		} finally {
			logger.debug("ִ��SQL���...");
			try {
				if (null != rs)
					rs.close();
				if (null != conn)
					conn.setAutoCommit(true);
				DbUtils.freeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
