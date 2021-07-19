package com.gwssi.jms.cj.consumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.util.XmlToMapUtil;
import com.gwssi.log.collectlog.dao.CollectLogVo;
import com.gwssi.webservice.client.ClientDAO;
import com.gwssi.webservice.client.ClientDAOImpl;
import com.gwssi.webservice.client.TaskInfo;
import com.gwssi.webservice.client.WsClient;
import com.gwssi.webservice.client.collect.DataHandleFactory;
import com.gwssi.webservice.client.collect.DataHandleInterface;

public class JmsCjResListener implements MessageListener {
	protected static Logger	logger		= TxnLogger.getLogger(WsClient.class.getName());	// ��־
	
	
	
	
	public void onMessage(Message message)
	{

		TextMessage textMessage = (TextMessage) message;
		 TaskInfo			taskInfo	= new TaskInfo();		// ������Ϣ
		
		CollectLogVo collectLogVo = new CollectLogVo();
		try {
			System.out.println(textMessage.getText());

			String dom = textMessage.getText();
			
			Map param = XmlToMapUtil.dom2Map(dom);// ������ת����Map
			
			String wsTaskId=(String) param.get(CollectConstants.WEBSERVICE_TASK_ID);//����ID
			
			//��ѯ�ɼ������
			StringBuffer sql = new StringBuffer();
			
			sql.append("select * from collect_webservice_task t ");
			sql.append("where t.webservice_task_id =  '"+wsTaskId+"'");
			ClientDAO	dao = new ClientDAOImpl();
			Map webserviceMap = new HashMap();
			try {
				webserviceMap = dao.queryWebTask(sql.toString());
			} catch (DBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    String tableId=(String)webserviceMap.get("COLLECT_TABLE");
		    String  collectMode=(String)webserviceMap.get("COLLECT_MODE");
		    String svr_code=(String)webserviceMap.get("SERVICE_NO");
			
			// ��־ws����
			collectLogVo.setTask_id(wsTaskId);
			collectLogVo.setService_no(svr_code);
			
			
			//��������
			
			System.out.println("�ͻ��˽��յ��������˷��ؽ��Ϊ"+dom);
			
			logger.debug("��ʼ�����õķ��ؽ��...");
			Map resultMap = analyzeResult(dom, collectLogVo);
			boolean flag = Boolean.valueOf(resultMap.get("status").toString());
			// �����ؽ��������
			if (flag) {
				Map domMap = (Map) resultMap.get("result");
				DataHandleInterface dataHandle = DataHandleFactory
						.getDataHandle();
				int count=-1;
				try {
					count = dataHandle.insertData(tableId, collectMode, domMap,
							collectLogVo);
				} catch (DBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				collectLogVo.setCollect_data_amount(String.valueOf(count));
			} else {
				collectLogVo.setCollect_data_amount("0");
			}

			

			// �����ļ�
			try {
				inputFile(dom, svr_code);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// ��¼��־
			logger.debug("��ʼ��¼��־...");
			taskInfo.insertLog(collectLogVo);
			logger.debug("��¼��־�ɹ�...");
			
		
			

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	private static Map analyzeResult(String dom, CollectLogVo collectLogVo)
	{
		Map tepMap = new HashMap();
		boolean flag = false;
		if (null != dom && !"".equals(dom)) {
			try {
				Document doc = DocumentHelper.parseText(dom);
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
