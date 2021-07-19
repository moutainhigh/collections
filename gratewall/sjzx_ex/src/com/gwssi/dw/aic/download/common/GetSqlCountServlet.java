package com.gwssi.dw.aic.download.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;

/**
 * 数据下载
 * @author BarryWei
 * @data   2008-09-09 
 */
public class GetSqlCountServlet extends HttpServlet
{
	private static final long	serialVersionUID	= -5044594293065339308L;

	// 通过doPost方法生成文件
	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		String sql = request.getParameter("testsql");
//		System.out.println("countSql before:" + sql);
//		try {
//			sql = java.net.URLDecoder.decode(sql, "UTF-8");
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
//		System.out.println("countSql After:" + sql);
		sql = "select count(1) as totalRecord from (" + sql +") e";
		String errorMsg = "true";
		int totalRecord = 0;
		DBOperation operation = DBOperationFactory.createTimeOutOperation();
		try {
			Map map = operation.selectOne(sql);
			String strTotalRecord = (map.get("TOTALRECORD")!=null ? map.get("TOTALRECORD") : "0").toString();
			totalRecord = new Integer(strTotalRecord).intValue();
		} catch (DBException e1) {
			errorMsg = "false";
			e1.printStackTrace();
		}

		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		
		StringBuffer bf = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		bf.append("<results>");
		bf.append("<sql>" + errorMsg + "</sql>");
		bf.append("<totalRecord>" + totalRecord + "</totalRecord>");
		bf.append("</results>");
		
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println(bf.toString());
	}
}
