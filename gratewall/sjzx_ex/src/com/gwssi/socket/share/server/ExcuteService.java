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
 * 项目名称：bjgs_exchange 类名称：ExcuteService 类描述：接口实现方法 创建人：lizheng 创建时间：Mar 28, 2013
 * 4:21:15 PM 修改人：lizheng 修改时间：Mar 28, 2013 4:21:15 PM 修改备注：
 * 
 * @version
 * 
 */
public class ExcuteService extends AbsGeneralService
{

	public ExcuteService()
	{
	}

	// 日志
	protected static Logger	logger	= TxnLogger.getLogger(ExcuteService.class
											.getName());

	

	public String queryData(Map params, ShareLogVo shareLogVo)
			throws DBException
	{
		// 业务逻辑
		// 1.接收参数
		// 2.执行SQL查询
		// 3.封装结果集
		int startNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_KSJLS).toString());// 开始记录数
		int endNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_JSJLS).toString());// 结束记录数
		logger.debug("开始记录数为：" + startNum);
		logger.debug("结束记录数为：" + endNum);
		StringBuffer sql = new StringBuffer(); // 执行的sql
		if (null != params.get(ShareConstants.SERVICE_QUERY_SQL)
				&& !"".equals(params.get(ShareConstants.SERVICE_QUERY_SQL)
						.toString())) {
			sql.append(params.get(ShareConstants.SERVICE_QUERY_SQL));
		}
		Connection conn = DbUtils.getConnection("6"); // 初始化connection
		ResultSet rs = null;
		logger.debug("开始执行SQL...");
		String querySql = SQLHelper.getQueryNoOrderSQL(sql.toString(),
				startNum, endNum); // 获取编辑后的SQL
		logger.debug("执行的SQL为" + querySql);
		logger.debug("开始执行查询总条数的SQL...");
		String countSql = SQLHelper.getCountSQL(sql.toString()); // 获取查询总条数的SQL
		logger.debug("执行查询总条数的SQL为" + countSql);
		try {
			Long start = System.currentTimeMillis();
			rs = conn.createStatement().executeQuery(querySql); // 获取结果集
			Long end = System.currentTimeMillis();
			logger.debug("查询结果耗时：" + String.valueOf(((end - start) / 1000f))
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
			logger.debug("封装结果耗时：" + String.valueOf(((end1 - start1) / 1000f))
					+ "秒！");

			Long start2 = System.currentTimeMillis();
			String count = "";
			rs = conn.createStatement().executeQuery(countSql); // 获取记录总条数
			while (rs.next()) {
				count = rs.getString(ShareConstants.SERVICE_OUT_TOTALS);
			}
			Long end2 = System.currentTimeMillis();
			logger.debug("查询总条数耗时：" + String.valueOf(((end2 - start2) / 1000f))
					+ "秒！");
			return ResultParser.createResultXml(DataList, params, count,
					shareLogVo);
		} catch (SQLException e) { // 查询数据报错
			logger.debug("查询数据报错..." + e);
			e.printStackTrace();
			shareLogVo.setReturn_codes(ShareConstants.SERVICE_FHDM_SQL_ERROR);
			Map excpMap = ResultParser.createSqlErrorMap();
			return XmlToMapUtil.map2Dom(excpMap);
		} finally {
			logger.debug("执行SQL完毕...");
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
