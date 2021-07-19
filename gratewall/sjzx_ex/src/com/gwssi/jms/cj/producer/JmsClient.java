package com.gwssi.jms.cj.producer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.collect.webservice.vo.VoCollectTask;
import com.gwssi.collect.webservice.vo.VoCollectWebserviceTask;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.util.ParamUtil;
import com.gwssi.log.collectlog.dao.CollectLogVo;
import com.gwssi.resource.svrobj.vo.VoResDataSource;
import com.gwssi.resource.svrobj.vo.VoResServiceTargets;
import com.gwssi.socket.client.SocketGSGeneralClient;
import com.gwssi.webservice.client.TaskInfo;
import com.gwssi.webservice.client.WsClient;
import com.gwssi.webservice.client.collect.DataHandleFactory;
import com.gwssi.webservice.client.collect.DataHandleInterface;

public class JmsClient
{

	protected static Logger	logger		= TxnLogger.getLogger(WsClient.class.getName());	// ��־

	static TaskInfo			taskInfo	= new TaskInfo();		// ������Ϣ

	/**
	 * 
	 * doCollectTask ִ�� Jms �ɼ�����
	 * 
	 * @param taskId
	 * @return boolean
	 * @throws DBException
	 * @throws IOException
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public static void doCollectTask(String taskId) throws DBException,IOException
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
		
		int c = CollectConstants.COUNT;
		CollectConstants.COUNT++;
		System.out.println("��" + c + "��");

		CollectLogVo collectLogVo = new CollectLogVo(); // �ɼ���־��¼
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
		logger.debug("��ǰʱ��Ϊ��" + startTime + "��ʼ�ɼ�����...");
		logger.debug("��õĲ�������IDΪ��[" + taskId + "]...");

		//logger.debug("��ʼ��ѯ�ɼ��������...");
		//String targetNo = taskInfo.queryTargetNo(taskId);
		//logger.debug("��ѯ�ɼ�����������...");
		//logger.debug("�������Ϊ��" + targetNo);
		
		Map beforeMap = collectBefore(taskId, collectLogVo); // �ɼ�����׼������

		//doGetData(taskId, beforeMap, collectLogVo); // ��Jms �ɼ�����
		collecting(beforeMap, collectLogVo); // ��Jms �ɼ�����

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

		// ����taskId��ѯ����Դ��Ϣ
		logger.debug("��ʼ��ѯ����Դ...");
		Map dataSourceMap = taskInfo.queryDataSource(taskId);
		VoResDataSource dataSourceVo = new VoResDataSource();
		ParamUtil.mapToBean(dataSourceMap, dataSourceVo, false);
		logger.debug("��ѯ����Դ���...");

//		String accessUrl = dataSourceVo.getAccess_url();
//		logger.debug("�������·��Ϊ��" + accessUrl);
//		
		String ip=dataSourceVo.getData_source_ip();
		logger.debug("�������IPΪ��" + ip);
		
		String port=dataSourceVo.getAccess_port();
		logger.debug("������ʶ˿�Ϊ��" + port);
		
		String cjReqName = dataSourceVo.getReq_que_name();
		logger.debug("�����������Ϊ��" + cjReqName);
		
		String cjResName = dataSourceVo.getRes_que_name();
		logger.debug("���ض�������Ϊ��" + cjReqName);
		
		// ����taskId��ѯwebservice����
		logger.debug("��ʼ��ѯ���񷽷�...");
		List webserviceTask = taskInfo.queryMethod(taskId);
		logger.debug("��ѯ���񷽷����...");
		logger.debug("�÷�����[" + webserviceTask.size() + "]������...");

		// ����ֵ��װ
		Map tepMap = new HashMap();
		tepMap.put(CollectConstants.CJ_REQ_NAME, cjReqName);
		tepMap.put(CollectConstants.CJ_RES_NAME, cjResName);
		
//		tepMap.put(CollectConstants.WSDL_URL, accessUrl);
		tepMap.put(CollectConstants.WSDL_IP, ip);
		tepMap.put(CollectConstants.WSDL_PORT, port);
		tepMap.put("METHOD_LIST", webserviceTask);

		// ��������ʱ
		Long end = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
		String consumeTime = String.valueOf(((end - start) / 1000f));
		logger.debug("�ɼ�����׼������ʱ��" + consumeTime + "��");

		return tepMap;
	}
	
	/**
	 * 
	 * collecting �ɼ�������
	 * 
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
		for (int i = 0; i < webserviceTask.size(); i++) {
			Map wsTask = (Map) webserviceTask.get(i);
			doGetData(beforeMap, wsTask, collectLogVo); // ִ�вɼ�����
		}
	}
	/**
	 * 
	 * doGetData �ɼ�����
	 * 
	 * @param taskId
	 * @param beforeMap
	 * @param collectLogVo
	 * @throws DBException
	 * @throws IOException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected static void doGetData(Map beforeMap,Map webserviceTask,
			CollectLogVo collectLogVo) throws DBException, IOException
	{
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Long start = System.currentTimeMillis(); // ��ʼʱ�����ڼ����ʱ
		
		String ip = beforeMap.get(CollectConstants.WSDL_IP).toString();
		
		String port = beforeMap.get(CollectConstants.WSDL_PORT).toString();
		
		String cjReqName = beforeMap.get(CollectConstants.CJ_REQ_NAME).toString();
		
		String cjResName = beforeMap.get(CollectConstants.CJ_RES_NAME).toString();
		
		Map wsTask = webserviceTask;
		VoCollectWebserviceTask wsTaskVo = new VoCollectWebserviceTask();
		ParamUtil.mapToBean(wsTask, wsTaskVo, false);
		
		String wsTaskId = wsTaskVo.getWebservice_task_id();
		String qName = wsTaskVo.getMethod_name_en();
		String qNameCn = wsTaskVo.getMethod_name_cn();
		String tableId = wsTaskVo.getCollect_table();
		String collectMode = wsTaskVo.getCollect_mode();
		String srvCode = wsTaskVo.getService_no();
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

			logger.debug("��ʼ��ʽ������...");
			Map paramValueMap = taskInfo.fromatParamValue(paramList);
			logger.debug("valueList is " + paramValueMap);
			Map paraMap = new HashMap();
			paraMap.put(CollectConstants.QNAME, qName); // ��������
			paraMap.put(CollectConstants.WSDL_IP, ip); //ip
			paraMap.put(CollectConstants.WSDL_PORT, port); // �˿�
			paraMap.put(CollectConstants.PARAM_LIST, paramValueMap
					.get(CollectConstants.PARAM_LIST)); // ������
			paraMap.put(CollectConstants.PARAM_VALUE, paramValueMap
					.get(CollectConstants.PARAM_VALUE)); // ����ֵ
			paraMap.put(CollectConstants.CJ_REQ_NAME, cjReqName); // �����������
			paraMap.put(CollectConstants.CJ_RES_NAME, cjResName); // ���ض�������
			paraMap.put(CollectConstants.WEBSERVICE_TASK_ID, wsTaskId); // ����ID
			
			logger.debug("��ʽ���������,����Ϊ..." + paraMap);
			
			// ���ýӿ�
			JmsGSGeneralClient JmsGSGeneralClient = new JmsGSGeneralClient();
			
			JmsGSGeneralClient.colGetDom(paraMap, collectLogVo);
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			// ��������ʱ
			Long end = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ

			String consumeTime = String.valueOf(((end - start) / 1000f));
			logger.debug("�������ݸ�jms����������ʱ��" + consumeTime + "��");
		}
			
	}
			
		
	/**
	 * 
	 * analyzeResult ������õķ��ؽ���Ƿ���ȷ
	 * 
	 * @param dom
	 * @param collectLogVo
	 * @return boolean
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	private static boolean analyzeResult(String dom, CollectLogVo collectLogVo)
	{
		boolean flag = false;
		if (null != dom && !"".equals(dom)) {
			try {
				Document doc = DocumentHelper.parseText(dom);
				flag = true;
			} catch (DocumentException e) {
				collectLogVo
						.setReturn_codes(CollectConstants.CLIENT_FHDM_TODOM_ERROR);
				logger.debug("������ת����dom��ʽ����..." + e);
				e.printStackTrace();
			} finally {
				return flag;
			}
		} else {
			collectLogVo.setReturn_codes(CollectConstants.CLIENT_FHDM_FAIL);
			return flag;
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
	private static void inputFile(String dom, String serviceCode)
			throws IOException
	{
		java.sql.Timestamp dateForFileName = new java.sql.Timestamp(new Date()
				.getTime());

		logger.debug("��ʼ�洢xml�ļ�...");
		StringBuffer path = new StringBuffer();
		path.append(ExConstant.COLLECT_XML);
		path.append(File.separator);
		path.append(serviceCode);
		path.append("_");
		path.append(timeFormat(dateForFileName.toString()));
		path.append(".xml");

		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter output = null;
		try {
			Document doc = DocumentHelper.parseText(dom);
			output = new XMLWriter(new FileOutputStream(new File(path
					.toString())), format);
			output.write(doc);
			logger.debug("�洢xml�ļ����...");
			logger.debug("xml�ļ��洢·��Ϊ��" + path);

		} catch (DocumentException e) {
			if (null != output)
				output.close();
			logger.debug("DocumentException:" + e);
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			if (null != output)
				output.close();
			logger.debug("UnsupportedEncodingException:" + e);
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			if (null != output)
				output.close();
			logger.debug("FileNotFoundException:" + e);
			e.printStackTrace();
		} catch (IOException e1) {
			if (null != output)
				output.close();
			logger.debug("IOException:" + e1);
			e1.printStackTrace();
		} finally {
			if (null != output)
				output.close();
		}
	}
	
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
