package com.gwssi.dw.aic.download.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.OracleConnection;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;
import cn.gwssi.common.dao.ds.ConnectFactory;
import cn.gwssi.common.dao.ds.source.DBController;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;

/**
 * 数据下载
 * 
 * @author BarryWei
 * @data 2008-09-09
 */
public class TestSqlServlet extends HttpServlet
{
	private static final long	serialVersionUID	= -3842411907654095408L;

	protected static Logger		logger				= TxnLogger
															.getLogger(TestSqlServlet.class
																	.getName());

	// 通过doPost方法生成文件
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
	{
		String sql = request.getParameter("testsql");
		String errorMsg = "true";
		DBController dbcon = null;
		ResultSet rs = null;
		if (StringUtils.isNotBlank(sql)) {
			try {
				sql = sql.replaceAll("%", "%25"); // 保证URLDecoder解码时%可以被正确识别
				// System.out.println("1. " +sql);
				sql = java.net.URLDecoder.decode(sql, "UTF-8");
				// System.out.println("2. " +sql);
				sql = sql.replaceAll("%2B", "+")
						.replaceAll("\\(\\s*\\)", "(+)").replaceAll("''", "'");
				// System.out.println("3. " +sql);
				logger.debug("服务配置sql验证----->"+sql);
				sql = "explain PLAN for "
						+ sql
						+ ((sql.indexOf("WHERE") > -1) ? " and rownum <=1"
								: " where rownum <= 1");
				System.out.println("testSql:" + sql);
				ConnectFactory cf = cn.gwssi.common.dao.ds.ConnectFactory
						.getInstance();
				dbcon = cf.getConnection("gwssi_gxk");
				OracleConnection conn = (OracleConnection) dbcon
						.getConnection();
				rs = conn.createStatement().executeQuery(sql);
			} catch (UnsupportedEncodingException e) {
				errorMsg = "false";
				e.printStackTrace();
				logger.error(e.getMessage());
			} catch (Exception e) {
				errorMsg = "false";
				e.printStackTrace();
				logger.error(e.getMessage());
			} finally {
				if (dbcon != null)
					dbcon.closeResultSet(rs);
			}
		} else {
			errorMsg = "false";
		}
		logger.debug("服务配置sql验证结果:----->"+(errorMsg.equals("false") ? "失败" : "成功"));
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		StringBuffer bf = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"GBK\"?>");
		bf.append("<results>");
		bf.append("<sql>" + errorMsg + "</sql>");
		bf.append("</results>");

		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println(bf.toString());
	}

	/**
	 * 校验表名是否存在
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
	{
		String tableName = request.getParameter("tableName");
		System.out.println("tableName:" + tableName);
		try {
			tableName = java.net.URLDecoder.decode(tableName, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		List list = new ArrayList();

		String sql = "select t.object_name from user_objects t where "
				+ "(t.object_name = '" + tableName.toUpperCase()
				+ "' or t.object_name = '" + tableName.toUpperCase()
				+ "_RS') and rownum <= 1";

		String isExist = "true";
		DBOperation operation = DBOperationFactory.createTimeOutOperation();
		try {
			list = operation.select(sql);
			if (list.size() > 0) {
				isExist = "true";
			} else {
				isExist = "false";
			}
		} catch (DBException e1) {
			isExist = "true";
			e1.printStackTrace();
		}

		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");

		StringBuffer bf = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"GBK\"?>");
		bf.append("<results>");
		bf.append("<isExist>" + isExist + "</isExist>");
		bf.append("</results>");

		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println(bf.toString());
	}

	public static void main(String[] args)
	{
		String a = "SELECT '' FROM REG_BUS_ENT";
		System.out.println(a.replaceAll("\''", "'"));
	}
}
