package com.gwssi.jms.share.producer;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;

import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.constant.ShareConstants;
import com.gwssi.common.util.XmlToMapUtil;

public class DsClient {
	
	public static void main(String[] args){
		String url = CollectConstants.JMS_SERVER_URL;
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		try {
			ActiveMQConnection connection = (ActiveMQConnection)connectionFactory.createConnection();
			connection.start();
			ActiveMQSession session = (ActiveMQSession)connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("GxReqGsj");
			MessageProducer messageProducer = session.createProducer(destination);
			TextMessage message = session.createTextMessage();
			
			Map param = new HashMap();
			param.put(ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE, "service300");
			param.put("QUEUE_NAME", "GxResDs");
			param.put("LOGIN_NAME", "W_DS");
			param.put("PASSWORD", "15abdb76");
			param.put(ShareConstants.SERVICE_OUT_PARAM_KSJLS, "1");
			param.put(ShareConstants.SERVICE_OUT_PARAM_JSJLS, "5");
			//param.put("USER_TYPE", "TEST");
			param.put("UPDATE_DATE", "2012-06-01,2013-07-01");
			String dom = XmlToMapUtil.map2Dom(param);
			dom=dom.replace("\n", "");
			
			message.setText(dom);
			messageProducer.send(message);
			session.close();
			connection.stop();
			connection.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
