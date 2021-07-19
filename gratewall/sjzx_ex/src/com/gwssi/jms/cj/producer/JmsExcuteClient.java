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
 * 项目名称：bjgs_exchange 类名称：ExcuteClient 类描述：客户端的执行方法 创建人：lizheng 创建时间：Apr 2, 2013
 * 3:53:30 PM 修改人：lizheng 修改时间：Apr 2, 2013 3:53:30 PM 修改备注：
 * 
 * @version
 * 
 */
public class JmsExcuteClient extends JmsAbsGeneralClient
{
	// 日志
	protected static Logger	logger	= TxnLogger.getLogger(JmsExcuteClient.class.getName());
	String result=null;
	
	
	public String colGetDom(Map param, CollectLogVo collectLogVo)
	{
		logger.debug("开始调用对方Jms服务提供的采集数据的方法...");
		Long start = System.currentTimeMillis(); // 结束时间用于计算耗时
	
		String wsTaskId = ""; // 方法ID
		if (null != param.get(CollectConstants.WEBSERVICE_TASK_ID)
				&& !"".equals(param.get(CollectConstants.WEBSERVICE_TASK_ID).toString())) {
			wsTaskId = param.get(CollectConstants.WEBSERVICE_TASK_ID).toString();
			
		}
		logger.debug("wsTaskId :" + wsTaskId);
		
		String cjReqName = ""; // 请求队列名称
		if (null != param.get(CollectConstants.CJ_REQ_NAME)
				&& !"".equals(param.get(CollectConstants.CJ_REQ_NAME).toString())) {
			cjReqName = param.get(CollectConstants.CJ_REQ_NAME).toString();
			
		}
		logger.debug("请求队列名称为 :" + cjReqName);
		
		String cjResName = ""; // 返回队列名称
		
		if (null != param.get(CollectConstants.CJ_RES_NAME)
				&& !"".equals(param.get(CollectConstants.CJ_RES_NAME).toString())) {
			cjResName = param.get(CollectConstants.CJ_RES_NAME).toString();
			
		}
		logger.debug("返回队列名称为 :" + cjResName);
		
//		if (null != param.get(CollectConstants.WSDL_URL)
//				&& !"".equals(param.get(CollectConstants.WSDL_URL).toString())) {
//			targetEendPoint = param.get(CollectConstants.WSDL_URL).toString();
//			param.remove(CollectConstants.WSDL_URL);
//		}
//		logger.debug("调用访问路径为 :" + targetEendPoint);
		String ip = ""; // IP
		if (null != param.get(CollectConstants.WSDL_IP)
				&& !"".equals(param.get(CollectConstants.WSDL_IP).toString())) {
			ip = param.get(CollectConstants.WSDL_IP).toString();
			param.remove(CollectConstants.WSDL_IP);
		}
		logger.debug("调用访问IP为 :" + ip);
		
		String port = ""; // PORT
		if (null != param.get(CollectConstants.WSDL_PORT)
				&& !"".equals(param.get(CollectConstants.WSDL_PORT).toString())) {
			port = param.get(CollectConstants.WSDL_PORT).toString();
			param.remove(CollectConstants.WSDL_PORT);
		}
		logger.debug("调用访问port为 :" + port);
		
		
		String qName = ""; // 参数
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
		ValueMap.put(CollectConstants.QNAME, qName);//方法名称
		ValueMap.put(CollectConstants.CJ_RES_NAME, cjResName);//返回队列名称
		ValueMap.put(CollectConstants.WEBSERVICE_TASK_ID, wsTaskId);//方法ID
		
		
		System.out.println("封装Map为 :" + ValueMap);
		
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
				logger.debug("JMS服务错误..." + e);
				e.printStackTrace();
				return XmlToMapUtil.map2Dom(ResultFormat.createWsError());
			} finally {

				Long end = System.currentTimeMillis(); // 结束时间用于计算耗时
				String consumeTime = String.valueOf(((end - start) / 1000f));
				logger.debug("调用服务操作并接收到对方数据共耗时：" + consumeTime + "秒！");
			}
		} else {
			// 调用方法路径和方法名称错误
			collectLogVo
					.setReturn_codes(CollectConstants.CLIENT_FHDM_TASK_ERROR);
			logger.debug("调用JMS服务方法名称或访问URL错误...");
			return XmlToMapUtil.map2Dom(ResultFormat.createTaskError());
		}
	}

	
}
