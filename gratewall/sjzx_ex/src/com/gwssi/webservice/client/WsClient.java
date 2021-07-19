package com.gwssi.webservice.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.collect.webservice.vo.VoCollectTask;
import com.gwssi.collect.webservice.vo.VoCollectWebserviceTask;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.util.ParamUtil;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.common.util.XmlToMapUtil;
import com.gwssi.log.collectlog.dao.CollectLogVo;
import com.gwssi.resource.svrobj.vo.VoResDataSource;
import com.gwssi.resource.svrobj.vo.VoResServiceTargets;
import com.gwssi.webservice.client.collect.DataHandleFactory;
import com.gwssi.webservice.client.collect.DataHandleInterface;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�WsClient ��������web service�ͻ��� �����ˣ�lizheng ����ʱ�䣺Apr 10,
 * 2013 10:06:58 AM �޸��ˣ�lizheng �޸�ʱ�䣺Apr 10, 2013 10:06:58 AM �޸ı�ע��
 * 
 * @version
 * 
 */
public class WsClient
{
	protected static Logger	logger		= TxnLogger.getLogger(WsClient.class
												.getName());	// ��־

	static TaskInfo			taskInfo	= new TaskInfo();		// ������Ϣ

	/**
	 * 
	 * doCollectTask ִ��web service�ɼ�����
	 * 
	 * @param taskId
	 * @return boolean
	 * @throws DBException
	 * @throws IOException
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public static void doCollectTask(String taskId) throws DBException,
			IOException
	{
		// ҵ���߼�
		// ���ڲ�ͬί��ֵĽӿڶ���һ��(��������θ�������θ�ʽ�����θ�ʽ��)�����ݽ���ƽ̨�����ṩһ��ͳһ�ı�׼
		// ������ձ�׼���Ļ��Ϳ���ʹ��һ��ͳһ�ķ�����Ҫ��������͸��ݲ�ͬ�ĵ�λʹ�õĽӿڲ�ͬ������ͬ�Ĳɼ�����
		// ��ҵ������ֻ�����ڰ��ձ�׼��ʽwebservice�ɼ�
		// 1.��ȡ�����������ID
		// 2.��������ID��ѯ�ɼ��������NO
		// 3.���ݲ�ͬ�Ĳɼ������߲�ͬ�Ĳɼ�����������ǰ��ձ�׼��ʽ������ͳһ�����磺��˰��
		// 4.��������ID��ѯ������ϸ��Ϣ����¼��־
		// 5.��������ID��ѯ���������ϸ��Ϣ����¼��־
		// 6.��������ID��ѯ����Դ��Ϣ
		// 6a.ȷ��������Ҫ���õ�webservice��URL
		// 7.��������ID��ѯ�����ṩ�߹��ṩ�˶��ٸ����񷽷�,�����ÿ�����񷽷���Id
		// 8.���ݷ��񷽷�ID��ѯ�����ÿ����������ϸ��Ϣ
		// 8.���ݷ��񷽷�ID����ȡ��Ҫ����Ĳ���
		// 9.��װ����
		// 10.���÷��񣬲���ȡ�����
		// 11.���ݷ��񷽷�ID��ѯ���ô˷����õ��Ľ����Ҫ����Ĳɼ���
		// 12.��װ�����SQL���
		// 13.ִ��SQL����(����Ҫע������������ȫ��)
		// 13a.����������ȱ���ȡ�Ľ�����¼���󣬱�������������δȡ�꣬��ô�ظ�10ֱ������ȫ��ȡ��Ϊֹ
		// 14.��¼��־
		CollectLogVo collectLogVo = new CollectLogVo(); // �ɼ���־��¼
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
		logger.debug("��ǰʱ��Ϊ��" + startTime + "��ʼ�ɼ�����...");
		logger.debug("��õĲ�������IDΪ��[" + taskId + "]...");

		logger.debug("��ʼ��ѯ�ɼ��������...");
		String targetNo = taskInfo.queryTargetNo(taskId);
		logger.debug("��ѯ�ɼ�����������...");
		logger.debug("�������Ϊ��" + targetNo);

		String batch = UuidGenerator.getUUID(); // ���κ�
		collectLogVo.setBatch_num(batch);

		Map beforeMap = collectBefore(taskId, collectLogVo); // �ɼ�����׼������ ��ȡURL�������б�
		logger.debug("�жϲɼ�����...");
		// ��ʱֻ�����ֳ��������籣���������Ķ���һ��Ĭ��Ϊ�������ֶ̾����
		if ("rb_user".equals(targetNo)) {
			logger.debug("�ɼ�����Ϊ�����籣...");
		} else if ("W_ZJJ".equals(targetNo)) {
			logger.debug("�ɼ�����Ϊ�ʼ��...");
			QsWsClient qsWsClient = new QsWsClient();
			qsWsClient.qualitySupervisionCollecting(beforeMap, collectLogVo);
		} else {
			logger.debug("ͨ�òɼ�����...");
			collecting(beforeMap, collectLogVo); // ��˰�ɼ�����
		}
	}

	/**
	 * 
	 * collectBefore �ɼ�����׼������
	 * 
	 * @param taskId
	 * @param collectLogVo
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected static Map collectBefore(String taskId, CollectLogVo collectLogVo)
			throws DBException
	{
		Long start = System.currentTimeMillis(); // ��ʼʱ�����ڼ����ʱ
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
		collectLogVo.setTask_start_time(startTime); // �ɼ���ʼʱ��

		// ����taskId��ѯ�������
		logger.debug("��ʼ��ѯ����...");
		Map taskMap = taskInfo.queryTask(taskId);
		VoCollectTask taskVo = new VoCollectTask();
		ParamUtil.mapToBean(taskMap, taskVo, false);
		String srvTargetId = taskVo.getService_targets_id();
		logger.debug("��ѯ�������...");
		logger.debug("�ɼ�����IDΪ��" + srvTargetId);

		collectLogVo.setCollect_task_id(taskVo.getCollect_task_id());
		collectLogVo.setCollect_type(taskVo.getCollect_type());
		collectLogVo.setTask_name(taskVo.getTask_name());

		logger.debug("��ʼ��ѯ�ɼ�����...");
		VoResServiceTargets srvTargetVo = new VoResServiceTargets();
		Map srvTargetMap = taskInfo.querySrvTager(srvTargetId);
		ParamUtil.mapToBean(srvTargetMap, srvTargetVo, false);
		logger.debug("��ѯ�ɼ��������...");

		// ��־�������
		collectLogVo.setService_targets_id(srvTargetVo.getService_targets_id());
		collectLogVo.setService_targets_name(srvTargetVo
				.getService_targets_name());
		collectLogVo.setIs_formal(srvTargetVo.getIs_formal());

		// ����taskId��ѯ����Դ��Ϣ
		logger.debug("��ʼ��ѯ����Դ...");
		Map dataSourceMap = taskInfo.queryDataSource(taskId);
		VoResDataSource dataSourceVo = new VoResDataSource();
		ParamUtil.mapToBean(dataSourceMap, dataSourceVo, false);
		logger.debug("��ѯ����Դ���...");

		String accessUrl = dataSourceVo.getAccess_url();
		logger.debug("�������·��Ϊ��" + accessUrl);

		// ����taskId��ѯwebservice����
		logger.debug("��ʼ��ѯ���񷽷�...");
		List webserviceTask = taskInfo.queryMethod(taskId);
		logger.debug("��ѯ���񷽷����...");
		logger.debug("�÷�����[" + webserviceTask.size() + "]������...");

		// ����ֵ��װ
		Map tepMap = new HashMap();
		tepMap.put(CollectConstants.WSDL_URL, accessUrl);
		tepMap.put("METHOD_LIST", webserviceTask);

		// ��������ʱ
		Long end = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
		String consumeTime = String.valueOf(((end - start) / 1000f));
		logger.debug("�ɼ�����׼������ʱ��" + consumeTime + "��");

		return tepMap;
	}

	/**
	 * 
	 * collecting �ɼ�����ͨ�÷��� 
	 * @param beforeMap
	 * @param collectLogVo
	 * @throws DBException
	 * @throws IOException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected static void collecting(Map beforeMap, CollectLogVo collectLogVo)
			throws DBException, IOException
	{
		List webserviceTask = (List) beforeMap.get("METHOD_LIST"); // ��ȡ�ɼ������б�
		beforeMap.remove("METHOD_LIST");
		// 13a.����������ȱ���ȡ�Ľ�����¼���󣬱�������������δȡ�꣬��ô�ظ�10ֱ������ȫ��ȡ��Ϊֹ
		Map countMap = new HashMap();
		countMap.put("isDo", "N"); // ��һ������Ϊ���ظ�ȡ����
		System.out.println("countMap is " + countMap);
		for (int i = 0; i < webserviceTask.size(); i++) {
			Map wsTask = (Map) webserviceTask.get(i);
			doGetData(beforeMap, wsTask, collectLogVo, countMap); // ִ�вɼ�����
		}
	}

	/**
	 * 
	 * doGetData doGetData �ɼ�����
	 * 
	 * @param beforeMap
	 * @param webserviceTask
	 * @param collectLogVo
	 * @throws DBException
	 * @throws IOException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected static void doGetData(Map beforeMap, Map webserviceTask,
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

		Map resultMap = new HashMap();
		try {
			// ���ݷ���ID��ѯ�����Ĳ���
			logger.debug("��ʼ��ѯ���񷽷�����...");
			List paramList = taskInfo.queryParam(wsTaskId);
			logger.debug("��ѯ���񷽷��������...");

			logger.debug("��ʼ��ʽ������...");
			Map paramValueMap = taskInfo.fromatParamValue(paramList);
			logger.debug("valueList is " + paramValueMap);

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
			// ��������ʱ
			Long end = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
			String consumeTime = String.valueOf(((end - start) / 1000f));
			logger.debug("��ʱ��ʼ���÷��񷽷��ڴ�֮ǰ����ʱ��" + consumeTime + "��");

			Long star2 = System.currentTimeMillis();
			// ���ýӿ�
			GSGeneralClient gs = new GSGeneralClient();
			String dom = gs.collectData(paraMap, collectLogVo, countMap);
			Long end2 = System.currentTimeMillis();
			String consumeTime2 = String.valueOf(((end2 - star2) / 1000f));
			collectLogVo.setInvoke_consume_time(consumeTime2);
			//logger.debug("���ؽ��="+dom);
			logger.debug("��ʼ�����õķ��ؽ��...");
			resultMap = analyzeResult(dom, collectLogVo);
			//logger.debug("ת����Ľ��="+resultMap);
			boolean flag = Boolean.valueOf(resultMap.get("status").toString());

			// �����ؽ��������
			if (flag) {
				
				Map domMap = (Map) resultMap.get("result");
				DataHandleInterface dataHandle = DataHandleFactory
						.getDataHandle();
				int count = dataHandle.insertData(tableId, collectMode, domMap,
						collectLogVo);
				collectLogVo.setCollect_data_amount(String.valueOf(count));
			} else {
				logger.error("�ɼ�ʧ�� ������="+collectLogVo.getReturn_codes());
				collectLogVo.setCollect_data_amount("0");
				//��ȡ�ɼ�����
				Map tableMap = taskInfo.queryTable(tableId);
				if (!tableMap.isEmpty()) {					
					String tableName = tableMap
							.get(CollectConstants.COLLECT_TABLE_NAME).toString();// ��ȡ��Ӧ�������
					collectLogVo.setCollect_table_name(tableName);
				}
			}

			String endTime = sDateFormat.format(new java.util.Date());// ϵͳ��ǰ������ʱ����
			collectLogVo.setTask_end_time(endTime);

			// inputFile(dom, srvCode); // �����ļ�
		} catch (DBException e) {
			e.printStackTrace();
		} finally {
			Long end1 = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
			String consumeTime1 = String.valueOf(((end1 - start) / 1000f));
			collectLogVo.setTask_consume_time(consumeTime1);
			logger.debug("�ɼ����ݹ���ʱ��" + consumeTime1 + "��");
			//System.out.println("�ɼ�������"+collectLogVo.getCollect_table_name());
			logger.debug("��ʼ��¼��־...");
			taskInfo.insertLog(collectLogVo);
			logger.debug("��¼��־�ɹ�...");

			// �ݹ��ڴ˷������ж��Ƿ����������ڽ�����¼��,������������ڽ�����¼����Ҫ�ٴ�ȡ���ݣ�
			// ÿ��ȡ���ݵĿ�ʼ��¼��Ϊ�ϴν�����¼��+1��ÿ��ȡ���ݵĽ�����¼��Ϊ�ϴεĽ�����¼��+ÿ��ȡ������
			// ÿ��ȡ���ݵĿ�ʼ��¼���ͽ�����¼�������ڱ���countMap��
			isDoGetData(resultMap, beforeMap, webserviceTask, collectLogVo,
					countMap);
		}
	}

	/**
	 * 
	 * analyzeResult ������õķ��ؽ���Ƿ���ȷ
	 * 
	 * @param dom
	 * @param collectLogVo
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	private static Map analyzeResult(String dom, CollectLogVo collectLogVo)
	{
		Map tepMap = new HashMap();
		boolean flag = false; // ������õķ��ؽ���Ƿ���ȷ
		if (null != dom && !"".equals(dom)) {
			try {
				// ������Ƿ���ת����xml
				Document doc = DocumentHelper.parseText(dom);
				// �����ת����MAP
				Map domMap = XmlToMapUtil.dom2Map(dom);
				tepMap.put("result", domMap);
				flag = true;
			} catch (DocumentException e) {
				collectLogVo
						.setReturn_codes(CollectConstants.CLIENT_FHDM_TODOM_ERROR);
				logger.debug("������ת����dom��ʽ����..." + e);
				e.printStackTrace();
			} finally {
			}
		} else {
			collectLogVo.setReturn_codes(CollectConstants.CLIENT_FHDM_FAIL);
		}
		tepMap.put("status", flag);
		return tepMap;
	}

	/**
	 * 
	 * isDoGetData �ж��Ƿ�Ҫ�ٴ�ȡ����
	 * 
	 * @param resultMap
	 * @param beforeMap
	 * @param webserviceTask
	 * @param collectLogVo
	 * @param countMap
	 * @return void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public static void isDoGetData(Map resultMap, Map beforeMap,
			Map webserviceTask, CollectLogVo collectLogVo, Map countMap)
	{
		logger.debug("last time countMap is " + countMap);
		String startCount = "";
		String endCount = "";
		String allCount = "";
		if (null != resultMap.get("result")) {
			Map domMap = (Map) resultMap.get("result");
			if (null != domMap.get(CollectConstants.CLIENT_COLLECT_PARAM_FHDM)
					&& CollectConstants.CLIENT_FHDM_SUCCESS.equals(domMap
							.get(CollectConstants.CLIENT_COLLECT_PARAM_FHDM))) {
				if (null != domMap.get(CollectConstants.KSJLS))
					startCount = domMap.get(CollectConstants.KSJLS).toString();
				if (null != domMap.get(CollectConstants.JSJLS))
					endCount = domMap.get(CollectConstants.JSJLS).toString();

				if (null != domMap.get(CollectConstants.ZTS))
					allCount = domMap.get(CollectConstants.ZTS).toString();
			}
		}
		countMap.put("isDo", "N");
		if (!"".equals(startCount) && !"".equals(endCount)
				&& !"".equals(allCount)) {
			int zts = Integer.parseInt(allCount);
			int jsjls = Integer.parseInt(endCount);
			int ksjls = Integer.parseInt(startCount);
			// �����������ڽ�����¼��ʱ
			if (zts > jsjls) {
				int everCount = jsjls - ksjls;
				ksjls = jsjls + 1; // �´εĿ�ʼ��¼��
				jsjls = ksjls + everCount; // �´εĽ�����¼��
				countMap.put(CollectConstants.KSJLS, ksjls);
				countMap.put(CollectConstants.JSJLS, jsjls);
				countMap.put("isDo", "Y"); // �Ƿ�Ҫ�����´�ȡ����

				logger.debug("׼���ٴ�ȡ���ݣ�ȡ���ݵĲ���Ϊ" + countMap);
				doGetData(beforeMap, webserviceTask, collectLogVo, countMap);
			} else {
				logger.debug("����һ��ȫ��ȡ���˲����ٴ�ȡ����...");
			}
		}
	}

	/**
	 * 
	 * inputFile ����ļ�
	 * 
	 * @param dom
	 * @param serviceCode
	 * @throws IOException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	// private static void inputFile(String dom, String serviceCode)
	// throws IOException
	// {
	// java.sql.Timestamp dateForFileName = new java.sql.Timestamp(new Date()
	// .getTime());
	//
	// logger.debug("��ʼ�洢xml�ļ�...");
	// StringBuffer path = new StringBuffer();
	// path.append(ExConstant.COLLECT_XML);
	// path.append(File.separator);
	// path.append(serviceCode);
	// path.append("_");
	// path.append(timeFormat(dateForFileName.toString()));
	// path.append(".xml");
	//
	// OutputFormat format = OutputFormat.createPrettyPrint();
	// XMLWriter output = null;
	// try {
	// Document doc = DocumentHelper.parseText(dom);
	// output = new XMLWriter(new FileOutputStream(new File(path
	// .toString())), format);
	// output.write(doc);
	// logger.debug("�洢xml�ļ����...");
	// logger.debug("xml�ļ��洢·��Ϊ��" + path);
	// } catch (DocumentException e) {
	// if (null != output)
	// output.close();
	// logger.debug("DocumentException:" + e);
	// e.printStackTrace();
	// } catch (UnsupportedEncodingException e) {
	// if (null != output)
	// output.close();
	// logger.debug("UnsupportedEncodingException:" + e);
	// e.printStackTrace();
	// } catch (FileNotFoundException e) {
	// if (null != output)
	// output.close();
	// logger.debug("FileNotFoundException:" + e);
	// e.printStackTrace();
	// } catch (IOException e1) {
	// if (null != output)
	// output.close();
	// logger.debug("IOException:" + e1);
	// e1.printStackTrace();
	// } finally {
	// if (null != output)
	// output.close();
	// }
	// }
	/**
	 * 
	 * timeFormat ��ϵͳʱ���Ϊ�ض���ʽ
	 * 
	 * @param time
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	private static String timeFormat(String time)
	{
		String currenttime = "";
		if (time != null && !"".equals(time)) {
			String temp[] = time.split(" ");
			String temp1[] = temp[0].split("-");
			String temp2[] = temp[1].split(":");
			for (int i = 0; i < temp1.length; i++) {
				currenttime = currenttime + temp1[i];
			}
			for (int j = 0; j < temp2.length - 1; j++) {
				currenttime = currenttime + temp2[j];
			}
		}
		return currenttime;
	}
}
