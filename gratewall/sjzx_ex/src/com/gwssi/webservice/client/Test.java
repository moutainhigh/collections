package com.gwssi.webservice.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;
import com.gwssi.common.util.XmlToMapUtil;

public class Test
{

	public static void main(String[] args)
	{

	}

	public static int getDate()
	{
		Long start = System.currentTimeMillis();
		System.out.println("��ʼ�����������Է���...");
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
		System.out.println("��ǰʱ��Ϊ��" + startTime + "��ʼ�ɼ�����...");
		String targetEendPoint = "http://172.24.27.26:8011/BJTAX-PS-OUTER/GONGSHANG/PS/GsSjjhPS?wsdl";// ·��
		System.out.println("����·��Ϊ..." + targetEendPoint);
		String qName = "getSWDJ_QUERY";

		System.out.println("���ʷ���Ϊ..." + qName);
		Map paraMap = new HashMap();
		// *��¼ϵͳ���û���
		paraMap.put("LOGIN_NAME", "GongShang");
		// *����
		paraMap.put("PASSWORD", "11111111");
		// *��ʼ��¼��
//		paraMap.put("KSJLS", "1");
//		paraMap.put("JSJLS", "5000");
		// *������¼��(��ʼĬ��Ϊ5000��)
		paraMap.put("KSJLS", "5001");
		paraMap.put("JSJLS", "10000");
		// *�������(�ɹ��̾��ṩ)CXRQQ
		paraMap.put("CXRQQ", "20131202");
		paraMap.put("CXRQZ", "20131203");

		String nameSpace = "urn:GsWebServiceEjb";
		String paramName = "string";

		String param = XmlToMapUtil.map2Dom(paraMap);
		System.out.println("����Ϊ��" + param);
		Service service = new Service();
		int count = 0;
		try {
			Call call = (Call) service.createCall();
			call.setTimeout(300000);
			System.out.println("���ó�ʱʱ��Ϊ :" + 300000 / 1000f + "��");
			call.setTargetEndpointAddress(new URL(targetEendPoint));
			call.setOperationName(new QName(nameSpace, qName));
			call.addParameter(new QName(nameSpace, paramName),
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);
			call.setReturnType(org.apache.axis.Constants.XSD_STRING);
			call.setUseSOAPAction(true);
			String result = (String) call.invoke(new Object[] { param });
			// inputFile(result);
			count = getSql(result);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		} finally {
			Long end = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
			String consumeTime = String.valueOf(((end - start) / 1000f));
			return count;
		}
	}

