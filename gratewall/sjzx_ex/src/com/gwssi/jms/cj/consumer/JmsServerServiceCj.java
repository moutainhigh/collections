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
 * 项目名称：bjgs_exchange 类名称：ExcuteClient 类描述：客户端的执行方法 创建人：lizheng 创建时间：Apr 2, 2013
 * 3:53:30 PM 修改人：lizheng 修改时间：Apr 2, 2013 3:53:30 PM 修改备注：
 * 
 * @version
 * 
 */
public class JmsServerServiceCj
{
	// 日志
	protected static Logger	logger	= TxnLogger.getLogger(JmsServerServiceCj.class
											.getName());

	String					result	= null;

	/**
	 * 
	 * queryData 根据参数返回XML格式的数据给客户端
	 * 
	 * @param param
	 * @return String
	 * @throws IOException
	 * @throws DBException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String queryData(String xml) 
	{
		//解析xml  查询数据 并返回
		Connection conn = null;
		ResultSet rs = null;
		
		try {
			String result="";//返回dom
			
			logger.debug("传入的参数为:" + xml);
			Map params = XmlToMapUtil.dom2Map(xml);// 将参数转化成Map
			
			
			
			// 业务逻辑
			// 1.接收参数
			// 2.执行SQL查询
			// 3.封装结果集
			int startNum = Integer.parseInt(params.get(
					ShareConstants.SERVICE_OUT_PARAM_KSJLS).toString());// 开始记录数
			int endNum = Integer.parseInt(params.get(
					ShareConstants.SERVICE_OUT_PARAM_JSJLS).toString());// 结束记录数
			System.out.println("开始记录数为：" + startNum);
			System.out.println("结束记录数为：" + endNum);
			StringBuffer sql = new StringBuffer(); // 执行的sql
			sql.append("SELECT ent_name,reg_no from REG_BUS_ENT where ent_name is not null ");
			
			
			conn = DbUtils.getConnection("6"); // 初始化connection
			System.out.println("开始执行SQL...");
			String querySql = SQLHelper.getQueryNoOrderSQL(sql.toString(),
					startNum, endNum); // 获取编辑后的SQL
			System.out.println("执行的SQL为" + querySql);
			System.out.println("开始执行查询总条数的SQL...");
			String countSql = SQLHelper.getCountSQL(sql.toString()); // 获取查询总条数的SQL
			System.out.println("执行查询总条数的SQL为" + countSql);
			
				Long start = System.currentTimeMillis();
				rs = conn.createStatement().executeQuery(querySql); // 获取结果集
				Long end = System.currentTimeMillis();
				System.out.println("查询结果耗时：" + String.valueOf(((end - start) / 1000f))
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
				System.out.println("封装结果耗时：" + String.valueOf(((end1 - start1) / 1000f))
						+ "秒！");

				Long start2 = System.currentTimeMillis();
				String count = "";
				rs = conn.createStatement().executeQuery(countSql); // 获取记录总条数
				while (rs.next()) {
					count = rs.getString(ShareConstants.SERVICE_OUT_TOTALS);
				}
				Long end2 = System.currentTimeMillis();
				System.out.println("查询总条数耗时：" + String.valueOf(((end2 - start2) / 1000f))
						+ "秒！");
				return createResultXml(DataList, params, count);
			} catch (Exception e) { // 查询数据报错
				System.out.println("查询数据报错..." + e);
				e.printStackTrace();
				Map excpMap = ResultParser.createSqlErrorMap();
				return XmlToMapUtil.map2Dom(excpMap);
			} finally {
				System.out.println("执行SQL完毕...");
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
	 * createResultXml调用服务成功返回结果
	 * 
	 * @param resultMap
	 * @param params
	 * @param total
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public synchronized static String createResultXml(List resultMap,
			Map params, String total)
	{
		Map result = new HashMap();
		if (resultMap.size() < 1) {
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_NO_RESULT);// 查询成功但结果条数为0
		} else {
			result.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
					ShareConstants.SERVICE_FHDM_SUCCESS);
		}
		result.put(ShareConstants.SERVICE_OUT_PARAM_KSJLS, params
				.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS));// 开始记录数
		result.put(ShareConstants.SERVICE_OUT_PARAM_JSJLS, params
				.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS));// 结束记录数
		
		////////采集ID 
		result.put(CollectConstants.WEBSERVICE_TASK_ID, params
				.get(CollectConstants.WEBSERVICE_TASK_ID));// 任务方法ID
		
		
		result.put(ShareConstants.SERVICE_OUT_PARAM_ZTS, total);// 总条数
		Map[] maps = new HashMap[resultMap.size()];
		for (int i = 0; i < resultMap.size(); i++) {
			Map map = (Map) resultMap.get(i);
			if (map.containsKey(ShareConstants.SERVICE_OUT_RN))
				map.remove(ShareConstants.SERVICE_OUT_RN); // 将RN去掉
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
