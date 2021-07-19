package com.gwssi.webservice.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.collect.webservice.param.vo.VoCollectWsParamValue;
import com.gwssi.collect.webservice.vo.VoCollectWebservicePatameter;
import com.gwssi.collect.webservice.vo.VoCollectWebserviceTask;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.util.ParamUtil;
import com.gwssi.log.collectlog.dao.CollectLogVo;
import com.gwssi.webservice.client.collect.DataHandleFactory;
import com.gwssi.webservice.client.collect.DataHandleInterface;
import com.srit.encryption.AES;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�QsWsClient ���������ʼ�ɼ��ӿڿͻ��� �����ˣ�lizheng ����ʱ�䣺Aug 27, 2013
 * 4:53:57 PM �޸��ˣ�lizheng �޸�ʱ�䣺Aug 27, 2013 4:53:57 PM �޸ı�ע��
 * 
 * @version
 * 
 */
public class QsWsClient
{

	protected static Logger	logger		= TxnLogger.getLogger(QsWsClient.class
												.getName());	// ��־

	static TaskInfo			taskInfo	= new TaskInfo();		// ������Ϣ

	/**
	 * 
	 * qualitySupervisionCollecting �ɼ��ʼ�������
	 * 
	 * @param beforeMap
	 * @param collectLogVo
	 *            void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public void qualitySupervisionCollecting(Map beforeMap,
			CollectLogVo collectLogVo)
	{
		List webserviceTask = (List) beforeMap.get("METHOD_LIST"); // ��ȡ�ɼ������б�
		beforeMap.remove("METHOD_LIST");

		// �������������ʶ�Ƿ���ڶ��ȡֵ�������ʼ���ʱû��������������������ű��Ժ�ʹ��
		Map countMap = new HashMap();
		countMap.put("isDo", "N");

		for (int i = 0; i < webserviceTask.size(); i++) {
			Map wsTask = (Map) webserviceTask.get(i);
			doGetData(beforeMap, wsTask, collectLogVo, countMap); // ִ�вɼ�����
		}
	}

	/**
	 * 
	 * doGetData �ɼ��ʼ�����
	 * 
	 * @param beforeMap
	 * @param webserviceTask
	 * @param collectLogVo
	 * @param countMap
	 *            void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected void doGetData(Map beforeMap, Map webserviceTask,
			CollectLogVo collectLogVo, Map countMap)
	{
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Long start = System.currentTimeMillis(); // ��ʼʱ�����ڼ����ʱ
		String accessUrl = beforeMap.get(CollectConstants.WSDL_URL).toString();

		Map wsTask = webserviceTask;
		VoCollectWebserviceTask wsTaskVo = new VoCollectWebserviceTask();
		ParamUtil.mapToBean(wsTask, wsTaskVo, false);

		String wsTaskId = wsTaskVo.getWebservice_task_id();
		String qName = wsTaskVo.getMethod_name_en();
		String qNameCn = wsTaskVo.getMethod_name_cn();
		String tableId = wsTaskVo.getCollect_table();
		String collectMode = wsTaskVo.getCollect_mode();
		String srvCode = wsTaskVo.getService_no();
		String nameSpace = wsTaskVo.getWEB_name_space();
		logger.debug("���ݲɼ���ʽΪ��" + collectMode);
		logger.debug("��ǰ���÷��񷽷�Ϊ��" + qName);

		// ��־ws����
		collectLogVo.setTask_id(wsTaskId);
		collectLogVo.setService_no(wsTaskVo.getService_no());
		collectLogVo.setMethod_name_en(qName);
		collectLogVo.setMethod_name_cn(qNameCn);
		collectLogVo.setCollect_mode(collectMode);
		collectLogVo.setCollect_table(tableId);

		try {
			// ���ݷ���ID��ѯ�����Ĳ���
			logger.debug("��ʼ��ѯ���񷽷�����...");
			List paramList = taskInfo.queryParam(wsTaskId);
			logger.debug("��ѯ���񷽷��������...");
			
			Map paramValueMap = fromatParamValue(paramList);
			Map paraMap = new HashMap();
			paraMap.put(CollectConstants.WSDL_URL, accessUrl); // ����·��
			paraMap.put(CollectConstants.QNAME, qName); // ��������
			paraMap.put(CollectConstants.WEB_NAME_SPACE, nameSpace); // �����ռ䡢
			paraMap.put(CollectConstants.PARAM_LIST, paramValueMap
					.get(CollectConstants.PARAM_LIST)); // ������
			paraMap.put(CollectConstants.PARAM_VALUE, paramValueMap
					.get(CollectConstants.PARAM_VALUE)); // ����ֵ
			logger.debug("beforeMap is " + beforeMap);

			logger.debug("��ʽ���������,����Ϊ..." + paraMap);
			Long star2 = System.currentTimeMillis();

			// ���ýӿ�
			GSGeneralClient gs = new GSGeneralClient();
			Map result = gs
					.collectQualitySupervisionData(paraMap, collectLogVo);
			logger.debug("result is " + result);
			Long end2 = System.currentTimeMillis();
			String consumeTime2 = String.valueOf(((end2 - star2) / 1000f));
			collectLogVo.setInvoke_consume_time(consumeTime2);

			logger.debug("��ʼ�����õķ��ؽ��...");
			Map resultMap = new HashMap();
			resultMap = analyzeResult(result, collectLogVo);
			boolean flag = Boolean.valueOf(resultMap.get("status").toString());
			logger.debug("flag is " + flag);
			if (flag) {
				List resultList = (List) resultMap.get("RESULT");
				DataHandleInterface dataHandle = DataHandleFactory
						.getDataHandle();
				int count = dataHandle.insertDataForQs(tableId, collectMode,
						resultList, collectLogVo);
				collectLogVo.setCollect_data_amount(String.valueOf(count));
			} else {
				collectLogVo.setCollect_data_amount("0");
			}

			String endTime = sDateFormat.format(new java.util.Date());// ϵͳ��ǰ������ʱ����
			collectLogVo.setTask_end_time(endTime);
		} catch (DBException e) {
			e.printStackTrace();
		} finally {
			Long end1 = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
			String consumeTime1 = String.valueOf(((end1 - start) / 1000f));
			collectLogVo.setTask_consume_time(consumeTime1);
			logger.debug("�ɼ����ݹ���ʱ��" + consumeTime1 + "��");

			logger.debug("��ʼ��¼��־...");
			taskInfo.insertLog(collectLogVo);
			logger.debug("��¼��־�ɹ�...");
			// �ݹ��ڴ˷������ж��Ƿ����������ڽ�����¼��,������������ڽ�����¼����Ҫ�ٴ�ȡ���ݣ�
			// ÿ��ȡ���ݵĿ�ʼ��¼��Ϊ�ϴν�����¼��+1��ÿ��ȡ���ݵĽ�����¼��Ϊ�ϴεĽ�����¼��+ÿ��ȡ������
			// ÿ��ȡ���ݵĿ�ʼ��¼���ͽ�����¼�������ڱ���countMap��
		}

	}

	/**
	 * 
	 * dom2String documentת���ַ���
	 * 
	 * @param document
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public static String dom2String(Document document)
	{
		return document.asXML();
	}

	/**
	 * 
	 * fromatParamValue �����ʼ��ṩ�Ĳ�����ʽ�������
	 * 
	 * @param paramList
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected Map fromatParamValue(List paramList)
	{
		Map paraMap = new HashMap();
		Document document = DocumentHelper.createDocument();
		Element ws = document.addElement("ws");
		Element heads = ws.addElement("heads");
		Element bodys = ws.addElement("bodys");
		Element parameters = bodys.addElement("parameters");
		if (paramList.size() > 0) {
			Map parMap = (Map) paramList.get(0);
			VoCollectWebservicePatameter vo = new VoCollectWebservicePatameter();
			ParamUtil.mapToBean(parMap, vo, false);
			String paramid = vo.getWebservice_patameter_id();
			String name = vo.getPatameter_name();
			String style = vo.getPatameter_style();
			paraMap.put(CollectConstants.PARAM_LIST, name);
			Map valueMap = new HashMap();
			List paramValueList = taskInfo.queyrParamValue(paramid);
			for (int i = 0; i < paramValueList.size(); i++) {
				Map paramValueMap = (Map) paramValueList.get(i);
				VoCollectWsParamValue valueVo = new VoCollectWsParamValue();
				ParamUtil.mapToBean(paramValueMap, valueVo, false);
				if ("identifier".equals(valueVo.getPatameter_name())
						|| "servicecode".equals(valueVo.getPatameter_name())) {
					Element head = heads.addElement("head");
					head.addAttribute("name", valueVo.getPatameter_name());
					head.addAttribute("value", valueVo.getPatameter_value());
				}
				if ("3814-3815".equals(valueVo.getPatameter_name())) {
					Element parameter = parameters.addElement("parameter");
					parameter.addAttribute("name", valueVo.getPatameter_name());
					parameter.addAttribute("title", "���ʱ��");
					parameter.addAttribute("type", "DATE");
					// �������ڵĸ�ʽ
					String value = valueVo.getPatameter_value();
					parameter.addAttribute("value", getTime(value));
				}
			}
		}
		String dom = dom2String(document);
		logger.debug("����ֵΪ��" + dom);
		paraMap.put(CollectConstants.PARAM_VALUE, dom);
		return paraMap;
	}

	/**
	 * 
	 * analyzeResult �����ʼ�ӿڷ��ؽ��
	 * 
	 * @param result
	 * @param collectLogVo
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	private static Map analyzeResult(Map result, CollectLogVo collectLogVo)
	{
		logger.debug("��ʼ�������ؽ��");
		Map resultMap = new HashMap();
		resultMap.put("status", false);
		if (null != result) {
			String returnCode = result.get(
					CollectConstants.CLIENT_COLLECT_PARAM_FHDM).toString();
			if (returnCode.equals(CollectConstants.CLIENT_FHDM_SUCCESS)) {
				String outputString = result.get("RESULT").toString();
				try {
					String a = AES
							.Srit_Decrypt(
									"HsaKd5mQX3r/YNm9LwTHoc80K9Xh8bGSo9NTGfLYFr5qzLQhXXKsQbmh6f4Bf/mt",
									"73DAB975685258BC", outputString);
					List resultList = getAllNodes(a);
					resultMap.put("status", true);
					resultMap.put("RESULT", resultList);
				} catch (Exception e) {
					logger.debug("analyzeResult ����" + e);
					e.printStackTrace();
				}
			}
		} else {
			// ֵΪ��
			collectLogVo.setReturn_codes(CollectConstants.CLIENT_FHDM_FAIL);
		}
		logger.debug("resultMap is" + resultMap);
		return resultMap;
	}

	private static List getAllNodes(String xml)
	{
		List dataList = new ArrayList();
		try {
			Document authtmp = DocumentHelper.parseText(xml);
			List<Element> list = authtmp.selectNodes("//bodys/results/row");
			logger.debug("list size is " + list.size());
			for (int j = 0; j < list.size(); j++) {
				Map dataMap = new HashMap();
				Element node = (Element) list.get(j);
				nodeByNodes(node, dataMap);
				dataList.add(dataMap);
			}
		} catch (Exception e) {
			logger.debug("getAllNodes ����");
			e.printStackTrace();
		}
		return dataList;
	}

	/**
	 * 
	 * nodeByNodes ����dom
	 * 
	 * @param node
	 * @param dataMap
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	private static Map nodeByNodes(Element node, Map dataMap)
	{
		if (node.element("column") != null) {
			for (Iterator i = node.elementIterator("column"); i.hasNext();) {
				Element newNode = (Element) i.next();
				nodeByNodes(newNode, dataMap);
			}
		} else {
			dataMap.put(node.attributeValue("title"), node
					.attributeValue("value"));
		}
		return dataMap;
	}

	public String getTime(String value)
	{
		logger.debug("ʱ�����Ϊ��" + value);
		StringBuffer time = new StringBuffer();
		String[] a = value.split("@@");
		if (a.length > 1) {
			String start = a[0];
			int day1 = 0;
			if (start.contains("-")) {
				day1 = Integer.parseInt(start.substring(
						start.lastIndexOf("-") + 1, start.length()));
			}
			time.append(taskInfo.get03Time(day1));
			time.append(" 00:00:00");
			time.append("@@");
			String end = a[1];
			int day2 = 0;
			if (end.contains("-")) {
				day2 = Integer.parseInt(end.substring(end.lastIndexOf("-") + 1,
						end.length()));
			}
			time.append(taskInfo.get03Time(day2));
			time.append(" 23:59:59");
		}
		logger.debug("ʱ����Ϊ��" + time);
		return time.toString();
	}

	public static void main(String[] args)
	{
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read("C:\\Users\\lizheng\\Desktop\\temp.xml");
			String a = dom2String(doc);
			List dataList = getAllNodes(a);
			System.out.println("dataList size is " + dataList.size());
			for (int i = 0; i < dataList.size(); i++) {
				Map dataMap = (Map) dataList.get(i);
				System.out.println("dataMap is " + dataMap);
			}
		} catch (DocumentException e) {
			System.out.println("����");
			e.printStackTrace();
		}
	}
}