	public static int getSql(String dom)
	{
		int count = 0;
		StringBuffer filecontext = new StringBuffer();
		String tableName = "TAX_REG_INFO";
		List columnList = new ArrayList();
		columnList.add("JSJDM");
		columnList.add("NSRMC");
		columnList.add("DJSLLX");
		columnList.add("SWDJLX");
		columnList.add("ZZJGDM");
		columnList.add("YYZZH");
		columnList.add("JYDZ");
		columnList.add("JYDZYB");
		columnList.add("JYDZLXDH");
		columnList.add("QYZY");
		columnList.add("ZCZBBZDM");
		columnList.add("SWDJZH");
		columnList.add("SCJXDM");
		columnList.add("DJZCLXDM");
		columnList.add("LSGXDM");
		columnList.add("GJBZHYDM");
		columnList.add("KYDJRQ");
		columnList.add("NSRZT");
		columnList.add("SWJGZZJGDM");
		columnList.add("XM");
		columnList.add("ZJLXDM");
		columnList.add("ZJHM");
		columnList.add("GDSGGHBS");
		columnList.add("PZCLJGDM");
		Map domMap = XmlToMapUtil.dom2Map(dom);

		Map mm = new HashMap();
		List dataList = new ArrayList();
		if (!domMap.isEmpty()) {
			if (domMap.get("data") != null && !"".equals(domMap.get("data"))) {
				mm = (Map) domMap.get("data");
				if (null != mm.get("row") && !"".equals(mm.get("row"))) {
					if (mm.get("row") instanceof Map) {
						if (!mm.isEmpty())
							dataList.add(mm.get("row"));
					} else {
						dataList = (List) mm.get("row");
					}
				}
			}
		}
		Map m = new HashMap();
		StringBuffer insertSql = new StringBuffer();
		StringBuffer columnSql = new StringBuffer();
		insertSql.append("INSERT INTO ");
		insertSql.append(tableName);
		insertSql.append(" (");
		for (int i = 0; i < columnList.size(); i++) {
			insertSql.append(columnList.get(i));
			columnSql.append(columnList.get(i));
			if (i < columnList.size() - 1) {
				insertSql.append(",");
				columnSql.append(",");
			} else if (i == columnList.size() - 1) {
				insertSql.append(") VALUES (");
				columnSql.append("");
			}
		}

		Connection conn;
		try {
			conn = DbUtils.getConnection("5");
			Statement st = null;
			conn.setAutoCommit(false);

			st = conn.createStatement();
			for (int i = 0; i < dataList.size(); i++) {
				m = (Map) dataList.get(i);
				StringBuffer sql = insertCollectData(insertSql, columnList, m);
				st.addBatch(sql.toString());

			}
			int[] countList = st.executeBatch();
			conn.commit(); // �ύ����
			count = countList.length; // ��������
			System.out.println("��������Ϊ ��" + count);
		} catch (DBException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;

		// �����ļ�
		// AnalyCollectFile ac = new AnalyCollectFile();
		// ac.writeFile("D:\\20131101.txt", filecontext.toString());

		// String zts = domMap.get("ZTS").toString();
		// System.out.println("������Ϊ ��"+ zts);
	}

	public synchronized static StringBuffer insertCollectData(
			StringBuffer insertSql, List columnList, Map dataMap)
	{
		// ���ݸ���ʱ��
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String update_date = sDateFormat.format(new java.util.Date());

		StringBuffer valuesSql = new StringBuffer();
		valuesSql.append(insertSql);
		for (int j = 0; j < columnList.size(); j++) {
			if ("UPDATE_DATE".equals(columnList.get(j))) {
				valuesSql.append("'");
				valuesSql.append(update_date);
				valuesSql.append("'");
			} else {
				valuesSql.append("'");
				if (null != dataMap.get(columnList.get(j))
						&& !""
								.equals(dataMap.get(columnList.get(j))
										.toString()))
					valuesSql.append(dataMap.get(columnList.get(j)));
				else
					valuesSql.append("");
				valuesSql.append("'");
			}
			if (j < columnList.size() - 1)
				valuesSql.append(",");
			else if (j == columnList.size() - 1)
				valuesSql.append(")");
		}
		return valuesSql;
	}

	private static void inputFile(String dom) throws IOException
	{
		java.sql.Timestamp dateForFileName = new java.sql.Timestamp(new Date()
				.getTime());
		System.out.println("��ʼ�洢xml�ļ�...");
		StringBuffer path = new StringBuffer();
		path.append("D:\\temp1.xml");
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter output = null;
		try {
			Document doc = DocumentHelper.parseText(dom);
			output = new XMLWriter(new FileOutputStream(new File(path
					.toString())), format);
			output.write(doc);
			System.out.println("�洢xml�ļ����...");
			System.out.println("xml�ļ��洢·��Ϊ��" + path);
		} catch (DocumentException e) {
			if (null != output)
				output.close();
			System.out.println("DocumentException:" + e);
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			if (null != output)
				output.close();
			System.out.println("UnsupportedEncodingException:" + e);
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			if (null != output)
				output.close();
			System.out.println("FileNotFoundException:" + e);
			e.printStackTrace();
		} catch (IOException e1) {
			if (null != output)
				output.close();
			System.out.println("IOException:" + e1);
			e1.printStackTrace();
		} finally {
			if (null != output)
				output.close();
		}
	}

}
