package com.gwssi.jms.cj.producer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;

import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.util.XmlToMapUtil;
import com.gwssi.log.collectlog.dao.CollectLogVo;
import com.gwssi.webservice.client.ResultFormat;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�ExcuteClient ���������ͻ��˵�ִ�з��� �����ˣ�lizheng ����ʱ�䣺Apr 2, 2013
 * 3:53:30 PM �޸��ˣ�lizheng �޸�ʱ�䣺Apr 2, 2013 3:53:30 PM �޸ı�ע��
 * 
 * @version
 * 
 */
public class JmsExcuteClient extends JmsAbsGeneralClient
{
	// ��־
	protected static Logger	logger	= TxnLogger.getLogger(JmsExcuteClient.class.getName());
	String result=null;
	
	
	public String colGetDom(Map param, CollectLogVo collectLogVo)
	{
		logger.debug("��ʼ���öԷ�Jms�����ṩ�Ĳɼ����ݵķ���...");
		Long start = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
	
		String wsTaskId = ""; // ����ID
		if (null != param.get(CollectConstants.WEBSERVICE_TASK_ID)
				&& !"".equals(param.get(CollectConstants.WEBSERVICE_TASK_ID).toString())) {
			wsTaskId = param.get(CollectConstants.WEBSERVICE_TASK_ID).toString();
			
		}
		logger.debug("wsTaskId :" + wsTaskId);
		
		String cjReqName = ""; // �����������
		if (null != param.get(CollectConstants.CJ_REQ_NAME)
				&& !"".equals(param.get(CollectConstants.CJ_REQ_NAME).toString())) {
			cjReqName = param.get(CollectConstants.CJ_REQ_NAME).toString();
			
		}
		logger.debug("�����������Ϊ :" + cjReqName);
		
		String cjResName = ""; // ���ض�������
		
		if (null != param.get(CollectConstants.CJ_RES_NAME)
				&& !"".equals(param.get(CollectConstants.CJ_RES_NAME).toString())) {
			cjResName = param.get(CollectConstants.CJ_RES_NAME).toString();
			
		}
		logger.debug("���ض�������Ϊ :" + cjResName);
		
//		if (null != param.get(CollectConstants.WSDL_URL)
//				&& !"".equals(param.get(CollectConstants.WSDL_URL).toString())) {
//			targetEendPoint = param.get(CollectConstants.WSDL_URL).toString();
//			param.remove(CollectConstants.WSDL_URL);
//		}
//		logger.debug("���÷���·��Ϊ :" + targetEendPoint);
		String ip = ""; // IP
		if (null != param.get(CollectConstants.WSDL_IP)
				&& !"".equals(param.get(CollectConstants.WSDL_IP).toString())) {
			ip = param.get(CollectConstants.WSDL_IP).toString();
			param.remove(CollectConstants.WSDL_IP);
		}
		logger.debug("���÷���IPΪ :" + ip);
		
		String port = ""; // PORT
		if (null != param.get(CollectConstants.WSDL_PORT)
				&& !"".equals(param.get(CollectConstants.WSDL_PORT).toString())) {
			port = param.get(CollectConstants.WSDL_PORT).toString();
			param.remove(CollectConstants.WSDL_PORT);
		}
		logger.debug("���÷���portΪ :" + port);
		
		
		String qName = ""; // ����
		if (null != param.get(CollectConstants.QNAME)
				&& !"".equals(param.get(CollectConstants.QNAME).toString())) {
			qName = param.get(CollectConstants.QNAME).toString();
			//param.remove(CollectConstants.QNAME);
		}
		String paramName = "";
		if (null != param.get(CollectConstants.PARAM_LIST)) {
			List paramNameList = (List) param.get(CollectConstants.PARAM_LIST);
			paramName = paramNameList.get(0).toString();
		}

		Map ValueMap = new HashMap();
		if (null != param.get(CollectConstants.PARAM_VALUE)) {
			Map paramValueMap = (Map) param.get(CollectConstants.PARAM_VALUE);
			ValueMap = (Map) paramValueMap.get(paramName);
		}
		ValueMap.put(CollectConstants.QNAME, qName);//��������
		ValueMap.put(CollectConstants.CJ_RES_NAME, cjResName);//���ض�������
		ValueMap.put(CollectConstants.WEBSERVICE_TASK_ID, wsTaskId);//����ID
		
		
		System.out.println("��װMapΪ :" + ValueMap);
		
		if (!"".equals(cjReqName)&& !"".equals(qName)) {
			try {
				
				String dom = XmlToMapUtil.map2Dom(ValueMap);
				System.out.println("dom==========="+dom.replace("\n", ""));
				
				String url = "http://"+ip+":"+port;////////////////String url = "http://172.30.18.50:8089";
				ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
				try {
					ActiveMQConnection connection = (ActiveMQConnection)connectionFactory.createConnection();
					connection.start();
					ActiveMQSession session = (ActiveMQSession)connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
					Destination destination = session.createQueue(cjReqName);
					MessageProducer messageProducer = session.createProducer(destination);
					TextMessage message = session.createTextMessage();
					message.setText(dom);
					messageProducer.send(message);
					session.close();
					connection.stop();
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return CollectConstants.CLIENT_FHDM_SUCCESS;
			} catch (Exception e) {
				collectLogVo
						.setReturn_codes(CollectConstants.CLIENT_FHDM_WS_ERROR);
				logger.debug("JMS�������..." + e);
				e.printStackTrace();
				return XmlToMapUtil.map2Dom(ResultFormat.createWsError());
			} finally {

				Long end = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
				String consumeTime = String.valueOf(((end - start) / 1000f));
				logger.debug("���÷�����������յ��Է����ݹ���ʱ��" + consumeTime + "�룡");
			}
		} else {
			// ���÷���·���ͷ������ƴ���
			collectLogVo
					.setReturn_codes(CollectConstants.CLIENT_FHDM_TASK_ERROR);
			logger.debug("����JMS���񷽷����ƻ����URL����...");
			return XmlToMapUtil.map2Dom(ResultFormat.createTaskError());
		}
	}

	
}
