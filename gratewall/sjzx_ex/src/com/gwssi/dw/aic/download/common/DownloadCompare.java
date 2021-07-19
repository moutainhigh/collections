package com.gwssi.dw.aic.download.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gwssi.common.component.exception.TxnDataException;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;

import jxl.read.biff.BiffException;

public class DownloadCompare extends HttpServlet
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String filePath = request.getParameter("filePath");
		String tableName = request.getParameter("tableName");
		String tableNo = request.getParameter("tableNo");
		
		List sqls = null;
		try {
			if (filePath.endsWith(".txt") || filePath.endsWith(".TXT")){
				sqls = genCreateTableSqlFromTxt(filePath, tableName, tableNo);
			}else{
				sqls = genCreateTableSqlFromXls(filePath, tableName, tableNo);
			}
			
			
			
			DBOperation operation = DBOperationFactory.createTimeOutOperation();
			operation.execute(sqls, false);
		} catch (BiffException e) {
			e.printStackTrace();
			return;
		} catch (DBException e){
			e.printStackTrace();
			return;
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		
	}
	
	private List genCreateTableSqlFromXls(String filePath, String tableName, String tableNo) throws BiffException, IOException, DBException{
		File file = new File(filePath);
		jxl.Workbook xls = jxl.Workbook.getWorkbook(file);
		jxl.Sheet[] sheetArray = xls.getSheets();
		
		List sqls = new ArrayList();
		// 遍历所有数据的时候, 寻找最大长度，并组织好insert语句
		int[] columnLengthArray = null;
		// sheet页的列数目
		int defaultColumnNumber = 0;
		// 字段列表字符串
		String columnString = "";

		for (int i = 0 ; i < sheetArray.length; i++){
			if (sheetArray[i].getRows() > 0){
				// 初始化columnLengthArray
				if (columnLengthArray == null){
					defaultColumnNumber = sheetArray[i].getColumns();
					columnLengthArray = new int[defaultColumnNumber];
					columnString = genColumnList(defaultColumnNumber);
					String[] columnCnArray = new String[defaultColumnNumber];
					for (int t = 0; t < columnCnArray.length; t ++){
						columnCnArray[t] = sheetArray[i].getRow(0)[t].getContents().trim();
					}
					insertColumnInfo(columnString, tableNo, columnCnArray); 
				}
				
				// 从第二行开始遍历行
				for (int j = 1; j < sheetArray[i].getRows(); j++){
					StringBuffer sbInsertSql = new StringBuffer();
					sbInsertSql.append("insert into " + tableName + "(" + columnString + ") values(");
					// 遍历列, 从第一列开始
					for (int k = 0; k < sheetArray[i].getColumns(); k ++){
						String content = sheetArray[i].getCell(k, j).getContents().replaceAll("\\s+", "");
						
						if (content.length() > columnLengthArray[k] ){
							columnLengthArray[k] = content.length();
						}
						sbInsertSql.append("'" + content.replaceAll("'", "''") + "'");
						if (k != sheetArray[i].getColumns() - 1){
							sbInsertSql.append(",");
						}
					}
					sbInsertSql.append(")");
					sqls.add(sbInsertSql.toString());
				}
			}
		}
		
		String[] columnArray = columnString.split(",");
		String createTableSql = "create table " + tableName + "(";
		for (int in = 0; in < columnArray.length; in++){
			// 排除字段长度为0的情况
			if (columnLengthArray[in] == 0){
				columnLengthArray[in] = 1;
			}
			createTableSql += columnArray[in] + " VARCHAR2(" + columnLengthArray[in] * 2 + ")";
			if (in != columnArray.length - 1){
				createTableSql += ",";
			}
		}
		createTableSql += ")";
		
		sqls.add(0, createTableSql);
		
		return sqls;
	}
	
	/**
	 * 从TXT文件生成SQL语句
	 * @param filePath
	 * @param tableName
	 * @param tableNo
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 * @throws DBException
	 */
	private List genCreateTableSqlFromTxt(String filePath, String tableName, String tableNo) throws IOException, DBException{
		File file = new File(filePath);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int[] columnLengthArray = null;
		List sqls = new ArrayList();
		
		String columnString = "";
		String line = reader.readLine();
		int defaultLength = 0;
		String regex = "(\")?\\s*,\\s*(\")?|\\t";
		int i = 0;
		while ( line != null ){
			if(!line.equals("")){
				if (defaultLength == 0){
					String[] titleArray = line.split(regex);
					defaultLength = titleArray.length ;
					columnLengthArray = new int[defaultLength];
					columnString = genColumnList(defaultLength);
					
					String[] columnCnArray = new String[defaultLength];
					for (int t = 0; t < columnCnArray.length; t ++){
						String content = titleArray[t];
//						.startsWith("\"") ? 
//								titleArray[t].substring(1) :  titleArray[t];
//						content = content.endsWith("\"") ? 
//								content.substring(0, content.length() - 1) : content;
						columnCnArray[t] = content.replaceAll("'", "''");
					}
					insertColumnInfo(columnString, tableNo, columnCnArray);
				}else{
					String[] columnArray = line.split(regex);
					StringBuffer sbInsertSql = new StringBuffer();
					sbInsertSql.append("insert into " + tableName + "(" + columnString + ") values(");
					// 遍历列, 从第一列开始
					for (int k = 0; k < columnArray.length; k ++){
						String content = columnArray[k];
//						.startsWith("\"") ? 
//								columnArray[k].substring(1) : columnArray[k];
//						
//						content = content.endsWith("\"") ? 
//								content.substring(0, content.length() - 1) : content;
//						
						if (content.length() > columnLengthArray[k] ){
							columnLengthArray[k] = content.length();
						}
						sbInsertSql.append("'" + content.replaceAll("'", "''") + "'");
						if (k != columnArray.length - 1){
							sbInsertSql.append(",");
						}
					}
					sbInsertSql.append(")");
//					System.out.println("sbInsertSql.toString():" + sbInsertSql.toString());
					sqls.add(sbInsertSql.toString());
				}
			}
			line = reader.readLine();
			i ++;
		}
		
		String[] columnArray = columnString.split(",");
		String createTableSql = "create table " + tableName + "(";
		for (int in = 0; in < columnArray.length; in++){
			// 排除字段长度为0的情况
			if (columnLengthArray[in] == 0){
				columnLengthArray[in] = 1;
			}
			createTableSql += columnArray[in] + " VARCHAR2(" + columnLengthArray[in] * 2 + ")";
			if (in != columnArray.length - 1){
				createTableSql += ",";
			}
		}
		createTableSql += ")";
		
		sqls.add(0, createTableSql);
		
		return sqls;
	}
	
	/**
	 * 将数据插入字段记录表
	 * @param columnArray
	 * @param tableNo
	 * @throws DBException 
	 */
	private void insertColumnInfo(String columns, String tableNo, String[] columnCnArray) throws DBException{
		List sqls = new ArrayList();
		String[] columnArray = columns.split(",");
		for (int i=0; i < columnArray.length; i++){
			String sql = "INSERT INTO DOWNLOAD_COLUMN " +
					"(COLUMN_NO, TABLE_NO, COLUMN_NAME, COLUMN_NAME_CN, " +
					"COLUMN_ORDER, EDIT_TYPE, EDIT_CONTENT, DEMO, COLUMN_BYNAME) " +
					"VALUES (";
			//'1', '1', 'id', '字段1', 1, '1', '32', ''
			
			sql += "'" + com.gwssi.common.util.UuidGenerator.getUUID() + "', ";
			sql += "'" + tableNo + "', ";
			sql += "'" + columnArray[i] + "', ";
			sql += "'" + columnCnArray[i].replaceAll("'", "''") + "', ";
			sql += "" + i + ", ";
			sql += "'1', '100', '', ''";
			sql += ")";
			sqls.add(sql);
		}
//		
//		for (int j=0; j < sqls.size(); j++){
//			System.out.println("插入SQL:" + sqls.get(j));
//		}
		
		DBOperation operation = DBOperationFactory.createTimeOutOperation();
		operation.execute(sqls, false);
	}
	
	/**
	 * 
	 * @return
	 */
	private String genColumnList(int columnsNumber){
		String columnPrefix = "COL";
		String time = String.valueOf(System.currentTimeMillis());
		String result = "";
		for (int i = 0; i < columnsNumber; i++){
			result += columnPrefix + time + i;
			if (i != columnsNumber - 1){
				result += ",";
			}
		}
		
		return result;
	}
}
