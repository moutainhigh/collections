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
												.getName());	// ��־

	TaskInfo				taskInfo	= new TaskInfo();		// ������Ϣ

	/**
	 * 
	 * analyExcel(����excel�ļ������ѽ������ݷ�װ��sql��������ʽ����) TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C
	 * ��ѡ)
	 * 
	 * @param filePathAndName
	 *            :���������ļ�ȫ·��
	 * @param tableName
	 *            ���ɼ�������
	 * @param columnNames
	 *            ���ɼ����ֶ�����
	 * @param collect_mode
	 *            ���ɼ���ʽ
	 * @param checkResultFilePath1
	 *            :�����ļ����ȫ·�� void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
					// ���������ֵ
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

							// ��װsql
							if (k > 0 && !colNames.isEmpty()
									&& !colValues.isEmpty()) {
								StringBuffer insertCol = new StringBuffer();
								StringBuffer insertVal = new StringBuffer();
								for (int m = 0; m < cols; m++) {

									if (columnNames.containsValue(colNames
											.get(m).toString().toUpperCase())) {// ���������ڱ��������
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
								// ÿ����Ҫ�����sql
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

						checkResult.append("��" + (i + 1) + "����������У����Ϊ:\r\n");
						boolean flag = false;
						// У���ļ�����ѯ���ڱ��е��ֶ�����
						for (int a = 0; a < cols; a++) {
							if (!columnNames.containsValue(colNames.get(a)
									.toString().toUpperCase())) {
								checkResult.append("��" + (a + 1) + "��["
										+ colNames.get(a) + "]���Ǳ�[" + tableName
										+ "]�е��ֶ�\r\n");
								flag = true;
							}
						}

						if (!flag)
							checkResult.append("�ļ��е�ȫ���ֶξ��Ϸ�!\r\n");

					} else {
						checkResult.append("��" + (i + 1) + "����������У����Ϊ:\r\n");
						checkResult.append("����������Ϊ��\r\n");

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
	 * analyExcel_Ftp(����Ftp���͵�Excel�ɼ��ļ�)
	 * 
	 * @param filepath
	 * @param tableName
	 * @param columnNames
	 * @param titleType
	 *            : EN or CN
	 * @param collect_mode
	 * @param checkResultFilePath1
	 * @return String[]
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
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
					// ���������ֵ
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

							// ��װsql
							if (k > 0 && !colNames.isEmpty()
									&& !colValues.isEmpty()) {
								StringBuffer insertCol = new StringBuffer();
								StringBuffer insertVal = new StringBuffer();
								for (int m = 0; m < cols; m++) {

									if (titleType.equals("EN")) {// ������Ӣ����
										if (columnNames.containsValue(colNames
												.get(m).toString()
												.toUpperCase())) {// ���������ڱ��������
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

									} else if (titleType.equals("CN")) {// ������������
										if (columnNames.containsKey(colNames
												.get(m).toString())) {// ���������ڱ��������
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
								// ÿ����Ҫ�����sql
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

						checkResult.append("��" + (i + 1) + "����������У����Ϊ:\r\n");
						boolean flag = false;
						// У���ļ�����ѯ���ڱ��е��ֶ�����
						for (int a = 0; a < cols; a++) {
							if (titleType.equals("EN")) {// ������Ӣ����
								if (!columnNames.containsValue(colNames.get(a)
										.toString().toUpperCase())) {
									checkResult.append("��" + (a + 1) + "��["
											+ colNames.get(a) + "]���Ǳ�["
											+ tableName + "]�е��ֶ�\r\n");
									flag = true;
								}

							} else if (titleType.equals("CN")) {// ������������
								if (!columnNames.containsKey(colNames.get(a)
										.toString())) {
									checkResult.append("��" + (a + 1) + "��["
											+ colNames.get(a) + "]���Ǳ�["
											+ tableName + "]�е��ֶ�\r\n");
									flag = true;
								}

							}

						}

						if (!flag)
							checkResult.append("�ļ��е�ȫ���ֶξ��Ϸ�!\r\n");

					} else {
						checkResult.append("��" + (i + 1) + "����������У����Ϊ:\r\n");
						checkResult.append("����������Ϊ��\r\n");

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
	 * getDesDataItemExcel(�õ��ɼ��ļ���Ŀ���������ʵ�ʱ��ɼ���������)) TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C
	 * ��ѡ)
	 * 
	 * @param filepath
	 *            ���ɼ��ļ���·��
	 * @param columnNames
	 *            ���ɼ����ֶ���Ϣ
	 * @return String[] ��String[0]:��Ųɼ��ļ��������String[1]:���ʵ�ʱ��ɼ���������
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
			System.out.println("�ɼ��ļ���������==" + itemInfo[0]);
			System.out.println("ʵ�ʱ��ɼ���������==" + itemInfo[1]);

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
	 * getDesDataItemTxt(�õ��ɼ��ļ���Ŀ���������ʵ�ʱ��ɼ���������) TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C
	 * ��ѡ)
	 * 
	 * @param filepath
	 *            ���ɼ��ļ���·��
	 * @param columnNames
	 *            ���ɼ����ֶ���Ϣ
	 * @param speator
	 *            ���ļ��ָ���
	 * @return String[] ��String[0]:��Ųɼ��ļ��������String[1]:���ʵ�ʱ��ɼ���������
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
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

					// ��һ�������Ĵ���
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
		System.out.println("�ɼ��ļ���������==" + itemInfo[0]);
		System.out.println("ʵ�ʱ��ɼ���������==" + itemInfo[1]);
		return itemInfo;
	}

	/**
	 * 
	 * analyTxt(����txt�ļ������ѽ����װ��sql���鷵��) TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C
	 * ��ѡ)
	 * 
	 * @param filePathAndName
	 * @param tableName
	 * @param columnNames
	 * @param collect_mode
	 * @param speator
	 * @param checkResultFilePath1
	 * @return String[]
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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

					// ��һ�������Ĵ���
					if (line != null) {
						colNames = line.split(speator, -1);

						for (int i = 0; i < colNames.length; i++) {
							if (columnNames.containsValue(colNames[i]
									.toUpperCase())) {

							} else {
								checkResult.append("����[" + colNames[i]
										+ "]���ǲɼ����е��ֶ�\r\n");
							}
						}

						// �ڶ�����ֵ�ô���
						while ((line = reader.readLine()) != null) {
							StringBuffer insertCol = new StringBuffer();
							StringBuffer insertVal = new StringBuffer();

							colValues = line.split(speator, -1);

							for (int i = 0; i < colValues.length; i++) {

								if (columnNames.containsValue(colNames[i]
										.toUpperCase())) {// ���������ڲɼ�����
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

							// ÿ����Ҫ�����sql
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
						checkResult.append("�ļ��е�ȫ���ֶξ��Ϸ�!");
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
	 * analyTxt2(����txt�ļ������ѽ����װ��sql���鷵��) 
	 * 
	 * @param filePathAndName
	 * @param tableName
	 * @param columnNames
	 * @param collect_mode
	 * @param speator
	 * @param checkResultFilePath1
	 * @return String[]
	 * @throws Exception
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
				logger.info("�ļ������ʽΪ:" + filecode);
				FileInputStream read = new FileInputStream(f);
				BufferedReader reader = new BufferedReader(new UnicodeReader(
						read, filecode/* Charset.defaultCharset().name() */));
				// logger.info("Ĭ���ַ���="+Charset.defaultCharset().name());

				String line;
				// String breakStr=System.getProperty("line.separator");
				try {
					long start = System.currentTimeMillis();
					line = reader.readLine();
					logger.info("����line=" + line);
					// ��һ�������Ĵ���
					if (line != null) {
						colNames = line.split(speator, -1);
						// checkTitleLine(colNames,keyMap,columnCN,checkResult);
						for (int i = 0; i < colNames.length; i++) {
							if (!(columnCN.containsValue(colNames[i]
									.toUpperCase()) || columnCN
									.containsKey(colNames[i].toUpperCase()))) {

								checkResult.append("����[" + colNames[i]
										+ "]���ǲɼ����е��ֶ�\r\n");
							}
						}

						// ��ֵ�ô���
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
									// ���������ڲɼ�����
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

							// ÿ����Ҫ�����sql
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
					System.out.println("-----�ļ���ȡ������ʱ��" + consumeTime);
					if (checkResult.toString() == null
							|| "".equals(checkResult.toString())) {
						checkResult.append("�ļ��е�ȫ���ֶξ��Ϸ�!");
					}
					writeFile(checkResultFilePath1, checkResult.toString());
					logger.info("����У���ļ�");
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
			// logger.debug("���ݲ������sql["+i+"]="+sql[i]);
		}

		return sql;

	}

	/**
	 * 
	 * execDelSql(ִ��ɾ��������)
	 * 
	 * @param sql
	 *            :��ִ�е�sql��� void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void execDelSql(String sql)
	{
		DBPoolConnection conn = new DBPoolConnection(
				CollectConstants.DATASOURCE_CJK);
		conn.executeDML(sql);
	}

	/**
	 * 
	 * execBatch(����ִ�в������)
	 * 
	 * @throws DBException
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public int execBatch(String[] sqls) throws DBException
	{
		int a = 0;
		int count = -1;
		Connection conn = null;
		Statement st = null;
		try {
			
			  conn = DbUtils.getConnection("5"); // ʵ����con
			  conn.setAutoCommit(false); 
			  st = conn.createStatement(); 
			  for (int i = 0; i < sqls.length; i++) { 
				  a = i; 
				  st.addBatch(sqls[i]); 
			  }
			  System.out.println("׼��ִ�������������"); long start =
			  System.currentTimeMillis(); 
			  int[] countList = st.executeBatch();
			  conn.commit(); 
			  long end = System.currentTimeMillis(); 
			  String consumeTime = String.valueOf(((end - start) / 1000f));
			  System.out.println("-------����ִ�в�������ʱ"+consumeTime+"----------");
			  count = countList.length; System.out.println("�������ݣ�"+count);


		} catch (BatchUpdateException e) {
			int[] c = e.getUpdateCounts();
			System.out.println("sss..." + (a - 100 + c.length));
			logger.debug("���������Ϊ�ڼ���..." + (a - 100 + c.length));
			logger.error("�������Ϊ:" + sqls[(a - 100 + c.length)]);
			System.out.println("SQL������..." + e);
			e.printStackTrace();
		} catch (Exception e) {

			System.out.println("SQL������..." + e);
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
			// ����
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
	 * analyExcelData(��ȡexecel�ɼ��ļ����������ݲ������ݿ���) TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C
	 * ��ѡ)
	 * 
	 * @param filePathAndName
	 *            :���������ļ�ȫ·��
	 * @param tableName
	 *            ���ɼ�������
	 * @param columnNames
	 *            ���ɼ����ֶ�����
	 * @param collect_mode
	 *            ���ɼ���ʽ
	 * @param checkResultFilePath1
	 *            :�����ļ����ȫ·�� void
	 * @throws TxnDataException
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void analyExcelData(HashMap map, String filepath, String tableName,
			HashMap columnNames, String collect_mode, String checkResultFilePath)
	{

		Long start = System.currentTimeMillis();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
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
				// throw new TxnDataException("�ɼ�����ʧ��", "���ݸ�ʽ����!");
			} finally {
				String endTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
				Long end = System.currentTimeMillis();
				System.out.println("end:" + end);
				System.out.println("����" + tableName + "����ִ����" + count
						+ "Insert����������ʱ��" + (end - start) / 1000f + "�룡");
				String total = String.valueOf(((end - start) / 1000f));
				insertLogInfo(map, startTime, endTime, total,
						String.valueOf(count), return_code);
			}
		}

	}

	/**
	 * 
	 * analyExcelData(��ȡexecel�ɼ��ļ����������ݲ������ݿ���) TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C
	 * ��ѡ)
	 * 
	 * @param filePathAndName
	 *            :���������ļ�ȫ·��
	 * @param tableName
	 *            ���ɼ�������
	 * @param columnNames
	 *            ���ɼ����ֶ�����
	 * @param collect_mode
	 *            ���ɼ���ʽ
	 * @param checkResultFilePath1
	 *            :�����ļ����ȫ·�� void
	 * @throws TxnDataException
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public String analyExcelData2(HashMap map, String filepath,
			String tableName, HashMap columnNames, String collect_mode,
			String checkResultFilePath, String userName, String logFile,
			StringBuffer colTable_ColumnInfo) throws TxnDataException
	{

		Long start = System.currentTimeMillis();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
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
				throw new TxnDataException("�ɼ�����ʧ��", "���ݸ�ʽ����!");
			} finally {
				String endTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
				Long end = System.currentTimeMillis();
				System.out.println("end:" + end);
				System.out.println("����" + tableName + "����ִ����" + count
						+ "Insert����������ʱ��" + (end - start) / 1000f + "�룡");
				String total = String.valueOf(((end - start) / 1000f));
				try {
					logId = insertLogInfo(map, startTime, endTime, total,
							String.valueOf(count), return_code);// ������־��
				} catch (Exception e) {
					return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
					throw new TxnDataException("�ɼ���־��¼ʧ��", "�ɼ���־��¼ʧ��!");
				} finally {
					creatLogFile(map, filepath, startTime, endTime,
							return_code, userName, logId, logFile, columnNames,
							colTable_ColumnInfo, "excel", "");// ������־�ļ�

				}
			}
		}
		if (return_code.equals("BAIC0000")) {
			return_code = CollectConstants.COLLECT_STATUS_SUCCESS;// �ɼ��ɹ�
		} else {
			return_code = CollectConstants.COLLECT_STATUS_FAIL;// �ɼ�ʧ��
		}
		return return_code;

	}

	/**
	 * 
	 * analyExcelData_Ftp(��ȡexecel�ɼ��ļ����������ݲ������ݿ���) TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C
	 * ��ѡ)
	 * 
	 * @param map
	 * @param filepath
	 * @param tableName
	 * @param columnNames
	 * @param titleType
	 *            :�ɼ��ļ��������ͣ�CN or EN
	 * @param collect_mode
	 * @param checkResultFilePath
	 * @param userName
	 * @param logFile
	 * @param colTable_ColumnInfo
	 * @return
	 * @throws TxnDataException
	 *             String
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
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
		String startTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
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
				throw new TxnDataException("�ɼ�����ʧ��", "���ݸ�ʽ����!");
			} finally {
				String endTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
				Long end = System.currentTimeMillis();
				System.out.println("end:" + end);
				System.out.println("����" + tableName + "����ִ����" + count
						+ "Insert����������ʱ��" + (end - start) / 1000f + "�룡");
				String total = String.valueOf(((end - start) / 1000f));
				try {
					logId = insertLogInfo(map, startTime, endTime, total,
							String.valueOf(count), return_code);// ������־��
				} catch (Exception e) {
					return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
					throw new TxnDataException("�ɼ���־��¼ʧ��", "�ɼ���־��¼ʧ��!");
				} finally {
					creatLogFile(map, filepath, startTime, endTime,
							return_code, userName, logId, logFile, columnNames,
							colTable_ColumnInfo, "excel", "");// ������־�ļ�

				}
			}
		}
		if (return_code.equals("BAIC0000")) {
			return_code = CollectConstants.COLLECT_STATUS_SUCCESS;// �ɼ��ɹ�
		} else {
			return_code = CollectConstants.COLLECT_STATUS_FAIL;// �ɼ�ʧ��
		}
		return return_code;

	}

	/**
	 * 
	 * analyExcelData(��ȡexecel�ɼ��ļ����������ݲ������ݿ���) TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C
	 * ��ѡ)
	 * 
	 * @param filePathAndName
	 *            :���������ļ�ȫ·��
	 * @param tableName
	 *            ���ɼ�������
	 * @param columnNames
	 *            ���ɼ����ֶ�����
	 * @param collect_mode
	 *            ���ɼ���ʽ
	 * @param checkResultFilePath1
	 *            :�����ļ����ȫ·�� void
	 * @throws TxnDataException
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void analyExcelDataForFtp(Map map, String filepath,
			String tableName, HashMap columnNames, String collect_mode,
			String checkResultFilePath) throws TxnDataException
	{

		Long start = System.currentTimeMillis();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
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
				throw new TxnDataException("�ɼ�����ʧ��", "���ݸ�ʽ����!");
			} finally {
				String endTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
				Long end = System.currentTimeMillis();
				/*
				 * System.out.println("end:"+end); System.out.println("����" +
				 * tableName + "����ִ����" + count + "Insert����������ʱ��" + (end - start)
				 * / 1000f + "�룡");
				 */
				String total = String.valueOf(((end - start) / 1000f));
				insertLogInfoForFtp(map, startTime, endTime, total,
						String.valueOf(count), return_code);
			}
		}

	}

	/**
	 * 
	 * insertLogInfo(������־��Ϣ) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param context
	 * @param sTime
	 * @param eTime
	 * @param total
	 * @param count
	 *            void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public String insertLogInfo(HashMap<String, String> map, String sTime,
			String eTime, String total, String count, String return_code)
	{
		CollectLogVo collectLogVo = new CollectLogVo(); // �ɼ���־��¼

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
		// ��¼��־
		logger.debug("��ʼ��¼�ɼ���־...");
		taskInfo.insertLog(id, collectLogVo);
		// taskInfo.insertLog(collectLogVo);
		logger.debug("��¼�ɼ���־�ɹ�...");

		return id;

	}

	/**
	 * ������־�ļ�
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
		// logStr.append("�û���"+user+"�����ã�����ʱ��Ρ�"+startTime+"��"+endTime+"��,");
		String collect_mode = (String) map.get("COLLECT_MODE");
		String mode = "";
		if (collect_mode.equals("2")) {
			mode = "ȫ��";
		} else if (collect_mode.equals("1")) {
			mode = "����";
		} else {
			mode = "��";
		}
		// logStr.append("�û���"+user+"�����ã�����ʱ��Ρ�"+startTime+"��"+endTime+"��,");
		logStr.append("ʱ���:" + sTime + "��" + eTime + "\r\n");
		logStr.append("�û�:" + userName + "\r\n");

		if (colTable_ColumnInfo.toString() != null) {
			logStr.append("Դ������:" + colTable_ColumnInfo.toString() + "\r\n");
		}

		/** ****Ŀ��������Ͳɼ�������***** */
		if (type != null && type.equals("excel")) {
			String[] dataStr = getDesDataItemExcel(filepath, columnNames);
			logStr.append("Ŀ��������:" + dataStr[0] + "\r\n");
			logStr.append("�ɼ�������:" + dataStr[1] + "\r\n");
		} else {
			String[] dataStr = getDesDataItemTxt(filepath, columnNames, split);
			logStr.append("Ŀ��������:" + dataStr[0] + "\r\n");
			logStr.append("�ɼ�������:" + dataStr[1] + "\r\n");
		}

		StringBuffer querySql = new StringBuffer(
				"select collect_table_name,collect_data_amount,task_consume_time from collect_joumal where 1=1  ");
		if (logId != null && !"".equals(logId)) {
			querySql.append(" and  collect_joumal_id ='" + logId + "'");
		}
		System.out.println("��ѯ�ɼ���־sql===" + querySql);

		Map taskMap = null;
		ServiceDAO daoTable = new ServiceDAOImpl(); // �������ݱ�Dao
		try {
			taskMap = daoTable.queryService(querySql.toString());
			String count = (String) taskMap.get("COLLECT_DATA_AMOUNT");
			System.out.println("count===" + count);
			if (count != null && count.equals("-1")) {
				count = "0";
			}

			logStr.append("�ɼ���:" + taskMap.get("COLLECT_TABLE_NAME") + "\r\n");
			logStr.append("�ɼ���ʽ:" + mode + "\r\n");
			logStr.append("�ɼ�������:" + count + "��\r\n");
			logStr.append("�ܺ�ʱ:" + taskMap.get("TASK_CONSUME_TIME") + "��\r\n");

			System.out.println("�ɼ���" + taskMap.get("COLLECT_TABLE_NAME")
					+ "���ܹ��ɼ��ˡ�" + count + "�������ݣ��ܺ�ʱΪ��"
					+ taskMap.get("TASK_CONSUME_TIME") + "����!\r\n");

			if (return_code.equals("BAIC0000")) {
				logStr.append("ִ�н��:�ɹ�\r\n");
				logStr.append("����ԭ��:��\r\n");
			} else {
				logStr.append("ִ�н��:ʧ��\r\n");
				if (return_code.equals("CODE0002")) {
					logStr.append("����ԭ��:���ݳ��ȹ�������������ͻ\r\n");
				}
			}
			logStr.append("--------------------------------------------------------\r\n");
			System.out.println("%%%%%%%%%%%��־�ļ�����=" + logStr.toString());
			AnalyCollectFile acf = new AnalyCollectFile();
			acf.appendContext(logFile, logStr.toString());

		
		} catch (Exception e) {
			throw new TxnDataException("error", "��ѯ��־��collect_joumalʧ��!");

		}

	}

	/**
	 * 
	 * insertLogInfoForFtp(������һ�仰�����������������) TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C
	 * ��ѡ)
	 * 
	 * @param context
	 * @param sTime
	 * @param eTime
	 * @param total
	 * @param count
	 *            void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void insertLogInfoForFtp(Map map, String sTime, String eTime,
			String total, String count, String return_code)
	{
		CollectLogVo collectLogVo = new CollectLogVo(); // �ɼ���־��¼
		String collect_task_id = (String) map.get("COLLECT_TASK_ID");// �ɼ�����ID
		if (collect_task_id == null) {
			collect_task_id = "";
		}
		String task_name = (String) map.get("TASK_NAME");// ��������
		if (task_name == null) {
			task_name = "";
		}
		String collect_type = (String) map.get("COLLECT_TYPE");// �ɼ�����
		// webservice
		// ftp
		if (collect_type == null) {
			collect_type = "";
		}
		String service_targets_id = (String) map.get("SERCICE_TARGETS_ID");// �������id
		if (service_targets_id == null) {
			service_targets_id = "";
		}

		String service_targets_name = (String) map.get("SERVICE_TARGETS_NAME");// �����������
		if (service_targets_name == null) {
			service_targets_name = "";
		}

		String task_id = (String) map.get("TASK_ID");// ftp�ļ�id
		if (task_id == null) {
			task_id = "";
		}

		String service_no = (String) map.get("SERVICE_NO");// ������
		if (service_no == null) {
			service_no = "";
		}

		String collect_mode = (String) map.get("COLLECT_MODE");// �ɼ�ģʽ
		if (collect_mode == null) {
			collect_mode = "";
		}

		String collect_table = (String) map.get("COLLECT_TABLE");// �ɼ���ID
		if (collect_table == null) {
			collect_table = "";
		}

		String collect_table_name = (String) map.get("COLLECT_TABLE_NAME");// �ɼ���
		if (collect_table_name == null) {
			collect_table_name = "";
		}

		collectLogVo.setCollect_task_id(collect_task_id);// �ɼ�����ID
		collectLogVo.setTask_name(task_name);// ��������
		collectLogVo.setService_targets_id(service_targets_id);// �������id
		collectLogVo.setService_targets_name(service_targets_name);// �����������
		collectLogVo.setCollect_type(collect_type);// �ɼ�����
		collectLogVo.setTask_start_time(sTime);// �ɼ���ʼʱ��
		collectLogVo.setTask_end_time(eTime);// �ɼ�����ʱ��
		collectLogVo.setTask_consume_time(total);// �ɼ�ʱ��
		collectLogVo.setCollect_data_amount(count);// �ɼ�����
		collectLogVo.setTask_status("");
		collectLogVo.setTask_id(task_id);// ftp�ļ�id
		collectLogVo.setPatameter("");
		collectLogVo.setService_no(service_no);// ������
		collectLogVo.setReturn_codes(return_code);
		collectLogVo.setCollect_mode(collect_mode);// �ɼ�ģʽ
		collectLogVo.setCollect_table(collect_table);// �ɼ���ID
		collectLogVo.setCollect_table_name(collect_table_name);// �ɼ���

		// ��¼��־
		logger.debug("��ʼ��¼�ļ��ϴ���־...");
		taskInfo.insertLog(collectLogVo);
		logger.debug("��¼�ļ��ϴ���־�ɹ�...");

	}

	/**
	 * 
	 * analyTxtData(����txt�����ļ�) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param filePathAndName
	 *            :���������ļ�ȫ·��
	 * @param tableName
	 *            ���ɼ�������
	 * @param columnNames
	 *            ���ɼ����ֶ�����
	 * @param collect_mode
	 *            ���ɼ���ʽ
	 * @param speator
	 *            ���ɼ��ļ�ÿ�����ݵķָ���
	 * @param checkResultFilePath1
	 *            ����������ļ����ȫ·��
	 * @return String
	 * @throws TxnDataException
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void analyTxtDataForFtp(Map map, String filePathAndName,
			String tableName, HashMap columnNames, String collect_mode,
			String speator, String checkResultFilePath1)
			throws TxnDataException
	{
		Long start = System.currentTimeMillis();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
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
				throw new TxnDataException("�ɼ�����ʧ��", "���ݸ�ʽ����!");
			} finally {
				String endTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
				Long end = System.currentTimeMillis();
				System.out.println("end:" + end);
				System.out.println("����" + tableName + "����ִ����" + count
						+ "Insert����������ʱ��" + (end - start) / 1000f + "�룡");
				String total = String.valueOf(((end - start) / 1000f));
				insertLogInfoForFtp(map, startTime, endTime, total,
						String.valueOf(count), return_code);
			}
		}

	}

	/**
	 * 
	 * analyTxtData(����txt�����ļ�) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param filePathAndName
	 *            :���������ļ�ȫ·��
	 * @param tableName
	 *            ���ɼ�������
	 * @param columnNames
	 *            ���ɼ����ֶ�����
	 * @param collect_mode
	 *            ���ɼ���ʽ
	 * @param speator
	 *            ���ɼ��ļ�ÿ�����ݵķָ���
	 * @param checkResultFilePath1
	 *            ����������ļ����ȫ·��
	 * @return String
	 * @throws TxnDataException
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void analyTxtData(HashMap map, String filePathAndName,
			String tableName, HashMap columnNames, String collect_mode,
			String speator, String checkResultFilePath1)
	{
		Long start = System.currentTimeMillis();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
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
				System.out.println("�쳣����������������");
				return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
				// throw new TxnDataException("�ɼ�����ʧ��", "���ݸ�ʽ����!");
			} finally {
				String endTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
				Long end = System.currentTimeMillis();
				// System.out.println("end:"+end);
				System.out.println("����" + tableName + "����ִ����" + count
						+ "Insert����������ʱ��" + (end - start) / 1000f + "�룡");
				String total = String.valueOf(((end - start) / 1000f));
				insertLogInfo(map, startTime, endTime, total,
						String.valueOf(count), return_code);
			}
		}

	}

	/**
	 * 
	 * analyTxtData(����txt�����ļ�) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param filePathAndName
	 *            :���������ļ�ȫ·��
	 * @param tableName
	 *            ���ɼ�������
	 * @param columnNames
	 *            ���ɼ����ֶ�����
	 * @param collect_mode
	 *            ���ɼ���ʽ
	 * @param speator
	 *            ���ɼ��ļ�ÿ�����ݵķָ���
	 * @param checkResultFilePath1
	 *            ����������ļ����ȫ·��
	 * @return String
	 * @throws TxnDataException
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public String analyTxtData2(HashMap map, String filepath, String tableName,
			HashMap columnNames, String collect_mode, String speator,
			String checkResultFilePath1, String userName, String logFile,
			StringBuffer colTable_ColumnInfo) throws TxnDataException
	{
		Long start = System.currentTimeMillis();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
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
				System.out.println("�쳣����������������");
				return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
				throw new TxnDataException("�ɼ�����ʧ��", "���ݸ�ʽ����!");
			} finally {
				String endTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
				Long end = System.currentTimeMillis();
				// System.out.println("end:"+end);
				System.out.println("����" + tableName + "����ִ����" + count
						+ "Insert����������ʱ��" + (end - start) / 1000f + "�룡");
				String total = String.valueOf(((end - start) / 1000f));

				try {
					logId = insertLogInfo(map, startTime, endTime, total,
							String.valueOf(count), return_code);// ������־��
				} catch (Exception e) {
					return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
					throw new TxnDataException("�ɼ���־��¼ʧ��", "�ɼ���־��¼ʧ��!");
				} finally {
					creatLogFile(map, filepath, startTime, endTime,
							return_code, userName, logId, logFile, columnNames,
							colTable_ColumnInfo, "txt", speator);// ������־�ļ�
				}

			}
		}
		if (return_code.equals("BAIC0000")) {
			return_code = CollectConstants.COLLECT_STATUS_SUCCESS;// �ɼ��ɹ�
		} else {
			return_code = CollectConstants.COLLECT_STATUS_FAIL;// �ɼ�ʧ��
		}
		return return_code;

	}

	/**
	 * 
	 * analyTxtData3(����txt�����ļ�) 
	 * 
	 * @param filePathAndName
	 *            :���������ļ�ȫ·��
	 * @param tableName
	 *            ���ɼ�������
	 * @param columnNames
	 *            ���ɼ����ֶ�����
	 * @param collect_mode
	 *            ���ɼ���ʽ
	 * @param speator
	 *            ���ɼ��ļ�ÿ�����ݵķָ���
	 * @param checkResultFilePath1
	 *            ����������ļ����ȫ·��
	 * @return String
	 * @throws Exception
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public String analyTxtData3(HashMap map, String filepath, String tableName,
			HashMap colums, String titletype, String collect_mode,
			String speator, String checkResultFilePath1, String userName,
			String logFile, StringBuffer colTable_ColumnInfo) throws Exception
	{
		Long start = System.currentTimeMillis();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
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
				// System.out.println("�쳣����������������");
				return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
				throw new TxnDataException("�ɼ�����ʧ��", "���ݸ�ʽ����!");
			} finally {
				String endTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
				Long end = System.currentTimeMillis();
				// System.out.println("end:"+end);
				logger.info("����" + tableName + "����ִ����" + count
						+ "Insert����������ʱ��" + (end - start) / 1000f + "�룡");
				String total = String.valueOf(((end - start) / 1000f));

				try {
					// д��ɼ���־
					logId = insertLogInfo(map, startTime, endTime, total,
							String.valueOf(count), return_code);// ������־��
				} catch (Exception e) {
					return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
					throw new TxnDataException("�ɼ���־��¼ʧ��", "�ɼ���־��¼ʧ��!");
				} finally {
					creatLogFile(map, filepath, startTime, endTime,
							return_code, userName, logId, logFile, colums,
							colTable_ColumnInfo, "txt", speator);// ������־�ļ�
				}

			}
		}
		if (return_code.equals("BAIC0000")) {
			return_code = CollectConstants.COLLECT_STATUS_SUCCESS;// �ɼ��ɹ�
		} else {
			return_code = CollectConstants.COLLECT_STATUS_FAIL;// �ɼ�ʧ��
		}
		return return_code;

	}
	/**
	 * 
	 * analyTxtData3New(����txt�����ļ�) 
	 * 
	 * @param filePathAndName
	 *            :���������ļ�ȫ·��
	 * @param tableName
	 *            ���ɼ�������
	 * @param columnNames
	 *            ���ɼ����ֶ�����
	 * @param collect_mode
	 *            ���ɼ���ʽ
	 * @param speator
	 *            ���ɼ��ļ�ÿ�����ݵķָ���
	 * @param checkResultFilePath1
	 *            ����������ļ����ȫ·��
	 * @return String ��־��ɼ�״̬
	 * @throws Exception
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public String analyTxtData3New(HashMap map, String filepath, String tableName,
			HashMap colums, String titletype, String collect_mode,
			String speator, String checkResultFilePath1, String userName,
			String logFile, StringBuffer colTable_ColumnInfo) throws Exception
	{
		Long start = System.currentTimeMillis();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
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
			//�����ļ�
			preStatement = analyTxt2New(filepath, tableName, colums, 
					titletype,collect_mode, speator, 
					checkResultFilePath1,conn);
			
			//ִ����������
			Long insert_start = System.currentTimeMillis();
			int[] countList=preStatement.executeBatch();
			conn.commit();
			count=countList.length;
			String consumeTime = String.valueOf(((System.currentTimeMillis()- insert_start) / 1000f));
			logger.info("����" + tableName + "����ִ��" + count
					+ "��Insert��������ʱ��" + consumeTime + "�룡");
			
			
			if (count == -1) {
				return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
				count = 0;
			} else {
				return_code = CollectConstants.CLIENT_FHDM_SUCCESS;
			}
		} catch (BatchUpdateException e) {
			int[] c = e.getUpdateCounts();
			
			logger.debug("���������Ϊ�ڼ���..." +  c.length);
			//logger.error("�������Ϊ:" + sqls[c.length]);
			
			e.printStackTrace();
		} catch (Exception e) {
			return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
			throw new TxnDataException("�ɼ�����ʧ��", "���ݸ�ʽ����!");
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
		
		//��������ִ�к�ʱ
		Long end = System.currentTimeMillis();
		String endTime = sDateFormat.format(new java.util.Date()); 
		String total = String.valueOf(((end- start) / 1000f));
		

		try {
			// д��ɼ���־
			logId = insertLogInfo(map, startTime, endTime,total,
					String.valueOf(count), return_code);// ������־��
		} catch (Exception e) {
			return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
			throw new TxnDataException("�ɼ���־��¼ʧ��", "�ɼ���־��¼ʧ��!");
		} finally {
			creatLogFile(map, filepath, startTime, endTime,
					return_code, userName, logId, logFile, colums,
					colTable_ColumnInfo, "txt", speator);// ������־�ļ�
		}

		
		//���ݷ��ؽ����ֵ�ɼ�״̬
		if (return_code.equals("BAIC0000")) {
			return_code = CollectConstants.COLLECT_STATUS_SUCCESS;// �ɼ��ɹ�
		} else {
			return_code = CollectConstants.COLLECT_STATUS_FAIL;// �ɼ�ʧ��
		}
		return return_code;

	}

	/*
	 * public static List<Map> analyMdbData(){
	 * 
	 * List<Map> maplist = new ArrayList(); Properties prop = new Properties();
	 * prop.put("charSet", "gb2312");//����������� prop.put("user", "aaa");
	 * prop.put("password", "aaa");
	 * 
	 * String url = "jdbc:odbc:driver={Microsoft Access
	 * Driver(*.mdb)};DBQ=d:\\access.mdb";//�ļ���ַ PreparedStatement ps = null;
	 * Statement stmt = null; ResultSet rs = null;
	 * 
	 * try { Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); try { Connection
	 * conn = DriverManager.getConnection(url,prop); stmt =
	 * (Statement)conn.createStatement(); rs = stmt.executeQuery("select * from
	 * department"); ResultSetMetaData data = rs.getMetaData();
	 * while(rs.next()){ Map map = new HashMap(); for(int
	 * i=0;i<data.getColumnCount();i++){ String columnName =
	 * data.getColumnClassName(i);//���� String columnValue = rs.getString(i);
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
	 * writeFile(�����ļ�������ָ������д���ļ�) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C
	 * ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param filePathAndName
	 * @param fileContent
	 *            void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void writeFile(String filePathAndName, String fileContent)
	{
		try {

			String path = filePathAndName.substring(0,
					filePathAndName.lastIndexOf(File.separator) + 1);
			// System.out.println(path);
			File f = new File(path);
			// System.out.println(filePathAndName);
			// �����ļ���
			if (!f.exists()) {
				f.mkdirs();
				logger.info("ָ��·�������ڣ������ļ���path=" + path);
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
			System.out.println("д�ļ����ݲ�������");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * appendMethodB(׷�ӷ�ʽд���ļ�����) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C
	 * ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param fileName
	 * @param content
	 *            void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void appendContext(String fileName, String content)
	{
		/*
		 * try { // ��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ� //FileWriter writer = new
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
			// �����ļ���
			if (!f.exists()) {
				f.mkdirs();
				logger.info("ָ��·�������ڣ������ļ���path=" + path);
			}
			f = new File(fileName);
			if (!f.exists()) {
				f.createNewFile();
			}
			// String fileEncode = System.getProperty("file.encoding");
			// System.out.println("%%%%ϵͳĬ�ϵ�fileEncode��="+fileEncode);

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
	 * collectDatabase(������һ�仰�����������������) TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C
	 * ��ѡ)
	 * 
	 * @param map
	 * @param filepath
	 * @param tableName
	 * @param desccolumnNames
	 *            ���������Ĳɼ����вɼ����������
	 * @param collect_mode
	 * @param sourceColumnName
	 *            :Դ�ɼ����вɼ������������Ϣ
	 * @param userName
	 * @param logFile
	 * @param colTable_ColumnInfo
	 * @return
	 * @throws TxnDataException
	 *             String
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
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
		String startTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
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
				throw new TxnDataException("�ɼ�����ʧ��", "���ݸ�ʽ����!");
			} finally {
				String endTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
				Long end = System.currentTimeMillis();
				System.out.println("end:" + end);
				System.out.println("����" + tableName + "����ִ����" + count
						+ "Insert����������ʱ��" + (end - start) / 1000f + "�룡");
				String total = String.valueOf(((end - start) / 1000f));
				try {
					logId = insertLogInfo(map, startTime, endTime, total,
							String.valueOf(count), return_code);// ������־��
				} catch (Exception e) {
					return_code = CollectConstants.CLIENT_FHDM_SQL_ERROR;
					throw new TxnDataException("�ɼ���־��¼ʧ��", "�ɼ���־��¼ʧ��!");
				} finally {
					creatDatabaseLogFile(db_type, souColumnsType, map,
							desccolumnNames, startTime, endTime, return_code,
							userName, logId, logFile, sourceColumnName,
							colTable_ColumnInfo);// ������־�ļ�

				}
			}
		}
		if (return_code.equals("BAIC0000") || "".equals(return_code)) {
			return_code = CollectConstants.COLLECT_STATUS_SUCCESS;// �ɼ��ɹ�
		} else {
			return_code = CollectConstants.COLLECT_STATUS_FAIL;// �ɼ�ʧ��
		}
		return return_code;

	}

	/**
	 * 
	 * creatDatabaseLogFile(�������ݿ����־�ļ�) TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C
	 * ��ѡ)
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
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
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
		// logStr.append("�û���"+user+"�����ã�����ʱ��Ρ�"+startTime+"��"+endTime+"��,");
		String collect_mode = (String) map.get("COLLECT_MODE");
		String mode = "";
		if (collect_mode.equals("2")) {
			mode = "ȫ��";
		} else if (collect_mode.equals("1")) {
			mode = "����";
		} else {
			mode = "��";
		}
		// logStr.append("�û���"+user+"�����ã�����ʱ��Ρ�"+startTime+"��"+endTime+"��,");
		logStr.append("ʱ���:" + sTime + "��" + eTime + "\r\n");
		logStr.append("�û�:" + userName + "\r\n");

		if (colTable_ColumnInfo.toString() != null) {
			logStr.append("Դ������:" + colTable_ColumnInfo.toString() + "\r\n");
		}

		/** ****Ŀ��������Ͳɼ�������***** */

		String[] dataStr = getDatabaseDesDataItem(db_type, sourceColumnType,
				sourceColumns, columnNames);
		logStr.append("Ŀ��������:" + dataStr[0] + "\r\n");
		logStr.append("�ɼ�������:" + dataStr[1] + "\r\n");

		StringBuffer querySql = new StringBuffer(
				"select collect_table_name,collect_data_amount,task_consume_time from collect_joumal where 1=1  ");
		if (logId != null && !"".equals(logId)) {
			querySql.append(" and  collect_joumal_id ='" + logId + "'");
		}
		System.out.println("��ѯ�ɼ���־sql===" + querySql);

		Map taskMap = null;
		ServiceDAO daoTable = new ServiceDAOImpl(); // �������ݱ�Dao
		try {
			taskMap = daoTable.queryService(querySql.toString());
			String count = (String) taskMap.get("COLLECT_DATA_AMOUNT");
			System.out.println("count===" + count);
			if (count != null && count.equals("-1")) {
				count = "0";
			}

			logStr.append("�ɼ���:" + taskMap.get("COLLECT_TABLE_NAME") + "\r\n");
			logStr.append("�ɼ���ʽ:" + mode + "\r\n");
			logStr.append("�ɼ�������:" + count + "��\r\n");
			logStr.append("�ܺ�ʱ:" + taskMap.get("TASK_CONSUME_TIME") + "��\r\n");

			System.out.println("�ɼ���" + taskMap.get("COLLECT_TABLE_NAME")
					+ "���ܹ��ɼ��ˡ�" + count + "�������ݣ��ܺ�ʱΪ��"
					+ taskMap.get("TASK_CONSUME_TIME") + "����!\r\n");

			if (return_code.equals("BAIC0000")) {
				logStr.append("ִ�н��:�ɹ�\r\n");
				logStr.append("����ԭ��:��\r\n");
			} else {
				logStr.append("ִ�н��:ʧ��\r\n");
				if (return_code.equals("CODE0002")) {
					logStr.append("����ԭ��:���ݳ��ȹ�������������ͻ\r\n");
				}
			}
			logStr.append("--------------------------------------------------------\r\n");
			AnalyCollectFile acf = new AnalyCollectFile();
			acf.appendContext(logFile, logStr.toString());
		} catch (Exception e) {
			throw new TxnDataException("error", "��ѯ��־��collect_joumalʧ��!");

		}

	}

	/**
	 * 
	 * getDesDataItem(��ȡ���ݿ���Դ�ɼ����ʵ�ʱ��ɼ���������) TODO(����������������������� �C ��ѡ)
	 * TODO(�����������������ִ������ �C ��ѡ) TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C
	 * ��ѡ)
	 * 
	 * @param souColumns
	 *            :Դ�ɼ�������
	 * @param desColumns
	 *            :ʵ�ʱ��ɼ���������
	 * @return String[] String[0]:��Ųɼ��ļ��������String[1]:���ʵ�ʱ��ɼ���������
	 * @Exception �쳣����
	 * @since CodingExample��Ver(���뷶���鿴) 1.1
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
		System.out.println("�ɼ��ļ���������==" + itemInfo[0]);
		System.out.println("ʵ�ʱ��ɼ���������==" + itemInfo[1]);
		return itemInfo;

	}

	/**
	 * main(������һ�仰�����������������) TODO(����������������������� �C ��ѡ) TODO(�����������������ִ������ �C ��ѡ)
	 * TODO(�����������������ʹ�÷��� �C ��ѡ) TODO(�����������������ע������ �C ��ѡ)
	 * 
	 * @param args
	 *            void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
		 * ac.analyTxtData("D:\\REG_BUS_ENT_10����111.txt","REG_BUS_ENT_DWN_TEST"
		 * ,cols,"2","\\|","d:/cr.txt");
		 */
		/*
		 * String str =
		 * "D:/datafiles/file_upload/2013/2013-05/2013-05-07/00000000.xls";
		 * System.out.println("�ļ���׺��"+str.substring(str.lastIndexOf("/")+1));
		 * System.out.println("�ļ���׺1��"+str.substring(0,str.lastIndexOf(".")));
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
		 * try { loadFile("D:\\REG_BUS_ENT���±��ɼ��ļ�����.txt"); } catch (IOException
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
		 * IllegalArgumentException("��������Ϊ��!"); }
		 */
		AnalyCollectFile ac = new AnalyCollectFile();
		String fileName = "E:/logFile/t2.txt";
		ac.appendContext(fileName, "");

		/*
		 * String fileName = "E:/logFile/t2.xls"; try { // ���ļ� WritableWorkbook
		 * book = Workbook.createWorkbook( new File(fileName)); //
		 * ������Ϊ����һҳ���Ĺ���������0��ʾ���ǵ�һҳ WritableSheet sheet = book.createSheet( "
		 * ��һҳ " , 0 ); // ��Label����Ĺ�������ָ����Ԫ��λ���ǵ�һ�е�һ��(0,0) // �Լ���Ԫ������Ϊtest
		 * Label label = new Label( 0 , 0 , " test " ); // ������õĵ�Ԫ����ӵ���������
		 * sheet.addCell(label); jxl.write.Number number = new jxl.write.Number(
		 * 1 , 0 , 555.12541 ); sheet.addCell(number); // д�����ݲ��ر��ļ�
		 * book.write(); book.close(); } catch (Exception e) {
		 * System.out.println(e); }
		 */

	}

	/**
	 * �ж��ļ��ı����ʽ �粻ʶ��Ĭ��ΪGBK
	 * 
	 * @param fileName
	 *            :file
	 * @return �ļ������ʽ
	 * @throws Exception
	 */
	public String codeString(String fileName) throws Exception
	{
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
				fileName));
		int p = (bin.read() << 8) + bin.read();
		System.out.println("�ļ�ͷ=" + p);
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
	 * analyTxt2New(����txt�ļ�) 
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
		
		StringBuffer checkResult = new StringBuffer();//����У����
		String[] colNames;
		String[] colValues;
		if (speator == null || "".equals(speator)) {
			speator = CollectConstants.COLLECT_FILE_SPEARTOR;
		}

		//List<String> sqlList = new ArrayList();
		StringBuffer insertSQL=new StringBuffer();
		
		PreparedStatement preStatement =null; 
		
		//����ɼ���ʽ��ȫ��������ձ�������
		if (CollectConstants.COLLECT_MODE_ALL.equals(collect_mode)) {
			String delSql = "delete from " + tableName;
			execDelSql(delSql);

		}
		/**��ȡ�ļ�����**/
		
		File f = new File(filePathAndName);
		if (f.isFile() && f.exists()) {
			
			try {
				long start = System.currentTimeMillis();//�ļ�������ʱ
				//�ж��ļ������ʽ
				//String filecode = codeString(filePathAndName);
				String filecode = "UTF-8";
				logger.info("�ļ������ʽΪ:" + filecode);
				FileInputStream read = new FileInputStream(f);
				BufferedReader reader = new BufferedReader(new UnicodeReader(
						read, filecode/* Charset.defaultCharset().name() */));

				String line;
				
				try {
					
					line = reader.readLine();
					logger.info("����line=" + line);
					StringBuffer placeholder = new StringBuffer();//�ʺ�ռλ��
					if (line != null) {
						// ��һ�������Ĵ���
						insertSQL.append("insert into ").append(tableName).append("(");
						colNames = line.split(speator, -1);
						// checkTitleLine(colNames,keyMap,columnCN,checkResult);
						for (int i = 0; i < colNames.length; i++) {
							//�ж�������Ч��
							if (!(columnCN.containsValue(colNames[i]
									.toUpperCase()) || columnCN
									.containsKey(colNames[i].toUpperCase()))) {

								checkResult.append("����[" + colNames[i]
										+ "]���ǲɼ����е��ֶ�\r\n");
							}else{//��Ч����
								if ("EN".equals(titleType)) {
									//������Ӣ��ֱ��������װSQL���
									insertSQL.append(colNames[i]).append(",");
									placeholder.append("?,");
								} else {
									//�������������������Map��ȡ��Ӧ��Ӣ������������װSQL���
									insertSQL.append(columnCN.get(colNames[i])).append(",");
									placeholder.append("?,");
								}
								
							}
						}
					
						//���ϸ���ʱ����
						insertSQL.append("UPDATE_DATE").append(") values(")
							.append(placeholder).append("?)");
						System.out.println(insertSQL.toString());
						preStatement = conn.prepareStatement(insertSQL.toString());						
						// ���ݴ���
						line = reader.readLine();
						while (line != null && line != "") {
							colValues = line.split(speator, -1);
							int j=1;
							for (int i = 0; i < colValues.length; i++) {

								if (columnCN.containsValue(colNames[i]
										.toUpperCase())
										|| columnCN.containsKey(colNames[i]
												.toUpperCase())) {
									// ���������ڲɼ�����
									preStatement.setString(j,colValues[i] );
									j++;
								}
							}
							//����ʱ��Ϊ����
							SimpleDateFormat sDateFormat = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							String updateDate = sDateFormat.format(new java.util.Date());
							preStatement.setString(j, updateDate);
							preStatement.addBatch();
							
							line = reader.readLine();
						}

					}else{
						logger.error("�ļ�Ϊ��");
					}
					long end = System.currentTimeMillis();
					String consumeTime = String
							.valueOf(((end - start) / 1000f));
					logger.debug("-----�ļ���ȡ������ʱ��" + consumeTime);
					if (checkResult.toString() == null
							|| "".equals(checkResult.toString())) {
						checkResult.append("�ļ��е�ȫ���ֶξ��Ϸ�!");
					}
					writeFile(checkResultFilePath1, checkResult.toString());
					logger.info("����У���ļ�");
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
