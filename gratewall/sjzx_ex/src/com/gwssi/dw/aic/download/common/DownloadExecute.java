package com.gwssi.dw.aic.download.common;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;

import jxl.read.biff.BiffException;

public class DownloadExecute extends HttpServlet
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5121646867335531649L;

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		String sql = request.getParameter("sql");
		String tableName = request.getParameter("tableName");
		
		DBOperation operation = DBOperationFactory.createTimeOutOperation();
		
		String querySql = "select t.object_name from user_objects t where " +
			"t.object_name = '" + tableName.toUpperCase() + "_LS' and rownum <= 1";
		System.out.println("querySql:" + querySql);
		List list = new ArrayList();
		long totalCount = 0;
		boolean hasSuccess = false;
		try {
			list = operation.select(querySql);
			if (list.size() > 0){
				operation.execute("drop table " + tableName + "_LS" , false);
			}
			operation.execute(sql, false);
			totalCount = operation.count("select * from " + tableName + "_LS");
			hasSuccess = true;
		} catch (Exception e1) {
			hasSuccess = false;
			e1.printStackTrace();
		}
		
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		StringBuffer bf = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		bf.append("<results>");
		bf.append("<hasSuccess>" + hasSuccess + "</hasSuccess>");
		bf.append("<totalCount>" + totalCount + "</totalCount>");
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
