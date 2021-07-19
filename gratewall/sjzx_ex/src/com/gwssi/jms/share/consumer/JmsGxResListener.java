package com.gwssi.jms.share.consumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
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
import com.gwssi.socket.server.ServerServiceCj;
import com.gwssi.socket.share.server.ServerService;
import com.gwssi.webservice.client.ClientDAO;
import com.gwssi.webservice.client.ClientDAOImpl;
import com.gwssi.webservice.client.TaskInfo;
import com.gwssi.webservice.client.WsClient;
import com.gwssi.webservice.client.collect.DataHandleFactory;
import com.gwssi.webservice.client.collect.DataHandleInterface;

public class JmsGxResListener implements MessageListener {
	protected static Logger	logger		= TxnLogger.getLogger(WsClient.class.getName());	// 日志
	
	
	
	
	public void onMessage(Message message)
	{

		TextMessage textMessage = (TextMessage) message;

		try {
			System.out.println(textMessage.getText());

			String dom = textMessage.getText();
			String result = null;
			String ip = "";
			
			Map param = XmlToMapUtil.dom2Map(dom);// 将参数转化成Map
			String queueName=(String)param.get("QUEUE_NAME");
			// //////共享
			ServerService service = new ServerService();
			result = service.queryData(dom, ip);// 查询共享数据
			result = result.replace("\n", "");
			
			String url = CollectConstants.JMS_SERVER_URL;
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

			ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
			connection.start();
			ActiveMQSession session = (ActiveMQSession) connection
					.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(queueName);
			MessageProducer messageProducer = session
					.createProducer(destination);
			TextMessage ms = session.createTextMessage();
			ms.setText(result);
			messageProducer.send(ms);
			session.close();
			connection.stop();
			connection.close();
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
