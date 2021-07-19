package com.gwssi.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import oracle.jdbc.OracleConnection;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.component.logger.TxnLogger;
import cn.gwssi.common.dao.ds.ConnectFactory;
import cn.gwssi.common.dao.ds.source.DBController;

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.database.DBConnectUtil;
import com.gwssi.common.database.DBPoolConnection;
import com.gwssi.common.testconnection.DatabaseResource;
import com.gwssi.log.collectlog.dao.CollectLogVo;
import com.gwssi.webservice.client.TaskInfo;
import com.gwssi.webservice.client.WsClient;
import com.gwssi.webservice.server.ServiceDAO;
import com.gwssi.webservice.server.ServiceDAOImpl;

public class AnalyCollectFile
{

	protected static Logger	logger		= TxnLogger.getLogger(AnalyCollectFile.class
												.getName());	// 日志

	TaskInfo				taskInfo	= new TaskInfo();		// 任务信息

	/**
	 * 
	 * analyExcel(解析excel文件，并把解析内容封装成sql以数组形式返回) TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 –
	 * 可选)
	 * 
	 * @param filePathAndName
	 *            :被解析的文件全路径
	 * @param tableName
	 *            ：采集表名称
	 * @param columnNames
	 *            ：采集表字段名称
	 * @param collect_mode
	 *            ：采集方式
	 * @param checkResultFilePath1
	 *            :解析文件存放全路径 void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String[] analyExcel(String filepath, String tableName,
			HashMap columnNames, String collect_mode,
			String checkResultFilePath1)
	{
		HashMap colNames = new HashMap();
		HashMap colValues = new HashMap();
		String checkResultFilePath = checkResultFilePath1;
		StringBuffer checkResult = new StringBuffer();

		List<String> sqlList = new ArrayList();

		if (CollectConstants.COLLECT_MODE_ALL.equals(collect_mode)) {
			String delSql = "delete from " + tableName;
			execDelSql(delSql);
		}

		try {
			Workbook workbook;
			workbook = Workbook.getWorkbook(new File(filepath));
			Sheet[] sheet = workbook.getSheets();
			for (int i = 0; i < sheet.length; i++) {
				if (sheet[i] != null) {
					// 清空列名和值
					colNames.clear();
					colValues.clear();
					int cols = sheet[i].getColumns();
					int rows = sheet[i].getRows();

					if (cols != 0 && rows != 0) {
						// String[] inserSql = new String[rows-1];

						for (int k = 0; k < rows; k++) {
							for (int l = 0; l < cols; l++) {
								Cell c = sheet[i].getCell(l, k);
								String cs = c.getContents();
								if (k == 0) {
									colNames.put(l, cs);
								} else {
									colValues.put(l, cs);
								}
							}

							// 组装sql
							if (k > 0 && !colNames.isEmpty()
									&& !colValues.isEmpty()) {
								StringBuffer insertCol = new StringBuffer();
								StringBuffer insertVal = new StringBuffer();
								for (int m = 0; m < cols; m++) {

									if (columnNames.containsValue(colNames
											.get(m).toString().toUpperCase())) {// 列名包含在表的列名中
										if (insertCol == null
												|| insertCol.toString().equals(
														"")) {
											insertCol.append(colNames.get(m));
											insertVal.append("'"
													+ colValues.get(m) + "'");
										} else {
											insertCol.append(","
													+ colNames.get(m));
											insertVal.append(",'"
													+ colValues.get(m) + "'");
										}
									}
								}
								// 每行需要插入的sql
								if (tableName != null && !"".equals(tableName)
										&& insertCol.toString() != null
										&& !"".equals(insertCol.toString())
										&& insertVal.toString() != null
										&& !"".equals(insertVal.toString())) {
									String insertSql = "insert into "
											+ tableName + " ("
											+ insertCol.toString()
											+ ") values("
											+ insertVal.toString() + ")";
									if (insertSql != null
											&& !"".equals(insertSql)) {
										sqlList.add(insertSql);
									}
								}

							}
						}

						checkResult.append("第" + (i + 1) + "个工作簿的校验结果为:\r\n");
						boolean flag = false;
						// 校验文件，查询不在表中的字段列名
						for (int a = 0; a < cols; a++) {
							if (!columnNames.containsValue(colNames.get(a)
									.toString().toUpperCase())) {
								checkResult.append("第" + (a + 1) + "列["
										+ colNames.get(a) + "]不是表[" + tableName
										+ "]中的字段\r\n");
								flag = true;
							}
						}

						if (!flag)
							checkResult.append("文件中的全部字段均合法!\r\n");

					} else {
						checkResult.append("第" + (i + 1) + "个工作簿的校验结果为:\r\n");
						checkResult.append("工作簿内容为空\r\n");

					}

				}
			}
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] sql = new String[sqlList.size()];
		for (int i = 0; i < sql.length; i++) {
			sql[i] = sqlList.get(i);
		}
		writeFile(checkResultFilePath, checkResult.toString());
		return sql;
	}

	/**
	 * 
	 * analyExcel_Ftp(解析Ftp类型的Excel采集文件)
	 * 
	 * @param filepath
	 * @param tableName
	 * @param columnNames
	 * @param titleType
	 *            : EN or CN
	 * @param collect_mode
	 * @param checkResultFilePath1
	 * @return String[]
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public String[] analyExcel_Ftp(String filepath, String tableName,
			HashMap columnNames, String titleType, String collect_mode,
			String checkResultFilePath1)
	{
		HashMap colNames = new HashMap();
		HashMap colValues = new HashMap();
		String checkResultFilePath = checkResultFilePath1;
		StringBuffer checkResult = new StringBuffer();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String updateDate = sDateFormat.format(new java.util.Date());
		List<String> sqlList = new ArrayList();

		if (CollectConstants.COLLECT_MODE_ALL.equals(collect_mode)) {
			String delSql = "delete from " + tableName;
			execDelSql(delSql);
		}

		try {
			Workbook workbook;
			workbook = Workbook.getWorkbook(new File(filepath));
			Sheet[] sheet = workbook.getSheets();
			for (int i = 0; i < sheet.length; i++) {
				if (sheet[i] != null) {
					// 清空列名和值
					colNames.clear();
					colValues.clear();
					int cols = sheet[i].getColumns();
					int rows = sheet[i].getRows();

					if (cols != 0 && rows != 0) {
						// String[] inserSql = new String[rows-1];

						for (int k = 0; k < rows; k++) {
							for (int l = 0; l < cols; l++) {
								Cell c = sheet[i].getCell(l, k);
								String cs = c.getContents();
								if (k == 0) {
									colNames.put(l, cs);
								} else {
									colValues.put(l, cs);
								}
							}

							// 组装sql
							if (k > 0 && !colNames.isEmpty()
									&& !colValues.isEmpty()) {
								StringBuffer insertCol = new StringBuffer();
								StringBuffer insertVal = new StringBuffer();
								for (int m = 0; m < cols; m++) {

									if (titleType.equals("EN")) {// 列名是英文名
										if (columnNames.containsValue(colNames
												.get(m).toString()
												.toUpperCase())) {// 列名包含在表的列名中
											if (insertCol == null
													|| insertCol.toString()
															.equals("")) {
												insertCol.append(colNames
														.get(m));
												insertVal.append("'"
														+ colValues.get(m)
														+ "'");
											} else {
												insertCol.append(","
														+ colNames.get(m));
												insertVal.append(",'"
														+ colValues.get(m)
														+ "'");
											}
										}

									} else if (titleType.equals("CN")) {// 列名是中文名
										if (columnNames.containsKey(colNames
												.get(m).toString())) {// 列名包含在表的列名中
											// String column_en =
											// columnNames.get();
											if (insertCol == null
													|| insertCol.toString()
															.equals("")) {
												insertCol.append(columnNames
														.get(colNames.get(m)));
												insertVal.append("'"
														+ colValues.get(m)
														+ "'");
											} else {
												insertCol
														.append(","
																+ columnNames
																		.get(colNames
																				.get(m)));
												insertVal.append(",'"
														+ colValues.get(m)
														+ "'");
											}
										}
									}

								}
								// 每行需要插入的sql
								if (tableName != null && !"".equals(tableName)
										&& insertCol.toString() != null
										&& !"".equals(insertCol.toString())
										&& insertVal.toString() != null
										&& !"".equals(insertVal.toString())) {
									String insertSql = "insert into "
											+ tableName + " ("
											+ insertCol.toString()
											+ ",UPDATE_DATE " + ") values("
											+ insertVal.toString() + ",'"
											+ updateDate + "')";
									if (insertSql != null
											&& !"".equals(insertSql)) {
										sqlList.add(insertSql);
									}
								}

							}
						}

						checkResult.append("第" + (i + 1) + "个工作簿的校验结果为:\r\n");
						boolean flag = false;
						// 校验文件，查询不在表中的字段列名
						for (int a = 0; a < cols; a++) {
							if (titleType.equals("EN")) {// 列名是英文名
								if (!columnNames.containsValue(colNames.get(a)
										.toString().toUpperCase())) {
									checkResult.append("第" + (a + 1) + "列["
											+ colNames.get(a) + "]不是表["
											+ tableName + "]中的字段\r\n");
									flag = true;
								}

							} else if (titleType.equals("CN")) {// 列名是中文名
								if (!columnNames.containsKey(colNames.get(a)
										.toString())) {
									checkResult.append("第" + (a + 1) + "列["
											+ colNames.get(a) + "]不是表["
											+ tableName + "]中的字段\r\n");
									flag = true;
								}

							}

						}

						if (!flag)
							checkResult.append("文件中的全部字段均合法!\r\n");

					} else {
						checkResult.append("第" + (i + 1) + "个工作簿的校验结果为:\r\n");
						checkResult.append("工作簿内容为空\r\n");

					}

				}
			}
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] sql = new String[sqlList.size()];
		for (int i = 0; i < sql.length; i++) {
			sql[i] = sqlList.get(i);
		}
		writeFile(checkResultFilePath, checkResult.toString());
		return sql;
	}

	/**
	 * 
	 * getDesDataItemExcel(得到采集文件的目标数据项和实际被采集的数据项)) TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 –
	 * 可选)
	 * 
	 * @param filepath
	 *            ：采集文件的路径
	 * @param columnNames
	 *            ：采集表字段信息
	 * @return String[] ：String[0]:存放采集文件的数据项；String[1]:存放实际被采集的数据项
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String[] getDesDataItemExcel(String filepath, HashMap columnNames)
	{
		String[] itemInfo = new String[2];
		StringBuffer souItem = new StringBuffer();
		StringBuffer colItem = new StringBuffer();

		try {
			Workbook workbook;
			workbook = Workbook.getWorkbook(new File(filepath));
			Sheet[] sheet = workbook.getSheets();
			for (int i = 0; i < sheet.length; i++) {
				if (sheet[i] != null) {
					int cols = sheet[i].getColumns();
					for (int j = 0; j < cols; j++) {
						Cell c = sheet[i].getCell(j, 0);
						String cs = c.getContents();
						if (cs != null) {
							if (j != (cols - 1)) {
								souItem.append(cs + ",");
								if (columnNames.containsValue(cs.toUpperCase())) {
									colItem.append(cs + ",");
								}
							} else {
								souItem.append(cs);
								if (columnNames.containsValue(cs.toUpperCase())) {
									colItem.append(cs);
								}
							}

						}
					}

				}
			}

			itemInfo[0] = souItem.toString();
			itemInfo[1] = colItem.toString();
			System.out.println("采集文件的数据项==" + itemInfo[0]);
			System.out.println("实际被采集的数据项==" + itemInfo[1]);

		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return itemInfo;
	}

	/**
	 * 
	 * getDesDataItemTxt(得到采集文件的目标数据项和实际被采集的数据项) TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 –
	 * 可选)
	 * 
	 * @param filepath
	 *            ：采集文件的路径
	 * @param columnNames
	 *            ：采集表字段信息
	 * @param speator
	 *            ：文件分隔符
	 * @return String[] ：String[0]:存放采集文件的数据项；String[1]:存放实际被采集的数据项
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public String[] getDesDataItemTxt(String filepath, HashMap columnNames,
			String speator)
	{
		String[] itemInfo = new String[2];
		String[] colNames;
		String souItem = "";
		StringBuffer colItem = new StringBuffer();

		if (speator == null || "".equals(speator)) {
			speator = CollectConstants.COLLECT_FILE_SPEARTOR;
		}

		File f = new File(filepath);
		if (f.isFile() && f.exists()) {
			try {

				FileInputStream read = new FileInputStream(f);
				BufferedReader reader = new BufferedReader(new UnicodeReader(
						read, Charset.defaultCharset().name()));
				String line;

				try {
					line = reader.readLine();

					// 第一行列名的处理
					if (line != null) {
						souItem = line.toString();
						colNames = line.split(speator, -1);

						for (int i = 0; i < colNames.length; i++) {
							if (columnNames.containsValue(colNames[i]
									.toUpperCase())) {
								if (i == colNames.length - 1) {
									colItem.append(colNames[i]);
								} else {
									colItem.append(colNames[i] + ",");
								}
							}
						}

					}

					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		itemInfo[0] = souItem;
		itemInfo[1] = colItem.toString();
		System.out.println("采集文件的数据项==" + itemInfo[0]);
		System.out.println("实际被采集的数据项==" + itemInfo[1]);
		return itemInfo;
	}

	/**
	 * 
	 * analyTxt(解析txt文件，并把结果封装成sql数组返回) TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 –
	 * 可选)
	 * 
	 * @param filePathAndName
	 * @param tableName
	 * @param columnNames
	 * @param collect_mode
	 * @param speator
	 * @param checkResultFilePath1
	 * @return String[]
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String[] analyTxt(String filePathAndName, String tableName,
			HashMap columnNames, String collect_mode, String speator,
			String checkResultFilePath1)
	{
		StringBuffer checkResult = new StringBuffer();
		String[] colNames;
		String[] colValues;

		if (speator == null || "".equals(speator)) {
			speator = CollectConstants.COLLECT_FILE_SPEARTOR;
		}

		List<String> sqlList = new ArrayList();

		if (CollectConstants.COLLECT_MODE_ALL.equals(collect_mode)) {
			String delSql = "delete from " + tableName;
			execDelSql(delSql);

		}

		File f = new File(filePathAndName);
		if (f.isFile() && f.exists()) {
			try {

				FileInputStream read = new FileInputStream(f);
				BufferedReader reader = new BufferedReader(new UnicodeReader(
						read, Charset.defaultCharset().name()));
				String line;

				try {
					line = reader.readLine();

					// 第一行列名的处理
					if (line != null) {
						colNames = line.split(speator, -1);

						for (int i = 0; i < colNames.length; i++) {
							if (columnNames.containsValue(colNames[i]
									.toUpperCase())) {

							} else {
								checkResult.append("列名[" + colNames[i]
										+ "]不是采集表中的字段\r\n");
							}
						}

						// 第二行列值得处理
						while ((line = reader.readLine()) != null) {
							StringBuffer insertCol = new StringBuffer();
							StringBuffer insertVal = new StringBuffer();

							colValues = line.split(speator, -1);

							for (int i = 0; i < colValues.length; i++) {

								if (columnNames.containsValue(colNames[i]
										.toUpperCase())) {// 列名包含在采集表中
									if (insertCol == null
											|| insertCol.toString().equals("")) {
										insertCol.append(colNames[i]);
										insertVal.append("'" + colValues[i]
												+ "'");
									} else {
										insertCol.append("," + colNames[i]);
										insertVal.append(",'" + colValues[i]
												+ "'");
									}
								}
							}

							// 每行需要插入的sql
							if (tableName != null && !"".equals(tableName)
									&& insertCol.toString() != null
									&& !"".equals(insertCol.toString())
									&& insertVal.toString() != null
									&& !"".equals(insertVal.toString())) {
								String insertSql = "insert into " + tableName
										+ " (" + insertCol.toString()
										+ ") values(" + insertVal.toString()
										+ ")";
								// System.out.println(insertSql+";");
								sqlList.add(insertSql);
							}
						}

					}
					if (checkResult.toString() == null
							|| "".equals(checkResult.toString())) {
						checkResult.append("文件中的全部字段均合法!");
					}
					writeFile(checkResultFilePath1, checkResult.toString());
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		String[] sql = new String[sqlList.size()];
		for (int i = 0; i < sql.length; i++) {
			sql[i] = sqlList.get(i);
			// System.out.println("sql["+i+"]="+sql[i]);
		}

		return sql;

	}

	/**
	 * 
	 * analyTxt2(解析txt文件，并把结果封装成sql数组返回) 
	 * 
	 * @param filePathAndName
	 * @param tableName
	 * @param columnNames
	 * @param collect_mode
	 * @param speator
	 * @param checkResultFilePath1
	 * @return String[]
	 * @throws Exception
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String[] analyTxt2(String filePathAndName, String tableName,
			HashMap columnCN, String titleType, String collect_mode,
			String speator, String checkResultFilePath1) throws Exception
	{
		// System.out.println("columnCN="+columnCN);
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String updateDate = sDateFormat.format(new java.util.Date());
		StringBuffer checkResult = new StringBuffer();
		String[] colNames;
		String[] colValues;
		if (speator == null || "".equals(speator)) {
			speator = CollectConstants.COLLECT_FILE_SPEARTOR;
		}

		List<String> sqlList = new ArrayList();

		if (CollectConstants.COLLECT_MODE_ALL.equals(collect_mode)) {
			String delSql = "delete from " + tableName;
			execDelSql(delSql);

		}

		File f = new File(filePathAndName);
		if (f.isFile() && f.exists()) {
			try {
				// System.out.println("file speator="+speator);
				String filecode = codeString(filePathAndName);
				logger.info("文件编码格式为:" + filecode);
				FileInputStream read = new FileInputStream(f);
				BufferedReader reader = new BufferedReader(new UnicodeReader(
						read, filecode/* Charset.defaultCharset().name() */));
				// logger.info("默认字符集="+Charset.defaultCharset().name());

				String line;
				// String breakStr=System.getProperty("line.separator");
				try {
					long start = System.currentTimeMillis();
					line = reader.readLine();
					logger.info("标题line=" + line);
					// 第一行列名的处理
					if (line != null) {
						colNames = line.split(speator, -1);
						// checkTitleLine(colNames,keyMap,columnCN,checkResult);
						for (int i = 0; i < colNames.length; i++) {
							if (!(columnCN.containsValue(colNames[i]
									.toUpperCase()) || columnCN
									.containsKey(colNames[i].toUpperCase()))) {

								checkResult.append("列名[" + colNames[i]
										+ "]不是采集表中的字段\r\n");
							}
						}

						// 列值得处理
						line = reader.readLine();
						while (line != null && line != "") {
							// logger.info(line);
							StringBuffer insertCol = new StringBuffer();
							StringBuffer insertVal = new StringBuffer();

							colValues = line.split(speator, -1);

							for (int i = 0; i < colValues.length; i++) {

								if (columnCN.containsValue(colNames[i]
										.toUpperCase())
										|| columnCN.containsKey(colNames[i]
												.toUpperCase())) {
									// 列名包含在采集表中
									if (insertCol == null
											|| insertCol.toString().equals("")) {
										if ("EN".equals(titleType)) {
											insertCol.append(colNames[i]);
										} else {
											insertCol.append(columnCN
													.get(colNames[i]));
										}

										insertVal.append("'" + colValues[i]
												+ "'");
									} else {
										if ("EN".equals(titleType)) {
											insertCol.append("," + colNames[i]);
										} else {
											insertCol
													.append(","
															+ columnCN
																	.get(colNames[i]));
										}
										insertVal.append(",'" + colValues[i]
												+ "'");
									}
								}
							}

							// 每行需要插入的sql
							if (tableName != null && !"".equals(tableName)
									&& insertCol.toString() != null
									&& !"".equals(insertCol.toString())
									&& insertVal.toString() != null
									&& !"".equals(insertVal.toString())) {
								String insertSql = "insert into " + tableName
										+ " (" + insertCol.toString()
										+ ",UPDATE_DATE" + ") values("
										+ insertVal.toString() + ",'"
										+ updateDate + "')";
								// System.out.println(insertSql+";");
								sqlList.add(insertSql);
							}
							line = reader.readLine();
						}

					}
					long end = System.currentTimeMillis();
					String consumeTime = String
							.valueOf(((end - start) / 1000f));
					System.out.println("-----文件读取解析耗时：" + consumeTime);
					if (checkResult.toString() == null
							|| "".equals(checkResult.toString())) {
						checkResult.append("文件中的全部字段均合法!");
					}
					writeFile(checkResultFilePath1, checkResult.toString());
					logger.info("生成校验文件");
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		String[] sql = new String[sqlList.size()];
		for (int i = 0; i < sql.length; i++) {
			sql[i] = sqlList.get(i);
			// logger.debug("数据插入语句sql["+i+"]="+sql[i]);
		}

		return sql;

	}

	/**
	 * 
	 * execDelSql(执行删除语句操作)
	 * 
	 * @param sql
	 *            :待执行的sql语句 void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void execDelSql(String sql)
	{
		DBPoolConnection conn = new DBPoolConnection(
				CollectConstants.DATASOURCE_CJK);
		conn.executeDML(sql);
	}

	/**
	 * 
	 * execBatch(批量执行插入操作)
	 * 
	 * @throws DBException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public int execBatch(String[] sqls) throws DBException
	{
		int a = 0;
		int count = -1;
		Connection conn = null;
		Statement st = null;
		try {
			
			  conn = DbUtils.getConnection("5"); // 实例化con
			  conn.setAutoCommit(false); 
			  st = conn.createStatement(); 
			  for (int i = 0; i < sqls.length; i++) { 
				  a = i; 
				  st.addBatch(sqls[i]); 
			  }
			  System.out.println("准备执行批量插入语句"); long start =
			  System.currentTimeMillis(); 
			  int[] countList = st.executeBatch();
			  conn.commit(); 
			  long end = System.currentTimeMillis(); 
			  String consumeTime = String.valueOf(((end - start) / 1000f));
			  System.out.println("-------批量执行插入语句耗时"+consumeTime+"----------");
			  count = countList.length; System.out.println("插入数据："+count);


		} catch (BatchUpdateException e) {
			int[] c = e.getUpdateCounts();
			System.out.println("sss..." + (a - 100 + c.length));
			logger.debug("错误的数据为第几条..." + (a - 100 + c.length));
			logger.error("错误语句为:" + sqls[(a - 100 + c.length)]);
			System.out.println("SQL语句错误..." + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("SQL语句错误..." + e);
		} finally {
			System.out.println("eeess..." + a);
			try {
				if (null != st)
					st.close();
				if (null != conn)
					conn.setAutoCommit(true);
				DbUtils.freeConnection(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return a;
		}

	}

	public int execBatchJob(String[] sqls)
	{
		/*
		 * DBPoolConnection conn = new
		 * DBPoolConnection(ExConstant.DATA_SOURCE_COLLECT); int count =
		 * conn.executeBatch(sqls); return count;
		 */

		ConnectFactory cf = ConnectFactory.getInstance();
		System.out.println("ConnectFactory is : " + cf);

		DBController dbcon = null;
		Statement st = null;
		OracleConnection conn = null;
		int count = 0;

		try {
			dbcon = cf.getConnection(ExConstant.DATA_SOURCE_COLLECT);
			// 连接
			conn = (OracleConnection) dbcon.getConnection();
			System.out.println("conn is : " + conn);

			conn.setRemarksReporting(true);
			conn.setAutoCommit(false);
			st = conn.createStatement();

			for (int i = 0; i < sqls.length; i++) {
				st.addBatch(sqls[i]);
			}
			int[] countList = st.executeBatch();
			conn.commit();
			count = countList.length;

		} catch (TxnException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != dbcon) {
				System.out.println("closeStatement");
				dbcon.closeStatement(st);
				try {
					if (null != conn) {
						conn.close();
						System.out.println("conn.close");
					}
					dbcon.close();
					System.out.println("dbcon.close");
				} catch (TxnException e1) {
					e1.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return count;

	}

	/**
	 * 
	 * analyExcelData(读取execel采集文件，并把数据插入数据库中) TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 –
	 * 可选)
	 * 
	 * @param filePathAndName
	 *            :被解析的文件全路径
	 * @param tableName
	 *            ：采集表名称
	 * @param columnNames
	 *            ：采集表字段名称
	 * @param collect_mode
	 *            ：采集方式
	 * @param checkResultFilePath1
	 *            :解析文件存放全路径 void
	 * @throws TxnDataException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void analyExcelData(HashMap map, String filepath, String tableName,
			HashMap columnNames, String collect_mode, String checkResultFilePath)
	{

		Long start = System.currentTimeMillis();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
		System.out.println("start:" + start);
		int count = 0;
		String return_code = "";

		String[] sql = analyExcel(filepath, tableName, columnNames,
				collect_mode, checkResultFilePath);
		if (sql != null && sql.length != 0) {
			try {
				count = execBatch(sql);
				return_code = CollectConstants.CLIENT_FHDM_SUCCESS;
			} catch (Exception e) {
				return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
				// throw new TxnDataException("采集数据失败", "数据格式错误!");
			} finally {
				String endTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
				Long end = System.currentTimeMillis();
				System.out.println("end:" + end);
				System.out.println("往表" + tableName + "批量执行条" + count
						+ "Insert操作，共耗时：" + (end - start) / 1000f + "秒！");
				String total = String.valueOf(((end - start) / 1000f));
				insertLogInfo(map, startTime, endTime, total,
						String.valueOf(count), return_code);
			}
		}

	}

	/**
	 * 
	 * analyExcelData(读取execel采集文件，并把数据插入数据库中) TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 –
	 * 可选)
	 * 
	 * @param filePathAndName
	 *            :被解析的文件全路径
	 * @param tableName
	 *            ：采集表名称
	 * @param columnNames
	 *            ：采集表字段名称
	 * @param collect_mode
	 *            ：采集方式
	 * @param checkResultFilePath1
	 *            :解析文件存放全路径 void
	 * @throws TxnDataException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String analyExcelData2(HashMap map, String filepath,
			String tableName, HashMap columnNames, String collect_mode,
			String checkResultFilePath, String userName, String logFile,
			StringBuffer colTable_ColumnInfo) throws TxnDataException
	{

		Long start = System.currentTimeMillis();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
		// System.out.println("start:" + start);
		int count = 0;
		String return_code = "";
		String logId = null;
		String[] sql = analyExcel(filepath, tableName, columnNames,
				collect_mode, checkResultFilePath);
		if (sql != null && sql.length != 0) {
			try {
				count = execBatch(sql);
				if (count == -1) {
					return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
					count = 0;
				} else {
					return_code = CollectConstants.CLIENT_FHDM_SUCCESS;
				}
			} catch (Exception e) {
				return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
				throw new TxnDataException("采集数据失败", "数据格式错误!");
			} finally {
				String endTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
				Long end = System.currentTimeMillis();
				System.out.println("end:" + end);
				System.out.println("往表" + tableName + "批量执行条" + count
						+ "Insert操作，共耗时：" + (end - start) / 1000f + "秒！");
				String total = String.valueOf(((end - start) / 1000f));
				try {
					logId = insertLogInfo(map, startTime, endTime, total,
							String.valueOf(count), return_code);// 插入日志表
				} catch (Exception e) {
					return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
					throw new TxnDataException("采集日志记录失败", "采集日志记录失败!");
				} finally {
					creatLogFile(map, filepath, startTime, endTime,
							return_code, userName, logId, logFile, columnNames,
							colTable_ColumnInfo, "excel", "");// 生成日志文件

				}
			}
		}
		if (return_code.equals("BAIC0000")) {
			return_code = CollectConstants.COLLECT_STATUS_SUCCESS;// 采集成功
		} else {
			return_code = CollectConstants.COLLECT_STATUS_FAIL;// 采集失败
		}
		return return_code;

	}

	/**
	 * 
	 * analyExcelData_Ftp(读取execel采集文件，并把数据插入数据库中) TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 –
	 * 可选)
	 * 
	 * @param map
	 * @param filepath
	 * @param tableName
	 * @param columnNames
	 * @param titleType
	 *            :采集文件列名类型：CN or EN
	 * @param collect_mode
	 * @param checkResultFilePath
	 * @param userName
	 * @param logFile
	 * @param colTable_ColumnInfo
	 * @return
	 * @throws TxnDataException
	 *             String
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public String analyExcelData_Ftp(HashMap map, String filepath,
			String tableName, HashMap columnNames, String titleType,
			String collect_mode, String checkResultFilePath, String userName,
			String logFile, StringBuffer colTable_ColumnInfo)
			throws TxnDataException
	{

		Long start = System.currentTimeMillis();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
		// System.out.println("start:" + start);
		int count = 0;
		String return_code = "";
		String logId = null;
		String[] sql = analyExcel_Ftp(filepath, tableName, columnNames,
				titleType, collect_mode, checkResultFilePath);
		if (sql != null && sql.length != 0) {
			try {
				count = execBatch(sql);
				if (count == -1) {
					return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
					count = 0;
				} else {
					return_code = CollectConstants.CLIENT_FHDM_SUCCESS;
				}
			} catch (Exception e) {
				return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
				throw new TxnDataException("采集数据失败", "数据格式错误!");
			} finally {
				String endTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
				Long end = System.currentTimeMillis();
				System.out.println("end:" + end);
				System.out.println("往表" + tableName + "批量执行条" + count
						+ "Insert操作，共耗时：" + (end - start) / 1000f + "秒！");
				String total = String.valueOf(((end - start) / 1000f));
				try {
					logId = insertLogInfo(map, startTime, endTime, total,
							String.valueOf(count), return_code);// 插入日志表
				} catch (Exception e) {
					return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
					throw new TxnDataException("采集日志记录失败", "采集日志记录失败!");
				} finally {
					creatLogFile(map, filepath, startTime, endTime,
							return_code, userName, logId, logFile, columnNames,
							colTable_ColumnInfo, "excel", "");// 生成日志文件

				}
			}
		}
		if (return_code.equals("BAIC0000")) {
			return_code = CollectConstants.COLLECT_STATUS_SUCCESS;// 采集成功
		} else {
			return_code = CollectConstants.COLLECT_STATUS_FAIL;// 采集失败
		}
		return return_code;

	}

	/**
	 * 
	 * analyExcelData(读取execel采集文件，并把数据插入数据库中) TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 –
	 * 可选)
	 * 
	 * @param filePathAndName
	 *            :被解析的文件全路径
	 * @param tableName
	 *            ：采集表名称
	 * @param columnNames
	 *            ：采集表字段名称
	 * @param collect_mode
	 *            ：采集方式
	 * @param checkResultFilePath1
	 *            :解析文件存放全路径 void
	 * @throws TxnDataException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void analyExcelDataForFtp(Map map, String filepath,
			String tableName, HashMap columnNames, String collect_mode,
			String checkResultFilePath) throws TxnDataException
	{

		Long start = System.currentTimeMillis();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
		System.out.println("start:" + start);
		int count = 0;
		String return_code = "";

		String[] sql = analyExcel(filepath, tableName, columnNames,
				collect_mode, checkResultFilePath);
		if (sql != null && sql.length != 0) {
			try {
				// count = execBatch(sql);
				count = execBatchJob(sql);
				return_code = CollectConstants.CLIENT_FHDM_SUCCESS;
			} catch (Exception e) {
				return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
				throw new TxnDataException("采集数据失败", "数据格式错误!");
			} finally {
				String endTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
				Long end = System.currentTimeMillis();
				/*
				 * System.out.println("end:"+end); System.out.println("往表" +
				 * tableName + "批量执行条" + count + "Insert操作，共耗时：" + (end - start)
				 * / 1000f + "秒！");
				 */
				String total = String.valueOf(((end - start) / 1000f));
				insertLogInfoForFtp(map, startTime, endTime, total,
						String.valueOf(count), return_code);
			}
		}

	}

	/**
	 * 
	 * insertLogInfo(插入日志信息) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param context
	 * @param sTime
	 * @param eTime
	 * @param total
	 * @param count
	 *            void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String insertLogInfo(HashMap<String, String> map, String sTime,
			String eTime, String total, String count, String return_code)
	{
		CollectLogVo collectLogVo = new CollectLogVo(); // 采集日志记录

		String id = map.get("COLLECT_JOUMAL_ID");
		if (id == null || "".equals(id)) {
			id = UuidGenerator.getUUID();
		}
		collectLogVo.setCollect_task_id(map.get("COLLECT_TASK_ID"));
		collectLogVo.setTask_name(map.get("TASK_NAME"));
		collectLogVo.setService_targets_id(map.get("SERVICE_TARGETS_ID"));
		collectLogVo.setService_targets_name(map.get("SERVICE_TARGETS_NAME"));
		collectLogVo.setCollect_type(map.get("COLLECT_TYPE"));
		collectLogVo.setTask_start_time(sTime);
		collectLogVo.setTask_end_time(eTime);
		collectLogVo.setTask_consume_time(total);
		collectLogVo.setCollect_data_amount(count);
		collectLogVo.setTask_status("");
		collectLogVo.setTask_id((String) map.get("TASK_ID"));
		collectLogVo.setPatameter("");
		collectLogVo.setService_no(map.get("SERVICE_NO"));
		collectLogVo.setReturn_codes(return_code);
		collectLogVo.setCollect_table(map.get("COLLECT_TABLE"));
		collectLogVo.setCollect_table_name(map.get("COLLECT_TABLE_NAME"));
		collectLogVo.setCollect_mode(map.get("COLLECT_MODE"));
		collectLogVo.setMethod_name_cn(map.get("METHOD_NAME_CN"));
		collectLogVo.setMethod_name_en(map.get("METHOD_NAME_EN"));
		collectLogVo.setBatch_num(map.get("BATCH"));
		collectLogVo.setIs_formal(map.get("IS_FORMAL"));
		// 记录日志
		logger.debug("开始记录采集日志...");
		taskInfo.insertLog(id, collectLogVo);
		// taskInfo.insertLog(collectLogVo);
		logger.debug("记录采集日志成功...");

		return id;

	}

	/**
	 * 生成日志文件
	 * 
	 * @param map
	 * @param sTime
	 * @param eTime
	 * @param return_code
	 * @param userName
	 * @param logId
	 * @param logFile
	 * @throws TxnDataException
	 */
	public void creatLogFile(HashMap<String, String> map, String filepath,
			String sTime, String eTime, String return_code, String userName,
			String logId, String logFile, HashMap columnNames,
			StringBuffer colTable_ColumnInfo, String type, String split)
			throws TxnDataException
	{
		StringBuffer logStr = new StringBuffer("");

		String endTime = CalendarUtil.getCurrentDateTime();
		// logStr.append("用户【"+user+"】您好，您在时间段【"+startTime+"至"+endTime+"】,");
		String collect_mode = (String) map.get("COLLECT_MODE");
		String mode = "";
		if (collect_mode.equals("2")) {
			mode = "全量";
		} else if (collect_mode.equals("1")) {
			mode = "增量";
		} else {
			mode = "无";
		}
		// logStr.append("用户【"+user+"】您好，您在时间段【"+startTime+"至"+endTime+"】,");
		logStr.append("时间段:" + sTime + "至" + eTime + "\r\n");
		logStr.append("用户:" + userName + "\r\n");

		if (colTable_ColumnInfo.toString() != null) {
			logStr.append("源数据项:" + colTable_ColumnInfo.toString() + "\r\n");
		}

		/** ****目标数据项和采集数据项***** */
		if (type != null && type.equals("excel")) {
			String[] dataStr = getDesDataItemExcel(filepath, columnNames);
			logStr.append("目标数据项:" + dataStr[0] + "\r\n");
			logStr.append("采集数据项:" + dataStr[1] + "\r\n");
		} else {
			String[] dataStr = getDesDataItemTxt(filepath, columnNames, split);
			logStr.append("目标数据项:" + dataStr[0] + "\r\n");
			logStr.append("采集数据项:" + dataStr[1] + "\r\n");
		}

		StringBuffer querySql = new StringBuffer(
				"select collect_table_name,collect_data_amount,task_consume_time from collect_joumal where 1=1  ");
		if (logId != null && !"".equals(logId)) {
			querySql.append(" and  collect_joumal_id ='" + logId + "'");
		}
		System.out.println("查询采集日志sql===" + querySql);

		Map taskMap = null;
		ServiceDAO daoTable = new ServiceDAOImpl(); // 操作数据表Dao
		try {
			taskMap = daoTable.queryService(querySql.toString());
			String count = (String) taskMap.get("COLLECT_DATA_AMOUNT");
			System.out.println("count===" + count);
			if (count != null && count.equals("-1")) {
				count = "0";
			}

			logStr.append("采集表:" + taskMap.get("COLLECT_TABLE_NAME") + "\r\n");
			logStr.append("采集方式:" + mode + "\r\n");
			logStr.append("采集数据量:" + count + "条\r\n");
			logStr.append("总耗时:" + taskMap.get("TASK_CONSUME_TIME") + "秒\r\n");

			System.out.println("采集表【" + taskMap.get("COLLECT_TABLE_NAME")
					+ "】总共采集了【" + count + "】条数据，总耗时为【"
					+ taskMap.get("TASK_CONSUME_TIME") + "】秒!\r\n");

			if (return_code.equals("BAIC0000")) {
				logStr.append("执行结果:成功\r\n");
				logStr.append("错误原因:无\r\n");
			} else {
				logStr.append("执行结果:失败\r\n");
				if (return_code.equals("CODE0002")) {
					logStr.append("错误原因:数据长度过长或者主键冲突\r\n");
				}
			}
			logStr.append("--------------------------------------------------------\r\n");
			System.out.println("%%%%%%%%%%%日志文件内容=" + logStr.toString());
			AnalyCollectFile acf = new AnalyCollectFile();
			acf.appendContext(logFile, logStr.toString());

		
		} catch (Exception e) {
			throw new TxnDataException("error", "查询日志表collect_joumal失败!");

		}

	}

	/**
	 * 
	 * insertLogInfoForFtp(这里用一句话描述这个方法的作用) TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 –
	 * 可选)
	 * 
	 * @param context
	 * @param sTime
	 * @param eTime
	 * @param total
	 * @param count
	 *            void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void insertLogInfoForFtp(Map map, String sTime, String eTime,
			String total, String count, String return_code)
	{
		CollectLogVo collectLogVo = new CollectLogVo(); // 采集日志记录
		String collect_task_id = (String) map.get("COLLECT_TASK_ID");// 采集任务ID
		if (collect_task_id == null) {
			collect_task_id = "";
		}
		String task_name = (String) map.get("TASK_NAME");// 任务名称
		if (task_name == null) {
			task_name = "";
		}
		String collect_type = (String) map.get("COLLECT_TYPE");// 采集类型
		// webservice
		// ftp
		if (collect_type == null) {
			collect_type = "";
		}
		String service_targets_id = (String) map.get("SERCICE_TARGETS_ID");// 服务对象id
		if (service_targets_id == null) {
			service_targets_id = "";
		}

		String service_targets_name = (String) map.get("SERVICE_TARGETS_NAME");// 服务对象名称
		if (service_targets_name == null) {
			service_targets_name = "";
		}

		String task_id = (String) map.get("TASK_ID");// ftp文件id
		if (task_id == null) {
			task_id = "";
		}

		String service_no = (String) map.get("SERVICE_NO");// 服务编号
		if (service_no == null) {
			service_no = "";
		}

		String collect_mode = (String) map.get("COLLECT_MODE");// 采集模式
		if (collect_mode == null) {
			collect_mode = "";
		}

		String collect_table = (String) map.get("COLLECT_TABLE");// 采集表ID
		if (collect_table == null) {
			collect_table = "";
		}

		String collect_table_name = (String) map.get("COLLECT_TABLE_NAME");// 采集表
		if (collect_table_name == null) {
			collect_table_name = "";
		}

		collectLogVo.setCollect_task_id(collect_task_id);// 采集任务ID
		collectLogVo.setTask_name(task_name);// 任务名称
		collectLogVo.setService_targets_id(service_targets_id);// 服务对象id
		collectLogVo.setService_targets_name(service_targets_name);// 服务对像名称
		collectLogVo.setCollect_type(collect_type);// 采集类型
		collectLogVo.setTask_start_time(sTime);// 采集开始时间
		collectLogVo.setTask_end_time(eTime);// 采集结束时间
		collectLogVo.setTask_consume_time(total);// 采集时间
		collectLogVo.setCollect_data_amount(count);// 采集数量
		collectLogVo.setTask_status("");
		collectLogVo.setTask_id(task_id);// ftp文件id
		collectLogVo.setPatameter("");
		collectLogVo.setService_no(service_no);// 服务编号
		collectLogVo.setReturn_codes(return_code);
		collectLogVo.setCollect_mode(collect_mode);// 采集模式
		collectLogVo.setCollect_table(collect_table);// 采集表ID
		collectLogVo.setCollect_table_name(collect_table_name);// 采集表

		// 记录日志
		logger.debug("开始记录文件上传日志...");
		taskInfo.insertLog(collectLogVo);
		logger.debug("记录文件上传日志成功...");

	}

	/**
	 * 
	 * analyTxtData(解析txt数据文件) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param filePathAndName
	 *            :被解析的文件全路径
	 * @param tableName
	 *            ：采集表名称
	 * @param columnNames
	 *            ：采集表字段名称
	 * @param collect_mode
	 *            ：采集方式
	 * @param speator
	 *            ：采集文件每行内容的分隔符
	 * @param checkResultFilePath1
	 *            ：解析结果文件存放全路径
	 * @return String
	 * @throws TxnDataException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void analyTxtDataForFtp(Map map, String filePathAndName,
			String tableName, HashMap columnNames, String collect_mode,
			String speator, String checkResultFilePath1)
			throws TxnDataException
	{
		Long start = System.currentTimeMillis();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
		System.out.println("start:" + start);
		int count = 0;
		String return_code = "";
		DBPoolConnection dbPool = new DBPoolConnection(
				CollectConstants.DATASOURCE_CJK);

		String[] sql = analyTxt(filePathAndName, tableName, columnNames,
				collect_mode, speator, checkResultFilePath1);
		System.out.println(sql[0]);
		if (sql != null && sql.length != 0) {
			try {
				// count = execBatch(sql);
				// dbPool.executeSql(sql[0]);
				System.out.println("sql.length=" + sql.length);
				count = execBatchJob(sql);
				return_code = CollectConstants.CLIENT_FHDM_SUCCESS;
			} catch (Exception e) {
				return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
				throw new TxnDataException("采集数据失败", "数据格式错误!");
			} finally {
				String endTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
				Long end = System.currentTimeMillis();
				System.out.println("end:" + end);
				System.out.println("往表" + tableName + "批量执行条" + count
						+ "Insert操作，共耗时：" + (end - start) / 1000f + "秒！");
				String total = String.valueOf(((end - start) / 1000f));
				insertLogInfoForFtp(map, startTime, endTime, total,
						String.valueOf(count), return_code);
			}
		}

	}

	/**
	 * 
	 * analyTxtData(解析txt数据文件) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param filePathAndName
	 *            :被解析的文件全路径
	 * @param tableName
	 *            ：采集表名称
	 * @param columnNames
	 *            ：采集表字段名称
	 * @param collect_mode
	 *            ：采集方式
	 * @param speator
	 *            ：采集文件每行内容的分隔符
	 * @param checkResultFilePath1
	 *            ：解析结果文件存放全路径
	 * @return String
	 * @throws TxnDataException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void analyTxtData(HashMap map, String filePathAndName,
			String tableName, HashMap columnNames, String collect_mode,
			String speator, String checkResultFilePath1)
	{
		Long start = System.currentTimeMillis();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
		// System.out.println("start:"+start);
		int count = 0;
		String return_code = "";

		String[] sql = analyTxt(filePathAndName, tableName, columnNames,
				collect_mode, speator, checkResultFilePath1);
		if (sql != null && sql.length != 0) {
			try {
				count = execBatch(sql);
				if (count == -1) {
					return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
					count = 0;
				} else {
					return_code = CollectConstants.CLIENT_FHDM_SUCCESS;
				}
			} catch (Exception e) {
				System.out.println("异常！！！！！！！！");
				return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
				// throw new TxnDataException("采集数据失败", "数据格式错误!");
			} finally {
				String endTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
				Long end = System.currentTimeMillis();
				// System.out.println("end:"+end);
				System.out.println("往表" + tableName + "批量执行条" + count
						+ "Insert操作，共耗时：" + (end - start) / 1000f + "秒！");
				String total = String.valueOf(((end - start) / 1000f));
				insertLogInfo(map, startTime, endTime, total,
						String.valueOf(count), return_code);
			}
		}

	}

	/**
	 * 
	 * analyTxtData(解析txt数据文件) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param filePathAndName
	 *            :被解析的文件全路径
	 * @param tableName
	 *            ：采集表名称
	 * @param columnNames
	 *            ：采集表字段名称
	 * @param collect_mode
	 *            ：采集方式
	 * @param speator
	 *            ：采集文件每行内容的分隔符
	 * @param checkResultFilePath1
	 *            ：解析结果文件存放全路径
	 * @return String
	 * @throws TxnDataException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String analyTxtData2(HashMap map, String filepath, String tableName,
			HashMap columnNames, String collect_mode, String speator,
			String checkResultFilePath1, String userName, String logFile,
			StringBuffer colTable_ColumnInfo) throws TxnDataException
	{
		Long start = System.currentTimeMillis();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
		// System.out.println("start:"+start);
		int count = 0;
		String return_code = "";
		String logId = "";
		String[] sql = analyTxt(filepath, tableName, columnNames, collect_mode,
				speator, checkResultFilePath1);
		if (sql != null && sql.length != 0) {
			try {
				count = execBatch(sql);
				if (count == -1) {
					return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
					count = 0;
				} else {
					return_code = CollectConstants.CLIENT_FHDM_SUCCESS;
				}
			} catch (Exception e) {
				System.out.println("异常！！！！！！！！");
				return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
				throw new TxnDataException("采集数据失败", "数据格式错误!");
			} finally {
				String endTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
				Long end = System.currentTimeMillis();
				// System.out.println("end:"+end);
				System.out.println("往表" + tableName + "批量执行条" + count
						+ "Insert操作，共耗时：" + (end - start) / 1000f + "秒！");
				String total = String.valueOf(((end - start) / 1000f));

				try {
					logId = insertLogInfo(map, startTime, endTime, total,
							String.valueOf(count), return_code);// 插入日志表
				} catch (Exception e) {
					return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
					throw new TxnDataException("采集日志记录失败", "采集日志记录失败!");
				} finally {
					creatLogFile(map, filepath, startTime, endTime,
							return_code, userName, logId, logFile, columnNames,
							colTable_ColumnInfo, "txt", speator);// 生成日志文件
				}

			}
		}
		if (return_code.equals("BAIC0000")) {
			return_code = CollectConstants.COLLECT_STATUS_SUCCESS;// 采集成功
		} else {
			return_code = CollectConstants.COLLECT_STATUS_FAIL;// 采集失败
		}
		return return_code;

	}

	/**
	 * 
	 * analyTxtData3(解析txt数据文件) 
	 * 
	 * @param filePathAndName
	 *            :被解析的文件全路径
	 * @param tableName
	 *            ：采集表名称
	 * @param columnNames
	 *            ：采集表字段名称
	 * @param collect_mode
	 *            ：采集方式
	 * @param speator
	 *            ：采集文件每行内容的分隔符
	 * @param checkResultFilePath1
	 *            ：解析结果文件存放全路径
	 * @return String
	 * @throws Exception
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String analyTxtData3(HashMap map, String filepath, String tableName,
			HashMap colums, String titletype, String collect_mode,
			String speator, String checkResultFilePath1, String userName,
			String logFile, StringBuffer colTable_ColumnInfo) throws Exception
	{
		Long start = System.currentTimeMillis();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
		// System.out.println("start:"+start);
		int count = 0;
		String return_code = "";
		String logId = "";
		String[] sql = analyTxt2(filepath, tableName, colums, titletype,
				collect_mode, speator, checkResultFilePath1);

		if (sql != null && sql.length != 0) {
			try {
				count = execBatch(sql);
				if (count == -1) {
					return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
					count = 0;
				} else {
					return_code = CollectConstants.CLIENT_FHDM_SUCCESS;
				}
			} catch (Exception e) {
				// System.out.println("异常！！！！！！！！");
				return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
				throw new TxnDataException("采集数据失败", "数据格式错误!");
			} finally {
				String endTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
				Long end = System.currentTimeMillis();
				// System.out.println("end:"+end);
				logger.info("往表" + tableName + "批量执行条" + count
						+ "Insert操作，共耗时：" + (end - start) / 1000f + "秒！");
				String total = String.valueOf(((end - start) / 1000f));

				try {
					// 写入采集日志
					logId = insertLogInfo(map, startTime, endTime, total,
							String.valueOf(count), return_code);// 插入日志表
				} catch (Exception e) {
					return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
					throw new TxnDataException("采集日志记录失败", "采集日志记录失败!");
				} finally {
					creatLogFile(map, filepath, startTime, endTime,
							return_code, userName, logId, logFile, colums,
							colTable_ColumnInfo, "txt", speator);// 生成日志文件
				}

			}
		}
		if (return_code.equals("BAIC0000")) {
			return_code = CollectConstants.COLLECT_STATUS_SUCCESS;// 采集成功
		} else {
			return_code = CollectConstants.COLLECT_STATUS_FAIL;// 采集失败
		}
		return return_code;

	}
	/**
	 * 
	 * analyTxtData3New(解析txt数据文件) 
	 * 
	 * @param filePathAndName
	 *            :被解析的文件全路径
	 * @param tableName
	 *            ：采集表名称
	 * @param columnNames
	 *            ：采集表字段名称
	 * @param collect_mode
	 *            ：采集方式
	 * @param speator
	 *            ：采集文件每行内容的分隔符
	 * @param checkResultFilePath1
	 *            ：解析结果文件存放全路径
	 * @return String 日志表采集状态
	 * @throws Exception
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String analyTxtData3New(HashMap map, String filepath, String tableName,
			HashMap colums, String titletype, String collect_mode,
			String speator, String checkResultFilePath1, String userName,
			String logFile, StringBuffer colTable_ColumnInfo) throws Exception
	{
		Long start = System.currentTimeMillis();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
		int count = 0;
		String return_code = "";
		String logId = "";
		PreparedStatement preStatement=null;
		DBConnectUtil dbUtil=null;
		Connection conn=null;
		try {
			dbUtil = new DBConnectUtil("gwssi_cjk");
			conn = dbUtil.getConnection();
			conn.setAutoCommit(false);
			//解析文件
			preStatement = analyTxt2New(filepath, tableName, colums, 
					titletype,collect_mode, speator, 
					checkResultFilePath1,conn);
			
			//执行批量插入
			Long insert_start = System.currentTimeMillis();
			int[] countList=preStatement.executeBatch();
			conn.commit();
			count=countList.length;
			String consumeTime = String.valueOf(((System.currentTimeMillis()- insert_start) / 1000f));
			logger.info("往表" + tableName + "批量执行" + count
					+ "条Insert操作，耗时：" + consumeTime + "秒！");
			
			
			if (count == -1) {
				return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
				count = 0;
			} else {
				return_code = CollectConstants.CLIENT_FHDM_SUCCESS;
			}
		} catch (BatchUpdateException e) {
			int[] c = e.getUpdateCounts();
			
			logger.debug("错误的数据为第几条..." +  c.length);
			//logger.error("错误语句为:" + sqls[c.length]);
			
			e.printStackTrace();
		} catch (Exception e) {
			return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
			throw new TxnDataException("采集数据失败", "数据格式错误!");
		} finally {
			
			try {
				if(preStatement!=null){
					preStatement.close();
				}				
				dbUtil.closeConnection(conn, null, null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		//计算任务执行耗时
		Long end = System.currentTimeMillis();
		String endTime = sDateFormat.format(new java.util.Date()); 
		String total = String.valueOf(((end- start) / 1000f));
		

		try {
			// 写入采集日志
			logId = insertLogInfo(map, startTime, endTime,total,
					String.valueOf(count), return_code);// 插入日志表
		} catch (Exception e) {
			return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
			throw new TxnDataException("采集日志记录失败", "采集日志记录失败!");
		} finally {
			creatLogFile(map, filepath, startTime, endTime,
					return_code, userName, logId, logFile, colums,
					colTable_ColumnInfo, "txt", speator);// 生成日志文件
		}

		
		//根据返回结果赋值采集状态
		if (return_code.equals("BAIC0000")) {
			return_code = CollectConstants.COLLECT_STATUS_SUCCESS;// 采集成功
		} else {
			return_code = CollectConstants.COLLECT_STATUS_FAIL;// 采集失败
		}
		return return_code;

	}

	/*
	 * public static List<Map> analyMdbData(){
	 * 
	 * List<Map> maplist = new ArrayList(); Properties prop = new Properties();
	 * prop.put("charSet", "gb2312");//解决中文乱码 prop.put("user", "aaa");
	 * prop.put("password", "aaa");
	 * 
	 * String url = "jdbc:odbc:driver={Microsoft Access
	 * Driver(*.mdb)};DBQ=d:\\access.mdb";//文件地址 PreparedStatement ps = null;
	 * Statement stmt = null; ResultSet rs = null;
	 * 
	 * try { Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); try { Connection
	 * conn = DriverManager.getConnection(url,prop); stmt =
	 * (Statement)conn.createStatement(); rs = stmt.executeQuery("select * from
	 * department"); ResultSetMetaData data = rs.getMetaData();
	 * while(rs.next()){ Map map = new HashMap(); for(int
	 * i=0;i<data.getColumnCount();i++){ String columnName =
	 * data.getColumnClassName(i);//列名 String columnValue = rs.getString(i);
	 * map.put(columnName, columnValue); } maplist.add(map); } } catch
	 * (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } } catch (ClassNotFoundException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * 
	 * 
	 * return maplist; }
	 */

	/**
	 * writeFile(创建文件，并把指定内容写入文件) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 –
	 * 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param filePathAndName
	 * @param fileContent
	 *            void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void writeFile(String filePathAndName, String fileContent)
	{
		try {

			String path = filePathAndName.substring(0,
					filePathAndName.lastIndexOf(File.separator) + 1);
			// System.out.println(path);
			File f = new File(path);
			// System.out.println(filePathAndName);
			// 创建文件夹
			if (!f.exists()) {
				f.mkdirs();
				logger.info("指定路径不存在，创建文件夹path=" + path);
			}
			f = new File(filePathAndName);
			if (!f.exists()) {
				f.createNewFile();
			}

			OutputStreamWriter write = new OutputStreamWriter(
					new FileOutputStream(f), "UTF-8");
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(fileContent);
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("写文件内容操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * appendMethodB(追加方式写入文件内容) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 –
	 * 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param fileName
	 * @param content
	 *            void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void appendContext(String fileName, String content)
	{
		/*
		 * try { // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件 //FileWriter writer = new
		 * FileWriter(fileName, true); Writer writer = new BufferedWriter(new
		 * OutputStreamWriter(new FileOutputStream(fileName),"UTF-8"));
		 * writer.write(content); writer.close(); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */

		try {
			String path = fileName.substring(0,
					fileName.lastIndexOf(File.separator) + 1);
			// System.out.println(path);
			File f = new File(path);
			// System.out.println(fileName);
			// 创建文件夹
			if (!f.exists()) {
				f.mkdirs();
				logger.info("指定路径不存在，创建文件夹path=" + path);
			}
			f = new File(fileName);
			if (!f.exists()) {
				f.createNewFile();
			}
			// String fileEncode = System.getProperty("file.encoding");
			// System.out.println("%%%%系统默认的fileEncode码="+fileEncode);

			OutputStreamWriter osw = new OutputStreamWriter(
			// new FileOutputStream(fileName, true), fileEncode);
					new FileOutputStream(fileName, true), "UTF-8");

			// osw.write(new String(content.getBytes("UTF-8"),fileEncode));
			osw.write(content);
			osw.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * collectDatabase(这里用一句话描述这个方法的作用) TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 –
	 * 可选)
	 * 
	 * @param map
	 * @param filepath
	 * @param tableName
	 * @param desccolumnNames
	 *            ：数据中心采集库中采集表的数据项
	 * @param collect_mode
	 * @param sourceColumnName
	 *            :源采集哭中采集表的数据项信息
	 * @param userName
	 * @param logFile
	 * @param colTable_ColumnInfo
	 * @return
	 * @throws TxnDataException
	 *             String
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public String collectDatabase(String db_type,
			HashMap<String, String> souColumnsType, HashMap map, String[] sql,
			String tableName, HashMap desccolumnNames, String collect_mode,
			HashMap sourceColumnName, String userName, String logFile,
			StringBuffer colTable_ColumnInfo) throws TxnDataException
	{

		Long start = System.currentTimeMillis();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
		// System.out.println("start:" + start);
		int count = 0;
		String return_code = "";
		String logId = null;

		if (sql != null && sql.length != 0) {
			try {
				count = execBatch(sql);
				if (count == -1) {
					return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
					count = 0;
				} else {
					return_code = CollectConstants.CLIENT_FHDM_SUCCESS;
				}
			} catch (Exception e) {
				return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
				throw new TxnDataException("采集数据失败", "数据格式错误!");
			} finally {
				String endTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
				Long end = System.currentTimeMillis();
				System.out.println("end:" + end);
				System.out.println("往表" + tableName + "批量执行条" + count
						+ "Insert操作，共耗时：" + (end - start) / 1000f + "秒！");
				String total = String.valueOf(((end - start) / 1000f));
				try {
					logId = insertLogInfo(map, startTime, endTime, total,
							String.valueOf(count), return_code);// 插入日志表
				} catch (Exception e) {
					return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
					throw new TxnDataException("采集日志记录失败", "采集日志记录失败!");
				} finally {
					creatDatabaseLogFile(db_type, souColumnsType, map,
							desccolumnNames, startTime, endTime, return_code,
							userName, logId, logFile, sourceColumnName,
							colTable_ColumnInfo);// 生成日志文件

				}
			}
		}
		if (return_code.equals("BAIC0000") || "".equals(return_code)) {
			return_code = CollectConstants.COLLECT_STATUS_SUCCESS;// 采集成功
		} else {
			return_code = CollectConstants.COLLECT_STATUS_FAIL;// 采集失败
		}
		return return_code;

	}

	/**
	 * 
	 * creatDatabaseLogFile(生成数据库的日志文件) TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 –
	 * 可选)
	 * 
	 * @param map
	 * @param filepath
	 * @param sTime
	 * @param eTime
	 * @param return_code
	 * @param userName
	 * @param logId
	 * @param logFile
	 * @param columnNames
	 * @param colTable_ColumnInfo
	 * @param type
	 * @param split
	 * @throws TxnDataException
	 *             void
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public void creatDatabaseLogFile(String db_type,
			HashMap<String, String> sourceColumnType, HashMap map,
			HashMap sourceColumns, String sTime, String eTime,
			String return_code, String userName, String logId, String logFile,
			HashMap columnNames, StringBuffer colTable_ColumnInfo)
			throws TxnDataException
	{
		StringBuffer logStr = new StringBuffer("");

		String endTime = CalendarUtil.getCurrentDateTime();
		// logStr.append("用户【"+user+"】您好，您在时间段【"+startTime+"至"+endTime+"】,");
		String collect_mode = (String) map.get("COLLECT_MODE");
		String mode = "";
		if (collect_mode.equals("2")) {
			mode = "全量";
		} else if (collect_mode.equals("1")) {
			mode = "增量";
		} else {
			mode = "无";
		}
		// logStr.append("用户【"+user+"】您好，您在时间段【"+startTime+"至"+endTime+"】,");
		logStr.append("时间段:" + sTime + "至" + eTime + "\r\n");
		logStr.append("用户:" + userName + "\r\n");

		if (colTable_ColumnInfo.toString() != null) {
			logStr.append("源数据项:" + colTable_ColumnInfo.toString() + "\r\n");
		}

		/** ****目标数据项和采集数据项***** */

		String[] dataStr = getDatabaseDesDataItem(db_type, sourceColumnType,
				sourceColumns, columnNames);
		logStr.append("目标数据项:" + dataStr[0] + "\r\n");
		logStr.append("采集数据项:" + dataStr[1] + "\r\n");

		StringBuffer querySql = new StringBuffer(
				"select collect_table_name,collect_data_amount,task_consume_time from collect_joumal where 1=1  ");
		if (logId != null && !"".equals(logId)) {
			querySql.append(" and  collect_joumal_id ='" + logId + "'");
		}
		System.out.println("查询采集日志sql===" + querySql);

		Map taskMap = null;
		ServiceDAO daoTable = new ServiceDAOImpl(); // 操作数据表Dao
		try {
			taskMap = daoTable.queryService(querySql.toString());
			String count = (String) taskMap.get("COLLECT_DATA_AMOUNT");
			System.out.println("count===" + count);
			if (count != null && count.equals("-1")) {
				count = "0";
			}

			logStr.append("采集表:" + taskMap.get("COLLECT_TABLE_NAME") + "\r\n");
			logStr.append("采集方式:" + mode + "\r\n");
			logStr.append("采集数据量:" + count + "条\r\n");
			logStr.append("总耗时:" + taskMap.get("TASK_CONSUME_TIME") + "秒\r\n");

			System.out.println("采集表【" + taskMap.get("COLLECT_TABLE_NAME")
					+ "】总共采集了【" + count + "】条数据，总耗时为【"
					+ taskMap.get("TASK_CONSUME_TIME") + "】秒!\r\n");

			if (return_code.equals("BAIC0000")) {
				logStr.append("执行结果:成功\r\n");
				logStr.append("错误原因:无\r\n");
			} else {
				logStr.append("执行结果:失败\r\n");
				if (return_code.equals("CODE0002")) {
					logStr.append("错误原因:数据长度过长或者主键冲突\r\n");
				}
			}
			logStr.append("--------------------------------------------------------\r\n");
			AnalyCollectFile acf = new AnalyCollectFile();
			acf.appendContext(logFile, logStr.toString());
		} catch (Exception e) {
			throw new TxnDataException("error", "查询日志表collect_joumal失败!");

		}

	}

	/**
	 * 
	 * getDesDataItem(获取数据库中源采集项和实际被采集的数据项) TODO(这里描述这个方法适用条件 – 可选)
	 * TODO(这里描述这个方法的执行流程 – 可选) TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 –
	 * 可选)
	 * 
	 * @param souColumns
	 *            :源采集数据项
	 * @param desColumns
	 *            :实际被采集的数据项
	 * @return String[] String[0]:存放采集文件的数据项；String[1]:存放实际被采集的数据项
	 * @Exception 异常对象
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 */
	public String[] getDatabaseDesDataItem(String db_type,
			HashMap<String, String> souColumnsType, HashMap souColumns,
			HashMap desColumns)
	{
		String[] itemInfo = new String[2];
		StringBuffer souItem = new StringBuffer();
		StringBuffer colItem = new StringBuffer();
		DatabaseResource dr = new DatabaseResource();
		String bigNumType = dr.getBigNumType(db_type);

		for (int i = 0; i < souColumns.size(); i++) {
			String tmpColumn = souColumns.get(i).toString().toUpperCase();

			if (i != (souColumns.size() - 1)) {
				souItem.append(tmpColumn + ",");
				if (desColumns.containsValue(tmpColumn)
						&& (bigNumType.indexOf(souColumnsType.get(tmpColumn)
								.toUpperCase()) < 0)) {
					colItem.append(tmpColumn + ",");
				}
			} else {
				souItem.append(tmpColumn + " ");
				if (desColumns.containsValue(tmpColumn)
						&& (bigNumType.indexOf(souColumnsType.get(tmpColumn)
								.toUpperCase()) < 0)) {
					colItem.append(tmpColumn + " ");
				}
			}

		}

		//
		String colItemStr = colItem.toString();

		if (colItemStr.endsWith(",")) {
			colItemStr = colItemStr.substring(0, colItemStr.length() - 1);
		}

		itemInfo[0] = souItem.toString();
		itemInfo[1] = colItemStr;
		System.out.println("采集文件的数据项==" + itemInfo[0]);
		System.out.println("实际被采集的数据项==" + itemInfo[1]);
		return itemInfo;

	}

	/**
	 * main(这里用一句话描述这个方法的作用) TODO(这里描述这个方法适用条件 – 可选) TODO(这里描述这个方法的执行流程 – 可选)
	 * TODO(这里描述这个方法的使用方法 – 可选) TODO(这里描述这个方法的注意事项 – 可选)
	 * 
	 * @param args
	 *            void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		/* .excel */
		/*
		 * HashMap cols = new HashMap(); cols.put("0", "FILE_TEST_ID");
		 * cols.put("1", "FILE_NAME"); cols.put("2", "FILE_PATH"); cols.put("3",
		 * "CREAT_DATE");
		 * AnalyExcel.analyExcelData("D:\\fiel_test.xls","file_test",cols,"2");
		 */

		/* .txt */
		/*
		 * AnalyCollectFile ac = new AnalyCollectFile(); ac.execDelSql("delete
		 * from file_test");
		 */
		/*
		 * HashMap cols = new HashMap(); cols.put("0", "REG_BUS_ENT_ID");
		 * cols.put("1", "ORGAN_CODE"); cols.put("2", "PRI_P_ID"); cols.put("3",
		 * "REG_NO");
		 * ac.analyTxtData("D:\\REG_BUS_ENT_10万条111.txt","REG_BUS_ENT_DWN_TEST"
		 * ,cols,"2","\\|","d:/cr.txt");
		 */
		/*
		 * String str =
		 * "D:/datafiles/file_upload/2013/2013-05/2013-05-07/00000000.xls";
		 * System.out.println("文件后缀："+str.substring(str.lastIndexOf("/")+1));
		 * System.out.println("文件后缀1："+str.substring(0,str.lastIndexOf(".")));
		 */
		/*
		 * String fileType =
		 * "D:/datafiles/file_upload/2013/2013-05/2013-05-07/00000000.xls";
		 * String checkResultPath =
		 * fileType.substring(0,fileType.lastIndexOf("."
		 * ))+"_xls_"+"checkResult.txt";
		 * System.out.println("checkResultPath=="+checkResultPath);
		 */
		/*
		 * try { loadFile("D:\\REG_BUS_ENT记事本采集文件样例.txt"); } catch (IOException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */

		// analyMdbData();
		/*
		 * HashMap hm = new HashMap();
		 * 
		 * System.out.println("hm="+hm.size());
		 */
		/*
		 * int num = 0; if(num == 0){ throw new
		 * IllegalArgumentException("除数不能为零!"); }
		 */
		AnalyCollectFile ac = new AnalyCollectFile();
		String fileName = "E:/logFile/t2.txt";
		ac.appendContext(fileName, "");

		/*
		 * String fileName = "E:/logFile/t2.xls"; try { // 打开文件 WritableWorkbook
		 * book = Workbook.createWorkbook( new File(fileName)); //
		 * 生成名为“第一页”的工作表，参数0表示这是第一页 WritableSheet sheet = book.createSheet( "
		 * 第一页 " , 0 ); // 在Label对象的构造子中指名单元格位置是第一列第一行(0,0) // 以及单元格内容为test
		 * Label label = new Label( 0 , 0 , " test " ); // 将定义好的单元格添加到工作表中
		 * sheet.addCell(label); jxl.write.Number number = new jxl.write.Number(
		 * 1 , 0 , 555.12541 ); sheet.addCell(number); // 写入数据并关闭文件
		 * book.write(); book.close(); } catch (Exception e) {
		 * System.out.println(e); }
		 */

	}

	/**
	 * 判断文件的编码格式 如不识别默认为GBK
	 * 
	 * @param fileName
	 *            :file
	 * @return 文件编码格式
	 * @throws Exception
	 */
	public String codeString(String fileName) throws Exception
	{
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
				fileName));
		int p = (bin.read() << 8) + bin.read();
		System.out.println("文件头=" + p);
		String code = null;
		bin.close();
		switch (p) {
		case 0xefbb:
			code = "UTF-8";
			break;
		case 0xfffe:
			code = "Unicode";
			break;
		case 0xfeff:
			code = "UTF-16BE";
			break;
		default:
			code = "GBK";
		}

		return code;
	}
	
	/**
	 * analyTxt2New(解析txt文件) 
	 * @param filePathAndName
	 * @param tableName
	 * @param columnCN
	 * @param titleType
	 * @param collect_mode
	 * @param speator
	 * @param checkResultFilePath1
	 * @return
	 * @throws Exception
	 */
	public PreparedStatement analyTxt2New(String filePathAndName, String tableName,
			HashMap columnCN, String titleType, String collect_mode,
			String speator, String checkResultFilePath1 , Connection conn) throws Exception
	{
		
		StringBuffer checkResult = new StringBuffer();//列名校验结果
		String[] colNames;
		String[] colValues;
		if (speator == null || "".equals(speator)) {
			speator = CollectConstants.COLLECT_FILE_SPEARTOR;
		}

		//List<String> sqlList = new ArrayList();
		StringBuffer insertSQL=new StringBuffer();
		
		PreparedStatement preStatement =null; 
		
		//如果采集方式是全量则先清空表内数据
		if (CollectConstants.COLLECT_MODE_ALL.equals(collect_mode)) {
			String delSql = "delete from " + tableName;
			execDelSql(delSql);

		}
		/**读取文件内容**/
		
		File f = new File(filePathAndName);
		if (f.isFile() && f.exists()) {
			
			try {
				long start = System.currentTimeMillis();//文件解析计时
				//判断文件编码格式
				//String filecode = codeString(filePathAndName);
				String filecode = "UTF-8";
				logger.info("文件编码格式为:" + filecode);
				FileInputStream read = new FileInputStream(f);
				BufferedReader reader = new BufferedReader(new UnicodeReader(
						read, filecode/* Charset.defaultCharset().name() */));

				String line;
				
				try {
					
					line = reader.readLine();
					logger.info("标题line=" + line);
					StringBuffer placeholder = new StringBuffer();//问号占位符
					if (line != null) {
						// 第一行列名的处理
						insertSQL.append("insert into ").append(tableName).append("(");
						colNames = line.split(speator, -1);
						// checkTitleLine(colNames,keyMap,columnCN,checkResult);
						for (int i = 0; i < colNames.length; i++) {
							//判断列名有效性
							if (!(columnCN.containsValue(colNames[i]
									.toUpperCase()) || columnCN
									.containsKey(colNames[i].toUpperCase()))) {

								checkResult.append("列名[" + colNames[i]
										+ "]不是采集表中的字段\r\n");
							}else{//有效列名
								if ("EN".equals(titleType)) {
									//标题是英文直接用于组装SQL语句
									insertSQL.append(colNames[i]).append(",");
									placeholder.append("?,");
								} else {
									//标题是中文描述则根据Map获取对应的英文列名，再组装SQL语句
									insertSQL.append(columnCN.get(colNames[i])).append(",");
									placeholder.append("?,");
								}
								
							}
						}
					
						//加上更新时间列
						insertSQL.append("UPDATE_DATE").append(") values(")
							.append(placeholder).append("?)");
						System.out.println(insertSQL.toString());
						preStatement = conn.prepareStatement(insertSQL.toString());						
						// 数据处理
						line = reader.readLine();
						while (line != null && line != "") {
							colValues = line.split(speator, -1);
							int j=1;
							for (int i = 0; i < colValues.length; i++) {

								if (columnCN.containsValue(colNames[i]
										.toUpperCase())
										|| columnCN.containsKey(colNames[i]
												.toUpperCase())) {
									// 列名包含在采集表中
									preStatement.setString(j,colValues[i] );
									j++;
								}
							}
							//更新时间为当天
							SimpleDateFormat sDateFormat = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							String updateDate = sDateFormat.format(new java.util.Date());
							preStatement.setString(j, updateDate);
							preStatement.addBatch();
							
							line = reader.readLine();
						}

					}else{
						logger.error("文件为空");
					}
					long end = System.currentTimeMillis();
					String consumeTime = String
							.valueOf(((end - start) / 1000f));
					logger.debug("-----文件读取解析耗时：" + consumeTime);
					if (checkResult.toString() == null
							|| "".equals(checkResult.toString())) {
						checkResult.append("文件中的全部字段均合法!");
					}
					writeFile(checkResultFilePath1, checkResult.toString());
					logger.info("生成校验文件");
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		

		return preStatement;

	}

	

}
