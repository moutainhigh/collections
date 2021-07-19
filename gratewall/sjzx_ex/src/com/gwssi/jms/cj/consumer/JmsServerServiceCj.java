package com.gwssi.jms.cj.consumer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.constant.ShareConstants;
import com.gwssi.common.util.XmlToMapUtil;
import com.gwssi.webservice.server.ResultParser;
import com.gwssi.webservice.server.SQLHelper;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�ExcuteClient ���������ͻ��˵�ִ�з��� �����ˣ�lizheng ����ʱ�䣺Apr 2, 2013
 * 3:53:30 PM �޸��ˣ�lizheng �޸�ʱ�䣺Apr 2, 2013 3:53:30 PM �޸ı�ע��
 * 
 * @version
 * 
 */
public class JmsServerServiceCj
{
	// ��־
	protected static Logger	logger	= TxnLogger.getLogger(JmsServerServiceCj.class
											.getName());

	String					result	= null;

	/**
	 * 
	 * queryData ���ݲ�������XML��ʽ�����ݸ��ͻ���
	 * 
	 * @param param
	 * @return String
	 * @throws IOException
	 * @throws DBException
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public String queryData(String xml) 
	{
		//����xml  ��ѯ���� ������
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			String result="";//����dom
			
			logger.debug("����Ĳ���Ϊ:" + xml);
			Map params = XmlToMapUtil.dom2Map(xml);// ������ת����Map
			
			
			
			// ҵ���߼�
			// 1.���ղ���
			// 2.ִ��SQL��ѯ
			// 3.��װ�����
			int startNum = Integer.parseInt(params.get(
					ShareConstants.SERVICE_OUT_PARAM_KSJLS).toString());// ��ʼ��¼��
			int endNum = Integer.parseInt(params.get(
					ShareConstants.SERVICE_OUT_PARAM_JSJLS).toString());// ������¼��
			System.out.println("��ʼ��¼��Ϊ��" + startNum);
			System.out.println("������¼��Ϊ��" + endNum);
			StringBuffer sql = new StringBuffer(); // ִ�е�sql
			sql.append("SELECT ent_name,reg_no from REG_BUS_ENT where ent_name is not null ");
			
			
			conn = DbUtils.getConnection("6"); // ��ʼ��connection
			System.out.println("��ʼִ��SQL...");
			String querySql = SQLHelper.getQueryNoOrderSQL(sql.toString(),
					startNum, endNum); // ��ȡ�༭���SQL
			System.out.println("ִ�е�SQLΪ" + querySql);
			System.out.println("��ʼִ�в�ѯ��������SQL...");
			String countSql = SQLHelper.getCountSQL(sql.toString()); // ��ȡ��ѯ��������SQL
			System.out.println("ִ�в�ѯ��������SQLΪ" + countSql);
			
				Long start = System.currentTimeMillis();
				rs = conn.createStatement().executeQuery(querySql); // ��ȡ�����
				Long end = System.currentTimeMillis();
				System.out.println("��ѯ�����ʱ��" + String.valueOf(((end - start) / 1000f))
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
				System.out.println("��װ�����ʱ��" + String.valueOf(((end1 - start1) / 1000f))
						+ "�룡");

				Long start2 = System.currentTimeMillis();
				String count = "";
				rs = conn.createStatement().executeQuery(countSql); // ��ȡ��¼������
				while (rs.next()) {
					count = rs.getString(ShareConstants.SERVICE_OUT_TOTALS);
				}
				Long end2 = System.currentTimeMillis();
				System.out.println("��ѯ��������ʱ��" + String.valueOf(((end2 - start2) / 1000f))
						+ "�룡");
				return createResultXml(DataList, params, count);
			} catch (Exception e) { // ��ѯ���ݱ���
				System.out.println("��ѯ���ݱ���..." + e);
				e.printStackTrace();
				Map excpMap = ResultParser.createSqlErrorMap();
				return XmlToMapUtil.map2Dom(excpMap);
			} finally {
				System.out.println("ִ��SQL���...");
				try {
					if (null != rs)
						rs.close();
					if (null != conn)
						conn.setAutoCommit(true);
					DbUtils.freeConnection(conn);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}

	/**
	 * 
	 * createResultXml���÷���ɹ����ؽ��
	 * 
	 * @param resultMap
	 * @param params
	 * @param total
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static String createResultXml(List resultMap,
			Map params, String total)
	{
		Map result = new HashMap();
		if (resultMap.size() < 1) {
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_NO_RESULT);// ��ѯ�ɹ����������Ϊ0
		} else {
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_SUCCESS);
		}
		result.put(ShareConstants.SERVICE_OUT_PARAM_KSJLS, params
				.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS));// ��ʼ��¼��
		result.put(ShareConstants.SERVICE_OUT_PARAM_JSJLS, params
				.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS));// ������¼��
		
		////////�ɼ�ID 
		result.put(CollectConstants.WEBSERVICE_TASK_ID, params
				.get(CollectConstants.WEBSERVICE_TASK_ID));// ���񷽷�ID
		
		
		result.put(ShareConstants.SERVICE_OUT_PARAM_ZTS, total);// ������
		Map[] maps = new HashMap[resultMap.size()];
		for (int i = 0; i < resultMap.size(); i++) {
			Map map = (Map) resultMap.get(i);
			if (map.containsKey(ShareConstants.SERVICE_OUT_RN))
				map.remove(ShareConstants.SERVICE_OUT_RN); // ��RNȥ��
			if (map.get(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP) != null) {
				map.put(ShareConstants.SERVICE_OUT_ETL_TIMESTAMP, map.get(
						ShareConstants.SERVICE_OUT_ETL_TIMESTAMP).toString());
			}
			maps[i] = map;
		}
		result.put(ShareConstants.SERVICE_OUT_PARAM_ARRAY, maps);
		return XmlToMapUtil.map2Dom(result);
	}


}
