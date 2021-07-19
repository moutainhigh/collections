package com.gwssi.jms.cj.consumer;

import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;

import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.util.XmlToMapUtil;

public class DsListener implements MessageListener {
	
	public void onMessage(Message message)
	{

		TextMessage textMessage = (TextMessage) message;
		try {
			System.out.println(textMessage.getText());

			String xml = textMessage.getText();
			Map param = XmlToMapUtil.dom2Map(xml);// 将参数转化成Map
			String methodName = (String) param.get(CollectConstants.QNAME);
			String cjResName=(String) param.get(CollectConstants.CJ_RES_NAME);
			JmsServerServiceCj service = new JmsServerServiceCj();
			String result = null;
			if (methodName != null && methodName.equals("queryData")) {
				result = service.queryData(xml);
			}
			result = result.replace("\n", "");
			System.out.println("返回查询结果" + result);

			//String url = "http://172.30.18.50:8089";
			String url = CollectConstants.JMS_SERVER_URL;
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

			ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
			connection.start();
			ActiveMQSession session = (ActiveMQSession) connection
					.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(cjResName);//CjResGsj
			MessageProducer messageProducer = session
					.createProducer(destination);
			TextMessage ms = session.createTextMessage();
			ms.setText(result);
			messageProducer.send(ms);
			session.close();
			connection.stop();
			connection.close();

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
